/**
 * 
 */
package model;

/**
 * @author Idrissa
 *
 */
public class Acces {
	
	/**
	 * @param acsId
	 * @param acsDesc
	 * @param acsPlcId
	 * @param acstypeId
	 */
	public Acces(String acsId, String acsDesc, String acsPlcId, String acsTypeId) {
		this.acsId = acsId;
		this.acsDesc = acsDesc;
		this.acsPlcId = acsPlcId;
		this.acsTypeId=acsTypeId;
	}
	/**
	 * @return the acsId
	 */
	public String getAcsId() {
		return acsId;
	}
	/**
	 * @param acsId the acsId to set
	 */
	public void setAcsId(String acsId) {
		this.acsId = acsId;
	}
	/**
	 * @return the acsDesc
	 */
	public String getAcsDesc() {
		return acsDesc;
	}
	/**
	 * @param acsDesc the acsDesc to set
	 */
	public void setAcsDesc(String acsDesc) {
		this.acsDesc = acsDesc;
	}
	/**
	 * @return the acsPlcId
	 */
	public String getAcsPlcId() {
		return acsPlcId;
	}
	/**
	 * @param acsPlcId the acsPlcId to set
	 */
	public void setAcsPlcId(String acsPlcId) {
		this.acsPlcId = acsPlcId;
	}
	/**
	 * @return acsTypeId
	 */
	public String getAcsTypeId() {
		return acsTypeId;
	}
	/**
	 * @param a to set
	 */
	public void setAcsType(String acsTypeId) {
		this.acsTypeId = acsTypeId;
	}
	private String acsId;
	private String acsDesc;
	private String acsPlcId;
	private String acsTypeId;
}
