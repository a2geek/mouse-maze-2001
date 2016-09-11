package a2geek.games.mousemaze2001;
import java.io.*;
import java.awt.image.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;

import a2geek.games.mousemaze2001.images.*;

/**
 * Insert the type's description here.
 * 
 * Creation date: (10/16/01 10:28:58 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/31/2001 22:12:32 
 */
public class IntroPanel extends JPanel {
	private static IntroPanel instance = new IntroPanel();
	private String[] imageNames = {
			"OriginalMouseMazeLogo.gif",
			"OriginalMouseMazeHelp.gif",
			"OriginalMouseMazeGameShot.gif",
			"OriginalMouseMazeWin.gif"
		};
	private Image[] scaledImages;
	private int ticker;
	private int stage;
	private String[] story = {
			"A long time ago,",
			"when life was much different,",
			"I wrote my first game called...",
			"",
			"&1",
			"",
			"",
			"This a rewrite.",
			"",
			"",
			"The Apple ][,",
			"simple graphics,",
			"small computers,",
			"All Gone!",
			"",
			"",
			"The original help screen:",
			"&2",
			"",
			"A game in progress:",
			"&3",
			"",
			"The winners' screen:",
			"&4"
		};
	private Image marquee;
	private Thread creationThread;

/**
 * IntroPanel constructor comment.
 */
protected IntroPanel() {
	super();
	initialize();
}


public static IntroPanel getInstance() {
	return instance;
}


protected int getMaxStage() {
	return 2;
}


public void incrementTicker() {
	ticker++;
}


private void initialize() {
	setMinimumSize(new Dimension(560,384));
	setMaximumSize(new Dimension(560*2,384*2));
	setPreferredSize(new Dimension(560,384));

	// load original images
	Image originalImages[] = new Image[imageNames.length];
	ImageManager imageManager = ImageManager.getInstance();
	for (int i=0; i<imageNames.length; i++) {
		originalImages[i] = imageManager.getImage(imageNames[i]);
	}
	
	// create scaled images (except #0)
	MediaTracker tracker = new MediaTracker(this);
	scaledImages = new Image[originalImages.length];
	for (int i=0; i<originalImages.length; i++) {
		Image scaled = originalImages[i];
		int width = scaled.getWidth(null) / 2;
		if (i > 0) {
			scaled = scaled.getScaledInstance(width, -1, Image.SCALE_FAST);
		}
		tracker.addImage(scaled, i);
		scaledImages[i] = scaled;
	}
	try {
		tracker.waitForAll();
	} catch (InterruptedException ex) {
	}
}


/**
 * This method will layout the marquee on the given grapics context - which can be
 * the screen as well as an off-screen image.
 *
 * Creation date: (10/19/01 11:16:17 PM)
 */
protected int layoutMarquee(Image layoutImage) {
	Graphics g = layoutImage.getGraphics();
	Font font = new Font(g.getFont().getFontName(), Font.BOLD, 20);
	g.setFont(font);
	FontMetrics metrics = g.getFontMetrics();
	int fontHeight = metrics.getHeight();
	int lines = story.length;

	int screenHeight = layoutImage.getHeight(null);
	int screenWidth = layoutImage.getWidth(null);
	g.setColor(Color.black);
	g.fillRect(0,0,screenWidth,screenHeight);

	int y = fontHeight + 10;	// just a buffer
	for (int i=0; i<lines; i++) {
		String line = story[i];
		if (line.startsWith("&")) {
			int shape = Integer.parseInt(line.substring(1))-1;
			if (shape >= 0 && shape < scaledImages.length) {
				Image image = scaledImages[shape];
				int shapeWidth = image.getWidth(null);
				int shapeHeight = image.getHeight(null);
				int x = (screenWidth - shapeWidth) / 2;
				int yPos = y - fontHeight+10;
				g.setColor(Color.blue);
				g.fillRoundRect(x-5,yPos-5,shapeWidth+10,shapeHeight+10,5,5);
				g.drawImage(image,x,yPos,null);
				y+= shapeHeight+10;
			}
		} else {
			int stringWidth = metrics.stringWidth(line);
			int x = (screenWidth - stringWidth) / 2;
			g.setColor(Color.lightGray);
			g.drawString(line, x, y);
			y+= fontHeight;
		}
	}
	return y + 10;
}


private void nextStage() {
	stage++;
	if (stage >= getMaxStage()) {
		stage = 0;
	}
	resetTicker();
}


public void paint(Graphics g) {
	Font oldFont = g.getFont();

	int screenHeight = getHeight();
	int screenWidth = getWidth();
	g.setColor(Color.black);
	g.fillRect(0,0,screenWidth,screenHeight);

	switch (stage) {
		case 0:	paintMarquee(g);
				break;
		case 1:	paintSectionTwo(g);
				break;
	}

	g.setFont(oldFont);		// not sure if this needs to be done..
}


/**
 * Paint the scrolling marquee.
 * The marquee image creation is placed here since the initialize method apparantly
 * cannot create a Graphics object from an Image; thus the marquee cannot be rendered
 * from the constructor. The marquee image itself is laid out twice - once to get the
 * height (allows it to remain somewhat dynamic) and then to generate the real image.
 * The rest of the logic here is just to draw the image on the physical screen.
 *
 * Creation date: (10/19/01 11:13:58 PM)
 */
public void paintMarquee(Graphics g) {
	if (marquee == null) {
		if (creationThread == null) {
			creationThread = new Thread() {
					public void run() {
						int width = 500;
						int height = 800;
						Image layoutImage = createImage(width,height);
						height = layoutMarquee(layoutImage);
						Image marqueeTemp = createImage(width, height);
						layoutMarquee(marqueeTemp);
						marquee = marqueeTemp;
						resetTicker();
					};
				};
			creationThread.setDaemon(true);
			creationThread.start();
		}
	}

	int screenHeight = getHeight();
	int screenWidth = getWidth();
	if (marquee == null) {
		String message = "Please wait...";
		Font font = new Font(g.getFont().getFontName(), Font.BOLD, 20);
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics();
		int fontHeight = metrics.getHeight();
		int stringWidth = metrics.stringWidth(message);
		int x = (screenWidth - stringWidth) / 2;
		int y = (screenHeight - fontHeight) / 2;
		g.setColor(Color.green);
		g.drawString(message, x, y);
	} else {
		int y = screenHeight - ticker;;
		int shapeWidth = marquee.getWidth(null);
		int shapeHeight = marquee.getHeight(null);
		int x = (screenWidth - shapeWidth) / 2;
		g.drawImage(marquee,x,y,null);
		y+= shapeHeight;
		if (y < 0) {
			nextStage();
		}
	}
}


public void paintSectionTwo(Graphics g) {
	Font font = new Font(g.getFont().getFontName(), Font.BOLD, 20);
	g.setFont(font);
	g.setColor(Color.blue);
	FontMetrics metrics = g.getFontMetrics();
	int fontHeight = metrics.getHeight();
	int fontAscent = metrics.getAscent();
	int screenHeight = getHeight();
	int screenWidth = getWidth();
	int y = (screenHeight - fontHeight*8) / 2;

	String title = "Controlling Mouse Maze";
	int stringWidth = metrics.stringWidth(title);
	g.drawString(title, (screenWidth - stringWidth) / 2, y);

	int saveY = y;
	int x;
	String[] keys;
	for (int z=0; z<2; z++) {
		if (z == 0) {
			y = saveY;
			x = (screenWidth / 4);
			title = "To Shoot:";
			keys = new String[] { "Q", "W", "E", "A", null, "D", "Z", "X", "C" };
		} else {
			y = saveY;
			x= (screenWidth / 2);
			title = "To Move:";
			keys = new String[] { "Home", "Up", "PgUp", "Left", null, "Right", "End", "Down", "PgDn" };
		}
		g.setColor(Color.blue);
		y+= fontHeight * 2;
		g.drawString(title, x, y);
		y+= fontHeight;
		int maxWidth = 0;
		for (int i=0; i<keys.length; i++) {
			String key = keys[i];
			if (key != null) {
				int width = metrics.stringWidth(key);
				if (width > maxWidth) maxWidth = width;
			}
		}
		int padding = 6;
		for (int i=0; i<keys.length; i++) {
			String key = keys[i];
			if (key != null) {
				int x0 = x + (i%3)*(maxWidth + padding*2);
				g.setColor(new Color(0xafafaf));
				g.fillRoundRect(x0-padding,y-padding,maxWidth+padding,fontHeight+padding,padding*2,padding*2);
				g.setColor(Color.white);
				g.drawRoundRect(x0-padding,y-padding,maxWidth+padding,fontHeight+padding,padding*2,padding*2);
				g.setColor(Color.black);
				int width = metrics.stringWidth(key);
				x0 += (maxWidth - width) / 2;
				g.drawString(key, x0-(padding/2), y+fontAscent-(padding/2));
				if (i%3 == 2) {
					y+= fontHeight + padding*2;
				}
			}
		}
	}

	y+= fontHeight;
	g.setColor(Color.blue);
	title = "Press Escape to end game";
	stringWidth = metrics.stringWidth(title);
	g.drawString(title, (screenWidth - stringWidth) / 2, y);
	y+= fontHeight;
	title = "Press 'P' to pause the game";
	stringWidth = metrics.stringWidth(title);
	g.drawString(title, (screenWidth - stringWidth) / 2, y);
	
	if (ticker > 1000) {	
		nextStage();
	}
}


public void resetTicker() {
	ticker= 0;
}
}