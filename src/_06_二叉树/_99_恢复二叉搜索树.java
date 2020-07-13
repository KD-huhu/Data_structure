package _06_二叉树;

import common.TreeNode;

/**
 * https://leetcode-cn.com/problems/recover-binary-search-tree/
 * @author DELL
 * 思路：二叉搜搜树的中序遍历是升序的
 * 两个节点在中序遍历时相邻，会出现一个逆序对
 * 不过不相邻会出现两个逆序对
 * 第一个错误节点：第一个逆序对中较大的节点
 * 第二个错误节点：第二个逆序对中较小的节点
 */
public class _99_恢复二叉搜索树 {
	// 中序遍历：时间复杂度O(n)、空间复杂度O(1)
    /**
     * 上一次中序遍历过的节点
     */
    private TreeNode prev;
    /**
     * 第一个错误节点
     */
    private TreeNode first;
    /**
     * 第二个错误节点
     */
    private TreeNode second;
    
    /**
     * @param root 是一棵错误的二叉搜索树（有2个节点被错误交换）
     */
    public void recoverTree(TreeNode root) {
        findWrongNodes(root);
        // 交换2个错误节点的值
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
    }
    
    private void findWrongNodes(TreeNode root) {
        if (root == null) return;
        findWrongNodes(root.left);
        find(root);
        findWrongNodes(root.right);
    }
    private void find(TreeNode node) {
        // 出现了逆序对
        if (prev != null && prev.val > node.val) {
            // 第2个错误节点：最后一个逆序对中较小的那个节点
            second = node;
            // 第1个错误节点：第一个逆序对中较大的那个节点
            if (first != null) return;
            first = prev;
        }
        prev = node;
    }
    
    // 降低空间复杂度
    public void recoverTree1(TreeNode root) {
        TreeNode node = root;
        while (node != null) {
            if (node.left != null) {
                // 找到前驱节点(predecessor)、后继节点(successor)
                TreeNode pred = node.left;
                while (pred.right != null && pred.right != node) {
                    pred = pred.right;
                }
                if (pred.right == null) {
                    pred.right = node;
                    node = node.left;
                } else { // pred.right == node
                    find(node);
                    pred.right = null;
                    node = node.right;
                }
            } else {
                find(node);
                node = node.right;
            }
        }
        // 交换2个错误节点的值
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
    }
}
