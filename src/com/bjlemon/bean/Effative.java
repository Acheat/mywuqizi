package com.bjlemon.bean;

import com.bjlemon.util.Ruler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Effative {
    private  Pattern five ;
    private Pattern  liveFour;
    private Pattern deathFour;
    private Pattern liveThree;
    private Pattern deathThree;
    private Pattern liveTwo;
    private Pattern deathTwo;

    private int color;
    private int ohtherColor;

    public static void main(String args[]){
       int c = 0;
       char cr ;
       for(int i = -128;i< 128;i++){
           c++;
           cr = (char)i;
       }

    }
    /***
     * 接受一个二维数组并进行打分
     * @param array
     * @return
     */
    public  int effative(int[][] array){

        //获取打分的字符串line
        ArrayList<String> lines = new ArrayList<>(20);
        int len = array.length;
        int width  = array[0].length;
        //获取竖线
        for(int[] line : array){
            StringBuilder stringBuilder = new StringBuilder();
            for(int i =0; i < array.length;i++){
                stringBuilder.append(line[i]);
            }
            lines.add(stringBuilder.toString());
        }
        //获取横线
        for(int x=0;x<width;x++){
            StringBuilder stringBuilder = new StringBuilder();
            for (int y= 0; y < len;y++ ){

                stringBuilder.append(array[y][x]);
            }
            lines.add(stringBuilder.toString());
        }

        //获取斜线
        for(int t = 0;t < width;t++){
            int x = t;
            int y = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while(x< width && y < len){
                stringBuilder.append(array[x][y]);
                x++;
                y++;
            }
            lines.add(stringBuilder.toString());
        }

        //获取斜线
        for(int t = 1;t < width ;t++){
            int x = 0;
            int y = t;
            StringBuilder stringBuilder = new StringBuilder();
            while(x < width && y < len){
                stringBuilder.append(array[x][y]);
                x++;
                y++;
            }
            lines.add(stringBuilder.toString());
        }



        /**获取反斜线的内容**/
        //获取斜线
        for(int t = 0;t < width ;t++){
            int x = t;
            int y = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while(x >= 0 && y < len){
                stringBuilder.append(array[x][y]);
                x--;
                y++;
            }
            lines.add(stringBuilder.toString());
        }

        //获取斜线
        for(int t = 1;t < width ;t++){
            int x = t;
            int y = len-1;
            StringBuilder stringBuilder = new StringBuilder();
            while(x < width && y >= 0){
                stringBuilder.append(array[x][y]);
                x++;
                y--;
            }
            lines.add(stringBuilder.toString());
        }

        int sum = 0;
        for(String line : lines){
           // System.out.println(line);
            sum += getSizeOfSocre(line);
        }
        //System.out.println(lines.size());

        return sum;
    }



    /***
     * 获取当前坐标，米字形5格内 不为空的 棋子
     * @param x
     * @param y
     * @return  List<Point>
     */
    public int getRacePoint(int[][] qipan,int x, int y){
        qipan[x][y] = color;
        ArrayList<String> lines = new ArrayList<>(100);

        int startX = 0;
        int endX = 0;
        startX = (x -4) < 0? 0:(x-4);
        endX = (x+4 < 14)?(x+4):14;

        //获取当前点的横向位置
        StringBuilder stringBuilder = new StringBuilder();
        for(int start = startX;start <= endX;start++ ){
            stringBuilder.append(qipan[start][y]);
        }
        lines.add(stringBuilder.toString());

        int startY = 0;
        int endY = 0;
        startY = (y -4) < 0 ? 0:(y-4);
        endY = (y+4 < 14)?(y+4):14;

        //获取当前点的横向位置
        StringBuilder stringBuilder1 = new StringBuilder();
        for(int start = startY;start <= endY;start++ ){
            stringBuilder1.append(qipan[x][start]);
        }
        lines.add(stringBuilder1.toString());

        int X = x;
        int Y = y;
        int minStep = Math.min(X-startX,Y-startY);
        X = x - minStep;
        Y = y - minStep;
        //获取当前点的横向位置
        StringBuilder stringBuilder2 = new StringBuilder();
        for(;X <= endX && Y <= endY ;X++,Y++ ){
            stringBuilder2.append(qipan[X][Y]);
        }
        lines.add(stringBuilder2.toString());

        X = x;
        Y = y;
        minStep = Math.min(x-startX,endY-y);

        X = x - minStep;
        Y = y + minStep;
        StringBuilder stringBuilder3 = new StringBuilder();
        for(;X >= startX && Y >= startY ;X++,Y-- ){
            stringBuilder3.append(qipan[X][Y]);
        }
        lines.add(stringBuilder3.toString());



        int sum = 0;

        for(String line : lines){
            // System.out.println(line);
            sum += getSizeOfSocre(line);
        }
        qipan[x][y] = QiType.EmptyQi.getType();
        //System.out.println(lines.size());

        return sum;
    }




    /***
     * 总计 各种情况出现的次数
     * @return
     */
    void initParame(){
        five = Pattern.compile(""+color+color+color+color+color);
        liveFour = Pattern.compile("0"+color+color+color+color+"0");
        deathFour = Pattern.compile("("+ohtherColor+color+color+color+color+"0"+")|("+"0"+color+color+color+color+ohtherColor+")|(^"+color+color+color+color+"0"+")|("+"0"+color+color+color+color+"$)");
        liveThree = Pattern.compile("0"+color+color+color+"0");
        deathThree = Pattern.compile("("+ohtherColor+color+color+color+"0"+")|("+"0"+color+color+color+ohtherColor+")|(^"+color+color+color+"0"+")|("+"0"+color+color+color+"$)");
        liveTwo = Pattern.compile("0"+color+color+"0");
        deathTwo = Pattern.compile("("+ohtherColor+color+color+"0"+")|("+"0"+color+color+ohtherColor+")");

    }

    public  int getSizeOfSocre(String line){
        int sum = 0;

        sum += getSize(five,line)* Ruler.FIVE;
        sum += getSize(liveFour,line)*Ruler.FOUR_LIVE;
        sum += getSize(deathFour,line)*Ruler.FOUR_DEAD;
        sum +=  getSize(liveThree,line)*Ruler.THREE_LIVE;
        sum += getSize(deathThree,line)*Ruler.THREE_DEAD;
        sum +=  getSize(liveTwo,line)*Ruler.TWO_LIVE;
        sum +=  getSize(deathTwo,line)*Ruler.TWO_DEAD;


       return sum;
    }

    private   int   getSize(Pattern pattern,String line){
       Matcher matcher =  pattern.matcher(line);
       int size = 0;
        while(matcher.find()){
            size++;
        }
        return  size;
    }

    Effative(){

    }
    Effative(int color){
        this.color = color;
        if(color == 1){
            this.ohtherColor = 2;
        }else{
            this.ohtherColor = 1;
        }
        initParame();
    }
    public void  setMyColor(int myColor){
        this.color = myColor;
        if(myColor == 1){
            this.ohtherColor = 2;
        }else{
            this.ohtherColor = 1;
        }
        initParame();
    }
}
