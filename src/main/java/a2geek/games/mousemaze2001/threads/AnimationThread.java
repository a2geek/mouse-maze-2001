package a2geek.games.mousemaze2001.threads;

import a2geek.games.mousemaze2001.MouseMaze2001;
import a2geek.games.mousemaze2001.domain.MazeDomain;

/**
 * Insert the type's description here.
 * 
 * Creation date: (10/27/01 12:38:44 AM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/27/2001 01:02:48 
 */
public class AnimationThread extends Thread {
/**
 * AnimationThread constructor comment.
 */
public AnimationThread() {
	super("AnimationThread");
}


/**
 * Provide animation.
 *
 * Creation date: (10/27/01 12:38:59 AM)
 */
public void run() {
	MazeDomain domain = MazeDomain.getInstance();
	MouseMaze2001 game = MouseMaze2001.getInstance();
	while (domain.isGameOver() == false) {
		try {
			sleep(250);
		} catch (InterruptedException ex) {
		}
		domain.incrementAnimationSequence();
		game.repaintNeeded();
	}
}
}