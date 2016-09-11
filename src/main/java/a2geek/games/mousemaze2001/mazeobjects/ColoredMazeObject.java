package a2geek.games.mousemaze2001.mazeobjects;
import java.awt.*;

/**
 * Will draw the MazeObject in a solid color.
 * 
 * Creation date: (10/23/01 9:37:10 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/23/2001 23:16:23 
 */
public abstract class ColoredMazeObject extends MazeObject {
/**
 * ColoredMazeObject constructor comment.
 */
public ColoredMazeObject() {
	super();
}


/**
 * Returns true if this is an explosion.
 *
 * Creation date: (10/14/01 9:42:51 PM)
 */
public boolean isExplosion() {
	return true;
}


/**
 * Draw image on screen.
 * It is expected that the region to be drawn has been clipped.
 * See Graphics.create for more details.
 *
 * Creation date: (10/14/01 9:45:16 PM)
 */
public void paint(Graphics g) {
	Rectangle rect = g.getClipBounds();
	g.setColor(getBackground());
	g.fillRect(0, 0, rect.width, rect.height);
}
}