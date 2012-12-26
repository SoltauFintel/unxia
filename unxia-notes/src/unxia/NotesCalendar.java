package unxia;

import java.util.ArrayList;
import java.util.List;

import lotus.domino.DateRange;
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
			calendar = notes.database.getView("($Calendar)");
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
					getDateRange(vonDatum, bisDatum), true);
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
	
	private DateRange getDateRange(String vonDatum, String bisDatum) {
		try {
			DateTime von = notes.session.createDateTime(vonDatum);
			DateTime bis = notes.session.createDateTime(bisDatum);
			return notes.session.createDateRange(von, bis);
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
		e.setType(Integer.parseInt(d.getItemValueString("AppointmentType")));
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
}
