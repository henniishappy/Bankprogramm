package verarbeitung;

/**
 * Liste aller angebotenen Kontoarten
 * @author Doro
 *
 */
public enum Kontoart {
	/**
	 * ein Girokonto
	 */
	GIROKONTO("ganz hoher Dispo"), 
	/**
	 * ein Sparbuch
	 */
	SPARBUCH("ganz viele Zinsen"), 
	/**
	 * ein Festgeldkonto
	 */
    FESTGELDKONTO("nur Dummy");
	
	private Kontoart(String text)
	{
		this.werbebotschaft = text;
	}
	
	private String werbebotschaft;

	/**
	 * die Werbung dieser Kontoart
	 * @return Werbebotschaft
	 */
	public String getWerbebotschaft() {
		return werbebotschaft;
	}
	
	
}
