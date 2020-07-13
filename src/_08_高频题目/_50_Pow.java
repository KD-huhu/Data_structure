package _08_高频题目;

/**
 * https://leetcode-cn.com/problems/powx-n/
 * @author DELL
 * 思路：快速幂，分治的思想
 * 1. 递归：3^20=3^10+3^10
 * 		  3^21=3^10+3^10+3^1
 * 2. 
 */
public class _50_Pow {
	// 递归实现
	// 时间复杂度：// T(n) = T(n/2) + O(1) = O(logn)
    public double myPow1(double x, int n) {
    	if (n == 0) return 1;
    	if (n == -1) return 1 / x;
    	// 是否是奇数
    	boolean odd = (n & 1) == 1;
    	double half = myPow1(x, n >> 1);
    	half *= half;
    	return odd ? (half * x) : half;
    }
    
    // 非递归实现
    public double myPow2(double x, int n) {
        long y = (n < 0) ? -((long) n) : n;
        double res = 1.0;
        while (y > 0) {
            if ((y & 1) == 1) {
                // 如果最后一个二进制位是1，就累乘上x
                res *= x;
            }
            x *= x;
            // 舍弃掉最后一个二进制位
            y >>= 1;
        }
        return (n < 0) ? (1 / res) : res;
    }
}
