package com.bjlemon.util;

/**
 * 五子棋规则制定
 * @author 柠檬学院李伟
 *
 */
public class Ruler {

	private int[][] qizi;

	private int width = 0;
	/**
	 * 成5, 1000分 20种棋形
	 * 活4，90分      16种棋形
	 * 死4，60分      
	 * 活3，50分      12种棋形
	 * 死3，30分      
	 * 活2，20分      4种棋形
	 * 死2，10分      
	 * 单子   0分        
	 * @return
	 */
	public static final int FIVE=100000;
	public static final int FOUR_LIVE=10000;
	public static final int FOUR_DEAD=1000;
	public static final int THREE_LIVE=1000;
	public static final int THREE_DEAD=100;
	public static final int TWO_LIVE=100;
	public static final int TWO_DEAD=10;
	
	
	public Ruler(int[][] qizi){
		this.qizi=qizi;
	}
	/**
	 * 游戏是否结束
	 * 0 未结束
	 * 1 黑胜
	 * 2 白胜
	 */
	public  int isOver(){
		/**
		 * 一共四种情况，
		 * 1、横排胜利
		 * 2、竖排胜利
		 * 3、左下斜排胜利
		 * 4、右下斜排胜利
		 */
		for(int i=0;i<= (width -5);i++){
			for(int j=0;j<= (width -5);j++){
				//1、横排
				if(qizi[i][j]==1&&qizi[i+1][j]==1&&qizi[i+2][j]==1&&qizi[i+3][j]==1&&qizi[i+4][j]==1){
					return 1;
				}
				if(qizi[i][j]==2&&qizi[i+1][j]==2&&qizi[i+2][j]==2&&qizi[i+3][j]==2&&qizi[i+4][j]==2){
					return 2;
				}
				
				//2、竖排
				if(qizi[i][j]==1&&qizi[i][j+1]==1&&qizi[i][j+2]==1&&qizi[i][j+3]==1&&qizi[i][j+4]==1){
					return 1;
				}
				if(qizi[i][j]==2&&qizi[i][j+1]==2&&qizi[i][j+2]==2&&qizi[i][j+3]==2&&qizi[i][j+4]==2){
					return 2;
				}
				//3、右斜排
				if(qizi[i][j]==1&&qizi[i+1][j+1]==1&&qizi[i+2][j+2]==1&&qizi[i+3][j+3]==1&&qizi[i+4][j+4]==1){
					return 1;
				}
				if(qizi[i][j]==2&&qizi[i+1][j+1]==2&&qizi[i+2][j+2]==2&&qizi[i+3][j+3]==2&&qizi[i+4][j+4]==2){
					return 2;
				}
				//4、左斜排
				if(i-4>=0){
					if(qizi[i][j]==1&&qizi[i-1][j+1]==1&&qizi[i-2][j+2]==1&&qizi[i-3][j+3]==1&&qizi[i-4][j+4]==1){
						return 1;
					}
					if(qizi[i][j]==2&&qizi[i-1][j+1]==2&&qizi[i-2][j+2]==2&&qizi[i-3][j+3]==2&&qizi[i-4][j+4]==2){
						return 2;
					}
				}
				
			}
		}
		return 0;
	}


	/**
	 * 返回赢得一方的棋子颜色
	 * @return
	 */
	public  int isWin(){
		/**
		 * 一共四种情况，
		 * 1、横排胜利
		 * 2、竖排胜利
		 * 3、左下斜排胜利
		 * 4、右下斜排胜利
		 */
		for(int i=0;i< (width -5);i++){
			for(int j=0;j< (width -5);j++){
				//1、横排
				if(qizi[i][j]==1&&qizi[i+1][j]==1&&qizi[i+2][j]==1&&qizi[i+3][j]==1&&qizi[i+4][j]==1){
					return 1;
				}
				if(qizi[i][j]==2&&qizi[i+1][j]==2&&qizi[i+2][j]==2&&qizi[i+3][j]==2&&qizi[i+4][j]==2){
					return 2;
				}

				//2、竖排
				if(qizi[i][j]==1&&qizi[i][j+1]==1&&qizi[i][j+2]==1&&qizi[i][j+3]==1&&qizi[i][j+4]==1){
					return 1;
				}
				if(qizi[i][j]==2&&qizi[i][j+1]==2&&qizi[i][j+2]==2&&qizi[i][j+3]==2&&qizi[i][j+4]==2){
					return qizi[i][j];
				}
				//3、右斜排
				if(qizi[i][j]==1&&qizi[i+1][j+1]==1&&qizi[i+2][j+2]==1&&qizi[i+3][j+3]==1&&qizi[i+4][j+4]==1){
					return qizi[i][j];
				}
				if(qizi[i][j]==2&&qizi[i+1][j+1]==2&&qizi[i+2][j+2]==2&&qizi[i+3][j+3]==2&&qizi[i+4][j+4]==2){
					return qizi[i][j];
				}
				//4、左斜排
				if(i-4>=0){
					if(qizi[i][j]==1&&qizi[i-1][j+1]==1&&qizi[i-2][j+2]==1&&qizi[i-3][j+3]==1&&qizi[i-4][j+4]==1){
						return qizi[i][j];
					}
					if(qizi[i][j]==2&&qizi[i-1][j+1]==2&&qizi[i-2][j+2]==2&&qizi[i-3][j+3]==2&&qizi[i-4][j+4]==2){
						return qizi[i][j];
					}
				}

			}
		}
		return 0;
	}

