package verarbeitung;

import java.time.LocalDate;

public class Student extends Kunde {

	/**
	 * Name der Universität
	 */
	private String uni;

	/**
	 * Studienfach
	 */
	private String fach;

	/**
	 * erzeugt einen Standardstudenten
	 */
	public Student() {
		super();
		this.uni = "HTW";
		this.fach = "AI";
	}

	/**
	 * erzeugt einen Studenten mit den übergebenen Werten
	 * 
	 * @param vorname
	 * @param nachname
	 * @param adresse
	 * @param gebDat
	 * @param uni
	 * @param fach
	 * @throws IllegalArgumentException
	 *             wenn einer der Parameter null ist
	 */
	public Student(String vorname, String nachname, String adresse, LocalDate gebDat, String uni, String fach) {
		super(vorname, nachname, adresse, gebDat);
		if (uni == null || fach == null)
			throw new IllegalArgumentException("null als Parameter nicht erlaubt");
		this.uni = uni;
		this.fach = fach;
	}

	/**
	 * letztes Datum, an dem eine Studienbescheinigung eingereicht wurde
	 */
	private LocalDate letzteBescheinigung;

	/**
	 * aktualisiert das Einreichungsdatum der letzten Studienbescheinigung
	 */
	public void bescheinigungEintragen() {
		letzteBescheinigung = LocalDate.now();
	}

	/**
	 * trägt manuell das Datum der letzten Studienbescheinigung ein Diese methode
	 * ist nur zum Testen der Funktionalität von hatBescheinigung gedacht
	 * 
	 * @param datum
	 */
	public void manuellEintragen(LocalDate datum) {
		letzteBescheinigung = datum;
	}

	/**
	 * fragt ab, ob eine Semesterbescheinigung vorliegt
	 */
	public boolean hatBescheinigung() {

		if (letzteBescheinigung == null)
			return false;

		boolean sommerS = false;
		boolean auchSS = false;

		if (LocalDate.now().getMonthValue() >= 4 && LocalDate.now().getMonthValue() < 10)
			sommerS = true;

		if (letzteBescheinigung.getMonthValue() >= 4 && letzteBescheinigung.getMonthValue() < 10)
			auchSS = true;
		
		if (sommerS && !auchSS)
			return false;
		else if (!sommerS && auchSS)
			return false;
		else if (sommerS && auchSS) {
			if (letzteBescheinigung.getYear() == LocalDate.now().getYear())
				return true;
			else
				return false;
		} else if (!sommerS && !auchSS) {
			if (LocalDate.now().getMonthValue() >= 10) {
				if (letzteBescheinigung.getYear() == LocalDate.now().getYear())
					return true;
				else
					return false;
			} else {
				if (letzteBescheinigung.getYear() == LocalDate.now().getYear()
						|| letzteBescheinigung.getYear() + 1 == LocalDate.now().getYear())
					return true;
				else
					return false;
			}
		} else
			return false;
	}

	/**
	 * liefert das Datum zurück, an dem zuletzt eine aktuelle Semesterbescheinigung
	 * eingereicht wurde
	 * 
	 * @return das Einreichungsdatum der letzten Studienbescheinigung
	 */
	public LocalDate getBescheinigung() {
		return letzteBescheinigung;
	}

	@Override
	public String toString() {
		String ausgabe = "STUDENT" + System.lineSeparator() + super.toString() + "Universität: " + this.uni
				+ System.lineSeparator() + "Studienfach: " + this.fach + System.lineSeparator();
		return ausgabe;
	}
}
