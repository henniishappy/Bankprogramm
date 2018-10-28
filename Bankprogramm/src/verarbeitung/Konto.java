package verarbeitung;

/**
 * stellt ein allgemeines Konto dar
 * @author doro
 * @author Henriette Sand, s0564285@htw-berlin.de
 * @version 1.1
 */
public abstract class Konto implements Comparable<Konto>
{
	/** 
	 * der Kontoinhaber
	 */
	private Kunde inhaber;

	/**
	 * die Kontonummer
	 */
	private final long nummer;

	/**
	 * der aktuelle Kontostand
	 */
	private double kontostand;

	/**
	 * setzt den aktuellen Kontostand
	 * @param kontostand neuer Kontostand
	 */
	protected void setKontostand(double kontostand) {
		this.kontostand = kontostand;
	}

	/**
	 * Wenn das Konto gesperrt ist (gesperrt = true), können keine Aktionen daran mehr vorgenommen werden,
	 * die zum Schaden des Kontoinhabers wären (abheben, Inhaberwechsel)
	 */
	private boolean gesperrt;
	
	/**
	 * die aktuelle Waehrung
	 */
	private Waehrung waehrung;

	/**
	 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
	 * der anfängliche Kontostand wird auf 0 gesetzt.
	 *
	 * @param inhaber Kunde
	 * @param kontonummer long
	 * @throws IllegalArgumentException wenn der Inhaber null
	 */
	public Konto(Kunde inhaber, long kontonummer) {
		if(inhaber == null)
			throw new IllegalArgumentException("Inhaber darf nicht null sein!");
		this.inhaber = inhaber;
		this.nummer = kontonummer;
		this.kontostand = 0;
		this.gesperrt = false;
		this.waehrung = Waehrung.EUR;
	}
	
	/**
	 * setzt alle Eigenschaften des Kontos auf Standardwerte
	 */
	public Konto() {
		this(Kunde.MUSTERMANN, 1234567);
	}

	/**
	 * liefert den Kontoinhaber zurück
	 * @return   Kunde
	 */
	public final Kunde getInhaber() {
		return this.inhaber;
	}
	
	/**
	 * setzt den Kontoinhaber
	 * @param kinh   neuer Kontoinhaber
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn kinh null ist
	 */
	public final void setInhaber(Kunde kinh) throws GesperrtException{
		if (kinh == null)
			throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
		if(this.gesperrt)
			throw new GesperrtException(this.nummer);        
		this.inhaber = kinh;

	}
	
	/**
	 * liefert den aktuellen Kontostand
	 * @return   double
	 */
	public final double getKontostand() {
		return kontostand;
	}

	/**
	 * liefert die Kontonummer zurück
	 * @return   long
	 */
	public final long getKontonummer() {
		return nummer;
	}
	
	/**
	 * liefert die aktuelle Waehrung zurück
	 * @return aktuelle Waehrung
	 */
	public Waehrung getAktuelleWaehrung() {
		return waehrung;
	}
	
	/**
	 * aendert die aktuelle Waehrung und passt den Kontostand an
	 * @param neu neue Waehrung
	 */
	public void waehrungswechsel(Waehrung neu) {
		if (getAktuelleWaehrung() == Waehrung.EUR) 
			this.kontostand = neu.euroInWaehrungUmrechnen(this.kontostand);
		else if (neu == Waehrung.EUR)
			this.kontostand = neu.waehrungInEuroUmrechnen(this.kontostand);
		else 
			this.kontostand = this.waehrung.waehrungenUmrechnen(neu, this.kontostand);
		this.waehrung = neu;
	}
	

	/**
	 * liefert zurück, ob das Konto gesperrt ist oder nicht
	 * @return true wenn das Konto gesperrt ist
	 * 		   false wenn das Konto nicht geseprrt ist 
	 */
	public final boolean isGesperrt() {
		return gesperrt;
	}
	
	/**
	 * Erhöht den Kontostand um den eingezahlten Betrag.
	 *
	 * @param betrag double
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 */
	public void einzahlen(double betrag) {
		if (betrag < 0) {
			throw new IllegalArgumentException("Negativer Betrag");
		}
		setKontostand(getKontostand() + betrag);
	}
	
	/**
	 * zahlt den in der Waehrung w angegebenen Betrag in der aktuellen Waehrung des Kontos ein
	 * @param betrag einzuzahlender Betrag
	 * @param w Waehrung des einzuzahlenden Betrags
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 */
	public void einzahlen(double betrag, Waehrung w) {
		if (betrag < 0)
			throw new IllegalArgumentException("Negativer Betrag");
		if (this.waehrung == w)
			this.einzahlen(betrag);
		else if (this.waehrung == Waehrung.EUR)
			this.einzahlen(w.waehrungInEuroUmrechnen(betrag));
		else if (w == Waehrung.EUR)
			this.einzahlen(this.waehrung.waehrungInEuroUmrechnen(betrag));
		else {
			this.einzahlen(w.waehrungenUmrechnen(this.waehrung, betrag));
		}
	}
	
