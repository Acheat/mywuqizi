package com.bjlemon.bean;

import com.bjlemon.util.MyMap;
import com.bjlemon.util.Ruler;

import javax.xml.crypto.Data;
import java.io.InputStream;
import java.time.chrono.MinguoChronology;
import java.util.*;

import static com.bjlemon.bean.QiType.EmptyQi;

/***
 * �ҵ��µ�AI ר���������� ���µļ�Ч��
 */
public class MyNewAi {
    /**
     * ����
     */
    int[][] qipan;

    /***
     * �ҷ�
     */
    int myColor ;

    /***
     * ������ɫ
     */
    int ohtherColor;

    Effative effative ;
    Effative ohtherEffative ;

    static int MAX = 100000000;
    static int MIN = -100000000;

    Point root ;

    /***
     * Zobrist hash�Ż�
     */
    int[][] my;
    int[][] ohther;
    int[][] emtyp;

    /***
     * hashMap �������ݵ�����
     */
    HashMap<Integer,Integer> dateMap;


    /***
     * �߷�����
     * ���̵�ΨһHashCode+��ǰ��X��Y��+colorҪ�ߵ�
     */
    HashMap<Integer,SocreAndDepth> stepMap;

    List<Point> resultPoint = new ArrayList<>(20);

    MyMap mystepMap = new MyMap();
    /**
     * ���
     */
    int depth;

    Ruler ruler;

    /***
     * �ܹ��ߵĲ���
     */

    int sum ;

    int stepSum;

    int zorisHash = 0;

     MyCompaer compaer = new MyCompaer();

     float myRow = 1f;
     float ohtherRow = 1f;

    /***
     * ����Ϊ������
     */
    //�ܹ�����5�Ľڵ�
    ArrayList<Point> myFive = new ArrayList<>(20);

    //�ܹ�����5�Ľڵ�
    ArrayList<Point> ohtherFive = new ArrayList<Point>(20);

    //�ܹ������ĵĽڵ�
    ArrayList<Point> myFour = new ArrayList<>(20);

    //�ܹ������ĵĽڵ�
    ArrayList<Point> ohtherFour = new ArrayList<>(20);

    //�ܹ�����˫���Ľڵ�
    ArrayList<Point> myTowThree = new ArrayList<>(20);

    //�ܹ�����˫���Ľڵ�
    ArrayList<Point> ohtherTowThree = new ArrayList<>(20);



    //�ܹ��������Ľڵ�
    ArrayList<Point> ohtherThree = new ArrayList<>(20);

    //�ܹ����̶��Ľڵ�
    ArrayList<Point> myTwo = new ArrayList<>(20);

    //�ܹ����̶��Ľڵ�
    ArrayList<Point> ohtherTwo = new ArrayList<>(20);

    //�����ڵ�
    ArrayList<Point> ohtherList = new ArrayList<>(40);

    ArrayList<Point> nextNerberho = new ArrayList<>(40);


    //�ܹ��������Ľڵ�
    ArrayList<Point> myThree = new ArrayList<>(20);


    Point test ;

    Point gg;

    /***
     * ��ʼ��˼���Ĳ���
     * ���� ��Ҫ�Ľڵ�
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
          * ����������ɫ
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
             System.out.println("��ɱ�ɹ�");
             return  suansha;
         }
         */


         Date date = new Date(System.currentTimeMillis());

         System.out.println("��ʼ��ʱ");
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
         System.out.println("����ʱ�䣺"+(dates.getTime() -date.getTime()));
         System.out.println("���д�����"+mystepMap.t);
         for(Point point : resultPoint){
             System.out.println(point);
         }
         sort(resultPoint);
         root = resultPoint.get(0);

        // Random random = new Random(resultPoint.size());
        // System.out.println("size"+resultPoint.size());
        // root = resultPoint.get(random.nextInt(resultPoint.size()));

         //ѡ��ǰ�����Ǹ�


         System.out.println("�Ż�����:"+youhua);
         System.out.println("�߷��Ż�:"+stepSum);
         System.out.println("��̬����:"+sum);



