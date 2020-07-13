package _07_DFS;

import java.util.ArrayList;
import java.util.List;

public class _17_电话号码的字母组合_成员变量变参数 {
    private char[][] lettersArray = {
            {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
            {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'},
            {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}
    };

    public List<String> letterCombinations(String digits) {
        if (digits == null) return null;
        List<String> list = new ArrayList<>();
        char[] chars = digits.toCharArray();
        if (chars.length == 0) return list;
        char[] string = new char[chars.length];
        dfs(0, chars, string, list);
        return list;
    }
    
    private void dfs(int idx, char[] chars, char[] string, List<String> list) {
        // 已经进入到最后一层了，不能再往下搜索
        if (idx == chars.length) {
            // 得到了一个正确的解
            list.add(new String(string));
            return;
        }
        // 先枚举这一层可以做的所有选择
        char[] letters = lettersArray[chars[idx] - '2'];
        for (char letter : letters) {
            string[idx] = letter;
            dfs(idx + 1, chars, string, list);
        }
    }

}
