package SpaceInvaders;

import java.awt.Rectangle;

public class Bullet {
	
	public final int BULLET_WIDTH = 3, BULLET_HEIGHT = 6;
	private final int GWIDTH = 500, GHEIGHT = 400;
	
	private int x, y;
	public boolean onScreen = false;
	
	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void moveBullet() {
		y-=2;
	}
	
	public boolean isOnScreen() {
		return y > 10;
	}
	
	// Our bullets boundaries, we use this for hit detection (eg, does the bullet boundary cross the mob boundary?)
	public Rectangle getBounds() {
		return new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT);
	}
}
