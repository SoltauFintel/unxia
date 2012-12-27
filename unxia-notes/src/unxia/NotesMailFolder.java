/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;

public class NotesMailFolder implements UnxiaMailFolder {
	private View folder;
	
	NotesMailFolder(Notes notes, String foldername) {
		try {
			folder = notes.getView(foldername);
		} catch (NotesException ex) {
			throw new UnxiaException(ex);
		}
	}
	
	@Override
	public void cleanup() {
		if (folder != null) {
			try {
				folder.recycle();
				folder = null;
			} catch (NotesException e) {
				throw new UnxiaException(e);
			}
		}
	}
	
	@Override
	public List<UnxiaMail> getEntries() {
		return getEntries(250);
	}

	@Override
	public List<UnxiaMail> getEntries(int max) {
		try {
			List<UnxiaMail> ret = new ArrayList<UnxiaMail>();
			ViewEntryCollection entries = folder.getAllEntries();
			ViewEntry entry = entries.getLastEntry();
			int i = 0;
			while (entry != null && (max < 0 || i++ < max)) {
				Document d = entry.getDocument();
				ret.add(doc2Mail(d));
				d.recycle();

				entry = entries.getPrevEntry();
			}
			entries.recycle();
			return ret;
		} catch (NotesException e) {
			throw new UnxiaException(e);
		}
	}
	
	private UnxiaMail doc2Mail(Document d) throws NotesException {
		UnxiaMail mail = new UnxiaMail();
		mail.setSubject(d.getItemValueString("Subject"));
		mail.setDate(d.getCreated().toJavaDate());
		mail.setFrom(d.getItemValueString("From"));
		Vector<?> v = d.getItemValue("SendTo");
		for (int j = 0; j < v.size(); j++) {
			mail.addTo((String) v.get(j));
		}
		mail.setBody(d.getItemValueString("Body"));
		mail.setPrincipal(d.getItemValueString("Principal"));
		return mail;
	}
}
