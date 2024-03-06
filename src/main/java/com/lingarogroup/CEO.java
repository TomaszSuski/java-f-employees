package com.lingarogroup;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CEO implements Employee {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int avgStockPrice = 0;
    private int dr = 0;
    private int salaryModifier = 0;
    private int salary = 0;

    private  final String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s(?<role>\\w+)(?:,\\s\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePat = Pattern.compile(peopleRegex);

    private final String ceoRegex = "\\w+=(?<avgStockPrice>\\w+)";
    private final Pattern ceoPat = Pattern.compile(ceoRegex);

    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();

    public CEO(String employeeRecord, int baseSalary) {
        Matcher peopleMat = peoplePat.matcher(employeeRecord);
        if (peopleMat.find()) {
            firstName = peopleMat.group("firstName");
            lastName = peopleMat.group("lastName");
            dateOfBirth = LocalDate.from(dtFormatter.parse(peopleMat.group("dob")));
            salary = baseSalary;
        }
        Matcher ceoMat = ceoPat.matcher(peopleMat.group("details"));
        if (ceoMat.find()) {
            avgStockPrice = Integer.parseInt(ceoMat.group("avgStockPrice"));
            salary = salary * avgStockPrice;
        }
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSalaryModifier() {
        return salaryModifier;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("%s %s, salary: %s", firstName, lastName, moneyFormat.format(salary));
    }
}
