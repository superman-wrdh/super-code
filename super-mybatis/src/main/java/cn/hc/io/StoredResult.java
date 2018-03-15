package cn.hc.io;

/**
 * stored result
 *
 * when file stored by storage provider, this class wrap the saving result for the file
 */
public final class StoredResult {
    private String storageParam;
    private long storedSize;
    private String extension;
    private String mimeType;

    public String getStorageParam() {
        return storageParam;
    }

    public void setStorageParam(String storageParam) {
        this.storageParam = storageParam;
    }

    public long getStoredSize() {
        return storedSize;
    }

    public void setStoredSize(long storedSize) {
        this.storedSize = storedSize;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}