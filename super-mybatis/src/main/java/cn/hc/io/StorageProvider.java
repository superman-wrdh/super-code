package cn.hc.io;

import java.io.InputStream;
import java.util.UUID;

/**
 * storage provider interface
 * @author timmy
 *
 */
public interface StorageProvider {
	/**
	 * save resource to storage
	 * @param id resource id
	 * @param name resource name (file name)
	 * @param inputStream resource stream
	 * @return storage parameter
	 */
	StoredResult store(UUID id, String name, InputStream inputStream);
	
	/**
	 * delete resource from storage
	 * @param id resource id
	 * @param name resource name (file name)
	 * @param param storage parameter
	 */
	void delete(UUID id, String name, String param);
	
	/**
	 * read resource from storage
	 * @param id resource id
	 * @param name resource name
	 * @param param storage parameter
	 * @return
	 */
	InputStream read(UUID id, String name, String param);
	
	/**
	 * get resource URL
	 * @param id resource id
	 * @param name resource name
	 * @param param storage parameter
	 * @return
	 */
	String getUrl(UUID id, String name, String param);
	
	/**
	 * get storage type
	 * @return type
	 */
	String getStorageType();
}