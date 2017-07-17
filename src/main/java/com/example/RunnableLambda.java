package com.example;

public class RunnableLambda {
    public static void main(String[] args) {
        // Runnable runable = new Runnable() {
        //
        // @Override
        // public void run() {
        // for (int i = 0; i < 3; i++) {
        // System.out.println("number " + i);
        // }
        // }
        // };

        Runnable runable = () -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("number " + i);
            }
        };
        Thread th1 = new Thread(runable);
        th1.start();
    }

}
