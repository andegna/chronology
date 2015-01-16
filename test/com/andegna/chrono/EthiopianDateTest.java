package com.andegna.chrono;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Sam As End
 */
public class EthiopianDateTest {

    @Test
    public void testNow() {
        EthiopianDate ethiopianDate = EthiopianDate.now();
        LocalDate converted = EthiopianChronology.INSTANCE
                .toGregorian(ethiopianDate);
        LocalDate localDate = LocalDate.now();

        assertEquals(converted, localDate);
    }

    @Test
    public void testGetChronology() {
        EthiopianDate instance = EthiopianDate.now();
        EthiopianChronology expResult = EthiopianChronology.INSTANCE;
        EthiopianChronology result = instance.getChronology();
        assertEquals(expResult, result);
    }

    @Test
    public void testLengthOfMonth() {
        EthiopianDate tir = new EthiopianDate(EthiopianEra.AMETE_MIHRET,
                1986, 5, 12);
        EthiopianDate hidar = new EthiopianDate(EthiopianEra.AMETE_MIHRET,
                2007, 3, 21);
        EthiopianDate pagume5 = new EthiopianDate(EthiopianEra.AMETE_MIHRET,
                2004, 13, 5);
        EthiopianDate pagume6 = new EthiopianDate(EthiopianEra.AMETE_MIHRET,
                1999, 13, 6);

        assertEquals(30, tir.lengthOfMonth());
        assertEquals(30, hidar.lengthOfMonth());
        assertEquals(5, pagume5.lengthOfMonth());
        assertEquals(6, pagume6.lengthOfMonth());
    }

    @Test
    public void testGet() {
        EthiopianDate date = new EthiopianDate(EthiopianEra.AMETE_MIHRET,
                1986, 3, 21);
        assertEquals(date.get(ChronoField.YEAR), 1986);
        assertEquals(date.get(ChronoField.MONTH_OF_YEAR), 3);
        assertEquals(date.get(ChronoField.DAY_OF_MONTH), 21);
        assertEquals(date.get(ChronoField.DAY_OF_WEEK), 2);
        assertEquals(date.get(ChronoField.DAY_OF_YEAR), 2 * 30 + 21);
    }

    @Test(expected = DateTimeException.class)
    public void testInvalidDay1() {
        new EthiopianDate(EthiopianEra.AMETE_MIHRET, 2007, 4, 31);
    }

    @Test(expected = DateTimeException.class)
    public void testInvalidDay2() {
        new EthiopianDate(EthiopianEra.AMETE_MIHRET, 2007, 4, 0);
    }

    @Test(expected = DateTimeException.class)
    public void testInvalidMonth1() {
        new EthiopianDate(EthiopianEra.AMETE_MIHRET, 2007, 0, 21);
    }

    @Test(expected = DateTimeException.class)
    public void testInvalidMonth2() {
        new EthiopianDate(EthiopianEra.AMETE_MIHRET, 2007, 14, 21);
    }

    @Test(expected = DateTimeException.class)
    public void testInvalidDayWithPagume() {
        new EthiopianDate(EthiopianEra.AMETE_MIHRET, 2007, 13, 7);
    }

    @Test(expected = DateTimeException.class)
    public void testInvalidDayWithPagumeLeap1() {
        new EthiopianDate(EthiopianEra.AMETE_MIHRET, 2000, 13, 6);
    }

    @Test(expected = DateTimeException.class)
    public void testInvalidDayWithPagumeLeap2() {
        new EthiopianDate(EthiopianEra.AMETE_MIHRET, 2004, 13, 6);
    }

}
