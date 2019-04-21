package cn.hc.algorithm.proxy.v2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy {
    public static void main(String[] args) {
        // 动态代理
        ProxyHandler proxy = new ProxyHandler();
        Subject subject = (Subject) proxy.bind(new RealSubject());
        subject.doSomething();
    }
}

interface Subject {
    void doSomething();
}


class RealSubject implements Subject {

    @Override
    public void doSomething() {
        System.out.println("do some thing");
    }
}

class ProxyHandler implements InvocationHandler {

    private Object tar;

    public Object bind(Object tar) {
        this.tar = tar;
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        String methodName = method.getName();
        System.out.println("run:" + methodName);
        result = method.invoke(tar, args);
        return result;
    }


}