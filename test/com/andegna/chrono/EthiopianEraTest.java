package com.andegna.chrono;

import static com.andegna.chrono.EthiopianEra.AMETE_ALEM;
import static com.andegna.chrono.EthiopianEra.AMETE_MIHRET;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Sam As End
 */
public class EthiopianEraTest {

    /**
     * Test of values method, of class EthiopianEra.
     */
    @Test
    public void testValues() {
        EthiopianEra[] expResult
                = {EthiopianEra.AMETE_ALEM, EthiopianEra.AMETE_MIHRET};
        EthiopianEra[] result = EthiopianEra.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class EthiopianEra.
     */
    @Test
    public void testGetValue() {
        EthiopianEra instance = EthiopianEra.AMETE_ALEM;
        int expResult = 0;
        int result = instance.getValue();
        assertEquals(expResult, result);

        instance = EthiopianEra.AMETE_MIHRET;
        expResult = 1;
        result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEpochOffset method, of class EthiopianEra.
     */
    @Test
    public void testGetEpochOffset() {
        EthiopianEra instance = EthiopianEra.AMETE_ALEM;
        int expResult = -285019;
        int result = instance.getEpochOffset();
        assertEquals(expResult, result);

        instance = EthiopianEra.AMETE_MIHRET;
        expResult = 1723856;
        result = instance.getEpochOffset();
        assertEquals(expResult, result);
    }

    /**
     * Test of era method, of class EthiopianEra.
     */
    @Test
    public void testEra() {
        int[][] cases = {
            {Integer.MIN_VALUE, AMETE_ALEM.getValue()},
            {-5_500, AMETE_ALEM.getValue()},
            {-456, AMETE_ALEM.getValue()},
            {-45, AMETE_ALEM.getValue()},
            {-1, AMETE_ALEM.getValue()},
            //
            {1, AMETE_MIHRET.getValue()},
            {45, AMETE_MIHRET.getValue()},
            {1986, AMETE_MIHRET.getValue()},
            {5_500, AMETE_MIHRET.getValue()},
            {Integer.MAX_VALUE, AMETE_MIHRET.getValue()}
        };

        for (int[] cs : cases) {
            EthiopianEra era = EthiopianEra.era(cs[0]);
            assertEquals(era.getValue(), cs[1]);
        }
    }

}
