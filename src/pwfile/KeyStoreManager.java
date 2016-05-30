///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package pwfile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
//import java.security.KeyStore;
//import java.security.KeyStore.PasswordProtection;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.UnrecoverableEntryException;
//import java.security.cert.CertificateException;
//import java.security.spec.InvalidKeySpecException;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.Scanner;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.crypto.SecretKey;
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.PBEKeySpec;
//
///**
// *
// * @author WangL
// */
//public class KeyStoreManager {
//
//    String keyStorePath;
//    String keyStorePass;
//    KeyStore ks;
//    PasswordProtection keyStorePP;
//    SecretKeyFactory factory;
//    ArrayList<String> aliasList;
//    final String encrypKey = "Bar12345Bar12345"; // 128 bit key
//    final String initVector = "RandomInitVector"; // 16 bytes IV
//    
//
//    public KeyStoreManager(String confFilePath) throws FileNotFoundException, KeyStoreException, NoSuchAlgorithmException {
//           
//        String[] conf = checkConfFile(confFilePath);
//        
//        this.keyStorePath = conf[0];
//        //this.keyStorePass = conf[1];
//        this.keyStorePass = getStorePW(confFilePath,conf[1]);
//        //System.out.println(this.keyStorePass);
//                
//        this.ks = KeyStore.getInstance("JCEKS");
//        this.factory = SecretKeyFactory.getInstance("PBE");
//        this.keyStorePP = new KeyStore.PasswordProtection(this.keyStorePass.toCharArray());
//        aliasList = new ArrayList<String>();
//    }       
 
    

