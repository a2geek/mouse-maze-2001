package a2geek.games.mousemaze2001.mazeobjects;

import a2geek.games.mousemaze2001.mazeobjects.*;

/**
 * Represents the inverted mouse image used for the life indicator.
 * 
 * Creation date: (10/27/01 12:56:35 AM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/29/2001 23:31:16 
 */
public class InvertedMouse extends MazeImageObject {
/**
 * InvertedMouse constructor comment.
 */
public InvertedMouse() {
	super();
}


/**
 * Answers with the image name of this object.
 *
 * Creation date: (10/14/01 10:31:56 PM)
 */
protected String getImageName() {
	return "InvertedMouse.gif";
}


/**
 * Returns true if this is the mouse.
 *
 * Creation date: (10/14/01 9:43:11 PM)
 */
public boolean isMouse() {
	return true;
}
}