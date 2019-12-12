package com.ruihe.community;

import java.util.ArrayList;

public class Test2 {
    public String ReverseSentence(String str) {
        if (str.length()==0 || str==null) return str;
        ArrayList<String> list = new ArrayList<>();
        for (String s : str.split(" ")) {
            list.add(s);
        }
        if (list.size()==0) return str;
        String string = "";
        for (int i=list.size()-1; i>=0; i--) {
            if(i==0)
                string += list.get(i);
            else
                string += list.get(i) + " ";
        }
        return string;
    }

    public static void main(String[] args) {
        Test2 test2 = new Test2();
        test2.ReverseSentence(" ");
    }
}