	/**
	 * 成5, 1000分 20种棋形
	 * 活4，90分      16种棋形
	 * 死4，60分      24种棋形
	 * 活3，50分      12种棋形
	 * 死3，30分      16种棋形
	 * 活2，20分      4种棋形
	 * 死2，10分      4种棋形
	 * 单子   0分        
	 * @return
	 * 这是一个模拟
	 */
	public int calScore(int x,int y,int color){
	 
		//成5, 1000分 20种棋形
		int score=0;
		//X0000
		if(x+4 < 15&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x+3][y]==color&&qizi[x+4][y]==color){
			score+=FIVE;
		}
		//0X000
		if(x-1>=0&&qizi[x-1][y]==color&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x+3][y]==color){
			score+=FIVE;
		}
		//00X00
		if(x-2>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x+1][y]==color&&qizi[x+2][y]==color){
			score+=FIVE;
		}
		//000X0
		if(x-3>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x-3][y]==color&&qizi[x+1][y]==color){
			score+= FIVE;
		}
		//0000X
		if(x-4>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x-3][y]==color&&qizi[x-4][y]==color){
			score+=FIVE;
		}

		/**
		 * x
		 * 0
		 * 0
		 * 0
		 * 0
		 */
		if( (y+4 < 15) &&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y+3]==color&&qizi[x][y+4]==color){
			score+=FIVE;
		}
		/**
		 * 0
		 * x
		 * 0
		 * 0
		 * 0
		 */
		if((y+3 < 15) &&y-1>=0&&qizi[x][y-1]==color&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y+3]==color){
			score+=FIVE;
		}
		/**
		 * 0
		 * 0
		 * x
		 * 0
		 * 0
		 */
		if( (y+2 < 15)&& y-2>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y+1]==color&&qizi[x][y+2]==color){
			score+=FIVE;
		}
		/**
		 * 0
		 * 0
		 * 0
		 * x
		 * 0
		 */
		if(y-3>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y-3]==color&&qizi[x][y+1]==color){
			score+=FIVE;
		}
		/**
		 * 0
		 * 0
		 * 0
		 * 0
		 * x
		 */
		if(y-4>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y-3]==color&&qizi[x][y-4]==color){
			score+=FIVE;
		}
		/**    x
		 *    0
		 *   0
		 *  0
		 * 0
		 */
		if(x-4>=0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x-3][y+3]==color&&qizi[x-4][y+4]==color){
			score+=FIVE;
		}
		/**    0
		 *    x
		 *   0
		 *  0
		 * 0
		 */
		if(x-3>=0&&x+1<15&&y+3<15&&y-1>=0&&qizi[x+1][y-1]==color&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x-3][y+3]==color){
			score+=FIVE;
		}
		/**    0
		 *    0
		 *   x
		 *  0
		 * 0
		 */
		if(x-2>=0&&y-2>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color){
			score+=FIVE;
		}
		/**    0
		 *    0
		 *   0
		 *  x
		 * 0
		 */
		if(x-1>=0&&y-3>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x+3][y-3]==color&&qizi[x-1][y+1]==color){
			score+=FIVE;
		}
		/**    0
		 *    0
		 *   0
		 *  0
		 * x
		 */
		if(y-4>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x+3][y-3]==color&&qizi[x+4][y-4]==color){
			score+=FIVE;
		}
		/**   
		 * x
		 *  0
		 *   0
		 *    0
		 *     0
		 */
		if(qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x+3][y+3]==color&&qizi[x+4][y+4]==color){
			score+=FIVE;
		}
		/**   
		 * 0
		 *  x
		 *   0
		 *    0
		 *     0
		 */
		if(x-1>=0&&y-1>=0&&qizi[x-1][y-1]==color&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x+3][y+3]==color){
			score+=FIVE;
		}
		/**   
		 * 0
		 *  0
		 *   x
		 *    0
		 *     0
		 */
		if(x-2>=0&&y-2>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color){
			score+=FIVE;
		}
		/**   
		 * 0
		 *  0
		 *   0
		 *    x
		 *     0
		 */
		if(x-3>0&&y-3>0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x-3][y-3]==color&&qizi[x-4][y-4]==color){
			score+=FIVE;
		}
		/**   
		 * 0
		 *  0
		 *   0
		 *    0
		 *     x
		 */
		if(x-4>=0&&y-4>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x-3][y-3]==color&&qizi[x-4][y-4]==color){
			score+=FIVE;
		}
		//活4
		//_x000_
		if(x-1>=0&&qizi[x-1][y]==0&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x+3][y]==color&&qizi[x+4][y]==0){
			score+=FOUR_LIVE;
		}
		//_0x00_
		if(x-2>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==0&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x+3][y]==0){
			score+=FOUR_LIVE;
		}
		//_00x0_
		if(x-3>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x-3][y]==0&&qizi[x+1][y]==color&&qizi[x+2][y]==0){
			score+=FOUR_LIVE;
		}
		//_000x_
		if(x-4>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x-3][y]==color&&qizi[x-4][y]==0&&qizi[x+1][y]==0){
			score+=FOUR_LIVE;
		}
		/**
		 * _
		 * x
		 * 0
		 * 0
		 * 0
		 * _
		 */
		if(y-1>=0&&qizi[x][y-1]==0&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y+3]==color&&qizi[x][y+4]==0){
			score+=FOUR_LIVE;
		}
		/**
		 * _
		 * 0
		 * x
		 * 0
		 * 0
		 * _
		 */
		if(y-2>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==0&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y+3]==0){
			score+=FOUR_LIVE;
		}
		/**
		 * _
		 * 0
		 * 0
		 * x
		 * 0
		 * _
		 */
		if(y-3>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y-3]==0&&qizi[x][y+1]==color&&qizi[x][y+2]==0){
			score+=FOUR_LIVE;
		}
		/**
		 * _
		 * 0
		 * 0
		 * 0
		 * x
		 * _
		 */
		if(y-4>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y-3]==color&&qizi[x][y-4]==0&&qizi[x][y+1]==0){
			score+=FOUR_LIVE;
		}
		/**
		 * _
		 *  x
		 *   0
		 *    0
		 *     0
		 *      _
		 */
		if(x-1>=0&&y-1>=0&&qizi[x-1][y-1]==0&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x+3][y+3]==color&&qizi[x+4][y+4]==0){
			score+=FOUR_LIVE;
		}
		/**
		 * _
		 *  0
		 *   x
		 *    0
		 *     0
		 *      _
		 */
		if(x-2>=0&&y-2>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==0&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x+3][y+3]==0){
			score+=FOUR_LIVE;
		}
		/**
		 * _
		 *  0
		 *   0
		 *    x
		 *     0
		 *      _
		 */
		if(x-3>=0&&y-3>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x-3][y-3]==0&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==0){
			score+=FOUR_LIVE;
		}
		/**
		 * _
		 *  0
		 *   0
		 *    0
		 *     X
		 *      _
		 */
		if(x-4>=0&&y-4>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x-3][y-3]==color&&qizi[x-4][y-4]==0&&qizi[x+1][y+1]==0){
			score+=FOUR_LIVE;
		}
		/**
		 *      _
		 *     x
		 *    0
		 *   0
		 *  0
		 * _
		 */
		if(x-4>=0&&y-1>=0&&qizi[x+1][y-1]==0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x-3][y+3]==color&&qizi[x-4][y+4]==0){
			score+=FOUR_LIVE;
		}
		/**
		 *      _
		 *     0
		 *    x
		 *   0
		 *  0
		 * _
		 */
		if(x-3>=0&&y-2>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x-3][y+3]==0){
			score+=FOUR_LIVE;
		}
		/**
		 *      _
		 *     0
		 *    0
		 *   x
		 *  0
		 * _
		 */
		if(x-2>=0&&y-3>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x+3][y-3]==0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==0){
			score+=FOUR_LIVE;
		}
		/**
		 *      _
		 *     0
		 *    0
		 *   0
		 *  x
		 * _
		 */
		if(x-1>=0&&y-4>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x+3][y-3]==color&&qizi[x+4][y-4]==0&&qizi[x-1][y+1]==0){
			score+=FOUR_LIVE;
		}
		
		//死4
		//●x○○○_
		if((x==0||(x-1>=0&&qizi[x-1][y]!=color))&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x+3][y]==color&&qizi[x+4][y]==0){
			score+=FOUR_DEAD;
		}
		//●○x○○_
		if((x-1==0||(x-2>=0&&qizi[x-2][y]!=color))&&x-1>=0&&qizi[x-1][y]==color&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x+3][y]==0){
			score+=FOUR_DEAD;
		}
		//●○○x○_
		if((x-2==0||(x-3>=0&&qizi[x-3][y]!=color))&&x-2>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x+1][y]==color&&qizi[x+2][y]==0){
			score+=FOUR_DEAD;
		}
		//●○○○x_
		if((x-3==0||(x-4>=0&&qizi[x-4][y]!=color))&&x-3>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x-3][y]==color&&qizi[x+1][y]==0){
			score+=FOUR_DEAD;
		}
		//_○○○x●
		if((x==14||(x+1<=14&&qizi[x+1][y]!=color))&&x-4>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x-3][y]==color&&qizi[x-4][y]==0){
			score+=FOUR_DEAD;
		}
		//_○○x○●
		if((x+1==14||(x+2<=14&&qizi[x+2][y]!=color))&&x-3>=0&&qizi[x+1][y]==color&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x-3][y]==0){
			score+=FOUR_DEAD;
		}
		//_○x○○●
		if((x+2==14||(x+3<=14&&qizi[x+3][y]!=color))&&x-2>=0&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x-1][y]==color&&qizi[x-2][y]==0){
			score+=FOUR_DEAD;
		}
		//_x○○○●
		if((x==14||(x+4<=14&&qizi[x+4][y]!=color))&&x-1>=0&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x+3][y]==color&&qizi[x-1][y]==0){
			score+=FOUR_DEAD;
		}
		 
		/**
		 * ●
		 * x
		 * ○
		 * ○
		 * ○
		 * _
		 */
		if((y==0||(y-1>=0&&qizi[x][y-1]!=color))&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y+3]==color&&qizi[x][y+4]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * ●
		 * o
		 * x
		 * ○
		 * ○
		 * _
		 */
		if((y-1==0||(y-2>=0&&qizi[x][y-2]!=color))&&y-1>=0&&qizi[x][y-1]==color&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y+3]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * ●
		 * o
		 * o
		 * x
		 * ○
		 * _
		 */
		if((y-2==0||(y-3>=0&&qizi[x][y-3]!=color))&&y-2>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y+1]==color&&qizi[x][y+2]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * ●
		 * o
		 * o
		 * o
		 * x
		 * _
		 */
		if((y-3==0||(y-4>=0&&qizi[x][y-4]!=color))&&y-3>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y-3]==color&&qizi[x][y+1]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * _
		 * o
		 * o
		 * o
		 * x
		 * ●
		 */
		if((y==14||(y+1<=14&&qizi[x][y+1]!=color))&&y-4>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y-3]==color&&qizi[x][y-4]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * _
		 * o
		 * o
		 * x
		 * o
		 * ●
		 */
		if((y+1==14||(y+2<=14&&qizi[x][y+2]!=color))&&y-3>=0&&qizi[x][y+1]==color&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y-3]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * _
		 * o
		 * x
		 * o
		 * o
		 * ●
		 */
		if((y+2==14||(y+3<=14&&qizi[x][y+3]!=color))&&y-2>=0&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y-1]==color&&qizi[x][y-2]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * _
		 * x
		 * o
		 * o
		 * o
		 * ●
		 */
		if((y+3==14||(y+4<=14&&qizi[x][y+4]!=color))&&y-1>=0&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y+3]==color&&qizi[x][y-1]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * ●
		 *  x
		 *   o
		 *    o
		 *     o
		 *      _
		 */
		if((x==0||y==0||(x-1>=0&&y-1>=0&&qizi[x-1][y-1]!=color))&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x+3][y+3]==color&&qizi[x+4][y+4]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * ●
		 *  o
		 *   x
		 *    o
		 *     o
		 *      _
		 */
		if((x-1==0||y-1==0||(x-2>=0&&y-2>=0&&qizi[x-2][y-2]!=color))&&x-1>=0&&y-1>=0&&qizi[x-1][y-1]==color&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x+3][y+3]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * ●
		 *  o
		 *   o
		 *    x
		 *     o
		 *      _
		 */
		if((x-2==0||y-2==0||(x-3>=0&&y-3>=0&&qizi[x-3][y-3]!=color))&&x-2>=0&&y-2>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * ●
		 *  o
		 *   o
		 *    o
		 *     x
		 *      _
		 */
		if((x-3==0||y-3==0||(x-4>=0&&y-4>=0&&qizi[x-4][y-4]!=color))&&x-3>=0&&y-3>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x-3][y-3]==color&&qizi[x+1][y+1]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * _
		 *  o
		 *   o
		 *    o
		 *     x
		 *      ●
		 */
		if((x==14||y==14||(x+1<=14&&y+1<=14&&qizi[x+1][y+1]!=color))&&x-4>=0&&y-4>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x-3][y-3]==color&&qizi[x-4][y-4]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * _
		 *  o
		 *   o
		 *    x
		 *     o
		 *      ●
		 */
		if((x+1==14||y+1==14||(x+2<=14&&y+2<=14&&qizi[x+2][y+2]!=color))&&x-3>=0&&y-3>=0&&qizi[x+1][y+1]==color&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x-3][y-3]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * _
		 *  o
		 *   x
		 *    o
		 *     o
		 *      ●
		 */
		if((x+2==14||y+2==14||(x+3<=14&&y+3<=14&&qizi[x+3][y+3]!=color))&&x-2>=0&&y-2>=0&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==0){
			score+=FOUR_DEAD;
		}
		/**
		 * _
		 *  x
		 *   o
		 *    o
		 *     o
		 *      ●
		 */
		if((x+3==14||y+3==14||(x+4<=14&&y+4<=14&&qizi[x+4][y+4]!=color))&&x-1>=0&&y-1>=0&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x+3][y+3]==color&&qizi[x-1][y-1]==0){
			score+=FOUR_DEAD;
		}

		/**
		 *      ●
		 *     x
		 *    o
		 *   o
		 *  o
		 * _
		 */
		if((x==14||y==0||(x+1<=14&&y-1>=0&&qizi[x+1][y-1]!=color))&&x-4>=0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x-3][y+3]==color&&qizi[x-4][y+4]==0){
			score+=FOUR_DEAD;
		}
		
		/**
		 *      ●
		 *     o
		 *    x
		 *   o
		 *  o
		 * _
		 */
		if((x+1==14||y-1==0||(x+2<=14&&y-2>=0&&qizi[x+2][y-2]!=color))&&x-3>=0&&y-1>=0&&qizi[x+1][y-1]==color&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x-3][y+3]==0){
			score+=FOUR_DEAD;
		}
		
		/**
		 *      ●
		 *     o
		 *    o
		 *   x
		 *  o
		 * _
		 */
		if((x+2==14||y-2==0||(x+3<=14&&y-3>=0&&qizi[x+3][y-3]!=color))&&x-2>=0&&y-2>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==0){
			score+=FOUR_DEAD;
		}
		/**
		 *      ●
		 *     o
		 *    o
		 *   o
		 *  x
		 * _
		 */
		if((x+3==14||y-3==0||(x+4<=14&&y-4>=0&&qizi[x+4][y-4]!=color))&&x-1>=0&&y-3>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x+3][y-3]==color&&qizi[x-1][y+1]==0){
			score+=FOUR_DEAD;
		}
		
		/**
		 *      _
		 *     o
		 *    o
		 *   o
		 *  x
		 * ●
		 */
		if((x==0||y==14||(x-1>=0&&y+1<=14&&qizi[x-1][y+1]!=color))&&y-4>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x+3][y-3]==color&&qizi[x+4][y-4]==0){
			score+=FOUR_DEAD;
		}
		/**
		 *      _
		 *     o
		 *    o
		 *   x
		 *  o
		 * ●
		 */
		if((x-1==0||y+1==14||(x-2>=0&&y+2<=14&&qizi[x-2][y+2]!=color))&&x-1>=0&&y-3>=0&&qizi[x-1][y+1]==color&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x+3][y-3]==0){
			score+=FOUR_DEAD;
		}
		/**
		 *      _
		 *     o
		 *    x
		 *   o
		 *  o
		 * ●
		 */
		if((x-2==0||y+2==14||(x-3>=0&&y+3<=14&&qizi[x-3][y+3]!=color))&&x-2>=0&&y-2>=0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==0){
			score+=FOUR_DEAD;
		}
		/**
		 *      _
		 *     x
		 *    o
		 *   o
		 *  o
		 * ●
		 */
		if((x-3==0||y+3==14||(x-4>=0&&y+4<=14&&qizi[x-4][y+4]!=color))&&x-3>=0&&y-1>=0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x-3][y+3]==color&&qizi[x+1][y-1]==0){
			score+=FOUR_DEAD;
		}
		
		//活3
		//_xoo_
		if(x-1>=0&&qizi[x-1][y]==0&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x+3][y]==0){
			score+=THREE_LIVE;
		}
		//_oxo_
		if(x-2>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==0&&qizi[x+1][y]==color&&qizi[x+2][y]==0){
			score+=THREE_LIVE;
		}
		//_oox_
		if(x-3>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x-3][y]==0&&qizi[x+1][y]==0){
			score+=THREE_LIVE;
		}
		/**
		 * _
		 * x
		 * o
		 * o
		 * _
		 */
		if(y-1>=0&&qizi[x][y-1]==0&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y+3]==0){
			score+=THREE_LIVE;
		}
		/**
		 * _
		 * o
		 * x
		 * o
		 * _
		 */
		if(y-2>=0&&qizi[x][y-2]==0&&qizi[x][y-1]==color&&qizi[x][y+1]==color&&qizi[x][y+2]==0){
			score+=THREE_LIVE;
		}
		/**
		 * _
		 * o
		 * o
		 * x
		 * _
		 */
		if(y-3>=0&&qizi[x][y-3]==0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y+1]==0){
			score+=THREE_LIVE;
		}
		/**
		 * _
		 *  x
		 *   o
		 *    o
		 *     _
		 */
		if(x-1>=0&&y-1>=0&&qizi[x-1][y-1]==0&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x+3][y+3]==0){
			score+=THREE_LIVE;
		}
		/**
		 * _
		 *  o
		 *   x
		 *    o
		 *     _
		 */
		if(x-2>=0&&y-2>=0&&qizi[x-2][y-2]==0&&qizi[x-1][y-1]==color&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==0){
			score+=THREE_LIVE;
		}
		
		/**
		 * _
		 *  o
		 *   o
		 *    x
		 *     _
		 */
		if(x-3>=0&&y-3>=0&&qizi[x-3][y-3]==0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x+1][y+1]==0){
			score+=THREE_LIVE;
		}
		/**  
		 *     _
		 *    o
		 *   o
		 *  x
		 * _
		 */
		if(x-1>=0&&y-3>=0&&qizi[x-1][y+1]==0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x+3][y-3]==0){
			score+=THREE_LIVE;
		}
		/**  
		 *     _
		 *    o
		 *   x
		 *  o
		 * _
		 */
		if(y+2 < 15 && x-2>=0&&y-2>=0&&qizi[x-2][y+2]==0&&qizi[x-1][y+1]==color&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==0){
			score+=THREE_LIVE;
		}
		/**  
		 *     _
		 *    x
		 *   o
		 *  o
		 * _
		 */
		if(y+3 <15 && x-3>=0&&y-1>=0&&qizi[x-3][y+3]==0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x+1][y-1]==0){
			score+=THREE_LIVE;
		}
		//死三
		//●x○○_
		if((x==0||(x-1>=0&&qizi[x-1][y]!=color))&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x+3][y]==0){
			score+=THREE_DEAD;
		}
		//●○x○_
		if((x-1==0||(x-2>=0&&qizi[x-2][y]!=color))&&qizi[x-1][y]==color&&qizi[x+1][y]==color&&qizi[x+2][y]==0){
			score+=THREE_DEAD;
		}
		//●○○x_
		if((x-2==0||(x-3>=0&&qizi[x-3][y]!=color))&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x+1][y]==0){
			score+=THREE_DEAD;
		}
		//_○○x●
		if((x==14||(x+1<=14&&qizi[x+1][y]!=color))&&x-3>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==color&&qizi[x-3][y]==0){
			score+=THREE_DEAD;
		}
		//_○x○●
		if((x+1==14||(x+2<=14&&qizi[x+2][y]!=color))&&x-2>=0&&qizi[x+1][y]==color&&qizi[x-1][y]==color&&qizi[x-2][y]==0){
			score+=THREE_DEAD;
		}
		//_x○○●
		if((x+2==14||(x+3<=14&&qizi[x+3][y]!=color))&&x-1>=0&&qizi[x+1][y]==color&&qizi[x+2][y]==color&&qizi[x-1][y]==0){
			score+=THREE_DEAD;
		}
		 
		/**
		 * ●
		 * x
		 * ○
		 * ○
		 * _
		 */
		if((y==0||(y-1>=0&&qizi[x][y-1]!=color))&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y+3]==0){
			score+=THREE_DEAD;
		}
		/**
		 * ●
		 * o
		 * x
		 * ○
		 * _
		 */
		if((y-1==0||(y-2>=0&&qizi[x][y-2]!=color))&&qizi[x][y-1]==color&&qizi[x][y+1]==color&&qizi[x][y+2]==0){
			score+=THREE_DEAD;
		}
		/**
		 * ●
		 * o
		 * o
		 * x
		 * _
		 */
		if((y-2==0||(y-3>=0&&qizi[x][y-3]!=color))&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y+1]==0){
			score+=THREE_DEAD;
		}
		
		/**
		 * _
		 * o
		 * o
		 * x
		 * ●
		 */
		if((y==14||(y+1<=14&&qizi[x][y+1]!=color))&&y-3>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==color&&qizi[x][y-3]==0){
			score+=THREE_DEAD;
		}
		/**
		 * _
		 * o
		 * x
		 * o
		 * ●
		 */
		if((y+1==14||(y+2<=14&&qizi[x][y+2]!=color))&&y-2>=0&&qizi[x][y+1]==color&&qizi[x][y-1]==color&&qizi[x][y-2]==0){
			score+=THREE_DEAD;
		}
		/**
		 * _
		 * x
		 * o
		 * o
		 * ●
		 */
		if((y+2==14||(y+3<=14&&qizi[x][y+3]!=color))&&y-1>=0&&qizi[x][y+1]==color&&qizi[x][y+2]==color&&qizi[x][y-1]==0){
			score+=THREE_DEAD;
		}
		 
		/**
		 * ●
		 *  x
		 *   o
		 *    o
		 *      _
		 */
		if((x==0||y==0||(x-1>=0&&y-1>=0&&qizi[x-1][y-1]!=color))&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x+3][y+3]==0){
			score+=THREE_DEAD;
		}
		/**
		 * ●
		 *  o
		 *   x
		 *    o
		 *      _
		 */
		if((x-1==0||y-1==0||(x-2>=0&&y-2>=0&&qizi[x-2][y-2]!=color))&&x-1>=0&&y-1>=0&&qizi[x-1][y-1]==color&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==0){
			score+=THREE_DEAD;
		}
		/**
		 * ●
		 *  o
		 *   o
		 *    x
		 *      _
		 */
		if((x-2==0||y-2==0||(x-3>=0&&y-3>=0&&qizi[x-3][y-3]!=color))&&x-2>=0&&y-2>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x+1][y+1]==0){
			score+=THREE_DEAD;
		}
		
		/**
		 *  _
		 *   o
		 *    o
		 *     x
		 *      ●
		 */
		if((x==14||y==14||(x+1<=14&&y+1<=14&&qizi[x+1][y+1]!=color))&&x-3>=0&&y-3>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==color&&qizi[x-3][y-3]==0){
			score+=THREE_DEAD;
		}
		/**
		 *  _
		 *   o
		 *    x
		 *     o
		 *      ●
		 */
		if((x+1==14||y+1==14||(x+2<=14&&y+2<=14&&qizi[x+2][y+2]!=color))&&x-2>=0&&y-2>=0&&qizi[x+1][y+1]==color&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==0){
			score+=THREE_DEAD;
		}
		/**
		 * _
		 *   x
		 *    o
		 *     o
		 *      ●
		 */
		if((x+2==14||y+2==14||(x+3<=14&&y+3<=14&&qizi[x+3][y+3]!=color))&&x-1>=0&&y-1>=0&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==color&&qizi[x-1][y-1]==0){
			score+=THREE_DEAD;
		}
		 
		/**
		 *      ●
		 *     x
		 *    o
		 *   o
		 * _
		 */
		if((x==14||y==0||(x+1<=14&&y-1>=0&&qizi[x+1][y-1]!=color))&&x-3>=0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x-3][y+3]==0){
			score+=THREE_DEAD;
		}
		
		/**
		 *      ●
		 *     o
		 *    x
		 *   o
		 * _
		 */
		if((x+1==14||y-1==0||(x+2<=14&&y-2>=0&&qizi[x+2][y-2]!=color))&&x-2>=0&&y-1>=0&&qizi[x+1][y-1]==color&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==0){
			score+=THREE_DEAD;
		}
		
		/**
		 *      ●
		 *     o
		 *    o
		 *   x
		 * _
		 */
		if((x+2==14||y-2==0||(x+3<=14&&y-3>=0&&qizi[x+3][y-3]!=color))&&x-1>=0&&y-2>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x-1][y+1]==0){
			score+=THREE_DEAD;
		}
		
		/**
		 *     _
		 *    o
		 *   o
		 *  x
		 * ●
		 */
		if((x==0||y==14||(x-1>=0&&y+1<=14&&qizi[x-1][y+1]!=color))&&y-3>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==color&&qizi[x+3][y-3]==0){
			score+=THREE_DEAD;
		}
		/**
		 *     _
		 *    o
		 *   x
		 *  o
		 * ●
		 */
		if((x-1==0||y+1==14||(x-2>=0&&y+2<=14&&qizi[x-2][y+2]!=color))&&x-1>=0&&y-2>=0&&qizi[x-1][y+1]==color&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==0){
			score+=THREE_DEAD;
		}
		/**
		 *     _
		 *    x
		 *   o
		 *  o
		 * ●
		 */
		if((x-2==0||y+2==14||(x-3>=0&&y+3<=14&&qizi[x-3][y+3]!=color))&&x-2>=0&&y-1>=0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==color&&qizi[x+1][y-1]==0){
			score+=THREE_DEAD;
		}
		 
		//活2
		//_xo_
		if(x-1>=0&&qizi[x-1][y]==0&&qizi[x+1][y]==color&&qizi[x+2][y]==0){
			score+=TWO_LIVE;
		}
		//_ox_
		if(x-2>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==0&&qizi[x+1][y]==0){
			score+=TWO_LIVE;
		}
		 
		/**
		 * _
		 * x
		 * o
		 * _
		 */
		if(y-1>=0&&qizi[x][y-1]==0&&qizi[x][y+1]==color&&qizi[x][y+2]==0){
			score+=TWO_LIVE;
		}
		/**
		 * _
		 * o
		 * x
		 * _
		 */
		if(y-2>=0&&qizi[x][y-2]==0&&qizi[x][y-1]==color&&qizi[x][y+1]==0){
			score+=TWO_LIVE;
		}
		 
		/**
		 * _
		 *  x
		 *   o
		 *    _
		 */
		if(x-1>=0&&y-1>=0&&qizi[x-1][y-1]==0&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==0){
			score+=TWO_LIVE;
		}
		/**
		 * _
		 *  o
		 *   x
		 *     _
		 */
		if(x-2>=0&&y-2>=0&&qizi[x-2][y-2]==0&&qizi[x-1][y-1]==color&&qizi[x+1][y+1]==0){
			score+=TWO_LIVE;
		}
		
		 
		/**  
		 *    _
		 *   o
		 *  x
		 * _
		 */
		if(x-1>=0&&y-2>=0&&qizi[x-1][y+1]==0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==0){
			score+=TWO_LIVE;
		}
		/**  
		 *    _
		 *   x
		 *  o
		 * _
		 */
		if( y+2 <15 && x-2>=0&&y-1>=0&&qizi[x-2][y+2]==0&&qizi[x-1][y+1]==color&&qizi[x+1][y-1]==0){
			score+=TWO_LIVE;
		}
		
		//死二
		//●x○_
		if((x==0||(x-1>=0&&qizi[x-1][y]!=color))&&qizi[x+1][y]==color&&qizi[x+2][y]==0){
			score+=TWO_DEAD;
		}
		//●○x_
		if((x-1==0||(x-2>=0&&qizi[x-2][y]!=color))&&qizi[x-1][y]==color&&qizi[x+1][y]==0){
			score+=TWO_DEAD;
		}
		 
		//_○x●
		if((x==14||(x+1<=14&&qizi[x+1][y]!=color))&&x-2>=0&&qizi[x-1][y]==color&&qizi[x-2][y]==0){
			score+=TWO_DEAD;
		}
		//_x○●
		if((x+1==14||(x+2<=14&&qizi[x+2][y]!=color))&&x-1>=0&&qizi[x+1][y]==color&&qizi[x-1][y]==0){
			score+=TWO_DEAD;
		}
		 
		/**
		 * ●
		 * x
		 * ○
		 * _
		 */
		if((y==0||(y-1>=0&&qizi[x][y-1]!=color))&&qizi[x][y+1]==color&&qizi[x][y+2]==0){
			score+=TWO_DEAD;
		}
		/**
		 * ●
		 * o
		 * x
		 * _
		 */
		if((y-1==0||(y-2>=0&&qizi[x][y-2]!=color))&&qizi[x][y-1]==color&&qizi[x][y+1]==0){
			score+=TWO_DEAD;
		}
		
		/**
		 * _
		 * o
		 * x
		 * ●
		 */
		if((y==14||(y+1<=14&&qizi[x][y+1]!=color))&&y-2>=0&&qizi[x][y-1]==color&&qizi[x][y-2]==0){
			score+=TWO_DEAD;
		}
		/**
		 * _
		 * x
		 * o
		 * ●
		 */
		if((y+1==14||(y+2<=14&&qizi[x][y+2]!=color))&&y-1>=0&&qizi[x][y+1]==color&&qizi[x][y-1]==0){
			score+=TWO_DEAD;
		}
		 
		/**
		 * ●
		 *  x
		 *   o
		 *     _
		 */
		if((x==0||y==0||(x-1>=0&&y-1>=0&&qizi[x-1][y-1]!=color))&&qizi[x+1][y+1]==color&&qizi[x+2][y+2]==0){
			score+=TWO_DEAD;
		}
		/**
		 * ●
		 *  o
		 *   x
		 *     _
		 */
		if((x-1==0||y-1==0||(x-2>=0&&y-2>=0&&qizi[x-2][y-2]!=color))&&x-1>=0&&y-1>=0&&qizi[x-1][y-1]==color&&qizi[x+1][y+1]==0){
			score+=TWO_DEAD;
		}
		 
		/**
		 *   _
		 *    o
		 *     x
		 *      ●
		 */
		if((x==14||y==14||(x+1<=14&&y+1<=14&&qizi[x+1][y+1]!=color))&&x-2>=0&&y-2>=0&&qizi[x-1][y-1]==color&&qizi[x-2][y-2]==0){
			score+=TWO_DEAD;
		}
		/**
		 *   _
		 *    x
		 *     o
		 *      ●
		 */
		if((x+1==14||y+1==14||(x+2<=14&&y+2<=14&&qizi[x+2][y+2]!=color))&&x-1>=0&&y-1>=0&&qizi[x+1][y+1]==color&&qizi[x-1][y-1]==0){
			score+=TWO_DEAD;
		}
		 
		/**
		 *      ●
		 *     x
		 *    o
		 *  _
		 */
		if((x==14||y==0||(x+1<=14&&y-1>=0&&qizi[x+1][y-1]!=color))&&x-2>=0&&qizi[x-1][y+1]==color&&qizi[x-2][y+2]==0){
			score+=TWO_DEAD;
		}
		
		/**
		 *      ●
		 *     o
		 *    x
		 *  _
		 */
		if((x+1==14||y-1==0||(x+2<=14&&y-2>=0&&qizi[x+2][y-2]!=color))&&x-1>=0&&y-1>=0&&qizi[x+1][y-1]==color&&qizi[x-1][y+1]==0){
			score+=TWO_DEAD;
		}
		
		 
		/**
		 *    _
		 *   o
		 *  x
		 * ●
		 */
		if((x==0||y==14||(x-1>=0&&y+1<=14&&qizi[x-1][y+1]!=color))&&y-2>=0&&qizi[x+1][y-1]==color&&qizi[x+2][y-2]==0){
			score+=TWO_DEAD;
		}
		/**
		 *    _
		 *   x
		 *  o
		 * ●
		 */
		if((x-1==0||y+1==14||(x-2>=0&&y+2<=14&&qizi[x-2][y+2]!=color))&&x-1>=0&&y-1>=0&&qizi[x-1][y+1]==color&&qizi[x+1][y-1]==0){
			score+=TWO_DEAD;
		}
		 
				////////////////////////////
		return score;
	}
	
	public int[][] getQizi() {
		return qizi;
	}
	public void setQizi(int[][] qizi) {
		this.qizi = qizi;
		width = qizi[0].length;
	}
}
