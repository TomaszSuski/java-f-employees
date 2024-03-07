package com.lingarogroup;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Employee implements IEmployee {
    private static final String PEOPLE_REGEX = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s(?<role>\\w+)(?:,\\s\\{(?<details>.*)\\})?\\n";
    public static final Pattern PEOPLE_PATTERN = Pattern.compile(PEOPLE_REGEX);
    protected final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    protected final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
    protected final Matcher peopleMatcher;
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected int salary = 0;
    protected int salaryModifier = 0;

    public Employee(String employeeRecord, int baseSalary) {
        peopleMatcher = Employee.PEOPLE_PATTERN.matcher(employeeRecord);
        if (peopleMatcher.find()) {
            firstName = peopleMatcher.group("firstName");
            lastName = peopleMatcher.group("lastName");
            dateOfBirth = LocalDate.from(dtFormatter.parse(peopleMatcher.group("dob")));
            salary = baseSalary;
        }
    }

    public static Employee createEmployee(String employeeRecord) {
        Matcher peopleMat = Employee.PEOPLE_PATTERN.matcher(employeeRecord);
        if(peopleMat.find()) {
            return switch (peopleMat.group("role")) {
                case "Programmer" -> new Programmer(employeeRecord, Main.PROGRAMMER_BASE_SALARY);
                case "Manager" -> new Manager(employeeRecord, Main.MANAGER_BASE_SALARY);
                case "Analyst" -> new Analyst(employeeRecord, Main.ANALYST_BASE_SALARY);
                case "CEO" -> new CEO(employeeRecord, Main.CEO_BASE_SALARY);
                default -> null;
            };
        } else {
            return null;
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
