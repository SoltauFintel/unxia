package unxia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import lotus.domino.Database;
import lotus.domino.DateRange;
import lotus.domino.DateTime;
import lotus.domino.DbDirectory;
import lotus.domino.Document;
import lotus.domino.EmbeddedObject;
import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.RichTextItem;
import lotus.domino.Session;
import lotus.domino.View;

/**
 * IBM Lotus Notes 8.5 Zugriff
 * 
 * <p>Der Zugriff erfolgt via Notes.jar.
 */
public class Notes implements Unxia {
	public static String CFG_SERVER = "server";
	public static String CFG_MAILFILE = "mailfile";
	private Map<String, String> config;
	private Session session;
	private Database database;

	public Notes() {
	}

	/**
	 * @param server z.B. "GI-KEV-DOM01/GENEVA-ID"
	 * @param mailfile relativer Pfad zur .nsf Datei
	 */
	public Notes(String server, String mailfile) {
		config = new HashMap<String, String>();
		config.put(CFG_SERVER, server);
		config.put(CFG_MAILFILE, mailfile);
		login();
	}
	
	/**
	 * Es müssen die Parameter CFG_SERVER und CFG_MAILFILE angegeben werden.
	 */
	@Override
	public void setConfig(Map<String, String> config) {
		this.config = config;
	}
	
	/* Wenn kein Notes Client läuft:
	 * <p>Registration reg = s.createRegistration();
	 * <p>reg.switchToID("d:\\lotus\\notes\\data\\bku.id", "mypassword");
	 * <p>(Code noch nicht getestet)
	 */
	@Override
	public void login() {
		try {
			NotesThread.sinitThread();
			session = NotesFactory.createSession();
			database = session.getDatabase(config.get(CFG_SERVER),
					config.get(CFG_MAILFILE));
			if (!database.isOpen()) {
				logout();
				throw new UnxiaException("Notes Datenbank konnte nicht geöffnet werden. Mögliche Ursache: Mailfile nicht vorhanden.");
			}
		} catch (NotesException e) {
			throw new UnxiaException(e);
		}
	}

	@Override
	public void logout() {
		try {
			if (database != null) {
				database.recycle();
				database = null;
			}
			if (session != null) {
				session.recycle();
				session = null;
			}
			NotesThread.stermThread();
		} catch (NotesException e) {
			throw new UnxiaException(e);
		}
	}

	@Override
	public UnxiaCalendar getCalendar() {
		return new NotesCalendar(this);
	}

	@Override
	public UnxiaMailFolder getInbox() {
		return new NotesMailFolder(this, "($Inbox)");
	}

	@Override
	public final void send(UnxiaMail mail) {
		send(mail, true, true);
	}
	
	/**
	 * @param send wird nicht unterstützt
	 */
	@Override
	public void send(UnxiaMail mail, boolean send, boolean save) {
		try {
			DbDirectory dir = session.getDbDirectory(config.get(CFG_SERVER));
			Database db = dir.openMailDatabase();
			Document d = db.createDocument();
			d.appendItemValue("Form", "Memo");
			d.appendItemValue("Subject", mail.getSubject());
			d.appendItemValue("Body", mail.getBody());

			appendList(mail.getToList(), d, "SendTo");
			appendList(mail.getCcList(), d, "CopyTo");
			appendList(mail.getBCcList(), d, "BlindCopyTo");
			
			int lfd = 0;
			for (String dn : mail.getAttachments()) {
				RichTextItem anlage = d.createRichTextItem("attachment" + (++lfd));
				anlage.embedObject(EmbeddedObject.EMBED_ATTACHMENT, "", dn, null);
			}
			
			d.computeWithForm(true, false);
			d.setSaveMessageOnSend(save);
			d.send();
			mail.setId(d.getUniversalID());
		} catch (NotesException e) {
			throw new UnxiaException(e);
		}
	}
	
	private void appendList(List<String> mails, Document d, String feldname) throws NotesException {
		Vector<String> v = new Vector<String>();
		for (String m : mails) {
			v.add(m);
		}
		d.appendItemValue(feldname, v);
	}

	Document byId(String id) throws NotesException {
		return database.getDocumentByUNID(id);
	}
	
	/**
	 * @param datum Datum und Uhrzeit
	 * @return Notes DateTime
	 * @throws NotesException
	 */
	DateTime createDateTime(java.util.Date datum) throws NotesException {
		return session.createDateTime(datum);
	}

	/**
	 * @param vonDatum Format TT.MM.JJJJ
	 * @param bisDatum Format TT.MM.JJJJ
	 * @return Notes DateRange
	 * @throws NotesException
	 */
	DateRange createDateRange(String vonDatum, String bisDatum) throws NotesException {
		DateTime von = session.createDateTime(vonDatum);
		DateTime bis = session.createDateTime(bisDatum);
		return session.createDateRange(von, bis);
	}
	
	Document createDocument() throws NotesException {
		return database.createDocument();
	}
	
	View getView(String name) throws NotesException {
		return database.getView(name);
	}
}
