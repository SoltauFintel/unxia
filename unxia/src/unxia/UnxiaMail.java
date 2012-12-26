package unxia;

import java.util.List;

public class UnxiaMail {
	private String subject;
	private String from;
	private java.util.Date date;
	private String body;
	private List<String> toList;
	private List<String> ccList;
	private List<String> bccList;
	private List<String> attachments;
	
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
}
