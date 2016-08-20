package SpaceInvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Ship {

	public SpaceInvaders si;
	
	private ArrayList bullets = new ArrayList();
	
	private final int MAX_BULLETS = 5;
	
	public int x = 0, xa = 0,
			   y = 0, ya = 0;
	
	public final int X_BOUND_LEFT = 10, X_BOUND_RIGHT = si.GWIDTH - 60;
	public final int SHIP_WIDTH = 40, SHIP_HEIGHT = 40;
	public int cannon_x = 0, cannon_y = 0;
	
	public Ship(SpaceInvaders s) {
		this.si = s;
		x = si.GWIDTH / 2 - SHIP_WIDTH;
		y = (si.GHEIGHT - 50) - SHIP_WIDTH;
		cannon_x = x + (SHIP_WIDTH / 2) - 2;
		cannon_y = y - (SHIP_HEIGHT / 2);
	}
	
	public void clearBullets() {
		bullets.clear();
	}
	
	public void move() {
		if (x + xa >= X_BOUND_LEFT && x + xa <= X_BOUND_RIGHT) {
			x += xa;
			cannon_x += xa;
		}
	}
	
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		// BASE
		g.fillRect(x, y, SHIP_WIDTH, SHIP_HEIGHT);
		// CANNON
		g.fillRect(cannon_x, cannon_y, 5, 20);
		
		if (bullets.size() > 0) {
			g.setColor(Color.RED);
			for (int x = 0; x < bullets.size(); x++) {
				Bullet b = (Bullet) bullets.get(x);
				// if the bullet is on the screen then we continue letting it move, otherwise
				// it's out of bounds and we remove it from the array
				if (b.isOnScreen()) {
					g.fillRect(b.getX(), b.getY(), b.BULLET_WIDTH, b.BULLET_HEIGHT);
					b.moveBullet();
					
					if (checkCollision()) bullets.remove(x);
				} else {
					bullets.remove(x);
 				}
			}
		}
	}
	
	// Simple movement. Left arrow key moves our tank left, Right arrow moves us right, and spacebar shoots a bullet
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			xa = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			xa =  1;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			fireBullet();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) 
			xa = 0;
	}
	
	// Only fires when the number of current visible bullets is less than the maximum
	// allowed to be fired at once
	public void fireBullet() {
		if (bullets.size() < MAX_BULLETS)
			bullets.add(new Bullet(cannon_x + 1, cannon_y));
	}
	
	// Our collision detection. If our bullet intersects a mob then we remove
	// both from the screen, meaning we've killed a mob.
	public boolean checkCollision() {
		ArrayList mobs = si.mh.getMob();
		
		for (int x = 0; x < mobs.size(); x++) {
			for (int y = 0; y < bullets.size(); y ++) {
				
				Bullet b = (Bullet) bullets.get(y);
				Mob    m = (Mob)       mobs.get(x);
				Rectangle r = b.getBounds();
				Rectangle r2 = m.getBounds();
				
				if (r.intersects(r2) && m.isVisible()) {
					System.out.println("Collision!! Number " + x);
					m.setVisible(false);
					//updateMobSpeed(m, mobs.size());
					return true;
				}
			}
		}
		
		return false;
	}
}
