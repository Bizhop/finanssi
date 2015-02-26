package finanssi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by ville on 26.2.2015.
 */
public class Hasher {
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            return String.valueOf(Base64.getEncoder().encode(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            //fuck off, this doesn't happen
            e.printStackTrace();
            return null;
        }
    }
}
