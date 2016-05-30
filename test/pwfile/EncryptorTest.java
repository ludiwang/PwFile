/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwfile;

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
public class EncryptorTest {
    
    public EncryptorTest() {
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
     * Test of encrypt method, of class Encryptor.
     */
    @Test
    public void testEncrypt() {
        System.out.println("encrypt");
        String key = "";
        String initVector = "";
        String value = "";
        String expResult = "";
        String result = Encryptor.encrypt(key, initVector, value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decrypt method, of class Encryptor.
     */
    @Test
    public void testDecrypt() {
        System.out.println("decrypt");
        String key = "";
        String initVector = "";
        String encrypted = "";
        String expResult = "";
        String result = Encryptor.decrypt(key, initVector, encrypted);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
