package com.lingarogroup;

import java.text.NumberFormat;
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
        while (peopleMatcher.find()) {
            String employeeRecord = peopleMatcher.group();
            employee = Employee.createEmployee(employeeRecord);
            // checking if class equals another via equals method
            if (employee.getClass().equals(Programmer.class)) {
                System.out.println("programmer");
            } else if (employee.getClass().equals(Manager.class)) {
                System.out.println("manager");
            } else {
                System.out.println("other");
            }
            // example of checking instanceof
            // useful for checking the specific type and casting to it to access specific methods of that type
            if (employee instanceof Programmer) {
                System.out.println("programmer");
                // example of casting employee to Programmer after checking it is an instance of Programmer to access method
                System.out.println(((Programmer) employee).getIq());
            // example of "pattern matching" - while checking instanceof, it's possible to cast new variable to checked type
            // only in java v16 or higher
            } else if (employee instanceof Manager manager) {
                System.out.println("manager");
                // using type specific method on cast variable
                System.out.println(manager.getOrganisationSize());
            } else {
                System.out.println("other");
            }

            // to do it in the switch is possible from v17preview
            // before that version, switches accepted only primitives and enums
            switch (employee) {
                case Programmer prog:
                    System.out.println(prog.getIq());
                    break;
                case Manager man:
                    System.out.println(man.getOrganisationSize());
                    break;
                default:
                    System.out.println("other");
            }

            System.out.println(employee);
            totalSalaries += employee.getSalary();
        }
        return totalSalaries;
    }

}
