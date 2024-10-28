package com.liferay.chatbot.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
@GraphQLName("ChatbotRequest")
@JsonFilter("Liferay.Vulcan")
@Schema(requiredProperties = {"messages", "openAPIURL"})
@XmlRootElement(name = "ChatbotRequest")
public class ChatbotRequest implements Serializable {

	public static ChatbotRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(ChatbotRequest.class, json);
	}

	public static ChatbotRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(ChatbotRequest.class, json);
	}

	@Schema(description = "The conversation history")
	@Valid
	public Message[] getMessages() {
		if (_messagesSupplier != null) {
			messages = _messagesSupplier.get();

			_messagesSupplier = null;
		}

		return messages;
	}

	public void setMessages(Message[] messages) {
		this.messages = messages;

		_messagesSupplier = null;
	}

	@JsonIgnore
	public void setMessages(
		UnsafeSupplier<Message[], Exception> messagesUnsafeSupplier) {

		_messagesSupplier = () -> {
			try {
				return messagesUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The conversation history")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected Message[] messages;

	@JsonIgnore
	private Supplier<Message[]> _messagesSupplier;

	@Schema(description = "The OpenAPI URL for Chatbot tools.")
	public String getOpenAPIURL() {
		if (_openAPIURLSupplier != null) {
			openAPIURL = _openAPIURLSupplier.get();

			_openAPIURLSupplier = null;
		}

		return openAPIURL;
	}

	public void setOpenAPIURL(String openAPIURL) {
		this.openAPIURL = openAPIURL;

		_openAPIURLSupplier = null;
	}

	@JsonIgnore
	public void setOpenAPIURL(
		UnsafeSupplier<String, Exception> openAPIURLUnsafeSupplier) {

		_openAPIURLSupplier = () -> {
			try {
				return openAPIURLUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The OpenAPI URL for Chatbot tools.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String openAPIURL;

	@JsonIgnore
	private Supplier<String> _openAPIURLSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ChatbotRequest)) {
			return false;
		}

		ChatbotRequest chatbotRequest = (ChatbotRequest)object;

		return Objects.equals(toString(), chatbotRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Message[] messages = getMessages();

		if (messages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messages\": ");

			sb.append("[");

			for (int i = 0; i < messages.length; i++) {
				sb.append(String.valueOf(messages[i]));

				if ((i + 1) < messages.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		String openAPIURL = getOpenAPIURL();

		if (openAPIURL != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"openAPIURL\": ");

			sb.append("\"");

			sb.append(_escape(openAPIURL));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.chatbot.dto.v1_0.ChatbotRequest",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}