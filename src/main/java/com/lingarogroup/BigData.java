package com.lingarogroup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BigData {

    record Person(String firstName, String lastName, long salary, String state) {}

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
                    .map(a -> new Person(a[firstNameIndex], a[lastNameIndex], Long.parseLong(a[salaryIndex]), a[stateIndex]))
                    .collect(Collectors.summingLong(Person::salary));
            long endTime4 = System.currentTimeMillis();

            System.out.printf("$%,d.00 calculated in %d%n",personSalarySum, endTime4 - startTime4);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
