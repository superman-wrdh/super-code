package cn.hc.http.file;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by hc on 2017/5/23.
 */
public class DownLoad {
    private String location ="";
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
    String _fileName="";
    String _savePath="";


    public void set_url(String _url) {
        this._url = _url;
    }

    public void set_params(Map<String, String> _params) {
        this._params = _params;
    }

    public void set_fileName(String _fileName) {
        this._fileName = _fileName;
    }

    public void set_savePath(String _savePath) {
        this._savePath = _savePath;
    }

    public DownLoad(String url, Map<String,String> keyValueParams, String savePath, String fileName) {
        this._url=url;
        this._params=keyValueParams;
        this._fileName=fileName;
        this._savePath=savePath;

    }
    public DownLoad(){
        //location =  ApplicationContext.getConfig().getParam("download.pdf.location", String.class);
    }

    public String Do() throws Exception {
        String result = "";
        BufferedReader in = null;
        try {

            String urlStr = this._url + "&" + getParamStr();

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new DownLoad.TrustAnyTrustManager() },
                    new java.security.SecureRandom());
            URL realUrl = new URL(urlStr);

            HttpsURLConnection connection = (HttpsURLConnection) realUrl.openConnection();

            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setHostnameVerifier(new DownLoad.TrustAnyHostnameVerifier());
            connection.setDoOutput(true);


            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            connection.connect();


            InputStream inputStream = connection.getInputStream();
            //************************************************************************
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(_savePath);
            if(!saveDir.exists()){
                saveDir.mkdir();
            }
            File file = new File(saveDir+File.separator+_fileName);
            file.setExecutable(true);   //设置可执行权限
            file.setReadable(true);     //设置可读权限
            file.setWritable(true);     //设置可写权限
            result=saveDir+File.separator+_fileName;
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if(fos!=null){
                fos.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }
            //*****************************************************************************

        } catch (Exception e) {
            throw e;
        }

        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {

                throw e2;
            }
        }
        return result;

    }

    private String getParamStr(){
        String paramStr="";
        Map<String, String> params = this._params;
        for (String key : params.keySet()) {
            paramStr+=key+"="+params.get(key)+"&";
        }
        paramStr=paramStr.substring(0, paramStr.length()-1);
        return paramStr;
    }



    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public int downLoadFile(String filePath,String no,String fileName){
        int statusCode=0;
        DownLoad downLoad = new DownLoad();
        String token = new SupplierReport().getToken();
        String downloadURL= "https://erp.genetalks.com/ghealth/api/get_pdf/cd?token="+token+"&no="+no;
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        map.put("no",no);
        downLoad.set_url(downloadURL);
        downLoad.set_params(map);
        if(StringUtils.isEmpty(fileName)){
            downLoad.set_fileName(no+".zip");
        }else {
            downLoad.set_fileName(fileName+".zip");
        }
        downLoad.set_savePath(filePath);
        try {
            downLoad.Do();
        } catch (Exception e) {
            statusCode=1;
        }
        return statusCode;
    }

    /**
     * 解压缩
     * @param fileFullName
     * @param outputName
     *//*
    public List<String> unzip(String fileFullName,String outputName){
        List<String> fileNmaeList = new ArrayList<>();
        long startTime=System.currentTimeMillis();
        try {
            ZipInputStream Zin=new ZipInputStream(new FileInputStream(
                    fileFullName));//输入源zip路径
            BufferedInputStream Bin=new BufferedInputStream(Zin);
            String Parent=outputName; //输出路径（文件夹目录）
            File Fout=null;
            ZipEntry entry;
            try {
                while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){
                    Fout=new File(Parent,entry.getName());
                    if(!Fout.exists()){
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out=new FileOutputStream(Fout);
                    BufferedOutputStream Bout=new BufferedOutputStream(out);
                    int b;
                    while((b=Bin.read())!=-1){
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                    fileNmaeList.add(Fout.getName().toString());
                    System.out.println(Fout+"解压成功");
                }
                Bin.close();
                Zin.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("耗费时间： "+(endTime-startTime)+" ms");
        return fileNmaeList;
    }

    public static void main(String[] args) throws Exception{
        DownLoad downLoad = new DownLoad();
        downLoad.downLoadFile("E:/resource","GI02000045",null);
        List<String> fileList = downLoad.unzip("E:\\resource\\GI02000045.zip", "E:\\resource\\");
        for (String s : fileList) {
            System.out.println("---> "+s);
        }

    }*/
}
