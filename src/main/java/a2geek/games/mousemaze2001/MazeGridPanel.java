package a2geek.games.mousemaze2001;
import java.awt.*;
import javax.swing.*;

import a2geek.games.mousemaze2001.domain.*;
import a2geek.games.mousemaze2001.mazeobjects.*;

/**
 * Insert the type's description here.
 * 
 * Creation date: (10/11/01 10:13:35 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/31/2001 22:12:32 
 */
public class MazeGridPanel extends JPanel {
	private int gridWidth = 14;
	private int gridHeight = 9;
	// the following keep an aspect ratio of 1.33333:1 for w:h
	private int minCellHeight = 16;
	private int minCellWidth = 15;
	private int prefCellWidth = 32;
	private int prefCellHeight = 30;
	private int maxCellWidth = 64;
	private int maxCellHeight = 60;

/**
 * MazeGridPanel constructor comment.
 */
public MazeGridPanel() {
	super();
	initialize();
}


/**
 * MazeGridPanel constructor comment.
 * @param layout java.awt.LayoutManager
 */
public MazeGridPanel(java.awt.LayoutManager layout) {
	super(layout);
	initialize();
}


/**
 * MazeGridPanel constructor comment.
 * @param layout java.awt.LayoutManager
 * @param isDoubleBuffered boolean
 */
public MazeGridPanel(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
	super(layout, isDoubleBuffered);
	initialize();
}


/**
 * MazeGridPanel constructor comment.
 * @param isDoubleBuffered boolean
 */
public MazeGridPanel(boolean isDoubleBuffered) {
	super(isDoubleBuffered);
	initialize();
}


protected void initialize() {
	setMinimumSize(new Dimension(gridWidth * minCellWidth, gridHeight * minCellHeight));
	setMaximumSize(new Dimension(gridWidth * maxCellWidth, gridHeight * maxCellHeight));
	setPreferredSize(new Dimension(gridWidth * prefCellWidth, gridHeight * prefCellHeight));
}


public void paint(Graphics g) {
	//System.out.println(new java.util.Date() + " - painting screen");
	int screenWidth = getWidth();
	int screenHeight = getHeight();
	g.setColor(Color.black);
	g.fillRect(0, 0, screenWidth, screenHeight);
	g.setColor(Color.white);
	screenWidth -= 2;
	screenHeight -= 2;
	for (int x=0; x <= gridWidth; x++) {
		int xPos = (screenWidth * x) / gridWidth;
		g.drawLine(xPos, 0, xPos, screenHeight);
		xPos++;
		g.drawLine(xPos, 0, xPos, screenHeight);
	}
	for (int y=0; y <= gridHeight; y++) {
		int yPos = (screenHeight * y) / gridHeight;
		g.drawLine(0, yPos, screenWidth, yPos);
		yPos++;
		g.drawLine(0, yPos, screenWidth, yPos);
	}

	MazeDomain maze = MazeDomain.getInstance();
	if (maze.getMap() == null) return;
	
	int cellWidth = screenWidth / gridWidth - 2;
	int cellHeight = screenHeight / gridHeight - 2;
	for (int x=0; x<maze.getMapWidth(); x++) {
		int x0 = (screenWidth * x) / gridWidth + 2;
		for (int y=0; y<maze.getMapHeight(); y++) {
			int y0 = (screenHeight * y) / gridHeight + 2;
			MazeObject object = maze.getMazeObject(x,y);
			if (object != null) {
				Graphics cell = g.create(x0,y0,cellWidth+1,cellHeight+1);
				if (object instanceof AnimatedRobot) {
					AnimatedRobot robot = (AnimatedRobot) object;
					if (robot.isAlive() == false) continue;
				}
				object.paint(cell);
			}
		}
	}

	String message = maze.getPauseMessage();
	if (maze.isPaused()) {
		//System.out.println(new java.util.Date() + " - showing message = " + message);
		Font font = new Font(g.getFont().getName(), Font.BOLD, 20);
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics();
		int fontHeight = metrics.getAscent();
		int stringWidth = metrics.stringWidth(message);
		int xText = (screenWidth - stringWidth) / 2;
		int yText = (screenHeight - fontHeight) / 2;
		int xBox = xText - 15;
		int yBox = yText - 15;
		int xWidth = stringWidth + 30;
		int yHeight = fontHeight + 30;
		g.setColor(Color.blue);
		g.fillRoundRect(xBox,yBox,xWidth,yHeight,15,15);
		g.setColor(Color.white);
		g.drawRoundRect(xBox,yBox,xWidth,yHeight,15,15);
		g.setColor(Color.white);
		g.drawString(message, xText, yText + fontHeight);
	}
}
}