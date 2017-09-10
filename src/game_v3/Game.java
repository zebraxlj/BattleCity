package game_v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
	JFrame jf = null;
	GamePanel gp = null;
	
	public Game() {
		jf = new JFrame("Battle City");
		gp = new GamePanel();
//		System.out.println(gp.getWidth() + " " + gp.getHeight());
		
		jf.add(gp, BorderLayout.CENTER);
		
		jf.addKeyListener(gp);
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocation(0, 0);
		jf.setResizable(false);
		jf.setSize(Const.WINDOW_W, Const.WINDOW_H);
		jf.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Game();
	}

}

class GamePanel extends JPanel implements KeyListener, Runnable{
	PlayerTank p1 = null;
	Vector<ComTank> comp_tanks = new Vector<ComTank>();
	Vector<Shell> comp_shells = new Vector<Shell>();
	Vector<Shell> p1_shells = new Vector<Shell>();
	int enSize = 3;
	
	public GamePanel() {
		this.setBackground(Color.BLACK);
		p1 = new PlayerTank(10, 10, Tank.UP, Tank.SPEED_TANK1);
		for (int i = 0; i < enSize; ++i) {
			comp_tanks.addElement(new ComTank((i+1)*50, 50, Tank.DOWN, 4));
		}
		comp_tanks.addElement(new ComTank(200, 50, Tank.UP, 4));
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawTank(g, p1);
		for (int i = 0; i < comp_tanks.size(); ++i) {
			drawTank(g, comp_tanks.get(i));
		}
		for (int i = 0; i < p1_shells.size(); ++i) {
			drawShell(g, p1_shells.get(i));				
		}
	}
	
	void drawShell(Graphics g, Shell s) {
		g.setColor(s.getColor());
		int w = 10, h = 10;
		switch (s.getDirect()) {
		case Shell.UP:
		case Shell.DOWN:
			w = 2;
			h = 4;
			break;
		case Shell.LEFT:
		case Shell.RIGHT:
			w = 4;
			h = 2;
			break;
		}
		g.fillOval(s.getX(), s.getY(), w, h);
	}
	
