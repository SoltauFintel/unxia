/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

/**
 * Unxia Dummy Implementierung, die Emails und Kalendereintr�ge einfach
 * aus einer Datei liest.
 */
public final class UnxiaFileFactory implements UnxiaFactory {

	@Override
	public Unxia create() {
		return new UnxiaFileGroupware();
	}
}
