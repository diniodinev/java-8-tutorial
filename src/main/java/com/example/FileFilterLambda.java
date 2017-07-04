package com.example;

import java.io.File;
import java.io.FileFilter;

/**
 * Hello world!
 *
 */
public class FileFilterLambda {
    public static void main(String[] args) {
        FileFilter simpleFilter = (pathname) -> pathname.getName().endsWith(".txt");
        // FileFilter simpleFilter = new FileFilter() {
        //
        // @Override
        // public boolean accept(File pathname) {
        // return pathname.getName().endsWith(".txt");
        // }
        // };

        File cDir = new File("C:");
        for (File a : cDir.listFiles(simpleFilter)) {
            System.out.println(a.getAbsolutePath());
        }
    }
}
