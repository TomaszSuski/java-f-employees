package com.lingarogroup;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final int PROGRAMMER_BASE_SALARY = 3000;
    private static final int MANAGER_BASE_SALARY = 3500;
    private static final int ANALYST_BASE_SALARY = 2500;
    private static final int CEO_BASE_SALARY = 5000;
    public static void main(String[] args) {
        String peopleList = """
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone2, Fred, 1/1/1900, Programmer, {locpd=1300,yoe=14,iq=100}
            Flinstone3, Fred, 1/1/1900, Programmer, {locpd=2300,yoe=8,iq=105}
            Flinstone4, Fred, 1/1/1900, Programmer, {locpd=1630,yoe=3,iq=115}
            Flinstone5, Fred, 1/1/1900, Programmer, {locpd=5,yoe=10,iq=100}
            Rubble, Barney, 2/2/1905, Manager, {orgsize=300,dr=10}
            Rubble2, Barney, 2/2/1905, Manager, {orgsize=100,dr=4}
            Rubble3, Barney, 2/2/1905, Manager, {orgsize=200,dr=2}
            Rubble4, Barney, 2/2/1905, Manager, {orgsize=500,dr=8}
            Rubble5, Barney, 2/2/1905, Manager, {orgsize=175,dr=20}
            Flinstone, Wilma, 3/3/1910, Analyst, {projectCount=3}
            Flinstone2, Wilma, 3/3/1910, Analyst, {projectCount=4}
            Flinstone3, Wilma, 3/3/1910, Analyst, {projectCount=5}
            Flinstone4, Wilma, 3/3/1910, Analyst, {projectCount=6}
            Flinstone5, Wilma, 3/3/1910, Analyst, {projectCount=9}
            Rubble, Betty, 4/4/1915, CEO, {avgStockPrice=300}
            """;

        String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s(?<role>\\w+)(?:,\\s\\{(?<details>.*)\\})?\\n";
        Pattern peoplePat = Pattern.compile(peopleRegex);
        int totalSalaries = getTotalSalaries(peoplePat, peopleList);

        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        System.out.printf("total salary should be %s%n", currencyInstance.format(totalSalaries));

    }

    private static int getTotalSalaries(Pattern peoplePat, String peopleList) {
        Matcher peopleMat = peoplePat.matcher(peopleList);

        int totalSalaries = 0;
        Employee employee = null;
        while (peopleMat.find()) {
            String employeeRecord = peopleMat.group();
            employee = switch (peopleMat.group("role")) {
                case "Programmer" -> new Programmer(employeeRecord, PROGRAMMER_BASE_SALARY);
                case "Manager" -> new Manager(employeeRecord, MANAGER_BASE_SALARY);
                case "Analyst" -> new Analyst(employeeRecord, ANALYST_BASE_SALARY);
                case "CEO" -> new CEO(employeeRecord, CEO_BASE_SALARY);
                default -> null;
            };
            if(employee != null) {
                System.out.println(employee);
                totalSalaries += employee.getSalary();
            }
        }
        return totalSalaries;
    }

}
