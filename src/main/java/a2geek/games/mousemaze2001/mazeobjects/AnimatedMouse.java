package a2geek.games.mousemaze2001.mazeobjects;
/**
 * Represents the animated mouse.
 * 
 * Creation date: (10/27/01 12:22:59 AM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/27/2001 01:02:48 
 */
public class AnimatedMouse extends AnimatedMouseMazeImageObject {
/**
 * AnimatedMouse constructor comment.
 */
public AnimatedMouse() {
	super();
}


/**
 * Answers with the image names for this object.
 *
 * Creation date: (10/27/01 12:22:59 AM)
 */
protected String[] getImageNames() {
	return new String[] {
			"OriginalMouse0.gif",
			"OriginalMouse1.gif",
			"OriginalMouse2.gif",
			"OriginalMouse3.gif"
		};
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