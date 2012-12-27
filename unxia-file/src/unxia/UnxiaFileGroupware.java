/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.util.Map;

public class UnxiaFileGroupware implements Unxia {
	public static String CFG_MAILFILE = "mailfile";
//	private Map<String, String> config;
	
	@Override
	public void setConfig(Map<String, String> config) {
//		this.config = config;
	}

	@Override
	public void login() {
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
