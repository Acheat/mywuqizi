package com.bjlemon.bean;

import com.bjlemon.util.MyMap;
import com.bjlemon.util.Ruler;

import javax.xml.crypto.Data;
import java.io.InputStream;
import java.time.chrono.MinguoChronology;
import java.util.*;

import static com.bjlemon.bean.QiType.EmptyQi;

/***
 * 我的新的AI 专门用来测试 αβ的剪效率
 */
public class MyNewAi {
    /**
     * 棋盘
     */
    int[][] qipan;

    /***
     * 我方
     */
    int myColor ;

    /***
     * 对手颜色
     */
    int ohtherColor;

    Effative effative ;
    Effative ohtherEffative ;

    static int MAX = 100000000;
    static int MIN = -100000000;

    Point root ;

    /***
     * Zobrist hash优化
     */
    int[][] my;
    int[][] ohther;
    int[][] emtyp;

    /***
     * hashMap 保存数据的内容
     */
    HashMap<Integer,Integer> dateMap;


    /***
     * 走法缓存
     * 棋盘的唯一HashCode+当前的X，Y，+color要走的
     */
    HashMap<Integer,SocreAndDepth> stepMap;

    List<Point> resultPoint = new ArrayList<>(20);

    MyMap mystepMap = new MyMap();
    /**
     * 深度
     */
    int depth;

    Ruler ruler;

    /***
     * 总共走的步数
     */

    int sum ;

    int stepSum;

    int zorisHash = 0;

     MyCompaer compaer = new MyCompaer();

     float myRow = 1f;
     float ohtherRow = 1f;

    /***
     * 以下为辅助类
     */
    //能过构成5的节点
    ArrayList<Point> myFive = new ArrayList<>(20);

    //能过构成5的节点
    ArrayList<Point> ohtherFive = new ArrayList<Point>(20);

    //能够构成四的节点
    ArrayList<Point> myFour = new ArrayList<>(20);

    //能够构成四的节点
    ArrayList<Point> ohtherFour = new ArrayList<>(20);

    //能够构成双三的节点
    ArrayList<Point> myTowThree = new ArrayList<>(20);

    //能够构成双三的节点
    ArrayList<Point> ohtherTowThree = new ArrayList<>(20);



    //能够构成三的节点
    ArrayList<Point> ohtherThree = new ArrayList<>(20);

    //能够过程二的节点
    ArrayList<Point> myTwo = new ArrayList<>(20);

    //能够过程二的节点
    ArrayList<Point> ohtherTwo = new ArrayList<>(20);

    //其他节点
    ArrayList<Point> ohtherList = new ArrayList<>(40);

    ArrayList<Point> nextNerberho = new ArrayList<>(40);


    //能够构成三的节点
    ArrayList<Point> myThree = new ArrayList<>(20);


    Point test ;

    Point gg;

    /***
     * 初始化思考的层数
     * 返回 需要的节点
     * @param depth
     * @return
     */
     public Point think(int[][] qipan,int depth,int myColor){

         sum = 0;
         stepSum = 0;
         mystepMap.t = 0;
         youhua = 0;
         root = null;
         this.myColor = myColor;
         this.depth = depth;
         resultPoint.clear();;



         /**
          * 设置棋盘颜色
          * */
         if(myColor == 1){
             ohtherColor = 2;
         }else{
             ohtherColor = 1;
         }
         effative = new Effative(myColor);
         ohtherEffative = new Effative(ohtherColor);
         this.qipan = copyArray(qipan);
         ruler.setQizi(this.qipan);
         this.zorisHash = getZobrist();
         SuanSha suanSha = new SuanSha();
         mystepMap.init();


         /*
         Point suansha = suanSha.sunSha(this.qipan,myColor,8) ;

         if(suansha != null){
             System.out.println("算杀成功");
             return  suansha;
         }
         */


         Date date = new Date(System.currentTimeMillis());

         System.out.println("开始计时");
         //System.out.println("score:"+);
         //
         /*
         int score  = 0;
         for(int nowDepth = 0; nowDepth <= depth ; nowDepth +=2){
                 score = mtd(Integer.MIN_VALUE,Integer.MAX_VALUE,nowDepth,score);
                 System.out.println("nowDepth:"+nowDepth+" socre:"+score);
         }

         System.out.println(gg);
             */
         //pvs(Integer.MIN_VALUE,Integer.MAX_VALUE,depth);

        // System.out.println( max(4));
         this.depth = 8;
         // System.out.println(max_aphal(Integer.MIN_VALUE,Integer.MAX_VALUE,4));

        System.out.println(negMax(-5000000,5000000,8));
        // System.out.println( mtdTest(Integer.MIN_VALUE,Integer.MAX_VALUE,6,0));

        // System.out.println( max_window_aphal(-5000000,5000000,5));

         Date dates = new Date(System.currentTimeMillis());
         //resultPoint = getPoint1();
         System.out.println("花费时间："+(dates.getTime() -date.getTime()));
         System.out.println("命中次数："+mystepMap.t);
         for(Point point : resultPoint){
             System.out.println(point);
         }
         sort(resultPoint);
         root = resultPoint.get(0);

        // Random random = new Random(resultPoint.size());
        // System.out.println("size"+resultPoint.size());
        // root = resultPoint.get(random.nextInt(resultPoint.size()));

         //选择当前最大的那个


         System.out.println("优化次数:"+youhua);
         System.out.println("走法优化:"+stepSum);
         System.out.println("静态计算:"+sum);



         return  root;
     }



