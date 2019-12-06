package cn.hc.util.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Created by hc on 2017/7/4.
 * java version <= 8
 */
public class ImgBase64 {
    public static void main(String[] args) throws Exception{
        String imageStr = getImageStr("E:/test.png");
        System.out.println(imageStr);
        generateImage(imageStr,"E:/test2.png");
    }

    public void Base64ToImg(String base64Img)throws Exception{
        BASE64Decoder decoder  = new BASE64Decoder();
        byte[] buffer = decoder.decodeBuffer(base64Img);
        for (byte b : buffer) {
            if(b<0){
                b+=256;
            }
        }
        InputStream stream= new FileInputStream(new String(buffer));
    }

    /**
     * img to base64
     * @param fullFileName file name and path
     * @return
     */
    public static String getImageStr(String fullFileName) {
        String imgFile = fullFileName;
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static boolean generateImage(String imgStr,String saveImgFilePath) {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(saveImgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
