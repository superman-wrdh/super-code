package cn.hc.io;

import cn.geneapps.xilong.ApplicationContext;
import cn.geneapps.xilong.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

/**
 * simple storage provider, store uploaded files into '_res' folder under web context path
 * @author timmy
 *
 */
public class WebLocalStorageProvider extends LocalStorageProviderBase {
	static final String CONFIG_STATIC_URL_ROOT = "storage.weblocal.staticUrlRoot";
	static final String CONFIG_PHYSIC_PATH_ROOT = "storage.weblocal.physicPathRoot";

	@Override
	public String getStorageType() {
		return "weblocal";
	}

	@Override
	protected String getUrl(String fileName, String param) {
		HttpServletRequest request = ApplicationContext.getCurrentContext().getRequest();
		if(StringUtils.isEmpty(fileName))
			return "";
		String staticUrlRoot = ApplicationContext.getConfig().getParam(CONFIG_STATIC_URL_ROOT, String.class);
		if(StringUtils.isEmpty(staticUrlRoot))
            return String.format("%s/_res/%s/%s", request.getContextPath(), param, fileName);
		else
			return String.format("%s/%s/%s", staticUrlRoot, param, fileName);
	}

	@Override
	protected LocalStorageConfiguration getConfigurationForNew() {
		String param = DateUtils.format(new Date(), "yyyyMM");

		return getConfiguration(param);
	}

	@Override
	protected LocalStorageConfiguration getConfiguration(String storageParam) {
		ServletContext ctx = ApplicationContext.getCurrentContext().getServletContext();
		String path = ApplicationContext.getConfig().getParam(CONFIG_PHYSIC_PATH_ROOT, String.class);
		
		if(StringUtils.isEmpty(path) || StringUtils.isEmpty(storageParam)) {
            path = String.format("%s/_res", ctx.getRealPath("/"));
            File dir = new File(path);
            if(!dir.exists())
                dir.mkdir();
            path = path +"/" + storageParam;
        }
		else
			path = String.format("%s/%s", path, storageParam);
		
		File file = new File(path);
		if(!file.exists())
			file.mkdir();
		LocalStorageConfiguration config = new LocalStorageConfiguration();
		config.setPhysicalPath(file.getAbsolutePath());
		config.setStorageParam(storageParam);
		return config;
	}

}
