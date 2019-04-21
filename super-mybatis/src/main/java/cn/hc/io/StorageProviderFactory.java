//package cn.hc.io;
//
//import cn.geneapps.xilong.ApplicationContext;
//import cn.geneapps.xilong.ApplicationException;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * storage provider factory
// * @author timmy
// *
// */
//public final class StorageProviderFactory {
//	public static final String PARAM_STORAGE_PROVIDERS = "storage.providers";
//	public static final String PARAM_DEFAULT_STORAGE_PROVIDER = "storage.provider.default";
//
//	private static Map<String, StorageProvider> storageProviders;
//	private static Object lock = new Object();
//
//	/**
//	 * read configurations
//	 */
//	private void readConfigurations() {
//		synchronized (lock) {
//			if(storageProviders==null)
//				storageProviders = new HashMap<String, StorageProvider>();
//			String providers = ApplicationContext.getConfig().getParam(PARAM_STORAGE_PROVIDERS, String.class);
//
//			if(StringUtils.isBlank(providers))
//				throw new ApplicationException("no storage provider configured.");
//
//			for(String provider:providers.split(","))
//				initProvider(provider);
//		}
//	}
//
//	/**
//	 * init provider type
//	 * @param name
//	 */
//	private void initProvider(String name) {
//		String typeParam = String.format("storage.%s.provider", name);
//
//		Class clz = ApplicationContext.getConfig().getParam(typeParam, Class.class);
//
//		if(!StorageProvider.class.isAssignableFrom(clz))
//			throw new ApplicationException("storage provider class must implement StorageProvider interface");
//
//		try {
//			storageProviders.put(name, (StorageProvider)clz.newInstance());
//		} catch (InstantiationException | IllegalAccessException e) {
//			throw new ApplicationException("unable to create storage provider", e);
//		}
//	}
//
//	/**
//	 * get storage provider
//	 * @return
//	 */
//	public StorageProvider getDefaultStorageProvider() {
//		return getStorageProvider(ApplicationContext.getConfig().getParam(PARAM_DEFAULT_STORAGE_PROVIDER, String.class));
//	}
//
//	/**
//	 * get storage provider
//	 * @return
//	 */
//	public StorageProvider getStorageProvider(String storageType) {
//		if(storageProviders==null)
//			readConfigurations();
//		synchronized (lock) {
//			if(!storageProviders.containsKey(storageType))
//				throw new ApplicationException(String.format("storage type '%s' is not configured.", storageType));
//			return storageProviders.get(storageType);
//		}
//	}
//}