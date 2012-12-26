package unxia;

import java.util.List;

/**
 * Zugriff auf einen Mail Ordner
 */
public interface UnxiaMailFolder {
	
	/**
	 * Muss aufgerufen werden bevor Objekt nicht mehr verwendet wird.
	 */
	void cleanup();
	
	/**
	 * Reihenfolge: jüngste Emails zuerst
	 * @return alle Emails des Ordners
	 */
	List<UnxiaMail> getEntries();

	/**
	 * Reihenfolge: jüngste Emails zuerst
	 * @param max Ganzzahl ab 1
	 * @return höchstens max Emails des Ordners
	 */
	List<UnxiaMail> getEntries(int max);
}
