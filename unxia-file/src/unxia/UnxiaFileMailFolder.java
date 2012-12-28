/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Zugriff auf unxia-file Mailordner
 */
public class UnxiaFileMailFolder implements UnxiaMailFolder {
	private final String folder;
	private final List<Map<String, String>> entries;
	
	UnxiaFileMailFolder(String folder, List<Map<String, String>> entries) {
		this.folder = folder;
		this.entries = entries;
	}
	
	@Override
	public List<UnxiaMail> getEntries(int max) {
		List<UnxiaMail> ret = new ArrayList<UnxiaMail>();
		for (Map<String, String> doc : entries) {
			if ("MAIL".equals(doc.get(UnxiaFileReader.TYPE)) && doc.get("folder").equals(folder)) {
				UnxiaMail mail = new UnxiaMail();
				set(doc, mail);
				ret.add(mail);
				if (ret.size() == max) break;
			}
		}
		return ret;
	}

	private void set(Map<String, String> d, UnxiaMail m) {
		m.setId(d.get("id"));
		m.setFrom(d.get("from"));
		m.setSubject(d.get("subject"));
		m.setDate(UnxiaDateService.toDateTime(d.get("date")));
		m.setPrincipal(d.get("principal"));
		m.setBody(d.get("body"));
		for (String ea : d.get("to").split(";")) {
			m.addTo(ea.trim());
		}
		if (d.get("cc") != null) {
			for (String ea : d.get("cc").split(";")) {
				m.addCc(ea.trim());
			}
		}
		if (d.get("bcc") != null) {
			for (String ea : d.get("bcc").split(";")) {
				m.addBCc(ea.trim());
			}
		}
		// TODO Attachments
	}
	
	@Override
	public void cleanup() {
	}

	@Override
	public List<UnxiaMail> getEntries() {
		return getEntries(250);
	}
}
