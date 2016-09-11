package a2geek.games.mousemaze2001.mazeobjects;
import java.awt.*;
/**
 * Represents the exit.
 * 
 * Creation date: (10/14/01 10:32:40 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/24/2001 22:58:13 
 */
public class Exit extends MazeImageObject {
/**
 * Exit constructor comment.
 */
public Exit() {
	super();
}


/**
 * Answers with the image name of this object.
 *
 * Creation date: (10/14/01 10:32:40 PM)
 */
protected String getImageName() {
	return "OriginalExit.gif";
}


/**
 * Returns true if this is an exit.
 *
 * Creation date: (10/14/01 9:43:27 PM)
 */
public boolean isExit() {
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
	g.setColor(Color.white);
	g.fillRect(0,0,rect.width,rect.height);
	super.paint(g);
}
}