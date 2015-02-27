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
	private static MailConfiguration instance = null;

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

	@Bean
    public JavaMailSender getMailService() {
        JavaMailSenderImpl m = new JavaMailSenderImpl();
        Properties p = new Properties();
        p.put("mail.smtp.auth", auth);
        p.put("mail.smtp.starttls.enable", starttls);
        p.put("mail.debug", debug);
        m.setJavaMailProperties(p);
        m.setHost(host);
        m.setPort(port);
        m.setProtocol(protocol);
        m.setUsername(user);
        m.setPassword(password);
        return m;
    }
}