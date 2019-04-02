package cn.hc.algorithm.test;

public class TryDemo {
    public static void main(StringTest[] args) {
        int j = query();
        System.out.println(j);
    }

    public static int query() {
        int i = 0;
        try {
            System.out.print("try\n");
            return i += 10;
        } catch (Exception e) {
            System.out.print("catch\n");
            i += 20;
        } finally {
            System.out.print("finally-i:" + i + "\n");
            i += 10;
            System.out.print("finally\n");
            return i;
        }
    }
}


