package com.lingarogroup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsExample {
    public static void main(String[] args) {
        String peopleList = """
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
            Flinstone, Fred, 1/1/1900, Programmerzzz, {locpd=2000,yoe=10,iq=140}
            Flinstone2, Fred2, 1/1/1900, Programmer, {locpd=1300,yoe=14,iq=100}
            Flinstone3, Fred3, 1/1/1900, Programmer, {locpd=2300,yoe=8,iq=105}
            Flinstone4, Fred4, 1/1/1900, Programmer, {locpd=1630,yoe=3,iq=115}
            Flinstone5, Fred5, 1/1/1900, Programmer, {locpd=5,yoe=10,iq=100}
            Rubble, Barney, 2/2/1905, Manager, {orgsize=300,dr=10}
            Rubble2, Barney2, 2/2/1905, Manager, {orgsize=100,dr=4}
            Rubble3, Barney3, 2/2/1905, Manager, {orgsize=200,dr=2}
            Rubble4, Barney4, 2/2/1905, Manager, {orgsize=500,dr=8}
            Rubble5, Barney5, 2/2/1905, Manager, {orgsize=175,dr=20}
            Flinstone, Wilma, 3/3/1910, Analyst, {projectCount=3}
            Flinstone2, Wilma2, 3/3/1910, Analyst, {projectCount=4}
            Flinstone3, Wilma3, 3/3/1910, Analyst, {projectCount=5}
            Flinstone4, Wilma4, 3/3/1910, Analyst, {projectCount=6}
            Flinstone5, Wilma5, 3/3/1910, Analyst, {projectCount=9}
            Rubble, Betty, 4/4/1915, CEO, {avgStockPrice=300}
            """;
        // 22 entries

        // stream on multiline string
        // creating employee object via method reference (if method accepts one input it's passed automatically because
        // previous pipeline step returns one output. In this case a single line
        // Then doing smthn for each of the outputs
        peopleList
                .lines()
                .map(Employee::createEmployee)
                .forEach((iEmployee -> System.out.println(iEmployee.getFirstName())));

        // stream from a List
        List<Integer> nums = List.of(1, 2, 3);
        nums
            .stream()
            .forEach(System.out::println);

        // stream created via Stream.of method (if nulls have to be filtered - use .ofNullable instead)
        // the method recognise type of elements and infer output type
        Stream.of("one", "two", "three")
            .map(String::hashCode)
            .map(Integer::toHexString)
            .forEach(System.out::println);

        // for numbers there are specific InstStream, DoubleStream, LongStream interfaces with specific math methods
        int sum = IntStream.rangeClosed(1, 20)
                .sum();

        // creating output of numbers with hyphen after: 1-2-3-4-5-
        IntStream ints = IntStream.rangeClosed(1, 5);
        // intStream output is of type IntStream, so it has to be converted
        // it may be done via mapToObj method
        ints
            .mapToObj(String::valueOf)
            .map(s -> s.concat("-"))
            .forEach(System.out::print);
        // or via boxed method in pipeline, which converts IntStream to Stream of Integers
        IntStream ints2 = IntStream.rangeClosed(1, 5);
        ints2
            .boxed()
            .map(String::valueOf)
            .map(s -> s.concat("-"))
            .forEach(System.out::print);

        // stram from an array
        String[] names = {"tom", "jerry"};
        Arrays.stream(names).map(String::toUpperCase).forEach(System.out::println);


        // streams on Files class
        try {
            Files.lines(Path.of("/home/tomasz_suski/projects/JAVA/course/Employees/data/employees.txt"))
                .map(Employee::createEmployee)
                .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
