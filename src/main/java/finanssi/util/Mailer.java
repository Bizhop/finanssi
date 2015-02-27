package finanssi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by ville on 27.2.2015.
 */
@Component
public class Mailer {
    private static JavaMailSender sender;

    @Autowired
    Mailer(JavaMailSender javaMailSender) {
        this.sender = javaMailSender;
    }

    public static void sendResetLink(String email, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("ville.piispa@gmail.com");
        message.setSubject("Finanssi - please setup your password");
        message.setText(String.format("http://finanssi.nuthou.se/reset.html?token=%s", resetToken));
        sender.send(message);
    }
}
