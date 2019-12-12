package com.ruihe.community;

import java.util.ArrayList;
import java.util.Collections;

public class Test4 {
        public boolean isContinuous(int [] numbers) {
            ArrayList<Integer> n = new ArrayList<>();
            ArrayList<Integer> m = new ArrayList<>();
            for (int i=0; i<5; i++) {
                n.add(numbers[i]);
            }
            Collections.sort(n);
            for (int i=0; i<n.size()-1; i++) {
                if (n.get(i)==0) {
                    m.add(0);
                    n.remove(i);
                }
            }

            int k = m.size();
            for (int i=0; i<n.size()-1; i++) {
                int j=i+1;
                int s = n.get(j);
                int y = n.get(i);
                int l = n.get(j)-n.get(i) - 1;
                k -= l;
                if (k<0) {
                    return false;
                }
            }
            return true;
        }

    public static void main(String[] args) {
        Test4 test4 = new Test4();
        int[] arr = {0, 3, 2, 6, 4};
        test4.isContinuous(arr);
    }

}