    /***
     * 改造为 负极大值 计算
     * 引入αβ剪枝
     * @param depth
     * @param fatherScore 父类的分数 父类总数选最大的 如果你当前的值 小于我已经有的最大值，拿没必要在算其他节点了
     * @return
     */
    private Integer MaxMin(int depth,Integer fatherScore){

        if(depth <= 0 ||  ruler.isOver() != 0){
            //对当前的棋盘打个分，这个分是很大的 或者很小的，因为这是对棋盘的一个估计
            int overGanme = efftive();
           // System.out.println(overGanme);
            sum++;
            return  overGanme;
        }

        //判断 是Max还是Min层 设备 0代表max 1代表MIn
        int who = depth%2 ==0 ?0:1;

        /***
         * 缓存走法的计算结果
         * 当发现棋盘的 局势是一样的，走的点有是一样的
         * 则缓存下来，若果谁下次在碰到 则直接取
         */


        Integer best = null;
        List<Point> list = genPoints(who,depth);

        int len = list.size();
        for(int i = 0;i < len ; i++) {
            Point point = list.get(i);
            zorisHash = zorisHash ^ emtyp[point.getX()][point.getY()];
            if(who == 0){
               qipan[point.getX()][point.getY()] = myColor;
               zorisHash = zorisHash ^ my[point.getX()][point.getY()];

           }else{
               qipan[point.getX()][point.getY()] = ohtherColor;
                zorisHash = zorisHash ^ ohther[point.getX()][point.getY()];

           }

            /***
             * 计算直接判断缓存是否有此数据
             */

            SocreAndDepth socreAndDepth =  stepMap.get(zorisHash);
            int  score;
            if(socreAndDepth == null || socreAndDepth.depth < depth ){

                score = -MaxMin(depth -1,best);
                 stepMap.put(zorisHash,new SocreAndDepth(depth,score));

            }else{
                 score = socreAndDepth.socre;
                 stepSum++;
            }

            // 棋盘内容还原
           qipan[point.getX()][point.getY()] = QiType.EmptyQi.getType();
            if(who == 0){
                zorisHash = zorisHash ^ my[point.getX()][point.getY()];

            }else{
                zorisHash = zorisHash ^ ohther[point.getX()][point.getY()];

            }
            zorisHash = zorisHash ^ emtyp[point.getX()][point.getY()];

            if( best == null ||  score > best) {
                best = new Integer(score);
                //System.out.println("setValue === depth: "+depth+" best:"+best);
                if(depth == this.depth){
                    root = point;

                }
            }



           //当取父类要取最大值的时候，我的值已经比父类要小，则不用后续的计算

            if( fatherScore!= null && -best < fatherScore) {
                return  best;
            }

            //加入深层剪枝
            if(who == 1 && root != null){
                if(-best < root.getScore()){
                    return  best;
                }
            }

            /*
            if(depth <= 2 && who ==0 && best < Ruler.FOUR_LIVE  && best > -Ruler.FOUR_LIVE  ){
                int success = MaxWin(7);
                if( success != 0 ){
                    //System.out.println("算杀成功 socre:"+success*Ruler.FIVE);
                    return  success*Ruler.FIVE;
                }
            }*/



        }
        return  best;
    }


    /***
     * 负极大值优化尝试
     */
    int pvs (int alpah,int beta ,int depth){



        //设置 这一步的 初始最优值
        int bestValue = Integer.MIN_VALUE;

        //判断 是Max还是Min层 设备 0代表max 1代表MIn
        int who = depth%2 ==0 ?0:1;
        List<Point> list = genPoints(who,depth);


         int len = list.size();
        for(int i = 0;i < len  ; i++) {
            Point point = list.get(i);
            int x = point.getX();
            int y = point.getY();
            zorisHash = zorisHash ^ emtyp[x][y];
            if (who == 0) {
                qipan[x][y] = myColor;
                zorisHash = zorisHash ^ my[x][y];

            } else {
                qipan[x][y] = ohtherColor;
                zorisHash = zorisHash ^ ohther[x][y];

            }

            /***
             * 计算直接判断缓存是否有此数据
             */

            SocreAndDepth socreAndDepth = stepMap.get(zorisHash);

            int score;
            if (socreAndDepth == null || socreAndDepth.depth < depth) {

                //判断是否已经结束
                if(depth <= 1 ||  ruler.isOver() != 0){
                    //对当前的棋盘打个分，这个分是很大的 或者很小的，因为这是对棋盘的一个估计
                    score = -efftive();
                    // System.out.println(overGanme);
                    sum++;
                }else {


                    //增加pvs  小窗口 所搜 判断 小窗口 情况下 用小窗口 代替大 窗口，判断是否可以 ，是否有必要进行大窗口 搜索
                    score = -pvs(-alpah-1,-alpah,depth-1);
                    if(score < alpah){
                        //证明 没有必要 搜索这个窗口，进行数据还原，下一个。
                    }else{

                        score = -pvs( -beta, -alpah,depth-1);
                        stepMap.put(zorisHash, new SocreAndDepth(depth, score));
                    }

                }



            } else {
                score = socreAndDepth.socre;
                stepSum++;
            }

            // 棋盘内容还原
            qipan[x][y] = QiType.EmptyQi.getType();
            if (who == 0) {
                zorisHash = zorisHash ^ my[x][y];

            } else {
                zorisHash = zorisHash ^ ohther[x][y];

            }
            zorisHash = zorisHash ^ emtyp[x][y];

            //判断 是否发生剪枝
            if(score >= beta) {


              return score;
            }

            //保存最优值
            if (bestValue < score) {
                bestValue = score ;
                alpah = bestValue;
                if (depth == this.depth) {
                    root = point;

                }


                //System.out.println("setValue === depth: "+depth+" best:"+best);

            }




        }
        return  bestValue;

    }



