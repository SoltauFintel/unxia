package unxia;

import java.util.Map;

/**
 * Unxia: universeller Mail und Kalender Zugriff
 * 
 * <p>Ziel von Unxia ist es, Mail/Kalender-Programme wie IBM Lotus Notes 8.5 und
 * Microsoft Outlook universell anzubinden.
 * 
 * <p>Aufruffolge: setConfig, login, getCalendar|getMailInboxFolder|createNewMail+send,
 * logout.
 * 
 * <p>Warum hei�t dieses Projekt "Unxia"? Unxia ist eine r�mische G�ttin.
 * 
 * <p>Copyright 2012 by Marcus Warm
 * 
 * @author Marcus Warm
 * @since  26.12.2012
 */
public interface Unxia {

	/**
	 * Vor login() aufzurufen. Die Keys sind der jeweiligen Unxia Implementierung
	 * zu entnehmen.
	 * @param config
	 */
	void setConfig(Map<String, String> config);
	
	/**
	 * Am Mail/Kalender-Programm anmelden.
	 */
	void login();
	
	/**
	 * Nach erfolgreichem login ist logout() im finally Block aufzurufen.
	 */
	void logout();
	
	/**
	 * @return Zugriff auf Kalender
	 */
	UnxiaCalendar getCalendar();
	
	/**
	 * @return Zugriff auf eingegangene Emails
	 */
	UnxiaMailFolder getMailInboxFolder();

	/**
	 * Email versenden
	 * <p>Das Email wird stets sofort gesendet und danach gespeichert.
	 * 
	 * @param mail das zu versendende Mail
	 */
	void send(UnxiaMail mail);

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
