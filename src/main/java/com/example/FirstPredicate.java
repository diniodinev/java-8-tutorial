package com.example;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class FirstPredicate {
    public static void main(String[] args) {
        Stream<String> numberStream = Stream.of("one", "two", "three", "four", "five");

        Predicate<String> lengthPredicateMoreThan3 = s1 -> s1.length() > 3;
        Predicate<String> lengthPredicateLessThan5 = s1 -> s1.length() < 5;
        numberStream.filter(lengthPredicateMoreThan3.and(lengthPredicateLessThan5)).forEach(System.out::println);
        
    }

}
