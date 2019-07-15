package com.bjlemon.bean;

/***
 * 棋子类型的枚举类
 */
public enum QiType {
    /***
     * 没有棋子
     */
    EmptyQi(0),

    /***
     * 黑棋
     */
    BlackQi(1),

    /***
     * 白棋
     */
    WhiteQi(2);

    private int type;
    QiType(int i) {
        this.type = i;
    }

    public int getType() {
        return type;
    }
}
