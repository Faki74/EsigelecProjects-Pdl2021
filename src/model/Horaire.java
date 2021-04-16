package model;
public class Horaire {
	public Horaire(String hrId,String hrOpen,String hrClose) {
		this.hrId=hrId;this.hrOpen=hrOpen;this.hrClose=hrClose;
	}
	/**
	 * @return the hrId
	 */
	public String getHrId() {
		return hrId;
	}
	/**
	 * @param hrId the hrId to set
	 */
	public void setHrId(String hrId) {
		this.hrId = hrId;
	}
	/**
	 * @return the hrOpen
	 */
	public String getHrOpen() {
		return hrOpen;
	}
	/**
	 * @param hrOpen the hrOpen to set
	 */
	public void setHrOpen(String hrOpen) {
		this.hrOpen = hrOpen;
	}
	/**
	 * @return the hrClose
	 */
	public String getHrClose() {
		return hrClose;
	}
	/**
	 * @param hrClose the hrClose to set
	 */
	public void setHrClose(String hrClose) {
		this.hrClose = hrClose;
	}
	private String hrId;
	private String hrOpen;
	private String hrClose;
}
