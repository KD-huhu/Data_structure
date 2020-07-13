package _03_栈_队列;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/daily-temperatures/
 * @author DELL
 * 思路：题目其实是求解某一个位置右边第一个比自己大的元素
 */
public class _739_每日温度 {
	// 使用栈来实现
    public int[] dailyTemperatures1(int[] T) {
    	if (T == null || T.length == 0) return null;
    	int[] result = new int[T.length];
    	Stack<Integer> stack = new Stack<>();
    	for (int i = 0; i < T.length; i++) {
    		// 这里应该要写大于，不要写大于等于
    		while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
				result[stack.peek()] = i - stack.peek();
				stack.pop();
			}
    		stack.push(i);
		}
    	return result;
    }
    
    /**
     * 倒推法
     * 自右向左扫描
     * 因为最后一天的结果为0
     * 两个指针。i从倒数第二个元素开始扫描
     * 对于每一个i ，一开始令j=i+1
     * 1.1 如果T[i] < T[j], 那么values[i]=j-i, 然后i--
     * 1.2 如果T[i] < T[j], 且values[j]==0, 那么 values[i]=0, 然后i--
     * 2.1 如果T[i] = T[j], 且values[j]==0, 那么 values[i]=0, 然后i--
     * 2.2 如果T[i] = T[j], 且values[j]!=0, 那么 values[i]=values[j]+j-1
     * 3.1 如果T[i] > T[j], 且values[j]==0, 那么 values[i]=0, 然后i--
     * 3.2 如果T[i] > T[j], 且values[j]!=0, 那么j=j+values[j], 重新进入1判断
     */
    public int[] dailyTemperatures2(int[] T) {
    	if (T == null || T.length == 0) return null;
    	int[] values = new int[T.length];
    	for (int i = T.length - 2; i >= 0; i--) {
			int j = i + 1;
			while (true) {
				if (T[i] < T[j]) {
					values[i] = j - i;
					break;
				} else if (values[j] == 0) {
					values[i] = 0;
					break;
				} else if (T[i] == T[j]) {
					values[i] = values[j] + j - i;
					break;
				} else {
					j = j + values[j];
				}
			}
		}
    	return values;
    }
    
    /**
     * 判断条件简化
     * 自右向左扫描
     * 因为最后一天的结果为0
     * 两个指针。i从倒数第二个元素开始扫描
     * 对于每一个i ，一开始令j=i+1
     * 1. 如果T[i] < T[j], 那么values[i]=j-i, 然后i--
     * 2. 如果values[j]==0, 那么 values[i]=0, 然后i--
     * 3. 否则j=j+values[j], 重新进入1判断
     */
    public int[] dailyTemperatures3(int[] T) {
    	if (T == null || T.length == 0) return null;
    	int[] values = new int[T.length];
    	for (int i = T.length - 2; i >= 0; i--) {
			int j = i + 1;
			while (true) {
				if (T[i] < T[j]) {
					values[i] = j - i;
					break;
				} else if (values[j] == 0) {
					values[i] = 0;
					break;
				}
				// 当T[i] == T[j]的时候
				j = j + values[j];
			}
		}
    	return values;
    }
}
