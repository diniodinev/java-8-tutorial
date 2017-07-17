package com.example.tashev.images;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Download {

    private static Log logger = LogFactory.getLog(Download.class);
    
    final static String baseUrlImages = "http://www.tashev-galving.com/images/";
    final static String baseUrlFiles = "http://www.tashev-galving.com/files/";
    final static String baseUrlSrv = "http://tashev-galving.com/srv/";
    final static File mainResourceDir = new File("src/main/resources/");

    final static String extensionSearchedForDownload = ".";

    public Document dowloadResource(String url) throws IOException {
        if (isValidUlr(url)) {
            return Jsoup.connect(url).get();
        }
        return null;
    }

    public boolean isValidUlr(String urlForCheck) {
        try {
            if (!urlForCheck.startsWith("http")) {
                return false;
            }
            URL url = new URL(urlForCheck);
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public List<String> traverse(List<String> urls) {

        Stream<String> linksFromDirectories = traverseDir(urls);
        return linksFromDirectories.parallel().collect(Collectors.toList());

    }

    public Stream<String> traverseDir(List<String> directories) {
        if (directories.size() == 0) {
            return Stream.empty();
        }
        Predicate<String> isDir = url -> url.toString().endsWith("/");
        Predicate<String> isDirectUrl = isDir.negate();

        Supplier<Stream<String>> streamSupplier = () -> directories.parallelStream()
                .flatMap((String dir) -> exctractUrls(getUrls(dir, extensionSearchedForDownload), dir));

        List<String> directoriesForTraversal = streamSupplier.get().filter(isDir).collect(Collectors.toList());
        Stream<String> directUrlTraversal = streamSupplier.get().filter(isDirectUrl);

        return Stream.concat(directUrlTraversal, traverseDir(directoriesForTraversal));
    }

    public Stream<Element> getUrls(String baseUrlForChecking, String fileExtansionToCheck) {
        if (fileExtansionToCheck == null) {
            fileExtansionToCheck = ".jpg";
        }
        Document basePage = null;
        try {
            basePage = dowloadResource(baseUrlForChecking);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Predicate<Element> containsExtension = element -> element.text().contains(extensionSearchedForDownload);
        Predicate<Element> endsWIthSlash = element -> element.text().endsWith("/");

        return basePage.getElementsByAttribute("href").stream().filter(containsExtension.or(endsWIthSlash));

    }

    public Stream<String> exctractUrls(Stream<Element> htmlContant, String baseUrlForChecking) {
        return htmlContant.map(link -> baseUrlForChecking + link.text().replaceAll(" ", "%20"))
                .filter(link -> isValidUlr(link));
    }

    public void download(List<String> filesToDownload) {
        Function<String, String> identityGroupByDirectoryPaths = element -> {
            URL urlPath = null;
            try {
                urlPath = new URL(element);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return urlPath.getPath().substring(0, urlPath.getPath().lastIndexOf('/'));
        };

        Map<String, List<String>> elements = filesToDownload.stream()
                .collect(Collectors.groupingBy(identityGroupByDirectoryPaths, Collectors.toList()));

        elements.entrySet().stream().forEach(entry -> {

            cleanIfFull(entry.getKey(), 10 * FileUtils.ONE_MB);

            saveFileToDisk(entry);
        });
    }

    private void saveFileToDisk(Entry<String, List<String>> entry) {
        entry.getValue().parallelStream().forEach(url -> {
            try {
                FileUtils.copyURLToFile(new URL(url), new File(mainResourceDir + entry.getKey(),
                        FilenameUtils.getName(url).replaceAll("%20", "")), 10000, 10000);
            } catch (IOException e) {
                logger.debug(e);
            }
        });
    }

    private void cleanIfFull(String endingPath, long size) {
        File resourceDir = new File(mainResourceDir + endingPath);
        if (resourceDir.exists() && resourceDir.canExecute() && FileUtils.sizeOfDirectory(mainResourceDir) > size) {
            try {
                FileUtils.deleteDirectory(mainResourceDir);
            } catch (IOException e) {
                logger.debug(e);
            }
        }
    }
}
