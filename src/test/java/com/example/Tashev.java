package com.example;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.example.tashev.images.Main;

public class Tashev {

    @Test
    public void testMain() throws IOException {
        Main main = new Main();
        main.main(new String[]{});
        assertTrue( true );
    }

}
