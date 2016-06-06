/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwfile;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author WangL
 */
public class PwFileTest {

    public PwFileTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class PwFile.
     */
//    @Test
//    public void testMain() throws Exception {
//        System.out.println("main");
//        String[] args = "-conf /tmp/test1 -create_conffile /tmp/testkeystore1,ludi".split(" ");
//        PwFile.main(args);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
    
    /**
     * Test of getConfig method, of class PwFile.
     */
//    @Test
//    public void testGetConfig() throws Exception {
//        System.out.println("getConfig");
//        String filePath = "/tmp/test1";
//        String[] expResult = null;
//        String[] result = PwFile.getConfig(filePath);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
    /**
     * Test of CreateKeyStore method, of class PwFile.
     */
//    @Test
//    public void testCreateKeyStore() throws Exception {
//        System.out.println("createKeyStore");
//        String confFilePath = "/tmp/test1";
//        PwFile.createKeyStore(confFilePath);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
    
    
//    /**
//     * Test of getStorePW method, of class PwFile.
//     */
//    @Test
//    public void testGetStorePW() {
//        System.out.println("getStorePW");
//        String confFilePath = "/tmp/test1";
//        String expResult = "ludi";
//        String result = PwFile.getStorePW(confFilePath);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
    
    /**
     * Test of getFullList method, of class PwFile.
     */
//    @Test
//    public void TestGetFullList() {
//        System.out.println("getFullList");
//        String confFilePath = "/tmp/test1";
//        String expResult = "ludi";
//        ArrayList<String> result = null;
//        result = PwFile.getFullList(confFilePath);
//        if (result != null) {
//            for (String temp : result) {
//                System.out.println(temp);
//
//            }
//        } else {
//            System.out.println("no key found.");
//        }
//
//    }

    /**
     * Test of addNewKey method, of class PwFile.
     */
//    @Test
//    public void TestAddNewKey() {
//        System.out.println("loadStore");
//        String confFilePath = "/tmp/test1";  
//        String keyName="ludi2";
//        String keyValue="wang2";
//        String expResult = "ludi";
//         
//        PwFile.addNewKey(keyName, keyValue,confFilePath);
//        //PwFile.updateKey(keyName, keyValue,confFilePath);
//    }
    
     /**
     * Test of updateKey method, of class PwFile.
     */
//    @Test
//    public void TestUpdateKey() {
//        System.out.println("loadStore");
//        String confFilePath = "/tmp/test1";  
//        String keyName="dummy";
//        String keyValue="zhang";
//        String expResult = "ludi";
//         
//        PwFile.updateKey(keyName, keyValue,confFilePath);
//    }   
    
    /**
     * Test of getKeyPW method, of class PwFile.
     */
//    @Test
//    public void TestGetKeyPW() {
//         
//        String confFilePath = "/tmp/test1";  
//        String keyName="ludi2";
//        String expResult="wang3";
//        String result = PwFile.getKeyPW("ludi2", confFilePath);
//         assertEquals(expResult, result);
//         
//       
//
//    }
//    @Test
//    public void TestDeleteKey() {
//
//        String confFilePath = "/tmp/testconffile";
//        String keyName = "ludi1";
//
//        PwFile.deleteKey(keyName, confFilePath);
//        //assertEquals(expResult, result);
//
//    }

//    @Test
//    public void TestUpdateConfFilePWString() {
//
//        String confFilePath = "/tmp/test1";
//        String newPW = "wang";
//
//        PwFile.updateConfFilePWString(confFilePath, newPW);
//        //assertEquals(expResult, result);
//
//    }

//       @Test
//    public void TestChangeKeyStorePW() {
//
//        String confFilePath = "/tmp/test1";
//        String newPW = "wang";
//
//        PwFile.changeKeyStorePW(confFilePath, newPW);
//        //assertEquals(expResult, result);
//
//    }
    
//           @Test
//    public void TestExportAllKeys() {
//        String confFilePath = "/tmp/test1";
//        String exportFilePath = "/tmp/testExport";
//        String newPW = "wang";
//        Boolean expResult = true;
//        Boolean result = PwFile.exportAllKeys(confFilePath, exportFilePath);
//        assertEquals(expResult, result);
//
//    }
    
//    @Test
//    public void TestImportKeys() {
//
//        String confFilePath = "/tmp/test1";
//        String importFilePath = "/tmp/testImport";
//
//        PwFile.importKeys(confFilePath, importFilePath);
//        //assertEquals(expResult, result);
//
//    }
    
//     @Test
//    public void TestArgs() {
//
//       String[] args = "-conf testfile -create_keystore".split(" ");
//       
//       try {
//       Args vArg = new Args(args);
//       vArg.parse();
//       } catch (Exception ex) {
//           ex.printStackTrace();
//       }
//
//    }  
    
//       @Test
//    public void testCreateConfFile() {
//        System.out.println("create conffile");
//        String confFilePath = "/tmp/test1"; 
//        String keyStorePath = "/tmp/store1";
//        String keyStorePass = "wang";
//        boolean expResult = true;
//        boolean result = PwFile.createConfFile(confFilePath, keyStorePath, keyStorePass);
//        assertEquals(expResult, result);
//    }
}
