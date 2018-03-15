package cn.hc.http.html;

import cn.hc.http.file.HttpsGetData;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetHtml {
    public static void main(String[] args) {
        try {
            System.out.println(getHtml("http://tieba.baidu.com/p/5240386486?red_tag=t1898017796"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHtml(String urlStr)throws Exception{
        BufferedReader in = null;
        String result = "";
        URL realUrl = new URL(urlStr);
        HttpsURLConnection httpConnection = (HttpsURLConnection) realUrl.openConnection();
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.connect();
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            //result =result +"\n"+ line;

        }
        return result;
    }

    public String getImgAttr(String input){
        Pattern p = Pattern.compile("<img|IMG");
        Matcher matcher = p.matcher(input);
        while (matcher.find()){
            return matcher.group();
        }
        return null;
    }
}
