package _08_高频题目;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/meeting-rooms/
 * @author DELL
 * 思路：先排序
 * 排序标准：按照会议的开始时间排序
 * 一次比较会议的开始时间和前一个会议的结束时间
 * 如果开始时间小于前一个的结束时间，不可以
 */
public class _252_会议室 {
	public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return true;
        // 按照会议的开始时间，从小到大排序
        Arrays.sort(intervals, (m1, m2) -> m1[0] - m2[0]);
        // 遍历每一个会议
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) return false;
        }
        return true;
    }
}
