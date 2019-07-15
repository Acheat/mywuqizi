package com.bjlemon.bean;

import com.bjlemon.util.Ruler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static com.bjlemon.bean.QiType.BlackQi;
import static com.bjlemon.bean.QiType.EmptyQi;
import static com.bjlemon.bean.QiType.WhiteQi;

/***
 * �����ҵ�Ai��
 * @author ������
 * @date 2019 3 26
 *
 * ����ͨ�� ������ ��ʵ�� �����㷨
 */
public class MyAi {

    /***
     * ��Ҫ���ݵ�����
     * ����������һ�� 0 ����û������,1������죬2�������
     */
    private int qiPan[][];

    /***
     * ���̵Ŀ��
     */
    private int width;


    /***
     * ������������
     */
    private QiType myType;

    /***
     * �Է�����������
     */
    private QiType otherType;

    public int sum ;
    /***
     * ����������
     */
    private Ruler ruler;

    private Point rootPoint ;

    private int area = 10;

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    /***
     * �ݹ����� �ҿ��Ի�ȡ�����Point��,
     * @param depth ��Ҫ���ݵ����
     * @param  parentScore ����Ǹ��ڵ�� ��ֵ �Ǹ��������õġ��Ҹо���Intger �ȽϺ�,�ж��Ƿ�ΪNull ��ȷ�����ڵ��Ƿ�Ϊnull
     * @return
     */
    public Point think(int depth, int nowDepth, Integer parentScore) {
        int maxScore = 0;
        Point finalPoint = null;
        /** �ҵ�ǰӦ��վ��˭�������Ͽ�������,����Ϊ�Լ�,˫��Ϊ �Է�*/
        int who = (nowDepth % 2) == 0 ? 1 : 2;


        for (int i = 0; i < 2; i++) {
            for (int y = 0; y < 1; y++) {

                int state = 0;//qiPan[i][y];
                Point newPoint = new Point(i, y, 0);
                //�����ǰλ�ò�Ϊ��,�򲻿���
                if (state != EmptyQi.getType()) {
                    continue;
                } else if (nowDepth < depth) {
                    //���Է��ص� ��һ��,�Ǹ���ã�������˼���,����Ӫ��
                    //�����ǻ�û��ﵽָ������ȵ�ʱ��,��Ҫ�ٴ��Ʋ�� �����Ԥ�⣬���Ҵ���ЩԤ���У���ȡ����õ��Ǹ�����
                    //�жϵ�ǰ����ܲ���ѡ

                    //��ǰ�����ѡ,����ǰ��λ�� ��Ϊ��ǰ�ߵ�����,�ж�
                    int nextPoint = 0;
                    if (who == 1) {
                        nextPoint = myType.getType();
                    } else if (myType.equals(BlackQi)) {
                        nextPoint = 2;
                    } else {
                        nextPoint = 1;
                    }

                    qiPan[i][y] = nextPoint;

                    /***
                     * ���paraentScore �������Լ���Null�ģ����point ����������õ��Ǹ�
                     */
                    Integer myParanetScore = null;
                    if (finalPoint != null) {
                        myParanetScore = finalPoint.getQiScore();
                    }
                    Point point = think(depth, nowDepth + 1, myParanetScore);

                    //�����վ�������,����п϶���ѡ �ҵĵ÷�������
                    if (who == 1) {
                        //���� ���Ƿ���Ҫ���㣬�����֦�㷨

                        if (parentScore != null && point != null) {
                            if (point.getScore() > parentScore) {
                                return null;
                            }
                        }

                        if (finalPoint == null || point != null && point.getScore() > finalPoint.getScore()) {
                            //�㱻������ �Ҳ������ŵ�
                            System.out.println("depth:" + nowDepth + " fram max:" + point.getScore());
                            finalPoint = point;
                        }


                    } else if (who == 2) {
                        //վ�ڵ����Ǳ�,��Ҫ��ȡ���ٵķ���

                        if (parentScore != null && point != null) {

                            if (point.getScore() < parentScore) {
                                return null;
                            }
                        }

                        //�����֦�㷨

                        if (finalPoint == null || point != null && point.getScore() < finalPoint.getScore()) {

                            System.out.println("depth:" + nowDepth + " fram min:" + point.getScore());
                            finalPoint = point;
                        }

                    }
                    //���ݽ���ʱ,�������ݵĻ�ԭ;
                    qiPan[i][y] = 0;

                } else {
                    //���������һ���ʱ��,�Ҿ�Ҫ������,�����ľ�̬�������Ĳ���λ��f = me��û�п��ǵ�����������


                    int score = (int) (0.8 * calScore(i, y, myType) + calScore(i, y, otherType));

                    finalPoint = new Point();
                    finalPoint.setQiScore(score);
                    finalPoint.setScore(score);
                    finalPoint.setX(i);
                    finalPoint.setY(y);
                    //�����ҵ�������,��ѡȡ�ҵ÷������Ǹ�
                    return finalPoint;


                }
            }
        }
        return finalPoint;
    }

