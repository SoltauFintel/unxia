/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Auf Lotus Notes 8.5 Kalendereinträge zugreifen.
 * 
 * <p>Es muss die Umgebungsvariable PATH auf das Notes Verzeichnis
 * (z.B. "C:\Program Files (x86)\IBM\Lotus\Notes") zeigen.
 * 
 * <p>Entwickler-Test. Testmethoden einzeln ausführen!
 */
public class TestNotesCalendar {

	private Unxia getGroupware() {
		return new Notes("GI-KEV-DOM01/GENEVA-ID", "mail\\kev\\mwarm.nsf");
	}

	@Test
	public void listCalendarEntries() {
		System.out.println("PATH: " + System.getenv("PATH"));
		Unxia mc = getGroupware();
		try {
			UnxiaCalendar cal = mc.getCalendar();
			try {
				listCalendarEntries(cal);
			} finally {
				cal.cleanup();
			}
		} finally {
			mc.logout();
		}
	}
	
	private void listCalendarEntries(UnxiaCalendar cal) {
		List<UnxiaCalendarEntry> entries =
				cal.getEntries("12.12.2011", "02.01.2012");
		for (UnxiaCalendarEntry e : entries) {
			System.out.println("----------------------------");
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void addCalendarEntry() {
		System.out.println("PATH: " + System.getenv("PATH"));
		Unxia mc = getGroupware();
		try {
			UnxiaCalendar cal = mc.getCalendar();
			try {
				addCalendarEntry(cal);
			} finally {
				cal.cleanup();
			}
		} finally {
			mc.logout();
		}
	}

	private void addCalendarEntry(UnxiaCalendar cal) {
		UnxiaCalendarEntry k = new UnxiaCalendarEntry();
		k.setSubject("Tomate");
		k.setBody("Zeile 1\nZeile 2");
		k.setNewBegin(getDate(2011, Calendar.DECEMBER, 25,   8, 10));
		k.setNewEnd(  getDate(2011, Calendar.DECEMBER, 25,   8, 40));
		k.setType(3);
		k.setLocation("zuhause");
		k.setChair("Marcus Warm/GENEVA-ID");
		Assert.assertNull(k.getId());
		cal.saveNew(k);
		System.out.println("neuen Kalendereintrag gespeichert: " + k.getSubject()
				+ " / " + k.getNewBegin().toString() + " / " + k.getTypetext()
				+ " / ID: " + k.getId());
		Assert.assertNotNull(k.getId());
	}
	
	private java.util.Date getDate(int year, int month, int day,
			int hour, int minutes) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, hour, minutes, 0);
		return c.getTime();
	}
	
	@Test
	public void updateCalendarEntry() {
		System.out.println("PATH: " + System.getenv("PATH"));
		Unxia mc = getGroupware();
		try {
			UnxiaCalendar cal = mc.getCalendar();
			try {
				updateCalendarEntry(cal);
			} finally {
				cal.cleanup();
			}
		} finally {
			mc.logout();
		}
	}

	private void updateCalendarEntry(UnxiaCalendar cal) {
		UnxiaCalendarEntry e;
		e = getEntry(cal, "Tomate");
//		e = cal.getEntry("TO DO");
		e.setSubject("Ketchup " + e.getId().substring(0, 6));
		e.setBody(e.getBody() + "\n\nDa hab ich die Tomate zu Ketchup gemacht.");
		cal.save(e);
	}
	
	private UnxiaCalendarEntry getEntry(UnxiaCalendar cal, String subject) {
		List<UnxiaCalendarEntry> entries = cal.getEntries("01.12.2011", "02.01.2012");
		for (UnxiaCalendarEntry e : entries) {
			if (e.getSubject().equals(subject)) {
				return e;
			}
		}
		return null;
	}
	
	@Test
	public void removeCalendarEntry() {
		System.out.println("PATH: " + System.getenv("PATH"));
		Unxia mc = getGroupware();
		try {
			UnxiaCalendar cal = mc.getCalendar();
			try {
				cal.remove("TO DO");
			} finally {
				cal.cleanup();
			}
		} finally {
			mc.logout();
		}
	}
}
