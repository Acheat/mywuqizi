package com.bjlemon.bean;

import sun.plugin2.jvm.RemoteJVMLauncher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/***
 * 我的AI 线程,给定固定的时间,返回计算的结果
 * 我很累想睡觉
 */
public class MyAiThread {
    int[][] qizi;
    int depth;

    /***
     * 共享一个线程池
     * 设置最大线程数 为5
     */
    private static ExecutorService threadPoolExecutor = new ScheduledThreadPoolExecutor(5);

    /***
     * 最优结果
     */
    Point point ;

    /***
     * 当前遍历的深度
     */
    int nowDepth;

    /***
     * 给定的最小时间
     */
    long time = 30;

    int color;
    /***
     * 一层一层的遍历，0,2,4 这样的顺序
     */
    int x;
    int y;

    public MyAiThread(int[][] qizi,int color) {
        this.qizi = qizi;
        this.color = color;
    }

    public  void setXY(int x,int y){
        this.x = x;
        this.y = y;
    }

    /***
     * 运行
     */
    public Point run(){
        //创建线程任务
        Stack stack1 = new Stack(qizi,1,color);
        //Stack stack2 = new Stack(qizi,2,color);
        Stack stack3 = new Stack(qizi,4,color);

        stack1.setXY(x,y);
       // stack2.setXY(x,y);
        //stack3.setXY(x,y);

      List<Stack> tasks = new ArrayList<Stack>(4);
        tasks.add(stack1);
        //tasks.add(stack2);
        tasks.add(stack3);

        try {
            List<Future<Point>> result = threadPoolExecutor.invokeAll(tasks,time,TimeUnit.SECONDS);
            for(Future<Point> future : result){
                if(future.isDone()){
                   Point  threadPoint = future.get();
                   System.out.println(threadPoint.toString());
                   if(threadPoint != null){
                       this.point = threadPoint;
                   }
                }else {
                    future.cancel(true);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {

            return  point;
        }


    }




    /***
     * 任务线程
     * 实现线程的run方法，此run方法实现，可以用线程池 同时计算不同层次的内容哈
     */
    private class Stack implements  Callable<Point>{
        /***
         * 模拟的棋盘
         */
        int[][] qiziMode;

        /***
         * 任务需要遍历的深度
         */
        int depth;

        int color;

        int x;
        int y;

        /***
         * 构造函数,将数组的内容拷贝到棋盘中
         * @param qipan
         */
        Stack(int[][] qipan,int depth,int color){
             this.qiziMode = copyArray(qipan);
             this.depth = depth;
             this.color = color;
        }

        public  void setXY(int x,int y){
            this.x = x;
            this.y = y;
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

        @Override
        public Point call() throws Exception {
            MyAi myAi = new MyAi(qiziMode,color);
            Point point = myAi.think3(x,y,depth);

            return point;
        }
    }


    /***
     * 定义结果返回类
     */
    private class Result implements  Future<Point>{

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
           return  mayInterruptIfRunning;
        }

        @Override
        public boolean isCancelled() {
            return false ;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public Point get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public Point get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }



    public synchronized Point  getPoint() {
        return point;
    }

    public synchronized void   setPoint(Point point) {
        this.point = point;
    }

    /***
     * 设置当前遍历到的Depth 值
     * @param nowDepth
     */
    private synchronized  boolean setNowDepth(int nowDepth,Point point) {
        if(nowDepth > this.nowDepth){
            this.nowDepth = nowDepth;
            return  true;
        }else{
            return  false;
        }
    }
}
