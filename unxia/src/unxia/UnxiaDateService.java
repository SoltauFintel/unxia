/*
 * Copyright 2012 by Marcus Warm
 */
package unxia;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Date/Time <-> String Umwandlungen
 */
public class UnxiaDateService {

	private UnxiaDateService() {
	}
	
	/**
	 * @param pDatum Datum als String im SHORT-Format (in Deutschland "TT.MM.JJJJ")
	 * @return Datum als java.sql.Date
	 */
	public static java.sql.Date toDate(String pDatum) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		try {
			return new java.sql.Date(df.parse(pDatum).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param pDatum Datum, Typ: java.sql.Date oder java.util.Date
	 * @return Datum als String im Format "TT.MM.JJJJ"
	 */
	public static String formatDate(java.util.Date pDatum) {
		Calendar c = Calendar.getInstance();
		c.setTime(pDatum);
		return zweistellig(c.get(Calendar.DAY_OF_MONTH)) + "." +
			zweistellig(c.get(Calendar.MONTH) + 1) + "." +
			c.get(Calendar.YEAR);
	}
	
	/**
	 * @param date Datum und Uhrzeit (java.util.Date) 
	 * @return Datum Uhrzeit als String im Format "TT.MM.JJJJ HH:MM:SS"
	 */
	public static String formatDateTime(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return zweistellig(c.get(Calendar.DAY_OF_MONTH)) + "." +
			zweistellig(c.get(Calendar.MONTH) + 1) + "." +
			c.get(Calendar.YEAR) + " " +
			zweistellig(c.get(Calendar.HOUR_OF_DAY)) + ":" +
			zweistellig(c.get(Calendar.MINUTE)) + ":" +
			zweistellig(c.get(Calendar.SECOND));
	}
	
	private static String zweistellig(int g) {
		if (g < 10) {
			return "0" + g;
		} else {
			return "" + g;
		}
	}
	
}
