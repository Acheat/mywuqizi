package com.bjlemon.bean;

import java.util.List;

/***
 * �µ��뷨��������в�ռ�� ����
 */
public class NewThink {
    int[][] qipan ;
    int myColor;
    int ohtherColor;


    /***
     * ������в��,true ���� �γɱ�ɱ��в
     * @return
     */
    public boolean createWX(){

        //��ȡ���� ���γ�˫����в�� ��
        List<Point>  points = null;
        for(Point point : points){
            int x = point.getX();
            int y = point.getY();
            
            qipan[x][y] = myColor;
        }
        return false;
    }
}
