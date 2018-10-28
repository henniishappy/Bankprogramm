package tests;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import verarbeitung.Student;

class StudentTest {

	@Test
	public void testKeineBescheinigung() {
		Student testperson = new Student();
		Assertions.assertFalse(testperson.hatBescheinigung());
	}
	
	@Test
	public void testBescheinigungAusAktuellemSemester() {
		Student testperson = new Student();
		testperson.bescheinigungEintragen();
		Assertions.assertTrue(testperson.hatBescheinigung());
	}
	
	@Test
	public void testBescheinigungAusLetztemSemester() {
		Student testperson = new Student();
		testperson.manuellEintragen(LocalDate.parse("2018-07-20"));
		Assertions.assertFalse(testperson.hatBescheinigung());
	}
	
	@Test
	public void testBescheinigungAusLetztemJahr() {
		Student testperson = new Student();
		testperson.manuellEintragen(LocalDate.parse("2017-10-27"));
		Assertions.assertFalse(testperson.hatBescheinigung());
	}


}
