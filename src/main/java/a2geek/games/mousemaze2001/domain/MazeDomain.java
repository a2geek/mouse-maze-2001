package a2geek.games.mousemaze2001.domain;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.util.*;
import javax.swing.event.ChangeListener;

import a2geek.games.mousemaze2001.mazeobjects.*;
import a2geek.games.mousemaze2001.threads.*;

/**
 * Contain the MouseMaze domain in a singleton instance.
 * Basically, all map related items are here.
 * 
 * Creation date: (10/14/01 9:35:03 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 11/29/2001 22:42:21 
 */
public class MazeDomain {
	private static final String BASE_PATH = "/sounds/%s";
	private static MazeDomain instance = null;
	private MazeObject[][] map = null;
	private int mapWidth = 14;
	private int mapHeight = 9;
	private int mapLevel = 1;
	private int totalMice = 3;
	private int lives;
	private int kills;
	private Point mousePoint = new Point();
	private Point exitPoint = new Point();
	private Vector robots = new Vector();
	private MazeObject mouse = new AnimatedMouse();
	private MazeObject bomb = new AnimatedBomb();
	private MazeObject exit = new Exit();
	private MazeObject mine = new Mine();
	private Vector domainListeners = new Vector();
	private String pauseMessage = null;
	private boolean paused = false;
	private int animationSequence = 0;
	private GameSettings gameSettings;

