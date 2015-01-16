// http://ethiopic.org/Calendars/EthiopicCalendar.java
package org.ethiopic;

public class EthiopicCalendar {
    /*
     ** ********************************************************************************
     **  Era Definitions and Private Data
     ** ********************************************************************************
     */

    public static final int JD_EPOCH_OFFSET_AMETE_ALEM = -285019; // ዓ/ዓ
    public static final int JD_EPOCH_OFFSET_AMETE_MIHRET = 1723856; // ዓ/�?
    public static final int JD_EPOCH_OFFSET_COPTIC = 1824665;
    public static final int JD_EPOCH_OFFSET_GREGORIAN = 1721426;
    public static final int JD_EPOCH_OFFSET_UNSET = -1;

    private int jdOffset = JD_EPOCH_OFFSET_UNSET;

    private int year = -1;
    private int month = -1;
    private int day = -1;
    private boolean dateIsUnset = true;


    /*
     ** ********************************************************************************
     **  Constructors
     ** ********************************************************************************
     */
    public EthiopicCalendar() {
    }

    public EthiopicCalendar(int year, int month, int day, int era) throws java.lang.ArithmeticException {
        this.set(year, month, day, era);
    }

    public EthiopicCalendar(int year, int month, int day) {
        this.set(year, month, day);
    }

    /*
     ** ********************************************************************************
     **  Simple Setters and Getters
     ** ********************************************************************************
     */
    public void set(int year, int month, int day, int era) throws java.lang.ArithmeticException {
        this.year = year;
        this.month = month;
        this.day = day;
        this.setEra(era);
        this.dateIsUnset = false;
    }

