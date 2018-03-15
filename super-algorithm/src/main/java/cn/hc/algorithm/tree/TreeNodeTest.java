package cn.hc.algorithm.tree;

/**
 * Created by super on 2017/11/27
 */
public class TreeNodeTest {
     /**
      * create a tree
              A
            /   \
          B      c
         /\        \
       D   E        F
          /
         G

      前序遍历
      A B D E G C F
      中序遍历
      D B G E A C F
      后序遍历
      D G E B F C A

     */
    public TreeNode createTree(){
        TreeNode root = new TreeNode("A");
        root.setLeft(new TreeNode("B"));
        root.getLeft().setLeft(new TreeNode("D"));
        root.getLeft().setRight(new TreeNode("E"));
        root.getLeft().getRight().setLeft(new TreeNode("G"));
        root.setRight(new TreeNode("C"));
        root.getRight().setRight(new TreeNode("F"));
        return root;
    }

    /**
     * 前序遍历
     * @param root the tree node root node
     */
    public static void preOrder(TreeNode root){
        if(root == null){
            return;
        }
        System.out.print(root.getValue() + " ");
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    /**
     * 中序遍历
     * @param root the tree node root node
     */
    public static void inOrder(TreeNode root){
        if(root == null){
            return;
        }
        inOrder(root.getLeft());
        System.out.print(root.getValue() + " ");
        inOrder(root.getRight());
    }

    /**
     * 后序遍历
     * @param root the tree node root node
     */
    public static void postOrder(TreeNode root){
        if(root == null){
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.print(root.getValue() + " ");
    }

    public static void main(String[] args) {
        TreeNodeTest treeNodeTest = new TreeNodeTest();
        TreeNode root = treeNodeTest.createTree();
        System.out.println("前序遍历");
        TreeNodeTest.preOrder(root);

        System.out.println("\n中序遍历");
        TreeNodeTest.inOrder(root);
        System.out.println("\n后序遍历");
        TreeNodeTest.postOrder(root);
    }
}
