/*
 * All Rights reserved
 * Copyright(c) 2016 L.Wang Consultancy  All Rights Reserved.
 * This software is the proprietary information of L.Wang Consultancy.
 * The keystore creation is based on following blog:
 * http://kingsfleet.blogspot.nl/2008/12/storing-password-somewhere-safe.html
 */
package pwfile;

/**
 * This program provides functions to create a java keystore for saving user
 * name/password pairs.
 * 
 *
 * @author WangL
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.crypto.IllegalBlockSizeException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author WangL
 * @version 1.0
 * @since 01-June-2016
 * 
 */
public class PwFile {

    /**
     * This is the main program, it accepts command line arguments and parse
     * them with Args class, then call the appropriate function based on the
     * input argument.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Args vArg = new Args(args);
        vArg.parse();

        if (vArg.conf == null) {
            System.err.println("Conf file not found, exiting.");
            System.exit(1);
        }

        switch (vArg.argName) {
            case "create_conffile": {
                try {
                String keystorePath = vArg.argValue.split(",")[0];
                String keystorePass = vArg.argValue.split(",")[1];
                createConfFile(vArg.conf, keystorePath, keystorePass);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("keystorePath or password not given.");
                    vArg.help();
                    System.exit(1);
                }
                

            }
            break;
            case "create_keystore": {
                try {
                    createKeyStore(vArg.conf);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            break;
            case "add_key": {
                String name = vArg.argValue.split("=")[0];
                String value = vArg.argValue.split("=")[1];
                addNewKey(name, value, vArg.conf);
            }
            break;
            case "update_key": {
                String name = vArg.argValue.split("=")[0];
                String value = vArg.argValue.split("=")[1];
                updateKey(name, value, vArg.conf);
            }
            break;
            case "delete_key": {

                deleteKey(vArg.argValue, vArg.conf);
            }
            break;
            case "import_keys": {

                importKeys(vArg.conf, vArg.argValue);
            }
            break;
            case "export_keys": {

                exportAllKeys(vArg.conf, vArg.argValue);
            }
            break;
            case "get_pw": {

                String pw = getKeyPW(vArg.argValue, vArg.conf);
                if ( pw != null) {
                    System.out.println(pw);
                }
            }
            break;
        }

    }

    /**
     * This function create the configuration file, it contains the path
     * and password of the keystore.
     *
     * @param confFilePath
     * @param keyStorePath
     * @param keyStorePass
     * @return boolean Shows if the action is succeeded.
     */
    public static boolean createConfFile(String confFilePath, String keyStorePath, String keyStorePass) {

        Path path = Paths.get(confFilePath);
        if (Files.exists(path)) {
            System.err.println("File " + confFilePath + " already exist.");
            return false;
        }

        File confFile = new File(confFilePath);

        try {
            PrintWriter writer = new PrintWriter(confFilePath);
            writer.println("keyStorePath=" + keyStorePath);
            writer.println("keyStorePass=" + keyStorePass);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String storePw = getStorePW(confFilePath);
        return true;
    }

    /**
     * This function read the config file and return the keystore path and
     * keystore password in a String array.
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public static String[] getConfig(String filePath) throws FileNotFoundException {

        String[] result = new String[2];
        File confFile = new File(filePath);

        Scanner scan = new Scanner(confFile);
        String line;

        while (scan.hasNextLine()) {
            line = scan.nextLine();
            String[] out1 = line.split("=");

            if (out1[0].equals("keyStorePath")) {

                result[0] = out1[1];
            }
            if (out1[0].equals("keyStorePass")) {

                result[1] = out1[1];
            }
        }
        if (result[0] == null) {
            System.err.println("keyStorePath value not found");
        }
        if (result[1] == null) {
            System.err.println("Keystore pass value not found");
        }

        return result;

    }

    /**
     * This function creates a keystore.
     *
     * @param confFilePath
     * @throws FileNotFoundException
     */
    public static void createKeyStore(String confFilePath) throws FileNotFoundException {
        //String[] config = getConfig(confFilePath);
        String keyStorePath = getStorePath(confFilePath);
        String keyStorePass = getStorePW(confFilePath);

        try {

            Path path = Paths.get(keyStorePath);
            if (Files.exists(path)) {
                System.err.println("File " + keyStorePath + " already exist.");
                System.exit(1);
            }
        } catch (Exception e) {

        }
        //create keystore
        try {

            char[] keyStorePassword = keyStorePass.toCharArray();

            //create keystore
            KeyStore ks = KeyStore.getInstance("JCEKS");
            ks.load(null, keyStorePassword);
            KeyStore.PasswordProtection keyStorePP = new KeyStore.PasswordProtection(keyStorePassword);
            //add dummpy entry to keystore, otherwise get EOF exception next time when loading keystore
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");
            SecretKey generatedSecret = factory.generateSecret(new PBEKeySpec("dummy".toCharArray()));
            ks.setEntry("dummy", new KeyStore.SecretKeyEntry(generatedSecret), keyStorePP);

            // Store away the keystore.
            FileOutputStream fos = new FileOutputStream(keyStorePath);
            ks.store(fos, keyStorePassword);
            fos.close();
        } catch (Exception e) {

        }

    }

    /**
     * This function calls getConfig function and returns keystore path.
     *
     * @param confFilePath
     * @return
     */
    public static String getStorePath(String confFilePath) {

        String[] config = null;

        try {
            config = getConfig(confFilePath);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return config[0];

    }

    /**
     * This function accepts conf file path,then check if the password in 
     * conf file is encrypted. If no, then encrypted it, save the encrypted 
     * password in the conf ile, return the not encrypted password,
     * if already encrypted, then decrypt it and return  it. 
     *
     * @param confFilePath
     * @return String keystore password value
     */
    public static String getStorePW(String confFilePath) {
 
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV

        String[] config = null;
        String returnPw = null;
        String passwordPlain = null;
        String encryptedPassword = null;
        try {
            config = getConfig(confFilePath);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        String keyStorePath = config[0];
        String keyStorePass = config[1];

        try {

            passwordPlain = Encryptor.decrypt(key, initVector, keyStorePass);

        } catch (Exception e) {

        }
        if (passwordPlain == null) {
            //password not encrypted

            try {

                encryptedPassword = Encryptor.encrypt(key, initVector, keyStorePass);
            } catch (Exception ex) {
                Logger.getLogger(PwFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateConfFilePWString(confFilePath, encryptedPassword);

//            File confFile = new File(confFilePath);
//            try {
//                Scanner scan = new Scanner(confFile);
//                PrintWriter out = new PrintWriter("tempEncrypFile");
//
//                String line;
//                while (scan.hasNextLine()) {
//                    line = scan.nextLine();
//                    String[] out1 = line.split("=");
//                    if (out1[0].equals("keyStorePass")) {
//                        out1[1] = encryptedPassword;
//                        line = out1[0] + "=" + out1[1];
//                    }
//                    out.println(line);
//                }
//                out.close();
//                File tempFile = new File("tempEncrypFile");
//
//                try {
//                    Files.copy(tempFile.toPath(), confFile.toPath(), REPLACE_EXISTING);
//                } catch (IOException ex) {
//                    System.out.println("wrong1");
//                }
//
//            } catch (FileNotFoundException ex) {
//                System.out.println("wrong2");
//            }
            returnPw = keyStorePass;
        } else {
            //passwored already encrypted, return decrepted one

            returnPw = passwordPlain;

        }
        return returnPw;
    }

    /** 
     * This function load the keystore to make furthre keystore action possible.
     *
     * @param confFilePath
     * @return
     */
    public static KeyStore loadKeyStore(String confFilePath) {
        KeyStore ks = null;
        KeyStore.SecretKeyEntry ske = null;
        PasswordProtection keyStorePP = null;
        SecretKeyFactory factory = null;
//                String[] config = null;
//        try {
//            config = getConfig(confFilePath);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
        String keyStorePath = getStorePath(confFilePath);
        String keyStorePass = getStorePW(confFilePath);

        try {
            ks = KeyStore.getInstance("JCEKS");
            factory = SecretKeyFactory.getInstance("PBE");
            keyStorePP = new KeyStore.PasswordProtection(keyStorePass.toCharArray());
            ks.load(new FileInputStream(keyStorePath), keyStorePass.toCharArray());
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | NullPointerException ex) {
            ex.printStackTrace();
            System.out.println("Error loading keystore");
            return null;
        }
        return ks;
    }

    /**
     * This function read the keystore and returns the full list of key/password 
     * pairs in an ArrayList.
     *
     * @param confFilePath
     * @return
     */
    public static ArrayList<String> getFullList(String confFilePath) {
        //This function load the keystore entries into an arraylist
        //is also the check if keystore/keystorepass combination is correct.
        KeyStore ks = loadKeyStore(confFilePath);
        ArrayList<String> aliasList = new ArrayList<>();

        KeyStore.SecretKeyEntry ske = null;
        PasswordProtection keyStorePP = null;
        SecretKeyFactory factory = null;

//        this.factory = SecretKeyFactory.getInstance("PBE");
//        this.keyStorePP = new KeyStore.PasswordProtection(this.keyStorePass.toCharArray());
//        String[] config = null;
//        try {
//            config = getConfig(confFilePath);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
        String keyStorePath = getStorePath(confFilePath);
        String keyStorePass = getStorePW(confFilePath);

        //load keystore and read list of users
        try {
            //ks = KeyStore.getInstance("JCEKS");
            //factory = SecretKeyFactory.getInstance("PBE");
            //keyStorePP = new KeyStore.PasswordProtection(keyStorePass.toCharArray());
            //ks.load(new FileInputStream(keyStorePath), keyStorePass.toCharArray());
            //System.out.println(this.ks.aliases().nextElement().toString()+"new");

            Enumeration<String> aliases = ks.aliases();

            //System.out.println(aliases.nextElement().toString());
            while (aliases.hasMoreElements()) {
                //System.out.println(aliases.nextElement().toString() +"xx");
                aliasList.add(aliases.nextElement());

            }
        } catch (KeyStoreException | NullPointerException ex) {
            System.out.println("Keystore is empty");
            return null;
        }

        for (int i = 0; i < aliasList.size(); i++) {

            try {
                factory = SecretKeyFactory.getInstance("PBE");
                keyStorePP = new KeyStore.PasswordProtection(keyStorePass.toCharArray());

                ske = (KeyStore.SecretKeyEntry) ks.getEntry(aliasList.get(i), keyStorePP);
                PBEKeySpec keySpec = null;

                if (ske != null) {
                    try {
                        keySpec = (PBEKeySpec) factory.getKeySpec(
                                ske.getSecretKey(),
                                PBEKeySpec.class);
                        String temp = aliasList.get(i) + "=" + new String(keySpec.getPassword());
                        aliasList.set(i, temp);
                    } catch (InvalidKeySpecException ex) {
                        ex.printStackTrace();
                    }
                }

            } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException ex) {
                ex.printStackTrace();
            }

        }

        return aliasList;

    }

    /**
     * This function adds a new key/password.
     *
     * @param keyName
     * @param keyValue
     * @param confFilePath
     */
    public static void addNewKey(String keyName, String keyValue, String confFilePath) {
        setKey(keyName, keyValue, confFilePath, "add");
    }

    /**
     * This function calls setKey function to changes the password 
     * of a given key.
     *
     * @param keyName
     * @param keyValue
     * @param confFilePath
     */
    public static void updateKey(String keyName, String keyValue, String confFilePath) {
        setKey(keyName, keyValue, confFilePath, "update");
    }
    
    /**
     * This function is used to add and update the password of a given key.
     * 
     * @param keyName
     * @param keyValue
     * @param confFilePath
     * @param action 
     */

    private static void setKey(String keyName, String keyValue, String confFilePath, String action) {

        KeyStore ks = loadKeyStore(confFilePath);
        if (!(action.equalsIgnoreCase("add") || action.equalsIgnoreCase("update"))) {
            System.out.println("Wrong action, only valid values are : add, update");
            return;
        }

        try {
            if (ks.containsAlias(keyName) && action.equalsIgnoreCase("add")) {
                System.out.println("Can't add Key " + keyName + ", it already exist.");
                return;
            }
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
        }

        try {
            if ((!ks.containsAlias(keyName)) && action.equalsIgnoreCase("update")) {
                System.out.println("Can't update Key " + keyName + ", it does not exist.");
                return;
            }
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
        }

//        String[] config = null;
//        try {
//            config = getConfig(confFilePath);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
        String keyStorePath = getStorePath(confFilePath);
        String keyStorePass = getStorePW(confFilePath);

        char[] keyPassword = keyValue.toCharArray();
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBE");

            SecretKey generatedSecret = factory.generateSecret(new PBEKeySpec(keyPassword));

            //ks = KeyStore.getInstance("JCEKS");
            //ks.load(new FileInputStream(keyStorePath), keyStorePass.toCharArray());
            PasswordProtection keyStorePP = new KeyStore.PasswordProtection(keyStorePass.toCharArray());

            ks.setEntry(keyName, new KeyStore.SecretKeyEntry(generatedSecret), keyStorePP);
            char[] keyStorePassword = keyStorePass.toCharArray();
            FileOutputStream fos = new FileOutputStream(keyStorePath);
            ks.store(fos, keyStorePassword);
            //ks.store(fos, "wang".toCharArray());
            fos.close();
            System.out.println(action + " key " + keyName + " done.");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | KeyStoreException | IOException | CertificateException ex) {
            ex.printStackTrace();
        }

    }

    /** 
     * This function returns the password of a given key.
     *
     * @param keyName
     * @param confFilePath
     * @return
     */
    public static String getKeyPW(String keyName, String confFilePath) {
        ArrayList<String> keyPairList = getFullList(confFilePath);
        String vPW = null;
        String[] out = new String[2];
        for (String temp : keyPairList) {
            out = temp.split("=");
            if (out[0].toString().equals(keyName)) {
                vPW = out[1].toString();
            }
        }

        if (vPW == null) {
            System.err.println("Key " + keyName + " not found.");
        }
        return vPW;
    }

    /**
     * This function deletes a key/password entry based on given keyname.
     *
     * @param keyName
     * @param confFilePath
     */
    public static void deleteKey(String keyName, String confFilePath) {

//        if (!keyExist(keyName, confFilePath)) {
//            System.out.println("Key " + keyName + " does not exist.");
//            return;
//        }
        KeyStore ks = loadKeyStore(confFilePath);

        try {
            if (!ks.containsAlias(keyName)) {
                System.out.println("Key " + keyName + " does not exist.");
                return;
            }
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
        }

//                String[] config = null;
//        try {
//            config = getConfig(confFilePath);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
        String keyStorePath = getStorePath(confFilePath);
        String keyStorePass = getStorePW(confFilePath);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");

            SecretKey generatedSecret = factory.generateSecret(new PBEKeySpec(keyStorePass.toCharArray()));

            //ks.load(new FileInputStream(keyStorePath), keyStorePass.toCharArray());
            PasswordProtection keyStorePP = new KeyStore.PasswordProtection(keyStorePass.toCharArray());
            ks.deleteEntry(keyName);
            char[] keyStorePassword = keyStorePass.toCharArray();
            FileOutputStream fos = new FileOutputStream(keyStorePath);
            ks.store(fos, keyStorePassword);
            fos.close();
            System.out.println("Key " + keyName + " deleted.");

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | KeyStoreException | IOException | CertificateException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This function export the whole list of key/password entries to a file.
     *
     * @param confFilePath
     * @param exportFilePath
     * @return
     */
    public static boolean exportAllKeys(String confFilePath, String exportFilePath) {

        Path path = Paths.get(exportFilePath);
        if (Files.exists(path)) {
            System.err.println("File " + exportFilePath + " already exist.");
            return false;
        }
        
        File exportFile = new File(exportFilePath);
        ArrayList<String> fullList = getFullList(confFilePath);
        try {

            PrintWriter out = new PrintWriter(exportFilePath);

            for (String temp : fullList) {
                String[] out1 = temp.split("=");
                if (!out1[0].equalsIgnoreCase("dummy")) {
                    out.println(temp);
                }
            }

            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
         return true;
    }

    /**
     * This function imports the key/password entries from a file into an 
     * existing keystore.
     *
     * @param confFilePath
     * @param importFilePath
     */
    public static void importKeys(String confFilePath, String importFilePath) {

        File file = new File(importFilePath);
        Scanner scan;
        try {
            scan = new Scanner(file);
            String[] out1;
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                out1 = line.split("=");

                addNewKey(out1[0], out1[1], confFilePath);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    //after change keystore password, keystore.getEntry stop working with this error:
    //java.security.UnrecoverableKeyException: Given final block not properly padded
//    public static void changeKeyStorePW(String confFilePath, String newPW) {
//        String keyStorePath = getStorePath(confFilePath);
//        String keyStorePass = getStorePW(confFilePath);
//        KeyStore ks = loadKeyStore(confFilePath);
//        char[] keyStorePassword = keyStorePass.toCharArray();
//        FileOutputStream fos;
//        try {
//            fos = new FileOutputStream(keyStorePath);
//            KeyStore.PasswordProtection keyStorePP = new KeyStore.PasswordProtection(keyStorePassword);
//            //add dummpy entry to keystore, otherwise get EOF exception next time when loading keystore
////            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");
////            SecretKey generatedSecret = factory.generateSecret(new PBEKeySpec(newPW.toCharArray()));
////            ks.setEntry("dummy", new KeyStore.SecretKeyEntry(generatedSecret), keyStorePP);
//            ks.store(fos, newPW.toCharArray());            
//            fos.close();
//            
//            System.out.println("Keystore updated.");
//        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | NullPointerException ex) {
//            ex.printStackTrace();
//        }
//        
//        updateConfFilePWString(confFilePath,newPW);
//    }

    /**
     * This function updates the keystore password in the conf file.
     *
     * @param confFilePath
     * @param newPWString
     */
    public static void updateConfFilePWString(String confFilePath, String newPWString) {
        File confFile = new File(confFilePath);
        try {
            Scanner scan = new Scanner(confFile);
            PrintWriter out = new PrintWriter("tempEncrypFile");

            String line;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                String[] out1 = line.split("=");
                if (out1[0].equals("keyStorePass")) {
                    out1[1] = newPWString;
                    line = out1[0] + "=" + out1[1];

                }
                out.println(line);
            }
            out.close();
            File tempFile = new File("tempEncrypFile");

            Files.copy(tempFile.toPath(), confFile.toPath(), REPLACE_EXISTING);
            Files.delete(tempFile.toPath());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
