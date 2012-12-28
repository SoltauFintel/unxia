/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UnxiaFileGroupware implements Unxia {
	public static String CFG_MAILFILE = "mailfile";
	private String mailfile;
	private List<Map<String, String>> entries;
	
	public UnxiaFileGroupware() {
	}

	public UnxiaFileGroupware(String mailfile) {
		this.mailfile = mailfile;
		login();
	}
	
	@Override
	public void setConfig(Map<String, String> config) {
		mailfile = config.get(CFG_MAILFILE);
	}

	@Override
	public void login() {
		try {
			entries = UnxiaFileReader.getEntries(mailfile);
		} catch (IOException e) {
			throw new UnxiaException(e);
		}
		System.out.println(mailfile + "   (" + entries.size() + " entries)");
	}

	@Override
	public void logout() {
	}

	@Override
	public UnxiaCalendar getCalendar() {
		return new UnxiaFileCalendar(entries);
	}

	@Override
	public UnxiaMailFolder getInbox() {
		return new UnxiaFileMailFolder("inbox", entries);
	}

	@Override
	public final void send(UnxiaMail mail) {
		send(mail, true, true);
	}

	@Override
	public void send(UnxiaMail mail, boolean send, boolean save) {
		mail.setId(genId());
	}
	
	public static String genId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
