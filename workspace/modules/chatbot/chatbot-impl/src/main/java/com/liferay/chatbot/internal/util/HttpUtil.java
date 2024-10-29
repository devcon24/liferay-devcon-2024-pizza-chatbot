package com.liferay.chatbot.internal.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Base64;

/**
 * @author Alejandro Tardín
 */
public class HttpUtil {

	public static String callEndpoint(
			String method, String path, String payload)
		throws Exception {

		HttpURLConnection connection = _getAuthenticatedConnection(path);

		connection.setDoOutput(true);
		connection.setRequestMethod(method.toUpperCase());

		if (Validator.isNotNull(payload)) {
			connection.setRequestProperty("Content-Type", "application/json");

			try (OutputStream os = connection.getOutputStream()) {
				os.write(payload.getBytes("UTF-8"));
			}
		}

		if (connection.getResponseCode() >= 300) {
			throw new Exception(StringUtil.read(connection.getErrorStream()));
		}

		return StringUtil.read(connection.getInputStream());
	}

	public static String getOpenAPI(String url) throws Exception {
		HttpURLConnection connection = _getAuthenticatedConnection(url);

		connection.setDoOutput(true);
		connection.setRequestMethod("GET");

		if (connection.getResponseCode() != 200) {
			throw new Exception(StringUtil.read(connection.getErrorStream()));
		}

		return StringUtil.read(connection.getInputStream());
	}

	private static HttpURLConnection _getAuthenticatedConnection(
			String urlString)
		throws Exception {

		URL url = new URL(urlString);

		HttpURLConnection connection = (HttpURLConnection)url.openConnection();

		String credentials =
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD;

		Base64.Encoder encoder = Base64.getEncoder();

		connection.setRequestProperty(
			"Authorization",
			"Basic " + encoder.encodeToString(credentials.getBytes()));

		return connection;
	}

}