

/**
 * This program has it's origin on the internet, with no confirmd author.
 *  
 */

package pwfile;

/**
 *
 * @author Unknown
 * 
 */
 
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



import  org.apache.commons.codec.binary.Base64;

/**
 *
 * @author unknown
 */
public class Encryptor {

    /**
     * This function encrypts a string with given entrypt key and initVector.
     * 
     * @param key   The encryption key.
     * @param initVector The initVector used for encryption.
     * @param value   The String that need to be encrypted.
     * @return String The encrprted String.
     */
    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            //System.out.println("encrypted string: " + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * This function decrypts an encrypted String with given key and initVector.
     *
     * @param key  The encryption key.
     * @param initVector The initVector used in decryption.
     * @param encrypted  The encrypted String that need to be decrypted.
     * @return String The decrypted String.
     */
    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
             
            return new String(original);
        } catch (Exception ex) {
           // ex.printStackTrace();
            //System.out.println("String not encrypted.");
                   
            
        }

        return null;
    }

//    public static void main(String[] args) {
//        String key = "Bar12345Bar12345"; // 128 bit key
//        String initVector = "RandomInitVector"; // 16 bytes IV
//
//        System.out.println(decrypt(key, initVector,
//                encrypt(key, initVector, "Hello World")));
//        
//        String decerptedText = "9MU7vSBqfzPnj7iWvvfsEw==";
//        System.out.println(decrypt(key, initVector,decerptedText));
//    }
}

