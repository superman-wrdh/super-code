package cn.hc.io;

/**
 * local storage configuration
 * @author xliu6
 *
 */
public final class LocalStorageConfiguration {
	private String storageParam;
	private String physicalPath;
	
	/**
	 * get storage parameter
	 * @return
	 */
	public String getStorageParam() {
		return storageParam;
	}
	/**
	 * set storage parameter
	 * @param storageParam storage parameter
	 */
	public void setStorageParam(String storageParam) {
		this.storageParam = storageParam;
	}
	/**
	 * get physical path
	 * @return
	 */
	public String getPhysicalPath() {
		return physicalPath;
	}
	/**
	 * set physical path
	 * @param physicalPath physical path
	 */
	public void setPhysicalPath(String physicalPath) {
		this.physicalPath = physicalPath;
	}
	
}