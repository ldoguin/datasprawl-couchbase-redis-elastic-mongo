package org.couchbase.devex.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import org.couchbase.devex.BinaryStoreConfiguration;
import org.couchbase.devex.databases.api.CRUD;
import org.couchbase.devex.domain.StoredFile;
import org.couchbase.devex.domain.StoredFileDocument;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BinaryStoreService {

	public static String MIME_TYPE_PDF = "application/pdf";

	private BinaryStoreConfiguration configuration;

	private CRUD crud;

	private DataExtractionService dataExtractionService;

	private SHA1Service sha1Service;

	public BinaryStoreService(BinaryStoreConfiguration configuration, CRUD crud,
			DataExtractionService dataExtractionService, SHA1Service sha1Service) {
		this.configuration = configuration;
		this.crud = crud;
		this.dataExtractionService = dataExtractionService;
		this.sha1Service = sha1Service;
	}

	public StoredFile findFile(String digest) {
		File f = new File(configuration.getBinaryStoreRoot() + File.separator + digest);
		if (!f.exists()) {
			return null;
		}
		StoredFileDocument fileDoc = crud.read(digest);
		return new StoredFile(f, fileDoc);
	}

	public void deleteFile(String digest) {
		File f = new File(configuration.getBinaryStoreRoot() + File.separator + digest);
		if (!f.exists()) {
			throw new IllegalArgumentException("Can't delete file that does not exist");
		}
		f.delete();
		crud.delete(digest);
	}

	public void storeFile(String name, MultipartFile uploadedFile) {
		if (!uploadedFile.isEmpty()) {
			try {
				String digest = sha1Service.getSha1Digest(uploadedFile.getInputStream());
				File file2 = new File(configuration.getBinaryStoreRoot() + File.separator + digest);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file2));
				FileCopyUtils.copy(uploadedFile.getInputStream(), stream);
				stream.close();
				StoredFileDocument document = new StoredFileDocument();
				Map<String, Object> metadata = dataExtractionService.extractMetadata(file2);
				document.setBinaryStoreDigest(digest);
				document.setFileId(digest);
				document.setBinaryStoreLocation(name);
				document.setFileSize((Integer)metadata.get(StoredFileDocument.BINARY_STORE_METADATA_SIZE_PROPERTY));
				document.setBinaryStoreFilename(uploadedFile.getOriginalFilename());
				String mimeType = (String) metadata.get(StoredFileDocument.BINARY_STORE_METADATA_MIMETYPE_PROPERTY);
				document.setMIMEType(mimeType);
				if (MIME_TYPE_PDF.equals(mimeType)) {
					String fulltextContent = dataExtractionService.extractText(file2);
					document.setFulltext(fulltextContent);
				}
				document.setMetadata(metadata);
				crud.upsert(digest, document);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new IllegalArgumentException("File empty");
		}
	}

}
