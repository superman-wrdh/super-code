package cn.hc.util.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by super on 2018/3/14
 */
public class Collection {
    public static void main(String[] args) {
        map();
    }

    public static void map(){
        Map<String,String> map = new HashMap<>();
        map.put("1","2");
        map.put("2","3");
        map.forEach((key,v)-> System.out.println(key+":"+v));
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey()+entry.getValue());
        }

    }
}
