package finanssi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ChatMessage {
	private String message;
	private String user;
    private Long timestamp;

	@JsonCreator
	public ChatMessage(@JsonProperty("message") String message, @JsonProperty("user") String user) {
		this.message = message;
		this.user = user;
        this.timestamp = new Date().getTime();
	}

	public String getMessage() {
		return message;
	}

	public String getUser() {
		return user;
	}

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[user: %s, message: %s, timestamp: %s]", user, message, timestamp.toString());
    }
}