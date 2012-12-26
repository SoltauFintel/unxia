package unxia;

import java.util.ArrayList;
import java.util.List;

/**
 * Eingegangenes oder zu versendenes Email
 * 
 * <p>Zu setzende Felder bei neuem Email:
 * subject, body, 1x toList. Optional: mehrfach toList, mehrfach ccList,
 * mehrfach bccList, mehrfach attachments.</p>
 */
public class UnxiaMail {
	private String id = null;
	private String subject = "";
	private String from;
	private java.util.Date date = null;
	private String body = "";
	private List<String> toList = new ArrayList<String>();
	private List<String> ccList = new ArrayList<String>();
	private List<String> bccList = new ArrayList<String>();
	/** Dateinamen der Anlagen */
	private List<String> attachments = new ArrayList<String>();
	private String principal;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void addTo(String m) {
		toList.add(m);
	}

	public void addCc(String m) {
		ccList.add(m);
	}

	public void addBCc(String m) {
		bccList.add(m);
	}

	public List<String> getToList() {
		return toList;
	}

	public List<String> getCcList() {
		return ccList;
	}

	public List<String> getBCcList() {
		return bccList;
	}

	public void addAttachment(String filename) {
		attachments.add(filename);
	}
	
	public List<String> getAttachments() {
		return attachments;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
}
