/**
 * 
 */
package model;

/**
 * @author Idrissa
 *
 */
public class User {
	/**
	 * @param usrId
	 * @param usrNom
	 * @param usrPrenom
	 * @param usrBirthday
	 * @param usrFonction
	 */
	public User(String usrId, String usrNom, String usrPrenom, String usrBirthday, String usrFonction) {
		this.usrId = usrId;
		this.usrNom = usrNom;
		this.usrPrenom = usrPrenom;
		this.usrBirthday = usrBirthday;
		this.usrFonction = usrFonction;
	}
	
	/**
	 * @return the usrId
	 */
	public String getUsrId() {
		return usrId;
	}
	/**
	 * @param usrId the usrId to set
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	/**
	 * @return the usrNom
	 */
	public String getUsrNom() {
		return usrNom;
	}
	/**
	 * @param usrNom the usrNom to set
	 */
	public void setUsrNom(String usrNom) {
		this.usrNom = usrNom;
	}
	/**
	 * @return the usrPrenom
	 */
	public String getUsrPrenom() {
		return usrPrenom;
	}
	/**
	 * @param usrPrenom the usrPrenom to set
	 */
	public void setUsrPrenom(String usrPrenom) {
		this.usrPrenom = usrPrenom;
	}
	/**
	 * @return the usrBirthday
	 */
	public String getUsrBirthday() {
		return usrBirthday;
	}
	/**
	 * @param usrBirthday the usrBirthday to set
	 */
	public void setUsrBirthday(String usrBirthday) {
		this.usrBirthday = usrBirthday;
	}
	/**
	 * @return the usrFonction
	 */
	public String getUsrFonction() {
		return usrFonction;
	}
	/**
	 * @param usrFonction the usrFonction to set
	 */
	public void setUsrFonction(String usrFonction) {
		this.usrFonction = usrFonction;
	}
	/**
	 * @return the usrCard
	 */
	public Card getUsrCard() {
		return usrCard;
	}
	/**
	 * @param usrCard the usrCard to associate
	 */
	public void setUsrCard(Card usrCard) {
		this.usrCard = usrCard;
	}
	/**
	 * @param aCard the usrCard to disassociate
	 */
	public void resetUsrCard() {
		this.setUsrCard(null);
	}
	private String usrId;
	private String usrNom;
	private String usrPrenom;
	private String usrBirthday;
	private String usrFonction;
	private Card usrCard;
}
