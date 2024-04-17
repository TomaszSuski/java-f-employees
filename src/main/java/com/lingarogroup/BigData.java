package com.lingarogroup;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BigData {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

    record Person(
            String firstName,
            String lastName,
            long salary,
            String state,
            char gender,
            BigDecimal salaryBD,
            LocalDate birthDate,
            LocalTime birthTime
    ) {
        long getAge() {
//            return LocalDate.now().getYear() - birthDate.getYear();       // just math
            return Period.between(birthDate, LocalDate.now()).getYears();  // using Period class
        }

        String getDOBAsText() {
            return dateFormatter.format(birthDate);
        }
    }

    public static void main(String[] args) {
        try {
            // counting using stream count method
//            long count = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
//                    .skip(1)
//                    .count();
//            System.out.println(count);

            // counting using collectors framework
//            long count2 = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
//                    .skip(1)
//                    .collect(Collectors.counting());
//            System.out.println(count2);

            // summing all salary fields
            // first finding index of Salary
            // and other fields
            List<String> columns = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .limit(1)
                    .map(str -> str.toLowerCase())
                    .map(line -> line.split(","))
                    .flatMap(Arrays::stream)
                    .toList();

            int salaryIndex = columns.indexOf(("salary"));
            int firstNameIndex = columns.indexOf("first name");
            int lastNameIndex = columns.indexOf("last name");
            int stateIndex = columns.indexOf("state");
            int genderIndex = columns.indexOf("gender");
            int dobIndex = columns.indexOf("date of birth");
            int tobIndex = columns.indexOf("time of birth");

            //then sum
            long startTime = System.currentTimeMillis();
            long salarySum = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(arr -> arr[salaryIndex])
                    .collect(Collectors.summingLong(sal -> Long.parseLong(sal)));
            long endTime = System.currentTimeMillis();

            System.out.printf("$%,d.00 calculated in %d%n",salarySum, endTime - startTime);

            // checking speed of other approach
            long startTime2 = System.currentTimeMillis();
            long salarySum2 = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(arr -> arr[salaryIndex])
                    .mapToLong(sal -> Long.parseLong(sal))
                    .sum();
            long endTime2 = System.currentTimeMillis();

            System.out.printf("$%,d.00 calculated in %d%n",salarySum2, endTime2 - startTime2);

            // both ways are around 4-5 seconds.
            // now applying parallel stream
            long startTime3 = System.currentTimeMillis();
            long salarySum3 = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .parallel()
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(arr -> arr[salaryIndex])
                    .mapToLong(sal -> Long.parseLong(sal))
                    .sum();
            long endTime3 = System.currentTimeMillis();

            System.out.printf("$%,d.00 calculated in %d%n",salarySum3, endTime3 - startTime3);
            // this time it's about 2 seconds


//            =======================================
            // now instantiating every line as record of Person
            long startTime4 = System.currentTimeMillis();
            Long personSalarySum = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .parallel()
                    .skip(1)
                    .map(l -> l.split(","))
                    .map(createPerson(firstNameIndex, lastNameIndex, salaryIndex, stateIndex, genderIndex, dobIndex, tobIndex))
                    .collect(Collectors.summingLong(Person::salary));
            long endTime4 = System.currentTimeMillis();

            System.out.printf("$%,d.00 calculated in %d%n",personSalarySum, endTime4 - startTime4);
            System.out.println();


//            =======================================
            // using more complex Collectors possibilities

            // grouping alphabetised using treeMap as the MapFactory and toList method
            Map<String, List<Person>> PersonsByState = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .parallel()
                    .skip(1)
                    .map(l -> l.split(","))
                    .map(createPerson(firstNameIndex, lastNameIndex, salaryIndex, stateIndex, genderIndex, dobIndex, tobIndex))
                    .collect(Collectors.groupingBy(Person::state, TreeMap::new, Collectors.toList()));

            // summing salaries by states
            TreeMap<String, String> salariesByState = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .parallel()
                    .skip(1)
                    .map(l -> l.split(","))
                    .map(createPerson(firstNameIndex, lastNameIndex, salaryIndex, stateIndex, genderIndex, dobIndex, tobIndex))
                    .collect(Collectors.groupingBy(Person::state,
                            TreeMap::new,
                            Collectors.collectingAndThen(Collectors.summingLong(Person::salary),
//                                    s -> String.format("$%,d.00%n", s))));   ---- formatting using String.format
                                    NumberFormat.getCurrencyInstance()::format)));  // formatting using numberformat

            System.out.println(salariesByState);
            System.out.println();

            // averaging salaries by state and gender
            Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .parallel()
                    .skip(1)
                    .map(l -> l.split(","))
                    .map(createPerson(firstNameIndex, lastNameIndex, salaryIndex, stateIndex, genderIndex, dobIndex, tobIndex))
                    .collect(
                            Collectors.groupingBy(Person::state, TreeMap::new,
                                    Collectors.groupingBy(Person::gender,
                                                Collectors.collectingAndThen(
                                                        Collectors.averagingLong(Person::salary),
                                                        NumberFormat.getCurrencyInstance()::format
                                                ))
                            ))
                    .forEach((state, map) -> {
                        System.out.printf("salaries in %s:%n", state);
                        map.forEach((gender, salary) -> System.out.printf("%s -> %s%n", gender, salary));
                    });
            System.out.println();

            // averaging salaries by state and gender BUT using BigDecimal for accuracy of money math
            Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .parallel()
                    .skip(1)
                    .map(l -> l.split(","))
                    .map(createPerson(firstNameIndex, lastNameIndex, salaryIndex, stateIndex, genderIndex, dobIndex, tobIndex))
                    .collect(
                            Collectors.groupingBy(Person::state, TreeMap::new,
                                    Collectors.groupingBy(Person::gender,
                                            Collectors.collectingAndThen(
                                                    Collectors.reducing(BigDecimal.ZERO, Person::salaryBD, BigDecimal::add),
                                                    NumberFormat.getCurrencyInstance()::format
                                            ))
                            ))
                    .forEach((state, map) -> {
                        System.out.printf("salaries in %s:%n", state);
                        map.forEach((gender, salary) -> System.out.printf("%s -> %s%n", gender, salary));
                    });
            System.out.println();



            // partitioningBy - works as a conduct of filter and collect - filter "loses" not matching elements
            // this method creates a map with exactly two key-value pairs. One value is a list of matching elements
            // and the other with nto matching elements
            Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .parallel()
                    .skip(1)
                    .map(l -> l.split(","))
                    .map(createPerson(firstNameIndex, lastNameIndex, salaryIndex, stateIndex, genderIndex, dobIndex, tobIndex))
                    .collect(
//                            Collectors.partitioningBy(p -> p.gender() == 'F')  // this creates keys of true and false and inserts proper values
                            // returns Map<Boolean, List<Person>>
//                            Collectors.partitioningBy(p -> p.gender() == 'F', Collectors.counting()) // this counts all values in keys
                            // returns Map<Boolean, Long>
                            // second parameter accepts any collector method
                            Collectors.partitioningBy(p->p.gender()=='F', Collectors.groupingBy(Person::state, Collectors.counting())))
                            // above partitions by gender, then group both lists by state and counts. return value Map<Boolean, Map<String, Long>>
                    .forEach((bool, map) -> {
                        String gender = bool ? "Women" : "Men";
                        System.out.printf("Total number of %s by states:%n", gender);
                        map.forEach((state, count) -> System.out.printf("%s -> %d%n", state, count));
                        System.out.println();
                    });
            System.out.println();



            // printing 100 records of peoples first name, last name, date of birth and age
            Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .parallel()
                    .skip(1)
                    .limit(100)
                    .map(l -> l.split(","))
                    .map(createPerson(firstNameIndex, lastNameIndex, salaryIndex, stateIndex, genderIndex, dobIndex, tobIndex))
                    .forEach(p -> System.out.printf("%s %s born: %s - age: %d%n", p.firstName(), p.lastName(), p.getDOBAsText(), p.getAge()));
            System.out.println();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Function<String[], Person> createPerson(int firstNameIndex,
                                                           int lastNameIndex,
                                                           int salaryIndex,
                                                           int stateIndex,
                                                           int genderIndex,
                                                           int dobIndex,
                                                           int tobIndex) {
        return a -> new Person(
                a[firstNameIndex],
                a[lastNameIndex],
                Long.parseLong(a[salaryIndex]),
                a[stateIndex], a[genderIndex].strip().charAt(0),
                new BigDecimal(a[salaryIndex]),
                LocalDate.parse(a[dobIndex], dateFormatter),  // this is the format of date in the file
                // so parsing needs second parameter of DateTimeFormatter with proper format
                LocalTime.parse(a[tobIndex], timeFormatter)  // this is the format of time in the file
                // so parsing needs second parameter of DateTimeFormatter with proper format (a is for am/pm)
        );
    }
}
