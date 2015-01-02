package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessage {
	private String message;
	private String user;

	@JsonCreator
	public ChatMessage(@JsonProperty("message") String message, @JsonProperty("user") String user) {
		this.message = message;
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public String getUser() {
		return user;
	}
}