package a2geek.games.mousemaze2001.mazeobjects;
import java.awt.*;

/**
 * Represents a maze object.
 * 
 * Creation date: (10/14/01 9:38:50 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/23/2001 23:16:23 
 */
public abstract class MazeObject extends Canvas {
/**
 * MazeObject constructor comment.
 */
public MazeObject() {
	super();
}


/**
 * Returns true if this is a bomb.
 *
 * Creation date: (10/14/01 9:43:27 PM)
 */
public boolean isBomb() {
	return false;
}


/**
 * Returns true if this is an exit.
 *
 * Creation date: (10/14/01 9:43:27 PM)
 */
public boolean isExit() {
	return false;
}


/**
 * Returns true if this is an explosion.
 *
 * Creation date: (10/14/01 9:42:51 PM)
 */
public boolean isExplosion() {
	return false;
}


/**
 * Returns true if this is a mine.
 *
 * Creation date: (10/14/01 9:43:56 PM)
 */
public boolean isMine() {
	return false;
}


/**
 * Returns true if this is the mouse.
 *
 * Creation date: (10/14/01 9:43:11 PM)
 */
public boolean isMouse() {
	return false;
}


/**
 * Returns true if this is a robot.
 *
 * Creation date: (10/14/01 9:42:51 PM)
 */
public boolean isRobot() {
	return false;
}


/**
 * Draw image on screen.
 * It is expected that the region to be drawn has been clipped.
 * See Graphics.create for more details.
 *
 * Creation date: (10/14/01 9:45:16 PM)
 */
public void paint(Graphics g) {
}
}