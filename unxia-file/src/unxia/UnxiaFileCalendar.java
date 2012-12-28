/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Zugriff auf unxia-file Kalender
 */
public class UnxiaFileCalendar implements UnxiaCalendar {
	public static final String TYPE = "CALENDAR";
	private final List<Map<String, String>> entries;
	private boolean dirty = false;
	
	UnxiaFileCalendar(List<Map<String, String>> entries) {
		this.entries = entries;
		int n = 0;
		for (Map<String, String> doc : this.entries) {
			if (TYPE.equals(doc.get(UnxiaFileReader.TYPE))) {
				n++;
			}
		}
		System.out.println(n + " Kalendereinträge");
	}
	
	@Override
	public void cleanup() {
		if (dirty) {
			// mailfile speichern
		}
	}

	@Override
	public List<UnxiaCalendarEntry> getEntries(String vonDatum, String bisDatum) {
		java.sql.Date von = UnxiaDateService.toDate(vonDatum);
		java.sql.Date bis = UnxiaDateService.toDate(bisDatum);
		List<UnxiaCalendarEntry> ret = new ArrayList<UnxiaCalendarEntry>();
		for (Map<String, String> doc : entries) {
			if (TYPE.equals(doc.get(UnxiaFileReader.TYPE))) {
				java.util.Date d = UnxiaDateService.toDateTime(doc.get("begin"));
				if (!d.before(von) && !d.after(bis)) {
					ret.add(create(doc));
				}
			}
		}
		return ret;
	}

	@Override
	public UnxiaCalendarEntry getEntry(String id) {
		for (Map<String, String> doc : entries) {
			if (TYPE.equals(doc.get(UnxiaFileReader.TYPE))) {
				if (id.equals(doc.get("id"))) {
					return create(doc);
				}
			}
		}
		return null;
	}

	private UnxiaCalendarEntry create(Map<String, String> doc) {
		UnxiaCalendarEntry k = new UnxiaCalendarEntry();
		k.setId(doc.get("id"));
		k.setBody(doc.get("body") == null ? "" : doc.get("body"));
		k.setChair(doc.get("chair"));
		k.setBegin(doc.get("begin"));
		k.setEnd(doc.get("end"));
		k.setFrom(doc.get("from"));
		k.setSubject(doc.get("subject"));
		k.setLocation(doc.get("location") == null ? "" : doc.get("location"));
		k.setType(Integer.parseInt(doc.get("type")));
		return k;
	}
	
	@Override
	public void saveNew(UnxiaCalendarEntry entry) {
		entry.setId(UnxiaFileGroupware.genId());
	}

	@Override
	public void save(UnxiaCalendarEntry entry) {
	}

	@Override
	public boolean remove(String id) {
		for (Map<String, String> doc : entries) {
			if (TYPE.equals(doc.get(UnxiaFileReader.TYPE))) {
				if (id.equals(doc.get("id"))) {
					entries.remove(doc);
					dirty = true;
					return true;
				}
			}
		}
		return false;
	}
}
