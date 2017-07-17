package com.example.tashev.images;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Download {

	final static String baseUrlImages = "http://www.tashev-galving.com/images/";
	final static String baseUrlFiles = "http://www.tashev-galving.com/files/";
	final static String baseUrlSrv = "http://tashev-galving.com/srv/";

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
		filesToDownload.parallelStream().forEach(url -> {
			try {
				URL urlPath = new URL(url);
				String relativeFile = urlPath.getPath().substring(0, urlPath.getPath().lastIndexOf('/'));

				File resourceDir = new File("src/main/resources/" + relativeFile);
				if (resourceDir.exists() && FileUtils.sizeOfDirectory(resourceDir) > 1 * FileUtils.ONE_MB) {
					FileUtils.deleteDirectory((new File("src/main/resources/" + relativeFile)));
				}
				System.out.println(FilenameUtils.getName(url).replaceAll("%20", ""));
				FileUtils.copyURLToFile(new URL(url), new File(
						"src/main/resources/" + relativeFile + "/" + FilenameUtils.getName(url).replaceAll("%20", "")),
						10000, 10000);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
