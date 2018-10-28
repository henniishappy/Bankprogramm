package programme;
import java.time.LocalDate;

import verarbeitung.GesperrtException;
import verarbeitung.Girokonto;
import verarbeitung.Kunde;
import verarbeitung.Sparbuch;
import verarbeitung.Student;

/**
 * Testprogramm für Konten
 * @author Doro
 * @author Henriette Sand, s0564285@htw-berlin.de
 *
 */
public class KontenTest {

	/**
	 * Testprogramm für Konten
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args) {
		Kunde ich = new Student("Henriette", "Sand", "Berlin", LocalDate.parse("1997-01-16"), "HTW", "Angewandte Informatik");

		Girokonto meinGiro = new Girokonto(ich, 1234, 1000.0);
		meinGiro.einzahlen(50);
		System.out.println(meinGiro);
		
		Sparbuch meinSpar = new Sparbuch(ich, 9876);
		meinSpar.einzahlen(50);
		try
		{
			boolean hatGeklappt = meinSpar.abheben(70);
			System.out.println("Abhebung hat geklappt: " + hatGeklappt);
			System.out.println(meinSpar);
		}
		catch (GesperrtException e)
		{
			System.out.println("Zugriff auf gesperrtes Konto - Polizei rufen!");
		}
	}

}
