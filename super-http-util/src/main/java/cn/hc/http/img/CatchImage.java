package cn.hc.http.img; /**
 * Created by hc on 2017/5/28.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***
 * java抓取网络图片
 * @author swinglife
 *
 */
public class CatchImage {

    // 地址
    //private static final String URL = "http://w2.aqu1024.org/pw/htm_data/14/1705/654166.html";
    //private static final String URL = "http://w2.aqu1024.org/pw/htm_data/14/1705/653224.html";
    //private static final String URL = "http://x2.pix378.net/pw/htm_data/15/1706/665138.html";
    //private static final String URL = "http://s2.lulujjs.biz/pw/htm_data/14/1706/663537.html";
    //private static final String URL = "http://1024.stv919.pw/pw/htm_data/14/1706/678510.html";
    private static final String URL = "http://1024.stv919.pw/pw/htm_data/14/1706/679918.html";
    // 编码
    private static final String ECODING = "UTF-8";
    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";


    public static void main(String[] args) throws Exception {
        CatchImage cm = new CatchImage();
        //获得html文本内容
        String HTML = cm.getHTML(URL);
        //获取图片标签
        List<String> imgUrl = cm.getImageUrl(HTML);
        //获取图片src地址
        //List<String> imgSrc = cm.getImageSrc(imgUrl);
        //下载图片
        cm.Download(imgUrl,"D:\\img\\xiameijiang");
    }


    /***
     * 获取HTML内容
     *
     * @param url
     * @return
     * @throws Exception
     */
    private String getHTML(String url) throws Exception {
        URL uri = new URL(url);
        // 设置通用的请求属性

        URLConnection connection = uri.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        InputStream in = connection.getInputStream();
        byte[] buf = new byte[1024];
        int length = 0;
        StringBuffer sb = new StringBuffer();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            sb.append(new String(buf, ECODING));
        }
        in.close();
        return sb.toString();
    }

    /***
     * 获取ImageUrl地址
     * @param HTML
     * @return
     */
    private List<String> getImageUrl(String HTML) {
        List<String> listImgUrl = new ArrayList<String>();
        Document document = Jsoup.parse(HTML);
        Elements elements=document.getElementsByTag("img");
        for(Element element : elements) {
            String imgSrc=element.attr("src"); //获取src属性的值
            if(imgSrc.startsWith("http") && imgSrc.endsWith(".jpg")){
                listImgUrl.add(imgSrc);
                System.out.println("--->"+imgSrc);
            }
        }
        return listImgUrl;
    }



    /***
     * 下载图片
     */
    private void Download(List<String> listImgSrc,String saveDir) {
            URLConnection connection;
            InputStream in=null;
            FileOutputStream fo=null;
            int count=0;
            System.out.println("开始下载---------------------------------------");
            for (String url : listImgSrc) {
                String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                try {
                    URL uri = new URL(url);
                    //
                    connection= uri.openConnection();
                    connection.setRequestProperty("accept", "*/*");
                    connection.setRequestProperty("connection", "Keep-Alive");
                    connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    in = connection.getInputStream();

                    File dir = new File(saveDir);
                    if(!dir.exists()){
                        dir.setExecutable(true);   //设置可执行权限
                        dir.setReadable(true);     //设置可读权限
                        dir.setWritable(true);     //设置可写权限
                        dir.mkdir();
                    }
                    File file = new File(saveDir + File.separator + imageName);
                    file.setExecutable(true);   //设置可执行权限
                    file.setReadable(true);     //设置可读权限
                    file.setWritable(true);     //设置可写权限
                    fo= new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int length = 0;
                    System.out.println("开始下载:" + url);
                    while ((length = in.read(buf, 0, buf.length)) != -1) {
                        fo.write(buf, 0, length);
                    }
                    System.out.println(imageName + "下载第"+count+"张完成");
                    count++;
                }
                catch (Exception e){
                        e.printStackTrace();
                }
            }
            System.out.println("下载完成 共下载"+count+"张图片---------------------------------------");
             try {
                 if(fo!=null){
                      fo.close();
                 }if(in!=null){
                      in.close();
                   }
                 }
              catch (Exception e){
                 e.printStackTrace();
              }

    }

    public  String getImgStr(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String src="";
        String regEx_img = "]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                src=m.group(1);
            }
        }
        return src;
    }


}
