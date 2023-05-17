package org.couchbase.devex.domain;

import java.io.Serializable;
import java.util.Map;

public class StoredFileDocument implements Serializable {
    
	private static final long serialVersionUID = 128943L;

	private String fileId;

	private String binaryStoreDigest;

	private String binaryStoreLocation;

	private String binaryStoreFilename;

	private Integer FileSize;

	private String MIMEType;

	private String fulltext;

	private Map<String, Object> metadata;

	public static String BINARY_STORE_DIGEST_PROPERTY = "binaryStoreDigest";

	public static String BINARY_STORE_LOCATION_PROPERTY = "binaryStoreLocation";

	public static String BINARY_STORE_FILENAME_PROPERTY = "binaryStoreFilename";

	public static String BINARY_STORE_METADATA_SIZE_PROPERTY = "FileSize";

	public static String BINARY_STORE_METADATA_MIMETYPE_PROPERTY = "MIMEType";

	public static String BINARY_STORE_METADATA_FULLTEXT_PROPERTY = "fulltext";

	public static String STORED_FILE_DOCUMENT_TYPE = "file";

	public static String COLLECTION_NAME = "file";


	// private JsonObject content;

	// public StoredFileDocument(JsonObject doc) {
	// 	this.content = doc;
	// }


	
	public String getMimeType() {
		// String mimeType = content.getString(BINARY_STORE_METADATA_MIMETYPE_PROPERTY);
		if (MIMEType == null) {
			MIMEType = "application/octet-stream";
		}
		return MIMEType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Integer getSize() {
		// return content.getInt(BINARY_STORE_METADATA_SIZE_PROPERTY);
		return FileSize;
	}

	public String getBinaryStoreLocation() {
		// return content.getString(BINARY_STORE_LOCATION_PROPERTY);
		return binaryStoreLocation;
	}

	public String getBinaryStoreFilename() {
		// return content.getString(BINARY_STORE_FILENAME_PROPERTY);
		return binaryStoreFilename;
	}

	public String getBinaryStoreDigest() {
		// return content.getString(BINARY_STORE_DIGEST_PROPERTY);
		return binaryStoreDigest;
	}

	public String getType() {
		// return COUCHBASE_STORED_FILE_DOCUMENT_TYPE;
		return STORED_FILE_DOCUMENT_TYPE;
	}

	public void setBinaryStoreDigest(String binaryStoreDigest) {
		this.binaryStoreDigest = binaryStoreDigest;
	}

	public void setBinaryStoreLocation(String binaryStoreLocation) {
		this.binaryStoreLocation = binaryStoreLocation;
	}

	public void setBinaryStoreFilename(String binaryStoreFilename) {
		this.binaryStoreFilename = binaryStoreFilename;
	}

	public Integer getFileSize() {
		return FileSize;
	}

	public void setFileSize(Integer fileSize) {
		FileSize = fileSize;
	}

	public String getMIMEType() {
		return MIMEType;
	}

	public void setMIMEType(String mIMEType) {
		MIMEType = mIMEType;
	}

	public String getFulltext() {
		return fulltext;
	}

	public void setFulltext(String fulltext) {
		this.fulltext = fulltext;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		result = prime * result + ((binaryStoreDigest == null) ? 0 : binaryStoreDigest.hashCode());
		result = prime * result + ((binaryStoreLocation == null) ? 0 : binaryStoreLocation.hashCode());
		result = prime * result + ((binaryStoreFilename == null) ? 0 : binaryStoreFilename.hashCode());
		result = prime * result + ((FileSize == null) ? 0 : FileSize.hashCode());
		result = prime * result + ((MIMEType == null) ? 0 : MIMEType.hashCode());
		result = prime * result + ((fulltext == null) ? 0 : fulltext.hashCode());
		result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoredFileDocument other = (StoredFileDocument) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		if (binaryStoreDigest == null) {
			if (other.binaryStoreDigest != null)
				return false;
		} else if (!binaryStoreDigest.equals(other.binaryStoreDigest))
			return false;
		if (binaryStoreLocation == null) {
			if (other.binaryStoreLocation != null)
				return false;
		} else if (!binaryStoreLocation.equals(other.binaryStoreLocation))
			return false;
		if (binaryStoreFilename == null) {
			if (other.binaryStoreFilename != null)
				return false;
		} else if (!binaryStoreFilename.equals(other.binaryStoreFilename))
			return false;
		if (FileSize == null) {
			if (other.FileSize != null)
				return false;
		} else if (!FileSize.equals(other.FileSize))
			return false;
		if (MIMEType == null) {
			if (other.MIMEType != null)
				return false;
		} else if (!MIMEType.equals(other.MIMEType))
			return false;
		if (fulltext == null) {
			if (other.fulltext != null)
				return false;
		} else if (!fulltext.equals(other.fulltext))
			return false;
		if (metadata == null) {
			if (other.metadata != null)
				return false;
		} else if (!metadata.equals(other.metadata))
			return false;
		return true;
	}
	
}
