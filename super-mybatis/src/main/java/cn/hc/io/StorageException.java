package cn.hc.io;

/**
 * storage exception
 */
public class StorageException extends RuntimeException {
    public StorageException() {
        super();
    }

    public StorageException(String msg) {
        super(msg);
    }

    public StorageException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
