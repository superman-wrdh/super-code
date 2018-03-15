package cn.hc.util.File;

/**
 * Created by hc on 2017/7/19.
 */

import java.io.*;

/**
 * 1 分类
 *   按照操作数据为 字节流  字符流
 *   按照流向       输入流  输出流
 *
 *   字节流抽象基类  (读)InputStream       (写)OutputStream
 *
 *   字符流抽象基类  (读)Reader            (写)Writer
 */

public class JavaIOBase {
    public static void main(String[] args) {
        //testFileWriter();
        testFileReader();
        Reader reader = new InputStreamReader(System.in);
    }
    /**
     * FileWriter
     */
     public static void testFileWriter(){
         FileWriter fw=null;
         try {
              fw= new FileWriter("E:\\test.txt");
              fw.write("abc测试写入文件");
              fw.flush();
         }catch (Exception e){
             //TODO handler exception
             e.printStackTrace();
         }finally {
             try{
                 if(fw != null){
                     fw.close();
                 }
             }catch (Exception e){
                 //TODO handler exception
             }
         }
     }


    /**
     * FileReader
     */
    public static void testFileReader(){
         FileReader fr=null;
         try {
             fr=new FileReader("E:\\test.txt");
             char[] buffer=new char[1024];
             int num=0;
             while ( (num=fr.read(buffer)) != -1){
                 System.out.println(new String(buffer,0,num));
             }
         }catch (Exception e){
             //TODO handler exception
         }finally {
             try{
                 if(fr != null){
                     fr.close();
                 }
             }catch (Exception e){
                 //TODO handler exception
             }
         }
     }

    /**
     * test BufferWrite
     */
    public static void testBufferWriter(){
        FileWriter fw = null;
        BufferedWriter bfw = null;
        try {
            fw=new FileWriter("E:\\test.txt");
            bfw=new BufferedWriter(fw);
            bfw.write("abc测试bufferWriter");
            bfw.newLine();
            bfw.flush();
        }catch (Exception e){
            //TODO handler exception
            e.printStackTrace();
        }finally {
            try {
                if(bfw != null){
                    bfw.close();
                }
                if(fw != null){
                    fw.close();
                }
            }catch (Exception e){
                //TODO handler exception
            }
        }
    }

    /**
     * test BufferReader
     */
    public static void testBufferReader(){
        FileReader fr = null;
        BufferedReader bfr = null;
        try {
            fr = new FileReader("E:\\test.txt");
            bfr = new BufferedReader(fr);
            String line = null;
            while ((line=bfr.readLine()) != null){
                System.out.println(line);
            }
        }catch (Exception e){
            //TODO handler exception
            e.printStackTrace();
        }finally {
            try{
                if(bfr != null){
                    bfr.close();
                }
                if(fr != null){
                    fr.close();
                }
            }catch (Exception e){
                //TODO handler exception
                e.printStackTrace();
            }
        }
    }

    /**
     * use FileWriter and FileReader copy text file
     */
    public static void copyFile()
    {
        FileWriter fw = null;
        FileReader fr = null;
        try
        {
            fw = new FileWriter("E:\\test_copy.txt");
            fr = new FileReader("E:\\test.txt");
            char[] buf = new char[1024];
            int len = 0;
            while((len=fr.read(buf))!=-1)
            {
                fw.write(buf,0,len);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("读写失败");

        }
        finally
        {
            if(fr!=null)
                try
                {
                    fr.close();
                }
                catch (IOException e)
                {
                }
            if(fw!=null)
                try
                {
                    fw.close();
                }
                catch (IOException e)
                {
                }
        }
    }

}
