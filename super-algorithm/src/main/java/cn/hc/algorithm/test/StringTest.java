package cn.hc.algorithm.test;

import java.util.HashMap;

/**
 * Created by super on 2019/4/3
 */
public class StringTest {
    public static void main(java.lang.String[] args) {
        String aaa = "aaa";
        String intern = aaa.intern();
        String bbb = "aaa";
        System.out.println(aaa == bbb);
    }

}
