package _04_字符串;

import common.TreeNode;

/**
 * https://leetcode-cn.com/problems/subtree-of-another-tree/
 * @author DELL
 * 思路1：遍历一棵二叉树，来判断是否是子树（注意：层序遍历在该题目不合适）
 * 思路2：对二叉树进行序列化（序列化成字符串）
 * 	对二叉树进行后序遍历，注意为了确保结果的唯一性要加上叶子节点的空节点
 * 	（空节点也要完全序列化）
 *  注意前序遍历时2也是12的子串，所以最好使用后序遍历
 */
public class _572_另一个树的子树 {
    public boolean isSubtree(TreeNode s, TreeNode t) {
    	if (s == null || t == null) return false;
    	return postSerialize(s).contains(postSerialize(t));
    }
    
	/**
	 * 利用后序遍历的方式进行序列化
	 * @param root 树的根节点
	 * @return
	 */
	private String postSerialize(TreeNode root) {
		StringBuilder sb = new StringBuilder("!");
		postSerialize(root, sb);
		return sb.toString();
	}
	
	private void postSerialize(TreeNode node, StringBuilder sb) {
		if (node.left == null) {
			sb.append("#!");
		} else {
			postSerialize(node.left, sb);
		}
		if (node.right == null) {
			sb.append("#!");
		} else {
			postSerialize(node.right, sb);
		}
		sb.append(node.val).append("!");
	}
}
