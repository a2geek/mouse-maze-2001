package a2geek.games.mousemaze2001.threads;
import java.awt.Component;

/**
 * A RepaintThread manages repainting of the screen.  This gives all threads in a program
 * the ability to repaint the screen without having multiple threads repainting at the same
 * time.  In addition, the RepaintThread will pause for a short period of time after a
 * screen has been repainted to ensure that the CPU is not over-taxed.
 * 
 * Creation date: (10/24/01 9:09:07 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/31/2001 22:12:32 
 */
public class RepaintThread implements Runnable {
	private Component component;
	private Thread thread;
	private int delay;
	private boolean repaintNeeded;

/**
 * RepaintThread constructor.
 */
public RepaintThread(Component theComponent) {
	super();
	thread = new Thread(this, "RepaintThread");
	thread.setDaemon(true);
	component = theComponent;
	delay = 50;
	thread.setPriority(thread.getPriority()-1);
}


/**
 * Retrieve the current delay used by the RepaintThread.
 * 
 * Creation date: (10/24/01 9:12:16 PM)
 * @return int
 */
public int getDelay() {
	return delay;
}


/**
 * Tell the RepaintThread that a repaint needs to occur.
 *
 * Creation date: (10/24/01 9:19:23 PM)
 */
public synchronized void repaintNeeded() {
	//System.out.println(new java.util.Date() + " + repaint received");
	repaintNeeded = true;
	notifyAll();
}


/**
 * Control the RepaintThread.
 *
 * Creation date: (10/24/01 9:15:14 PM)
 */
public void run() {
	while (true) {
		try {
			if (repaintNeeded) {
				repaintNeeded = false;
				component.repaint();
			}
			thread.sleep(getDelay());
			synchronized(this) {
				wait();
			}
		} catch (Exception ex) {
			break;
		}
	}
}


/**
 * Set the delay to be used by the RepaintThread.
 * 
 * Creation date: (10/24/01 9:12:16 PM)
 * @param newDelay int
 */
public void setDelay(int newDelay) {
	delay = newDelay;
}


/**
 * Start the thread.
 * <p>
 * Creation date: (10/20/01 10:24:37 PM)
 */
public void start() {
	thread.start();
}
}