package com.lingarogroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CEO extends Employee implements Flyer {
    private int avgStockPrice = 0;
    private final String ceoRegex = "\\w+=(?<avgStockPrice>\\w+)";
    private final Pattern ceoPattern = Pattern.compile(ceoRegex);
    // example of composition design - class as a field in another class implementing the same interface
    private final Flyer pilot = new Pilot(1000, true);

    public void fly() {
        pilot.fly();
    }

    public int getHoursFlown() {
        return pilot.getHoursFlown();
    }

    public void setHoursFlown(int hoursFlown) {
        pilot.setHoursFlown(hoursFlown);
    }

    public boolean isInstrumentFlightRated() {
        return pilot.isInstrumentFlightRated();
    }

    public void setInstrumentFlightRated(boolean instrumentFlightRated) {
        pilot.setInstrumentFlightRated(instrumentFlightRated);
    }



    public CEO(String employeeRecord, int baseSalary) {
        super(employeeRecord, baseSalary);
        Matcher ceoMat = ceoPattern.matcher(peopleMatcher.group("details"));
        if (ceoMat.find()) {
            avgStockPrice = Integer.parseInt(ceoMat.group("avgStockPrice"));
        }
        salaryModifier = avgStockPrice;
        salary = salary * salaryModifier;
    }

    @Override
    public int getSalary() {
        return salary;
    }
}
