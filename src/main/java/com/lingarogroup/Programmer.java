package com.lingarogroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Programmer extends Employee {
    private int linesofCode = 0;
    private int yearsOfExp = 0;
    private int iq = 0;

    private final String programmerRegex = "\\w+=(?<locpd>\\w+),\\w+=(?<yoe>\\w+),\\w+=(?<iq>\\w+)";
    private final Pattern programmerPattern = Pattern.compile(programmerRegex);

    public Programmer(String employeeRecord, int baseSalary) {
        super(employeeRecord, baseSalary);
        Matcher programmerMatcher = programmerPattern.matcher(peopleMatcher.group("details"));
        if (programmerMatcher.find()) {
            linesofCode = Integer.parseInt(programmerMatcher.group("locpd"));
            yearsOfExp = Integer.parseInt(programmerMatcher.group("yoe"));
            iq = Integer.parseInt(programmerMatcher.group("iq"));
        }
    }

    @Override
    public int getSalary() {
        salaryModifier = linesofCode * yearsOfExp * iq;
        salary += salaryModifier;
        return salary;
    }


}