	void drawTank(Graphics g, Tank t) {
		int x = t.getX();
		int y = t.getY();
		int[] xPoints = null;
		int[] yPoints = null;
		int nPoints = 3;
		g.setColor(t.getColor());
		switch (t.getDirect()) {
		case Tank.UP:
			xPoints = new int[]{x, x+30, x+15};
			yPoints = new int[]{y+30, y+30, y};
			g.fillPolygon(xPoints, yPoints, nPoints);
			break;
		case Tank.DOWN:
			xPoints = new int[]{x, x+30, x+15};
			yPoints = new int[]{y, y, y+30};
			g.fillPolygon(xPoints, yPoints, nPoints);
			break;
		case Tank.LEFT:
			xPoints = new int[]{x+30, x+30, x};
			yPoints = new int[]{y, y+30, y+15};
			g.fillPolygon(xPoints, yPoints, nPoints);
			break;
		case Tank.RIGHT:
			xPoints = new int[]{x, x, x+30};
			yPoints = new int[]{y, y+30, y+15};
			g.fillPolygon(xPoints, yPoints, nPoints);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
//		try {
//			Thread.sleep(30); // this will stack in the scheduler
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		switch (e.getKeyCode()){
		case KeyEvent.VK_UP:
			p1.setDirect(Tank.UP);
			p1.moveUp();
			break;
		case KeyEvent.VK_DOWN:
			p1.setDirect(Tank.DOWN);
			p1.moveDown();
			break;
		case KeyEvent.VK_LEFT:
			p1.setDirect(Tank.LEFT);
			p1.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			p1.setDirect(Tank.RIGHT);
			p1.moveRight();
			break;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Z && 
				System.currentTimeMillis() - p1.getLastFire() >= 1000) {
			int x, y, w, h;
			x = p1.getX()-1;
			y = p1.getY()-1;
			w = PlayerTank.W;
			h = PlayerTank.H;
			switch (p1.getDirect()) {
			case Tank.UP:
				x += w / 2;
				break;
			case Tank.DOWN:
				x += w / 2;
				y += h;
				break;
			case Tank.LEFT:
				y += h / 2;
				break;
			case Tank.RIGHT:
				x += w;
				y += h / 2;
				break;
			}
			Shell s = new Shell(x, y, p1.getDirect(), Shell.SPEED_SHELL, Color.YELLOW);
			p1_shells.add(s);
			p1.setLastFire(System.currentTimeMillis());
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public boolean isShellHitsTank(Shell s, Tank t) {
		int t_x1, t_y1, t_x2, t_y2;
		int s_x, s_y;
		t_x1 = t.getX();
		t_y1 = t.getY();
		t_x2 = t_x1 + 30;
		t_y2 = t_y1 + 30;
		s_x = s.getX();
		s_y = s.getY();
		if (s_x > t_x1 && s_x < t_x2 && s_y > t_y1 && s_y < t_y2) {
			return true;
		}
		return false;
	}
	
	public void updateCompTankAlive() {
		// update tank and shell alive or not
		for (int i = 0; i < comp_tanks.size(); ++i) {
			ComTank t = comp_tanks.get(i);
			if (!t.isAlive()) {
				continue;
			}
			for (int j = 0; j < p1_shells.size(); ++j) {
				Shell s = p1_shells.get(j);
				if (!s.isAlive()) {
					continue;
				}
				if (isShellHitsTank(s, t)) {
					t.setDead();
					s.setDead();
				}
			}
		}
	}
	
	public void makeCompTankAction() {
		boolean isOverlapping;
		ComTank t = null;
		for (int i = 0; i < comp_tanks.size(); ++i) {
			t = comp_tanks.get(i);
			do {
				isOverlapping = false;
				for (int j = 0; j < comp_tanks.size(); ++i) {
					if (i == j) {
						continue;
					}
					if (t.isOverlappingWith(comp_tanks.get(j))) {
						isOverlapping = true;
						break;
					}
				}
				if (t.isOverlappingWith(p1)) {
					isOverlapping = true;
				}
			} while (isOverlapping);
		}
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			int x_t1, y_t1, x_t2, y_t2;
			int x_s, y_s;
			// Check if game ends
			x_t1 = p1.getX();
			x_t2 = x_t1 + PlayerTank.W;
			y_t1 = p1.getY();
			y_t2 = y_t1 + PlayerTank.H;
			for (Shell s : comp_shells) {
				x_s = s.getX();
				y_s = s.getY();
				if (x_s > x_t1 && x_s < x_t2 && y_s > y_t1 && y_s < y_t2) {
					// game should end here
					System.out.println("Defeated");
					System.exit(1);
				}
			}
			
			updateCompTankAlive();
			
			// remove dead tanks
			for (int i = 0; i < comp_tanks.size(); ++i) {
				ComTank t = comp_tanks.get(i);
				if (!t.isAlive()) {
					comp_tanks.remove(t);
				}
			}
			
			// remove dead shells
			for (int i = 0; i < comp_shells.size(); ++i) {
				Shell s = comp_shells.get(i);
				if (!s.isAlive()) {
					comp_shells.remove(s);
				}
			}
			for (int i = 0; i < p1_shells.size(); ++i) {
				Shell s = p1_shells.get(i);
				if (!s.isAlive()) {
					p1_shells.remove(s);
				}
			}
			
//			makeCompTankAction();
			
			// update tank and shell alive or not
//			for (int i = 0; i < comp_tanks.size(); ++i) {
//				ComTank t = comp_tanks.get(i);
//				x_t1 = t.getX();
//				y_t1 = t.getY();
//				x_t2 = x_t1 + 30;
//				y_t2 = y_t1 + 30;
//				for (int j = 0; j < p1_shells.size(); ++j) {
//					Shell s = p1_shells.get(j);
//					x_s = s.getX();
//					y_s = s.getY();
//					if (x_s > x_t1 && x_s < x_t2 && y_s > y_t1 && y_s < y_t2) {
//						t.setDead();
//						s.setDead();
//						comp_tanks.remove(t);
//						p1_shells.remove(s);
//					}
//				}
//			}
			
			
			repaint();
		}
	}
}