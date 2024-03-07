package com.lingarogroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager extends Employee {
    private int organisationSize = 0;
    private int directReports = 0;

    private final String managerRegex = "\\w+=(?<orgSize>\\w+),\\w+=(?<dr>\\w+)";
    private final Pattern managerPattern = Pattern.compile(managerRegex);

    public Manager(String employeeRecord, int baseSalary) {
        super(employeeRecord, baseSalary);
        Matcher managerMat = managerPattern.matcher(peopleMatcher.group("details"));
        if (managerMat.find()) {
            organisationSize = Integer.parseInt(managerMat.group("orgSize"));
            directReports = Integer.parseInt(managerMat.group("dr"));
            salaryModifier = organisationSize * directReports;
            salary += salaryModifier;
        }
    }
}
