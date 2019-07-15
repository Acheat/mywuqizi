package com.bjlemon.util;

public class ArrayUtil {

    public static  int[][] copyArray(int[][] array) {
        int len = array[0].length;
        int[][] taget = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int y = 0; y < len; y++) {
                taget[i][y] = array[i][y];
            }
        }
        return taget;
    }

    public static void main(String[] args) {
        Integer i = 1;
        System.out.println(i.hashCode());
        i++;
        System.out.println(i.hashCode());

    }
}
