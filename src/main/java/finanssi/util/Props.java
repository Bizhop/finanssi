package finanssi.util;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Created by ville on 27.2.2015.
 */
@Component
public class Props {
	private static Props instance = null;

	@Value("${smtp.server}")
	private String smtpServer;
	@Value("${smtp.user}")
	private String smtpUser;
	@Value("${smtp.password}")
	private String smtpPassword;

	private Props() {}

	public static Props getInstance() {
		if(instance == null) {
			instance = new Props();
		}
		return instance;
	}

	public String getSmtpServer() {
		return this.smtpServer;
	}
	public String setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}
	public String getSmtpUser() {
                return this.smtpUser;
        }
        public String setSmtpUser(String smtpUser) {
                this.smtpUser = smtpUser;
        }
	public String getSmtpPassword() {
                return this.smtpPassword;
        }
        public String setSmtpPassword(String smtpPassword) {
                this.smtpPassword = smtpPassword;
        }
}