    /***
     * ��������һ���� �ķ�ֵ
     *
     * @return
     */
    public int calScore(int x, int y, QiType type) {
        return ruler.calScore(x, y, type.getType());
    }

    /***
     * ��ɨ��Ľ�ռ䣬��Ϊ�����������
     * @param x
     * @param y
     * @param depth
     * @param nowDepth
     * @param parentScore
     * @return
     */

    public Point think1(int x, int y, int depth, int nowDepth, Integer parentScore) {
        int maxScore = 0;
        Point finalPoint = null;
        /** �ҵ�ǰӦ��վ��˭�������Ͽ�������,����Ϊ�Լ�,˫��Ϊ �Է�*/
        int who = (nowDepth % 2) == 0 ? 1 : 2;
        int startx = x - area;
        int endx = x + area;

        int starty = y - area;
        int endy = y + area;

        if (startx < 0) {
            startx = 0;
        }
        if (endx > (width - 5)) {
            endx = width - 5;
        }

        if (starty < 0) {
            starty = 0;
        }
        if (endy > (width - 5)) {
            endy = width - 5;
        }
        //�����ǰ��û�� �ݹ�Ĳ�� ��ݹ�
        if (nowDepth < depth) {
            for (int i = startx; i < endx; i++) {
                for (int t = starty; t < endy; t++) {
                    int state = qiPan[i][t];
                    Point newPoint = new Point(i, t, 0);
                    //�����ǰλ�ò�Ϊ��,�򲻿���
                    if (state != EmptyQi.getType()) {
                        continue;
                    } else {
                        //���Է��ص� ��һ��,�Ǹ���ã�������˼���,����Ӫ��
                        //�����ǻ�û��ﵽָ������ȵ�ʱ��,��Ҫ�ٴ��Ʋ�� �����Ԥ�⣬���Ҵ���ЩԤ���У���ȡ����õ��Ǹ�����
                        //�жϵ�ǰ����ܲ���ѡ

                        //��ǰ�����ѡ,����ǰ��λ�� ��Ϊ��ǰ�ߵ�����,�ж�
                        int nextPoint = 0;
                        if (who == 1) {
                            nextPoint = myType.getType();
                        } else {
                            nextPoint = otherType.getType();
                        }

                        qiPan[i][t] = nextPoint;

                        Integer myParanetScore = null;
                        if (finalPoint != null) {
                            myParanetScore = finalPoint.getQiScore();
                        }

                        Point point = think1(i, t, depth, nowDepth + 1, myParanetScore);

                        qiPan[i][t] = 0;

                        //�ж� �����������ֵ������Сֵ
                        if (who == 1) {
                            //���� ���Ƿ���Ҫ���㣬�����֦�㷨
                            if (parentScore != null && point != null) {
                                if (point.getScore() > parentScore) {
                                    return null;
                                }
                            }

                            if (finalPoint == null || point != null && point.getScore() > finalPoint.getScore()) {
                                //�㱻������ �Ҳ������ŵ�
                                // System.out.println("depth:"+nowDepth+" fram max:"+point.getScore());
                                finalPoint = point;

                            }


                        } else if (who == 2) {

                            //�����֦�㷨
                            if (parentScore != null && point != null) {
                                if (point.getScore() < parentScore) {
                                    return null;
                                }
                            }


                            if (finalPoint == null || point != null && point.getScore() < finalPoint.getScore()) {

                                // System.out.println("depth:"+nowDepth+" fram min:" + point.getScore());
                                finalPoint = point;
                            }
                        }
                    }
                }
            }


        } else {
            //�ݹ鵽�� ���һ�� ���㷵��
            int score = (int) 0.8 * calScore(x, y, myType) + calScore(x, y, otherType);
            ;
            // System.out.println("score:"+score);
            finalPoint = new Point();
            finalPoint.setX(x);
            finalPoint.setY(y);
            finalPoint.setScore(score);
            finalPoint.setQiScore(score);

        }

        return finalPoint;
    }


