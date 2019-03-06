package cn.hc.algorithm.thread;

public class SingeThreadExecution {

    public static void main(String[] args) {
        Gate gate = new Gate();
        new UserThread(gate, "Alice", "Alaska").start();
        new UserThread(gate, "Bobby", "Brazil").start();
        new UserThread(gate, "Chris", "Canada").start();

    }
}


class Gate {
    private int count = 0;
    private String name = "nobody";
    private String address = "nowhere";

    public synchronized void pass(String name, String address) {
        this.count++;
        this.name = name;
        this.address = address;
        check();
    }

    private  void check() {
        if (name.charAt(0) != address.charAt(0)) {
            System.out.println(" **** BROKEN **** " + toString());
        }
    }

    @Override
    public synchronized String toString() {
        return "No." + count + " " + name + " " + address;
    }
}

class UserThread extends Thread {
    private final Gate gate;
    public final String myname;
    public final String myaddress;

    public UserThread(Gate gate, String myname, String myaddress) {
        this.gate = gate;
        this.myname = myname;
        this.myaddress = myaddress;
    }

    @Override
    public void run() {
        System.out.println(myname + " BEGIN");
        while (true) {
            gate.pass(myname, myaddress);
        }

    }
}