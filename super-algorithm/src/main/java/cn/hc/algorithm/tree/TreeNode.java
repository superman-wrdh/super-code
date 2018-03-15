package cn.hc.algorithm.tree;

/**
 * Created by super on 2017/11/27
 */
public class TreeNode {
    private final String value;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public String getValue() {
        return value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