    /***
     * 负极大值优化尝试
     */
    int apcha_beta (int alpah,int beta ,int depth){



        //设置 这一步的 初始最优值
        int bestValue = Integer.MIN_VALUE;

        //判断 是Max还是Min层 设备 0代表max 1代表MIn
        int who = depth%2 ==0 ?0:1;
        List<Point> list = genPoints(who,depth);


        int len = list.size();
        for(int i = 0;i < len  ; i++) {
            Point point = list.get(i);
            int x = point.getX();
            int y = point.getY();
            zorisHash = zorisHash ^ emtyp[x][y];
            if (who == 0) {
                qipan[x][y] = myColor;
                zorisHash = zorisHash ^ my[x][y];

            } else {
                qipan[x][y] = ohtherColor;
                zorisHash = zorisHash ^ ohther[x][y];

            }

            /***
             * 计算直接判断缓存是否有此数据
             */

            SocreAndDepth socreAndDepth = stepMap.get(zorisHash);

            int score;
            if (socreAndDepth == null || socreAndDepth.depth < depth) {

                //判断是否已经结束
                if(depth <= 1 ||  ruler.isOver() != 0){
                    //对当前的棋盘打个分，这个分是很大的 或者很小的，因为这是对棋盘的一个估计
                    score = -efftive();
                    // System.out.println(overGanme);
                    sum++;
                }else {
                        score = -pvs( -beta, -alpah,depth-1);
                        stepMap.put(zorisHash, new SocreAndDepth(depth, score));

                }

            } else {
                score = socreAndDepth.socre;
                stepSum++;
            }

            // 棋盘内容还原
            qipan[x][y] = QiType.EmptyQi.getType();
            if (who == 0) {
                zorisHash = zorisHash ^ my[x][y];

            } else {
                zorisHash = zorisHash ^ ohther[x][y];

            }
            zorisHash = zorisHash ^ emtyp[x][y];

            //判断 是否发生剪枝
            if(score >= beta) {

                return score;
            }

            //保存最优值
            if (bestValue < score) {
                bestValue = score ;
                alpah = bestValue;

                if (depth == this.depth) {
                    test = point;

                }

                //System.out.println("setValue === depth: "+depth+" best:"+best);

            }




        }
        return  bestValue;

    }



    /***
     * 负极大值优化尝试
     * 作为max始终选择，一直在体改ahpal
     */
    int max_aphal (int aphal,int beta,int depth){

        //MyMap.Node node = mystepMap.get(zorisHash,aphal,beta);
       // if(node != null){
      //      return node.score;
      //  }

        if(depth == 0 || ruler.isOver() != 0){
            //对当前的棋盘打个分，这个分是很大的 或者很小的，因为这是对棋盘的一个估计
            sum++;
            return  efftive1();
        }

        List<Point> list = getPoint1();
        int len = list.size();
        int max = Integer.MIN_VALUE;
        int score = Integer.MIN_VALUE;


        for(int i = 0;i < len  ; i++) {
            Point point = list.get(i);
            int x = point.getX();
            int y = point.getY();

            qipan[x][y] = myColor;
            //zorisHash ^= emtyp[x][y];
          //  zorisHash ^= my[x][y];

            score = min_beta(aphal,beta,depth-1);


            // 棋盘内容还原
            qipan[x][y] = 0;
          //  zorisHash ^= my[x][y];
         //   zorisHash ^= emtyp[x][y];

            //beta剪枝
            if(beta < score){
                youhua++;
                /*
                if( node == null) {
                    node = new MyMap.Node();
                    node.aphal = aphal;
                    node.beta = beta;
                    node.depth = this.depth;
                    node.score = score;
                    node.key = zorisHash;
                    mystepMap.put(zorisHash,node);
                }
                */
                return  score;
            }

            //判断是否比当前大
            if(  score >= max){

                if( score > aphal){
                    aphal = score;
                }

                if(depth == 4 ){
                    if(score == max){
                        point.setScore(score);
                        resultPoint.add( point);
                    }else{
                        resultPoint.clear();
                    }

                }
                max = score;
            }
        }

        /*
        if( node == null) {
            node = new MyMap.Node();
            node.aphal = aphal;
            node.beta = beta;
            node.depth = this.depth;
            node.score = max;
            node.key = zorisHash;
            mystepMap.put(zorisHash,node);
        }
      */
        return max;

    }



