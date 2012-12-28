/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnxiaFileMailFolder implements UnxiaMailFolder {
	private final List<Map<String, String>> entries;
	
	UnxiaFileMailFolder(List<Map<String, String>> entries) {
		this.entries = entries;
	}
	
	@Override
	public List<UnxiaMail> getEntries(int max) {
		List<UnxiaMail> ret = new ArrayList<UnxiaMail>();
		for (Map<String, String> doc : entries) {
			if ("MAIL".equals(doc.get(UnxiaFileReader.TYPE))) {
				if (ret.size() == max) break;
				UnxiaMail mail = new UnxiaMail();
				set(doc, mail);
				ret.add(mail);
			}
		}
		return ret;
	}

	private void set(Map<String, String> d, UnxiaMail m) {
		m.setId(d.get("id"));
		m.setFrom(d.get("from"));
		m.setSubject(d.get("subject"));
		m.setDate(new java.util.Date()); // TODO
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
