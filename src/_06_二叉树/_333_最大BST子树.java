package _06_二叉树;

import common.TreeNode;

/**
 * https://leetcode-cn.com/problems/largest-bst-subtree/
 * @author DELL
 * 思路：
 * 有4种情况，以root为根节点的二叉树就是一棵BST，最大BST子树就是其本身
   	li != null && ri != null
    && li.root == root.left && root.val > li.max
    && ri.root == root.right && root.val < ri.min

    li != null && ri == null
    && li.root == root.left && root.val > li.max

    li == null && ri != null
    && ri.root == root.right && root.val < ri.min

    li == null && ri == null
 */
public class _333_最大BST子树 {
	public int largestBSTSubtree(TreeNode root) {
        return (root == null) ? 0 : getInfo(root).size;
    }
	
	/**
     * 返回以root为根节点的二叉树的最大BST子树信息
     * 后序遍历，减少重复调用
     */
    private Info getInfo(TreeNode root) {
    	if (root == null) return null;
        // li(left info)：左子树的最大BST子树信息
        Info li = getInfo(root.left);
        // ri(right info)：右子树的最大BST子树信息
        Info ri = getInfo(root.right);
        int leftBstSize = -1, rightBstSize = -1, max = root.val, min = root.val;
        if (li == null) {
            leftBstSize = 0;
        } else if (li.root == root.left && root.val > li.max) {
            leftBstSize = li.size;
            min = li.min;
        }
        if (ri == null) {
            rightBstSize = 0;
        } else if (ri.root == root.right && root.val < ri.min) {
            rightBstSize = ri.size;
            max = ri.max;
        }
        if (leftBstSize >= 0 && rightBstSize >= 0) {
            return new Info(root, 1 + leftBstSize + rightBstSize, max, min);
        }
        // 以root为根节点的二叉树并不是BST
        // 返回最大BST子树的节点数量较多的那个Info
        if (li != null && ri != null) return (li.size > ri.size) ? li : ri;
        // 返回li、ri中不为null的那个Info
        return (li != null) ? li : ri;
    }
    
    /**
     * 最大BST子树的信息
     */
    private static class Info {
        /** 根节点 */
        public TreeNode root;
        /** 节点总数 */
        public int size;
        /** 最大值 */
        public int max;
        /** 最小值 */
        public int min;

        public Info(TreeNode root, int size, int max, int min) {
            this.root = root;
            this.size = size;
            this.max = max;
            this.min = min;
        }
    }
    
//  自顶向下的调用，前序遍历；时间复杂度：O(n^2)
//	private boolean isBST(TreeNode root) {
//		return false;
//	}
//	
//	private int nodesCount(TreeNode root) {
//		return 0;
//	}
//	
//	public int largestBSTSubtree(TreeNode root) {
//		if (root == null) return 0;
//		if (isBST(root)) return nodesCount(root);
//		return Math.max(largestBSTSubtree(root.left), largestBSTSubtree(root.right));
//	}
}
