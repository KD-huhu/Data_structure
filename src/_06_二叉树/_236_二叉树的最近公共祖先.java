package _06_二叉树;

import common.TreeNode;

/**
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * @author DELL
 * 思路：去以root为根节点的二叉树中查找p、q的最近公共祖先
 * 	1. 如果p、q同时存在于这棵二叉树中，就能成功返回它们的最近公共祖先
 * 	2. 如果p、q都不存在于这棵二叉树中，返回null
 * 	3. 如果只有p存在于这棵二叉树中，返回p
 * 	4. 如果只有q存在于这棵二叉树中，返回q
 */
public class _236_二叉树的最近公共祖先 {
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 递归边界
		if (root == null || root == p || root == q) return root;
        // 去以root.left为根节点的二叉树中查找p、q的最近公共祖先
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        // 去以root.right为根节点的二叉树中查找p、q的最近公共祖先
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return (left != null) ? left : right;
    }
}
