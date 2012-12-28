package unxia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnxiaFileCalendar implements UnxiaCalendar {
	public static final String TYPE = "CALENDAR";
	private final List<Map<String, String>> entries;
	private boolean dirty = false;
	
	UnxiaFileCalendar(List<Map<String, String>> entries) {
		this.entries = entries;
	}
	
	@Override
	public void cleanup() {
		if (dirty) {
			// mailfile speichern
		}
	}

	@Override
	public List<UnxiaCalendarEntry> getEntries(String vonDatum, String bisDatum) {
		List<UnxiaCalendarEntry> ret = new ArrayList<UnxiaCalendarEntry>();
		for (Map<String, String> doc : entries) {
			if (TYPE.equals(doc.get(UnxiaFileReader.TYPE))) {
				ret.add(create(doc));
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
