package cn.hc.http.img;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhotoHome {
    static int num = 0;
    static int saveimg = 0;
    public static void main(String[] args) throws Exception{
        String url = "http://www.tupianzj.com/meinv/xiezhen/";
        for (int i = 83; i <= 85; i++) {
            Set<String> urx = new HashSet<String>();
            Set<String> TrueSrcUrl = new HashSet<String>();
            String html = "";
            if (i==0) {
                html = getdizhi(url);
            }else {
                html = getdizhi(url+"list_179_"+i+".html");
            }
            urx = getAHref(html);
            System.out.println("-----------------------------------------"+i+"----------------------------------------");
            for (String page : urx ) {
                int k = getPage(page);
                for (int u = 1; u <= k; u++) {
                    if (u==1) {
                        String shurl = page;
                        TrueSrcUrl.add(getJpgSrc(shurl));
                    }else {
                        String shurl = page.replaceAll(".html", "_"+u+".html");
                        TrueSrcUrl.add(getJpgSrc(shurl));
                    }
                }
                saveImg(TrueSrcUrl, i);
                TrueSrcUrl.clear();
            }
            System.out.println("---------------------------本次查询到："+TrueSrcUrl.size()+"：张---------------------------");
			for (String string :TrueSrcUrl) {
				System.out.println(string);
			}
        }
        System.out.println("\n本次一共下载： "+num+" 张图片。");
    }
    public static String getdizhi(String url) throws Exception{
        StringBuilder sb = new StringBuilder();
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"gb2312"));
        String line = null;
        while((line = reader.readLine()) != null)
            sb.append(line +"\n");
        //System.out.println(sb);
        return sb.toString();
    }
    public static Set<String> getAHref(String html) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<a href=.*target");
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            list.add(matcher.group());
            //System.out.println(matcher.group());
        }

        Set<String> list2 = new HashSet<String>();
        for (String shtml : list) {
            if (!(shtml.contains("http://")||shtml.contains("mingxing"))) {
                String Surl = shtml.substring(9, shtml.length()-8);
                list2.add("http://www.tupianzj.com"+Surl);
            }
        }
        return list2;
    }
    public static int getPage(String url) throws Exception {
        String source = getdizhi(url);
        Pattern pattern = Pattern.compile("<a>.*[0-9][0-9].*</a>");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            //System.out.print(url+" ------- ");
            String index = matcher.group();
            String num = index.substring(3, 6);
            String cout = num.replaceAll("[\u4e00-\u9fa5]+", "");
            //System.out.println(Integer.parseInt(cout));
            return Integer.parseInt(cout);
        }
        return 0;
    }
    public static String getJpgSrc(String html) throws Exception{
        String shtml = getdizhi(html);
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<img.*>");
        Matcher matcher = pattern.matcher(shtml);
        while (matcher.find()) {
            list.add(matcher.group());
            //System.out.println(matcher.group());
        }

        Set<String> list2 = new HashSet<String>();
        String TURL = "";
        for (String stml : list) {
            if (stml.contains("http")&&stml.contains("jpg")&&stml.contains("allimg")&&!stml.contains("-L")) {
                int index = stml.indexOf("http");
                int end = stml.indexOf(".jpg");
                TURL = stml.substring(index, end+4);
                //System.out.println("获取到图片地址："+TURL);
                //list2.add(stml);
            }
        }
        return TURL;
    }
    public static void saveImg(Set<String> list,int i) throws Exception{
        String dirName = "D:/Spider2/Pic2/"+i;
        createDir(dirName);
        for (String url : list) {
            if (url.trim().equals("")) {
                continue;
            }else {
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                URL uri = new URL(url);
                InputStream in = uri.openStream();
                FileOutputStream fo = new FileOutputStream(new File(dirName,imageName));
                byte[] buf = new byte[1024];
                int length = 0;
                System.out.print("开始下载:" + url);
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                }
                in.close();
                fo.close();
                saveimg++;
                num++;
                System.out.println("----------"+imageName + "下载完成");
            }
        }
        System.out.println("\n本次一共下载"+saveimg+"张图片.存储在---"+dirName+"---.");
        saveimg = 0;
        System.out.println("-----------------------------本次下载结束------------------------------");
    }
    public static void createDir(String name) {
        File f = new File(name);
        // 创建文件夹
        if (!f.exists()) {
            f.mkdirs();
        }
    }
}