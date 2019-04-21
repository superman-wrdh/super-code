package cn.hc.algorithm.reflect;

/**
 * Created by super on 2019/4/2
 */
public class ClassLoaderAndClassForNameTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> rc = Class.forName("cn.hc.algorithm.reflect.Robot");
        System.out.println("----------------");
        ClassLoader loader = Robot.class.getClassLoader();
    }
}
