package a2geek.games.mousemaze2001;
import java.awt.*;
import javax.swing.*;

import a2geek.games.mousemaze2001.domain.*;
import a2geek.games.mousemaze2001.images.*;
import a2geek.games.mousemaze2001.threads.*;

import java.awt.event.*;

/**
 * Mouse Maze 2001!
 * 
 * Creation date: (10/9/01 10:02:39 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 11/05/2001 22:35:58 
 */
public class MouseMaze2001 extends JFrame implements ActionListener, KeyListener, DomainListener {
	private static MouseMaze2001 instance = new MouseMaze2001();
	private static final String OK = "ok";
	private static final String CANCEL = "cancel";
	private static final String START = "start";
	private static final String QUIT = "quit";
	private static final String PREFERENCES = "preferences";

	private JPanel infoPanel = null;
	private CardLayout infoCardPages = null;
	private JPanel infoCardPanel = null;
	private JPanel logoPanel = null;
	private JPanel controlPanel = null;
	private JPanel gameInfoPanel = null;
	private JPanel gameCardPanel = null;
	private MouseLivesPanel mouseLivesPanel = null;
	private JLabel robotsCountLabel = null;
	private JLabel levelCountLabel = null;
	private IntroPanel introPanel = null;
	private MazeGridPanel mazePanel = null;
	private CardLayout gameCardPages = null;
	private IntroThread introThread = new IntroThread();
	private GameThread gameThread = new GameThread();
	private RepaintThread repaintThread = null;
	private JPanel preferencesControlPanel = null;
	private GameSettingsPanel gameSettingsPanel = null;
	private ImageManager imageManager = null;

/**
 * MouseMaze2001Layout constructor comment.
 */
public MouseMaze2001() {
	Package pkg = getClass().getPackage();
	String title = pkg.getImplementationTitle();
	if (title == null) {
		title = "Mouse Maze 2001";
	}
	String version = pkg.getImplementationVersion();
	if (version == null) {
		version = "PROTOTYPE";
	}
	setTitle(String.format("%s - %s", title, version));
	
	setResizable(false);
	setSize(500,200);
	centerWindow();

	ImageManager.getInstance();		// toggle image loading
	getContentPane().add(new Component() {
			public void paint(Graphics g) {
				String message = "Please wait, loading images...";
				int screenHeight = getHeight();
				int screenWidth = getWidth();
				Font font = new Font(g.getFont().getFontName(), Font.BOLD, 20);
				g.setFont(font);
				FontMetrics metrics = g.getFontMetrics();
				int fontHeight = metrics.getHeight();
				int stringWidth = metrics.stringWidth(message);
				int x = (screenWidth - stringWidth) / 2;
				int y = (screenHeight - fontHeight) / 2 + fontHeight;
				g.setColor(Color.black);
				g.fillRect(0,0,screenWidth,screenHeight);
				g.setColor(Color.green);
				g.drawString(message, x, y);
			}
		});

	Thread thread = new Thread() {
			public void run() {
				ImageManager imageManager = ImageManager.getInstance();
				while (imageManager.isDoneLoading() == false) {
					try {
						sleep(100);
					} catch (InterruptedException ex) {
					}
				}
				layoutScreen();
			}
		};
	thread.start();
}


/**
 * Invoked when an action occurs.
 */
public void actionPerformed(ActionEvent e) {
	String command = START;	// default if called from elsewhere (yup, a hack)
	MazeDomain domain = MazeDomain.getInstance();
	if (e != null) {
		command = e.getActionCommand();
	}
	if (START.equals(command)) {
		if (introThread.isPaused()) {
			gameThread.suspend();
			introThread.resume();
			gameCardPages.show(gameCardPanel, getPreviewPanelName());
			infoCardPages.show(infoCardPanel, getPreviewPanelName());
			domain.clear();
		} else {
			if (!gameThread.isRunning()) {
				gameThread.start();			// yes, a total hack
				gameThread.suspend();
				domain.addDomainListener(this);
			}
			introThread.suspend();
			gameThread.resume();
			gameCardPages.show(gameCardPanel, getPlayPanelName());
			infoCardPages.show(infoCardPanel, getPlayPanelName());
			mazePanel.requestFocus();
			domain.newGame();
			if (domain.getGameSettings().isAnimatedImages()) {
				AnimationThread animationThread = new AnimationThread();
				animationThread.start();
			}
		}
	} else if (PREFERENCES.equals(command)) {
		infoCardPages.show(infoCardPanel, getPreferencesPanelName());
		gameCardPages.show(gameCardPanel, getPreferencesPanelName());
		gameSettingsPanel.setCurrentSettings(MazeDomain.getInstance().getGameSettings());
	} else if (OK.equals(command)) {
		domain.setGameSettings(gameSettingsPanel.getCurrentSettings());
		infoCardPages.show(infoCardPanel, getPreviewPanelName());
		gameCardPages.show(gameCardPanel, getPreviewPanelName());
	} else if (CANCEL.equals(command)) {
		infoCardPages.show(infoCardPanel, getPreviewPanelName());
		gameCardPages.show(gameCardPanel, getPreviewPanelName());
	}
}


/**
 * Center the current window.
 *
 * Creation date: (10/31/01 10:02:47 PM)
 */
public void centerWindow() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension windowSize = getSize();
	Point point = new Point();
	point.x = (screenSize.width - windowSize.width) / 2;
	point.y = (screenSize.height - windowSize.height) / 2;
	setLocation(point);
}


