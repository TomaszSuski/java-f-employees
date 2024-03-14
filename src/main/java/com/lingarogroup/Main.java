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
//        List<String> undesirables = new ArrayList<>();
//        undesirables.add("Flinstone3");
//        undesirables.add("Rubble4");

        // and easier way of doing it, but this way it's unmodifiable
//        List<String> undesirables = List.of("Flinstone3", "Rubble4");
        // or the same, but creating modifiable ArrayList in one line
//        List<String> undesirables = new ArrayList<>(List.of("Flinstone3", "Rubble4"));

        // creating arrays from lists
        // generic Object array - not very helpful, but may be necessary in some cases
        Object[] genericArray = employees.toArray();
        // typed array - should be used when possible
        // needs an array instance initialized in argument
        Object[] typedArray = employees.toArray(new IEmployee[0]);

        // CONTAINS method and it's limitations
        // simple example of truthy check
        IEmployee myEmp = employees.get(4);
        System.out.println(employees.contains(myEmp)); // true
        // but in case of checking using an object created separately, which is not an actual element of the list:
        IEmployee employee1 = Employee.createEmployee("Flinstone4, Fred, 1/1/1900, Programmer, {locpd=1630,yoe=3,iq=115}");
        System.out.println(employees.contains(employee1)); // false initially. true after implementing own equals() in Employee
        // the reason is that contains() uses default Object class equals() method inside. And the default equals() method
        // is checking o1 == o2, which is checking if it is the same space in the memory
        // to avoid this, own created objects (classes) should override equals() method, which is done now in Employee class





        /* following example is fixed below, using Iterator loop
        // looping through the list when needed
        for (IEmployee worker: employees) {
            // =====
            // trying to remove element while looping through the list doesn't work
            if (worker instanceof Employee) {
                Employee tmpWorker = (Employee) worker;
                if (undesirables.contains(tmpWorker.getLastName())) {
                    employees.remove(worker);
                }
            }
            // =====
            System.out.println(worker);
            totalSalaries += worker.getSalary();
        }
        */

        // =======
        // SORTING
        employees.sort(new Comparator<IEmployee>() {
            /* generated code uses IEmployee interface, which don't use firstnames or lastnames, so it can't compare them
            the objects passed to compare method have to be cast to Employee class to use these fields

            @Override
            public int compare(IEmployee o1, IEmployee o2) {
                return 0;
            }
            */

            // actual implementation
            @Override
            public int compare(IEmployee o1, IEmployee o2) {
                if (o1 instanceof Employee emp1 && o2 instanceof Employee emp2) {
                    int lastNameCheck = emp1.lastName.compareTo(emp2.lastName);
                    int firstNameCheck = emp1.firstName.compareTo(emp2.firstName);
                    return lastNameCheck != 0 ? lastNameCheck : firstNameCheck;
                }
                return 0;
            }
        });


        // COMPARABLE
        // now it is possible to use Comparator class static methods
        employees.sort(Comparator.reverseOrder());
        // another usage of the same concept, but in Collections class static method
        Collections.sort(employees, Comparator.naturalOrder()); // it just calls List.sort inside


//        removeUndesirables(employees, undesirables);
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
