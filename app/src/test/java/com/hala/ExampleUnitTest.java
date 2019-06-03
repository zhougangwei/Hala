package com.hala;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        int[][] numbers = {
                {1, 2, 3, 4, 5},
                {16, 17, 18, 19, 213},
                {15, 24, 25, 20, 44},
                {14, 23, 22, 21, 8},
                {13, 12, 11, 10, 333},
        };
        ArrayList<Integer> integers = printMatrix(numbers);
        for (Integer integer : integers) {
            System.out.println(integer+"");
        }
    }

    public static ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> als = new ArrayList<Integer>();
        if (null == matrix) {
            return null;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int _row = (1 == row % 2) ? row / 2 + 1 : row / 2;
        int _col = (1 == col % 2) ? col / 2 + 1 : col / 2;
        int x = 0, y = 0;// 起点的坐标

        while (x < _row && y < _col) {
            // print right, and x is not changed
            int right_bound = col - y;
            int down_bound = row - x;

            for (int i = y; i < right_bound; i++) {
                als.add(matrix[x][i]);
            }

            // print down, and y is not changed
            boolean flag = false;
            for (int i = x + 1; i < down_bound; i++) {
                als.add(matrix[i][right_bound - 1]);
                flag = true;
            }
            if (!flag)
                break;
            // print left
            boolean flag1 = false;
            for (int i = right_bound - 2; i >= y; i--) {
                als.add(matrix[down_bound - 1][i]);
                flag1 = true;
            }
            if (!flag1)
                break;
            // print up
            for (int i = down_bound - 2; i > x; i--) {
                als.add(matrix[i][y]);
            }
            // 打印一圈完成之后换下一个起点继续打印
            x++;
            y++;
        }
        return als;
    }

}