protected void createControlPanel() {
	JButton startButton = createImageButton("StartButton.gif", START);
	JButton preferencesButton = createImageButton("PreferencesButton.gif", PREFERENCES);
	JPanel topButtons = new JPanel(new GridLayout(2,1));
	topButtons.add(startButton);
	topButtons.add(preferencesButton);

	JButton quitButton = createImageButton("QuitButton.gif", QUIT, new WindowCloseAdapter());
	
	controlPanel = new JPanel(new BorderLayout());
	controlPanel.add(topButtons, BorderLayout.NORTH);
	controlPanel.add(quitButton, BorderLayout.SOUTH);
	controlPanel.setBackground(Color.white);
}


protected void createGameInfoPanel() {
	JLabel aHackForTheDamnFontName = new JLabel();
	String theDamnFontName = aHackForTheDamnFontName.getFont().getName();
	Font font = new Font(theDamnFontName, Font.BOLD, 20);
	
	JLabel miceText = new JLabel("Mice:");
	miceText.setFont(font);
	miceText.setForeground(Color.blue);
	JLabel levelText = new JLabel("Level:");
	levelText.setFont(font);
	levelText.setForeground(Color.blue);
	JLabel robotsText = new JLabel("Robots:");
	robotsText.setFont(font);
	robotsText.setForeground(Color.blue);

	mouseLivesPanel = new MouseLivesPanel();
	mouseLivesPanel.setBackground(Color.white);
	levelCountLabel = new JLabel("0");
	levelCountLabel.setFont(font);
	levelCountLabel.setForeground(Color.blue);
	robotsCountLabel = new JLabel("0");
	robotsCountLabel.setFont(font);
	robotsCountLabel.setForeground(Color.blue);
	
	gameInfoPanel = new JPanel(new GridLayout(3,2));
	gameInfoPanel.add(miceText);
	gameInfoPanel.add(mouseLivesPanel);
	gameInfoPanel.add(levelText);
	gameInfoPanel.add(levelCountLabel);
	gameInfoPanel.add(robotsText);
	gameInfoPanel.add(robotsCountLabel);
	gameInfoPanel.setBackground(Color.white);
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
	JButton button = new JButton(new ImageIcon(imageManager.getImage(resourceName)));
	button.setActionCommand(commandString);
	button.setBackground(Color.white);
	button.addActionListener(actionListener);
	return button;
}


