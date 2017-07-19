package com.example.dnesbg;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;

import com.example.tashev.images.Download;

public class DownloadImages {

    private static Log logger = LogFactory.getLog(Download.class);

   // final static String baseUrlImages = "http://85.14.28.164/d/images/photos/0347/0000";
    final static String baseUrlImages = " http://85.14.28.164/d/images/slideshows/00000";
      final static String suffix = "-middle.jpg";
    // http://85.14.28.164/d/images/photos/0347/0000347608-article2.jpg

    public void downloadImages(int from, int to) {
        // 347607
        List<Document> allPages = new LinkedList<>();
        IntStream.rangeClosed(from, to).parallel().forEach(i -> {
            try {
                Download.saveFile(baseUrlImages + i + suffix, "");
                logger.trace(baseUrlImages + i + suffix);
            } catch (IOException e) {
                //logger.debug(e);
            }
        });

    }
}
