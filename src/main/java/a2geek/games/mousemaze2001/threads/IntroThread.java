package a2geek.games.mousemaze2001.threads;

import a2geek.games.mousemaze2001.IntroPanel;

/**
 * Manage the Intro (demo) control thread.
 * <p>
 * Creation date: (10/20/01 9:08:13 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/23/2001 23:16:23 
 */
public class IntroThread extends ControlledThread {
/**
 * IntroThread constructor comment.
 */
public IntroThread() {
	super();
	setDelay(15);
}


/**
 * Instruct the IntroPanel to perform another "tick".
 * <p>
 * Creation date: (10/20/01 10:25:15 PM)
 */
protected void process() {
	IntroPanel introPanel = IntroPanel.getInstance();
	introPanel.incrementTicker();
	introPanel.repaint();
}
}