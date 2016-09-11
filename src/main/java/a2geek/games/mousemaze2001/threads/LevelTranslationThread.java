package a2geek.games.mousemaze2001.threads;

import a2geek.games.mousemaze2001.MouseMaze2001;
import a2geek.games.mousemaze2001.domain.MazeDomain;
import a2geek.games.mousemaze2001.mazeobjects.*;

/**
 * This thread will pause the game while the map is redrawn; semi artistically.
 * 
 * Creation date: (10/25/01 11:45:57 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 11/05/2001 22:35:58 
 */
public class LevelTranslationThread extends Thread {
	private int delay;
	private MazeObject[][] newMap;

/**
 * LevelTranslationThread constructor comment.
 */
public LevelTranslationThread(String message, MazeObject[][] map) {
	this(message, 5, map);
}


/**
 * LevelTranslationThread constructor comment.
 */
public LevelTranslationThread(String message, int delay, MazeObject[][] map) {
	super();
	MazeDomain.getInstance().setPause(message);
	MouseMaze2001.getInstance().repaintNeeded();
	this.newMap = map;
	this.delay = delay;
}


/**
 * Pause for a short period of time and then disable the game pause.
 *
 * Creation date: (10/25/01 11:49:16 PM)
 */
public void run() {
	MazeDomain domain = MazeDomain.getInstance();
	MouseMaze2001 controller = MouseMaze2001.getInstance();
	GreenTile greenTile = new GreenTile();
	PurpleTile purpleTile = new PurpleTile();
	try {
		for (int x=0; x<domain.getMapWidth(); x++) {
			for (int y=0; y<domain.getMapHeight(); y++) {
				domain.setMazeObject(x, y, greenTile);
				controller.repaint();
				sleep(delay);
				domain.setMazeObject(x, y, purpleTile);
				controller.repaint();
				sleep(delay);
				domain.setMazeObject(x, y, newMap[x][y]);
				controller.repaint();
				sleep(delay);
			}
		}
	} catch (InterruptedException ex) {
		// ignore
	}
	domain.setMap(newMap);	// in case thread was interrupted
	domain.setPause(null);
}
}