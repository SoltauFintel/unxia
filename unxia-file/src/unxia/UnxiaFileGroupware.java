/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.Map;

public class UnxiaFileGroupware implements Unxia {
	public static String CFG_MAILFILE = "mailfile";
	private String mailfile;
	
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
		System.out.println("[login] mailfile = " + mailfile);
	}

	@Override
	public void logout() {
	}

	@Override
	public UnxiaCalendar getCalendar() {
		throw new UnsupportedOperationException();
	}

	@Override
	public UnxiaMailFolder getInbox() {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void send(UnxiaMail mail) {
		send(mail, true, true);
	}

	@Override
	public void send(UnxiaMail mail, boolean send, boolean save) {
		throw new UnsupportedOperationException();
	}
}
