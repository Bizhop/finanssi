package finanssi.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by ville on 27.2.2015.
 */
@Configuration
public class MailConfiguration {
    private static MailConfiguration instance;

	@Value("${smtp.host}")
	private String host;
	@Value("${smtp.user}")
	private String user;
	@Value("${smtp.password}")
	private String password;
    @Value("${smtp.port}")
    private int port;
    @Value("${smtp.auth}")
    private boolean auth;
    @Value("${smtp.starttls}")
    private boolean starttls;
    @Value("${smtp.protocol}")
    private String protocol;
    @Value("${smtp.debug}")
    private boolean debug;

    public static MailConfiguration getInstance() {
        if(instance == null) {
            instance = new MailConfiguration();
        }
        return instance;
    }

	@Bean
    public JavaMailSender getMailService() {
        JavaMailSenderImpl m = new JavaMailSenderImpl();
        Properties p = new Properties();
        p.put("mail.smtp.auth", isAuth());
        p.put("mail.smtp.starttls.enable", isStarttls());
        p.put("mail.debug", isDebug());
        m.setJavaMailProperties(p);
        m.setHost(getHost());
        m.setPort(getPort());
        m.setProtocol(getProtocol());
        m.setUsername(getUser());
        m.setPassword(getPassword());
        return m;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isStarttls() {
        return starttls;
    }

    public void setStarttls(boolean starttls) {
        this.starttls = starttls;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
