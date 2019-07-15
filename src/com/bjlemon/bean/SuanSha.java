package com.bjlemon.bean;

import com.bjlemon.util.ArrayUtil;
import com.bjlemon.util.Ruler;

import java.util.*;

import static com.bjlemon.bean.QiType.EmptyQi;

/***
 * ��ɱ��
 */
public class SuanSha {
    /**
     * ����
     */
    private int[][] qipan;

    /***
     * ��ɱ���
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
     * Zobrist hash�Ż�
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
            System.out.println("��ɱ�ɹ�");
            System.out.println(result);
            System.out.println("�߷�����"+step +" --- �ײ�����"+jisun);
        }else{
            System.out.println("�߷�����"+step +" --- �ײ�����"+jisun);
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
     * ��ѯ�Ƿ��Ӯ
     * ���ø����µķ�ʽ ��int  ����������
     * 1����true
     * 0 ����false
     * �������ִ��� �ҿ�����ɱ���ٴ�
     * �Ӷ� ѡ�����ŵ��Ǹ�
     * ���� ��ҪhashCode
     * @return
     */
    boolean  MaxWin(int depth){
        ruler.setQizi(qipan);
        int win = ruler.isOver();
        //������ �ǲο���Ӯ�û� ���򷵻� true ��Ӯ��
        if(myColor == win){
            return true ;
        }
        //����ǶԷ�Ӯ�û����򷵻���ûӮ
        if(ohtherColor == win){
            return  false;
        }
        //����ﵽ�� �޶ȵĻ� �㻹��ûӮ
        if(depth <= 0){
            jisun++;
            return false;
        }


        //���ݽ�ɫ��ȡ����ѡ��Ľڵ�
       Point[] points = getFourPoints(0,Ruler.THREE_LIVE);

        int len = points.length;

        //��û�н�������
        if(points.length == 0){
            return false;
        }

      for(int i= 0 ;i < len ; i++){
            Point point =  points[i];
            //��������Ѿ�����
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

            //�ж�����ô�����߷� ֻҪ�߶Ե����־ͺ���,�����һ�ֿ��Գɹ�ɱ�����Ҿ�����һ��
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
     * Min ������ߣ��ж����Ƿ��ܷ���ס
     * true �������ܷ���ס
     * @return
     */
    boolean  MinWin(int depth){

        ruler.setQizi(qipan);
        int win = ruler.isOver();
        //������ �ǲο���Ӯ�û� ���򷵻� true ��Ӯ��
        if(ohtherColor == win){
            return true ;
        }
        //����ǶԷ�Ӯ�û����򷵻���ûӮ
        if(myColor == win){
            return  false;
        }
        //����ﵽ�� �޶ȵĻ� �㻹��ûӮ
        if(depth <= 0){
            jisun++;
            return true;

        }

        //���ݽ�ɫ��ȡ����ѡ��Ľڵ�,ѡ��Է��Ѿ��� ���� ���߳��ĵĿսڵ㣬���� �ҵĻ��ģ��������ĵĽڵ�

        //���ݽ�ɫ��ȡ����ѡ��Ľڵ�
        Point[] points = getFourPoints(1,Ruler.FOUR_LIVE);

        int len = points.length;

        //��û�н�������
        if(points.length == 0){
            return true;
        }

        for(int i= 0 ;i < len ; i++){

            Point point = points[i];
            //��������Ѿ�����

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


            //����ô��ķ��ز����У���ֻҪѡһ�� �㲻��Ӯ�ļ���
            if(nextWin == false ){
                return  true;
            }
        }
        return false;

    }


    /***
     * �����ر����ɱ�ڵ㣬����who == 0 ��˵�����Լ�
     * ֻ�ҳ�  �Լ��� ��Ҫ���������߽�Ҫ���ĵĽڵ�
     * ����ϸ��
     * ����1.�ҵĳ���
     *     2.���ҵط��ĳ���
     *     3.�����ҵĳ���
     *     4.���ҵط��ĳ���
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
                    //���㵱ǰ��ķ�ֵ �������� Ҫ��
                    //Ϊ�Լ���ɱ��ս
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



    /***
     * ��ʼ��Hash ���飬
     * ����һ��64λ�� ������ΪhashCode
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
