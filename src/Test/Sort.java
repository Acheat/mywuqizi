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
        //����λ��ָ��
        int pre = mindde;
        /**
         * ѡ�ò�շ�
         * ��λΪ ��ǰ���ֵ
         * */

        //��ʼ�Ӻ���ǰ�ң���value С�ĵ�һ��ֵ
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

            //����λ��
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

            //����λ��
            array[pre] = left;
            pre = starts;

        }
        array[pre] = value;

        //�������������һ������
        //sort(start,mindde-1,array);
        //sort(mindde+1,end,array);
    }
}
