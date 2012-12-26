package unxia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Auf Lotus Notes 8.5 Mails zugreifen.
 * 
 * <p>Es muss die Umgebungsvariable PATH auf das Notes Verzeichnis
 * (z.B. "C:\Program Files (x86)\IBM\Lotus\Notes") zeigen.
 * 
 * <p>Entwickler-Test. Testmethoden einzeln ausführen!
 */
public class TestNotesMails {

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

	@Test
	public void inbox() {
		System.out.println("PATH: " + System.getenv("PATH"));
		Unxia mc = getNotes();
		try {
			UnxiaMailFolder inbox = mc.getInbox();
			try {
				List<UnxiaMail> mails = inbox.getEntries(40);
				for (UnxiaMail mail : mails) {
					System.out.println("- " + mail.getDate().toString() // z.B. "Tue Dec 25 16:36:22 CET 2012"
							+ " | " + mail.getFrom() // z.B. "CN=Max Muster/O=Firma"
							+ " | \"" + mail.getSubject() + "\"");
				}
			} finally {
				inbox.cleanup();
			}
		} finally {
			mc.logout();
		}
	}
	
	@Test
	public void sendMail() {
		System.out.println("PATH: " + System.getenv("PATH"));
		Unxia mc = getNotes();
		try {
			UnxiaMail mail = new UnxiaMail();
			mail.addTo("warm@mwvb.de");
			mail.setSubject("Unxia Testmail 7.1");
			mail.setBody(new java.util.Date().toString() + "\nDas ist ein Test.");
			// TODO Cc, BCc Test
			// TODO attachments Test
			mc.send(mail);
			System.out.println("Mail versendet | \"" + mail.getSubject() + "\" | " + mail.getId());
		} finally {
			mc.logout();
		}
	}
}
