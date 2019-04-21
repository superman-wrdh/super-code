package cn.hc.algorithm.proxy.v1;

public class StaticProxy {


    public static void main(String[] args) {
        // 静态代理
        Subject sub = new SubjectProxy();
        sub.doSomething();
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

class SubjectProxy implements Subject {

    Subject subjectIml = new RealSubject();

    @Override
    public void doSomething() {
        subjectIml.doSomething();
    }
}
