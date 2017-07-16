package com.example;

import java.io.IOException;

import org.junit.Test;

import com.example.tashev.images.Main;

import junit.framework.Assert;

public class Tashev {

    @Test
    public void testMain() throws IOException {
        Main main = new Main();
        main.main(new String[]{});
        Assert.assertTrue(true);
    }

}
