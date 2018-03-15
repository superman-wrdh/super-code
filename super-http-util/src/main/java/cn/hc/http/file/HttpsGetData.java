package cn.hc.http.file;

/**
 * Created by hc on 2017/5/12.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpsGetData {
    private static Log logger = LogFactory.getLog(HttpsGetData.class);

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    String _url="";

    Map<String,String> _params;

    public  HttpsGetData(){

    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public void set_params(Map<String, String> _params) {
        this._params = _params;
    }

    public HttpsGetData(String url, Map<String,String> keyValueParams) {
        this._url=url;
        this._params=keyValueParams;
    }
    public String Do() throws Exception {
        String result = "";
        BufferedReader in = null;
        try {

            String urlStr = this._url + "&" + getParamStr();
            System.out.println("GET请求的URL为："+urlStr);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                    new java.security.SecureRandom());
            URL realUrl = new URL(urlStr);
            // 打开和URL之间的连接
            HttpsURLConnection connection = (HttpsURLConnection) realUrl.openConnection();
            //设置https相关属性
            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            connection.setDoOutput(true);

            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            logger.info("get data"+result);
            //System.out.println("获取的结果为："+result);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            //e.printStackTrace();
            throw e;
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                //e2.printStackTrace();
                throw e2;
            }
        }
        return result;

    }

    private String getParamStr(){

        String paramStr="";
        // 获取所有响应头字段
        Map<String, String> params = this._params;
        // 获取参数列表组成参数字符串
        for (String key : params.keySet()) {
            paramStr+=key+"="+params.get(key)+"&";
        }
        //去除最后一个"&"
        paramStr=paramStr.substring(0, paramStr.length()-1);

        return paramStr;
    }

    public List<String> toList(String s){
        s=s.replace("[","").replace("]","");
        s=s.replace("\"","");
        String[] arr=s.split(",");
        List<String> strings = Arrays.asList(arr);
        List<String> list=new ArrayList<>();
        for (String str : strings) {
            list.add(str.trim());
        }
        return list;
    }


}
