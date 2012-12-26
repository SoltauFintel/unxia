package unxia;

import java.util.List;

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
}
