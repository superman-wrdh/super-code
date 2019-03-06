package cn.hc.algorithm.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class JUC {
    public static void main(String[] args) {
        ThreadFactory factory = Executors.defaultThreadFactory();
        factory.newThread(new Runner()).start();

        for (int i = 0; i < 100; i++) {
            System.out.println("good");
        }


    }
}

class Runner implements Runnable {

    @Override
    public void run() {
        System.out.println("hello");
    }
}