	/**
	 * Gibt eine Zeichenkettendarstellung der Kontodaten zurück.
	 */
	@Override
	public String toString() {
		String ausgabe;
		ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
				+ System.getProperty("line.separator");
		ausgabe += "Inhaber: " + this.inhaber;
		ausgabe += "Aktueller Kontostand: " + this.kontostand + " Euro ";
		ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
		return ausgabe;
	}

	/**
	 * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist.
	 *
	 * @param betrag double
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 * @return true, wenn die Abhebung geklappt hat, 
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public abstract boolean abheben(double betrag) throws GesperrtException;
	
	/**
	 * hebt den gewünschten in der Waehrung w angegebenen Betrag ab, wenn es nicht gesperrt ist
	 * @param betrag abzuhebender Betrag in Euro
	 * @param w Waehrung der Abhebung
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @return true, wenn die Abhebung geklappt hat,
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public boolean abheben(double betrag, Waehrung w) throws GesperrtException {
		if (this.isGesperrt())
			throw new GesperrtException(this.getKontonummer());
		
		if (betrag < 0) 
			throw new IllegalArgumentException("Negativer Betrag");
		
		//Fallunterscheidung je nach Waehrung, in der das Konto geführt wird
		if (this.waehrung == w) {
			if (this.gedeckterBetrag(betrag)) {
				this.kontostand = this.kontostandNachAbhebung(betrag);
				return true;
			} else
				return false;
		} else if (this.waehrung == Waehrung.EUR) {
			if (this.gedeckterBetrag(w.waehrungInEuroUmrechnen(betrag))) {
				this.kontostand = this.kontostandNachAbhebung(w.waehrungInEuroUmrechnen(betrag));
				return true;
			} else
				return false;
		} else {
			if (this.gedeckterBetrag(w.waehrungenUmrechnen(this.waehrung, betrag))) {
				this.kontostand = this.kontostandNachAbhebung(w.waehrungenUmrechnen(this.waehrung, betrag));
			}
		}
			return false;
	}
	
	/**
	 * überprüft, ob der aktuelle Kontostand einen bestimmten Betrag deckt
	 * Es wird angenommen, dass der Betrag der Waehrung des Kontos entspricht
	 * @param betrag Betrag, der abgehoben werden soll
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @return true, wenn der Betrag gedeckt ist
	 */
	private boolean gedeckterBetrag(double betrag) {
		if (betrag < 0)
			throw new IllegalArgumentException("negativer Betrag");
		return (this.kontostand >= betrag);
	}
	
	/**
	 * zieht den angegebenen Betrag vom Konto ab
	 * Es wird angenommen, dass der Betrag der Waehrung des Kontos entspricht
	 * @param betrag abzuhebender Betrag
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @throws IllegalArgumentException wenn der betrag nicht gedeckt ist
	 * @return neuer Kontostand
	 */
	private double kontostandNachAbhebung(double betrag) {
		if (betrag < 0)
			throw new IllegalArgumentException("Negativer Betrag");
		if (!this.gedeckterBetrag(betrag))
			throw new IllegalArgumentException("Betrag ist nicht gedeckt");
		 return this.kontostand = this.kontostand - betrag;
	}
	
	/**
	 * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
	 */
	public final void sperren() {
		this.gesperrt = true;
	}

	/**
	 * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
	 */
	public final void entsperren() {
		this.gesperrt = false;
	}
	
	
	/**
	 * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
	 * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
	 */
	public final String getGesperrtText()
	{
		if (this.gesperrt)
		{
			return "GESPERRT";
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * liefert die ordentlich formatierte Kontonummer
	 * @return auf 10 Stellen formatierte Kontonummer
	 */
	public String getKontonummerFormatiert()
	{
		return String.format("%10d", this.nummer);
	}
	
	/**
	 * liefert den ordentlich formatierten Kontostand
	 * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol €
	 */
	public String getKontostandFormatiert()
	{
		return String.format("%10.2f Euro" , this.getKontostand());
	}
	
	/**
	 * Vergleich von this mit other; Zwei Konten gelten als gleich,
	 * wen sie die gleiche Kontonummer haben
	 * @param other anderes Konto
	 * @return true, wenn beide Konten die gleiche Nummer haben
	 */
	@Override
	public boolean equals(Object other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		if(this.nummer == ((Konto)other).nummer)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
	}

	public int compareTo(Konto other)
	{
		if(other.getKontostand() > this.getKontostand())
			return -1;
		if(other.getKontostand() < this.getKontostand())
			return 1;
		return 0;
	}
}
