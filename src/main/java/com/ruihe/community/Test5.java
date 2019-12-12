package com.ruihe.community;

public class Test5 {
    public int[] multiply(int[] A) {
        int len = A.length;
        int[] B = new int[len];
        int sum = 1;
        for (int i=0; i<len; sum*=A[i++]) {
            B[i] = sum;
        }
        sum = 1;
        for (int i=len-1; i>=0; sum*=A[i--]) {
            B[i] *= sum;
        }
        return B;
    }

    public static void main(String[] args) {
        Test5 test5 = new Test5();
        int[] arr = {2,3,4,5};
        test5.multiply(arr);
    }
}
