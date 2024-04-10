package com.lingarogroup;

import java.time.LocalDate;

// interface extends Comparable generic for its own type to make use of Comparator.methods available
public interface IEmployee extends Comparable<IEmployee> {
    int getSalary();
    String getFirstName();
    String getLastName();
    LocalDate getDateOfBirth();
}