    public void set(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.dateIsUnset = false;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getEra() {
        return jdOffset;
    }

    public int[] getDate() {
        int date[] = {year, month, day, jdOffset};
        return date;
    }

    public void setEra(int era) throws java.lang.ArithmeticException {
        if ((JD_EPOCH_OFFSET_AMETE_ALEM == era)
                || (JD_EPOCH_OFFSET_AMETE_MIHRET == era)) {
            jdOffset = era;
        } else {
            throw (new java.lang.ArithmeticException("Unknown era: " + era + " must be either ዓ/ዓ or ዓ/�?."));
        }
    }

    public boolean isEraSet() {
        return (JD_EPOCH_OFFSET_UNSET == jdOffset) ? false : true;
    }

    public void unsetEra() {
        jdOffset = JD_EPOCH_OFFSET_UNSET;
    }

    public void unset() {
        unsetEra();
        year = -1;
        month = -1;
        day = -1;
        dateIsUnset = true;
    }

    public boolean isDateSet() {
        return (dateIsUnset) ? false : true;
    }


    /*
     ** ********************************************************************************
     **  Conversion Methods To/From the Ethiopic & Gregorian Calendars
     ** ********************************************************************************
     */
    public int[] ethiopicToGregorian(int era) throws java.lang.ArithmeticException {
        if (!isDateSet()) {
            throw (new java.lang.ArithmeticException("Unset date."));
        }
        return ethiopicToGregorian(this.year, this.month, this.day, era);
    }

    public int[] ethiopicToGregorian(int year, int month, int day, int era) throws java.lang.ArithmeticException {
        setEra(era);
        int[] date = ethiopicToGregorian(year, month, day);
        unsetEra();
        return date;
    }

    public int[] ethiopicToGregorian() throws java.lang.ArithmeticException {
        if (dateIsUnset) {
            throw (new java.lang.ArithmeticException("Unset date."));
        }
        return ethiopicToGregorian(this.year, this.month, this.day);
    }

    public int[] ethiopicToGregorian(int year, int month, int day) {
        if (!isEraSet()) {
            if (year <= 0) {
                setEra(JD_EPOCH_OFFSET_AMETE_ALEM);
            } else {
                setEra(JD_EPOCH_OFFSET_AMETE_MIHRET);
            }
        }

        int jdn = ethiopicToJDN(year, month, day);
        return jdnToGregorian(jdn);
    }

    public int[] gregorianToEthiopic() throws java.lang.ArithmeticException {
        if (dateIsUnset) {
            throw (new java.lang.ArithmeticException("Unset date."));
        }
        return gregorianToEthiopic(this.year, this.month, this.day);
    }

    public int[] gregorianToEthiopic(int year, int month, int day) {
        int jdn = gregorianToJDN(year, month, day);

        return jdnToEthiopic(jdn, guessEraFromJDN(jdn));
    }


    /*
     ** ********************************************************************************
     **  Conversion Methods To/From the Julian Day Number
     ** ********************************************************************************
     */
    private static int nMonths = 12;

    private static int monthDays[] = {
        0,
        31, 28, 31, 30, 31, 30,
        31, 31, 30, 31, 30, 31
    };

    private int quotient(long i, long j) {
        return (int) Math.floor((double) i / j);
    }

    private int mod(long i, long j) {
        return (int) (i - (j * quotient(i, j)));
    }

    private int guessEraFromJDN(int jdn) {
        return (jdn >= (JD_EPOCH_OFFSET_AMETE_MIHRET + 365))
                ? JD_EPOCH_OFFSET_AMETE_MIHRET
                : JD_EPOCH_OFFSET_AMETE_ALEM;
    }

    private boolean isGregorianLeap(int year) {
        return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
    }

    public int[] jdnToGregorian(int j) {
        int r2000 = mod((j - JD_EPOCH_OFFSET_GREGORIAN), 730485);
        int r400 = mod((j - JD_EPOCH_OFFSET_GREGORIAN), 146097);
        int r100 = mod(r400, 36524);
        int r4 = mod(r100, 1461);

        int n = mod(r4, 365) + 365 * quotient(r4, 1460);
        int s = quotient(r4, 1095);

        int aprime = 400 * quotient((j - JD_EPOCH_OFFSET_GREGORIAN), 146097)
                + 100 * quotient(r400, 36524)
                + 4 * quotient(r100, 1461)
                + quotient(r4, 365)
                - quotient(r4, 1460)
                - quotient(r2000, 730484);
        int year = aprime + 1;
        int t = quotient((364 + s - n), 306);
        int month = t * (quotient(n, 31) + 1) + (1 - t) * (quotient((5 * (n - s) + 13), 153) + 1);
        /*
         int day    = t * ( n - s - 31*month + 32 )
         + ( 1 - t ) * ( n - s - 30*month - quotient((3*month - 2), 5) + 33 )
         ;
         */

        // int n2000 = quotient( r2000, 730484 );
        n += 1 - quotient(r2000, 730484);
        int day = n;

        if ((r100 == 0) && (n == 0) && (r400 != 0)) {
            month = 12;
            day = 31;
        } else {
            monthDays[2] = (isGregorianLeap(year)) ? 29 : 28;
            for (int i = 1; i <= nMonths; ++i) {
                if (n <= monthDays[i]) {
                    day = n;
                    break;
                }
                n -= monthDays[i];
            }
        }

        int out[] = {year, month, day};

        return out;
    }

    public int gregorianToJDN(int year, int month, int day) {
        int s = quotient(year, 4)
                - quotient(year - 1, 4)
                - quotient(year, 100)
                + quotient(year - 1, 100)
                + quotient(year, 400)
                - quotient(year - 1, 400);

        int t = quotient(14 - month, 12);

        int n = 31 * t * (month - 1)
                + (1 - t) * (59 + s + 30 * (month - 3) + quotient((3 * month - 7), 5))
                + day - 1;

        int j = JD_EPOCH_OFFSET_GREGORIAN
                + 365 * (year - 1)
                + quotient(year - 1, 4)
                - quotient(year - 1, 100)
                + quotient(year - 1, 400)
                + n;

        return j;
    }

    public int[] jdnToEthiopic(int jdn) {
        return (isEraSet())
                ? jdnToEthiopic(jdn, jdOffset)
                : jdnToEthiopic(jdn, guessEraFromJDN(jdn));
    }

    public int[] jdnToEthiopic(int jdn, int era) {
        long r = mod((jdn - era), 1461);
        long n = mod(r, 365) + 365 * quotient(r, 1460);

        int year = 4 * quotient((jdn - era), 1461)
                + quotient(r, 365)
                - quotient(r, 1460);
        int month = quotient(n, 30) + 1;
        int day = mod(n, 30) + 1;

        return new int[]{year, month, day};
    }

    public int ethiopicToJDN() throws java.lang.ArithmeticException {
        if (dateIsUnset) {
            throw (new java.lang.ArithmeticException("Unset date."));
        }
        return ethiopicToJDN(this.year, this.month, this.day);
    }

    /**
     * Computes the Julian day number of the given Coptic or Ethiopic date. This
     * method assumes that the JDN epoch offset has been set. This method is
     * called by copticToGregorian and ethiopicToGregorian which will set the
     * jdn offset context.
     *
     * @param year a year in the Ethiopic calendar
     * @param month a month in the Ethiopic calendar
     * @param date a date in the Ethiopic calendar
     *
     * @return The Julian Day Number (JDN)
     */
    private int ethCopticToJDN(int year, int month, int day, int era) {
        int jdn = (era + 365)
                + 365 * (year - 1)
                + quotient(year, 4)
                + 30 * month
                + day - 31;

        return jdn;
    }

    public int ethiopicToJDN(int year, int month, int day) {
        return (isEraSet())
                ? ethCopticToJDN(year, month, day, jdOffset)
                : ethCopticToJDN(year, month, day, JD_EPOCH_OFFSET_AMETE_MIHRET);
    }

    public int ethiopicToJDN(int era) throws java.lang.ArithmeticException {
        return ethiopicToJDN(year, month, day, era);
    }

    public int ethiopicToJDN(int year, int month, int day, int era) throws java.lang.ArithmeticException {
        return ethCopticToJDN(year, month, day, era);
    }


    /*
     ** ********************************************************************************
     **  Methods for the Coptic Calendar
     ** ********************************************************************************
     */
    public int[] copticToGregorian() throws java.lang.ArithmeticException {
        if (dateIsUnset) {
            throw (new java.lang.ArithmeticException("Unset date."));
        }
        return copticToGregorian(this.year, this.month, this.day);
    }

    public int[] copticToGregorian(int year, int month, int day) {
        setEra(JD_EPOCH_OFFSET_COPTIC);
        int jdn = ethiopicToJDN(year, month, day);
        return jdnToGregorian(jdn);
    }

    public int[] gregorianToCoptic() throws java.lang.ArithmeticException {
        if (dateIsUnset) {
            throw (new java.lang.ArithmeticException("Unset date."));
        }
        return gregorianToCoptic(this.year, this.month, this.day);
    }

    public int[] gregorianToCoptic(int year, int month, int day) {
        setEra(JD_EPOCH_OFFSET_COPTIC);
        int jdn = gregorianToJDN(year, month, day);
        return jdnToEthiopic(jdn);
    }

    public int copticToJDN(int year, int month, int day) {
        return ethCopticToJDN(year, month, day, JD_EPOCH_OFFSET_COPTIC);
    }

}
