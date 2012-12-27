/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

/**
 * Liefert Unxia Implementierung 
 */
public interface UnxiaFactory {
	
	/**
	 * @return Mail/Kalender Zugriff
	 */
	Unxia create();
}