    /***
     * ���� ����ʽ ��������ٶ�
     * �ڵݹ��ʱ�� ���� ֮���һ�����Ҫ�����ҵ�ǰ�� x,yλ�ã�ֻ�Ƿ�ֵ���������
     * @param x
     * @param y
     * @param depth
     * @param nowDepth
     * @param parentScore
     * @return
     */
    public Point think2( int x,int y,int depth, int nowDepth, Integer parentScore) {

        Point finalPoint = new Point(x,y);
        /** �ҵ�ǰӦ��վ��˭�������Ͽ�������,����Ϊ�Լ�,˫��Ϊ �Է�*/
        int who = (nowDepth % 2) == 0 ? 1 : 2;

        //�����ǰ��û�� �ݹ�Ĳ�� ��ݹ�
        if (nowDepth < depth) {


            //���õ�ǰ��һ�� �Ѿ�����
            int nextPoint = 0;
            if (who == 1) {
                nextPoint = myType.getType();
            } else {
                nextPoint = otherType.getType();
            }
            qiPan[x][y] = nextPoint;

            //���������Ѿ�����,�򷵻أ����ǶԽ����֦��
            if(ruler.isOver() != 0){
                int result = efficiency(x, y);
                finalPoint.setScore(result);
                finalPoint.setQiScore(result);
                qiPan[x][y] = 0;
                return finalPoint;
            }

            //�������н��
            List<Point> pointList = null;

            pointList = genPoints(who,nowDepth);

            //�Կ��ܵ�ѡ���������

            //���Է��ص� ��һ��,�Ǹ���ã�������˼���,����Ӫ��
            //�����ǻ�û��ﵽָ������ȵ�ʱ��,��Ҫ�ٴ��Ʋ�� �����Ԥ�⣬���Ҵ���ЩԤ���У���ȡ����õ��Ǹ�����
            //�жϵ�ǰ����ܲ���ѡ




            //��ǰ�����ѡ,����ǰ��λ�� ��Ϊ��ǰ�ߵ�����,�ж�
            for (Point casepoint : pointList) {

                //��������ݵĽ�� Ӧ�õ����������������
                Integer myParanetScore = null;
                if (finalPoint.getQiScore() != null) {
                    myParanetScore = finalPoint.getQiScore();
                }
                Point point = think2( casepoint.getX(),casepoint.getY(),depth, nowDepth + 1, myParanetScore);

                //�ж� �����������ֵ������Сֵ
                if (who == 1) {


                    if (finalPoint.getQiScore() == null || point != null && point.getScore() > finalPoint.getScore()) {
                        //�㱻������ �Ҳ������ŵ�
                        // System.out.println("depth:"+nowDepth+" fram max:"+point.getScore());
                        if(nowDepth == 0) {
                          finalPoint = point;
                          rootPoint.setQiScore(finalPoint.getQiScore());
                        }else {
                            finalPoint.setQiScore(point.getQiScore());
                        }

                    }
                    //���� ���Ƿ���Ҫ���㣬�����֦�㷨

                    if (parentScore != null && finalPoint.getQiScore() != null) {
                        if (finalPoint.getScore() > parentScore) {
                            //�ڽ��� �Խ����ȡֵ��ʱ��,������ �Ż�ȥ
                            qiPan[x][y] = 0;
                            return finalPoint;
                        }
                    }




                } else if (who == 2) {



                    if (finalPoint.getQiScore() == null || point != null && point.getScore() < finalPoint.getScore()) {

                        // System.out.println("depth:"+nowDepth+" fram min:" + point.getScore());
                        finalPoint.setQiScore(point.getQiScore());
                    }

                    //�����֦�㷨,���ߵ�����ĳһ���Ѿ�Ӯ�ˣ����ҾͲ��ڽ��б���,ֱ�ӷ���
                    if (parentScore != null && finalPoint.getQiScore() != null ) {
                        if (finalPoint.getScore() < parentScore) {
                            qiPan[x][y] = 0;
                            return finalPoint;
                        }
                    }

                    //����֦
                     /*
                    if(rootPoint.getQiScore() != null && finalPoint.getQiScore() != null){
                        if (finalPoint.getScore() < rootPoint.getQiScore()) {
                            qiPan[x][y] = 0;
                            return finalPoint;
                        }
                    }
                    */



                }
            }

            //��ԭ���ݣ����˵���һ��
            qiPan[x][y] = 0;

        } else {
            //�ݹ鵽�� ���һ�� ���㷵��
            int score = efficiency(x, y);
            ;
            // System.out.println("score:"+score);
            finalPoint = new Point();
            finalPoint.setX(x);
            finalPoint.setY(y);
            finalPoint.setScore(score);
            finalPoint.setQiScore(score);
            sum++;

        }

        return finalPoint;
    }

