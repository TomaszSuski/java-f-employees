package com.lingarogroup;

public interface Flyer {
    void fly();

    int getHoursFlown();

    void setHoursFlown(int hoursFlown);

    boolean isInstrumentFlightRated();

    void setInstrumentFlightRated(boolean instrumentFlightRated);
}
