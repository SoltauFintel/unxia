/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.ArrayList;
import java.util.List;

import lotus.domino.DateTime;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;

public class NotesCalendar implements UnxiaCalendar {
	private final Notes notes;
	private View calendar;
	
	NotesCalendar(Notes notes) {
		this.notes = notes;
		try {
			calendar = notes.getView("($Calendar)");
		} catch (NotesException e) {
			throw new UnxiaException(e);
		}
	}
	
	@Override
	public void cleanup() {
		try {
			if (calendar != null) {
				calendar.recycle();
				calendar = null;
			}
		} catch (NotesException e) {
			throw new UnxiaException(e);
		}
	}
	
	@Override
	public List<UnxiaCalendarEntry> getEntries(String vonDatum, String bisDatum) {
		try {
			List<UnxiaCalendarEntry> ret = new ArrayList<UnxiaCalendarEntry>();
			ViewEntryCollection entries = calendar.getAllEntriesByKey(
					notes.createDateRange(vonDatum, bisDatum), true);
			ViewEntry entry = entries.getFirstEntry();
			while (entry != null) {
				Document d = entry.getDocument();
				UnxiaCalendarEntry k = new UnxiaCalendarEntry();
				copyToEntry(d, k);
				d.recycle();
				addToList(k, ret);

				entry = entries.getNextEntry();
			}
			entries.recycle();
			return ret;
		} catch (NotesException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void copyToEntry(Document d, UnxiaCalendarEntry e) throws NotesException {
		e.setId(d.getUniversalID());
		e.setBegin(datumuhrzeit(d, "StartDateTime"));
		e.setEnd(datumuhrzeit(d, "EndDateTime"));
		e.setSubject(d.getItemValueString("Subject"));
		e.setChair(d.getItemValueString("Chair")); // "CN=Marcus Warm/O=GENEVA-ID"
		e.setBody(d.getItemValueString("Body"));
		e.setFrom(d.getItemValueString("From"));
		e.setLocation(d.getItemValueString("Location"));
		try {
			e.setType(Integer.parseInt(d.getItemValueString("AppointmentType")));
		} catch (NumberFormatException e1) {
			System.err.println("AppointmentType '"
					+ d.getItemValueString("AppointmentType")
					+ "' ist keine Zahl! ID: " + d.getUniversalID());
			e.setType(-1);
		}
	}
	
	private String datumuhrzeit(Document d, String feldname) throws NotesException {
		return d.getItemValueDateTimeArray(feldname).toString()
				.replace("[", "").replace("]", "");
	}
	
	private void addToList(UnxiaCalendarEntry entry, List<UnxiaCalendarEntry> entries) {
		for (UnxiaCalendarEntry i : entries) {
			if (entry.getId().equals(i.getId())) {
				return;
			}
		}
		entries.add(entry);
	}

	@Override
	public UnxiaCalendarEntry getEntry(String id) {
		try {
			Document d = notes.byId(id);
			if (d == null) return null;
			UnxiaCalendarEntry e = new UnxiaCalendarEntry();
			copyToEntry(d, e);
			return e;
		} catch (NotesException ex) {
			throw new UnxiaException(ex);
		}
	}

	@Override
	public void saveNew(UnxiaCalendarEntry e) {
		try {
			Document d = notes.createDocument();
			DateTime startDate = notes.createDateTime(e.getNewBegin());
			DateTime endDate = notes.createDateTime(e.getNewEnd());
			d.replaceItemValue("Form", "Appointment");
			d.replaceItemValue("AppointmentType", "" + e.getType());
			d.replaceItemValue("Subject", e.getSubject());
			d.replaceItemValue("$PublicAccess", "1"); // "0" privat
			d.replaceItemValue("CALENDARDATETIME", startDate);
			d.replaceItemValue("StartDateTime", startDate);
			d.replaceItemValue("EndDateTime", endDate);
			d.replaceItemValue("StartDate", startDate);
			d.replaceItemValue("MeetingType", "1");
			d.replaceItemValue("Body", e.getBody());
			d.replaceItemValue("Location", e.getLocation());

			if (e.getChair() != null && !e.getChair().isEmpty()) {
				d.replaceItemValue("Chair", e.getChair());
			}

			/*d.replaceItemValue("$Alarm", "1");
			d.replaceItemValue("$AlarmDescription", "hello world (alarm)");
			d.replaceItemValue("$AlarmOptions", "");
			d.replaceItemValue("$AlarmOffset", "5"); // 5 Minuten vorher
			d.replaceItemValue("$AlarmSound", "tada");
			d.replaceItemValue("$AlarmUnit", "M");*/
			d.computeWithForm(true, false);
			d.save(true, false, false);
			e.setId(d.getUniversalID());
		} catch (NotesException ex) {
			throw new UnxiaException(ex);
		}
	}

	@Override
	public void save(UnxiaCalendarEntry k) {
		try {
			Document d = notes.byId(k.getId());
			try {
				d.replaceItemValue("Subject", k.getSubject());
				d.replaceItemValue("Location", k.getLocation()); 
				d.replaceItemValue("Body", k.getBody());
				if (k.getNewBegin() != null) {
					DateTime startDate = notes.createDateTime(k.getNewBegin());
					d.replaceItemValue("CALENDARDATETIME", startDate);
					d.replaceItemValue("StartDateTime", startDate);
					d.replaceItemValue("StartDate", startDate);
				}
				if (k.getNewEnd() != null) {
					DateTime endDate = notes.createDateTime(k.getNewEnd());
					d.replaceItemValue("EndDateTime", endDate);
				}
				if (k.getChair() != null) {
					d.replaceItemValue("Chair", k.getChair());
				}
				if (!d.save()) {
					throw new UnxiaException("Fehler beim Speichern des Dokuments. save liefert false.");
				}
			} finally {
				d.recycle();
			}
		} catch (NotesException ex) {
			throw new UnxiaException(ex);
		}
	}

	@Override
	public boolean remove(String id) {
		try {
			Document d = notes.byId(id);
			if (d == null) {
				throw new UnxiaException("Kalendereintrag " + id + " nicht vorhanden!");
			}
			return d.remove(false);
		} catch (NotesException e) {
			throw new UnxiaException(e);
		}
	}
}
