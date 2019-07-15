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
 * 这是我的Ai类
 * @author 王朝冬
 * @date 2019 3 26
 *
 * 此类通过 博弈树 来实现 博弈算法
 */
public class MyAi {

    /***
     * 我要推演的棋盘
     * 与棋盘数据一致 0 代表没有棋子,1代表黑旗，2代表白起
     */
    private int qiPan[][];

    /***
     * 棋盘的宽度
     */
    private int width;


    /***
     * 我是那种类型
     */
    private QiType myType;

    /***
     * 对方是那种类型
     */
    private QiType otherType;

    public int sum ;
    /***
     * 导入计算规则
     */
    private Ruler ruler;

    private Point rootPoint ;

    private int area = 10;

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    /***
     * 递归计算的 我可以获取的最大Point点,
     * @param depth 我要推演的深度
     * @param  parentScore 这个是父节点的 分值 是给他推算用的《我感觉用Intger 比较好,判断是否为Null 来确定父节点是否为null
     * @return
     */
    public Point think(int depth, int nowDepth, Integer parentScore) {
        int maxScore = 0;
        Point finalPoint = null;
        /** 我当前应该站在谁的立场上考虑问题,基数为自己,双数为 对方*/
        int who = (nowDepth % 2) == 0 ? 1 : 2;


        for (int i = 0; i < 2; i++) {
            for (int y = 0; y < 1; y++) {

                int state = 0;//qiPan[i][y];
                Point newPoint = new Point(i, y, 0);
                //如果当前位置不为空,则不考虑
                if (state != EmptyQi.getType()) {
                    continue;
                } else if (nowDepth < depth) {
                    //所以返回的 这一层,那个最好，这是有思想的,有阵营的
                    //当我们还没与达到指定的深度的时候,需要再次推测出 更深的预测，并且从这些预测中，获取到最好的那个返回
                    //判断当前落点能不能选

                    //当前落点能选,将当前的位置 置为当前走的棋子,判断
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
                     * 这个paraentScore 就是我自己的Null的，这个point 是子类中最好的那个
                     */
                    Integer myParanetScore = null;
                    if (finalPoint != null) {
                        myParanetScore = finalPoint.getQiScore();
                    }
                    Point point = think(depth, nowDepth + 1, myParanetScore);

                    //如果是站在我这边,则进行肯定是选 我的得分最大的了
                    if (who == 1) {
                        //插入 我是否还需要计算，插入剪枝算法

                        if (parentScore != null && point != null) {
                            if (point.getScore() > parentScore) {
                                return null;
                            }
                        }

                        if (finalPoint == null || point != null && point.getScore() > finalPoint.getScore()) {
                            //你被代替了 我才是最优的
                            System.out.println("depth:" + nowDepth + " fram max:" + point.getScore());
                            finalPoint = point;
                        }


                    } else if (who == 2) {
                        //站在敌人那边,我要获取最少的分数

                        if (parentScore != null && point != null) {

                            if (point.getScore() < parentScore) {
                                return null;
                            }
                        }

                        //插入剪枝算法

                        if (finalPoint == null || point != null && point.getScore() < finalPoint.getScore()) {

                            System.out.println("depth:" + nowDepth + " fram min:" + point.getScore());
                            finalPoint = point;
                        }

                    }
                    //推演结束时,设置数据的还原;
                    qiPan[i][y] = 0;

                } else {
                    //当我是最低一层的时候,我就要计算了,我这层的静态评估做的不到位，f = me，没有考虑到其他的因素


                    int score = (int) (0.8 * calScore(i, y, myType) + calScore(i, y, otherType));

                    finalPoint = new Point();
                    finalPoint.setQiScore(score);
                    finalPoint.setScore(score);
                    finalPoint.setX(i);
                    finalPoint.setY(y);
                    //咱在我的立场上,则选取我得分最大的那个
                    return finalPoint;


                }
            }
        }
        return finalPoint;
    }

