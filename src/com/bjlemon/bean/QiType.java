package com.bjlemon.bean;

/***
 * �������͵�ö����
 */
public enum QiType {
    /***
     * û������
     */
    EmptyQi(0),

    /***
     * ����
     */
    BlackQi(1),

    /***
     * ����
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