    /**
     * �̶�������Χ,Ѱ�Ҹ��м�ֵ��Ŀ��
     *
     * @return
     */
    public Point think3(int x, int y, int depth) {

        startX = x - area + 1;
        endX = x + area;
        sum =0;

        startY = y - area + 1;
        endY = y + area;
        if (startX < 0) {
            startX = 0;
        }
        if (startY < 0) {
            startY = 0;
        }

        if (endY > width - 5) {
            endY = width - 5;
        }
        if (endX > width - 5) {
            endX = width - 5;
        }
        rootPoint = new Point(x,y);
        Point point = think2(x, y, depth, 0, null);
        System.out.println("sum:"+sum);
        System.out.println("score:"+point.getQiScore());
        return point;
    }

    /***
     * �������Ч�溯��
     * @param x
     * @param y
     * @return
     */
    private int efficiency(int x, int y) {

        int my  = new Effative(myType.getType()).effative(qiPan);
        int ohther = new Effative(otherType.getType()).effative(qiPan);
        return my-ohther;
    }

    /***
     * ����ѡ��ķ�Χ
     * �����ĺ�ڵĵط����ǣ���û���������ҵļ���
     * �������ݣ������Ľڵ㣬�������ʽ����
     * @return
     */
    public List<Point> genPoints(int who,int depth) {


        //�ܹ�����5�Ľڵ�
        LinkedList<Point> five = new LinkedList<Point>();

        //�ܹ������ĵĽڵ�
        LinkedList<Point> four = new LinkedList<>();


        //�ܹ�����˫���Ľڵ�
        LinkedList<Point> towThree = new LinkedList<>();

        //�ܹ��������Ľڵ�
        LinkedList<Point> three = new LinkedList<>();

        //�ܹ����̶��Ľڵ�
        LinkedList<Point> two = new LinkedList<>();

        //�����ڵ�
        LinkedList<Point> ohtherList = new LinkedList<>();

        LinkedList<Point> nextNerberho = new LinkedList<>();

        //�������еĿ��ܣ�Ȼ�� ��������
        for (int x = 0; x < width -5; x++) {
            for (int y = 0; y < width -5; y++) {
                int state = qiPan[x][y];
                //�����ǰλ�ò�Ϊ��,�򲻿���
                if (state != EmptyQi.getType()) {
                    continue;
                } else if(hasNeighbors(x,y,1)){

                    //�Ե�ǰ�ڵ�����ж�,�������������,���ŵ���Ӧ�Ľڵ�����
                    int scoreMe;
                    int scoreOhther;

                    ruler.setQizi(qiPan);
                     if(who == 1){
                         scoreMe = ruler.calScore(x,y,myType.getType());
                         scoreOhther = ruler.calScore(x,y,otherType.getType());
                     }else{
                         scoreMe = ruler.calScore(x,y,otherType.getType());
                         scoreOhther = ruler.calScore(x,y,myType.getType());
                     }

                    Point point = new Point(x,y,0);

                    if(scoreMe >= Ruler.FIVE ){
                        five.addFirst(point);
                        point.setQiScore(scoreMe);
                        return five;
                    }else if(scoreOhther >= Ruler.FIVE){
                        //�ж� �������Ƿ������� 5�����ż� ��û�����꣬�����Լ�Ҳ������
                        point.setQiScore(scoreOhther);
                        five.addLast(point);
                    }else if(scoreMe >= Ruler.FOUR_LIVE){
                        point.setQiScore(scoreMe);
                        four.addFirst(point);
                    }else if(scoreOhther >= Ruler.FOUR_LIVE){
                        point.setQiScore(scoreOhther);
                        four.addLast(point);
                    } else if( scoreMe >= 2*Ruler.THREE_LIVE){
                        point.setQiScore(scoreMe);
                        towThree.addFirst(point);
                    }else if(scoreOhther >= 2*Ruler.THREE_LIVE){
                        point.setQiScore(scoreOhther);
                        towThree.addLast(point);
                    }else  if(scoreMe >= Ruler.THREE_LIVE){
                        point.setQiScore(scoreMe);
                        three.addFirst(point);
                    }else if(scoreOhther >= Ruler.THREE_LIVE){
                        point.setQiScore(scoreOhther);
                        three.addLast(point);
                    }else if(scoreMe >= Ruler.TWO_LIVE){
                        point.setQiScore(scoreMe);
                        two.addFirst(point);
                    }else if(scoreOhther >= Ruler.TWO_LIVE){
                        point.setQiScore(scoreOhther);
                        two.addLast(point);
                    }else{
                        ohtherList.add(point);
                    }

                }else if(depth <= 2 && hasNeighbors(x,y,2)){
                    Point point = new Point(x,y,0);
                    nextNerberho.add(point);
                }
            }
        }



        //������ڱ�ɱ������ֱ�ӷ���
        if(five.size() > 0){
            return  five;
        }

        if(four.size() > 0){
            return  four;
        }

        if(towThree.size() > 0){
            return  towThree;
        }



        //�������ݺϲ� �������򷵻أ���java��List ����add����
        List<Point> result = new LinkedList<>();
        result.addAll(three);
        result.addAll(two);
        result.sort(new MyCompaer());

        result.addAll(ohtherList);
        result.addAll(nextNerberho);
        if(result.size() > 10){
            return result.subList(0,10);
        }

        return result;
    }




