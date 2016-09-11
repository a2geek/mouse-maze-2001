package a2geek.games.mousemaze2001.mazeobjects;
import java.net.*;

import a2geek.games.mousemaze2001.images.ImageManager;

import java.io.*;
import java.awt.image.*;
import java.awt.*;

/**
 * Represents a MazeObject which is Image based.  (Basically, a 'tile'.)
 * 
 * Creation date: (10/23/01 9:31:27 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/31/2001 22:12:32 
 */
public abstract class MazeImageObject extends MazeObject {
	private Image image = null;

/**
 * MazeImageObject constructor comment.
 */
public MazeImageObject() {
	super();
	image = ImageManager.getInstance().getImage(getImageName());
}


/**
 * Return the height of the image.
 *
 * Creation date: (10/27/01 12:47:15 AM)
 */
public int getHeight() {
	return image.getHeight(null);
}


/**
 * Answers with the image name of this object.
 *
 * Creation date: (10/14/01 9:41:15 PM)
 */
protected abstract String getImageName();


/**
 * Return the width of the image.
 *
 * Creation date: (10/27/01 12:47:15 AM)
 */
public int getWidth() {
	return image.getWidth(null);
}


/**
 * Draw image on screen.
 * It is expected that the region to be drawn has been clipped.
 * See Graphics.create for more details.
 *
 * Creation date: (10/14/01 9:45:16 PM)
 */
public void paint(Graphics g) {
	if (image != null) {
		Rectangle rect = g.getClipBounds();
		int x = (rect.width - image.getWidth(null)) / 2;
		int y = (rect.height - image.getHeight(null)) / 2;
		g.drawImage(image, x, x, null);
	}
}
}