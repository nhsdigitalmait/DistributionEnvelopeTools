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
// $Id: ITKExceptionTest.java 32 2017-04-20 11:54:19Z sfarrow $
package uk.nhs.digital.mait.distributionenvelopetools.itk.util;

import java.util.logging.Level;
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
public class ITKExceptionTest {

    private ITKException instance;
    
    public ITKExceptionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new ITKException("code","text","diagnostics");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setCause method, of class ITKException.
     */
    @Test
    public void testSetCause() {
        System.out.println("setCause");
        Throwable e = null;
        instance.setCause(e);
    }

    /**
     * Test of noStackTrace method, of class ITKException.
     */
    @Test
    public void testNoStackTrace() {
        System.out.println("noStackTrace");
        instance.noStackTrace();
    }

    /**
     * Test of recordContext method, of class ITKException.
     */
    @Test
    public void testRecordContext() {
        System.out.println("recordContext");
        String n = "";
        String m = "";
        String s = "";
        instance.recordContext(n, m, s);
    }

    /**
     * Test of setLoggingLevel method, of class ITKException.
     */
    @Test
    public void testSetLoggingLevel() {
        System.out.println("setLoggingLevel");
        Level l = null;
        instance.setLoggingLevel(l);
    }

    /**
     * Test of report method, of class ITKException.
     */
    @Test
    public void testReport() {
        System.out.println("report");
        String s = "";
        instance.report(s);
    }

    /**
     * Test of toString method, of class ITKException.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "ITKException";
        String result = instance.toString();
        assertTrue(result.startsWith(expResult));
    }

    /**
     * Test of getId method, of class ITKException.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        int expResult = 36;
        String result = instance.getId();
        assertEquals(expResult, result.length());
    }

    /**
     * Test of getCode method, of class ITKException.
     */
    @Test
    public void testGetCode() {
        System.out.println("getCode");
        String expResult = "code";
        String result = instance.getCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of getText method, of class ITKException.
     */
    @Test
    public void testGetText() {
        System.out.println("getText");
        String expResult = "text";
        String result = instance.getText();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDiagnostics method, of class ITKException.
     */
    @Test
    public void testGetDiagnostics() {
        System.out.println("getDiagnostics");
        String expResult = "At ";
        String result = instance.getDiagnostics();
        assertTrue(result.startsWith(expResult));
    }

    /**
     * Test of updateDiagnostics method, of class ITKException.
     */
    @Test
    public void testUpdateDiagnostics() {
        System.out.println("updateDiagnostics");
        String s = "";
        instance.updateDiagnostics(s);
    }
    
}
