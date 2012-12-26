package unxia;

/**
 * Kalendereintrag
 */
public class UnxiaCalendarEntry {
	private String id;
	/** Text */
	private String body;
	/** Leitung der Besprechung */
	private String chair;
	/** Start Datum Uhrzeit */
	private String begin;
	/** Ende Datum Uhrzeit */
	private String end;
	/** neues Start Datum Uhrzeit */
	private java.util.Date newBegin = null;
	/** neues Ende Datum Uhrzeit */
	private java.util.Date newEnd = null;
	/** Autor */
	private String from;
	/** Betreff */
	private String subject;
	/** Ort */
	private String location;
	/**
	 * Art des Kalendereintrags:
	 * <br>0: Termin
	 * <br>1: Jahrestag
	 * <br>2: ganztägige Veranstaltung (ohne Uhrzeit)
	 * <br>3: Besprechung
	 * <br>4: Erinnerung
	 */
	private int type;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getChair() {
		return chair;
	}

	public void setChair(String chair) {
		this.chair = chair;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public java.util.Date getNewBegin() {
		return newBegin;
	}

	public void setNewBegin(java.util.Date newBegin) {
		this.newBegin = newBegin;
	}

	public java.util.Date getNewEnd() {
		return newEnd;
	}

	public void setNewEnd(java.util.Date newEnd) {
		this.newEnd = newEnd;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getType() {
		return type;
	}

	public String getTypetext() {
		if (type == 0) {
			return "Termin";
		} else if (type == 1) {
			return "Jahrestag";
		} else if (type == 2) {
			return "Ganztägige Veranstaltung";
		} else if (type == 3) {
			return "Besprechung";
		} else if (type == 4) {
			return "Erinnerung";
		}
		return "" + type;
	}
	
	/**
	 * @param type Art des Kalendereintrags:
	 * <br>0: Termin
	 * <br>1: Jahrestag
	 * <br>2: ganztägige Veranstaltung (ohne Uhrzeit)
	 * <br>3: Besprechung
	 * <br>4: Erinnerung
	 */
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		String ret = begin + " - " + end + ": " + subject
				+ "\nID: " + id
				+ "\nArt: " + type + " - " + getTypetext();
		if (!location.isEmpty()) {
			ret += "\nOrt: " + location;
		}
		if (!body.trim().isEmpty()) {
			ret += "\n" + body;
		}
		return ret;
	}
}
