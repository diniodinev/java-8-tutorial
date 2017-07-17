package com.example.tashev.images;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		Download tashev = new Download();
		List<String> allFiles = tashev.traverse(Arrays.asList(Download.baseUrlSrv));

		tashev.download(allFiles);
	}

}
