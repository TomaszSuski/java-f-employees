package com.lingarogroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CEO extends Employee {
    private int avgStockPrice = 0;

    private final String ceoRegex = "\\w+=(?<avgStockPrice>\\w+)";
    private final Pattern ceoPattern = Pattern.compile(ceoRegex);

    public CEO(String employeeRecord, int baseSalary) {
        super(employeeRecord, baseSalary);
        Matcher ceoMat = ceoPattern.matcher(peopleMatcher.group("details"));
        if (ceoMat.find()) {
            avgStockPrice = Integer.parseInt(ceoMat.group("avgStockPrice"));
            salaryModifier = avgStockPrice;
            salary = salary * salaryModifier;
        }
    }
}
