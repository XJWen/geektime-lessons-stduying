package homework.week8;

import java.util.*;

public class reverseWords {

    public String reverseWords(String s) {
        return useApiTodo(s);
    }

    private String useApiTodo(String str){
        //去除开头和末尾空白符
        str = str.trim();
        //正则表达式匹配空白符
        List<String> words = Arrays.asList(str.split("\\s+"));

        Collections.reverse(words);

        return String.join(" ", words);
    }

    public String useDequeTodo(String s) {
        int left = 0, right = s.length() - 1;
        // 去掉字符串开头的空白字符
        while (left <= right && s.charAt(left) == ' ') {
            ++left;
        }

        // 去掉字符串末尾的空白字符
        while (left <= right && s.charAt(right) == ' ') {
            --right;
        }

        Deque<String> deque = new ArrayDeque<String>();
        StringBuilder word = new StringBuilder();

        while (left <= right) {
            char c = s.charAt(left);
            if ((word.length() != 0) && (c == ' ')) {
                // 将单词 push 到队列的头部
                deque.offerFirst(word.toString());
                word.setLength(0);
            } else if (c != ' ') {
                word.append(c);
            }
            ++left;
        }
        deque.offerFirst(word.toString());

        return String.join(" ", deque);

    }

}
