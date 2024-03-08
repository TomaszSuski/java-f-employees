package com.lingarogroup;

public interface Chef {
    default void cook(String food) {
        System.out.println("I'm now cooking " + food);
    }

    default String cleanup() {
        return "It's clean now";
    }
}
