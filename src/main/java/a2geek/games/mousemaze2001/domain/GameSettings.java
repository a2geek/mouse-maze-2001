package a2geek.games.mousemaze2001.domain;
import java.io.*;
import java.util.*;
/**
 * Represents all the configurable information of the game.
 * 
 * Creation date: (10/27/01 2:39:27 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/29/2001 22:40:54 
 */
public class GameSettings {
	private static final String BASE_PATH = "/settings/%s";
	/*
	 * Indicates if the mouse is able to shoot.
	 */
	private boolean shootingMouse;
	/*
	 * Number of bombs added per level.  Set to 0 for none.
	 */
	private int bombsPerLevel;
	/*
	 * Maximum number of bombs to have per level.
	 * Note: Not used by game itself, used by preferences screen.
	 */
	private int maxBombsPerLevel;
	/*
	 * Range that robots can "see" or target the mouse.  When the mouse
	 * is within this range, the robot may shoot.
	 */
	private int robotVisibilityRange;
	/*
	 * Maximum robot visibility range.
	 * Note: Not used by game itself, used by preferences screen.
	 */
	private int maxRobotVisibilityRange;
	/*
	 * Longest shot that a robot can take.
	 */
	private int robotShotRange;
	/*
	 * Maximum shot range that can be chosen.
	 * Note: Not used by game itself, used by preferences screen.
	 */
	private int maxRobotShotRange;
	/*
	 * Can robots only shoot at their maximum range?
	 * (For example, maybe the mouse gets "too close" and under the gun.)
	 */
	private boolean fixedRobotShotRange;
	/*
	 * When the mouse is within shooting range, this is the percent chance that
	 * the robot will actually shoot.
	 */
	private int robotShootFrequency;
	/*
	 * Should the game use animated images, or does that annoy the player?
	 */
	private boolean animatedImages;
	/*
	 * How frequently should the robot drop mines?
	 */
	private int robotMineFrequency;
	/*
	 * Cheat: Game is too short, lets make it go on forever!
	 */
	private boolean unlimitedGameLevels;
	/*
	 * Cheat: Game is too hard, mouse is always reincarnated.
	 */
	private boolean unlimitedLives;
	/*
	 * Allow robots have shielding.
	 */
	private boolean shieldedRobots;

/**
 * GameSettings constructor comment.
 */
public GameSettings() {
	super();
}


/**
 * GameSettings constructor comment.
 */
public GameSettings(GameSettings other) {
	super();
	this.setAnimatedImages(other.isAnimatedImages());
	this.setBombsPerLevel(other.getBombsPerLevel());
	this.setFixedRobotShotRange(other.isFixedRobotShotRange());
	this.setMaxBombsPerLevel(other.getMaxBombsPerLevel());
	this.setMaxRobotShotRange(other.getMaxRobotShotRange());
	this.setMaxRobotVisibilityRange(other.getMaxRobotVisibilityRange());
	this.setRobotMineFrequency(other.getRobotMineFrequency());
	this.setRobotShootFrequency(other.getRobotShootFrequency());
	this.setRobotShotRange(other.getRobotShotRange());
	this.setRobotVisibilityRange(other.getRobotVisibilityRange());
	this.setShootingMouse(other.isShootingMouse());
	this.setUnlimitedGameLevels(other.isUnlimitedGameLevels());
	this.setUnlimitedLives(other.isUnlimitedLives());
	this.setShieldedRobots(other.isShieldedRobots());
}


/**
 * Retrieve the number of bombs per level.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return int
 */
public int getBombsPerLevel() {
	return bombsPerLevel;
}


/**
 * Get the maximum bombs per level.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return int
 */
public int getMaxBombsPerLevel() {
	return maxBombsPerLevel;
}


/**
 * Get the maximum robot shot range.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return int
 */
public int getMaxRobotShotRange() {
	return maxRobotShotRange;
}


/**
 * Get the maximum robot visibility range.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return int
 */
public int getMaxRobotVisibilityRange() {
	return maxRobotVisibilityRange;
}


/**
 * Get the robot mining frequency.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return int
 */
public int getRobotMineFrequency() {
	return robotMineFrequency;
}


/**
 * Get the robot shooting frequency.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return int
 */
public int getRobotShootFrequency() {
	return robotShootFrequency;
}


/**
 * Get the range of a robot shot.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return int
 */
public int getRobotShotRange() {
	return robotShotRange;
}


/**
 * Get the range a robot can see.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return int
 */
public int getRobotVisibilityRange() {
	return robotVisibilityRange;
}


/**
 * Are there animated images?
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return boolean
 */
public boolean isAnimatedImages() {
	return animatedImages;
}


/**
 * Do the robots have a fixed shot range?
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return boolean
 */
public boolean isFixedRobotShotRange() {
	return fixedRobotShotRange;
}


/**
 * Answer if the robots have shielding.
 *
 * Creation date: (10/29/01 10:05:30 PM)
 */
public boolean isShieldedRobots() {
	return shieldedRobots;
}


/**
 * Can this mouse shoot?
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return boolean
 */
public boolean isShootingMouse() {
	return shootingMouse;
}


/**
 * Are we too good for the game?
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return boolean
 */
public boolean isUnlimitedGameLevels() {
	return unlimitedGameLevels;
}


/**
 * Are we cheating?
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @return boolean
 */
public boolean isUnlimitedLives() {
	return unlimitedLives;
}


/**
 * Load the game settings from a Properties file.
 *
 * Creation date: (10/27/01 11:21:59 PM)
 */
public void load(String filename) {
	Properties props = new Properties();
	try {
		props.load(getClass().getResourceAsStream(String.format(BASE_PATH,filename)));
	} catch (IOException ex) {
		// ignore - default settings are set
	}
	setAnimatedImages(new Boolean(props.getProperty("animatedImages","true")).booleanValue());
	setBombsPerLevel(Integer.parseInt(props.getProperty("bombsPerLevel","2")));
	setFixedRobotShotRange(new Boolean(props.getProperty("fixedRobotShotRange","true")).booleanValue());
	setMaxBombsPerLevel(Integer.parseInt(props.getProperty("maxBombsPerLevel","5")));
	setMaxRobotShotRange(Integer.parseInt(props.getProperty("maxRobotShotRange","4")));
	setMaxRobotVisibilityRange(Integer.parseInt(props.getProperty("maxRobotVisibilityRange","5")));
	setRobotMineFrequency(Integer.parseInt(props.getProperty("robotMineFrequency","20")));
	setRobotShootFrequency(Integer.parseInt(props.getProperty("robotShootFrequency","80")));
	setRobotShotRange(Integer.parseInt(props.getProperty("robotShotRange","2")));
	setRobotVisibilityRange(Integer.parseInt(props.getProperty("robotVisibilityRange","4")));
	setShootingMouse(new Boolean(props.getProperty("shootingMouse","true")).booleanValue());
	setUnlimitedGameLevels(new Boolean(props.getProperty("unlimitedGameLevels","false")).booleanValue());
	setUnlimitedLives(new Boolean(props.getProperty("unlimitedLives","false")).booleanValue());
	setShieldedRobots(new Boolean(props.getProperty("shieldedRobots","false")).booleanValue());
}


/**
 * Set the animated images flag.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newAnimatedImages boolean
 */
public void setAnimatedImages(boolean newAnimatedImages) {
	animatedImages = newAnimatedImages;
}


/**
 * Set the number of bombs per level.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newBombsPerLevel int
 */
public void setBombsPerLevel(int newBombsPerLevel) {
	bombsPerLevel = newBombsPerLevel;
}


/**
 * Set the fixed range of the robots.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newFixedRobotShotRange boolean
 */
public void setFixedRobotShotRange(boolean newFixedRobotShotRange) {
	fixedRobotShotRange = newFixedRobotShotRange;
}


/**
 * Set the maximum bombs allowed per level.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newMaxBombsPerLevel int
 */
public void setMaxBombsPerLevel(int newMaxBombsPerLevel) {
	maxBombsPerLevel = newMaxBombsPerLevel;
}


/**
 * Set the maximum robot shot range.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newMaxRobotShotRange int
 */
public void setMaxRobotShotRange(int newMaxRobotShotRange) {
	maxRobotShotRange = newMaxRobotShotRange;
}


/**
 * Set the maximum robot visibility range.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newMaxRobotVisibilityRange int
 */
public void setMaxRobotVisibilityRange(int newMaxRobotVisibilityRange) {
	maxRobotVisibilityRange = newMaxRobotVisibilityRange;
}


/**
 * Set the robot mining frequency.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newRobotMineFrequency int
 */
public void setRobotMineFrequency(int newRobotMineFrequency) {
	robotMineFrequency = newRobotMineFrequency;
}


/**
 * Set the robot shooting frequency.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newRobotShootFrequency int
 */
public void setRobotShootFrequency(int newRobotShootFrequency) {
	robotShootFrequency = newRobotShootFrequency;
}


/**
 * Set the robot shooting distance.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newRobotShotRange int
 */
public void setRobotShotRange(int newRobotShotRange) {
	robotShotRange = newRobotShotRange;
}


/**
 * Set the robot visibility range.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newRobotVisibilityRange int
 */
public void setRobotVisibilityRange(int newRobotVisibilityRange) {
	robotVisibilityRange = newRobotVisibilityRange;
}


/**
 * Set if the robots have shielding.
 *
 * Creation date: (10/29/01 10:05:57 PM)
 */
public void setShieldedRobots(boolean newShieldedRobots) {
	shieldedRobots = newShieldedRobots;
}


/**
 * Tell me if this mouse is Rambo.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newShootingMouse boolean
 */
public void setShootingMouse(boolean newShootingMouse) {
	shootingMouse = newShootingMouse;
}


/**
 * Way cool game, but way too easy.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newUnlimitedGameLevels boolean
 */
public void setUnlimitedGameLevels(boolean newUnlimitedGameLevels) {
	unlimitedGameLevels = newUnlimitedGameLevels;
}


/**
 * Way cool game, but way too hard.
 * 
 * Creation date: (10/27/01 2:51:10 PM)
 * @param newUnlimitedLives boolean
 */
public void setUnlimitedLives(boolean newUnlimitedLives) {
	unlimitedLives = newUnlimitedLives;
}
}