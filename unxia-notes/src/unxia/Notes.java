package unxia;

import java.util.Map;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.Session;

/**
 * IBM Lotus Notes 8.5 Zugriff
 * 
 * <p>Der Zugriff erfolgt via Notes.jar.
 */
public class Notes implements Unxia {
	public static String CFG_SERVER = "server";
	public static String CFG_MAILFILE = "mailfile";
	private Map<String, String> config;
	Session session;
	Database database;

	/**
	 * Es müssen die Parameter CFG_SERVER und CFG_MAILFILE angegeben werden.
	 */
	@Override
	public void setConfig(Map<String, String> config) {
		this.config = config;
	}

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
	public UnxiaMailFolder getMailInboxFolder() {
		return new NotesMailFolder("($Inbox)");
	}

	@Override
	public void send(UnxiaMail mail, boolean send, boolean save) {
		// TODO Auto-generated method stub
	}

	@Override
	public UnxiaMail createNewMail() {
		return new UnxiaMail();
	}
	
	Document byId(String id) throws NotesException {
		return database.getDocumentByID(id);
	}
}
