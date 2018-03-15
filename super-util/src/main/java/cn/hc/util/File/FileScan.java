package cn.hc.util.File;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by hc on 2017/7/19.
 */
public class FileScan {
    private String save;
    public static void main(String[] args) {
         File dir = new File("E:\\IDEA16Project\\blog");
         showDir(dir,0);
    }
    public static String getLevel(int level) {
        StringBuilder sb = new StringBuilder();
        sb.append("|--");
        for(int x=0; x<level; x++) {
            sb.insert(0,"|  ");
        }
        return sb.toString();
    }

    public static void showDir(File dir, int level) {
        //System.out.println(getLevel(level)+dir.getName());
        String path=getLevel(level)+dir.getName();
        write(path);
        level++;
        File[] files = dir.listFiles();
        for(int x=0; x<files.length; x++)
        {
            if(files[x].isDirectory()) {
                showDir(files[x], level);
            }
            else {
                //System.out.println("start-----------------------------------");
                String line=getLevel(level)+files[x];
                System.out.println(line);
                write(line);
                //System.out.println("end-----------------------------------");
            }
        }
    }
    public static void write(String line){
        //System.out.println("******will write  "+line);
        FileWriter fw = null;
        BufferedWriter bfw = null;
        try {
            fw=new FileWriter("E:\\fileList.txt",true);
            bfw=new BufferedWriter(fw);
            bfw.write(line);
            bfw.newLine();
            bfw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
                if(bfw != null){
                    try {
                        bfw.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(fw != null){
                    try {
                        fw.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        }
    }



}
