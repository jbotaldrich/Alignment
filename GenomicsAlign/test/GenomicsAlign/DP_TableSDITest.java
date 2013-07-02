/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GenomicsAlign;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aldr699
 */
public class DP_TableSDITest {
    
    public DP_TableSDITest() {
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
     * Test of InitializeTable method, of class DP_TableSDI.
     */
    @Test
    public void testInitializeTable() {
        System.out.println("InitializeTable");
        DP_TableSDI instance = null;
        instance.InitializeTable();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Get alignment method, of class DP_TableSDI.
     */
    @Test
    public void testlocal(){
        Sequence s1 = new Sequence();
        s1.header = "doy";
        s1.sequence = "accccgtttccaacgtcaccccc";
        Sequence s2 = new Sequence();
        s2.header = "doy2";
        s2.sequence = "acggtccgtttccaacgtcaccgcc";
        System.out.println("local alignment");
        Parameters p = new Parameters();
        Alignment local = new LocalAlignment(p);
        DP_TableSDI instance = new DP_TableSDI(s1,s2,local);
        instance.Getalignment();
        ArrayList<AlignedSequences> seqs = instance.BacktrackAlignment();
        System.out.println(seqs.get(0));
        
    }
    
    @Test
    public void testGetalignment() {
        System.out.println("Getalignment");
        DP_TableSDI instance = null;
        boolean expResult = false;
        boolean result = instance.Getalignment();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of BacktrackAlignment method, of class DP_TableSDI.
     */
    @Test
    public void testBacktrackAlignment() {
        System.out.println("BacktrackAlignment");
        DP_TableSDI instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.BacktrackAlignment();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAlignment method, of class DP_TableSDI.
     */
    @Test
    public void testSetAlignment() {
        System.out.println("setAlignment");
        Alignment a = null;
        DP_TableSDI instance = null;
        instance.setAlignment(a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getcell method, of class DP_TableSDI.
     */
    @Test
    public void testGetcell() {
        System.out.println("getcell");
        int i = 0;
        int j = 0;
        DP_TableSDI instance = null;
        DP_CellSDI expResult = null;
        DP_CellSDI result = instance.getcell(i, j);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getm method, of class DP_TableSDI.
     */
    @Test
    public void testGetm() {
        System.out.println("getm");
        DP_TableSDI instance = null;
        int expResult = 0;
        int result = instance.getm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getn method, of class DP_TableSDI.
     */
    @Test
    public void testGetn() {
        System.out.println("getn");
        DP_TableSDI instance = null;
        int expResult = 0;
        int result = instance.getn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetElement method, of class DP_TableSDI.
     */
    @Test
    public void testGetElement() {
        System.out.println("GetElement");
        Sequence seq = null;
        int i = 0;
        DP_TableSDI instance = null;
        char expResult = ' ';
        char result = instance.GetElement(seq, i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class DP_TableSDI.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        DP_TableSDI instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}