package com.example.tashev.images;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Main {

    public static void main(String[] args) throws IOException {
        Download tashev = new Download();
        List<String> allFiles = tashev.traverse(Arrays.asList(Download.baseUrlImages));

        allFiles.parallelStream().forEach(url -> {
            try {
                System.out.println(url.replaceAll("%20", ""));
                System.out.println("Saved into:");

                URL urlPath = new URL(url);
                String relativeFile = urlPath.getPath().substring(0, urlPath.getPath().lastIndexOf('/'));
                System.out.println(
                        "src/main/resources/" + relativeFile +"/"+ FilenameUtils.getName(url).replaceAll("%20", ""));
                FileUtils.copyURLToFile(new URL(url), new File(
                        "src/main/resources/" + relativeFile +"/"+ FilenameUtils.getName(url).replaceAll("%20", "")), 10000,
                        10000);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // System.out.println(basePage);

    }

}