    /***
     * 负极大值优化尝试
     */
    int min_beta (int aphal,int beta, int depth){

       // MyMap.Node node = mystepMap.get(zorisHash,aphal,beta);

       // if(node != null){
        //    return node.score;
       // }


        if(depth == 0){
            sum++;
            return  efftive1();

        }

        List<Point> list = getPoint();

        int len = list.size();
        int min = Integer.MAX_VALUE;
        int score = Integer.MAX_VALUE;

        for(int i = 0;i < len  ; i++) {
            Point point = list.get(i);
            int x = point.getX();
            int y = point.getY();


            qipan[x][y] = ohtherColor;
            //zorisHash ^= emtyp[x][y];
           // zorisHash ^= ohther[x][y];

            score = max_aphal( aphal,beta,depth-1);

            // 棋盘内容还原
            qipan[x][y] = 0;
          //  zorisHash ^= ohther[x][y];
           // zorisHash ^= emtyp[x][y];


            if(aphal > score){
                youhua++;
                /*
                if( node == null) {
                    node = new MyMap.Node();
                    node.aphal = aphal;
                    node.beta = beta;
                    node.depth = this.depth;
                    node.score = score;
                    node.key = zorisHash;
                    mystepMap.put(zorisHash,node);
                }
                */
                return score;
            }
            if(score < min){
                min = score;
                if(min < beta){
                    beta = min;
                }

            }
        }

        /*
        if( node == null) {
            node = new MyMap.Node();
            node.aphal = aphal;
            node.beta = beta;
            node.depth = this.depth;
            node.score = min;
            node.key = zorisHash;
            mystepMap.put(zorisHash,node);
        }
        */
        return  min;

    }



    /***
     * 负极大值优化尝试
     */
    int max (int depth){

        if(depth == 0 || ruler.isOver() != 0){
            //对当前的棋盘打个分，这个分是很大的 或者很小的，因为这是对棋盘的一个估计
            sum++;
            return  efftive1();
        }

        List<Point> list = getPoint1();
        int len = list.size();
        int max = Integer.MIN_VALUE;
        int score = Integer.MIN_VALUE;

        for(int i = 0;i < len  ; i++) {
            Point point = list.get(i);
            int x = point.getX();
            int y = point.getY();

            qipan[x][y] = myColor;

            score = min(depth-1);

            // 棋盘内容还原
            qipan[x][y] = 0;

            //判断是否比当前大
            if(  score >= max){

                if(depth == 4){
                    if(score == max){
                        point.setQiScore(score);
                        resultPoint.add( point);
                    }else{
                        resultPoint.clear();
                    }

                }
                max = score;
            }
        }


       return max;

    }



    /***
     * 负极大值优化尝试
     */
    int min ( int depth){


        if(depth == 0 || ruler.isOver() != 0){
            sum++;
            return  efftive1();

        }

        List<Point> list = getPoint1();

        int len = list.size();
        int min = Integer.MAX_VALUE;
        int score = Integer.MAX_VALUE;

        for(int i = 0;i < len  ; i++) {
            Point point = list.get(i);
            int x = point.getX();
            int y = point.getY();

            qipan[x][y] = ohtherColor;

            score = max( depth-1);

            // 棋盘内容还原
            qipan[x][y] = 0;

          if(score < min){
             min = score;
          }
        }

        return  min;

    }


    /***
     * 极小窗口尝试
     */



    /***
     * 负极大值优化尝试
     * 作为max始终选择，一直在体改ahpal
     */
    int negMax (int aphal,int beta,int depth){



        MyMap.Node node = mystepMap.get(zorisHash,aphal,beta);
        if(node != null ){
            return  node.score;
        }



        int who = depth % 2;
        if(depth == 0 || ruler.isOver() != 0){
            //对当前的棋盘打个分，这个分是很大的 或者很小的，因为这是对棋盘的一个估计
            sum++;
            return  efftive1();
        }

        List<Point> list = genPoints(who,depth);
        int len = list.size();
        int max = Integer.MIN_VALUE;
        int score = Integer.MIN_VALUE;


        for(int i = 0;i < len  ; i++) {
            Point point = list.get(i);
            int x = point.getX();
            int y = point.getY();

            zorisHash ^= emtyp[x][y];
            if(who == 0) {
                zorisHash ^= my[x][y];
                qipan[x][y] = myColor;
            }else{
                zorisHash ^= ohther[x][y];
                qipan[x][y] = ohtherColor;
            }

            score = -negMax(-aphal-1,-aphal,depth-1);
           if(score > aphal && score < beta){
                aphal = score;
                score = -negMax(-beta,-aphal,depth-1);
            }else{
               stepSum++;
           }


            if(who == 0) {
                zorisHash ^= my[x][y];
            }else{
                zorisHash ^= ohther[x][y];
            }
            zorisHash ^= emtyp[x][y];
            // 棋盘内容还原
            qipan[x][y] = 0;


            if(score >= beta){
                youhua++;

                if(node == null){
                    node = new MyMap.Node();
                    node.aphal = aphal;
                    node.beta = beta;
                    node.score = score;
                    node.key = zorisHash;

                    mystepMap.put(zorisHash,node);
                }

                return  score;
            }

            //判断是否比当前大
            if(  score > max){

                if(depth == this.depth){

                    if(score == max){
                        point.setQiScore(score);
                        resultPoint.add( point);
                    }else{

                        resultPoint.clear();
                        point.setQiScore(score);
                        resultPoint.add( point);
                    }


                }

                max = score;
                if(aphal < max){
                    aphal = max;
                }

            }
        }


        if(node == null){
            node = new MyMap.Node();
            node.aphal = aphal;
            node.beta = beta;
            node.score = max;
            node.key = zorisHash;

            mystepMap.put(zorisHash,node);
        }


        return max;

    }

