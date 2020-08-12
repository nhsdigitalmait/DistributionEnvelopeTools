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
package uk.nhs.digital.mait.distributionenvelopetools.itk.distributionenvelope;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.nhs.digital.mait.distributionenvelopetools.itk.util.ITKException;

/**
 *
 * @author simonfarrow
 */
public class DistributionEnvelopeTest {

    private static final String CERT1
            = System.getenv("TKWROOT") + "/contrib/Test_Certificates/111_TLS_Test_Certificates_v3/Test01/test01_111_v3.crt";

    private static final String CERT2 = "test108.crt";
    private static final String KEY1 = "test102.pfx";
    private static final String KEY2 = "test108.pfx";
    private static X509Certificate cert1 = null;
    private static X509Certificate cert2 = null;
    private static RSAPrivateKey rsa1 = null;
    private static RSAPrivateKey rsa2 = null;
    private static KeyStore ks1 = null;
    private static KeyStore ks2 = null;

    private static final String SIGNINGKEY
            = System.getenv("TKWROOT") + "/contrib/Test_Certificates/111_TLS_Test_Certificates_v3/Test01/test01_111_v3.pkcs12";
    private static X509Certificate signingCert = null;
    private static RSAPrivateKey signingKey = null;

    private DistributionEnvelope instance;

    private static final String CN_102 = "CN=test102.oneoneone.nhs.uk, OU=ITK Accreditation Services, O=National Integration Centre";

