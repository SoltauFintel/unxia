/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.List;

import org.junit.Test;

/**
 * Auf Mails zugreifen.
 * 
 * <p>Entwickler-Test. Testmethoden einzeln ausführen!
 */
public class TestNotesMails {

	private Unxia getNotes() {
		return new UnxiaFileGroupware("C:\\dat\\unxia\\mails.txt");
	}

	@Test
	public void inbox() {
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
		Unxia mc = getNotes();
		try {
			UnxiaMail mail = new UnxiaMail();
			mail.addTo("warm@mwvb.de");
			mail.setSubject("Unxia Testmail 8.0");
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
