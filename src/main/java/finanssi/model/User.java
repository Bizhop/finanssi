package finanssi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ville on 17.1.2015.
 */
public class User {

	public enum Status {
		ACTIVE, INACTIVE
	}

	private String id;

	private String userName;
	private String password;
	private String email;
	private Status status;

	private User() {}

	@JsonCreator
	public User(@JsonProperty("userName") String userName, @JsonProperty("password") String password, @JsonProperty("password") String email) {
		this.userName = userName;
		this.password = password; //TODO: hash this shit
		this.email = email;
		this.status = Status.INACTIVE;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format(
				"User[id=%s, userName='%s', email='%s']",
				this.id, this.userName, this.email);
	}
}
