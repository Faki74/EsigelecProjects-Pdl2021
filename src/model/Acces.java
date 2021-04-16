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
	 * @param acstype
	 */
	public Acces(String acsId, String acsDesc, String acsPlcId, TypeAcces acstype) {
		this.acsId = acsId;
		this.acsDesc = acsDesc;
		this.acsPlcId = acsPlcId;
		this.acstype = acstype;
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
	 * @return the acstype
	 */
	public TypeAcces getAcstype() {
		return acstype;
	}
	/**
	 * @param acstype the acstype to set
	 */
	public void setAcstype(TypeAcces acstype) {
		this.acstype = acstype;
	}
	private String acsId;
	private String acsDesc;
	private String acsPlcId;
	private TypeAcces acstype=null;
}