    /***
     * 负极大值优化尝试
     * 作为max始终选择，一直在体改ahpal
     */
    int max_window_aphal (int aphal,int beta,int depth){

        if(depth == 0 || ruler.isOver() != 0){
            //对当前的棋盘打个分，这个分是很大的 或者很小的，因为这是对棋盘的一个估计
            sum++;
            return  efftive1();
        }

        List<Point> list = getPoint();
        int len = list.size();
        int max = Integer.MIN_VALUE;
        int score = Integer.MIN_VALUE;

        for(int i = 0;i < len  ; i++) {
            Point point = list.get(i);
            int x = point.getX();
            int y = point.getY();

            qipan[x][y] = myColor;

            //在搜索前进行0窗口搜索判断是否有意义
            int value = min_window_beta(aphal,aphal+1,depth-1) ;
            if(value == Integer.MIN_VALUE || value > aphal ){
                score = min_window_beta(aphal,beta,depth-1);
            }


            // 棋盘内容还原
            qipan[x][y] = 0;

            //beta剪枝
            if(beta <= score){
                youhua++;
                return  score;
            }

            //判断是否比当前大
            if(  score >= max){

                if(score > aphal) {
                    aphal = score;
                }
                if(depth == 4){
                    if(score == max){
                        point.setQiScore(score);
                        resultPoint.add( point);
                    }else{
                        resultPoint.clear();
                    }
                }
                max = score;
            }
        }


        return max;

    }



    /***
     * 负极大值优化尝试
     */
    int min_window_beta (int aphal,int beta, int depth){


        if(depth == 0 || ruler.isOver() != 0){
            sum++;
            return  efftive1();

        }

        List<Point> list = getPoint();

        int len = list.size();
        int min = Integer.MAX_VALUE;
        int score = Integer.MAX_VALUE;

        for(int i = 0;i < len  ; i++) {
            Point point = list.get(i);
            int x = point.getX();
            int y = point.getY();

            qipan[x][y] = ohtherColor;

            int value = max_window_aphal(beta-1,beta,depth-1);
            if(value == Integer.MIN_VALUE || value < beta ){
                score = max_window_aphal( aphal,beta,depth-1);
            }


            // 棋盘内容还原
            qipan[x][y] = 0;

            if(aphal > score){
                youhua++;
                return score;
            }
            if(score < min){
                min = score;
                if(score < beta) {
                    beta = min;
                }
            }
        }

        return  min;

    }



    //负极大值优化
    int aphal_betaSrearch(int aphal,int beta,int depth){
        int who = depth % 2 ;
        int max = Integer.MIN_VALUE;

        if(depth == 0 || ruler.isOver() != 0){
            sum++;
            return efftive1();
        }
        List<Point> points = getPoint();
        int len  = points.size();
        int score = Integer.MIN_VALUE;
        for(int i = 0; i < len; i++ ){
            Point point = points.get(i);
            int x = point.getX();
            int y = point.getY();

            //下棋
            if(who == 0){
                qipan[x][y] = myColor;
            }else{
                qipan[x][y] = ohtherColor;
            }

            //获取分值
            score = -aphal_betaSrearch(-beta,-aphal,depth-1);

            qipan[x][y] = 0;

            //判断剪枝
            if(score >= beta){
                return score;
            }

            if(score >= max){

                if(score > aphal){
                    aphal = score;
                }

                if(depth == 4){
                    if(score == max){
                        point.setQiScore(score);
                        resultPoint.add( point);
                    }else{
                        resultPoint.clear();
                    }
                }
                max = score;

            }



        }
        return  max;

    }




