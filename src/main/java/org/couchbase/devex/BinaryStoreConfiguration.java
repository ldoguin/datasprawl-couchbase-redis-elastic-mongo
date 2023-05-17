package org.couchbase.devex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinaryStoreConfiguration {

	@Value("${binaryStore.root:upload-dir}")
	private String binaryStoreRoot;

	@Value("${binaryStore.exiftool.path:C:\\Users\\Laurent Doguin\\scoop\\shims\\exiftool.exe}")
	private String exifToolPath;

	@Value("${binaryStore.pdfToText.path:/usr/bin/pdftotext}")
	private String pdfToText;

	public String getBinaryStoreRoot() {
		return binaryStoreRoot;
	}

	public String getExifToolPath() {
		return exifToolPath;
	}

	public String getPdfToText() {
		return pdfToText;
	}

}
