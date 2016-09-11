package a2geek.games.mousemaze2001.images;
import java.awt.image.ImageProducer;
import java.net.URL;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.*;
import java.io.*;

/**
 * Provide Image management for a system.
 * This brings two benefits: All images are centrally located within this class and
 * do not go away with a discarded class.  The Image manager can be configured to preload
 * images in a threaded background process.
 * All images are keyed by filename.
 * 
 * Creation date: (10/31/01 7:30:21 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/31/2001 22:12:32 
 */
public class ImageManager {
	private static final String BASE_PATH = "/images/%s";
	private static ImageManager instance = null;
	private Hashtable images = new Hashtable();

/**
 * ImageManager constructor comment.
 */
protected ImageManager() {
	super();
}


/**
 * Retrieve an image from the images table.
 * If the image has not been loaded, it is loaded.
 *
 * Creation date: (10/31/01 8:51:13 PM)
 */
public Image getImage(String imageName) {
	Image image = (Image) images.get(imageName);
	if (image == null) {
		image = loadImage(imageName);
	}
	return image;
}


/**
 * Retrieve the singleton instance of this class and
 * initialize the class.
 *
 * Creation date: (10/31/01 7:34:49 PM)
 */
public static synchronized ImageManager getInstance() {
	if (instance == null) {
		instance = new ImageManager();
		instance.initialize();
	}
	return instance;
}


/**
 * Initialize the class by preloading images.
 *
 * Creation date: (10/31/01 7:36:07 PM)
 */
protected void initialize() {
	Properties properties = new Properties();
	String resourceName = String.format(BASE_PATH, "ImageManager.properties");
	InputStream inputStream = getClass().getResourceAsStream(resourceName);
	if (inputStream != null) {
		try {
			properties.load(inputStream);
			Enumeration elements = properties.keys();
			while (elements.hasMoreElements()) {
				String imageName = (String) elements.nextElement();
				//if (imageName.startsWith("#") == false && imageName.length() > 0) {
					loadImage(imageName);
				//}
			}
		} catch (IOException ex) {
			log("initialize", "Unable to load " + resourceName, ex);
		}
	}
}


/**
 * Test if the requested images have completed loading.
 *
 * Creation date: (10/31/01 8:52:50 PM)
 */
public boolean isDoneLoading() {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Enumeration elements = images.elements();
	while (elements.hasMoreElements()) {
		Image image = (Image) elements.nextElement();
		if (image.getHeight(null) == -1 || image.getWidth(null) == -1) {
			return false;
		}
	}
	return true;
}


/**
 * Load an image into the images table.
 *
 * Creation date: (10/31/01 7:36:40 PM)
 */
protected Image loadImage(String imageName) {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	URL url = getClass().getResource(String.format(BASE_PATH,imageName));
	Image image = null;
	try {
		image = toolkit.createImage((ImageProducer) url.getContent());
		images.put(imageName, image);
	} catch (IOException ex) {
		log("loadImage", "Unable to load " + imageName, ex);
	}
	return image;
}


/**
 * Log errors.
 *
 * Creation date: (10/31/01 9:07:18 PM)
 */
protected void log(String methodName, String description, Exception exception) {
	System.out.println(new Date() + " ImageManager." + methodName + ":");
	System.out.println("    " + description);
	exception.printStackTrace(System.out);
}
}