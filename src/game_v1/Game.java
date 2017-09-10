package game_v1;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Game{
	JFrame jf = null;
	GamePanel gp = null;
	
	public Game() {
		jf = new JFrame("War");
		gp = new GamePanel();
		
		jf.add(gp, BorderLayout.CENTER);
		
		jf.addKeyListener(gp);
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocation(0, 0);
		jf.setResizable(false);
		jf.setSize(800, 600);
		jf.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
