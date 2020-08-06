/*
 Copyright 2016  Simon Farrow <simon.farrow1@hscic.gov.uk>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
// $Id: PayloadTest.java 32 2017-04-20 11:54:19Z sfarrow $
package uk.nhs.digital.mait.distributionenvelopetools.itk.distributionenvelope;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author simonfarrow
 */
public class PayloadTest {

    private Payload instance;

    private static Key privateKey;
    private static X509Certificate cert;
    
    private static final String MIME_TYPE = "text/xml";
    private static final String PAYLOAD_BODY = "<body/>";

    public PayloadTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        final String KEY_NAME = "1"; // alias in the keystore
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            char[] password = "test01tls_moscow".toCharArray();
            try (FileInputStream fis = new FileInputStream(System.getenv("TKWROOT") + "/contrib/Test_Certificates/TLS_Test_Certificates/Test01/test01.jks")) {
                ks.load(fis, password);
                Enumeration<String> aliases = ks.aliases();
                while (aliases.hasMoreElements()) {
                    System.out.println(aliases.nextElement());
                }
                privateKey = ks.getKey(KEY_NAME, password);
                cert = (X509Certificate)ks.getCertificate(KEY_NAME);

            } catch (IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | KeyStoreException ex) {
                Logger.getLogger(PayloadTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (KeyStoreException ex) {
            Logger.getLogger(PayloadTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        instance = new Payload(MIME_TYPE);
        instance.setBody(PAYLOAD_BODY, true);
        instance.setProfileId("profileid");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setEncryptedContent method, of class Payload.
     */
    @Test
    public void testSetEncryptedContent() {
        System.out.println("setEncryptedContent");
        String ec = "encryptedcontent";
        instance.setEncryptedContent(ec);
    }

    /**
     * Test of addReceivedReader method, of class Payload.
     */
    @Test
    public void testAddReceivedReader() {
        System.out.println("addReceivedReader");
        String ec = "encryptedcontent";
        instance.setEncryptedContent(ec);
        String n = cert.getSubjectDN().toString();
        String k = "base64encodedsymmetrickey";
        instance.addReceivedReader(n, k);
    }

    /**
     * Test of addReaderCertificate method, of class Payload.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAddReaderCertificate() throws Exception {
        System.out.println("addReaderCertificate");
        X509Certificate r = cert;
        instance.addReaderCertificate(r);
    }

    /**
     * Test of encrypt method, of class Payload.
     * encrypt and sign
     * @throws java.lang.Exception
     */
    @Test
    public void testEncrypt_PrivateKey_X509Certificate() throws Exception {
        System.out.println("encrypt");
        PrivateKey pk = (PrivateKey) PayloadTest.privateKey;
        X509Certificate x509cert = cert;
        instance.addReaderCertificate(x509cert);
        instance.encrypt(pk, x509cert);
        
        String expectedResult = "<xenc:EncryptedData";
        String payloadBody = instance.getPayloadBody();
        assertTrue(payloadBody.startsWith(expectedResult));
    }

    /**
     * Test of encrypt method, of class Payload.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEncrypt_0args() throws Exception {
        System.out.println("encrypt");
        instance.addReaderCertificate(cert);
        instance.encrypt();

        String expectedResult = "<xenc:EncryptedData";
        String payloadBody = instance.getPayloadBody();
        assertTrue(payloadBody.startsWith(expectedResult));
    }

    /**
     * Test of hasKeyForReader method, of class Payload.
     */
    @Test
    public void testHasKeyForReader() {
        System.out.println("hasKeyForReader");

        //initialises receivedReaders
        instance.setEncryptedContent("encryptedcontent");

        String s = "symmetrickeyname";
        boolean expResult = false;
        boolean result = instance.hasKeyForReader(s);
        assertEquals(expResult, result);

        instance.addReceivedReader(s, "symmetrickey");
        expResult = true;
        result = instance.hasKeyForReader(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of decryptTextContent method, of class Payload.
     * returns a string containing decrypted content
     * @throws java.lang.Exception
     */
    @Test
    public void testDecryptTextContent() throws Exception {
        System.out.println("decryptTextContent");
        
        // encrypt with cert
        setupEncryptedData();

        String expResult = PAYLOAD_BODY;
        String keyname = cert.getSubjectDN().toString();
        String result = instance.decryptTextContent(keyname, (PrivateKey) PayloadTest.privateKey);
        assertEquals(expResult, result);
    }

    private String getB64EncodedRSAEncryptedSymmetricKey(String payloadBody) {
        // first CipherValue element non greedy:greedy
        String symmetrickey = payloadBody.replaceFirst("^.*?<xenc:CipherValue>","");
        return symmetrickey.replaceFirst("</xenc:CipherValue>.*$","");
    }

    private String getB64EncodedSymmetricEncryptedContent(String payloadBody) {
        // last (second) CipherValue element greedy:greedy
        String content = payloadBody.replaceFirst("^.*<xenc:CipherValue>","");
        return content.replaceFirst("</xenc:CipherValue>.*$","");
    }

    /**
     * Test of decryptRawContent method, of class Payload.
     * returns a byte array of decrypted content
     * @throws java.lang.Exception
     */
    @Test
    public void testDecryptRawContent() throws Exception {
        System.out.println("decryptRawContent");
        
        setupEncryptedData();

        byte[] expResult = PAYLOAD_BODY.getBytes();
        byte[] result = instance.decryptRawContent(cert.getSubjectDN().toString(), (PrivateKey) privateKey);
        assertArrayEquals(expResult, result);
    }

    private void setupEncryptedData() throws Exception {
        // setup the encrypted data for decryption
        instance.addReaderCertificate(cert);
        instance.encrypt();
        String symmetrickey = getB64EncodedRSAEncryptedSymmetricKey(instance.getPayloadBody());
        String content = getB64EncodedSymmetricEncryptedContent(instance.getPayloadBody());
        
        // set symmetric encrypted content
        instance.setEncryptedContent(content);
        instance.addReceivedReader(cert.getSubjectDN().toString(), symmetrickey);
    }

    /**
     * Test of makeManifestItem method, of class Payload.
     */
    @Test
    public void testMakeManifestItem() {
        System.out.println("makeManifestItem");
        String prefix = "itk";
        String expResult = "<itk:manifestitem";
        String result = instance.makeManifestItem(prefix);
        assertTrue(result.startsWith(expResult));
    }

    /**
     * Test of setProfileId method, of class Payload.
     */
    @Test
    public void testSetProfileId() {
        System.out.println("setProfileId");
        String p = "profileid";
        instance.setProfileId(p);
    }

    /**
     * Test of setBody method, of class Payload.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSetBody() throws Exception {
        System.out.println("setBody");
        String b = PAYLOAD_BODY;
        boolean pack = false;
        instance.setBody(b, pack);
    }

    /**
     * Test of setContent method, of class Payload.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSetContent_byteArr_boolean() throws Exception {
        System.out.println("setContent");
        byte[] data = "content".getBytes();
        boolean pack = false;
        instance.setContent(data, pack);
    }

    /**
     * Test of setBase64 method, of class Payload.
     */
    @Test
    public void testSetBase64() {
        System.out.println("setBase64");
        boolean b = false;
        instance.setBase64(b);
    }

    /**
     * Test of setCompressed method, of class Payload.
     */
    @Test
    public void testSetCompressed() {
        System.out.println("setCompressed");
        boolean c = false;
        instance.setCompressed(c);
    }

    /**
     * Test of setEncrypted method, of class Payload.
     */
    @Test
    public void testSetEncrypted() {
        System.out.println("setEncrypted");
        boolean e = false;
        instance.setEncrypted(e);
    }

    /**
     * Test of isBase64 method, of class Payload.
     */
    @Test
    public void testIsBase64() {
        System.out.println("isBase64");
        boolean expResult = false;
        boolean result = instance.isBase64();
        assertEquals(expResult, result);
    }

    /**
     * Test of isCompressed method, of class Payload.
     */
    @Test
    public void testIsCompressed() {
        System.out.println("isCompressed");
        boolean expResult = false;
        boolean result = instance.isCompressed();
        assertEquals(expResult, result);
    }

    /**
     * Test of isEncrypted method, of class Payload.
     */
    @Test
    public void testIsEncrypted() {
        System.out.println("isEncrypted");
        boolean expResult = false;
        boolean result = instance.isEncrypted();
        assertEquals(expResult, result);
    }

    /**
     * Test of isDecoded method, of class Payload.
     */
    @Test
    public void testIsDecoded() {
        System.out.println("isDecoded");
        boolean expResult = false;
        boolean result = instance.isDecoded();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMimeType method, of class Payload.
     */
    @Test
    public void testGetMimeType() {
        System.out.println("getMimeType");
        String expResult = MIME_TYPE;
        String result = instance.getMimeType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getManifestId method, of class Payload.
     */
    @Test
    public void testGetManifestId() {
        System.out.println("getManifestId");
        String expResult = "uuid_";
        String result = instance.getManifestId();
        assertTrue(result.startsWith(expResult));
    }

    /**
     * Test of getProfileId method, of class Payload.
     */
    @Test
    public void testGetProfileId() {
        System.out.println("getProfileId");
        String expResult = "profileid";
        String result = instance.getProfileId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPayloadBody method, of class Payload.
     */
    @Test
    public void testGetPayloadBody() {
        System.out.println("getPayloadBody");
        String expResult = PAYLOAD_BODY;
        String result = instance.getPayloadBody();
        assertEquals(expResult, result);
    }

    /**
     * Test of getContent method, of class Payload.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetContent() throws Exception {
        System.out.println("getContent");
        String expResult = PAYLOAD_BODY;
        instance.setEncrypted(false);
        String result = instance.getContent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRawContent method, of class Payload.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetRawContent() throws Exception {
        System.out.println("getRawContent");
        byte[] expResult = PAYLOAD_BODY.getBytes();
        byte[] result = instance.getRawContent();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getEncryptionRecipients method, of class Payload.
     */
    @Test
    public void testGetEncryptionRecipients() {
        System.out.println("getEncryptionRecipients");
        instance.setEncrypted(true);
        // hard coded to return null !!
        String[] expResult = null;
        String[] result = instance.getEncryptionRecipients();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of setContent method, of class Payload.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSetContent_String() throws Exception {
        System.out.println("setContent");
        String pb = "payloadbody";
        instance.setContent(pb);
    }

}
