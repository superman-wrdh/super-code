package cn.hc.util.File;

/**
 * Created by hc on 2017/7/19.
 */

import java.io.*;

/**
 * 1 分类
 *   按照操作数据为 字节流  字符流
 *   按照流向       输入了  输出流
 *
 *   字节流抽象基类  (读)InputStream       (写)OutputStream
 *
 *   字符流抽象基类  (读)Reader            (写)Writer
 */

public class JavaIOStream {
    public static void main(String[] args)throws IOException {
        //writeFile();
        //readFile_1();
        copyPicture();
    }
    public static void writeFile()throws IOException {
        FileOutputStream fos = new FileOutputStream("E:\\fos.txt");
        fos.write("abcde测试FileOutputstream写文件".getBytes());
        fos.close();
    }

    public static void readFile_1()throws IOException {
        FileInputStream fis = new FileInputStream("E:\\fos.txt");
        int ch = 0;
        while((ch=fis.read())!=-1)
        {
            System.out.print((char)ch);
        }
        fis.close();
    }

    public static void readFile_2()throws IOException {
        FileInputStream fis = new FileInputStream("E:\\fos.txt");
        byte[] buf = new byte[1024];
        int len = 0;
        while((len=fis.read(buf))!=-1)
        {
            System.out.println(new String(buf,0,len));
        }
        fis.close();
    }

    public static void readFile_3()throws IOException {
        FileInputStream fis = new FileInputStream("E:\\fos.txt");
        //int num = fis.available();
        byte[] buf = new byte[fis.available()];//定义一个刚刚好的缓冲区。不用在循环了。
        fis.read(buf);
        System.out.println(new String(buf));
        fis.close();
    }

    /**
     * copy picture
     */
    public static void copyPicture(){
        {
            FileOutputStream fos = null;
            FileInputStream fis = null;
            try
            {
                fos = new FileOutputStream("E:\\test_copy.png");
                fis = new FileInputStream("E:\\test.png");

                byte[] buf = new byte[1024];

                int len = 0;

                while((len=fis.read(buf))!=-1)
                {
                    fos.write(buf,0,len);
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException("复制文件失败");
            }
            finally
            {
                try
                {
                    if(fis!=null)
                        fis.close();
                }
                catch (IOException e)
                {
                    throw new RuntimeException("读取关闭失败");
                }
                try
                {
                    if(fos!=null)
                        fos.close();
                }
                catch (IOException e)
                {
                    throw new RuntimeException("写入关闭失败");
                }
            }
        }
    }

    /**
     * copy mp3 use system method
     */
    public static void copyMp3_1()throws IOException{
        //通过字节流的缓冲区完成复制。
        BufferedInputStream bufis = new BufferedInputStream(new FileInputStream("E:\\0.mp3"));
        BufferedOutputStream bufos = new BufferedOutputStream(new FileOutputStream("E:\\1.mp3"));
        int by = 0;
        while((by=bufis.read())!=-1)
        {
                bufos.write(by);
        }
        bufos.close();
        bufis.close();
    }

    /**
     * use MyBufferedInputStream buffer copy mp3
     */
    public static void copyMp3_2()throws IOException{
        MyBufferedInputStream bufis = new MyBufferedInputStream(new FileInputStream("c:\\9.mp3"));
        BufferedOutputStream bufos = new BufferedOutputStream(new FileOutputStream("c:\\3.mp3"));
        int by = 0;
        //System.out.println("第一个字节:"+bufis.myRead());
        while((by=bufis.myRead())!=-1)
        {
            bufos.write(by);
        }
        bufos.close();
        bufis.myClose();
    }
}

/**
 * 利用装饰模式改进 InputStream 实现buffer功能
 */
class MyBufferedInputStream {
    private InputStream in;
    private byte[] buf = new byte[1024*4];
    private int pos = 0,count = 0;
    MyBufferedInputStream(InputStream in) {
        this.in = in;
    }

    //一次读一个字节，从缓冲区(字节数组)获取。
    public int myRead()throws IOException {
        //通过in对象读取硬盘上数据，并存储buf中。
        if(count==0)
        {
            count = in.read(buf);
            if(count<0)
                return -1;
            pos = 0;
            byte b = buf[pos];
            count--;
            pos++;
            return b&255;
        }
        else if(count>0)
        {
            byte b = buf[pos];
            count--;
            pos++;
            return b&0xff;
        }
        return -1;
    }
    public void myClose()throws IOException {
        in.close();
    }
}