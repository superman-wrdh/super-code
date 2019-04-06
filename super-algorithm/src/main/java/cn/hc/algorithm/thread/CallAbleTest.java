package cn.hc.algorithm.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by super on 2019/4/7
 */
public class CallAbleTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<String>(new MyCallAble());
        new Thread(task).start();
        if (!task.isDone()) {
            System.out.println("task has not finished,please wait");
        }
        System.out.println("task return data:" + task.get());
    }
}

class MyCallAble implements Callable<String> {

    @Override
    public String call() throws Exception {
        String value = "test";
        System.out.println("Ready to work");
        Thread.currentThread().sleep(5000);
        System.out.println("Work done");
        return value;
    }
}