    //负极大值优化
    int aphal_beta_MinSrearch(int aphal,int beta,int depth){
        int who = depth % 2;
        int max = Integer.MIN_VALUE;

        if(depth == 0 ){
            sum++;
            int value = efftive1();
            System.out.println(value);
            return value;
        }
        List<Point> points = getPoint();
        int len  = points.size();
        int score = Integer.MIN_VALUE;
        for(int i = 0; i < len; i++ ){
            Point point = points.get(i);
            int x = point.getX();
            int y = point.getY();

            //下棋
            if(who == 0){
                qipan[x][y] = myColor;
            }else{
                qipan[x][y] = ohtherColor;
            }

             score = -aphal_beta_MinSrearch(-aphal-1,-aphal,depth-1);
            if( score == Integer.MIN_VALUE || score > aphal && score < beta ){
                aphal = score;
                //获取分值
                score = -aphal_beta_MinSrearch(-beta,-aphal,depth-1);
            }


            qipan[x][y] = 0;

            //判断剪枝
            if(score >= beta){
                return score;
            }

            if(score >= max){

                if(score > aphal){
                    aphal = score;
                }

                if(depth == 4){
                    if(score == max){
                        point.setQiScore(score);
                        resultPoint.add( point);
                    }else{
                        resultPoint.clear();
                    }
                }
                max = score;

            }



        }
        return  max;

    }

    int mtdTest(int alpha,int beta,int depth,int test){
        int value = Integer.MIN_VALUE;
        do{
            //开始零窗口测试test
             value = max_aphal(test-1,test,depth);
            if(value < test){
                beta = value;
                test = (alpha+beta)/2;
            }else{
                alpha = value;
                test = alpha+10;
            }
        }while (alpha != beta);
        return value;
    }

    /***
     * MTD 优化尝试
     */
    int mtd(int alpha, int beta, int depth, int test){
        int best_value;
        do {
            // 进行零宽窗口试探
            best_value = apcha_beta(test-1, test, depth);
            // 如果是alpha节点
            if (best_value < test) {
                // 更新估值上限，并将此做为新的试探值
                test = beta = best_value;
                // 否则是beta节点
            } else {
                // 更新估值下限
                alpha = best_value;
                // 新的试探值
                test = best_value+10 ;
                gg = this.test;
            }
        } while (alpha < beta);
        return best_value;
    }


    /***
     * 获取搜索可以，所有可能的落子并且排序
     * @return
     */
    public List<Point> getPoint(){
        ArrayList<Point> points = new ArrayList<>(255);
        for(int x = 0;x < 15;x++) {
            for(int y = 0;y < 15;y++){
                if(qipan[x][y] == 0 && hasNeighbors(x,y,1)){
                    //计算分值排序
                    Point point = new Point(x,y);
                    //int socre =   effative.getRacePoint(qipan,x,y)+ohtherEffative.getRacePoint(qipan,x,y);
                    ;
                    points.add(point);
                }
            }
        }

       return  points;
    }


    /***
     * 获取搜索可以，所有可能的落子并且排序
     * @return
     */
    public  List<Point> getPoint1(){
        ArrayList<Point> points = new ArrayList<>(255);
        for(int x = 0;x < 15;x++) {
            for(int y = 0;y < 15;y++){
                if(qipan[x][y] == 0 && hasNeighbors(x,y,1)){
                    //计算分值排序
                    Point point = new Point(x,y);
                    int socre =   effative.getRacePoint(qipan,x,y)+ohtherEffative.getRacePoint(qipan,x,y);
                    point.setScore(socre);
                    points.add(point);
                }
            }
        }

        // points.sort(compaer);
       points.sort(compaer);

        return points;
    }

    /***
     *
     */
    List<Point>  sort(List<Point> points){

        for(Point point : points){
            int score = effative.getRacePoint(qipan,point.getX(),point.getY()) + ohtherEffative.getRacePoint(qipan,point.getX(),point.getY());
            point.setScore(score);

        }
        points.sort(compaer);
        return points;
    }

