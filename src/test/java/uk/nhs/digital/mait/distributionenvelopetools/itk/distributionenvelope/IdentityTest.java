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
public class IdentityTest {

    private Identity instance;
    
    public IdentityTest() {
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
            instance = new Identity("12345678901234567890123:567890");
        } catch (ITKException ex) {
            Logger.getLogger(IdentityTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isExternal method, of class Identity.
     */
    @Test
    public void testIsExternal() {
        System.out.println("isExternal");
        boolean expResult = false;
        boolean result = instance.isExternal();
        assertEquals(expResult, result);
    }

    /**
     * Test of getParts method, of class Identity.
     */
    @Test
    public void testGetParts() {
        System.out.println("getParts");
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("123");
        expResult.add("567890");
        ArrayList<String> result = instance.getParts();
        assertEquals(expResult, result);
    }
    
}
