package a2geek.games.mousemaze2001.threads;
import java.util.*;

import a2geek.games.mousemaze2001.domain.MazeDomain;

import java.awt.*;

/**
 * This thread sets up the bomb explosion and creates multiple explosion threads.
 * 
 * Creation date: (10/23/01 10:08:07 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/24/2001 22:58:13 
 */
public class BombExplosionThread extends Thread {
	private int delay = 400;
	private Point pt = null;
	private int maxSize = 4;

/**
 * ExplosionThread constructor comment.
 */
public BombExplosionThread(Point pt) {
	super("BombExplosionThread");
	this.pt = pt;
}


/**
 * Perform the threadded process to generate bomb explosions.
 * Generates the initial bomb explosion and then delayed rings around that point.
 * <p>
 * Creation date: (10/23/01 10:08:07 PM)
 */
public void run() {
	try {
		MazeDomain domain = MazeDomain.getInstance();
		Random random = new Random();
		int size = random.nextInt(maxSize-2)+2;
		for (int i=0; i<size; i++) {
			for (int dx=-i; dx<=i; dx++) {
				for (int dy=-i; dy<=i; dy++) {
					if (dx == i || dx == -i || dy == i || dy == -i) {
						Point at = new Point(pt);
						at.translate(dx,dy);
						if (domain.isValidPoint(at)) {
							ExplosionThread explosion = new ExplosionThread(at);
							explosion.start();
						}
					}
				}
			}
			sleep(delay);
		}
	} catch (InterruptedException ex) {
	}
}
}