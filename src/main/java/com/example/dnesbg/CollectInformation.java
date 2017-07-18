package com.example.dnesbg;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;

import com.example.tashev.images.Download;

public class CollectInformation {

    private static Log logger = LogFactory.getLog(Download.class);

    final static String baseUrlImages = "http://www.dnes.bg/redakcia/2017/07/18/a.";

    public void traversePages(int from, int to) {
        // 347607
        List<Document> allPages = new LinkedList<>();
        IntStream.rangeClosed(from, to).parallel().forEach(i -> {
            Document currentDoc = Download.dowloadResource(baseUrlImages + i);
            if (currentDoc != null) {
                allPages.add(currentDoc);
            }
        });
        System.out.println(allPages.size());
    }
    
}