    void sort(int start,int end,Point[] array){
        if(start >= end){
            return ;
        }

        int mindde = (start+end)/2;
        Point point = array[mindde];
        int value = point.getScore();
        int starts = start;
        int ends = end;
        //交换位置指针
        int pre = mindde;
        /**
         * 选用插空法
         * 空位为 当前这个值
         * */

        //开始从后往前找，比value 小的第一个值
        while (starts != ends){

            Point right;
            Point left;

            right = array[ends];
            while ( ends > starts && right.getScore() >= value){
                ends--;
                right = array[ends];
            }

            if(starts >= ends) {
                break;
            }

            //交换位置
            array[pre] = right;
            pre = ends;


           left = array[starts];
            while ( starts < ends && left.getScore() <= value){
                starts++;
                left = array[starts];
            }

            if(starts >= ends) {
                break;
            }

            //交换位置
            array[pre] = left;
            pre = starts;

        }
        array[pre] = point;

        //本层排完序后，下一层排序
       //sort(start,mindde-1,array);
        //sort(mindde+1,end,array);



    }
    /***
     * 生成选择的范围
     * 增加内容，搜索的节点，添加启发式搜索
     * 优化启发式搜索，增加 米字旗的 进攻方案
     * 下一步的  走法生成上 只生成米字旗的内容
     * @return
     */
    public List<Point> genPoints(int who,int depth) {


        myFive.clear();

        //能过构成5的节点
        ohtherFive.clear();

        //能够构成四的节点
        myFour.clear();

        //能够构成四的节点
       ohtherFour.clear();

        //能够构成双三的节点
        myTowThree.clear();

        //能够构成双三的节点
       ohtherTowThree.clear();



        //能够构成三的节点
       ohtherThree.clear();

        //能够过程二的节点
        myTwo.clear();

        //能够过程二的节点
       ohtherTwo.clear();

        //其他节点
       ohtherList.clear();

        nextNerberho.clear();


        //能够构成三的节点
        myThree.clear();


        ArrayList result = new ArrayList(100);



        //搜索所有的可能，然后 进行排序
        for (int x = 0; x <  15; x++) {
            for (int y = 0; y < 15; y++) {
                int state = qipan[x][y];
                //如果当前位置不为空,则不考虑
                if (state != EmptyQi.getType()) {
                    continue;
                } else if(hasNeighbors(x,y,2)){

                    //对当前节点进行判断,看满足那种情况,并放到相应的节点数组
                    int scoreMe;
                    int scoreOhther;

                    if(who == 0) {
                        scoreMe = effative.getRacePoint(qipan,x,y);
                        scoreOhther = ohtherEffative.getRacePoint(qipan,x,y);
                    }else{
                        scoreMe = ohtherEffative.getRacePoint(qipan,x,y);
                        scoreOhther = effative.getRacePoint(qipan,x,y);
                    }




                    Point point = new Point(x,y,scoreMe+ scoreOhther);
                    if(hasNeighbors(x,y,1)) {

                        if (scoreMe >= Ruler.FIVE) {
                            myFive.add(point);
                            point.setQiScore(scoreMe + scoreOhther);
                            return new ArrayList<>(myFive);
                            //result.add(point);
                        } else {
                            //优化， 好的 落子 肯定是 即考虑 我的一方  也考虑 别的一方


                            if (scoreOhther >= Ruler.FIVE) {
                                //判断 其他人是否能连城 5，别着急 还没遍历完，可能自己也能连城
                                point.setQiScore(scoreOhther);
                                ohtherFive.add(point);
                            } else if (scoreMe >= Ruler.FOUR_LIVE) {
                                point.setQiScore(scoreMe);
                                myFour.add(point);
                            } else if (scoreOhther >= Ruler.FOUR_LIVE) {
                                point.setQiScore(scoreOhther);
                                ohtherFour.add(point);
                            } else if (scoreMe >= 2 * Ruler.THREE_LIVE) {
                                point.setQiScore(scoreMe);
                                myTowThree.add(point);
                            } else if (scoreOhther >= 2 * Ruler.THREE_LIVE) {
                                point.setQiScore(scoreOhther);
                                ohtherTowThree.add(point);
                            } else {

                                int score = scoreMe + scoreOhther;

                                point.setQiScore(score);
                                result.add(point);


                            }
                        }
                    }
                    else{
                          nextNerberho.add(point);
                        }

                }
            }
        }



        //如果存在必杀器，则直接返回
        if(ohtherFive.size() > 0 ){

            return  new ArrayList<>(ohtherFive);
        }


        //我能成四
        if(myFour.size() > 0){

            return  new ArrayList<>(myFour);
        }

        //敌人能四
        if(ohtherFour.size() > 0){

            return   new ArrayList<>(ohtherFour);
        }

        //我能成三
        if(myTowThree.size() > 0){

            return  new ArrayList<>(myTowThree);
        }

        //敌人能成三
        if(ohtherTowThree.size() > 0){

            return   new ArrayList<>(ohtherTowThree);
        }

        /*主要是现在 除了必杀棋之外，其他的棋子，都是随机按照分数来的。
        * 如果优化的话，应该是按照 上一步形成的米子 空格，并且按照分数，作为下一层的 算杀内容
        * 需要参数  上一层的算杀的节点，防守的不算。
        * 那我怎么知道是算杀 还是防守呢，靠谁，我觉得谁来维护，这个算杀点是那个，在MAXmIN中么 ，不好，MaxMin 无法判断当前是杀的还是防守的。但是虽然这一层知道，但是对于回溯 这个函数没办法再次执行
        * MaxMin 中还有qipan【x】【y】 = color ，在此时 作为 算杀标识，而且也知道 who 是谁，那这样 我只需要 在加入一个 boolean  sha 是否为 防守，那不就可以了么。可以共有，因为产生节点的 不是对哪里方的，而是说，我产生棋子额
        * 时候是否是你们因为，防守而产生的。
        * 当上述的棋局 必杀棋 没有成立时，考虑自己的下一步走法，和对面的下一步米字形走法。
        * 需要函数想，x,y ，color，返回米字形 的落子点，并且用set集合去除重复节点。后在排序。
        * List<Point> raceList =  getRacePoint(myBeforePoint.getX(),myBeforePoint.getY());
        List<Point> ohtherRaceList = getRacePoint(ohtherBeaforePoint.getX(),ohtherBeaforePoint.getY());

        raceList.addAll(ohtherRaceList);
        raceList.sort(compaer);
        if( raceList.size() > 0){
            return  raceList;
        }
         */



        //否则将内容合并 ，在排序返回，在java的List 中有add方法


        result.addAll(nextNerberho);
        result.sort(compaer);

        if(result.size() > 10){
            return  result.subList(0,10);
        }

        return result;
    }








