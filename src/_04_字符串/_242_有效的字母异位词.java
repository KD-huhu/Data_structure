package _04_字符串;

/**
 * https://leetcode-cn.com/problems/valid-anagram/
 * @author DELL
 * 思路：可以使用hash表，但是题目中提示只有小写字母
 * 	所以可以使用整型数组来统计
 * 	使用ASCII码来求解索引
 * 	在统计完第一个串后使用同一个数组，遍历第二个串，出现时将对应索引的值减一
 * 	如果出现值小于0，表示有字母数量不同。
 */
public class _242_有效的字母异位词 {
	public boolean isAnagram(String s, String t) {
		if (s == null || t == null) return false;
		// 使用数组可以实现空间换时间
		char[] schars = s.toCharArray();
    	char[] tchars = t.toCharArray();
    	if (schars.length != tchars.length) return false;
    	int[] counts = new int[26];
    	for (int i = 0; i < schars.length; i++) {
			counts[schars[i] - 'a']++;
		}
    	for (int i = 0; i < tchars.length; i++) {
    		if (--counts[tchars[i] - 'a'] < 0) return false;
		}
    	return true;
	}
}
