package com.ruihe.community;

import java.util.ArrayList;

public class Test1 {
    public ArrayList<Integer> FindNumbersWithSum(int [] array, int sum) {
        ArrayList<Integer> list = new ArrayList<>();
        if (array.length==0 || array==null) return list;
        int l = 0;
        int r = array.length-1;
        int cur = array[l] + array[r];
        while (l < r) {
            if (cur == sum) {
                list.add(array[l]);
                list.add(array[r]);
                return list;
            } else if (cur < sum) {
                l++;
            } else if (cur > sum) {
                r--;
            }
        }
        return list;
    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();
        int[] arr = {1,2,4,7,11,15};
        test1.FindNumbersWithSum(arr,15);
    }

}
