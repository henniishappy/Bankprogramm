package verarbeitung;

/**
 * Liste aller Waehrungen mit festem Umrechnungskurs zum Euro
 * @author Henriette Sand, s0564285@htw-berlin.de
 */
public enum Waehrung {

	/**
	 * Euro
	 */
	EUR(1.0),
	
	/**
	 * Bulgarische Leva
	 */
	BGN(1.95583),
	
	/**
	 * Litauische Litas
	 */
	LTL(1.95583),
	
	/**
	 * Konvertible Mark
	 */
	KM(3.4528);
	
	private double umrechnungskurs;
	
	private Waehrung(double kurs) {
		this.umrechnungskurs = kurs;
	}
	
	/**
	 * liefert den Umrechnungskurs der Waehrung zum Euro zurück
	 * @return Umrechnungsurs zum Euro
	 */
	public double getKurs() {
		return this.umrechnungskurs;
	}
	
	/**
	 * rechnet einen Eurobetrag in die aktuelle Waehrung um
	 * @param betrag umzurechnender Betrag
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @return umgerechneter Betrag in der aktuellen Waehrung
	 */
	public double euroInWaehrungUmrechnen(double betrag) {
		if (betrag < 0) 
			throw new IllegalArgumentException("Negativer Betrag");
		return betrag * this.umrechnungskurs;
	}
	
	/**
	 * rechnet einen Betrag der aktuellen Waehrung in Euro um
	 * @param betrag umzurechnender Betrag
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @return Eurobetrag
	 */
	public double waehrungInEuroUmrechnen(double betrag) {
		if (betrag < 0)
			throw new IllegalArgumentException("Negativer Betrag");
		return betrag / umrechnungskurs;
	}
	
	/**
	 * rechnet Betraege von einer Waehrung in eine andere um, die beide nicht
	 * Euro sind
	 * @param neu Zielwaehrung
	 * @param betrag umzurechnender Betrag
	 * @throws IllegalArgumentException wenn der betrag negativ ist
	 * @return umgerechneter betrag in neuer Waehrung
	 */
	public double waehrungenUmrechnen(Waehrung neu, double betrag) {
		if (betrag < 0)
			throw new IllegalArgumentException("Negativer Betrag");
		return neu.euroInWaehrungUmrechnen(this.waehrungInEuroUmrechnen(betrag));
	}
	
	
	
}