//    public String[] checkConfFile(String filePath) throws FileNotFoundException {
//
//        String[] result = new String[2];
//        File confFile = new File(filePath);
//
//        Scanner scan = new Scanner(confFile);
//        String line;
//       
//
//        while (scan.hasNextLine()) {
//            line = scan.nextLine();
//            String[] out1 = line.split("=");
//
//            if (out1[0].equals("keyStorePath")) {
//
//                result[0] = out1[1];
//            }
//            if (out1[0].equals("keyStorePass")) {
//
//                result[1] = out1[1];
//            }
//        }
//        if (result[0] == null) {
//            System.err.println("keyStorePath value not found");
//        }
//        if (result[1] == null) {
//            System.err.println("Keystore pass value not found");
//        }
//        //System.out.println(this.getStorePW(filePath, result[1]) + "encry");
//        
//        return result;
//
//    }
//
//    public void createKeyStore() {
//
//        try {
//            Path path = Paths.get(this.keyStorePath);
//            if (Files.exists(path)) {
//                System.err.println("File " + this.keyStorePath + " already exist.");
//                System.exit(1);
//            }
//        } catch (Exception e) {
//
//        }
//        //create keystore
//        try {
//
//            char[] keyStorePassword = this.keyStorePass.toCharArray();
//
//            this.ks.load(null, keyStorePassword);
//
//            // Store away the keystore.
//            FileOutputStream fos = new FileOutputStream(this.keyStorePath);
//            this.ks.store(fos, keyStorePassword);
//            fos.close();
//        } catch (Exception e) {
//
//        }
//
//    }
//
//    public void loadKeyStore() throws FileNotFoundException, IOException, KeyStoreException, UnrecoverableEntryException {
//        try {
//            this.ks.load(new FileInputStream(this.keyStorePath), this.keyStorePass.toCharArray());
//            //System.out.println(this.ks.aliases().nextElement().toString()+"new");
//            Enumeration<String> aliases = this.ks.aliases();
//            //System.out.println(aliases.nextElement().toString());
//            while (aliases.hasMoreElements()) {
//                //System.out.println(aliases.nextElement().toString() +"xx");
//                aliasList.add(aliases.nextElement().toString());
//            }
//            //System.out.println("testload");
//            ;
//        } catch (NoSuchAlgorithmException | CertificateException ex) {
//            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public String getKey(String keyName) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
//
//        if (!keyExist(keyName)) {
//            System.out.println("Error: keyname " + keyName + " not found in keystore.");
//            return "";
//        }
//
//        //read key
//        KeyStore.SecretKeyEntry ske
//                = (KeyStore.SecretKeyEntry) ks.getEntry(keyName, keyStorePP);
//
//        PBEKeySpec keySpec = null;
//        if (ske != null) {
//            try {
//                keySpec = (PBEKeySpec) factory.getKeySpec(
//                        ske.getSecretKey(),
//                        PBEKeySpec.class);
//            } catch (InvalidKeySpecException ex) {
//                Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        return new String(keySpec.getPassword());
//
//    }
//
//    public void addNewKey(String keyName, String keyValue) throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, IOException, CertificateException {
//
//        if (keyExist(keyName)) {
//            System.out.println("Key " + keyName + " already exist.");
//            return;
//        }
//
//        char[] keyPassword = keyValue.toCharArray();
//
//        SecretKey generatedSecret = factory.generateSecret(new PBEKeySpec(keyPassword));
//
//        try {
//            this.ks.setEntry(keyName, new KeyStore.SecretKeyEntry(generatedSecret), this.keyStorePP);
//            char[] keyStorePassword = this.keyStorePass.toCharArray();
//            FileOutputStream fos = new FileOutputStream(this.keyStorePath);
//            this.ks.store(fos, keyStorePassword);
//            fos.close();
//            //System.out.println(keyName + " " + generatedSecret.toString());
//            System.out.println("Key " + keyName + " added.");
//
//        } catch (KeyStoreException ex) {
//            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public void updateKey(String keyName, String keyValue) throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, IOException, CertificateException {
//
//        if (!keyExist(keyName)) {
//            System.out.println("Key " + keyName + " does not exist.");
//            return;
//        }
//
//        char[] keyPassword = keyValue.toCharArray();
//
//        SecretKey generatedSecret = factory.generateSecret(new PBEKeySpec(keyPassword));
//
//        try {
//            this.ks.setEntry(keyName, new KeyStore.SecretKeyEntry(generatedSecret), this.keyStorePP);
//            char[] keyStorePassword = this.keyStorePass.toCharArray();
//            FileOutputStream fos = new FileOutputStream(this.keyStorePath);
//            this.ks.store(fos, keyStorePassword);
//            fos.close();
//            //System.out.println(keyName + " " + generatedSecret.toString());
//
//        } catch (KeyStoreException ex) {
//            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public void deleteKey(String keyName) throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, IOException, CertificateException {
//
//        if (! keyExist(keyName)) {
//            System.out.println("Key " + keyName + " does not exist.");
//            return;
//        }
//
//        try {
//
//            this.ks.deleteEntry(keyName);
//            char[] keyStorePassword = this.keyStorePass.toCharArray();
//            FileOutputStream fos = new FileOutputStream(this.keyStorePath);
//            this.ks.store(fos, keyStorePassword);
//            fos.close();
//            System.out.println("Key " + keyName + "Deleted.");
//                    
//
//        } catch (KeyStoreException ex) {
//            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public boolean keyExist(String keyName) {
//
//        boolean vFound = false;
//
//        for (int i = 0; i < aliasList.size(); i++) {
//            System.out.println(aliasList.get(i).toString() + "xxx");
//            if (aliasList.get(i).equalsIgnoreCase(keyName)) {
//                // System.out.println(aliasList.get(i).toString()+"xxx");
//
//                vFound = true;
//            }
//        }
//
//        return vFound;
//
//    }
//    
//    public String getStorePW(String confFilePath, String storePassword) {
//        //this function accept conffile location and storepassword
//        //then check if the password is encrypted
//        // if no, then encrypted it, save the encrepted password in the conffile, return the not encrypted password
//        // if already encrypted, then decrept it and return  it 
//         
//        String passwordPlain  = Encryptor.decrypt(encrypKey, initVector, storePassword);
//        
//        if (passwordPlain.length() == 0 ) {
//            //password not encrypted
//            System.out.println("hier");
//            String encryptedPassword = Encryptor.encrypt(encrypKey, initVector, storePassword);
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
//                    Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return storePassword;
//        } else {
//            //passwored already encrypted, return decrepted one
//            
//            return passwordPlain;
//            
//        }
//    }
//
//}
