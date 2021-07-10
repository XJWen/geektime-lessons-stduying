package homework.week2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个网站域名，如"discuss.leetcode.com"，包含了多个子域名。作为顶级域名，常用的有"com"，下一级则有"leetcode.com"，最低的一级为"discuss.leetcode.com"。当我们访问域名"discuss.leetcode.com"时，也同时访问了其父域名"leetcode.com"以及顶级域名 "com"。
 *
 * 给定一个带访问次数和域名的组合，要求分别计算每个域名被访问的次数。其格式为访问次数+空格+地址，例如："9001 discuss.leetcode.com"。
 *
 * 接下来会给出一组访问次数和域名组合的列表cpdomains。要求解析出所有域名的访问次数，输出格式和输入格式相同，不限定先后顺序。
 *
 * */
public class Solution {

    public static List<String> subdomainVisits(String[] cpdomains) {
        List<String> result = new ArrayList<String>();
        Map<String, Integer> map = new HashMap<String, Integer>();

        for(int i=0;i< cpdomains.length;i++){
            String[] temp = cpdomains[i].split(" ");
            int length = Integer.valueOf(temp[0]);
            String[] tips = temp[1].split("\\.");
            String connect = "";
            int tipsLength = tips.length;
            for(int j=tipsLength-1;j>=0;j--){
                if (j == tipsLength-1){
                    connect = tips[j];
                }else{
                    connect = tips[j] + "." + connect;
                }
                map.put(connect,map.getOrDefault(connect,0)+length);
            }
        }
        for (String key : map.keySet()){
            String tempStr = "";
            tempStr = map.get(key)+" "+key;
            result.add(tempStr);
        }
        return result;

    }

    public static void main(String[] args) {
        String[] str1 = {"9001 discuss.leetcode.com"};
        String[] str2 = {"900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"};
        List<String> result1 = subdomainVisits(str1);
        List<String> result2 = subdomainVisits(str2);
        System.out.println(result1.toString());
    }
}
