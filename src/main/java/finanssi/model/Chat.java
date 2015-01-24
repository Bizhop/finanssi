package finanssi.model;

import java.util.List;

public class Chat {
	private List<ChatMessage> messages;
	
	public Chat(List<ChatMessage> messages) {
		this.messages = messages;
	}

	public List<ChatMessage> getMessages() {
		return messages;
	}
}