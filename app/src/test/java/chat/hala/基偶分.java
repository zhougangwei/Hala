package chat.hala;

import org.junit.Test;

/**
 * @author wjy on 2019/9/16/016.
 */
public class 基偶分 {
    @Test
    public void addition_isCorrect() {


        int[] ints = reOrderArray(new int[]{8,0,3, 1,1,2, 1, 4, 6, 7, 5});

        System.out.println("111111");


    }


        public int[] reOrderArray(int [] array) {
            for(int i=0;i<array.length-1;i++){
                for(int j=0;j<array.length-1;j++){
                    if(array[j]%2==0&&!(array[j+1]%2==0)){
                        int a= array[j];
                        array[j]=array[j+1];
                        array[j+1]=a;
                    }
                }
            }
            return array;

        }


}
