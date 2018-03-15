package cn.hc.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.UUID;

/**
 * location storage provider base class
 *
 * provide basic logic for local system file system storage
 */
public abstract class LocalStorageProviderBase implements StorageProvider {
	private static final Log log = LogFactory.getLog(LocalStorageProviderBase.class);
	
	/**
	 * save resource to storage
	 * @param id resource id
	 * @param name resource name (file name)
	 * @param inputStream resource stream
	 * @return storage parameter
	 */
	public StoredResult store(UUID id, String name, InputStream inputStream) {
		String fileName = getFileName(id, name);
		LocalStorageConfiguration config = getConfigurationForNew();

        long size = 0;
		try {
			try(FileOutputStream output = new FileOutputStream(String.format("%s/%s", config.getPhysicalPath(),fileName))) {
				byte[] buffer = new byte[4096];
				int read = 0;
				while((read=inputStream.read(buffer, 0, buffer.length))>0) {
					output.write(buffer, 0, read);
					size+=read;
				}
				output.flush();
			}
		} catch(Exception e) {
			log.error(e);
			throw new StorageException(String.format("error to store resource '%s'", name), e);
		}
        StoredResult result = new StoredResult();
		result.setStorageParam(config.getStorageParam());
        result.setStoredSize(size);

		int pos = fileName.lastIndexOf('.');
		if(pos>0 && pos<fileName.length()-1)
			result.setExtension(fileName.substring(pos+1));

        return result;
	}
	
	/**
	 * delete resource from storage
	 * @param id resource id
	 * @param name resource name (file name)
	 * @param param storage parameter
	 */
	public void delete(UUID id, String name, String param) {
		String fileName = getFileName(id, name);
		LocalStorageConfiguration config = getConfiguration(param);
		File file = new File(String.format("%s\\%s", config.getPhysicalPath(),fileName));
		if(file.exists())
			file.delete();
	}
	
	/**
	 * read resource from storage
	 * @param id resource id
	 * @param name resource name
	 * @param param storage parameter
	 * @return input stream from storage
	 */
	public InputStream read(UUID id, String name, String param) {
		String fileName = getFileName(id, name);
		LocalStorageConfiguration config = getConfiguration(param);
		File file = new File(String.format("%s//%s", config.getPhysicalPath(),fileName));
		if(!file.exists())
			return null;
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			log.error(e);
			throw new StorageException(String.format("error to read resource '%s'", name), e);
		}
	}
	
	/**
	 * get resource URL
	 * @param id resource id
	 * @param name resource name
	 * @param param storage parameter
	 * @return resource url
	 */
	public String getUrl(UUID id, String name, String param) {
		return getUrl(getFileName(id, name), param);
	}
	
	/**
	 * get actual file name in file system
	 * @param id resource id
	 * @param name original resource file name
	 * @return resource actual file name
	 */
	private String getFileName(UUID id, String name) {
		if(id==null || name==null)
			return null;
		int pos = name.lastIndexOf('.');
		if(pos<0 || pos == name.length()-1)
			return id.toString();
		return String.format("%s.%s", id.toString(), name.substring(pos+1));
	}
	
	/**
	 * get URL for resource
	 * @param fileName resource file name
	 * @param param storage parameter
	 * @return resource url
	 */
	protected abstract String getUrl(String fileName, String param);
	
	/**
	 * get configuration for new resource
	 * @return configuration
	 */
	protected abstract LocalStorageConfiguration getConfigurationForNew();
	
	/**
	 * get configuration for specific storage parameter
	 * @param storageParam storage parameter
	 * @return configuration
	 */
	protected abstract LocalStorageConfiguration getConfiguration(String storageParam);
}