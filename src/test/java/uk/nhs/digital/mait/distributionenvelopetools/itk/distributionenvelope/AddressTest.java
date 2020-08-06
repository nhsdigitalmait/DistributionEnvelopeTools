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
// $Id: AddressTest.java 32 2017-04-20 11:54:19Z sfarrow $
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
public class AddressTest {
    
    public AddressTest() {
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
     * Test of getParts method, of class Address.
     */
    @Test
    public void testGetParts() {
        try {
            System.out.println("getParts");
            Address instance = new Address("12345678901234567890123456789:1234567890");
            ArrayList<String>  expResult = new ArrayList<>();
            expResult.add("3456789");
            expResult.add("1234567890");
            ArrayList<String> result = instance.getParts();
            assertEquals(expResult, result);
        } catch (ITKException ex) {
            Logger.getLogger(AddressTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}