package com.lingarogroup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BigData {
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
            int salaryIndex = Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/Hr5m.csv"))
                    .limit(1)
                    .map(line -> line.split(","))
                    .flatMap(Arrays::stream)
                    .toList()
                    .indexOf("Salary");

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

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
