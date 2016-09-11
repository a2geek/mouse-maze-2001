package a2geek.games.mousemaze2001.threads;
import java.awt.*;

import a2geek.games.mousemaze2001.MouseMaze2001;
import a2geek.games.mousemaze2001.domain.MazeDomain;
import a2geek.games.mousemaze2001.mazeobjects.*;

/**
 * This thread runs until the explosion effect is done.
 * 
 * Creation date: (10/23/01 10:08:07 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/29/2001 22:40:54 
 */
public class ExplosionThread extends Thread {
	private int delay = 100;
	private int loops = 3;
	private Point pt = null;

/**
 * ExplosionThread constructor comment.
 */
public ExplosionThread(Point pt) {
	super("ExplosionThread");
	this.pt = pt;
	this.setPriority(2);
}


/**
 * Perform the threadded process.
 * <p>
 * Creation date: (10/23/01 10:08:07 PM)
 */
public void run() {
	try {
		MazeDomain domain = MazeDomain.getInstance();
		domain.shoot(pt);
		for (int i=0; i<loops; i++) {
			domain.setMazeObject(pt, new GreenTile());
			MouseMaze2001.getInstance().repaintNeeded();
			sleep(delay);
			domain.setMazeObject(pt, new PurpleTile());
			MouseMaze2001.getInstance().repaintNeeded();
			sleep(delay);
		}
		domain.setMazeObject(pt, null);
		// ensure map is put back in order... specifically, if the mouse
		// was shot in its starting square, it needs to be placed there!
		domain.synchronizeMap();
		MouseMaze2001.getInstance().repaintNeeded();
	} catch (InterruptedException ex) {
	}
}
}