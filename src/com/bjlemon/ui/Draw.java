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
 * ������������
 * @author ����ѧԺ��ΰ
 *
 */
public class Draw extends JPanel{

	private int py_x=75;//xƫ��
	private int py_y=50;
	private User user;//���
	private boolean gameStart;//true�����У�false����
	private int whichColor;//��˭���� 1���壬2����
	private int[][] qizi;//������� 0û���ӣ� 1���壬2����
	private ImageIcon black,white,empty,blackNow,whiteNow;//���壬���壬���
	private int gameState;//1���˶��ģ�2�˻�����
	private Socket s;//�ͻ���
	private Ruler ruler;//����
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
			Qipan.gameInfo.setText("����ʤ");
			for(Point point:recove){

			}
			gameStart=false;
		}else if(ruler.isOver()==2){
			Qipan.gameInfo.setText("����ʤ");
			for(Point point:recove){
				System.out.println(point);
			}
			gameStart=false;
		}
	}
	
	/**
	 * ���ƹ��
	 * @param g
	 */
	public void paintCursor(Graphics g){
		g.drawImage(empty.getImage(),30*move_x+py_x-10,30*move_y+py_y-10,20,20,this);
	}
	/**
	 * �����������������
	 * @param g
	 */
	public void paintQipan(Graphics g){
		
		Graphics2D g2d=(Graphics2D)g;
		
		//�����߼Ӵ�
		g2d.setStroke(new BasicStroke(3f));
		g2d.drawLine(py_x+0,py_y+0,py_x+420,py_y+0);
		g2d.drawLine(py_x+0,py_y+420,py_x+420,py_y+420);
		g2d.drawLine(py_x+0,py_y+0,py_x+0,py_y+420);
		g2d.drawLine(py_x+420,py_y+0,py_x+420,py_y+420);
		g2d.setStroke(new BasicStroke());
		
		//����������Χ������
		for(int i=0;i<15;i++){
			g2d.drawString((i+1)+"",30*i+py_x-3,py_y-10);
			g2d.drawString((i+1)+"",py_x-25,30*i+py_y+3);
		}
		
		for(int i=0;i<15;i++){
			//������
			//���(0,0) (0,1) (0,2) �յ�(14,0) (14,1)
			g2d.drawLine(py_x+0,py_y+i*30,py_x+14*30,py_y+i*30);
			//������
			g2d.drawLine(py_x+i*30,py_y+0,py_x+i*30,py_y+14*30);
			
		}
		//(3,3) (3,11) (11,3) (11,11) (7,7)����С�ڵ�
		g2d.fillOval(py_x+30*3-3,py_y+30*3-3,6,6);
		g2d.fillOval(py_x+30*3-3,py_y+30*11-3,6,6);
		g2d.fillOval(py_x+30*11-3,py_y+30*3-3,6,6);
		g2d.fillOval(py_x+30*11-3,py_y+30*11-3,6,6);
		g2d.fillOval(py_x+30*7-3,py_y+30*7-3,6,6);
	}
	
	
	/**
	 * ��������
	 * @param g
	 */
	public void paintQizi(Graphics g){
		for(int i=0;i<20;i++){
			for(int j=0;j<20;j++){
				if(qizi[i][j]==1){
					//��һ������

					g.drawImage(black.getImage(),30*i+py_x-10,30*j+py_y-10,20,20,this);
				}else if(qizi[i][j]==2){
					//��һ������	System.out.println("2:"+i+","+j);
					g.drawImage(white.getImage(),30*i+py_x-10,30*j+py_y-10,20,20,this);

				}
			}
		}

		/**������һ�� ����*/
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
	 * ���Ӽ���
	 */
	public void addListener(){
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {

				//��Ϸδ��ʼ
				if(!gameStart)
				{return;}
				//û���ֵ��Լ�����
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
					
					//���˶���
					if(gameState==1){
						//֪ͨ����������ǰ�û��������Ϣ
						GameData data=new GameData();
						data.setUser(user);
						data.setP(new Point(x,y));
						sendInfo2Server(data);
					}else if(gameState==2){
						//�˻�����

						computerCalNextStep(x,y);


					}
					
				}
				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
				//��Ϸδ��ʼ
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
	 * ������������һ����
	 */
	public void computerCalNextStep(int x,int y){

		if(ruler.isOver() != 0){
			return ;
		}
		//����������AI
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
	
	//���Լ���õ����ŵ�
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
	
	//����ִ�ڵ�ʱ���ڣ�7,7����λ���µ�һ����
	public void computerFirstStep(){
		qizi[7][7]=1;
		p = new Point(7,7);
		repaint();
		changeQiziColor();
		showGameInfo();
	}
	//���ӷ�����
	public void connectServer(){
		try {
			s=new Socket("127.0.0.1",8888);
			GameData d=new GameData();
			d.setUser(user);
			IOUtil.writeObject(d,s.getOutputStream());
			//���������ݵ��߳�
			new Thread(new GetInfoFromServer()).start();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//������������Ϣ
	public void sendInfo2Server(GameData data){
		try {
			IOUtil.writeObject(data,s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �ı�������ɫ
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
			Qipan.gameInfo.setText("�ֵ��ڷ�����");
		}else if(whichColor==2){
			Qipan.gameInfo.setText("�ֵ��׷�����");
		}
	}
	/**
	 * �¿�һ����
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
	
	//�ӷ����������ݵ��߳�
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
