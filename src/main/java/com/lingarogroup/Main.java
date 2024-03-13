package com.lingarogroup;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

public class Main {
    public static final int PROGRAMMER_BASE_SALARY = 3000;
    public static final int MANAGER_BASE_SALARY = 3500;
    public static final int ANALYST_BASE_SALARY = 2500;
    public static final int CEO_BASE_SALARY = 5000;
    public static void main(String[] args) {
        String peopleList = """
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

        int totalSalaries = getTotalSalaries(peopleList);

        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
        System.out.printf("total salary should be %s%n", currencyInstance.format(totalSalaries));

        // example of default methods usage from Chef interface in Programmer class
//        Matcher peopleMatcher = Employee.PEOPLE_PATTERN.matcher(peopleList);
//        Programmer coder = new Programmer("", 0);
//        coder.cook("hamburger");
    }

    private static int getTotalSalaries(String peopleList) {
        Matcher peopleMatcher = Employee.PEOPLE_PATTERN.matcher(peopleList);

        int totalSalaries = 0;
        IEmployee employee = null;
        // declaration of new ArrayList via List interface generic
        List<IEmployee> employees = new ArrayList<>();
        while (peopleMatcher.find()) {
            String employeeRecord = peopleMatcher.group();
            employee = Employee.createEmployee(employeeRecord);
            // adding to the list
            employees.add(employee);
        }

        //another list of employees to remove
        List<String> removalNames = new ArrayList<>();
        removalNames.add("Flinstone3");
        removalNames.add("Rubble4");

        /* following example is fixed below, using Iterator loop
        // looping through the list when needed
        for (IEmployee worker: employees) {
            // =====
            // trying to remove element while looping through the list doesn't work
            if (worker instanceof Employee) {
                Employee tmpWorker = (Employee) worker;
                if (removalNames.contains(tmpWorker.getLastName())) {
                    employees.remove(worker);
                }
            }
            // =====
            System.out.println(worker);
            totalSalaries += worker.getSalary();
        }
        */

        removeUndesirables(employees, removalNames);
        for (IEmployee worker: employees) {
            // =====
            System.out.println(worker);
            totalSalaries += worker.getSalary();
        }

        return totalSalaries;
    }

    private static void removeUndesirables(List<IEmployee> employees, List<String> removalNames) {
        for (Iterator<IEmployee> it = employees.iterator(); it.hasNext();) {
            IEmployee worker = it.next();
            // =====
            // using iterators hasNext method, it's possible to remove item while looping through collection
            if (worker instanceof Employee tmpWorker) {
                if (removalNames.contains(tmpWorker.getLastName())) {
                    it.remove();
                }
            }
        }
    }

}
