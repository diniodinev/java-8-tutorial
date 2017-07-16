package com.example;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.List;

import javax.print.StreamPrintServiceFactory;

import java.util.stream.*;

public class MapFlatMapTest {
    public static void main(String[] args) {
        List<Integer> first = Arrays.asList(new Integer[] { 1, 4 });
        List<Integer> second = Arrays.asList(new Integer[] { 34, 63, 4563 });
        List<Integer> third = Arrays.asList(new Integer[] { 232, 42, 2242423, 24 });

        List<List<Integer>> combine = Arrays.asList(first, second, third);

        System.out.println("Result");
        System.out.println(combine);

        System.out.println("Result map:");
        combine.stream().map(list -> list.size()).forEach(System.out::println);

        System.out.println("Result flat map:");

        combine.stream().flatMap(l -> l.stream()).forEach(System.out::println);

    }

}
