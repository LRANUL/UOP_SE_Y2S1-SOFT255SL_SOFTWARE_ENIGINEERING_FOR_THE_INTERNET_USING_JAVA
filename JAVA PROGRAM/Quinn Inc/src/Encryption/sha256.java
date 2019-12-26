
package Encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author IshanC7
 */
public class sha256 {
    public static String hash(String base) {
    try{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] array = digest.digest(base.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            String hexvalue = Integer.toHexString(0xff & array[i]);
            if(hexvalue.length() == 1) hexString.append('0');
            hexString.append(hexvalue);
        }

        return hexString.toString();
    } catch(UnsupportedEncodingException | NoSuchAlgorithmException ex){
       throw new RuntimeException(ex);
    }
}
}
