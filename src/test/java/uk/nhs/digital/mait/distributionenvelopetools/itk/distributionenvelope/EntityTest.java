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
// $Id: EntityTest.java 32 2017-04-20 11:54:19Z sfarrow $
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
public class EntityTest {

    private EntityImpl instance;
    
    public EntityTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new EntityImpl();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getParts method, of class Entity.
     */
    @Test
    public void testGetParts() {
        System.out.println("getParts");
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getParts();
        assertEquals(expResult, result);
    }

    /**
     * Test of splitUri method, of class Entity.
     */
    @Test
    public void testSplitUri() {
        System.out.println("splitUri");
        String s = "a:b:c";
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("a");
        expResult.add("b");
        expResult.add("c");
        ArrayList<String> result = instance.splitUri(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of getUri method, of class Entity.
     */
    @Test
    public void testGetUri() {
        System.out.println("getUri");
        String expResult = null;
        String result = instance.getUri();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOID method, of class Entity.
     */
    @Test
    public void testGetOID() {
        System.out.println("getOID");
        String expResult = null;
        String result = instance.getOID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDisplayType method, of class Entity.
     */
    @Test
    public void testGetDisplayType() {
        System.out.println("getDisplayType");
        String expResult = null;
        String result = instance.getDisplayType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class Entity.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        int expResult = -1;
        int result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of isRoutable method, of class Entity.
     */
    @Test
    public void testIsRoutable() {
        System.out.println("isRoutable");
        boolean expResult = false;
        boolean result = instance.isRoutable();
        assertEquals(expResult, result);
    }

    public class EntityImpl extends Entity {

        @Override
        public ArrayList<String> getParts() {
            return null;
        }
    }
    
}
