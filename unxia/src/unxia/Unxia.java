package unxia;

import java.util.Map;

/**
 * Mail und Kalender Zugriff
 */
public interface Unxia {

	/**
	 * Vor login() aufzurufen. Die Keys sind der jeweiligen Unxia Implementierung
	 * zu entnehmen.
	 * @param config
	 */
	void setConfig(Map<String, String> config);
	
	void login();
	
	/**
	 * Nach erfolgtem login ist logout() im finally Block aufzurufen.
	 */
	void logout();
	
	/**
	 * @return Zugriff auf Kalender
	 */
	UnxiaCalendar getCalendar();
	
	/**
	 * @return Zugriff auf Posteingang
	 */
	UnxiaMailFolder getMailInboxFolder();
	
	UnxiaMail createNewMail();
	
	/**
	 * Email versenden
	 * <p>Die Implementierung muss send und save nicht zwingend unterst�tzen.
	 * 
	 * @param mail das zu versendende Mail
	 * @param send true wenn das Email sofort versendet werden soll,<br>
	 * false wenn der Anwender noch die M�glichkeit erhalten soll das Email
	 * zu sehen/�ndern.
	 * @param save true (Default) wenn Mail in Ordner 'Gesendete Objekte' o.�.
	 * gespeichert werden soll.<br>false wenn der Benutzer das Email sp�ter nicht
	 * sehen kann
	 */
	void send(UnxiaMail mail, boolean send, boolean save);
}
