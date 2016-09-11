package a2geek.games.mousemaze2001.mazeobjects;
import java.awt.Point;

import a2geek.games.mousemaze2001.domain.MazeDomain;

/**
 * Represents the animated robot.
 * 
 * Creation date: (10/27/01 12:08:56 AM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/31/2001 22:12:32 
 */
public class AnimatedRobot extends AnimatedMouseMazeImageObject {
	private static int idCounter = 0;
	private int id;
	private Point location;
	private int shieldLevel;
	private Thread controlThread;

/**
 * AnimatedRobot constructor comment.
 */
public AnimatedRobot(Point location, int shieldLevel) {
	super();
	id = idCounter++;
	setLocation(location);
	setShieldLevel(shieldLevel);
	createControlThread();
	controlThread.start();
}


/**
 * Create the control thread.
 *
 * Creation date: (10/29/01 10:55:38 PM)
 */
protected void createControlThread() {
	controlThread = new Thread() {
			private void log(String message) {
				//System.out.println(new java.util.Date() + " (" + id + ") - " + message);
			}
			public void run() {
				MazeDomain domain = MazeDomain.getInstance();
				log("Robot thread started.");
				while (getInstance().isAlive()) {
					if (domain.isGameOver()) break;
					log("Robot alive at " + location.x + "," + location.y);
					try {
						if (getInstance() != null) {
							domain.moveRobot(getInstance());
							sleep(1250);
						}
					} catch (InterruptedException ex) {
						ex.printStackTrace(System.out);
					}
				}
				log("Robot died.");
			}
		};
	controlThread.setDaemon(true);
}


/**
 * Decrease the robots shield level.
 *
 * Creation date: (10/29/01 7:48:53 PM)
 */
public void decreaseShieldLevel() {
	shieldLevel--;
}


/**
 * Provides the animation sequence.
 * This just needs to be an integer - not necessarily in the valid range, as
 * modulo arithmetic will be used.
 *
 * Creation date: (10/26/01 10:55:18 PM)
 */
protected int getAnimationSequence() {
	return (super.getAnimationSequence() % 4) + (shieldLevel * 4);
}


/**
 * Answers with the image names for this object.
 *
 * Creation date: (10/27/01 12:08:56 AM)
 */
protected java.lang.String[] getImageNames() {
	return new String[] {
			"OriginalRobot0.gif",
			"OriginalRobot1.gif",
			"OriginalRobot2.gif",
			"OriginalRobot3.gif",
			"OriginalRobot0shield1.gif",
			"OriginalRobot1shield1.gif",
			"OriginalRobot2shield1.gif",
			"OriginalRobot3shield1.gif",
			"OriginalRobot0shield2.gif",
			"OriginalRobot1shield2.gif",
			"OriginalRobot2shield2.gif",
			"OriginalRobot3shield2.gif"
		};
}


/**
 * Internal hook for control thread.
 *
 * Creation date: (10/29/01 11:00:57 PM)
 */
protected AnimatedRobot getInstance() {
	return this;
}


/**
 * Retrieve the Robots current position.
 * 
 * Creation date: (10/29/01 7:47:51 PM)
 * @return java.awt.Point
 */
public Point getLocation() {
	return location;
}


/**
 * Retrieve the robots current shield level.
 * 
 * Creation date: (10/29/01 7:47:51 PM)
 * @return int
 */
public int getShieldLevel() {
	return shieldLevel;
}


/**
 * Answers true if this robot is still alive.
 *
 * Creation date: (10/29/01 9:38:38 PM)
 */
public boolean isAlive() {
	return (shieldLevel >= 0);
}


/**
 * Returns true if this is a robot.
 *
 * Creation date: (10/14/01 9:42:51 PM)
 */
public boolean isRobot() {
	return true;
}


/**
 * Kill this robot.
 *
 * Creation date: (10/29/01 11:04:18 PM)
 */
public void kill() {
	shieldLevel = -1;
}


/**
 * Set the robots current location.
 * 
 * Creation date: (10/29/01 7:47:51 PM)
 * @param newLocation java.awt.Point
 */
public void setLocation(Point newLocation) {
	location = newLocation;
}


/**
 * Set the robots current shield level.
 * 
 * Creation date: (10/29/01 7:47:51 PM)
 * @param newShieldLevel int
 */
public void setShieldLevel(int newShieldLevel) {
	shieldLevel = newShieldLevel;
}
}