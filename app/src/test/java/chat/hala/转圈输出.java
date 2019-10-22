package chat.hala;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author wjy on 2019/10/22/022.
 */
public class 转圈输出 {



    @Test
    public void addition_isCorrect() {

        /*int[][] ints = {
           new int[]{1,2,3,4,5},
           new int[]{6,7,8,9,10},
           new int[]{11,12,13,14,15},
           new int[]{15,16,17,18,19},
           new int[]{20,21,22,23,24},
        };*/
       /* int[][] ints = {
                new int[]{1,2,3,4},
                new int[]{5,6,7,8},
                new int[]{9,10,11,12},
                new int[]{13,14,15,16},
        };*/
        /*int[][] ints = {
                new int[]{1,2,3},
                new int[]{5,6,7},
                new int[]{9,10,11},
        };*/
        int[][] ints = {
                new int[]{1,2,3,4,5,111},
                new int[]{6,7,8,9,10,222},
                new int[]{11,12,13,14,15,333},
                new int[]{15,16,17,18,19,444},
                new int[]{20,21,22,23,24,555},
                new int[]{25,26,27,28,29,666},
        };

        ArrayList<Integer> integers = printMatrix(ints);
        System.out.println(integers);


    }
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        ArrayList asList= new ArrayList();
        int a=matrix.length;        //总的行数
        int b=matrix[0].length;    //总的列数
        int c=a*b;                //总数
        int i=0;                    //第几个
        int j=0;                    //第几圈
        int x=0;                //x坐标
        int y=0;                //y坐标
        boolean readRow=true;        //读行
        boolean reverse=false;          //翻转
        while(i<c){
            if(readRow){
                asList.add(matrix[x][y]);
                i++;
                if(!reverse){
                    if(y+1==b-j){
                        readRow=false;
                        x++;
                    continue;
                    }
                    y++;
                }else{
                    if(y==j){
                        readRow=false;
                        x--;
                        continue;
                    }
                   y--;
                }


            }else{
                asList.add(matrix[x][y]);
                i++;
                if(!reverse){
                    if(x+1==b-j){
                        readRow=true;
                        if(x+y+2==a+b-j*2){
                            reverse=true;
                        }
                        y--;
                        continue;
                    }
                    x++;
                }else{
                    if(x-1==j){
                        readRow=true;
                        if(x+y==j+1){
                            j++;
                            reverse=false;
                        }
                        y++;
                        continue;
                    }
                    x--;
                }

            }




        }
        return asList;
    }
}