    /***
     * 计算你这一步棋 的分值
     *
     * @return
     */
    public int calScore(int x, int y, QiType type) {
        return ruler.calScore(x, y, type.getType());
    }

    /***
     * 将扫描的解空间，改为落点的五个棋子
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
        /** 我当前应该站在谁的立场上考虑问题,基数为自己,双数为 对方*/
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
        //如果当前还没到 递归的层次 则递归
        if (nowDepth < depth) {
            for (int i = startx; i < endx; i++) {
                for (int t = starty; t < endy; t++) {
                    int state = qiPan[i][t];
                    Point newPoint = new Point(i, t, 0);
                    //如果当前位置不为空,则不考虑
                    if (state != EmptyQi.getType()) {
                        continue;
                    } else {
                        //所以返回的 这一层,那个最好，这是有思想的,有阵营的
                        //当我们还没与达到指定的深度的时候,需要再次推测出 更深的预测，并且从这些预测中，获取到最好的那个返回
                        //判断当前落点能不能选

                        //当前落点能选,将当前的位置 置为当前走的棋子,判断
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

                        //判断 这个结果是最大值还是最小值
                        if (who == 1) {
                            //插入 我是否还需要计算，插入剪枝算法
                            if (parentScore != null && point != null) {
                                if (point.getScore() > parentScore) {
                                    return null;
                                }
                            }

                            if (finalPoint == null || point != null && point.getScore() > finalPoint.getScore()) {
                                //你被代替了 我才是最优的
                                // System.out.println("depth:"+nowDepth+" fram max:"+point.getScore());
                                finalPoint = point;

                            }


                        } else if (who == 2) {

                            //插入剪枝算法
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
            //递归到了 最后一层 计算返回
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
     * 尝试 启发式 提高搜索速度
     * 在递归的时候 饭后 之后我还是需要保留我当前的 x,y位置，只是分值，变成子类
     * @param x
     * @param y
     * @param depth
     * @param nowDepth
     * @param parentScore
     * @return
     */
    public Point think2( int x,int y,int depth, int nowDepth, Integer parentScore) {

        Point finalPoint = new Point(x,y);
        /** 我当前应该站在谁的立场上考虑问题,基数为自己,双数为 对方*/
        int who = (nowDepth % 2) == 0 ? 1 : 2;

        //如果当前还没到 递归的层次 则递归
        if (nowDepth < depth) {


            //设置当前这一步 已经走了
            int nextPoint = 0;
            if (who == 1) {
                nextPoint = myType.getType();
            } else {
                nextPoint = otherType.getType();
            }
            qiPan[x][y] = nextPoint;

            //如果发棋局已经结束,则返回，这是对结果剪枝，
            if(ruler.isOver() != 0){
                int result = efficiency(x, y);
                finalPoint.setScore(result);
                finalPoint.setQiScore(result);
                qiPan[x][y] = 0;
                return finalPoint;
            }

            //保存所有结果
            List<Point> pointList = null;

            pointList = genPoints(who,nowDepth);

            //对可能的选项进项排序

            //所以返回的 这一层,那个最好，这是有思想的,有阵营的
            //当我们还没与达到指定的深度的时候,需要再次推测出 更深的预测，并且从这些预测中，获取到最好的那个返回
            //判断当前落点能不能选




            //当前落点能选,将当前的位置 置为当前走的棋子,判断
            for (Point casepoint : pointList) {

                //将子类回溯的结果 应用到其他子类的搜索上
                Integer myParanetScore = null;
                if (finalPoint.getQiScore() != null) {
                    myParanetScore = finalPoint.getQiScore();
                }
                Point point = think2( casepoint.getX(),casepoint.getY(),depth, nowDepth + 1, myParanetScore);

                //判断 这个结果是最大值还是最小值
                if (who == 1) {


                    if (finalPoint.getQiScore() == null || point != null && point.getScore() > finalPoint.getScore()) {
                        //你被代替了 我才是最优的
                        // System.out.println("depth:"+nowDepth+" fram max:"+point.getScore());
                        if(nowDepth == 0) {
                          finalPoint = point;
                          rootPoint.setQiScore(finalPoint.getQiScore());
                        }else {
                            finalPoint.setQiScore(point.getQiScore());
                        }

                    }
                    //插入 我是否还需要计算，插入剪枝算法

                    if (parentScore != null && finalPoint.getQiScore() != null) {
                        if (finalPoint.getScore() > parentScore) {
                            //在结束 对结果的取值的时候,将数据 放回去
                            qiPan[x][y] = 0;
                            return finalPoint;
                        }
                    }




                } else if (who == 2) {



                    if (finalPoint.getQiScore() == null || point != null && point.getScore() < finalPoint.getScore()) {

                        // System.out.println("depth:"+nowDepth+" fram min:" + point.getScore());
                        finalPoint.setQiScore(point.getQiScore());
                    }

                    //插入剪枝算法,或者当我有某一步已经赢了，那我就不在进行遍历,直接返回
                    if (parentScore != null && finalPoint.getQiScore() != null ) {
                        if (finalPoint.getScore() < parentScore) {
                            qiPan[x][y] = 0;
                            return finalPoint;
                        }
                    }

                    //深层剪枝
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

            //还原数据，回退到上一层
            qiPan[x][y] = 0;

        } else {
            //递归到了 最后一层 计算返回
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
     * 固定搜索范围,寻找更有价值的目标
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
     * 五子棋的效益函数
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
     * 生成选择的范围
     * 我最大的后悔的地方就是，我没能力保护我的家人
     * 增加内容，搜索的节点，添加启发式搜索
     * @return
     */
    public List<Point> genPoints(int who,int depth) {


        //能过构成5的节点
        LinkedList<Point> five = new LinkedList<Point>();

        //能够构成四的节点
        LinkedList<Point> four = new LinkedList<>();


        //能够构成双三的节点
        LinkedList<Point> towThree = new LinkedList<>();

        //能够构成三的节点
        LinkedList<Point> three = new LinkedList<>();

        //能够过程二的节点
        LinkedList<Point> two = new LinkedList<>();

        //其他节点
        LinkedList<Point> ohtherList = new LinkedList<>();

        LinkedList<Point> nextNerberho = new LinkedList<>();

        //搜索所有的可能，然后 进行排序
        for (int x = 0; x < width -5; x++) {
            for (int y = 0; y < width -5; y++) {
                int state = qiPan[x][y];
                //如果当前位置不为空,则不考虑
                if (state != EmptyQi.getType()) {
                    continue;
                } else if(hasNeighbors(x,y,1)){

                    //对当前节点进行判断,看满足那种情况,并放到相应的节点数组
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
                        //判断 其他人是否能连城 5，别着急 还没遍历完，可能自己也能连城
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



        //如果存在必杀器，则直接返回
        if(five.size() > 0){
            return  five;
        }

        if(four.size() > 0){
            return  four;
        }

        if(towThree.size() > 0){
            return  towThree;
        }



        //否则将内容合并 ，在排序返回，在java的List 中有add方法
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
     * 判断是否有邻居
     * @param x
     * @param y
     * @param area  范围
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
     * 将内容进行排序,按照当前的局势进行排序
     * 我不需要内容的多少,我也不需要稳定的排序算法，我只需要能够快速的排出内容即可
     * @param list
     * @return
     */
    public LinkedList<Point> sort(List<Point> list) {
        return null;
    }

    public class MyCompaer implements Comparator<Point> {

        /***
         * 实现比较方法，如果相等 则大于0,小于 小于0 ，大于 大于0
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
         * 实现比较方法，如果相等 则大于0,小于 小于0 ，大于 大于0
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
