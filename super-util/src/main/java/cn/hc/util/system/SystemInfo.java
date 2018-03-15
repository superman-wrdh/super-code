package cn.hc.util.system;

import java.util.Properties;

/**
 * Created by hc on 2017/7/2.
 */
public class SystemInfo {
    public static void main(String[] args) {
        //System.out.println(getAllSystemInfo());
        System.out.println(getItemInfo("java.home"));
    }

    public static String getAllSystemInfo(){
        String sysinfo = System.getProperties().toString();
        int len = sysinfo.length();
        sysinfo=sysinfo.substring(1,len-1);
        String[] strings = sysinfo.split(",");
        String result="";
        for (String s : strings) {
            result=result+s+"\n";
        }
        return result;
    }

    public static String getItemInfo(String key){
        return System.getProperty(key);
    }
}
