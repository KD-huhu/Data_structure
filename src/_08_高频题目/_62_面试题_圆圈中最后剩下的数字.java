package _08_高频题目;

/**
 * https://leetcode-cn.com/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/
 * @author DELL
 * 思路：递推公式f(n, m)=(f(n-1, m)+m)%m
 */
public class _62_面试题_圆圈中最后剩下的数字 {
	public int lastRemaining1(int n, int m) {
        return (n == 1) ? 0 : (lastRemaining1(n - 1, m) + m) % n;
    }
	
	// 非递归
	public int lastRemaining(int n, int m) {
        int res = 0;
        for (int i = 2; i <= n; i++) { // i是数据规模，代表有多少个数字（有多少个人）
            res = (res + m) % i;
        }
        return res;
    }
}
