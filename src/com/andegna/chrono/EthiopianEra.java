package com.andegna.chrono;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.chrono.Era;

/**
 *
 * @author Sam As End
 */
public enum EthiopianEra implements Era, Serializable {

    AMETE_ALEM(0, -285019),
    AMETE_MIHRET(1, 1723856);

    private int value;
    private int epochOffset;

    private EthiopianEra(int value, int epochOffset) {
        this.value = value;
        this.epochOffset = epochOffset;
    }

    @Override
    public int getValue() {
        return value;
    }

    public int getEpochOffset() {
        return epochOffset;
    }

    public static EthiopianEra era(int prolepticYear) {
        if (prolepticYear > 0) {
            return AMETE_MIHRET;
        } else {
            return AMETE_ALEM;
        }
    }

    public static EthiopianEra eraOf(int eraValue) {
        switch (eraValue) {
            case 0:
                return EthiopianEra.AMETE_ALEM;
            case 1:
                return EthiopianEra.AMETE_MIHRET;
        }
        throw new DateTimeException("invalid Ethiopian era");
    }
}