protected void createInfoPanel() {
	createLogoPanel();
	createControlPanel();
	createGameInfoPanel();
	createPreferencesControlPanel();

	infoCardPages = new CardLayout();
	infoCardPanel = new JPanel(infoCardPages);
	infoCardPanel.add(getPreviewPanelName(), controlPanel);
	infoCardPanel.add(getPlayPanelName(), gameInfoPanel);
	infoCardPanel.add(getPreferencesPanelName(), preferencesControlPanel);

	JPanel nestedPanel = new JPanel(new BorderLayout());
	nestedPanel.setBackground(Color.white);
	nestedPanel.add(logoPanel, BorderLayout.NORTH);
	nestedPanel.add(infoCardPanel, BorderLayout.CENTER);

	// apparantly these need to be separate panels...
	JPanel dividerPanel = new JPanel();
	dividerPanel.setBackground(Color.white);
	JPanel dividerPanel2 = new JPanel();
	dividerPanel2.setBackground(Color.white);
	JPanel dividerPanel3 = new JPanel();
	dividerPanel3.setBackground(Color.white);
	JPanel dividerPanel4 = new JPanel();
	dividerPanel4.setBackground(Color.white);
	
	Dimension size = new Dimension(logoPanel.getWidth(), -1);
	infoPanel = new JPanel(new BorderLayout());
	infoPanel.setMaximumSize(size);
	infoPanel.add(dividerPanel, BorderLayout.WEST);
	infoPanel.add(dividerPanel2, BorderLayout.EAST);
	infoPanel.add(dividerPanel3, BorderLayout.SOUTH);
	infoPanel.add(dividerPanel4, BorderLayout.NORTH);
	infoPanel.add(nestedPanel, BorderLayout.CENTER);
}


protected void createLogoPanel() {
	logoPanel = new JPanel(new FlowLayout());
	ImageCanvas logo = new ImageCanvas("MouseMazeLogo.gif");
	logo.setColor(Color.white);
	logoPanel.add(logo);
	logoPanel.setBackground(Color.white);
}


protected void createPreferencesControlPanel() {
	JButton okButton = createImageButton("OkButton.gif", OK);
	JButton cancelButton = createImageButton("CancelButton.gif", CANCEL);
	
	preferencesControlPanel = new JPanel(new BorderLayout());
	preferencesControlPanel.add(okButton, BorderLayout.NORTH);
	preferencesControlPanel.add(cancelButton, BorderLayout.SOUTH);
	preferencesControlPanel.setBackground(Color.white);
}


/**
 * Act upon domain changed events.
 *
 * Creation date: (10/22/01 10:06:09 PM)
 */
public void domainChanged(DomainEvent domainEvent) {
	String event = domainEvent.getEvent();
	MazeDomain domain = MazeDomain.getInstance();
	if ("terminate".equals(event)) {
		actionPerformed(null);	// cheap game over
	}
	if ("robotShot".equals(event) || "newGame".equals(event)) {
		robotsCountLabel.setText(Integer.toString(domain.getKills()));
		robotsCountLabel.repaint();
	}
	if ("mouseKilled".equals(event)) {
		mouseLivesPanel.repaint();
		GameDelayThread thread = new GameDelayThread("Ouch!",500);
		thread.start();
	}
	if ("level".equals(event)) {
		//GameDelayThread thread = new GameDelayThread("Welcome to level " + domain.getLevel() + "!",1000);
		//thread.start();
		levelCountLabel.setText(Integer.toString(domain.getLevel()));
		levelCountLabel.repaint();
	}

	if ("newGame".equals(event)) {
		GameDelayThread thread = new GameDelayThread("Good Luck!",500);
		thread.start();
		levelCountLabel.setText(Integer.toString(domain.getLevel()));
		levelCountLabel.repaint();
		mouseLivesPanel.repaint();
	}
	if ("gameOver".equals(event)) {
		GameDelayThread thread = new GameDelayThread("Game Over",2000);
		thread.start();
		mouseLivesPanel.repaint();
	} else if ("gameWon".equals(event)) {
		GameDelayThread thread = new GameDelayThread("You have won Mouse Maze!",3000);
		thread.start();
	}
	repaintNeeded();
}


public static MouseMaze2001 getInstance() {
	return instance;
}


protected String getPlayPanelName() {
	return "play";
}


protected String getPreferencesPanelName() {
	return "preferences";
}


protected String getPreviewPanelName() {
	return "preview";
}


/**
 * Invoked when a key has been pressed.
 */
