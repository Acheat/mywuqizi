package com.bjlemon.bean;

import com.bjlemon.util.ArrayUtil;
import com.bjlemon.util.Ruler;

import java.util.*;

import static com.bjlemon.bean.QiType.EmptyQi;

/***
 * 算杀类
 */
public class SuanSha {
    /**
     * 棋盘
     */
    private int[][] qipan;

    /***
     * 算杀深度
     */
    private int depth;

    private int myColor;

    private int ohtherColor;

    private Ruler ruler;

    private Point result;

    private Effative effative;
    Effative ohtherEffative ;

    private HashMap<String,Boolean> dateMap;

    private HashMap<Integer,SuanSha.ShaResult> stepMap;

    private int zoribHash;

    /***
     * Zobrist hash优化
     */
    static int[][]  my;
    static int[][] ohther;
    static int[][] emtyp;

    private int step;
    private int jisun;






    ArrayList<Point> myFive = new ArrayList<>(40);

    ArrayList<Point> ohtherFive = new ArrayList<>(40);

    ArrayList<Point> myFour = new ArrayList<>(40);

    ArrayList<Point> ohtherFour = new ArrayList<>(40);

    Comparator<Point> comparable =  new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.getScore() - o2.getScore();
        }
    };


    public static void main(String[] agrs){
        int[][] qipan = new int[15][15];
        qipan[7][7] =2;
        qipan[7][6] =2;
        qipan[7][5] =2;

        qipan[8][7] =1;
        qipan[8][6] =1;
        qipan[8][5] =1;

        SuanSha suanSha = new SuanSha();
        System.out.print(suanSha.sunSha(qipan,1,4));


    }

    public Point sunSha(int[][] qipan,int myColor,int depth){
        this.qipan = ArrayUtil.copyArray(qipan);
        this.myColor = myColor;
        this.depth = depth;

        this.ruler.setQizi(qipan);
        this.zoribHash = getZobrist();
        step = 0;
        jisun = 0;
        if(myColor == 1){
            ohtherColor =2;
        }else{
            ohtherColor =1;
        }

        if(MaxWin(depth)){
            System.out.println("算杀成功");
            System.out.println(result);
            System.out.println("走法次数"+step +" --- 底部次数"+jisun);
        }else{
            System.out.println("走法次数"+step +" --- 底部次数"+jisun);
            return  null;
        }

        return  result;


    }

    public SuanSha() {

         my = new int[15][15];
         ohther= new int[15][15];
         emtyp = new int[15][15];
        ruler = new Ruler(my);
        initZobrist();
        ohtherEffative = new Effative();
        effative = new Effative();
        effative.setMyColor(myColor);
        ohtherEffative.setMyColor(ohtherColor);
        dateMap = new HashMap<>();
        stepMap = new HashMap<>(25000);

    }

    /***
     * 查询是否会赢
     * 采用更加新的方式 用int  来返回内容
     * 1代表true
     * 0 代表false
     * 其他数字代表 我可以算杀多少次
     * 从而 选出最优的那个
     * 首先 需要hashCode
     * @return
     */
    boolean  MaxWin(int depth){
        ruler.setQizi(qipan);
        int win = ruler.isOver();
        //如果棋局 是参考方赢得话 ，则返回 true 你赢了
        if(myColor == win){
            return true ;
        }
        //如果是对方赢得话，则返回我没赢
        if(ohtherColor == win){
            return  false;
        }
        //如果达到了 限度的话 你还是没赢
        if(depth <= 0){
            jisun++;
            return false;
        }


        //根据角色获取可以选择的节点
       Point[] points = getFourPoints(0,Ruler.THREE_LIVE);

        int len = points.length;

        //我没有进攻点了
        if(points.length == 0){
            return false;
        }

      for(int i= 0 ;i < len ; i++){
            Point point =  points[i];
            //假设这点已经下了
            int x = point.getX();
            int y = point.getY();

            qipan[x][y] = myColor;

            zoribHash = zoribHash ^ emtyp[x][y];
            zoribHash = zoribHash ^ my[x][y];

            ShaResult socreAndDepth =  stepMap.get(zoribHash);


            boolean nextWin = true;
            if(socreAndDepth != null && socreAndDepth.depth >= depth){
                nextWin = socreAndDepth.result;
            }else{
                nextWin = MinWin(depth -1);
                stepMap.put(zoribHash,new ShaResult(depth,nextWin));
                step++;
            }


            qipan[x][y] = QiType.EmptyQi.getType();
            zoribHash = zoribHash ^ my[x][y];
            zoribHash = zoribHash ^ emtyp[x][y];

            //判断我这么多种走法 只要走对的那种就好了,如果有一种可以成功杀，那我就走哪一种
            if(nextWin == false){
                if( depth == this.depth){
                    result = point;
                }
                return  true;
            }
        }
        return false;

    }

    /***
     * Min 层防守者，判断我是否能防的住
     * true 代表我能防的住
     * @return
     */
    boolean  MinWin(int depth){

        ruler.setQizi(qipan);
        int win = ruler.isOver();
        //如果棋局 是参考方赢得话 ，则返回 true 你赢了
        if(ohtherColor == win){
            return true ;
        }
        //如果是对方赢得话，则返回我没赢
        if(myColor == win){
            return  false;
        }
        //如果达到了 限度的话 你还是没赢
        if(depth <= 0){
            jisun++;
            return true;

        }

        //根据角色获取可以选择的节点,选择对放已经成 活三 或者冲四的空节点，或者 我的活四，或者死四的节点

        //根据角色获取可以选择的节点
        Point[] points = getFourPoints(1,Ruler.FOUR_LIVE);

        int len = points.length;

        //我没有进攻点了
        if(points.length == 0){
            return true;
        }

        for(int i= 0 ;i < len ; i++){

            Point point = points[i];
            //假设这点已经下了

            int x = point.getX();
            int y = point.getY();

            zoribHash = zoribHash ^ emtyp[x][y];
            zoribHash = zoribHash ^ ohther[x][y];
            qipan[x][y] = ohtherColor;

            ShaResult socreAndDepth =  stepMap.get(zoribHash);

            boolean nextWin = true;
            if(socreAndDepth != null && socreAndDepth.depth >= depth){
                nextWin = socreAndDepth.result;
            }else{
                nextWin = MaxWin(depth -1);
                stepMap.put(zoribHash,new ShaResult(depth,nextWin));
                step++;
            }


            qipan[x][y] = QiType.EmptyQi.getType();
            zoribHash = zoribHash ^ ohther[x][y];
            zoribHash = zoribHash ^ emtyp[x][y];


            //在这么多的防守策略中，我只要选一种 你不能赢的即可
            if(nextWin == false ){
                return  true;
            }
        }
        return false;

    }


    /***
     * 产生特别的算杀节点，对于who == 0 来说代表自己
     * 只找出  自己的 将要连三，或者将要冲四的节点
     * 更加细分
     * 先找1.我的成五
     *     2.再找地方的成五
     *     3.再找我的成四
     *     4.再找地方的成四
     * @param
     * @return
     */
    private Point[] getFourPoints(int color,int socre){


        ArrayList<Point> points = new ArrayList<>(40);

         myFive.clear();

        ohtherFive.clear();

        myFour.clear();

        ohtherFour.clear();


        for(int x = 0;x < 15 ;x++){
            for(int y = 0 ; y < 15 ;y++){
                int state = qipan[x][y];
                if(state != QiType.EmptyQi.getType()){
                    continue;
                }else  if(hasNeighbors(x,y,1)){
                    //计算当前点的分值 满不满足 要求
                    //为自己的杀而战
                    if(color == 0){
                        int score = effative.getRacePoint(qipan,x,y);;
                        if(score >= socre ){
                            Point p = new Point(x,y,score);
                            points.add(p);
                        }
                    }else{
                        int score = effative.getRacePoint(qipan,x,y);
                        int ohtherSocre = ohtherEffative.getRacePoint(qipan,x,y);
                        Point p = new Point(x,y);

                        if(score >= Ruler.FIVE){
                            myFive.add(p);
                        }else if(ohtherSocre >= Ruler.FIVE){
                            ohtherFive.add(p);
                        }else if(score >= Ruler.FOUR_LIVE){
                            myFour.add(p);
                        }else if(ohtherSocre >= Ruler.FOUR_LIVE){
                            ohtherFour.add(p);
                        }
                    }

                }
            }

        }
        if(color == 0){
            points.sort(comparable);

            return   points.toArray(new  Point[0] );
        }else{


            if(myFive.size() > 0){
                return   myFive.toArray(new  Point[0]);
            }

            if(ohtherFive.size() > 0){
                return   ohtherFive.toArray(new  Point[0]);
            }

            if(myFour.size() > 0){
                return myFour.toArray(new  Point[0]);
            }

            if(ohtherFive.size() >0){
                return   myFour.toArray(new  Point[0]);
            }

        }



        return (Point[]) points.toArray();

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
        if (endx > 14){
            endx = 14;
        }

        if(staty < 0){
            staty = 0;
        }
        if(endy > 14){
            endy = 14;
        }

        for(int i = startx;i <= endx ;i++){
            for(int t = staty;t <= endy ; t++){
                if(i==x && t==y){
                    continue;
                }
                int state = qipan[i][t];
                if(state != EmptyQi.getType()){
                    return  true;
                }
            }
        }

        return false;

    }



    /***
     * 初始化Hash 数组，
     * 生成一个64位的 数，作为hashCode
     */
    void initZobrist(){
        Random random = new Random();
        for(int x = 0; x < 15;x++){
            for(int y = 0; y < 15 ; y++){
                my[x][y] =100000000+ random.nextInt(999999999);
                ohther[x][y] = 10000000+random.nextInt(999999999);
                emtyp[x][y] = 10000000+random.nextInt(999999999);
            }
        }

    }


    int getZobrist(){
        int zobValue = 0;
        for (int x = 0;x < 15; x++){
            for(int y = 0; y < 15 ; y++ ){
                int state = qipan[x][y];
                if(state == myColor){
                    zobValue ^= my[x][y];
                }else if(state == ohtherColor){
                    zobValue ^= ohther[x][y];
                }else {
                    zobValue ^= emtyp[x][y];
                }

            }
        }
        return  zobValue;
    }

    public static class  ShaResult{
        int depth;
        boolean result = false;

        ShaResult(int depth,boolean result){
            this.depth = depth;
            this.result = result;
        }
    }

}
