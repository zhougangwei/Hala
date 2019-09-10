package chat.hala;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *	从尾到头打印链表
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest2 {
    @Test
    public void addition_isCorrect() {
        int [][]arr={{1,2,8,9},{4,7,10,13}};
        int [][]brr={{}};
        Find(7,arr);


    }


    public boolean Find(int target, int [][] array) {

        if(array.length==0){
            return false;
        }
        if(array[0].length==0){
            return false;
        }

        if(target<array[0][0]){
            return false;
        }
        int collength=array[0].length;
        int rowlength=array.length;

        if(target>array[rowlength-1][collength-1]){
            return false;
        }
        for(int i=0;i<rowlength;i++){
            for(int j=0;j<collength;j++){
                if(target==array[i][j]){
                    return true;
                }
            }

        }
        return false;

    }

}


