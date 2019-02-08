package cn.hc.algorithm.stack;

/**
 * Created by super on 2019/2/8
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 题目：我现在需要实现一个栈，这个栈除了可以进行普通的push、pop操作以外，
 * 还可以进行getMin的操作，getMin方法被调用后，会返回当前栈的最小值，你会怎么做呢？
 * 你可以假设栈里面存的都是int整数。
 */
public class MinStack {

    private List<Integer> data = new ArrayList<Integer>();
    private List<Integer> mins = new ArrayList<Integer>();

    public void push(int num) throws Exception {
        data.add(num);
        if (mins.size() == 0) {
            // 初始化mins
            mins.add(0);
        } else {
            // 辅助栈mins push最小值的索引
            int min = getMin();
            if (num < min) {
                mins.add(data.size() - 1);
            }
        }
    }

    public int pop() throws Exception {
        // 栈空，抛出异常
        if (data.size() == 0) {
            throw new Exception("栈为空");
        }
        // pop时先获取索引
        int popIndex = data.size() - 1;
        // 获取mins栈顶元素，它是最小值索引
        int minIndex = mins.get(mins.size() - 1);
        // 如果pop出去的索引就是最小值索引，mins才出栈
        if (popIndex == minIndex) {
            mins.remove(mins.size() - 1);
        }
        return data.remove(data.size() - 1);
    }

    public int getMin() throws Exception {
        // 栈空，抛出异常
        if (data.size() == 0) {
            throw new Exception("栈为空");
        }
        // 获取mins栈顶元素，它是最小值索引
        int minIndex = mins.get(mins.size() - 1);
        return data.get(minIndex);
    }

}
