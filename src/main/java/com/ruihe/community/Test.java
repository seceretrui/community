package com.ruihe.community;

import javax.swing.text.StyledEditorKit;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {
    public int FirstNotRepeatingChar(String str) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i=0; i<str.length(); i++) {
            String a = str.substring(i, i+1);
            if(map.containsKey(a))
                map.put(a, map.get(a)+1);
            else
                map.put(a, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue()==1) {
                String ch = entry.getKey();
                for (int i=0; i<str.length(); i++) {
                    if(str.substring(i, i+1).equals(ch))
                        return i;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.FirstNotRepeatingChar("google");
    }
}
