package finanssi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Created by ville on 27.2.2015.
 */
public class Mailer {
    @Autowired
    private JavaMailSender sender;

    public static void sendResetLink(String email, String resetToken, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("ville.piispa@gmail.com");
        message.setSubject("Finanssi - please setup your password");
        message.setText(String.format("http://finanssi.nuthou.se/reset.html?token=%s", resetToken));
    }
}
