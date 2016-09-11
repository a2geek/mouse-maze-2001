package a2geek.games.mousemaze2001.mazeobjects;

import a2geek.games.mousemaze2001.domain.MazeDomain;

/**
 * Provides the generic base-class for the MouseMaze 2001 games.
 * 
 * Creation date: (10/26/01 10:58:46 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/27/2001 01:02:48 
 */
public abstract class AnimatedMouseMazeImageObject extends AnimatedMazeImageObject {
/**
 * AnimatedMouseMazeImageObject constructor comment.
 */
public AnimatedMouseMazeImageObject() {
	super();
}


/**
 * Provides the animation sequence.
 * This just needs to be an integer - not necessarily in the valid range, as
 * modulo arithmetic will be used.
 *
 * Creation date: (10/26/01 10:55:18 PM)
 */
protected int getAnimationSequence() {
	return MazeDomain.getInstance().getAnimationSequence();
}
}