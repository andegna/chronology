package com.andegna.chrono;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.AbstractChronology;
import java.time.chrono.Era;
import java.time.temporal.ChronoField;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.List;
import org.ethiopic.EthiopicCalendar;

public class EthiopianChronology extends AbstractChronology {

    public static final float VERSION = 0.1f;

    // The underlying calender converter with the worst API ever
    private static final EthiopicCalendar ETHIOPIC_CALENDAR = new EthiopicCalendar();

    /**
     * Singleton instance EthiopianChronology
     */
    public static final EthiopianChronology INSTANCE = new EthiopianChronology();

    // prevent public instantiation
    private EthiopianChronology() {
    }

    /**
     * Returns unique identifier to {@link EthiopianChronology}.
     *
     * @return String
     */
    @Override
    public String getId() {
        return "Ethiopian";
    }

    /**
     * {@inheritDoc}
     *
     * @return String
     */
    @Override
    public String getCalendarType() {
        /* FIXME: search for ethiopian calendar type identifier defined by the
         * CLDR and Unicode Locale Data Markup Language(LDML) specifications */
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param prolepticYear
     * @param month
     * @param dayOfMonth
     * @return {@link EthiopianDate}
     */
    @Override
    public EthiopianDate date(int prolepticYear, int month, int dayOfMonth) {
        return new EthiopianDate(EthiopianEra.era(prolepticYear),
                Math.abs(prolepticYear), month, dayOfMonth);
    }

    /**
     * {@inheritDoc} <br />
     * <hr />
     * <b>Warning</b> Technical Detail
     * <hr />
     *
     * All Ethiopian months have 30 days except pagume <br />
     * if we just simply divide the month by 30 and add 1 we will get the month
     * <br />
     * and the <code>dayOfMonth</code> is simply <code>dayOfYear</code> modular
     * 30 <br />
     * <pre>
     * eg1:- Let assume Hidar 29
     *      dayOfYear = meskerem + tikimt + 29 ken
     *                = 30 + 30 + 29
     *                = 89
     *      means hidar 29 is the 89th day of the year
     * eg2:- Let say some one pass 89 as the dayOfYear
     *          month = 89 / 30 + 1 --> int division
     *                = 2 + 1 month
     *                = 3       => Hidar dayOfMonth = 89 % 30 = 29
     * </pre>
     *
     * @param prolepticYear
     * @param dayOfYear
     * @return {@link EthiopianDate}
     */
    @Override
    public EthiopianDate dateYearDay(int prolepticYear, int dayOfYear) {
        int month = dayOfYear / 30 + 1;
        int dayOfMonth = dayOfYear % 30;
        return date(prolepticYear, month, dayOfMonth);
    }

    /**
     * {@inheritDoc}
     *
     * @param epochDay
     * @return {@link EthiopianDate}
     */
    @Override
    public EthiopianDate dateEpochDay(long epochDay) {
        // first get the IsoLocalDate from the epoch
        LocalDate date = LocalDate.ofEpochDay(epochDay);
        // second get the year, monthOfYear and dayOfYear
        int year = date.get(ChronoField.YEAR);
        int monthOfYear = date.get(ChronoField.MONTH_OF_YEAR);
        int dayOfYear = date.get(ChronoField.DAY_OF_MONTH);
        // third convert the iso date to ethiopic
        int[] ethiopianDate = ETHIOPIC_CALENDAR.gregorianToEthiopic(
                year, monthOfYear, dayOfYear);
        // fourth call the date method with the returned value
        // the returnd array hold 3 values in the year,month and day sequence
        return date(ethiopianDate[0], ethiopianDate[1], ethiopianDate[2]);
    }

    /**
     * {@inheritDoc}
     *
     * @param temporal
     * @return {@link EthiopianDate}
     */
    @Override
    public EthiopianDate date(TemporalAccessor temporal) {
        /* if the temporal object is an instance of EthiopianDate,
         just cast and return it */
        if (temporal instanceof EthiopianDate) {
            return (EthiopianDate) temporal;
        }
        /* else get the epoch day of the temporal class and create to return
         an EthiopianDate object */
        return dateEpochDay(temporal.getLong(EPOCH_DAY));
    }

    /**
     * {@inheritDoc}
     *
     * @param prolepticYear
     * @return
     */
    @Override
    public boolean isLeapYear(long prolepticYear) {
        // true if the year is the last of the 4 year cycle
        // eg:- 1999 % 4 = 3 --> leap year
        return prolepticYear % 4 == 3;
    }

    @Override
    public int prolepticYear(Era era, int yearOfEra) {
        // check the era is for this chronology
        if (era instanceof EthiopianEra) {
            throw new ClassCastException("Era must be EthiopianEra");
        }

        // if the era is AMETE_ALEM multiply the year by -1
        return EthiopianEra.AMETE_ALEM.equals(era) ? -1 * yearOfEra
                : yearOfEra;
    }

    /**
     * {@inheritDoc}
     *
     * @param eraValue
     * @return
     */
    @Override
    public EthiopianEra eraOf(int eraValue) {
        switch (eraValue) {
            case 0:
                return EthiopianEra.AMETE_ALEM;
            case 1:
                return EthiopianEra.AMETE_MIHRET;
        }
        throw new DateTimeException("invalid Ethiopian era");
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public List<Era> eras() {
        return Arrays.<Era>asList(EthiopianEra.values());
    }

    /**
     * {@inheritDoc}
     *
     * @param field
     * @return
     */
    @Override
    public ValueRange range(ChronoField field) {
        if (field instanceof ChronoField) {
            ChronoField f = field;
            switch (f) {
                case DAY_OF_MONTH:
                    return ValueRange.of(1, 5, 30);
                case MONTH_OF_YEAR:
                    return ValueRange.of(1, 13);
                default:
                    return field.range();
            }
        }
        return field.range();
    }

    /**
     * Convert {@link EthiopianDate} to {@link LocalDate}
     *
     * @param date
     * @return {@link LocalDate}
     */
    public LocalDate toGregorian(EthiopianDate date) {
        int[] gregorian = ETHIOPIC_CALENDAR.ethiopicToGregorian(
                date.getYear(), date.getMonth(), date.getDay(), ((EthiopianEra) date.getEra()).getEpochOffset());
        return LocalDate.of(gregorian[0], gregorian[1], gregorian[2]);
    }

}
