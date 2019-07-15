package com.bjlemon.util;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyMap {

    final  static  int HASHTABLE_BIT_NUM = 128;
    final  static  int HASHTABLE_SIZE = 1 << HASHTABLE_BIT_NUM;
    /**
     * 让2的3次方 全部为1
     * */
    final  static int  HASHTABLE_MASK = HASHTABLE_SIZE -1;

     Node[] nodes = new Node[HASHTABLE_SIZE];
     Map<Integer,Node> map = new HashMap<>(HASHTABLE_SIZE);
     public int t;

    public  void init(){
         map.clear();
     }


    public Node get(int key,int aphal,int beta) {

        Node node = map.get(key);
        if(node != null){
            t++;
        }

        return  node;
        /*
        int hashCode = key & HASHTABLE_MASK;

        Node node;
        node = nodes[hashCode];

        if(node != null && node.key == key ){
            t++;
            return  node;
        }
       return null;
       */
    }


    public Node put(Integer key, Node node) {



        //int hashCode = key & HASHTABLE_MASK;

        Node node1 = map.get(key);
        if(node1 == null || node1.key == key && node.depth >= node1.depth ){
            map.put(key,node);;
        }

        return  null;

    }





    public static class Node{
       public int aphal;
       public int beta;
       public int depth;
       public int score;
       public int key;
    }
}
