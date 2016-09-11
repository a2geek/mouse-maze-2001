package a2geek.games.mousemaze2001.threads;

import a2geek.games.mousemaze2001.MouseMaze2001;
import a2geek.games.mousemaze2001.domain.MazeDomain;

/**
 * This thread will pause the game for a short period of time, displaying a message
 * on the screen.
 * 
 * Creation date: (10/25/01 11:45:57 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/31/2001 22:12:32 
 */
public class GameDelayThread extends Thread {
	private int delay;

/**
 * GameDelayThread constructor comment.
 */
public GameDelayThread(String message) {
	this(message, 250);
}


/**
 * GameDelayThread constructor comment.
 */
public GameDelayThread(String message, int delay) {
	super();
	MazeDomain.getInstance().setPause(message);
	MouseMaze2001.getInstance().repaintNeeded();
	this.delay = delay;
}


/**
 * Pause for a short period of time and then disable the game pause.
 *
 * Creation date: (10/25/01 11:49:16 PM)
 */
public void run() {
	try {
		sleep(delay);
		while (MazeDomain.getInstance().areExplosionsPresent()) {
			sleep(50);
		}
	} catch (InterruptedException ex) {
		int i = 0;
		// ignore
	}
	MazeDomain.getInstance().setPause(null);
}
}