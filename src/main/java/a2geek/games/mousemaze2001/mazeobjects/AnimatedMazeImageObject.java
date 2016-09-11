package a2geek.games.mousemaze2001.mazeobjects;
import java.net.*;

import a2geek.games.mousemaze2001.images.ImageManager;

import java.io.*;
import java.awt.image.*;
import java.awt.*;

/**
 * Represents a MazeObject which is Image based and is animated.  (Basically, a 'tile'.)
 * 
 * Creation date: (10/23/01 9:31:27 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/31/2001 22:12:32 
 */
public abstract class AnimatedMazeImageObject extends MazeObject {
	private Image[] images = null;

/**
 * MazeImageObject constructor comment.
 */
public AnimatedMazeImageObject() {
	super();
	ImageManager imageManager = ImageManager.getInstance();
	String[] names = getImageNames();
	images = new Image[names.length];
	for (int i=0; i<names.length; i++) {
		images[i] = imageManager.getImage(names[i]);
	}
}


/**
 * Provides the animation sequence.
 * This just needs to be an integer - not necessarily in the valid range, as
 * modulo arithmetic will be used.
 *
 * Creation date: (10/26/01 10:55:18 PM)
 */
protected abstract int getAnimationSequence();


/**
 * Answers with the image names for this object.
 *
 * Creation date: (10/14/01 9:41:15 PM)
 */
protected abstract String[] getImageNames();


/**
 * Draw image on screen.
 * It is expected that the region to be drawn has been clipped.
 * See Graphics.create for more details.
 *
 * Creation date: (10/14/01 9:45:16 PM)
 */
public void paint(Graphics g) {
	if (images == null) return;
	int sequence = getAnimationSequence() % images.length;
	Image image = images[sequence];
	if (image != null) {
		Rectangle rect = g.getClipBounds();
		int x = (rect.width - image.getWidth(null)) / 2;
		int y = (rect.height - image.getHeight(null)) / 2;
		g.drawImage(image, x, y, null);
	}
}
}