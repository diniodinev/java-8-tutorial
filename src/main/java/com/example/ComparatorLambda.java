package com.example;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorLambda {
    public static void main(String[] args) {
        /*
         * Comparator<String> comp = new Comparator<String>() {
         * 
         * @Override public int compare(String o1, String o2) { // TODO
         * Auto-generated method stub return o1.compareTo(o2); } };
         */

        Comparator<String> comp = (String o1, String o2) -> {
            return o1.compareTo(o2);
        };

        String a[] = new String[] { "**", "*", "****", "***" };
        Arrays.sort(a, comp);

        for (String current : a) {
            System.out.println(current);
        }

    }
}
