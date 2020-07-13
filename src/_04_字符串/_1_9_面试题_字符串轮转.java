package _04_字符串;

/**
 * https://leetcode-cn.com/problems/string-rotation-lcci/
 * @author DELL
 * 思路：如果字符串的长度相同，将源字符串拼接在自身之后，依次遍历新的字符串
 * 可以得到所有的轮转字符串
 */
public class _1_9_面试题_字符串轮转 {
	public static boolean isFlipedString(String s1, String s2) {
		if (s1 == null || s2 == null) return false;
		if (s1.length() != s2.length()) return false;
		// 这里还可以考虑使用KMP算法
		return (s1 + s1).contains(s2);
	}
}
