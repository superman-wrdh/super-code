package cn.hc.algorithm.reflect;

/**
 * Created by super on 2019/4/2
 */
public class Robot {
    public String name;

    static {
        System.out.println("static block ");
    }

    public Robot(String name) {
        this.name = name;
    }

    private void sayHi(String word) {
        System.out.println("robot:" + word);
    }
}
