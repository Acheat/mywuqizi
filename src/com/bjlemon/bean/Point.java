package com.bjlemon.bean;

import java.io.Serializable;

/**
 * 棋盘落子的信息
 * @author 柠檬学院李伟
 *
 */
public class Point implements Serializable{

	private int x;
	private int y;
	private int score;

	private Integer qiScore;
	
	public Point() {
		super();
	}

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y, int score) {
		super();
		this.x = x;
		this.y = y;
		this.score = score;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Integer getQiScore() {
		return qiScore;
	}

	public void setQiScore(Integer qiScore) {
		this.qiScore = qiScore;
	}

	@Override
	public String toString() {
		return "Point{" +
				"x=" + x +
				", y=" + y +
				", score=" + score +
				", qiScore=" + qiScore +
				'}';
	}
}
