/**
 * 
 */
package model;

/**
 * @author Idrissa
 *
 */
public class TypeAcces {
	
	/**
	 * @param acstypeId
	 * @param acstypeDesc
	 */
	public TypeAcces(int acstypeId, String acstypeDesc) {
		this.acstypeId = acstypeId;
		this.acstypeDesc = acstypeDesc;
	}
	
	/**
	 * @return the acstypeId
	 */
	public int getAcstypeId() {
		return acstypeId;
	}
	/**
	 * @param acstypeId the acstypeId to set
	 */
	public void setAcstypeId(int acstypeId) {
		this.acstypeId = acstypeId;
	}
	/**
	 * @return the acstypeDesc
	 */
	public String getAcstypeDesc() {
		return acstypeDesc;
	}
	/**
	 * @param acstypeDesc the acstypeDesc to set
	 */
	public void setAcstypeDesc(String acstypeDesc) {
		this.acstypeDesc = acstypeDesc;
	}

	private int acstypeId;
	private String acstypeDesc;
}
