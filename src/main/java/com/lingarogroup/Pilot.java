package com.lingarogroup;

public class Pilot implements Flyer {
    private int hoursFlown = 0;
    private boolean instrumentFlightRated = false;

    public Pilot(int hoursFlown, boolean instrumentFlightRated) {
        this.hoursFlown = hoursFlown;
        this.instrumentFlightRated = instrumentFlightRated;
    }

    @Override
    public void fly() {
        System.out.println("Take off");
    }
    @Override
    public int getHoursFlown() {
        return hoursFlown;
    }

    @Override
    public void setHoursFlown(int hoursFlown) {
        this.hoursFlown = hoursFlown;
    }

    @Override
    public boolean isInstrumentFlightRated() {
        return instrumentFlightRated;
    }

    @Override
    public void setInstrumentFlightRated(boolean instrumentFlightRated) {
        this.instrumentFlightRated = instrumentFlightRated;
    }
}
