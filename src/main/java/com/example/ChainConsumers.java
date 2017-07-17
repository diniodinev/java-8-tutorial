package com.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ChainConsumers {
    public static void main(String[] args) {
        List<String> words = Arrays.asList(new String[] { "one", "cow", "tree" });
        List<String> second = new LinkedList<>();

        Consumer<String> wordsConsumer = System.out::println;
        Consumer<String> secondConsumer = second::add;

        words.forEach(wordsConsumer.andThen(secondConsumer));
        second.forEach(wordsConsumer);

        System.out.println("______________________");
        List<String> filteredWords = words.stream().filter(s -> s.length() > 3).collect(Collectors.toList());
        filteredWords.stream().forEach(System.out::println);

        // Reduce
        System.out.println("______________________Reduce______________________");

        words.stream().reduce((s1, s2) -> s1.length() >= s2.length() ? s1 : s2).ifPresent(System.out::println);

    }

}
