package finanssi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class ChatMessage {
	private String message;
	private String user;
    private Instant timestamp;

	@JsonCreator
	public ChatMessage(@JsonProperty("message") String message, @JsonProperty("user") String user) {
		this.message = message;
		this.user = user;
        this.timestamp = Instant.now();
	}

	public String getMessage() {
		return message;
	}

	public String getUser() {
		return user;
	}

    public Instant getTimestamp() {
        return timestamp;
    }
}