/*
 * Copyright 2012 by Marcus Warm
 */
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
	 * Reihenfolge: j�ngste Emails zuerst
	 * @return alle Emails des Ordners
	 */
	List<UnxiaMail> getEntries();

	/**
	 * Reihenfolge: j�ngste Emails zuerst
	 * @param max Ganzzahl ab 1. Wenn kleiner 0 werden alle Mails geladen.
	 * @return h�chstens max Emails des Ordners
	 */
	List<UnxiaMail> getEntries(int max);
}
