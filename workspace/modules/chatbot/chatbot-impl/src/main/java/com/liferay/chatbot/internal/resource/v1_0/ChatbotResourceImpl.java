package com.liferay.chatbot.internal.resource.v1_0;

import com.liferay.chatbot.dto.v1_0.ChatbotRequest;
import com.liferay.chatbot.dto.v1_0.ChatbotResponse;
import com.liferay.chatbot.internal.openai.OpenAI;
import com.liferay.chatbot.resource.v1_0.ChatbotResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/chatbot.properties",
	scope = ServiceScope.PROTOTYPE, service = ChatbotResource.class
)
public class ChatbotResourceImpl extends BaseChatbotResourceImpl {

	@Override
	public ChatbotResponse chat(ChatbotRequest chatbotRequest)
		throws Exception {

		return new ChatbotResponse() {
			{
				try {
					setContent(
						OpenAI.getChatCompletion(
							chatbotRequest.getMessages(),
							chatbotRequest.getOpenAPIURL()));
				}
				catch (Exception exception) {
					setContent("There was an error: " + exception);
				}
			}
		};
	}

}