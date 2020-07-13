package _07_DFS;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/permutations/
 * @author DELL
 * 思路：注意在枚举每一次可以选择的选项时，必须注意不能重复
 */
public class _46_全排列 {
	private List<List<Integer>> list;
    private int[] nums;
    /** 用来保存每一层选择的数字 */
    private int[] result;
    /** 用来标记nums中的数字是否被使用过了 */
    private boolean[] used;
    
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) return null;
        list = new ArrayList<>();
        if (nums.length == 0) return list;
        this.nums = nums;
        result = new int[nums.length];
        used = new boolean[nums.length];
        dfs(0);
        return list;
    }
    
    private void dfs(int idx) {
        // 不能再往下搜索
        if (idx == nums.length) {
            List<Integer> resultList = new ArrayList<>();
            for (int value : result) {
                resultList.add(value);
            }
            list.add(resultList);
            return;
        }
        // 枚举这一层所有可以做出的选择
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            result[idx] = nums[i];
            used[i] = true;
            dfs(idx + 1);
            // 还原现场
            used[i] = false;
        }
    }
}
