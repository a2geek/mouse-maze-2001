package a2geek.games.mousemaze2001;

import java.awt.event.*;
import javax.swing.event.*;

/**
 * Generic adapter which shuts down the application.
 * <p>
 * Creation date: (10/6/01 11:29:16 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/08/2001 00:10:56 
 */
public class WindowCloseAdapter extends WindowAdapter implements ActionListener {
	/**
	 * WindowCloseAdapter constructor comment.
	 */
	public WindowCloseAdapter() {
		super();
	}

	/**
	 * Perform the close action.
	 *
	 * Creation date: (10/6/01 11:32:38 PM)
	 */
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * Close the system down.
	 *
	 * Creation date: (10/6/01 11:29:36 PM)
	 */
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
}