package unxia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Auf Lotus Notes 8.5 Kalendereinträge zugreifen.
 * 
 * <p>Es muss die Umgebungsvariable PATH auf das Notes Verzeichnis
 * (z.B. "C:\Program Files (x86)\IBM\Lotus\Notes") zeigen.
 */
public class TestNotesCalendar {

	@Test
	public void listCalendarEntries() {
		Unxia mc = getNotes();
		try {
			UnxiaCalendar cal = mc.getCalendar();
			try {
				_listCalendarEntries(cal);
			} finally {
				cal.cleanup();
			}
		} finally {
			mc.logout();
		}
	}
	
	private void _listCalendarEntries(UnxiaCalendar cal) {
		List<UnxiaCalendarEntry> entries =
				cal.getEntries("12.12.2011", "02.01.2012");
		for (UnxiaCalendarEntry e : entries) {
			System.out.println("----------------------------");
			System.out.println(e.toString());
		}
	}
	
	private Unxia getNotes() {
		UnxiaFactory factory = new UnxiaNotesFactory(); // Notes spezifisch
		Map<String, String> config = new HashMap<String, String>();
		config.put(Notes.CFG_SERVER, "GI-KEV-DOM01/GENEVA-ID"); // Notes spezifisch
		config.put(Notes.CFG_MAILFILE, "mail\\kev\\mwarm.nsf"); // Notes spezifisch
		Unxia notes = factory.create();
		notes.setConfig(config);
		notes.login();
		return notes;
	}
}