    public DistributionEnvelopeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws CertificateException, FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableEntryException {
        loadCerts();
        loadKeys();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        instance = DistributionEnvelopeHelper.getInstance().getDistributionEnvelope(
                new String(Files.readAllBytes(Paths.get(
                        System.getenv("TKWROOT") + "/contrib/ITK_2_01_Test_Messages/Correspondence_DE/Ambulance/POCD_MT030001UK01_DIST_Primary.xml"))));
        instance.addPayload(new Payload("payloadid"));
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of newInstance method, of class DistributionEnvelope.
     */
    @Test
    public void testNewInstance() {
        System.out.println("newInstance");
        DistributionEnvelope result = DistributionEnvelope.newInstance();
        assertNotNull(result);
    }

    /**
     * Test of setITKNamespacePrefix method, of class DistributionEnvelope.
     */
    @Test
    public void testSetITKNamespacePrefix() {
        System.out.println("setITKNamespacePrefix");
        String p = "itk:";
        instance.setITKNamespacePrefix(p);
    }

    /**
     * Test of setTo method, of class DistributionEnvelope.
     */
    @Test
    public void testSetTo() {
        try {
            System.out.println("setTo");
            Address[] t = new Address[]{new Address("address")};
            instance.setTo(t);
        } catch (ITKException ex) {
            Logger.getLogger(DistributionEnvelopeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of setAudit method, of class DistributionEnvelope.
     */
    @Test
    public void testSetAudit() {
        System.out.println("setAudit");
        try {
            Identity[] id = new Identity[]{new Identity("id")};
            instance.setAudit(id);
        } catch (ITKException ex) {
            Logger.getLogger(DistributionEnvelopeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of setSender method, of class DistributionEnvelope.
     */
    @Test
    public void testSetSender() {
        System.out.println("setSender");
        Address a = null;
        instance.setSender(a);
    }

    /**
     * Test of setDistributionEnvelope method, of class DistributionEnvelope.
     */
    @Test
    public void testSetDistributionEnvelope() {
        System.out.println("setDistributionEnvelope");
        String d = "";
        instance.setDistributionEnvelope(d);
    }

    /**
     * Test of setTrackingId method, of class DistributionEnvelope.
     */
    @Test
    public void testSetTrackingId() {
        System.out.println("setTrackingId");
        String t = "";
        instance.setTrackingId(t);
    }

    /**
     * Test of setService method, of class DistributionEnvelope.
     */
    @Test
    public void testSetService() {
        System.out.println("setService");
        String s = "";
        instance.setService(s);
    }

    /**
     * Test of addHandlingSpecification method, of class DistributionEnvelope.
     */
    @Test
    public void testAddHandlingSpecification() {
        System.out.println("addHandlingSpecification");
        String s = "";
        String v = "";
        instance.addHandlingSpecification(s, v);
    }

    /**
     * Test of getEnvelope method, of class DistributionEnvelope.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        String expResult = "\n<itk:DistributionEnvelope";
        String result = instance.getEnvelope();
        assertTrue(result.startsWith(expResult));
    }

    /**
     * Test of getService method, of class DistributionEnvelope.
     */
    @Test
    public void testGetService() {
        System.out.println("getService");
        String expResult = "urn:nhs-itk:services:201005:SendCDADocument-v2-0";
        String result = instance.getService();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrackingId method, of class DistributionEnvelope.
     */
    @Test
    public void testGetTrackingId() {
        System.out.println("getTrackingId");
        String expResult = "D7CB2376-F716-4145-B038-E492780D3BE3";
        String result = instance.getTrackingId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInteractionId method, of class DistributionEnvelope.
     */
    @Test
    public void testGetInteractionId() {
        System.out.println("getInteractionId");
        String expResult = "urn:nhs-itk:interaction:primaryRecipientAmbulanceServicePatientReport-v1-0";
        String result = instance.getInteractionId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTo method, of class DistributionEnvelope.
     */
    @Test
    public void testGetTo() {
        System.out.println("getTo");
        int expResult = 1;
        Address[] result = instance.getTo();
        assertEquals(expResult, result.length);
    }

    /**
     * Test of getAudit method, of class DistributionEnvelope.
     */
    @Test
    public void testGetAudit() {
        System.out.println("getAudit");
        int expResult = 1;
        Identity[] result = instance.getAudit();
        assertEquals(expResult, result.length);
    }

    /**
     * Test of getSender method, of class DistributionEnvelope.
     */
    @Test
    public void testGetSender() {
        System.out.println("getSender");
        String expResult = "ods";
        Address result = instance.getSender();
        assertEquals(expResult, result.getParts().get(0));
    }

    /**
     * Test of addRecipient method, of class DistributionEnvelope.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAddRecipient() throws Exception {
        System.out.println("addRecipient");
        String oid = "2.16.840.1.113883.2.1.3.2.4.18.22";
        String id = "recid";
        instance.addRecipient(oid, id);
    }

    /**
     * Test of addIdentity method, of class DistributionEnvelope.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAddIdentity() throws Exception {
        System.out.println("addIdentity");
        String oid = "idoid";
        String id = "idid";
        instance.addIdentity(oid, id);
    }

    /**
     * Test of addSender method, of class DistributionEnvelope.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAddSender() throws Exception {
        System.out.println("addSender");
        String oid = "2.16.840.1.113883.2.1.3.2.4.18.22";
        String id = "sendid";
        instance.addSender(oid, id);
    }

    /**
     * Test of setInteractionId method, of class DistributionEnvelope.
     */
    @Test
    public void testSetInteractionId() {
        System.out.println("setInteractionId");
        String id = "";
        instance.setInteractionId(id);
    }

    /**
     * Test of addPayload method, of class DistributionEnvelope.
     */
    @Test
    public void testAddPayload() {
        System.out.println("addPayload");
        Payload p = null;
        instance.addPayload(p);
    }

    /**
     * Test of parsePayloads method, of class DistributionEnvelope.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testParsePayloads() throws Exception {
        System.out.println("parsePayloads");
        instance.parsePayloads();
    }

    /**
     * Test of setHandlingSpecification method, of class DistributionEnvelope.
     */
    @Test
    public void testSetHandlingSpecification() {
        System.out.println("setHandlingSpecification");
        String key = "";
        String value = "";
        instance.setHandlingSpecification(key, value);
    }

    /**
     * Test of setAckRequested method, of class DistributionEnvelope.
     */
    @Test
    public void testSetAckRequested() {
        System.out.println("setAckRequested");
        boolean b = false;
        instance.setAckRequested(b);
    }

    /**
     * Test of getHandlingSpecification method, of class DistributionEnvelope.
     */
    @Test
    public void testGetHandlingSpecification() {
        System.out.println("getHandlingSpecification");
        String key = "urn:nhs-itk:ns:201005:ackrequested";
        String expResult = "true";
        String result = instance.getHandlingSpecification(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of write method, of class DistributionEnvelope.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testWrite() throws Exception {
        System.out.println("write");
        Writer w = new StringWriter();
        instance.write(w);
        System.out.println(w.toString());
    }

    /**
     * Test of toString method, of class DistributionEnvelope.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "<itk:DistributionEnvelope";
        String result = instance.toString();
        assertTrue(result.startsWith(expResult));
    }

    /**
     * Test of isAckable method, of class DistributionEnvelope.
     */
    @Test
    public void testIsAckable() {
        System.out.println("isAckable");
        boolean expResult = true;
        boolean result = instance.isAckable();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPayloadId method, of class DistributionEnvelope.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetPayloadId() throws Exception {
        System.out.println("getPayloadId");
        int i = 0;
        String expResult = "uuid_";
        String result = instance.getPayloadId(i);
        assertTrue(result.startsWith(expResult));
    }

    /**
     * tests the parsing of a given distribution envelope moved from test
     * package in src
     * @throws java.lang.Exception
     */
    @Test
    public void testRead() throws Exception {
        // This is not encrypted
        String inde = load(
                System.getenv("TKWROOT") + "/contrib/ITK_2_01_Test_Messages/Correspondence_DE/Ambulance/POCD_MT030001UK01_DIST_Primary.xml");
        DistributionEnvelopeHelper helper = DistributionEnvelopeHelper.getInstance();
        DistributionEnvelope de = helper.getDistributionEnvelope(inde);
        Payload[] p = helper.getPayloads(de);
        if (p[0].isEncrypted()) {
            helper.unpackEncryptedPayload(p[0]);
            if (p[0].hasKeyForReader(CN_102)) {
                String firstpayload = p[0].decryptTextContent(CN_102, rsa1);
//              Base64 b64 = new Base64();
//              byte[] x2 = b64.decode(firstpayload);
//              String s2 = new String(x2);
//              System.out.println(s2);
                System.out.println(firstpayload);
            }
        } else {
            String result = p[0].getContent();
            assertTrue(result.startsWith("<ClinicalDocument"));
        }
    }

    /**
     * tests the creation of a distribution envelope and encryption moved from
     * test package in src
     *
     * @throws java.lang.Exception
     */
    @Test
    public void write() throws Exception {
        String[] args = {"write", "text/xml", "src/test/resources/payload.xml"};
        DistributionEnvelope d = DistributionEnvelope.newInstance();
        d.addRecipient(null, "test:address:one");
        d.addRecipient("1.2.826.0.1285.0.2.0.107", "123456789012");
        d.addIdentity("1.2.826.0.1285.0.2.0.107", "99999999999");
        d.addSender(null, "test:address:two");
        d.setService("java:test:service");
        d.setInteractionId("test_interaction_UK01");
        for (int i = 1; i < args.length; i++) {
            // Álternate MIME type and file name
            String mt = args[i++];
            String file = args[i];
            String body = null;
            byte[] content = null;
            Payload p = new Payload(mt);
            boolean pack = (i != 2);
            pack = true;
            if (mt.contains("xml")) {
                body = load(file);
                if (!pack) {
                    p.setProfileId("itk:test:profile-id-v1-0");
                }
                p.setBody(body, pack);
            } else {
                content = binaryLoad(file);
                p.setContent(content, pack);
            }
            d.addPayload(p);
            p.addReaderCertificate(cert1);
//              p.addReaderCertificate(cert2);
//              p.encrypt();
            p.encrypt(signingKey, signingCert);
        }
        String result = d.toString();
        System.out.println(result);
        assertTrue(result.startsWith("<itk:DistributionEnvelope"));
    }

    private static void loadCerts() throws CertificateException, FileNotFoundException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(CERT1);
        cert1 = (X509Certificate) cf.generateCertificate(fis);
//      fis = new FileInputStream(CERT2);
//      cert2 = (X509Certificate) cf.generateCertificate(fis);
    }

    private static void loadKeys() throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, UnrecoverableEntryException, CertificateException {
        // TODO: Read the key files into PKCS8EncodedKeySpec instances (as byte[])
        // and then use an "RSA" KeyFactory.generatePrivateKey() from the spec to make
        // the keys. Check how we get passwords into the operation. 
        //
        // NO. ACTUALLY WANT TO USE EncryptedPrivateKeyInfo
        FileInputStream fis;
        char[] password = "111_washington".toCharArray();
//      ks1 = KeyStore.getInstance("PKCS12");
//      ks2 = KeyStore.getInstance("PKCS12");
//      fis = new FileInputStream(KEY1);
//      ks1.load(fis, password);
        KeyStore.PasswordProtection p = new KeyStore.PasswordProtection(password);
//      rsa1 = (RSAPrivateKey) (((KeyStore.PrivateKeyEntry) ks1.getEntry("1", p)).getPrivateKey());
//      fis = new FileInputStream(KEY2);
//      ks2.load(fis, password);
//      rsa2 = (RSAPrivateKey) (((KeyStore.PrivateKeyEntry) ks2.getEntry("1", p)).getPrivateKey());
//
        KeyStore sks = KeyStore.getInstance("PKCS12");
        fis = new FileInputStream(SIGNINGKEY);
        sks.load(fis, password);
        signingKey = (RSAPrivateKey) (((KeyStore.PrivateKeyEntry) sks.getEntry("1", p)).getPrivateKey());
        signingCert = (X509Certificate) sks.getCertificate("1");
    }

    private static byte[] binaryLoad(String fname)
            throws Exception {
        byte[] file = null;
        File f = new File(fname);
        int l = (int) f.length();
        file = new byte[l];
        int r = -1;
        int ptr = 0;
        try (FileInputStream fis = new FileInputStream(f)) {
            while ((r = fis.read(file, ptr, l)) != -1) {
                ptr += r;
                if (ptr == l) {
                    break;
                }
            }
        }
        return file;
    }

    private static String load(String fname)
            throws Exception {
        StringBuilder sb;
        try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
            sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\r");
            }
        }
        return sb.toString();
    }

    /**
     * Test of main method, of class DistributionEnvelope.
     * @throws java.lang.Exception
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = new String[0];
        DistributionEnvelope.main(args);
    }

}
