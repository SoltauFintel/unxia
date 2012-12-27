package unxia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UnxiaFileReader {
	static final String CRLF = System.getProperty("line.separator");
	static final String TYPE = "!TYPE";
	
	static List<Map<String, String>> getEntries(String dn) throws IOException {
		List<Map<String, String>> entries = new ArrayList<Map<String, String>>();
		BufferedReader r = new BufferedReader(new FileReader(dn));
		try {
			Map<String, String> doc = null;
			String lt_name = null;
			String lt_value = "";
			String ende = null;
			boolean longtextMode = false;
			String zeile;
			while ((zeile = r.readLine()) != null) {
				if (longtextMode && set(lt_name) && set(ende)) { // multi line Document entry
					if (zeile.equals(ende)) {
						longtextMode = false;
						doc.put(lt_name, lt_value);
						lt_name = null;
					} else {
						lt_value += zeile + CRLF;
					}
				} else if (zeile.startsWith("::") && zeile.endsWith("::")) { // Document begin
					doc = new HashMap<String, String>();
					entries.add(doc);
					String type = zeile.substring("::".length(), zeile.length() - "::".length());
					doc.put(TYPE, type);
				} else if (zeile.startsWith("\tlongtext>") && zeile.contains(":")) { // multi line Document entry begin
					lt_name = zeile.substring(zeile.indexOf(">") + 1,
							zeile.indexOf(":")).trim();
					ende = zeile.substring(zeile.indexOf(":") + 1).trim();
					lt_value = "";
					longtextMode = true;
				} else if (zeile.startsWith("\t") && zeile.contains(":")) { // Document entry
					String name = zeile.substring(0, zeile.indexOf(":")).trim();
					String value = zeile.substring(zeile.indexOf(":") + 1).trim();
					doc.put(name, value);
				} else {
					throw new RuntimeException("Unbekannter Zeilentyp: "
							+ zeile + "\r\nDatei: " + dn);
				}
			}
		} finally {
			r.close();
		}
		return entries;
	}
	
	private static boolean set(String s) {
		return s != null && !s.trim().isEmpty();
	}
}
