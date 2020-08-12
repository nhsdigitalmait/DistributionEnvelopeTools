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

import java.io.FileInputStream;
import java.io.InputStream;
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
import uk.nhs.digital.mait.distributionenvelopetools.itk.util.ITKException;

/**
 *
 * @author simonfarrow
 */
public class NackDistributionEnvelopeTest {
    @Rule
    public final RestoreSystemProperties restoreSystemProperties
            = new RestoreSystemProperties();

    private NackDistributionEnvelope instance;

    public NackDistributionEnvelopeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            System.setProperty("uk.nhs.digital.mait.distributionenvelopetools.itk.router.auditidentity", "auditid");
            System.setProperty("uk.nhs.digital.mait.distributionenvelopetools.itk.router.senderaddress", "senderaddress");
            DistributionEnvelope de;
            try (InputStream is = new FileInputStream(System.getenv("TKWROOT") + "/contrib/ITK_2_01_Test_Messages/Correspondence_DE/Ambulance/POCD_MT030001UK01_DIST_Primary.xml")) {
                de = DistributionEnvelopeHelper.getInstance().getDistributionEnvelope(is);
            }
            instance = new NackDistributionEnvelope(de, new ITKException("id", "code", "text"));
        } catch (Exception ex) {
            Logger.getLogger(NackDistributionEnvelopeTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of makeMessage method, of class NackDistributionEnvelope.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testMakeMessage() throws Exception {
        System.out.println("makeMessage");
        instance.makeMessage();
    }

}
