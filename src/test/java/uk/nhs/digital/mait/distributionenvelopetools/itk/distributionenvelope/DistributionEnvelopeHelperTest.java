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
// $Id: DistributionEnvelopeHelperTest.java 34 2017-06-08 12:47:40Z sfarrow $
package uk.nhs.digital.mait.distributionenvelopetools.itk.distributionenvelope;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.junit.Rule;
import org.junit.contrib.java.lang.system.RestoreSystemProperties;

/**
 *
 * @author simonfarrow
 */
public class DistributionEnvelopeHelperTest {

    @Rule
    public final RestoreSystemProperties restoreSystemProperties
            = new RestoreSystemProperties();

    private static Key privateKey;
    private static X509Certificate cert;

    private DistributionEnvelopeHelper instance;

    public DistributionEnvelopeHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            char[] password = "test01tls_moscow".toCharArray();
            try (FileInputStream fis = new FileInputStream(System.getenv("TKWROOT") + "/contrib/Test_Certificates/TLS_Test_Certificates/Test01/test01.jks")) {
                ks.load(fis, password);
                Enumeration<String> aliases = ks.aliases();
                while (aliases.hasMoreElements()) {
                    System.out.println(aliases.nextElement());
                }
                privateKey = ks.getKey("1", password);
                cert = (X509Certificate) ks.getCertificate("1");

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
    public void setUp() {
        try {
            instance = DistributionEnvelopeHelper.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(DistributionEnvelopeHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class DistributionEnvelopeHelper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetInstance() throws Exception {
        System.out.println("getInstance");
        DistributionEnvelopeHelper result = DistributionEnvelopeHelper.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of getPayloads method, of class DistributionEnvelopeHelper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetPayloads() throws Exception {
        System.out.println("getPayloads");
        System.setProperty("org.warlock.itk.router.auditidentity", "auditid");
        System.setProperty("org.warlock.itk.router.senderaddress", "senderaddress");

        InputStream is = new FileInputStream(System.getenv("TKWROOT") + "/contrib/ITK_2_01_Test_Messages/Correspondence_DE/Ambulance/POCD_MT030001UK01_DIST_Primary.xml");
        DistributionEnvelope d = DistributionEnvelopeHelper.getInstance().getDistributionEnvelope(is);
        is.close();

        int expResult = 1;
        Payload[] result = instance.getPayloads(d);
        assertEquals(expResult, result.length);
    }

    /**
     * Test of unpackEncryptedPayload method, of class
     * DistributionEnvelopeHelper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testUnpackEncryptedPayload() throws Exception {
        System.out.println("unpackEncryptedPayload");
        Payload p = new Payload("text/xml");
        String body = "<a><b>testtext</b></a>";
        p.setBody(body, true);
        Key pk = DistributionEnvelopeHelperTest.privateKey;
        p.addReaderCertificate(cert);
        p.encrypt();

        instance.unpackEncryptedPayload(p);
        assertEquals(body, p.decryptTextContent(cert.getSubjectDN().toString(), (PrivateKey) pk));
    }

    /**
     * Test of getDistributionEnvelope method, of class
     * DistributionEnvelopeHelper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDistributionEnvelope_String() throws Exception {
        System.out.println("getDistributionEnvelope");
        String s = new String(Files.readAllBytes(Paths.get(System.getenv("TKWROOT") + "/contrib/ITK_2_01_Test_Messages/Correspondence_DE/Ambulance/POCD_MT030001UK01_DIST_Primary.xml")));

        DistributionEnvelope result = instance.getDistributionEnvelope(s);
        assertNotNull(result);
    }

    /**
     * Test of getDistributionEnvelope method, of class
     * DistributionEnvelopeHelper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDistributionEnvelope_InputStream() throws Exception {
        System.out.println("getDistributionEnvelope");
        InputStream is = new FileInputStream(System.getenv("TKWROOT") + "/contrib/ITK_2_01_Test_Messages/Correspondence_DE/Ambulance/POCD_MT030001UK01_DIST_Primary.xml");
        DistributionEnvelope result = instance.getDistributionEnvelope(is);
        assertNotNull(result);
    }

}