    /***
     * 获取当前坐标，米字形5格内 不为空的 棋子
     * @param x
     * @param y
     * @return  List<Point>
     */
    private  List<Point> getRacePoint(int x,int y){
        ArrayList<Point> points = new ArrayList<>();

        int startX = 0;
        int endX = 0;
        startX = (x -4) < 0? 0:(x-4);
        endX = (x+5 < 14)?(x+5):14;

        //获取当前点的横向位置
        for(int start = startX;start < endX;start++ ){
                int state = qipan[start][y];
                if(state == QiType.EmptyQi.getType()){
                    Point p = new Point(start,y);
                    points.add(p);
                }

        }

        int startY = 0;
        int endY = 0;
        startY = (y -4) < 0 ? 0:(y-4);
        endY = (y+5 < 14)?(y+5):14;

        //获取当前点的横向位置
        for(int start = startY;start < endY;start++ ){
            int state = qipan[x][start];
            if(state == QiType.EmptyQi.getType()){
                Point p = new Point(x,start);
                points.add(p);
            }

        }


        int X = startX;
        int Y = startY;
        //获取当前点的横向位置
        for(;X < endX && Y < endY ;X++,Y++ ){
            int state = qipan[X][Y];
            if(state == QiType.EmptyQi.getType()){
                Point p = new Point(X,Y);
                points.add(p);
            }

        }


         X = startX;
         Y = endY -1;
        //获取当前点的横向位置

        for(;X < endX && Y >= startY ;X++,Y-- ){
            int state = qipan[X][Y];
            if(state == QiType.EmptyQi.getType()){
                Point p = new Point(X,Y);

                points.add(p);
            }

        }


        return points;
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

    public MyNewAi(int[][] qipan,int myColor) {
        this.qipan = copyArray(qipan);
        this.myColor = myColor;
        this.ruler = new Ruler(qipan);
        /**
         * 设置棋盘颜色
         * */
        if(myColor == 1){
            ohtherColor = 2;
        }else{
            ohtherColor = 1;
        }
        my = new int[15][15];
        ohther = new  int[15][15];
        emtyp = new  int[15][15];
        dateMap = new HashMap<>(255000);
        //初始化数组
        initZobrist();
    }

    public MyNewAi() {

        this.ruler = new Ruler(qipan);
        my = new int[15][15];
        ohther = new  int[15][15];
        emtyp = new  int[15][15];
        dateMap = new HashMap<>(2550000);
        stepMap = new HashMap<>(2550000);
        //初始化数组
        initZobrist();

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

            return  (o2.getScore() - o1.getScore());
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


    public int getSum() {
        return sum;
    }

    private int efftive(){
            int zobValue  = this.zorisHash;

        Integer date =  dateMap.get(zobValue);
        if(date == null){
            int cal =  (int) ( myRow*effative.effative(qipan) - ohtherRow*ohtherEffative.effative(qipan));
            dateMap.put(zobValue,cal);
            return  cal;
        }else{
            youhua++;
            return date;
        }

    }


    private int efftive1(){

            int cal =  effative.effative(qipan) - ohtherEffative.effative(qipan);

            return  cal;
    }

    /***
     * 初始化Hash 数组，
     * 生成一个64位的 数，作为hashCode
     */
    void initZobrist(){
        Random random = new Random();
        for(int x = 0; x < 15;x++){
            for(int y = 0; y < 15 ; y++){
               my[x][y] = random.nextInt();
               ohther[x][y] = random.nextInt();
               emtyp[x][y] = random.nextInt();
            }
        }



    }

    /***
     * 获取 当前的 hashCode 的值，然后判断是否重复
     * @return
     */
    int youhua = 0;

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

    public static void main(String[] agrs){
        int[][] qipan = new int[15][15];

        qipan[11][11] =2;
        qipan[12][10] =2;
        qipan[13][9] =2;
        qipan[14][8] =2;

        qipan[7][6] =1;
        qipan[7][8] =1;
        Calendar nowDate =  Calendar.getInstance();
        MyNewAi myNewAi = new MyNewAi();

        //  List<Point> points  = myNewAi.getRacePoint(7,7,1);

        //Iterator<Point> iterator = myNewAi.getRacePoint(7,7).iterator();
        // while (iterator.hasNext()){
        //    System.out.println(iterator.next());
        //  }
        Effative effative = new Effative(1);
        Effative ohthereffative = new Effative(2);
        Ruler ruler = new Ruler(qipan);
        ruler.setQizi(qipan);
        System.out.println(myNewAi.think(qipan,6,1));
       // System.out.println(effative.getRacePoint(qipan,7,7));
        System.out.println(ohthereffative.getRacePoint(qipan,10,12));
        // System.out.println (ruler.calScore(8,7,1));

        //System.out.println(myNewAi.MaxWin(1));

        Calendar endDate = Calendar.getInstance();
        //System.out.println(myNewAi.sum);
        // System.out.println(endDate.get(Calendar.SECOND)-nowDate.get(Calendar.SECOND));
    }

    /***
     * 辅助类
     * 记录 当前分数与得分
     */
    class  SocreAndDepth{
        Integer depth;
        Integer socre;

        public SocreAndDepth(Integer depth, Integer socre) {
            this.depth = depth;
            this.socre = socre;
        }
    }

}