public void keyPressed(KeyEvent e) {
	switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			MazeDomain.getInstance().moveMouse(-1,0);
			repaintNeeded();
			break;
		case KeyEvent.VK_RIGHT:
			MazeDomain.getInstance().moveMouse(1,0);
			repaintNeeded();
			break;
		case KeyEvent.VK_UP:
			MazeDomain.getInstance().moveMouse(0,-1);
			repaintNeeded();
			break;
		case KeyEvent.VK_DOWN:
			MazeDomain.getInstance().moveMouse(0,1);
			repaintNeeded();
			break;
		case KeyEvent.VK_HOME:
			MazeDomain.getInstance().moveMouse(-1,-1);
			repaintNeeded();
			break;
		case KeyEvent.VK_PAGE_UP:
			MazeDomain.getInstance().moveMouse(1,-1);
			repaintNeeded();
			break;
		case KeyEvent.VK_END:
			MazeDomain.getInstance().moveMouse(-1,1);
			repaintNeeded();
			break;
		case KeyEvent.VK_PAGE_DOWN:
			MazeDomain.getInstance().moveMouse(1,1);
			repaintNeeded();
			break;
	}

}


/**
 * Invoked when a key has been released.
 */
public void keyReleased(KeyEvent e) {
	switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			MazeDomain.getInstance().endGame();
			break;
	}
}


/**
 * Invoked when a key has been typed.
 * This event occurs when a key press is followed by a key release.
 */
public void keyTyped(KeyEvent e) {
	char ch = Character.toUpperCase(e.getKeyChar());
	MazeDomain domain = MazeDomain.getInstance();
	int dx = 0;
	int dy = 0;
	if (domain.canMouseShoot()) {
		switch (ch) {
			case 'Q':	dx = -1;
						dy = -1;
						break;
			case 'W':	dx = 0;
						dy = -1;
						break;
			case 'E':	dx = 1;
						dy = -1;
						break;
			case 'A':	dx = -1;
						dy = 0;
						break;
			case 'D':	dx = 1;
						dy = 0;
						break;
			case 'Z':	dx = -1;
						dy = 1;
						break;
			case 'X':	dx = 0;
						dy = 1;
						break;
			case 'C':	dx = 1;
						dy = 1;
						break;
		}
	}
	switch (ch) {
		case 'P':	if (domain.isPaused()) {
						domain.setPause(null);
					} else {
						domain.setPause("Game is paused");
					}
					repaintNeeded();
					break;
	}
	if (dx != 0 || dy != 0) {
		Point pt = new Point(domain.getMouseLocation());
		pt.translate(dx,dy);
		if (domain.isValidPoint(pt)) {
			Thread explosion = new ExplosionThread(pt);
			explosion.start();
		}
	}
}


/**
 * Layout the actual MouseMaze screen.
 *
 * Creation date: (10/31/01 9:38:13 PM)
 */
protected void layoutScreen() {
	setVisible(false);
	MazeDomain domain = MazeDomain.getInstance();
	imageManager = ImageManager.getInstance();
	
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	addWindowListener(new WindowCloseAdapter());

	createInfoPanel();

	introPanel = IntroPanel.getInstance();
	mazePanel = new MazeGridPanel();
	gameSettingsPanel = new GameSettingsPanel();
	gameCardPages = new CardLayout();
	gameCardPanel = new JPanel(gameCardPages);
	gameCardPanel.add(getPreviewPanelName(), introPanel);
	gameCardPanel.add(getPlayPanelName(), mazePanel);
	gameCardPanel.add(getPreferencesPanelName(), gameSettingsPanel);

	getContentPane().removeAll();
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(BorderLayout.CENTER, gameCardPanel);
	getContentPane().add(BorderLayout.EAST, infoPanel);
	pack();
	centerWindow();
	setVisible(true);

	mazePanel.addKeyListener(this);

	introThread.start();

	repaintThread = new RepaintThread(mazePanel);
	repaintThread.start();
}


/**
 * Start the program
 * 
 * Creation date: (10/9/01 10:04:11 PM)
 * @param args java.lang.String[]
 */
public static void main(String[] args) {
	instance.show();
}


/**
 * Tell the RepaintThread that a repaint is needed.
 *
 * Creation date: (10/24/01 9:20:23 PM)
 */
public void repaintNeeded() {
	//System.out.println(new java.util.Date() + " - repaint requested");
	repaintThread.repaintNeeded();
}
}