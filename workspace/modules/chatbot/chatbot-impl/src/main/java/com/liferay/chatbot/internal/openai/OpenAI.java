package com.liferay.chatbot.internal.openai;

import com.liferay.chatbot.dto.v1_0.Message;
import com.liferay.chatbot.internal.util.HttpUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.stream.Stream;

/**
 * @author Alejandro Tardín
 */
public class OpenAI {

	public static String getChatCompletion(
			Message[] messages, String openAPIURL)
		throws Exception {

		JSONObject requestJSONObject = JSONFactoryUtil.createJSONObject(
			StringUtil.read(OpenAI.class, "/openai-request.json"));

		JSONArray messagesJSONArray = requestJSONObject.getJSONArray(
			"messages");

		Stream.of(
			messages
		).map(
			message -> JSONUtil.put(
				"content", message.getContent()
			).put(
				"role",
				() -> {
					if (message.getSource() == Message.Source.USER) {
						return "user";
					}

					return "assistant";
				}
			)
		).forEach(
			messagesJSONArray::put
		);

		JSONObject promptJSONObject = messagesJSONArray.getJSONObject(0);

		promptJSONObject.put(
			"content",
			promptJSONObject.getString(
				"content"
			).replace(
				"$OPENAPI", HttpUtil.getOpenAPI(openAPIURL)
			));

		return _getChatCompletion(requestJSONObject);
	}

	private static String _callTools(
			JSONObject messageJSONObject, JSONObject requestJSONObject)
		throws Exception {

		JSONArray toolCallsJSONArray = messageJSONObject.getJSONArray(
			"tool_calls");

		JSONArray messagesJSONArray = requestJSONObject.getJSONArray(
			"messages"
		).put(
			messageJSONObject
		);

		for (int i = 0; i < toolCallsJSONArray.length(); i++) {
			JSONObject toolCallJSONObject = toolCallsJSONArray.getJSONObject(i);

			JSONObject argumentsJSONObject = JSONFactoryUtil.createJSONObject(
				toolCallJSONObject.getJSONObject(
					"function"
				).getString(
					"arguments"
				));

			messagesJSONArray.put(
				JSONUtil.put(
					"content",
					HttpUtil.callEndpoint(
						argumentsJSONObject.getString("method"),
						argumentsJSONObject.getString("path"),
						argumentsJSONObject.getString("payload"))
				).put(
					"role", "tool"
				).put(
					"tool_call_id", toolCallJSONObject.getString("id")
				));
		}

		return _getChatCompletion(requestJSONObject);
	}

	private static String _getChatCompletion(JSONObject requestJSONObject)
		throws Exception {

		URL url = new URL("https://api.openai.com/v1/chat/completions");

		HttpURLConnection connection = (HttpURLConnection)url.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty(
			"Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"));
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		try (OutputStream os = connection.getOutputStream()) {
			os.write(
				requestJSONObject.toString(
				).getBytes(
					"UTF-8"
				));
		}

		if (connection.getResponseCode() != 200) {
			throw new Exception(StringUtil.read(connection.getErrorStream()));
		}

		String responsePayload = StringUtil.read(connection.getInputStream());

		System.out.println(responsePayload);

		JSONObject messageJSONObject = JSONFactoryUtil.createJSONObject(
			responsePayload
		).getJSONArray(
			"choices"
		).getJSONObject(
			0
		).getJSONObject(
			"message"
		);

		if (messageJSONObject.has("tool_calls")) {
			return _callTools(messageJSONObject, requestJSONObject);
		}

		return messageJSONObject.getString("content");
	}

}