         return  root;
     }



    /***
     * ����Ϊ ������ֵ ����
     * ������¼�֦
     * @param depth
     * @param fatherScore ����ķ��� ��������ѡ���� ����㵱ǰ��ֵ С�����Ѿ��е����ֵ����û��Ҫ���������ڵ���
     * @return
     */
    private Integer MaxMin(int depth,Integer fatherScore){

        if(depth <= 0 ||  ruler.isOver() != 0){
            //�Ե�ǰ�����̴���֣�������Ǻܴ�� ���ߺ�С�ģ���Ϊ���Ƕ����̵�һ������
            int overGanme = efftive();
           // System.out.println(overGanme);
            sum++;
            return  overGanme;
        }

        //�ж� ��Max����Min�� �豸 0����max 1����MIn
        int who = depth%2 ==0 ?0:1;

        /***
         * �����߷��ļ�����
         * ���������̵� ������һ���ģ��ߵĵ�����һ����
         * �򻺴�����������˭�´������� ��ֱ��ȡ
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
             * ����ֱ���жϻ����Ƿ��д�����
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

            // �������ݻ�ԭ
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



           //��ȡ����Ҫȡ���ֵ��ʱ���ҵ�ֵ�Ѿ��ȸ���ҪС�����ú����ļ���

            if( fatherScore!= null && -best < fatherScore) {
                return  best;
            }

            //��������֦
            if(who == 1 && root != null){
                if(-best < root.getScore()){
                    return  best;
                }
            }

            /*
            if(depth <= 2 && who ==0 && best < Ruler.FOUR_LIVE  && best > -Ruler.FOUR_LIVE  ){
                int success = MaxWin(7);
                if( success != 0 ){
                    //System.out.println("��ɱ�ɹ� socre:"+success*Ruler.FIVE);
                    return  success*Ruler.FIVE;
                }
            }*/



        }
        return  best;
    }


    /***
     * ������ֵ�Ż�����
     */
    int pvs (int alpah,int beta ,int depth){



        //���� ��һ���� ��ʼ����ֵ
        int bestValue = Integer.MIN_VALUE;

        //�ж� ��Max����Min�� �豸 0����max 1����MIn
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
             * ����ֱ���жϻ����Ƿ��д�����
             */

            SocreAndDepth socreAndDepth = stepMap.get(zorisHash);

            int score;
            if (socreAndDepth == null || socreAndDepth.depth < depth) {

                //�ж��Ƿ��Ѿ�����
                if(depth <= 1 ||  ruler.isOver() != 0){
                    //�Ե�ǰ�����̴���֣�������Ǻܴ�� ���ߺ�С�ģ���Ϊ���Ƕ����̵�һ������
                    score = -efftive();
                    // System.out.println(overGanme);
                    sum++;
                }else {


                    //����pvs  С���� ���� �ж� С���� ����� ��С���� ����� ���ڣ��ж��Ƿ���� ���Ƿ��б�Ҫ���д󴰿� ����
                    score = -pvs(-alpah-1,-alpah,depth-1);
                    if(score < alpah){
                        //֤�� û�б�Ҫ ����������ڣ��������ݻ�ԭ����һ����
                    }else{

                        score = -pvs( -beta, -alpah,depth-1);
                        stepMap.put(zorisHash, new SocreAndDepth(depth, score));
                    }

                }



            } else {
                score = socreAndDepth.socre;
                stepSum++;
            }

            // �������ݻ�ԭ
            qipan[x][y] = QiType.EmptyQi.getType();
            if (who == 0) {
                zorisHash = zorisHash ^ my[x][y];

            } else {
                zorisHash = zorisHash ^ ohther[x][y];

            }
            zorisHash = zorisHash ^ emtyp[x][y];

            //�ж� �Ƿ�����֦
            if(score >= beta) {


              return score;
            }

            //��������ֵ
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
     * ������ֵ�Ż�����
     */
    int apcha_beta (int alpah,int beta ,int depth){



        //���� ��һ���� ��ʼ����ֵ
        int bestValue = Integer.MIN_VALUE;

        //�ж� ��Max����Min�� �豸 0����max 1����MIn
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
             * ����ֱ���жϻ����Ƿ��д�����
             */

            SocreAndDepth socreAndDepth = stepMap.get(zorisHash);

            int score;
            if (socreAndDepth == null || socreAndDepth.depth < depth) {

                //�ж��Ƿ��Ѿ�����
                if(depth <= 1 ||  ruler.isOver() != 0){
                    //�Ե�ǰ�����̴���֣�������Ǻܴ�� ���ߺ�С�ģ���Ϊ���Ƕ����̵�һ������
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

            // �������ݻ�ԭ
            qipan[x][y] = QiType.EmptyQi.getType();
            if (who == 0) {
                zorisHash = zorisHash ^ my[x][y];

            } else {
                zorisHash = zorisHash ^ ohther[x][y];

            }
            zorisHash = zorisHash ^ emtyp[x][y];

            //�ж� �Ƿ�����֦
            if(score >= beta) {

                return score;
            }

            //��������ֵ
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
     * ������ֵ�Ż�����
     * ��Ϊmaxʼ��ѡ��һֱ�����ahpal
     */
    int max_aphal (int aphal,int beta,int depth){

        //MyMap.Node node = mystepMap.get(zorisHash,aphal,beta);
       // if(node != null){
      //      return node.score;
      //  }

        if(depth == 0 || ruler.isOver() != 0){
            //�Ե�ǰ�����̴���֣�������Ǻܴ�� ���ߺ�С�ģ���Ϊ���Ƕ����̵�һ������
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


            // �������ݻ�ԭ
            qipan[x][y] = 0;
          //  zorisHash ^= my[x][y];
         //   zorisHash ^= emtyp[x][y];

            //beta��֦
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

            //�ж��Ƿ�ȵ�ǰ��
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
     * ������ֵ�Ż�����
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

            // �������ݻ�ԭ
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
     * ������ֵ�Ż�����
     */
    int max (int depth){

        if(depth == 0 || ruler.isOver() != 0){
            //�Ե�ǰ�����̴���֣�������Ǻܴ�� ���ߺ�С�ģ���Ϊ���Ƕ����̵�һ������
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

            // �������ݻ�ԭ
            qipan[x][y] = 0;

            //�ж��Ƿ�ȵ�ǰ��
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
     * ������ֵ�Ż�����
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

            // �������ݻ�ԭ
            qipan[x][y] = 0;

          if(score < min){
             min = score;
          }
        }

        return  min;

    }


    /***
     * ��С���ڳ���
     */



    /***
     * ������ֵ�Ż�����
     * ��Ϊmaxʼ��ѡ��һֱ�����ahpal
     */
    int negMax (int aphal,int beta,int depth){



        MyMap.Node node = mystepMap.get(zorisHash,aphal,beta);
        if(node != null ){
            return  node.score;
        }



        int who = depth % 2;
        if(depth == 0 || ruler.isOver() != 0){
            //�Ե�ǰ�����̴���֣�������Ǻܴ�� ���ߺ�С�ģ���Ϊ���Ƕ����̵�һ������
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
            // �������ݻ�ԭ
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

            //�ж��Ƿ�ȵ�ǰ��
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
     * ������ֵ�Ż�����
     * ��Ϊmaxʼ��ѡ��һֱ�����ahpal
     */
    int max_window_aphal (int aphal,int beta,int depth){

        if(depth == 0 || ruler.isOver() != 0){
            //�Ե�ǰ�����̴���֣�������Ǻܴ�� ���ߺ�С�ģ���Ϊ���Ƕ����̵�һ������
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

            //������ǰ����0���������ж��Ƿ�������
            int value = min_window_beta(aphal,aphal+1,depth-1) ;
            if(value == Integer.MIN_VALUE || value > aphal ){
                score = min_window_beta(aphal,beta,depth-1);
            }


            // �������ݻ�ԭ
            qipan[x][y] = 0;

            //beta��֦
            if(beta <= score){
                youhua++;
                return  score;
            }

            //�ж��Ƿ�ȵ�ǰ��
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
     * ������ֵ�Ż�����
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


            // �������ݻ�ԭ
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



    //������ֵ�Ż�
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

            //����
            if(who == 0){
                qipan[x][y] = myColor;
            }else{
                qipan[x][y] = ohtherColor;
            }

            //��ȡ��ֵ
            score = -aphal_betaSrearch(-beta,-aphal,depth-1);

            qipan[x][y] = 0;

            //�жϼ�֦
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




    //������ֵ�Ż�
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

            //����
            if(who == 0){
                qipan[x][y] = myColor;
            }else{
                qipan[x][y] = ohtherColor;
            }

             score = -aphal_beta_MinSrearch(-aphal-1,-aphal,depth-1);
            if( score == Integer.MIN_VALUE || score > aphal && score < beta ){
                aphal = score;
                //��ȡ��ֵ
                score = -aphal_beta_MinSrearch(-beta,-aphal,depth-1);
            }


            qipan[x][y] = 0;

            //�жϼ�֦
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
            //��ʼ�㴰�ڲ���test
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
     * MTD �Ż�����
     */
    int mtd(int alpha, int beta, int depth, int test){
        int best_value;
        do {
            // �����������̽
            best_value = apcha_beta(test-1, test, depth);
            // �����alpha�ڵ�
            if (best_value < test) {
                // ���¹�ֵ���ޣ���������Ϊ�µ���ֵ̽
                test = beta = best_value;
                // ������beta�ڵ�
            } else {
                // ���¹�ֵ����
                alpha = best_value;
                // �µ���ֵ̽
                test = best_value+10 ;
                gg = this.test;
            }
        } while (alpha < beta);
        return best_value;
    }


    /***
     * ��ȡ�������ԣ����п��ܵ����Ӳ�������
     * @return
     */
    public List<Point> getPoint(){
        ArrayList<Point> points = new ArrayList<>(255);
        for(int x = 0;x < 15;x++) {
            for(int y = 0;y < 15;y++){
                if(qipan[x][y] == 0 && hasNeighbors(x,y,1)){
                    //�����ֵ����
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
     * ��ȡ�������ԣ����п��ܵ����Ӳ�������
     * @return
     */
    public  List<Point> getPoint1(){
        ArrayList<Point> points = new ArrayList<>(255);
        for(int x = 0;x < 15;x++) {
            for(int y = 0;y < 15;y++){
                if(qipan[x][y] == 0 && hasNeighbors(x,y,1)){
                    //�����ֵ����
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
        //����λ��ָ��
        int pre = mindde;
        /**
         * ѡ�ò�շ�
         * ��λΪ ��ǰ���ֵ
         * */

        //��ʼ�Ӻ���ǰ�ң���value С�ĵ�һ��ֵ
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

            //����λ��
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

            //����λ��
            array[pre] = left;
            pre = starts;

        }
        array[pre] = point;

        //�������������һ������
       //sort(start,mindde-1,array);
        //sort(mindde+1,end,array);



    }
    /***
     * ����ѡ��ķ�Χ
     * �������ݣ������Ľڵ㣬�������ʽ����
     * �Ż�����ʽ���������� ������� ��������
     * ��һ����  �߷������� ֻ���������������
     * @return
     */
    public List<Point> genPoints(int who,int depth) {


        myFive.clear();

        //�ܹ�����5�Ľڵ�
        ohtherFive.clear();

        //�ܹ������ĵĽڵ�
        myFour.clear();

        //�ܹ������ĵĽڵ�
       ohtherFour.clear();

        //�ܹ�����˫���Ľڵ�
        myTowThree.clear();

        //�ܹ�����˫���Ľڵ�
       ohtherTowThree.clear();



        //�ܹ��������Ľڵ�
       ohtherThree.clear();

        //�ܹ����̶��Ľڵ�
        myTwo.clear();

        //�ܹ����̶��Ľڵ�
       ohtherTwo.clear();

        //�����ڵ�
       ohtherList.clear();

        nextNerberho.clear();


        //�ܹ��������Ľڵ�
        myThree.clear();


        ArrayList result = new ArrayList(100);



        //�������еĿ��ܣ�Ȼ�� ��������
        for (int x = 0; x <  15; x++) {
            for (int y = 0; y < 15; y++) {
                int state = qipan[x][y];
                //�����ǰλ�ò�Ϊ��,�򲻿���
                if (state != EmptyQi.getType()) {
                    continue;
                } else if(hasNeighbors(x,y,2)){

                    //�Ե�ǰ�ڵ�����ж�,�������������,���ŵ���Ӧ�Ľڵ�����
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
                            //�Ż��� �õ� ���� �϶��� ������ �ҵ�һ��  Ҳ���� ���һ��


                            if (scoreOhther >= Ruler.FIVE) {
                                //�ж� �������Ƿ������� 5�����ż� ��û�����꣬�����Լ�Ҳ������
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



        //������ڱ�ɱ������ֱ�ӷ���
        if(ohtherFive.size() > 0 ){

            return  new ArrayList<>(ohtherFive);
        }


        //���ܳ���
        if(myFour.size() > 0){

            return  new ArrayList<>(myFour);
        }

        //��������
        if(ohtherFour.size() > 0){

            return   new ArrayList<>(ohtherFour);
        }

        //���ܳ���
        if(myTowThree.size() > 0){

            return  new ArrayList<>(myTowThree);
        }

        //�����ܳ���
        if(ohtherTowThree.size() > 0){

            return   new ArrayList<>(ohtherTowThree);
        }

        /*��Ҫ������ ���˱�ɱ��֮�⣬���������ӣ�����������շ������ġ�
        * ����Ż��Ļ���Ӧ���ǰ��� ��һ���γɵ����� �ո񣬲��Ұ��շ�������Ϊ��һ��� ��ɱ����
        * ��Ҫ����  ��һ�����ɱ�Ľڵ㣬���صĲ��㡣
        * ������ô֪������ɱ ���Ƿ����أ���˭���Ҿ���˭��ά���������ɱ�����Ǹ�����MAXmIN��ô �����ã�MaxMin �޷��жϵ�ǰ��ɱ�Ļ��Ƿ��صġ�������Ȼ��һ��֪�������Ƕ��ڻ��� �������û�취�ٴ�ִ��
        * MaxMin �л���qipan��x����y�� = color ���ڴ�ʱ ��Ϊ ��ɱ��ʶ������Ҳ֪�� who ��˭�������� ��ֻ��Ҫ �ڼ���һ�� boolean  sha �Ƿ�Ϊ ���أ��ǲ��Ϳ�����ô�����Թ��У���Ϊ�����ڵ�� ���Ƕ����﷽�ģ�����˵���Ҳ������Ӷ�
        * ʱ���Ƿ���������Ϊ�����ض������ġ�
        * ����������� ��ɱ�� û�г���ʱ�������Լ�����һ���߷����Ͷ������һ���������߷���
        * ��Ҫ�����룬x,y ��color������������ �����ӵ㣬������set����ȥ���ظ��ڵ㡣��������
        * List<Point> raceList =  getRacePoint(myBeforePoint.getX(),myBeforePoint.getY());
        List<Point> ohtherRaceList = getRacePoint(ohtherBeaforePoint.getX(),ohtherBeaforePoint.getY());

        raceList.addAll(ohtherRaceList);
        raceList.sort(compaer);
        if( raceList.size() > 0){
            return  raceList;
        }
         */



        //�������ݺϲ� �������򷵻أ���java��List ����add����


        result.addAll(nextNerberho);
        result.sort(compaer);

        if(result.size() > 10){
            return  result.subList(0,10);
        }

        return result;
    }








    /***
     * ��ȡ��ǰ���꣬������5���� ��Ϊ�յ� ����
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

        //��ȡ��ǰ��ĺ���λ��
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

        //��ȡ��ǰ��ĺ���λ��
        for(int start = startY;start < endY;start++ ){
            int state = qipan[x][start];
            if(state == QiType.EmptyQi.getType()){
                Point p = new Point(x,start);
                points.add(p);
            }

        }


        int X = startX;
        int Y = startY;
        //��ȡ��ǰ��ĺ���λ��
        for(;X < endX && Y < endY ;X++,Y++ ){
            int state = qipan[X][Y];
            if(state == QiType.EmptyQi.getType()){
                Point p = new Point(X,Y);
                points.add(p);
            }

        }


         X = startX;
         Y = endY -1;
        //��ȡ��ǰ��ĺ���λ��

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
         * ����������ɫ
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
        //��ʼ������
        initZobrist();
    }

    public MyNewAi() {

        this.ruler = new Ruler(qipan);
        my = new int[15][15];
        ohther = new  int[15][15];
        emtyp = new  int[15][15];
        dateMap = new HashMap<>(2550000);
        stepMap = new HashMap<>(2550000);
        //��ʼ������
        initZobrist();

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
     * ��ʼ��Hash ���飬
     * ����һ��64λ�� ������ΪhashCode
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
     * ��ȡ ��ǰ�� hashCode ��ֵ��Ȼ���ж��Ƿ��ظ�
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
     * ������
     * ��¼ ��ǰ������÷�
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
