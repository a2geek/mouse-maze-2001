package a2geek.games.mousemaze2001.threads;
/**
 * A ControlledThread is a pseudo-Thread which allows thread-safe suspending/resuming and
 * stoppping/restarting. Technically, this class implements runnable. All methods, except
 * process are marked as final because it is assumed that you do not want to mess around
 * with the threading.
 * <p>
 * To implement a pausable thread, extend the ControlledThread and implement process.
 * Don't forget to set a default delay in your constructor, if appropriate!
 * <p>
 * Creation date: (10/20/01 9:08:13 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/29/2001 22:40:54 
 */
public abstract class ControlledThread implements Runnable {
	private boolean running = false;
	private boolean paused = false;
	private Thread thread;
	private long delay;

/**
 * ControlledThread constructor.
 */
public ControlledThread() {
	super();
	thread = new Thread(this, getThreadName());
	thread.setDaemon(true);
}


/**
 * Retrieve the delay that the thread will use.
 * <p>
 * Creation date: (10/20/01 9:42:01 PM)
 * @return long
 */
public final long getDelay() {
	return delay;
}


/**
 * Returns the name of this thread.  By default, it will be the class name.
 *
 * Creation date: (10/21/01 2:24:36 PM)
 */
protected String getThreadName() {
	String className = getClass().getName();
	int pos = className.lastIndexOf(".") + 1;
	if (pos > -1) return className.substring(pos);
	else return className;
}


/**
 * Returns true if the thread is paused.
 * Note that this does not indicate if the thread is actually running.
 * <p>
 * Creation date: (10/20/01 10:20:37 PM)
 */
public final boolean isPaused() {
	return paused;
}


/**
 * Returns true if this thread is actively running.
 * Note that this does not indicate if the thread has been paused.
 * <p>
 * Creation date: (10/20/01 10:21:07 PM)
 */
public final boolean isRunning() {
	return running;
}


/**
 * Perform the threadded process.
 * <p>
 * Creation date: (10/20/01 10:21:40 PM)
 */
protected abstract void process();


/**
 * Resume a paused thread.
 * Note that the thread must be in a running state.
 * <p>
 * Creation date: (10/20/01 10:22:13 PM)
 */
public synchronized final void resume() {
	paused = false;
	notifyAll();
}


/**
 * This is the thread control loop.
 * To customize, implement the process method.
 * <p>
 * Creation date: (10/20/01 10:22:32 PM)
 */
public final void run() {
	while (isRunning()) {
		try {
			process();		// <-- customize here!!
			synchronized(this) {
				while (isRunning() && isPaused()) {
					wait();
				}
			}
			thread.sleep(getDelay());
		} catch (Exception ex) {
			System.out.println("Exception in " + getThreadName());
			ex.printStackTrace(System.out);
		}
	}
	paused = false;
	running = false;
}


/**
 * Set the delay used in the control thread.
 * <p>
 * Creation date: (10/20/01 9:42:01 PM)
 * @param newDelay long
 */
public final void setDelay(long newDelay) {
	delay = newDelay;
}


/**
 * Start the thread.
 * <p>
 * Creation date: (10/20/01 10:24:37 PM)
 */
public final void start() {
	if (isRunning()) {
		resume();
	} else {
		running = true;
		paused = false;
		thread.start();
	}
}


/**
 * Stop the thread.
 * <p>
 * Creation date: (10/20/01 10:24:46 PM)
 */
public synchronized final void stop() {
	running = false;
	notifyAll();
}


/**
 * Suspend the thread.
 * <p>
 * Creation date: (10/20/01 10:24:58 PM)
 */
public final void suspend() {
	paused = true;
}
}