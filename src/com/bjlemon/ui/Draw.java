package com.bjlemon.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.bjlemon.bean.*;
import com.bjlemon.util.IOUtil;
import com.bjlemon.util.Ruler;

/**
 * 绘制下棋区域
 * @author 柠檬学院李伟
 *
 */
public class Draw extends JPanel{

	private int py_x=75;//x偏移
	private int py_y=50;
	private User user;//玩家
	private boolean gameStart;//true进行中，false结束
	private int whichColor;//该谁下棋 1黑棋，2白棋
	private int[][] qizi;//存放棋子 0没棋子， 1黑棋，2白棋
	private ImageIcon black,white,empty,blackNow,whiteNow;//黑棋，白棋，光标
	private int gameState;//1人人对弈，2人机对弈
	private Socket s;//客户端
	private Ruler ruler;//规则
	private int move_x,move_y;
	private int step = 0;
	private MyNewAi myNewAi = new MyNewAi();
	Point p = null;
	Point lastPoint;
	SuanSha suanSha = new SuanSha();


	LinkedList<Point> recove = new LinkedList<>();
	
	public Draw(){
		
		qizi=new int[20][20];
		ruler=new Ruler(qizi);
		String basePath=this.getClass().getResource("/").getPath();
		System.out.println(basePath);
		black=new ImageIcon(basePath+"black.jpg");
		white=new ImageIcon(basePath+"white.jpg");
		empty=new ImageIcon(basePath+"empty.png");
		blackNow = new ImageIcon(basePath+"black.png");
		whiteNow = new ImageIcon(basePath+"white.png");
		this.setSize(500, 500);
		this.setBackground(new Color(220,191,157));
		user=new User();
		addListener();
	}
	
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		paintQipan(g);
		paintQizi(g);
		if(gameStart)
		{
			paintCursor(g);
		};
		
		ruler.setQizi(qizi);
		
