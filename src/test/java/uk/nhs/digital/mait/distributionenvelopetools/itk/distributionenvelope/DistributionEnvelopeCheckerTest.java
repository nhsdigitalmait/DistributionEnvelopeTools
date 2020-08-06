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
public class DistributionEnvelopeCheckerTest {
    
    public DistributionEnvelopeCheckerTest() {
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
     * Test of check method, of class DistributionEnvelopeChecker.
     */
    @Test
    public void testCheck() throws Exception {
        System.out.println("check");
        DistributionEnvelope d = null;
        Object o = null;
        DistributionEnvelopeChecker instance = new DistributionEnvelopeCheckerImpl();
        String expResult = "";
        String result = instance.check(d, o);
        assertEquals(expResult, result);
    }

    /**
     * Test of setService method, of class DistributionEnvelopeChecker.
     */
    @Test
    public void testSetService() {
        System.out.println("setService");
        String service = "";
        DistributionEnvelopeChecker instance = new DistributionEnvelopeCheckerImpl();
        instance.setService(service);
    }

    public class DistributionEnvelopeCheckerImpl implements DistributionEnvelopeChecker {

        public String check(DistributionEnvelope d, Object o) throws Exception {
            return "";
        }

        public void setService(String service) {
        }
    }
    
}
