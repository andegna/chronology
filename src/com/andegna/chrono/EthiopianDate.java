package com.andegna.chrono;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoField;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

/**
 *
 * @author Sam As End
 */
public class EthiopianDate implements ChronoLocalDate, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * <b>NOW</b>
     *
     * @return
     */
    public static EthiopianDate now() {
        return EthiopianChronology.INSTANCE.date(LocalDate.now());
    }

    public static EthiopianDate from(TemporalAccessor temporal) {
        return EthiopianChronology.INSTANCE.date(temporal);
    }

    public static EthiopianDate of(int prolepticYear, int month, int dayOfMonthyear) {
        return EthiopianChronology.INSTANCE.date(prolepticYear, month, dayOfMonthyear);
    }

    public static EthiopianDate ofEpochDay(long epochDay) {
        return EthiopianChronology.INSTANCE.dateEpochDay(epochDay);
    }

    public static EthiopianDate ofYearDay(int prolepticYear, int dayOfYear) {
        return EthiopianChronology.INSTANCE.dateYearDay(prolepticYear, dayOfYear);
    }

    private final EthiopianEra era;
    private final int year;
    private final int month;
    private final int day;

    public EthiopianDate(EthiopianEra era, int year, int month, int day) {
        validate(year, month, day);
        this.era = era;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public EthiopianChronology getChronology() {
        return EthiopianChronology.INSTANCE;
    }

    @Override
    public int lengthOfMonth() {
        if (month == 13) {
            if (this.isLeapYear()) {
                return 6;
            } else {
                return 5;
            }
        }
        return 30;
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate endDateExclusive) {
        Period period = LocalDate.ofEpochDay(toEpochDay()).until(endDateExclusive);
        return getChronology().period(period.getYears(), period.getMonths(), period.getDays());
    }

    @Override
    public long getLong(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case DAY_OF_MONTH:
                    return day;
                case MONTH_OF_YEAR:
                    return month;
                case YEAR_OF_ERA:
                    return year;
                case YEAR:
                    return era.equals(EthiopianEra.AMETE_ALEM) ? -1 * year : year;
                case ERA:
                    return era.getValue();
                case EPOCH_DAY:
                    return getChronology().toGregorian(this).toEpochDay();
                case DAY_OF_YEAR:
                    return (month - 1) * 30 + day;
                case PROLEPTIC_MONTH:
                    return year * 13 + month - 1;
            }
            return getChronology().toGregorian(this).getLong(field);
        }
        return field.getFrom(this);
    }

    @Override
    public int get(TemporalField field) {
        return (int) getLong(field);
    }

    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        return getChronology().toGregorian(this).until(endExclusive, unit);
    }

    @Override
    public EthiopianDate plus(TemporalAmount amount) {
        return getChronology().date(
                getChronology().toGregorian(this).plus(amount));
    }

    @Override
    public EthiopianDate plus(long amountToAdd, TemporalUnit unit) {
        return getChronology().date(
                getChronology().toGregorian(this).plus(amountToAdd, unit));
    }

    @Override
    public EthiopianDate minus(TemporalAmount amount) {
        return getChronology().date(
                getChronology().toGregorian(this).minus(amount));
    }

    @Override
    public EthiopianDate minus(long amountToAdd, TemporalUnit unit) {
        return getChronology().date(
                getChronology().toGregorian(this).minus(amountToAdd, unit));
    }

    int getYear() {
        return year;
    }

    int getMonth() {
        return month;
    }

    int getDay() {
        return day;
    }

    private void validate(int year, int month, int day) {
        if (1 > day || day > 30) {
            throw new DateTimeException("Invalid date '" + day + "'. Day must be between 1-30");
        } else if (1 > month || month > 13) {
            throw new DateTimeException("Invalid month '" + month + "'. Month must be between 1-13");
        } else if (month == 13 && day > 6) {
            throw new DateTimeException("Invalid date 'Ṗagume " + day + "' " + year);
        } else if (!getChronology().isLeapYear(year) && month == 13 && day > 5) {
            throw new DateTimeException("Invalid date 'Ṗagume 6' as '" + year + "' is not a leap year");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EthiopianDate) {
            return equals((EthiopianDate) obj);
        }
        return false;
    }

    public boolean equals(EthiopianDate date) {
        return this.compareTo(date) == 0;
    }

}
