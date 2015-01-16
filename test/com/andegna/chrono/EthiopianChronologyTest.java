package com.andegna.chrono;

import static com.andegna.chrono.EthiopianEra.AMETE_MIHRET;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sam As End
 */
public class EthiopianChronologyTest {

    /**
     * Test of date method, of class EthiopianChronology.
     */
    @Test
    public void testDate_3args() {
        int prolepticYear = 1987;
        int month = 3;
        int dayOfMonth = 29;
        EthiopianChronology instance = EthiopianChronology.INSTANCE;
        EthiopianDate expResult = new EthiopianDate(AMETE_MIHRET,
                prolepticYear, month, dayOfMonth);
        EthiopianDate result = instance.date(prolepticYear, month, dayOfMonth);
        assertEquals(expResult, result);
    }

    /**
     * Test of dateYearDay method, of class EthiopianChronology.
     */
    @Test
    public void testDateYearDay() {
        int prolepticYear = 2007;
        int dayOfYear = 3 * 30 + 29;

        EthiopianChronology instance = EthiopianChronology.INSTANCE;
        EthiopianDate expResult = new EthiopianDate(AMETE_MIHRET,
                2007, 4, 29);
        EthiopianDate result = instance.dateYearDay(prolepticYear, dayOfYear);
        assertEquals(expResult.get(ChronoField.YEAR),
                result.get(ChronoField.YEAR));
        assertEquals(expResult.get(ChronoField.MONTH_OF_YEAR),
                result.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(expResult.get(ChronoField.DAY_OF_MONTH),
                result.get(ChronoField.DAY_OF_MONTH));
    }

    /**
     * Test of dateEpochDay method, of class EthiopianChronology.
     */
    @Test
    public void testDateEpochDay() {
        long epochDay = 0L;
        EthiopianChronology instance = EthiopianChronology.INSTANCE;
        LocalDate expResult = LocalDate.ofEpochDay(0);
        EthiopianDate result = instance.dateEpochDay(epochDay);
        assertEquals(expResult, instance.toGregorian(result));
    }

    /**
     * Test of date method, of class EthiopianChronology.
     */
    @Test
    public void testDate_TemporalAccessor() {
        //20/02/1855    29/10/1862
        TemporalAccessor temporal = LocalDate.of(1862, 10, 29);
        EthiopianChronology instance = EthiopianChronology.INSTANCE;
        EthiopianDate expResult = new EthiopianDate(AMETE_MIHRET,
                1855, 02, 20);
        EthiopianDate result = instance.date(temporal);
        assertEquals(expResult, result);
    }

    /**
     * Test of isLeapYear method, of class EthiopianChronology.
     */
    @Test
    public void testIsLeapYear() {
        long[] leapProlepticYears = {1999, 2003, 2007, 20011};
        long[] NonleapProlepticYears = {1998, 2000, 2002, 2005};

        EthiopianChronology instance = EthiopianChronology.INSTANCE;

        for (long prolepticYear : leapProlepticYears) {
            boolean result = instance.isLeapYear(prolepticYear);
            assertTrue(result);
        }

        for (long prolepticYear : NonleapProlepticYears) {
            boolean result = instance.isLeapYear(prolepticYear);
            assertFalse(result);
        }
    }

}
