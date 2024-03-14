package com.lingarogroup;

// interface extends Comparable generic for its own type to make use of Comparator.methods available
public interface IEmployee extends Comparable<IEmployee> {
    int getSalary();
}