    /**
     * �ж��Ƿ����ھ�
     * @param x
     * @param y
     * @param area  ��Χ
     * @return
     */
    public boolean hasNeighbors(int x,int y,int area){
        int startx = (x-area);
        int endx = (x+area);
        int staty = (y-area);
        int endy = y+area;

        if(startx < 0){
            startx = 0;
        }
        if (endx > width - 5){
            endx = width -5;
        }

        if(staty < 0){
            staty = 0;
        }
        if(endy > width -5){
            endy = width -5;
        }

        for(int i = startx;i <= endx ;i++){
            for(int t = staty;t <= endy ; t++){
                if(i==x && t==y){
                    continue;
                }
                int state = qiPan[i][t];
                if(state != EmptyQi.getType()){
                    return  true;
                }
            }
        }

        return false;

    }

    public int[][] getQiPan() {
        return qiPan;
    }

    public void setQiPan(int[][] qiPan) {
        this.qiPan = copyArray(qiPan);
        this.ruler = new Ruler(this.qiPan);
        this.width = qiPan[0].length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public QiType getMyType() {
        return myType;
    }

    public void setMyType(QiType myType) {
        this.myType = myType;
    }


    public MyAi(int[][] qiPan, int myType) {

        this.qiPan = copyArray(qiPan);
        this.width = qiPan.length;
        this.ruler = new Ruler(this.qiPan);

        if (myType == 0) {
            this.myType = EmptyQi;
        } else if (myType == 1) {
            this.myType = BlackQi;
            this.otherType = WhiteQi;
        } else {
            this.myType = WhiteQi;
            this.otherType = BlackQi;
        }

    }

    private int[][] copyArray(int[][] array) {
        int len = array[0].length;
        int[][] taget = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int y = 0; y < len; y++) {
                taget[i][y] = array[i][y];
            }
        }
        return taget;
    }

    public static void show(int[][] array) {


        int len = array.length;
        for (int i = 0; i < len; i++) {
            for (int y = 0; y < len; y++) {
                System.out.println(array[i][y]);
            }
        }

    }

    /***
     * �����ݽ�������,���յ�ǰ�ľ��ƽ�������
     * �Ҳ���Ҫ���ݵĶ���,��Ҳ����Ҫ�ȶ��������㷨����ֻ��Ҫ�ܹ����ٵ��ų����ݼ���
     * @param list
     * @return
     */
    public LinkedList<Point> sort(List<Point> list) {
        return null;
    }

    public class MyCompaer implements Comparator<Point> {

        /***
         * ʵ�ֱȽϷ����������� �����0,С�� С��0 ������ ����0
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(Point o1, Point o2) {

            return (o2.getScore() - o1.getScore());
        }
    }

    public class MyMinCompaer implements Comparator<Point> {

        /***
         * ʵ�ֱȽϷ����������� �����0,С�� С��0 ������ ����0
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(Point o1, Point o2) {

            return (o1.getScore() - o2.getScore());

        }


    }
}
