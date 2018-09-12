package cn.hc.algorithm.reflect;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
class RealHello {

    public void say(String s) {
        System.out.println("hello " + s);
    }

}

class HelloDelegate<T> implements MethodHandler {

    private T target;

    public HelloDelegate(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
        System.out.println("before print");
        method.invoke(target, args);
        System.out.println("after print");
        return null;
    }

}

public class DynamicProxy {

    public static void main(String[] args) {
        RealHello hello = enhanceHello(new RealHello());
        hello.say("world");
    }

    @SuppressWarnings("unchecked")
    public static <T> T enhanceHello(T target) {
        ProxyFactory proxy = new ProxyFactory();
        proxy.setSuperclass(RealHello.class);
        try {
            HelloDelegate<T> delegate = new HelloDelegate<T>(target);
            // create方法传递了两个空数组
            // 分别代表构造器的参数类型数组和构造器的参数实例数组
            return (T) proxy.create(new Class<?>[0], new Object[0], delegate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
