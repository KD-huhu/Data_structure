package _08_高频题目;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/two-sum/
 * @author DELL
 * 思路：使用hash表来实现，通过判断之前扫描的元素是否有可以组成目标数值的数字
 * key是值，value是索引
 */
public class _1_两数之和 {
    public int[] twoSum(int[] nums, int target) {
        if (nums == null) return null;
        // 用来存放之前扫描过的元素
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer idx = map.get(target - nums[i]);
            if (idx != null) return new int[]{idx, i};
            map.put(nums[i], i);
        }
        return null;
    }
}
