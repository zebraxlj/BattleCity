package game_v1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener{
	
	Player p1 = new Player(10, 10, Tank.UP, Tank.SPEED_PLAYER);
	public GamePanel() {
		this.setBackground(Color.BLACK);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawTank(g, p1);
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
	void drawTank(Graphics g, int x, int y, char direct) {
		int[] xPoints = null;
		int[] yPoints = null;
		int nPoints = 3;
		switch (direct) {
		case 'u':
			xPoints = new int[]{x, x+30, x+15};
			yPoints = new int[]{y+30, y+30, y};
			g.fillPolygon(xPoints, yPoints, nPoints);
			break;
		case 'd':
			xPoints = new int[]{x, x+30, x+15};
			yPoints = new int[]{y, y, y+30};
			g.fillPolygon(xPoints, yPoints, nPoints);
			break;
		case 'l':
			xPoints = new int[]{x+30, x+30, x};
			yPoints = new int[]{y, y+30, y+15};
			g.fillPolygon(xPoints, yPoints, nPoints);
			break;
		case 'r':
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
//		System.out.println(p1.getX() + " " + p1.getY());
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
