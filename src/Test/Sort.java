package Test;

import com.bjlemon.bean.Point;

public class Sort {
    public static void main(String[] args) {
        int[] array = new int[]{7,2,5};
        sort(0,array.length,array);

        for(int value : array){
            System.out.println(value);
        }
    }

    static void sort(int start,int end,int[] array){


        int mindde = (start+end)/2;
        int value = array[mindde];
        int starts = start;
        int ends = end -1;
        //交换位置指针
        int pre = mindde;
        /**
         * 选用插空法
         * 空位为 当前这个值
         * */

        //开始从后往前找，比value 小的第一个值
        while (starts <= ends){

            int right;
            int left;

            right = array[ends];
            while ( ends > starts && right >= value){
                ends--;
                right = array[ends];
            }

            if(starts >= ends) {
                break;
            }

            //交换位置
            array[pre] = right;
            pre = ends;


            left = array[starts];
            while ( starts < ends && left <= value){
                starts++;
                left = array[starts];
            }

            if(starts >= ends) {
                break;
            }

            //交换位置
            array[pre] = left;
            pre = starts;

        }
        array[pre] = value;

        //本层排完序后，下一层排序
        //sort(start,mindde-1,array);
        //sort(mindde+1,end,array);
    }
}
