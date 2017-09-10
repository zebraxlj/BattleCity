package game_v1;

import java.awt.Color;
/**
 * 
 * @author lxia
 *  Tank position
 *  Tank color
 */
interface Const{
	final int UP = 0;
	final int DOWN = 1;
	final int LEFT = 2;
	final int RIGHT = 3;
	
	final int SPEED_PLAYER = 1;
	
	final Color COLOR_PLAYER = Color.RED;
}
public abstract class Tank implements Const{
	protected int x, y;
	protected int direct, speed;
	protected Color color;
	int w, h; // width, height of the tank
	public Tank(int x, int y, int direct, int speed) {
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.speed = speed;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getDirect() {
		return direct;
	}
	public int getSpeed() {
		return speed;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void moveUp() {
		y -= speed;
	}
	public void moveDown() {
		y += speed;
	}
	public void moveLeft() {
		x -= speed;
	}
	public void moveRight() {
		x += speed;
	}
	
	public abstract Color getColor();
	public abstract void setColor(Color color);
}

class Player extends Tank {
	public Color color = Color.RED;
	public static final int speed = 5;
	public static final int W = 30, H = 30;
	public Player(int x, int y, int direct, int speed) {
		super(x, y, direct, speed);
	}
	@Override
	public Color getColor() {
		return color;
	}
	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	@Override
	public void moveUp() {
		super.moveUp();
	}
}
