package com.bjlemon.bean;

import java.util.List;

/***
 * 新的想法，基于威胁空间的 搜索
 */
public class NewThink {
    int[][] qipan ;
    int myColor;
    int ohtherColor;


    /***
     * 构建威胁树,true 代表 形成必杀威胁
     * @return
     */
    public boolean createWX(){

        //获取所有 能形成双重威胁的 点
        List<Point>  points = null;
        for(Point point : points){
            int x = point.getX();
            int y = point.getY();
            
            qipan[x][y] = myColor;
        }
        return false;
    }
}
