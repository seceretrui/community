package com.ruihe.community;

public class Test7 {
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str)
    {
        boolean[] res = new boolean[matrix.length];
        return find(matrix, rows, cols, 0, 0, str, res, 0);
    }

    public boolean find(char[] matrix, int rows, int cols, int row, int col, char[] str, boolean[] res, int i) {
        if (row<0 || row>=rows || col<0 || col>=cols) return false;
        for (int index=0; index<matrix.length; index++){
            if (i >= str.length) return true;
            if (matrix[index] == str[i] && res[index] != true) {
                res[index] = true;
                row = index / cols;
                col = index % cols;
                return find(matrix, rows, cols, row, col + 1, str, res, i++)
                        || find(matrix, rows, cols, row, col - 1, str, res, i++)
                        || find(matrix, rows, cols, row + 1, col, str, res, i++)
                        || find(matrix, rows, cols, row - 1, col, str, res, i++);
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Test7 test7 = new Test7();
        char[] m = "ABCESFCSADEE".toCharArray();
        char[] s = "ABCB".toCharArray();
        test7.hasPath(m, 3, 4, s);
    }

}
