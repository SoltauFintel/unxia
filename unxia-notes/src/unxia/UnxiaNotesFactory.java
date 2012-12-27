/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

/**
 * Unxia Factory für Lotus Notes 8.5
 * 
 * <h2>Wichtige Hinweise zur Eclipse Workspace Einrichtung:</h2>
 * 
 * <h3>NOTESJAR</h3>
 * <p>Die Variable NOTESJAR muss auf die Notes.jar verweisen.
 * z.B. 'C:\Program files (x86)\IBM\Lotus\Notes\jvm\ext\Notes.jar'
 * <br>unxia-notes -> Rechtsklick -> Build Path -> Confige Build Path
 * -> Libraries tab -> Add Variable button -> Configure Variables -> New
 * -> NOTESJAR als Name eingeben und über File Button die Notes.jar auswählen.</p>
 * 
 * <h3>JRE</h3>
 * <p>Es muss eine JRE namens "jvm" im Eclipse eingerichtet sein.
 * Diese wird vom Projekt unxia-notes verwendet.
 * Die JRE liegt z.B. im Ordner 'C:\Program files (x86)\IBM\Lotus\Notes\jvm'.</p>
 */
public final class UnxiaNotesFactory implements UnxiaFactory {

	@Override
	public Unxia create() {
		return new Notes();
	}
}
