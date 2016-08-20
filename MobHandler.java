package SpaceInvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MobHandler {
	public static final int MOB_WIDTH = 20, MOB_HEIGHT = 20;
	private static final int GWIDTH = 500, GHEIGHT = 400;
	
	// Store all of the mobs in an array to make it easier to go through them and manipulate
	private static ArrayList mobs = new ArrayList();
	public int remainingMobs = 0;
	
	SpaceInvaders si;
	
	private int x, y = 10;
	private int numOfMobs = 0;
	private int mobsPerLine = 0;
	
	public MobHandler(SpaceInvaders si) {
		this.si = si;
	}
	
	public void setNumberOfMobs(int num) {
		this.numOfMobs = num;
		this.remainingMobs = num;
		initMobs(num);
	}
	
	public void setMobsPerLine(int num) {
		this.mobsPerLine = num;
	}
	
	private void initMobs(int num) {

		int mobX = 10, mobY = 10;
		int rows = numOfMobs / mobsPerLine;
		
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < mobsPerLine; x++) {
				makeMob(mobX, mobY);
	
				mobX += ((GWIDTH - MOB_WIDTH) - 10) / mobsPerLine;

				if (mobX > (GWIDTH - MOB_WIDTH - 10)) {
					mobX = 10;
					break;
				}
			}
			
			mobY += MOB_HEIGHT + 10;
		}
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void makeMob(int x, int y) {
		System.out.println("new mob: " + x + ", " + y);
		mobs.add(new Mob(x, y));
		Mob m = (Mob) mobs.get(mobs.size() - 1);
	}
	
	public boolean isOnScreen() {
		return false;
	}
	
	public void paint(Graphics2D g) {
		g.setColor(Color.BLUE);
		
		for (int x = 0; x < mobs.size(); x++) {
			Mob m = (Mob) mobs.get(x);
			
			if (m.outboundLeft()) m.setDirection(1);
			if (m.outboundRight()) m.setDirection(-1);
			
			if (m.isVisible()) {
				g.fillRect(m.getX(), m.getY(), MOB_WIDTH, MOB_HEIGHT);	
			}
			
			getLeftMostMob().setLeftBound(10);
			getLeftMostMob().setRightBound(GWIDTH - MOB_WIDTH - 10);
			
			getRightMostMob().setLeftBound(10);
			getRightMostMob().setRightBound(GWIDTH - MOB_WIDTH - 10);
			
			remainingMobs = getRemainingMobs();
		}
	}
	
	// Sets the direction of all the mobs to the same direction
	public void setAllDir(int dir) {
		for (int x = 0; x < mobs.size(); x++) {
			Mob m = (Mob) mobs.get(x);
			m.setDirection(dir);
		}
	}
	
	// We get the left most mob on the screen so we can detect when they collide
	// with the left border, letting us know to change direction
	private Mob getLeftMostMob() {
		int num = 0;
		int minX = 999;
		
		for (int x = 0; x < mobs.size(); x++) {
			Mob m = (Mob) mobs.get(x);
			
			if (m.isVisible()) {
				int xx = m.getX();
				if (xx < minX) {
					minX = x;
					num = x;
				}
			}
			
		}
		return (Mob) mobs.get(num);
	}
	
	// We get the right most mob on the screen so we can detect when they collide
	// with the right border, letting us know to change direction
	private Mob getRightMostMob() {
		int num = 0;
		int maxX = 0;
		
		for (int x = 0; x < mobs.size(); x++) {
			Mob m = (Mob) mobs.get(x);
			if (m.isVisible()) {
				int xx = m.getX();
				if (xx > maxX) {
					maxX = x;
					num = x;
				}
			}
		}
		return (Mob) mobs.get(num);
	}
	
	public void moveMobs() {
		for (int x = 0; x < mobs.size(); x++) {
			moveMob((Mob) mobs.get(x));
		}
	}
	
	private void moveMob(Mob mob) {
		mob.move();
	}
	
	public static ArrayList getMob() {
		return mobs;
	}
	
	// Returns the number of mobs that are not killed yet
	private int getRemainingMobs() {
		int num = 0;
		
		for (int x = 0; x < mobs.size(); x++) {
			boolean yes = ((Mob) mobs.get(x)).isVisible();
			
			if (yes) num++;
		}
		
		return num;
	}
	
	// We change the speed of the mobs based on the percentage that haven't been killed
	// The less mobs there are, the faster they move
	private void updateMobSpeed() {
		Mob m = (Mob) mobs.get(0);
		
		int percentLeft = (remainingMobs / numOfMobs) * 100;
		
		if (percentLeft >= 0 && percentLeft <= 24) {
			m.speed = 4;
		} else if (percentLeft >= 25 && percentLeft <= 49) {
			m.speed = 3;
		} else if (percentLeft >= 50 && percentLeft <= 74) {
			m.speed = 2;
		} else if (percentLeft >= 75 && percentLeft <= 100) {
			m.speed = 1;
		}
		
		if (percentLeft <= 50) m.speed = 2;
		System.out.println("Mob speed: " + m.speed);
	}
}
