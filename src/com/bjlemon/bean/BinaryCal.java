package com.bjlemon.bean;

public class BinaryCal {

    public static void main(String[] agrs){
        int[][] array = new int[15][15];
        array[7][7] = 1;
        MyAi myAi = new MyAi(array,1);
        System.out.println(myAi.hasNeighbors(7,6,1));
      // Point point =  myAi.think1(0,0,3,0,null);
      // System.out.println(point.getScore());

    }




    private static int i = 0;

    public static int cal(){
        i++;
        switch (i){
            case 1:
                return 8;
            case 2:
                return 7;
            case 3:
                return 3;
            case 4:
                return 9;
            case 5:
                return 8;
            case 6:
                return 1;
            case 7:
                return 8;
            case 8:
                return 8;
            case 9:
                return 9;
            case  10:
                return 9;
            case  11:
                return  9;
            case  12:
                return  3;
            case 13:
                return 4;
            case  14:
                return  9;
            case 15:
                return 3;
            case 16:
                return 4;
        }
        return  0;
    }

}
