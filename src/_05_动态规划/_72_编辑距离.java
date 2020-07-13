package _05_动态规划;

/**
 * https://leetcode-cn.com/problems/edit-distance/
 * @author DELL
 * 思路: dp是大小为(n1+1)*(n2+1)的二维数组
 * 		dp[i][j]是s1[0,i)转换成s2[0,j)的最少操作数
 * 		dp[0][0]=0
 * 		dp[i][0]=i,dp[0][j]=j 删除所有字符
 * 	求解dp[i][j]:
 * 		1. 先删除s1[0,i)的最后一个字符得到s1[0,i-1),然后由s1[0,i-1)转换成s2[0,j)
 * 			dp[i][j]=dp[i-1][j]+1
 * 		2. 先由s1[0,i)转换为s2[0,j-1),然后在最后插入字符s2[j-1],得到s2[0,j)
 * 			dp[i][j]=dp[i][j-1]+1
 * 		3. 如果s1[i-1] != s2[j-1],先由s1[0,i-1)转换为s2[0,j-1),然后将s1[i-1]替换为s2[j-1]
 * 			dp[i][j]=dp[i-1][j-1]+1
 * 		4. 如果s1[i-1] == s2[j-1],直接s1[0,i-1)转换为s2[0,j-1)
 * 			dp[i][j]=dp[i-1][j-1]
 */
public class _72_编辑距离 {
	public int minDistance(String word1, String word2) {
		if (word1 == null || word2 == null) return 0;
		char[] cs1 = word1.toCharArray();
    	char[] cs2 = word2.toCharArray();
    	int[][] dp = new int[cs1.length + 1][cs2.length + 1];
    	dp[0][0] = 0;
    	// 第0列
    	for (int i = 1; i <= cs1.length; i++) {
			dp[i][0] = i;
		}
    	// 第0行
    	for (int j = 1; j <= cs2.length; j++) {
			dp[0][j] = j;
		}
    	// 其他行其他列
    	for (int i = 1; i <= cs1.length; i++) {
			for (int j = 1; j <= cs2.length; j++) {
				int top = dp[i - 1][j] + 1;
				int left = dp[i][j - 1] + 1;
				int leftTop = dp[i - 1][j - 1];
				if (cs1[i - 1] != cs2[j - 1]) {
					leftTop++;
				}
				dp[i][j] = Math.min(Math.min(top, left), leftTop);
			}
    	}
    	return dp[cs1.length][cs2.length];
	}
}