	private AudioClip moveSound;
	private AudioClip hitSound;

/**
 * MazeDomain constructor comment.
 */
protected MazeDomain() {
	super();
	
	moveSound = Applet.newAudioClip(getClass().getResource(String.format(BASE_PATH,"clickfast.wav")));
	hitSound = Applet.newAudioClip(getClass().getResource(String.format(BASE_PATH,"boing.wav")));
}


/**
 * Add a DomainListener.
 *
 * Creation date: (10/22/01 9:49:44 PM)
 */
public void addDomainListener(DomainListener domainListener) {
	domainListeners.add(domainListener);
}


/**
 * Determine if there are any explosions on the screen.
 * After explosions have disappered, checks to see if the terminate
 * message should be sent.  This allows the message to be displayed
 * stating that the game is over.
 *
 * Creation date: (10/26/01 12:12:54 PM)
 */
public boolean areExplosionsPresent() {
	for (int x=0; x<getMapWidth(); x++) {
		for (int y=0; y<getMapHeight(); y++) {
			MazeObject mazeObject = getMazeObject(x,y);
			if (mazeObject != null && mazeObject.isExplosion()) return true;
		}
	}

	// no explosions, check on mouse -- if dead, send terminate message
	// yes, it is a bit of a hack.
	if (getLives() <= 0) {
		notifyDomainListeners("terminate");
	}
	// if we are at the end of the game, terminate also
	if (!gameSettings.isUnlimitedGameLevels() && getLevel() > 5) {
		notifyDomainListeners("terminate");
	}
	return false;
}


/**
 * Answer if a mouse is able to shoot in this game.
 *
 * Creation date: (10/28/01 1:32:27 PM)
 */
public boolean canMouseShoot() {
	return gameSettings.isShootingMouse();
}


/**
 * Erase current domain as it relates to a specific game.
 *
 * Creation date: (11/5/01 10:29:06 PM)
 */
public void clear() {
	map = null;
	mapLevel = 1;
	lives = 0;
	kills = 0;
	mousePoint = new Point();
	exitPoint = new Point();
	robots = new Vector();
	pauseMessage = null;
	paused = false;
	animationSequence = 0;
}


/**
 * Initiate the game over sequence.
 *
 * Creation date: (10/26/01 12:54:32 PM)
 */
public void endGame() {
	lives = 0;
	notifyDomainListeners("gameOver");
}


/**
 * Provide random bomb explosions.
 *
 * Creation date: (10/26/01 12:38:22 PM)
 */
public void explodeBombsRandomly() {
	if (isPaused()) return;
	Random random = new Random();
	int x = random.nextInt(getMapWidth()-1);
	int y = random.nextInt(getMapHeight()-1);
	if (isBomb(x,y)) {
		setMazeObject(x,y,null);
		BombExplosionThread bombExplosion = new BombExplosionThread(new Point(x,y));
		bombExplosion.start();
	}
}


/**
 * Generate a map.
 *
 * Creation date: (10/14/01 10:01:32 PM)
 */
public void generateMap() {
	if (mapWidth > 0 && mapHeight > 0 && mapLevel > 0) {
		setPause(true);
		// setup information
		MazeObject[][] oldMap = map;
		map = new MazeObject[mapWidth][mapHeight];
		int midPoint = (mapHeight + 1) / 2 - 1;
		// place mouse
		setMazeObject(0, midPoint, mouse);
		// place exit
		exitPoint = new Point(mapWidth-1, midPoint);
		setMazeObject(exitPoint, exit);
		// place robots
		if (getLevel() >= 5) {
			placeRobot(0, 0);
			placeRobot(0, mapHeight-1);
		}
		for (int i=1; i<=Math.min(getLevel(),4); i++) {
			placeRobot(mapWidth-1, midPoint-i);
			placeRobot(mapWidth-1, midPoint+i);
		}
		// place bombs
		int bombs = mapLevel * gameSettings.getBombsPerLevel();
		Random random = new Random();
		while (bombs > 0) {
			int x = random.nextInt(mapWidth);
			int y = random.nextInt(mapHeight);
			if (getMazeObject(x,y) == null) {
				setMazeObject(x,y,bomb);
				bombs--;
			}
		}
		if (oldMap != null) {
			LevelTranslationThread thread = new LevelTranslationThread("Welcome to level " + getLevel() + "!", map);
			map = oldMap;
			thread.start();
		}
	}
}


/**
 * Retrieve the current animation sequence.
 *
 * Creation date: (10/26/01 10:59:35 PM)
 */
public int getAnimationSequence() {
	return animationSequence;
}


/**
 * Get game settings.
 *
 * Creation date: (10/28/01 12:43:41 PM)
 */
public GameSettings getGameSettings() {
	if (gameSettings == null) {
		gameSettings = new GameSettings();
		gameSettings.load("default.properties");
	}
	return gameSettings;
}


/**
 * Retrieve the singleton instance.
 *
 * Creation date: (10/14/01 9:36:19 PM)
 */
public static MazeDomain getInstance() {
	if (instance == null) {
		instance = new MazeDomain();
	}
	return instance;
}


/**
 * Retrieve the number of robots killed.
 *
 * Creation date: (10/25/01 10:47:06 PM)
 */
public int getKills() {
	return kills;
}


public int getLevel() {
	return mapLevel;
}


/**
 * Return the number of mouse lives left.
 *
 * Creation date: (10/25/01 11:24:01 PM)
 */
public int getLives() {
	return lives;
}


/**
 * Retrieve the current map.
 * 
 * Creation date: (10/14/01 9:35:25 PM)
 */
public MazeObject[][] getMap() {
	return map;
}


/**
 * Retrieve map height.
 *
 * Creation date: (10/14/01 10:11:27 PM)
 */
public int getMapHeight() {
	return mapHeight;
}


/**
 * Retrieve map width.
 *
 * Creation date: (10/14/01 10:11:53 PM)
 */
public int getMapWidth() {
	return mapWidth;
}


/**
 * Get MazeObject.
 *
 * Creation date: (10/14/01 10:10:05 PM)
 */
public MazeObject getMazeObject(int x, int y) {
	MazeObject mazeObject = null;
	if (map != null) {
		mazeObject = map[x][y];
	}
	return mazeObject;
}


/**
 * Get a MazeObject.
 *
 * Creation date: (10/21/01 3:49:44 PM)
 */
public MazeObject getMazeObject(Point pt) {
	return getMazeObject(pt.x,pt.y);
}


public Point getMouseLocation() {
	return mousePoint;
}


public String getPauseMessage() {
	return pauseMessage;
}


/**
 * Return the number of living robots.
 *
 * Creation date: (10/22/01 10:45:58 PM)
 */
public int getRobotCount() {
	if (robots == null) return 0;
	return robots.size();
}


/**
 * Retrieve the number of mouse lives.
 * 
 * Creation date: (10/24/01 10:27:45 PM)
 * @return int
 */
public int getTotalMice() {
	return totalMice;
}


/**
 * Increment the animation sequence.
 *
 * Creation date: (10/26/01 10:59:55 PM)
 */
public void incrementAnimationSequence() {
	animationSequence++;
}


/**
 * Test if a cell has a bomb.
 *
 * Creation date: (10/23/01 10:24:36 PM)
 */
public boolean isBomb(int x, int y) {
	return getMazeObject(x,y) == bomb;
}


/**
 * Answers true if the game has ended.
 * This is used by other threads to force termination.
 *
 * Creation date: (10/26/01 11:05:40 PM)
 */
public boolean isGameOver() {
	return (lives < 1);
}


public boolean isPaused() {
	return paused;
}


/**
 * Test if a point is a valid cell location.
 *
 * Creation date: (10/23/01 10:26:22 PM)
 */
public boolean isValidPoint(int x, int y) {
	return (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight);
}


/**
 * Test if a point is a valid cell location.
 *
 * Creation date: (10/23/01 10:26:22 PM)
 */
public boolean isValidPoint(Point pt) {
	return isValidPoint(pt.x, pt.y);
}


public void moveMouse(int dx, int dy) {
	if (isPaused()) return;
	if (mousePoint == null) return;
	Point pt = new Point(mousePoint);
	pt.translate(dx,dy);
	
	if (isValidPoint(pt) == false) return;

	if (getMazeObject(pt) == exit) {		// end of level
		moveSound.play();
		setMazeObject(mousePoint, null);
		mousePoint = null;
		if (gameSettings.isUnlimitedGameLevels() || getLevel() <= 5) {
			setLevel(getLevel()+1);
			generateMap();
		} else {
			notifyDomainListeners("gameWon");
		}
	} else if (getMazeObject(pt) == null) {	// a valid move
		moveSound.play();
		setMazeObject(mousePoint, null);
		setMazeObject(pt, mouse);
	} else if (getMazeObject(pt) == mine) {	// ouch, that hurts!
		setMazeObject(mousePoint, null);
		setMazeObject(pt, mouse);
		Thread explosion = new ExplosionThread(pt);
		explosion.start();
	} else {
		hitSound.play();
	}
}


/**
 * Provide robot AI.
 * An external thread will trigger this logic.
 *
 * Creation date: (10/22/01 10:47:15 PM)
 * @return moved status (true == moved)
 */
public boolean moveRobot(AnimatedRobot robot) {
	if (isPaused()) return true;
	if (mousePoint == null) return false;
	Point robotPoint = robot.getLocation();
	int dx = (mousePoint.x < robotPoint.x ? -1 : 0) + (mousePoint.x > robotPoint.x ? 1 : 0);
	int dy = (mousePoint.y < robotPoint.y ? -1 : 0) + (mousePoint.y > robotPoint.y ? 1 : 0);
	Random random = new Random();
	// determine if the robot will drop a mine
	boolean willDropMine = (random.nextInt(100) < gameSettings.getRobotMineFrequency());
	MazeObject replacementObject = (willDropMine ? mine : null);
	// try real move
	Point pt = new Point(robotPoint);
	pt.translate(dx,dy);
	if (isValidPoint(pt) && getMazeObject(pt) == null) {
		setMazeObject(robotPoint, replacementObject);
		robotPoint.translate(dx,dy);	// need to keep object intact!
		setMazeObject(robotPoint, robot);
		notifyDomainListeners("robot");
		// attempt to shoot...
		robotShoot(robotPoint);
		return true;
	} else if (isValidPoint(pt) && getMazeObject(pt) == mouse) {
		return false;
	}
	// try random move
	dx = random.nextInt(3) - 1;
	dy = random.nextInt(3) - 1;
	pt = new Point(robotPoint);
	pt.translate(dx,dy);
	if (isValidPoint(pt) && getMazeObject(pt) == null) {
		setMazeObject(robotPoint, replacementObject);
		robotPoint.translate(dx,dy);	// need to keep object intact!
		setMazeObject(robotPoint, robot);
		notifyDomainListeners("robot");
		// attempt to shoot...
		robotShoot(robotPoint);
		return true;
	}
	// shucks...
	return false;
}


/**
 * Invoke this method to start a new game.
 *
 * Creation date: (10/22/01 10:12:50 PM)
 */
public void newGame() {
	setLevel(1);
	lives = getTotalMice();
	kills = 0;
	notifyDomainListeners("newGame");
	generateMap();
}


/**
 * Notify all change listeners.
 *
 * Creation date: (10/22/01 9:51:10 PM)
 */
protected void notifyDomainListeners(String event) {
	Enumeration listeners = domainListeners.elements();
	DomainEvent domainEvent = new DomainEvent(this, event);
	while (listeners.hasMoreElements()) {
		DomainListener listener = (DomainListener) listeners.nextElement();
		listener.domainChanged(domainEvent);
	}
}


/**
 * Place a robot onto the maze. Used as a helper by generateMap.
 *
 * Creation date: (10/30/01 9:46:21 PM)
 */
protected void placeRobot(int x, int y) {
	//System.out.println("Creating new robot at " + x + "," + y + ".");
	int shieldLevels = (getGameSettings().isShieldedRobots() ? 2 : 0);
	Point point = new Point(x, y);
	AnimatedRobot robot = new AnimatedRobot(point, shieldLevels);
	setMazeObject(point, robot);
	robots.add(robot);
}


/**
 * Remove a DomainListener.
 *
 * Creation date: (10/22/01 9:50:42 PM)
 */
public void removeDomainListener(DomainListener domainListener) {
	domainListeners.remove(domainListener);
}


/**
 * Control robot shooting.
 *
 * Creation date: (10/28/01 1:43:21 PM)
 */
protected void robotShoot(Point robotPoint) {
	Random random = new Random();
	int distance = (int)robotPoint.distance(mousePoint);
	boolean closeEnough = distance < gameSettings.getRobotVisibilityRange();
	boolean randomChance = random.nextInt(100) < gameSettings.getRobotShootFrequency();
	if (closeEnough && randomChance) {
		int range;
		if (gameSettings.isFixedRobotShotRange()) {
			range = gameSettings.getRobotShotRange();
		} else {
			range = random.nextInt(gameSettings.getRobotShotRange());
		}
		int dx = (mousePoint.x < robotPoint.x ? -range : 0) + (mousePoint.x > robotPoint.x ? range : 0);
		int dy = (mousePoint.y < robotPoint.y ? -range : 0) + (mousePoint.y > robotPoint.y ? range : 0);
		Point pt = new Point(robotPoint);
		pt.translate(dx,dy);
		if (isValidPoint(pt)) {
			MazeObject mazeObject = getMazeObject(pt);
			if (mazeObject != null && mazeObject.isRobot() == false) {
				Thread explosion = new ExplosionThread(pt);
				explosion.start();
			}
		}
	}
}


/**
 * Set the game settings.
 *
 * Creation date: (10/28/01 12:44:51 PM)
 */
public void setGameSettings(GameSettings newSettings) {
	gameSettings = newSettings;
}


/**
 * Choose a level.
 *
 * Creation date: (10/14/01 10:00:05 PM)
 */
public void setLevel(int level) {
	// set new level
	mapLevel = level;
	notifyDomainListeners("level");
	// kill off any robots
	if (robots != null) {
		for (int i=0; i<robots.size(); i++) {
			AnimatedRobot robot = (AnimatedRobot) robots.elementAt(i);
			robot.kill();
		}
	}
	// erase existing locations
	robots = new Vector();
	mousePoint = new Point();
}


/**
 * Set the current map.
 * 
 * Creation date: (10/14/01 9:35:25 PM)
 */
public void setMap(MazeObject[][] newMap) {
	map = newMap;
}


/**
 * Place a MazeObject into the map.
 *
 * Creation date: (10/30/01 9:37:53 PM)
 */
public synchronized void setMazeObject(int x, int y, MazeObject mazeObject) {
	if (mazeObject == mouse) mousePoint.setLocation(x,y);
	map[x][y] = mazeObject;
}


public void setMazeObject(Point pt, MazeObject mazeObject) {
	setMazeObject(pt.x,pt.y,mazeObject);
}


/**
 * Set maze size.
 *
 * Creation date: (10/14/01 9:56:04 PM)
 */
public void setMazeSize(int width, int height) {
	mapWidth = width;
	mapHeight = height;
}


public void setPause(String message) {
	//System.out.println(new java.util.Date() + " - pause message = " + message);
	setPause(message != null);
	pauseMessage = message;
}


/**
 * Set the paused flag.
 *
 * Creation date: (11/5/01 10:20:27 PM)
 */
public void setPause(boolean pausedFlag) {
	paused = pausedFlag;
}


/**
 * Set the number of mouse lives.
 * 
 * Creation date: (10/24/01 10:27:45 PM)
 * @param newMice int
 */
public void setTotalMice(int newTotalMice) {
	totalMice = newTotalMice;
}


/**
 * Shoot in a particular direction.
 *
 * Creation date: (10/22/01 11:12:01 PM)
 */
protected Point shoot(int dx, int dy) {
	if (mousePoint == null) return null;
	Point pt = new Point(mousePoint);
	pt.translate(dx,dy);
	return shoot(pt);
}


/**
 * Shoot at specified cell.
 * This is used by the ExplosionThread when a Robot is hit.
 *
 * Creation date: (10/23/01 10:56:57 PM)
 */
public Point shoot(Point pt) {
	if (!isPaused() && isValidPoint(pt)) {
		MazeObject mazeObject = getMazeObject(pt);
		if (mazeObject == null) {
			// ignore these!
		} else if (mazeObject.isRobot()) {
			AnimatedRobot robot = (AnimatedRobot) mazeObject;
			robot.decreaseShieldLevel();
			if (robot.isAlive() == false) {
				robots.remove(mazeObject);
				setMazeObject(pt,null);
				kills++;
				notifyDomainListeners("robotShot");
			}
		} else if (mazeObject.isBomb()) {
			setMazeObject(pt, null);
			BombExplosionThread bombExplosion = new BombExplosionThread(pt);
			bombExplosion.start();
		} else if (mazeObject.isMouse()) {
			if (!gameSettings.isUnlimitedLives()) lives--;
			if (lives > 0) {
				setMazeObject(pt, null);
				mousePoint = new Point(0,mapHeight/2);
				setMazeObject(mousePoint, mouse);
				notifyDomainListeners("mouseKilled");
			} else {
				endGame();
			}
		}
	}
	return pt;
}


/**
 * Ensure that map is in sync.
 *
 * Creation date: (10/29/01 10:28:31 PM)
 */
public void synchronizeMap() {
	if (getLives() > 0) setMazeObject(mousePoint, mouse);
	setMazeObject(exitPoint, exit);
	for (int i=0; i<robots.size(); i++) {
		AnimatedRobot robot = (AnimatedRobot) robots.elementAt(i);
		setMazeObject(robot.getLocation(), robot);
	}
}
}