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

import java.util.ArrayList;
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
public class CheckingServiceTest {

    private CheckingService instance;
    
    public CheckingServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = CheckingService.getInstance();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addCheck method, of class CheckingService.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddCheck() throws Exception {
        System.out.println("addCheck");
        String s = "fqitkservicename";
        String c = "uk.nhs.digital.mait.distributionenvelopetools.itk.distributionenvelope.Checker";
        instance.addCheck(s, c);
    }

    /**
     * Test of getInstance method, of class CheckingService.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        CheckingService result = CheckingService.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of getCheckFailures method, of class CheckingService.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetCheckFailures() throws Exception {
        System.out.println("getCheckFailures");
        DistributionEnvelope d = null;
        Object o = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getCheckFailures(d, o);
        assertEquals(expResult, result);
    }
    
    
}
