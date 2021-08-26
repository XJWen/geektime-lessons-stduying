package homework.week8;

import java.util.HashMap;
import java.util.Map;

public class firstUniqChar {

    public int firstUniqChar(String s) {
       char[] chars = s.toCharArray();
       char[] result = new char[128];
       for (int i = 0; i < chars.length; i++){
           result[chars[i]]++;
       }

       for (int j = 0; j < chars.length; j++){
            if (result[chars[j]] == 1){
                return j;
            }
       }

       return -1;
    }

}
