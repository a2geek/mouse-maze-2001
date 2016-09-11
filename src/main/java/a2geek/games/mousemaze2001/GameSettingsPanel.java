package a2geek.games.mousemaze2001;
import java.util.*;
import javax.swing.border.*;

import a2geek.games.mousemaze2001.domain.*;
import a2geek.games.mousemaze2001.images.ImageManager;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

/**
 * Provides the panel used for game settings.
 * 
 * Creation date: (10/27/01 7:32:59 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/29/2001 22:40:54 
 */
public class GameSettingsPanel extends JPanel implements ActionListener {
	private static final String HARD = "hard";
	private static final String EASY = "easy";
	private static final String DEFAULT = "default";

	private JSlider bombsPerLevel;
	private JSlider robotRange;
	private JSlider robotShotFrequency;
	private JSlider robotMineFrequency;
	private JCheckBox mouseShoots;
	private JCheckBox robotShotDistanceFixed;
	private JCheckBox animatedImages;
	private JCheckBox unlimitedLevels;
	private JCheckBox unlimitedLives;
	private JCheckBox shieldedRobots;

	private GameSettings easySettings;
	private GameSettings hardSettings;
	private GameSettings defaultSettings;
	private GameSettings currentSettings;

/**
 * GameSettingsPanel constructor comment.
 */
public GameSettingsPanel() {
	super();
	initializeSettings();
	initializeComponents();
	copyFromCurrentSettings();
}


/**
 * Handle action events from buttons.
 *
 * Creation date: (10/27/01 7:45:27 PM)
 */
public void actionPerformed(ActionEvent actionEvent) {
	String command = actionEvent.getActionCommand();
	if (command != null) {
		if (DEFAULT.equals(command)) {
			currentSettings = new GameSettings(defaultSettings);
		} else if (EASY.equals(command)) {
			currentSettings = new GameSettings(easySettings);
		} else if (HARD.equals(command)) {
			currentSettings = new GameSettings(hardSettings);
		}
		copyFromCurrentSettings();
	}
}


/**
 * Copy the current settings values to the screen components.
 *
 * Creation date: (10/27/01 11:38:39 PM)
 */
public void copyFromCurrentSettings() {
	bombsPerLevel.setMaximum(currentSettings.getMaxBombsPerLevel());
	bombsPerLevel.setValue(currentSettings.getBombsPerLevel());
	robotRange.setMaximum(currentSettings.getMaxRobotShotRange());
	robotRange.setValue(currentSettings.getRobotShotRange());
	robotShotDistanceFixed.setSelected(currentSettings.isFixedRobotShotRange());
	robotShotFrequency.setValue(currentSettings.getRobotShootFrequency());
	robotMineFrequency.setValue(currentSettings.getRobotMineFrequency());
	mouseShoots.setSelected(currentSettings.isShootingMouse());
	animatedImages.setSelected(currentSettings.isAnimatedImages());
	unlimitedLevels.setSelected(currentSettings.isUnlimitedGameLevels());
	unlimitedLives.setSelected(currentSettings.isUnlimitedLives());
	shieldedRobots.setSelected(currentSettings.isShieldedRobots());
	repaint();
}


/**
 * Copy the current settings values from the screen components.
 *
 * Creation date: (10/27/01 11:38:39 PM)
 */
public void copyToCurrentSettings() {
	currentSettings.setBombsPerLevel(bombsPerLevel.getValue());
	currentSettings.setRobotShotRange(robotRange.getValue());
	currentSettings.setFixedRobotShotRange(robotShotDistanceFixed.isSelected());
	currentSettings.setRobotShootFrequency(robotShotFrequency.getValue());
	currentSettings.setRobotMineFrequency(robotMineFrequency.getValue());
	currentSettings.setShootingMouse(mouseShoots.isSelected());
	currentSettings.setAnimatedImages(animatedImages.isSelected());
	currentSettings.setUnlimitedGameLevels(unlimitedLevels.isSelected());
	currentSettings.setUnlimitedLives(unlimitedLives.isSelected());
	currentSettings.setShieldedRobots(shieldedRobots.isSelected());
}


/**
 * Create a bordered panel.
 *
 * Creation date: (10/27/01 8:35:30 PM)
 */
protected JPanel createBorderedPanel(String title) {
	return createBorderedPanel(title, BoxLayout.Y_AXIS);
}


/**
 * Create a bordered panel.
 *
 * Creation date: (10/27/01 8:35:30 PM)
 */
protected JPanel createBorderedPanel(String title, int axis) {
	JPanel thePanel = new JPanel();
	thePanel.setLayout(new BoxLayout(thePanel, axis));
	TitledBorder titledBorder = new TitledBorder(title);
	titledBorder.setTitleColor(Color.black);
	thePanel.setBorder(titledBorder);
	return thePanel;
}


/**
 * Create a standard image button.
 *
 * Creation date: (10/27/01 3:05:07 PM)
 */
protected JButton createImageButton(String resourceName, String commandString) {
	return createImageButton(resourceName, commandString, this);
}


/**
 * Create a standard image button.
 *
 * Creation date: (10/27/01 3:05:07 PM)
 */
protected JButton createImageButton(String resourceName, String commandString, ActionListener actionListener) {
	JButton button = new JButton(new ImageIcon(ImageManager.getInstance().getImage(resourceName)));
	button.setActionCommand(commandString);
	button.addActionListener(actionListener);
	return button;
}


/**
 * Create a slider panel.
 *
 * Creation date: (10/27/01 8:38:48 PM)
 */
protected JSlider createSlider(int minimumValue, int maximumValue, int currentValue) {
	JSlider theSlider = new JSlider(minimumValue, maximumValue, currentValue);
	if (maximumValue >= 100) {
		theSlider.setMinorTickSpacing(1);
		theSlider.setMajorTickSpacing(10);
	} else {
		theSlider.setMajorTickSpacing(1);
	}
	theSlider.setPaintTicks(true);
	theSlider.setPaintLabels(true);
	theSlider.setSnapToTicks(true);
	theSlider.setForeground(Color.black);
	// change the foreground color of all labels (numbers)
	Dictionary table = theSlider.getLabelTable();
	Enumeration e = table.elements();
	while (e.hasMoreElements()) {
		JLabel label = (JLabel) e.nextElement();
		label.setForeground(Color.black);
	}
	
	return theSlider;
}


/**
 * Retrieve the current game settings.
 *
 * Creation date: (10/28/01 1:23:58 PM)
 */
public GameSettings getCurrentSettings() {
	copyToCurrentSettings();
	return currentSettings;
}


/**
 * Initialize the graphical components.
 *
 * Creation date: (10/27/01 11:18:17 PM)
 */
protected void initializeComponents() {
	GameSettings curs = currentSettings;
	JPanel everything = new JPanel();
	BoxLayout boxLayout = new BoxLayout(everything, BoxLayout.Y_AXIS);
	everything.setLayout(boxLayout);

	JPanel bombsPanel = createBorderedPanel("Bombs per level");
	bombsPerLevel = createSlider(0,curs.getMaxBombsPerLevel(),curs.getBombsPerLevel());
	bombsPanel.add(bombsPerLevel);

	JPanel rangePanel = createBorderedPanel("Range of robot shooting");
	robotRange = createSlider(1,curs.getMaxRobotShotRange(),curs.getRobotShotRange());
	robotShotDistanceFixed = new JCheckBox("Robots can only shoot the maximum distance (specified above)");
	robotShotDistanceFixed.setSelected(curs.isFixedRobotShotRange());
	robotShotDistanceFixed.setHorizontalAlignment(JCheckBox.LEFT);
	rangePanel.add(robotRange);
	rangePanel.add(robotShotDistanceFixed);

	JPanel freqPanel = createBorderedPanel("Robot shooting frequency (%)");
	robotShotFrequency = createSlider(0,100,curs.getRobotShootFrequency());
	freqPanel.add(robotShotFrequency);

	JPanel minePanel = createBorderedPanel("Robot mine dropping frequency (%)");
	robotMineFrequency = createSlider(0,100,curs.getRobotMineFrequency());
	minePanel.add(robotMineFrequency);

	JPanel settingsPanel = createBorderedPanel("Other settings");
	mouseShoots = new JCheckBox("Honestly, mice can shoot. Really!");
	mouseShoots.setSelected(curs.isShootingMouse());
	animatedImages = new JCheckBox("Animated images");
	animatedImages.setSelected(curs.isAnimatedImages());
	unlimitedLevels = new JCheckBox("Unlimited levels");
	unlimitedLevels.setSelected(curs.isUnlimitedGameLevels());
	unlimitedLives = new JCheckBox("Unlimited lives");
	unlimitedLives.setSelected(curs.isUnlimitedLives());
	shieldedRobots = new JCheckBox("Are robots shielded?");
	shieldedRobots.setSelected(curs.isShieldedRobots());
	settingsPanel.add(mouseShoots);
	settingsPanel.add(shieldedRobots);
	settingsPanel.add(animatedImages);
	settingsPanel.add(unlimitedLevels);
	settingsPanel.add(unlimitedLives);

	JPanel predefinedPanel = createBorderedPanel("Predefined game settings");
	predefinedPanel.setLayout(new GridLayout(3,1));
	JButton easyButton = createImageButton("EasyButton.gif", EASY);
	JButton defaultButton = createImageButton("DefaultButton.gif", DEFAULT);
	JButton hardButton = createImageButton("HardButton.gif", HARD);
	predefinedPanel.add(easyButton);
	predefinedPanel.add(defaultButton);
	predefinedPanel.add(hardButton);

	JPanel comboPanel = new JPanel(new GridLayout(1,2));
	comboPanel.add(predefinedPanel);
	comboPanel.add(settingsPanel);

	everything.add(bombsPanel);
	everything.add(rangePanel);
	everything.add(freqPanel);
	everything.add(minePanel);
	everything.add(comboPanel);

	// apparantly these need to be separate panels...
	JPanel dividerPanel = new JPanel();
	JPanel dividerPanel2 = new JPanel();
	JPanel dividerPanel3 = new JPanel();
	JPanel dividerPanel4 = new JPanel();
	
	this.setLayout(new BorderLayout());
	this.add(dividerPanel, BorderLayout.WEST);
	this.add(dividerPanel2, BorderLayout.EAST);
	this.add(dividerPanel3, BorderLayout.SOUTH);
	this.add(dividerPanel4, BorderLayout.NORTH);
	this.add(everything, BorderLayout.CENTER);
}


/**
 * Initialize the predefined Settings.
 *
 * Creation date: (10/27/01 11:20:00 PM)
 */
protected void initializeSettings() {
	currentSettings = new GameSettings(MazeDomain.getInstance().getGameSettings());
	easySettings = new GameSettings();
	easySettings.load("easy.properties");
	hardSettings = new GameSettings();
	hardSettings.load("hard.properties");
	defaultSettings = new GameSettings();
	defaultSettings.load("default.properties");
}


/**
 * Set the current game settings.
 *
 * Creation date: (10/28/01 1:23:14 PM)
 */
public void setCurrentSettings(GameSettings gameSettings) {
	currentSettings = new GameSettings(gameSettings);
	copyFromCurrentSettings();
}
}