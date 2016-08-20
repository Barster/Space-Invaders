package SpaceInvaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpaceInvaders extends JPanel {

	public static SpaceInvaders space;
	Ship ship = new Ship(space);
	static MobHandler mh = new MobHandler(space);
	
	static Thread t;
	
	public static final int GWIDTH = 500, GHEIGHT = 400;
	public int level = 1;
	
	public boolean inGame = false, running = false;
	
	public boolean allMobsKilled = false;
	public boolean clickedPlay = false;
	
	public boolean mainScreenOpen = false;
	public boolean gameOver = false;
	
	public Rectangle main;
	public int MAIN_X = 0, MAIN_Y = 0;
	
	// 1 = MAIN MENU
	// 2 = IN GAME
	// 3 = END OF GAME
	// 4 = WAITING FOR PLAY
	
	public SpaceInvaders() {
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				ship.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				ship.keyReleased(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}
			
		});
	
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!inGame || allMobsKilled == true);
					clickedPlay = clickedPlay(e);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
		
		setFocusable(true);
		requestFocus();
		run();
	}
	
	private boolean clickedPlay(MouseEvent e) {
		Rectangle mouse = new Rectangle(e.getX(), e.getY(), 1, 1);
		Rectangle play  = new Rectangle(MAIN_X, MAIN_Y, 150, 30);
		
		return mouse.intersects(play);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.BLACK);
		
		if (running) {
				if (inGame) {
					if (clickedPlay) {
						if (allMobsKilled) {
							inGame = false;
							showMainMenu(g);
						} else {
							drawGame(g2d);
						}
					} else {
						showMainMenu(g);
					}
				} else {
					showMainMenu(g);
				}
		} else {
			showMainMenu(g);
		}
		
	}
	
	private void drawGame(Graphics2D g2d) {
		ship.paint(g2d);
		mh.paint(g2d);
	}
	
	public static void main(String[] args) {
		initGame();
	}
	
	public static void initGame() {
		JFrame game = new JFrame("Space Invaders");
		space = new SpaceInvaders();
		
		game.setSize(GWIDTH, GHEIGHT);
		game.setResizable(false);
		game.setLocationRelativeTo(null);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.add(space);
		game.setVisible(true);
		game.requestFocus();
		
	}

	private void showMainMenu(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.darkGray);
		
		MAIN_X = GWIDTH / 2 - 75; MAIN_Y = GHEIGHT / 2 - 40;
		main = new Rectangle(MAIN_X, MAIN_Y, 150, 30);

		g2d.fill(main);
		
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setFont(new Font("Veranda", Font.BOLD, 20));
		
		String msg = "PLAY";
		
		// Setting the buttons and which string to display
		// Between levels 1 and 6 we display "continue", and the end
		// of level 6 we display "congrats", as that is the end of the game.
		if (level > 1 && level <= 6) {
			msg = "CONTINUE";
			g2d.drawString(msg, GWIDTH / 2 - 50, GHEIGHT / 2 - 18);
		} else if (level == 1) {
			g2d.drawString(msg, GWIDTH / 2 - 25, GHEIGHT / 2 - 18);
		} else if (level > 6) {
			msg = "CONGRATS!";
			g2d.drawString(msg, GWIDTH / 2 - 60, GHEIGHT / 2 - 18);
		}

		mainScreenOpen = true;
	}
	
	
	public void run() {
		
		t = new Thread(new Runnable() {

			@Override
			public void run() {
				
				running = level <= 6; // MAX LEVELS = 6
				
				while (running) {
					setLevel(level);
					
					allMobsKilled = false;
					inGame = mh.remainingMobs > 0;

					while (inGame) {

						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}	
				
						space.move();
					   	mh.moveMobs();
					   space.repaint();
					   space.requestFocus();
					   inGame = mh.remainingMobs > 0;
					}
					
					allMobsKilled = true;
					clickedPlay = false;
					ship.clearBullets();
					
					level++;
					running = level <= 6; // MAX LEVELS = 6
				}

				System.out.println("You've Won!");
			}
		});
		t.start();
	} //END GAME LOOP
	
	// We set the level and number of mobs with this method
	private void setLevel(int level) {
		if (level == 1) {
			mh.setMobsPerLine(3);
			mh.setNumberOfMobs(9);
		} else if (level == 2) {
			mh.setMobsPerLine(4);
			mh.setNumberOfMobs(20);
		} else if (level == 3) {
			mh.setMobsPerLine(5);
			mh.setNumberOfMobs(25);
		} else if (level == 4) {
			mh.setMobsPerLine(6);
			mh.setNumberOfMobs(36);
		} else if (level == 5) {
			mh.setMobsPerLine(7);
			mh.setNumberOfMobs(49);
		} else if (level == 6) {
			mh.setMobsPerLine(8);
			mh.setNumberOfMobs(64);
		}
	}
	
	public void move() {
		ship.move();
	}
}
