package a2geek.games.mousemaze2001.mazeobjects;
/**
 * Provide an animated bomb.
 * 
 * Creation date: (10/26/01 11:01:35 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/27/2001 01:02:48 
 */
public class AnimatedBomb extends AnimatedMouseMazeImageObject {
/**
 * AnimatedMine constructor comment.
 */
public AnimatedBomb() {
	super();
}


/**
 * Answers with the image names for this object.
 *
 * Creation date: (10/26/01 11:01:35 PM)
 */
protected String[] getImageNames() {
	return new String[] {
			"OriginalBomb0.gif", 
			"OriginalBomb1.gif", 
			"OriginalBomb2.gif", 
			"OriginalBomb3.gif"
		};
}


/**
 * Returns true if this is a bomb.
 *
 * Creation date: (10/14/01 9:43:56 PM)
 */
public boolean isBomb() {
	return true;
}
}