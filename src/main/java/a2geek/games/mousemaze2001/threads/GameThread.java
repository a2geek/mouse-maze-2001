package a2geek.games.mousemaze2001.threads;
import java.awt.Point;
import java.util.*;

import a2geek.games.mousemaze2001.domain.MazeDomain;

/**
 * Provide AI for MouseMaze game.
 * 
 * Creation date: (10/21/01 2:00:35 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/29/2001 23:31:16 
 */
public class GameThread extends ControlledThread {
/**
 * GameThread constructor comment.
 */
public GameThread() {
	super();
	setDelay(300);
}


/**
 * Perform the threadded process.
 * <p>
 * Creation date: (10/21/01 2:00:35 PM)
 */
protected void process() {
	MazeDomain.getInstance().explodeBombsRandomly();
}
}