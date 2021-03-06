package cn.hc.algorithm.linkedlist;

public class Node {
    private final int value;
    private Node next;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }

    public int getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public static void printLinkedList(Node heed){
        while (heed != null){
            System.out.print(heed.getValue()+" ");
            heed = heed.getNext();
        }
        System.out.println();
    }
}
