package a2geek.games.mousemaze2001.domain;
/**
 * A DomainListener identifies an object that wants to know that
 * something has happened in the domain.
 * 
 * Creation date: (10/22/01 9:55:37 PM)
 * @author: <a href='mailto:greener@charter.net'>Rob Greene</a>
 * @version: RJG 10/22/2001 23:31:31 
 */
public interface DomainListener {
/**
 * A DomainListener will receive a domainChanged event
 * when the domain state has been modified.
 *
 * Creation date: (10/22/01 9:56:33 PM)
 */
public void domainChanged(DomainEvent domainEvent);
}