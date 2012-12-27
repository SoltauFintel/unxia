/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.List;

/**
 * Zugriff auf Kalender
 */
public interface UnxiaCalendar {

	/**
	 * Muss aufgerufen werden bevor Objekt nicht mehr verwendet wird.
	 */
	void cleanup();
	
	/**
	 * @param vonDatum Format TT.MM.JJJJ
	 * @param bisDatum Format TT.MM.JJJJ
	 * @return Kalendereinträge
	 */
	List<UnxiaCalendarEntry> getEntries(String vonDatum, String bisDatum);
	
	/**
	 * Lädt Kalendereintrag anhand ID.
	 * @param id
	 * @return null wenn nicht gefunden
	 */
	UnxiaCalendarEntry getEntry(String id);
	
	/**
	 * Speichert neuen Kalendereintrag.
	 * @param entry
	 */
	void saveNew(UnxiaCalendarEntry entry);

	/**
	 * Speichert geladenen Kalendereintrag.
	 * @param entry
	 */
	void save(UnxiaCalendarEntry entry);
	
	/**
	 * Kalendereintrag anhand id löschen
	 * @param id
	 * @return Erfolg
	 */
	boolean remove(String id);
}
