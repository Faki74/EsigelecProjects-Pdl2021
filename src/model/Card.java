package model;

public class Card {
	
	/**
	 * @param cardId
	 * @param cardUsrId
	 * @param cardStatus
	 */
	public Card(String cardId, String cardUsrId, int cardStatus) {
		this.cardId = cardId;
		this.cardUsrId = cardUsrId;
		this.cardStatus = cardStatus;
	}
	/**
	 * @return the cardId
	 */
	public String getCardId() {
		return cardId;
	}
	/**
	 * @param cardId the cardId to set
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	/**
	 * @return the cardUsrId
	 */
	public String getCardUsrId() {
		return cardUsrId;
	}
	/**
	 * @param cardUsrId the cardUsrId to set
	 */
	public void setCardUsrId(String cardUsrId) {
		this.cardUsrId = cardUsrId;
	}
	/**
	 * @return the cardStatus
	 * 0 for disabled 1 for enabled
	 */
	public int getCardStatus() {
		return cardStatus;
	}
	/**
	 * @param cardStatus the cardStatus to set
	 */
	public void setCardStatus(int cardStatus) {
		this.cardStatus = cardStatus;
	}
	private String cardId;
	private String cardUsrId;
	private int cardStatus;
}
