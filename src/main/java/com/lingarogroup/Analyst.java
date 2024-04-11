package com.lingarogroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyst extends Employee {
    private int projectCount = 0;

    private final String analystRegex = "\\w+=(?<projectCount>\\w+)";;
    private final Pattern analystPattern = Pattern.compile(analystRegex);

    public Analyst(String employeeRecord, int baseSalary) {
        super(employeeRecord, baseSalary);
        Matcher analystMat = analystPattern.matcher(peopleMatcher.group("details"));
        if (analystMat.find()) {
            projectCount = Integer.parseInt(analystMat.group("projectCount"));
        }
        salaryModifier = projectCount * 2;
        salary += salaryModifier;
    }

    @Override
    public int getSalary() {
        return salary;
    }
}
