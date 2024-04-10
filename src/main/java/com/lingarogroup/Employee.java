package com.lingarogroup;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Employee implements IEmployee {
    private static final String PEOPLE_REGEX = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s(?<role>\\w+)(?:,\\s\\{(?<details>.*)\\})?\\n?";
    public static final Pattern PEOPLE_PATTERN = Pattern.compile(PEOPLE_REGEX);
    protected final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    protected final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
    protected final Matcher peopleMatcher;
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected int salary = 0;
    protected int salaryModifier = 0;

    protected Employee() {
        peopleMatcher = null;
        lastName = "N/A";
        firstName = "N/A";
        dateOfBirth = null;
    }
    protected Employee(String employeeRecord, int baseSalary) {
        peopleMatcher = Employee.PEOPLE_PATTERN.matcher(employeeRecord);
        if (peopleMatcher.find()) {
            firstName = peopleMatcher.group("firstName");
            lastName = peopleMatcher.group("lastName");
            dateOfBirth = LocalDate.from(dtFormatter.parse(peopleMatcher.group("dob")));
            salary = baseSalary;
        }
    }

    public static IEmployee createEmployee(String employeeRecord) {
        Matcher peopleMat = Employee.PEOPLE_PATTERN.matcher(employeeRecord);
        if(peopleMat.find()) {
            return switch (peopleMat.group("role")) {
                case "Programmer" -> new Programmer(employeeRecord, Main.PROGRAMMER_BASE_SALARY);
                case "Manager" -> new Manager(employeeRecord, Main.MANAGER_BASE_SALARY);
                case "Analyst" -> new Analyst(employeeRecord, Main.ANALYST_BASE_SALARY);
                case "CEO" -> new CEO(employeeRecord, Main.CEO_BASE_SALARY);
                // example of anonymous nested class declaration and initialisation
//                default -> new Employee() {
//                    @Override
//                    public int getSalary() {
//                        return 0;
//                    }
//                };
                // in case of use interface with exactly one method as a return format, it's possible to use lambda expression
                // here is IEmployee interface that declares exactly one needed method
//                default -> () -> 0;

                // with extending the IEmployee the Comparable there's no mor only one method. Lambdas can't be used.
                default -> new DummyEmployee();
            };
        } else {
            return new DummyEmployee();
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
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return String.format("%s %s, salary: %s", getFirstName(), getLastName(), moneyFormat.format(salary));
    }

    private static final class DummyEmployee extends Employee {
        @Override
        public int getSalary() {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(dateOfBirth, employee.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth);
    }

    // compareTo method from the Comparable interface has to be implemented
    // below is the simplest implementation
    @Override
    public int compareTo(IEmployee o) {
        Employee other = (Employee) o;
        // comparing through the last names only
        return this.lastName.compareTo(other.lastName);
        // reimplemented method to be usable with TreeSet and current data
//        if (this.lastName.compareTo(other.lastName) == 0) {
//            if (this.firstName.compareTo(other.firstName) == 0) {
//                return this.dateOfBirth.compareTo(other.dateOfBirth);
//            } else {
//                return this.firstName.compareTo(other.firstName);
//            }
//        } else {
//            return this.lastName.compareTo(other.lastName);
//        }
        // the same approach may be implemented directly as a comparator in TreeSet constructor
        // which is made in Main class
    }
}
