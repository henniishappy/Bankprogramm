package programme;
import java.time.LocalDate;

import verarbeitung.GesperrtException;
import verarbeitung.Girokonto;
import verarbeitung.Kunde;
import verarbeitung.Sparbuch;
import verarbeitung.Waehrung;


/**
 * @author Henriette Sand, s0564285@htw-berlin.de
 */
public class Waehrungstest {

	/**
	 * Testprogramm für den Waehrungswechsel bei Bankkonten
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args) {
		Kunde henni = new Kunde("Henriette", "Sand", "Berlin", LocalDate.parse("1997-01-16"));
		
		Girokonto hsGiro = new Girokonto(henni, 564285, 1000.0);
		hsGiro.einzahlen(100, Waehrung.KM); //entspricht 28,96 Euro
		System.out.println("Einzahlung mit anderer Waehrung: ");
		System.out.println(hsGiro);
		
		hsGiro.waehrungswechsel(Waehrung.KM); //erwarteter Kontostand: 100,0 , erwarteter Dispo: 3452,8
		System.out.println("Waehrungswechsel von EUR zu KM: ");
		System.out.println(hsGiro);
		
		
		Sparbuch hsSpar = new Sparbuch(henni, 564285);
		
		hsSpar.einzahlen(100, Waehrung.BGN); //entspricht 51.13 Euro
		System.out.println("Einzahlung mit anderer Waehrung: ");
		System.out.println(hsSpar);
		
		hsSpar.waehrungswechsel(Waehrung.BGN);
		System.out.println("Waehrungswechsel von EUR zu BGN: ");
		System.out.println(hsSpar);
		
		try {
		hsSpar.abheben(50.0, Waehrung.LTL); //erwarteter Kontostand: 50.0
											// BGN und LTL haben selben Umrechnungskurs zum Euro
		} catch (GesperrtException e) {
			System.out.println("Da ist etwas schief gelaufen. Das Konto ist gesperrt");
		}
		System.out.println("Abhebung in LTL: "); 
		System.out.println(hsSpar);
		
		

	}

}
