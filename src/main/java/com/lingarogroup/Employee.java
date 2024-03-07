package com.lingarogroup;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Employee implements IEmployee {
    private  final String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s(?<role>\\w+)(?:,\\s\\{(?<details>.*)\\})?\\n";
    protected final Pattern peoplePat = Pattern.compile(peopleRegex);
    protected final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    protected final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
    protected final Matcher peopleMatcher;
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected int salary = 0;
    protected int salaryModifier = 0;

    public Employee(String employeeRecord, int baseSalary) {
        peopleMatcher = peoplePat.matcher(employeeRecord);
        if (peopleMatcher.find()) {
            firstName = peopleMatcher.group("firstName");
            lastName = peopleMatcher.group("lastName");
            dateOfBirth = LocalDate.from(dtFormatter.parse(peopleMatcher.group("dob")));
            salary = baseSalary;
        }
    }

    @Override
    public abstract int getSalary();

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return String.format("%s %s, salary: %s", getFirstName(), getLastName(), moneyFormat.format(salary));
    }
}
