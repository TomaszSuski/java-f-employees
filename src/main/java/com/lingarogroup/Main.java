package com.lingarogroup;

import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;

public class Main {
    public static final int PROGRAMMER_BASE_SALARY = 3000;
    public static final int MANAGER_BASE_SALARY = 3500;
    public static final int ANALYST_BASE_SALARY = 2500;
    public static final int CEO_BASE_SALARY = 5000;
    public static void main(String[] args) {
        String peopleList = """
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmerzzz, {locpd=2000,yoe=10,iq=140}
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
            // 22 entries


        int totalSalaries = getTotalSalaries(peopleList);

        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        System.out.printf("total salary should be %s%n", currencyInstance.format(totalSalaries));
    }

    private static int getTotalSalaries(String peopleList) {
        Matcher peopleMatcher = Employee.PEOPLE_PATTERN.matcher(peopleList);

        int totalSalaries = 0;
        IEmployee employee = null;
        // declaration of new HashSet via Set generic
        // creates unordered set
//        Set<IEmployee> employees = new HashSet<>();
        // the same using LinkedHashSet creates ordered set
        Set<IEmployee> employees = new LinkedHashSet<>();
        // but LinkedHashSet is a lot more expensive in time and computing power,
        // and it also doesn't support getting particular element via index.
        // To do this, the LinkedHashSet has to be changed to List, e.g. new ArrayList<>(employees).get(10)
        // but it's also expensive operation - whole set has to be put into a list to get some element

        while (peopleMatcher.find()) {
            String employeeRecord = peopleMatcher.group();
            employee = Employee.createEmployee(employeeRecord);
            // adding to the list
            employees.add(employee);
        }

        for (IEmployee worker: employees) {
            // =====
            System.out.println(worker);
            totalSalaries += worker.getSalary();
        }

        System.out.println(employees.size()); // 17 entries - duplicates eliminated

        return totalSalaries;
    }
}
