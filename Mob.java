package SpaceInvaders;

import java.awt.Rectangle;

public class Mob {
	
	private final int GWIDTH = 500, GHEIGHT = 400;
	private final int MOB_WIDTH = 20, MOB_HEIGHT = 20;
	
	public int min_x = 0, max_x = 0;
	
	private int x, y;
	
	private boolean isVisible = true;
	
	public static int xa = 0;
	public static int speed = 1;
	
	public Mob(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setLeftBound(int x) {
		this.min_x = x;
	}
	
	public void setRightBound(int x) {
		this.max_x = x;
	}
	
	public void setPersonalBounds(int minX, int maxX) {
		this.min_x = minX;
		this.max_x = maxX;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void move() {
		
		if (isVisible) {
			if (x + xa <= 10) {
				xa = speed;
			} else if (x + xa > GWIDTH - 40) {
				xa = -speed;
			}
			
			x += xa;
		} else {
			
		}
		
	}
	
	public void setDirection(int dir) {
		xa = dir;
	}
	
	public boolean outboundRight() {
		return x + xa > GWIDTH - 40;
	}
	
	public boolean outboundLeft() {
		return x + xa < 10;
	}
	
	// Our mob boundaries, used for hit detection
	public Rectangle getBounds() {
		return new Rectangle(x, y, MOB_WIDTH, MOB_HEIGHT);
	}
	
	public void setVisible(boolean visiblity) {
		isVisible = visiblity;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
}