		if(ruler.isOver()==1){
			Qipan.gameInfo.setText("黑棋胜");
			for(Point point:recove){

			}
			gameStart=false;
		}else if(ruler.isOver()==2){
			Qipan.gameInfo.setText("白棋胜");
			for(Point point:recove){
				System.out.println(point);
			}
			gameStart=false;
		}
	}
	
	/**
	 * 绘制光标
	 * @param g
	 */
	public void paintCursor(Graphics g){
		g.drawImage(empty.getImage(),30*move_x+py_x-10,30*move_y+py_y-10,20,20,this);
	}
	/**
	 * 绘制下棋区域的棋盘
	 * @param g
	 */
	public void paintQipan(Graphics g){
		
		Graphics2D g2d=(Graphics2D)g;
		
		//四条边加粗
		g2d.setStroke(new BasicStroke(3f));
		g2d.drawLine(py_x+0,py_y+0,py_x+420,py_y+0);
		g2d.drawLine(py_x+0,py_y+420,py_x+420,py_y+420);
		g2d.drawLine(py_x+0,py_y+0,py_x+0,py_y+420);
		g2d.drawLine(py_x+420,py_y+0,py_x+420,py_y+420);
		g2d.setStroke(new BasicStroke());
		
		//绘制棋盘外围的数字
		for(int i=0;i<15;i++){
			g2d.drawString((i+1)+"",30*i+py_x-3,py_y-10);
			g2d.drawString((i+1)+"",py_x-25,30*i+py_y+3);
		}
		
		for(int i=0;i<15;i++){
			//画横线
			//起点(0,0) (0,1) (0,2) 终点(14,0) (14,1)
			g2d.drawLine(py_x+0,py_y+i*30,py_x+14*30,py_y+i*30);
			//画竖线
			g2d.drawLine(py_x+i*30,py_y+0,py_x+i*30,py_y+14*30);
			
		}
		//(3,3) (3,11) (11,3) (11,11) (7,7)画上小黑点
		g2d.fillOval(py_x+30*3-3,py_y+30*3-3,6,6);
		g2d.fillOval(py_x+30*3-3,py_y+30*11-3,6,6);
		g2d.fillOval(py_x+30*11-3,py_y+30*3-3,6,6);
		g2d.fillOval(py_x+30*11-3,py_y+30*11-3,6,6);
		g2d.fillOval(py_x+30*7-3,py_y+30*7-3,6,6);
	}
	
	
	/**
	 * 绘制棋子
	 * @param g
	 */
	public void paintQizi(Graphics g){
		for(int i=0;i<20;i++){
			for(int j=0;j<20;j++){
				if(qizi[i][j]==1){
					//画一个黑棋

					g.drawImage(black.getImage(),30*i+py_x-10,30*j+py_y-10,20,20,this);
				}else if(qizi[i][j]==2){
					//画一个白棋	System.out.println("2:"+i+","+j);
					g.drawImage(white.getImage(),30*i+py_x-10,30*j+py_y-10,20,20,this);

				}
			}
		}

		/**绘制上一步 落子*/
		if(lastPoint != null){
			int i = lastPoint.getX();
			int j = lastPoint.getY();
			if(lastPoint.getScore() == 1){
				g.drawImage(blackNow.getImage(),30*i+py_x-10,30*j+py_y-10,20,20,this);
			}else{
				g.drawImage(whiteNow.getImage(),30*i+py_x-10,30*j+py_y-10,20,20,this);
			}

		}
	}

	/**
	 * 增加监听
	 */
	public void addListener(){
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				//游戏未开始
				if(!gameStart)
				{return;}
				//没有轮到自己下棋
				if(user.getQiziColor()!=whichColor){
					return;
				}

				int x=e.getX();
				int y=e.getY();
			
				if(x>=0+py_x-10&&x<=420+py_x+10&&y>=0+py_y-10&&y<=420+py_y+10){
					
					x=(int)Math.round((x-py_x)/30.0);
					y=(int)Math.round((y-py_y)/30.0);



					if(qizi[x][y]!=0){
						return;
					}
					/*
					MyAiThread myAi = new MyAiThread(qizi,user.getQiziColor());
					myAi.setXY(x,y);
					Point p =  myAi.run();
					*/
					//MyAi myAi = new MyAi(qizi,user.getQiziColor());

					Calendar nowDate =  Calendar.getInstance();

					Point point =  null;
					Calendar endDate = Calendar.getInstance();
					//System.out.println(myNewAi.getSum());
				//	System.out.println("time= "+ (endDate.get(Calendar.SECOND)-nowDate.get(Calendar.SECOND)));

					if(point != null){
						qizi[point.getX()][point.getY()]= user.getQiziColor();
						point.setQiScore(user.getQiziColor());
						recove.add(point);
						System.out.println("x:"+point.getX()+" y:"+point.getY());
					}else{

						qizi[x][y]= user.getQiziColor();
						lastPoint = new Point(x,y);
						lastPoint.setScore(user.getQiziColor());
					}


					repaint();
					changeQiziColor();
					showGameInfo();
					
					//人人对弈
					if(gameState==1){
						//通知服务器，当前用户下棋的信息
						GameData data=new GameData();
						data.setUser(user);
						data.setP(new Point(x,y));
						sendInfo2Server(data);
					}else if(gameState==2){
						//人机对弈

						computerCalNextStep(x,y);


					}
					
				}
				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
				//游戏未开始
				if(!gameStart){
					return;
				}
				
				int x=e.getX();
				int y=e.getY();
			
				if(x>=0+py_x-10&&x<=420+py_x+10&&y>=0+py_y-10&&y<=420+py_y+10){
					
					move_x=(int)Math.round((x-py_x)/30.0);
					move_y=(int)Math.round((y-py_y)/30.0);
					repaint();
				}

			}
			 
		});
	}
	
	/**
	 * 电脑来计算下一步棋
	 */
	public void computerCalNextStep(int x,int y){

		if(ruler.isOver() != 0){
			return ;
		}
		//电脑增加了AI
		Point bestPoint=this.getBestPoint();
		int computerColor=0;
		if(user.getQiziColor()==1){
			computerColor=2;
		}
		else if(user.getQiziColor()==2){
			computerColor=1;
		}




		Point point = myNewAi.think(qizi,8,computerColor);


		if(point != null ){
			qizi[point.getX()][point.getY()]= computerColor;
			System.out.println("not-null:score:"+point.getScore());
			System.out.println("other score: "+bestPoint.getScore());
			lastPoint = point;
			lastPoint.setScore(computerColor);
			//System.out.println("sum: "+ myAi.sum);

		}else{
			if(p == null){
				p = new Point(x,y);
			}else{
				p.setX(bestPoint.getX());
				p.setY(bestPoint.getY());
			}


			qizi[bestPoint.getX()][bestPoint.getY()]=computerColor;
			bestPoint.setQiScore(computerColor);
			recove.add(bestPoint);
		}
		step++;
		repaint();
		changeQiziColor();
		showGameInfo();

		



	}
	
	//电脑计算得到最优点
	public Point getBestPoint(){
		int maxScore=0;
		List<Point> allPoint=new ArrayList<Point>();
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				if(qizi[i][j]!=0){
					continue;
				}
				int tempScore=ruler.calScore(i,j,1)+ruler.calScore(i, j, 2);
				if(maxScore<tempScore){
					maxScore=tempScore;
				}
				Point p=new Point(i,j,tempScore);
				allPoint.add(p);
			}
		}
		
		List<Point> bestPoint=new ArrayList<Point>();
		for(Point p:allPoint){
			if(p.getScore()==maxScore){
				bestPoint.add(p);
			}
		}
		return bestPoint.get((int)(Math.random()*bestPoint.size()));
	}
	
	//电脑执黑的时候，在（7,7）的位置下第一步棋
	public void computerFirstStep(){
		qizi[7][7]=1;
		p = new Point(7,7);
		repaint();
		changeQiziColor();
		showGameInfo();
	}
	//连接服务器
	public void connectServer(){
		try {
			s=new Socket("127.0.0.1",8888);
			GameData d=new GameData();
			d.setUser(user);
			IOUtil.writeObject(d,s.getOutputStream());
			//启动读数据的线程
			new Thread(new GetInfoFromServer()).start();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//给服务器发信息
	public void sendInfo2Server(GameData data){
		try {
			IOUtil.writeObject(data,s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 改变棋子颜色
	 */
	public void changeQiziColor(){
		if(whichColor==1){
			whichColor=2;
		}
		else if(whichColor==2){
			whichColor=1;
		}
	}
	
	public void showGameInfo(){
		if(whichColor==1){
			Qipan.gameInfo.setText("轮到黑方下棋");
		}else if(whichColor==2){
			Qipan.gameInfo.setText("轮到白方下棋");
		}
	}
	/**
	 * 新开一盘棋
	 */
	public void playNewGame(){
		qizi=new int[20][20];
		gameStart=true;
		whichColor=1;
		step = 0;
		lastPoint = null;
		
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public int getGameState() {
		return gameState;
	}


	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	
	//从服务器读数据的线程
	class GetInfoFromServer implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					GameData data=(GameData)IOUtil.readObject(s.getInputStream());
					qizi[data.getP().getX()][data.getP().getY()]
							=data.getUser().getQiziColor();
					repaint();
					changeQiziColor();
					showGameInfo();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
