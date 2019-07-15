package Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parame {
    static Pattern  pattern = Pattern.compile("(ab)|(t)");

    public static void main(String[] agrs){
        int c = 0;
        char cr ;
        for(byte i = -128;i< 128;i++){
            c++;
            cr = (char)i;
            System.out.println(cr);
            if(c % 10 == 0){
                System.out.println("10Îª½ç");
            }
        }

    }


    static class Obj{
        int [][] array ;

        public void setObj(int[][] array){
            this.array = array;
        }

        public Obj(int[][] array) {
            this.array = array;
        }
    }

    public static void showArray(int[][] array){
        for(int x = 0; x < array.length ;x++){
            for(int y = 0 ; y < array[0].length;y++){
                System.out.println(x+" : "+y+" = "+array[x][y]);
            }

        }
    }
}
