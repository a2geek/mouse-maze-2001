package a2geek.games.mousemaze2001.domain;
/**
 * A DomainEvent is passed to DomainListeners.
 * 
 * Creation date: (10/22/01 9:58:14 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/22/2001 23:31:31 
 */
public class DomainEvent {
	private Object source;
	private String event;

/**
 * DomainEvent constructor comment.
 */
public DomainEvent(Object theSource, String theEvent) {
	super();
	source = theSource;
	event = theEvent;
}


/**
 * Retrieve the event that occurred.
 *
 * Creation date: (10/22/01 10:00:08 PM)
 */
public String getEvent() {
	return event;
}


/**
 * Retrieve the source of this DomainEvent.
 *
 * Creation date: (10/22/01 9:59:45 PM)
 */
public Object getSource() {
	return source;
}
}