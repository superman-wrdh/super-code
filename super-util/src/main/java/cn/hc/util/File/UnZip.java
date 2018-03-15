package cn.hc.util.File;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by hc on 2017/7/2.
 */
public class UnZip {
    /**
     * 解压缩
     * @param fileFullName
     * @param outputName
     */
    public List<String> unzip(String fileFullName, String outputName){
        List<String> fileNmaeList = new ArrayList<>();
        long startTime=System.currentTimeMillis();
        try {
            ZipInputStream Zin=new ZipInputStream(new FileInputStream(
                    fileFullName));//输入源zip路径
            BufferedInputStream Bin=new BufferedInputStream(Zin);
            String Parent=outputName; //输出路径（文件夹目录）
            File Fout=null;
            ZipEntry entry=null;
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
}
