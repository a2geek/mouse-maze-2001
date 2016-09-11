package a2geek.games.mousemaze2001.mazeobjects;
/**
 * Represents a mine.
 * 
 * Creation date: (10/22/01 11:23:56 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/23/2001 23:16:23 
 */
public class Mine extends MazeImageObject {
/**
 * Mine constructor comment.
 */
public Mine() {
	super();
}


/**
 * Answers with the image name of this object.
 *
 * Creation date: (10/22/01 11:23:56 PM)
 */
protected String getImageName() {
	return "OriginalMine.gif";
}


/**
 * Returns true if this is a mine.
 *
 * Creation date: (10/14/01 9:43:56 PM)
 */
public boolean isMine() {
	return true;
}
}