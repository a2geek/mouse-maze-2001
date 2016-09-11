package a2geek.games.mousemaze2001;
import java.awt.*;
import javax.swing.*;

import a2geek.games.mousemaze2001.domain.*;
import a2geek.games.mousemaze2001.mazeobjects.*;

/**
 * Display the number of lives graphically.
 * 
 * Creation date: (10/27/01 12:42:11 AM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/27/2001 01:02:48 
 */
public class MouseLivesPanel extends JPanel {
	InvertedMouse mouse = new InvertedMouse();

/**
 * MouseLivesPanel constructor comment.
 */
public MouseLivesPanel() {
	super();
}


/**
 * Display the number of mouse lives.
 *
 * Creation date: (10/27/01 12:43:13 AM)
 */
public void paint(Graphics g) {
	super.paint(g);
	Rectangle rect = g.getClipBounds();
	int lives = MazeDomain.getInstance().getLives();
	for (int i=0; i<lives; i++) {
		int x = mouse.getWidth() * i;
		int y = ((int)rect.getHeight() - mouse.getHeight()) / 2;
		Graphics mg = g.create(x,y,mouse.getWidth(),mouse.getHeight());
		mouse.paint(mg);
	}
}
}