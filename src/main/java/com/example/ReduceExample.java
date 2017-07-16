package com.example;

import java.util.Optional;
import java.util.stream.Stream;

public class ReduceExample {
    public static void main(String[] args) {
        Stream<Integer> numbers = Stream.of(3,45,32,4,2345);
        //Without identity
        Optional<Integer> maximum = numbers.reduce(Integer::max);
        
        maximum.ifPresent(System.out::println);
        
    }

}
