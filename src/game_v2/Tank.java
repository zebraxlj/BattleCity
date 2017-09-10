package game_v2;

import java.awt.Color;
import java.util.Vector;

public abstract class Tank implements Const {
	protected int x, y;
	protected int direct, speed;
	protected Color color;
	int w, h; // width, height of the tank
	private long lastFire = 0;
	
	public Tank(int x, int y, int direct, int speed) {
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.speed = speed;
		this.speed = 2;
		this.w = 30;
		this.h = 30;
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
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}
	public long getLastFire() {
		return lastFire;
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
	public void setLastFire(long lastFire) {
		this.lastFire = lastFire;
	}
	
	public void moveUp() {
		if (y - speed >= 0) {
			y -= speed;
		}
	}
	public void moveDown() {
		System.out.println(this.x + " " + this.y + " " + this.speed + " " + WINDOW_H);
		if (y + speed + 2*h <= WINDOW_H) {
			y += speed;
		}
	}
	public void moveLeft() {
		if (x - speed >= 0) {
			x -= speed;
		}
	}
	public void moveRight() {
		if (x + speed + w <= WINDOW_W) {
			x += speed;
		}
	}
		
	public abstract Color getColor();
	public abstract void setColor(Color color);

	public boolean isOverlappingWith(Tank t) {
		int t_x1, t_x2, t_y1, t_y2;
		int x1, x2, y1, y2;
		x1 = this.x;
		y1 = this.y;
		x2 = x1 + this.w;
		y2 = y1 + this.h;
		t_x1 = t.getX();
		t_y1 = t.getY();
		t_x2 = t_x1 + t.getW();
		t_y2 = t_y1 + t.getH();
		return true;
	}
}

class PlayerTank extends Tank {
	public Color color = Color.RED;
	public static final int speed = 5;
	public static final int W = 30, H = 30;
	public PlayerTank(int x, int y, int direct, int speed) {
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
}

class ComTank extends Tank implements Runnable{
	private boolean alive;
	public Color color = Color.BLUE;
	
	public ComTank(int x, int y, int direct, int speed) {
		super(x, y, direct, speed);
		alive = true;
		Thread t = new Thread(this);
		t.start();
	}
	@Override
	public Color getColor() {
		return color;
	}
	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setDead() {
		alive = false;
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch (direct) {
			case Tank.UP:
				moveUp();
				break;
			case Tank.DOWN:
				moveDown();
				break;
			case Tank.RIGHT:
				moveRight();
				break;
			case Tank.LEFT:
				moveLeft();
				break;
			}
			if (!this.alive) {
				break;
			}
		}
	}
}

class Shell implements Runnable, Const{
	private int x, y, speed;
	private int direct;
	private boolean alive;
	private Color color = null;
	
	public Shell(int x, int y, int direct, int speed, Color color) {
		this.x = x;
		this.y = y;
		this.direct = direct;
		this.color = color;
		this.speed = speed;
		this.alive = true;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void setDead() {
		this.alive = false;
	}
	public boolean isAlive() {
		return this.alive;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getSpeed() {
		return speed;
	}
	public Color getColor() {
		return color;
	}
	public int getDirect() {
		return direct;
	}
	@Override
	public void run() {
		while (true) {
			switch (direct) {
			case UP:
				y -= speed;
				break;
			case DOWN:
				y += speed;
				break;
			case LEFT:
				x -= speed;
				break;
			case RIGHT:
				x += speed;
				break;
			}
			if (x < 0 || y < 0 || x > Const.WINDOW_W || y > Const.WINDOW_H) {
				this.alive = false;
			}
			// shell will be dead if 1. flying out of board || 2. hit a tank
			if (!this.alive) {
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}