package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ville on 17.1.2015.
 */
public class User {
    private String id;

    private String userName;
    private String password;

    private User() {}

    @JsonCreator
    public User(@JsonProperty("userName") String userName, @JsonProperty("password") String password) {
        this.userName = userName;
        this.password = password; //TODO: hash this shit
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, userName='%s']",
                id, userName);
    }
}
