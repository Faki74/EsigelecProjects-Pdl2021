package model;

/**
 * @author Idrissa
 *
 */
public class profilXuser {
	
	/**
	 * @param usrpflId
	 * @param profil
	 * @param user
	 */
	public profilXuser(int usrpflId, Profil profil, User user) {
		this.usrpflId = usrpflId;
		this.profil = profil;
		this.user = user;
	}
	/**
	 * @return the profil
	 */
	public Profil getProfil() {
		return profil;
	}
	/**
	 * @param profil the profil to set
	 */
	public void setProfil(Profil profil) {
		this.profil = profil;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public boolean equals(Object o) {
		boolean isequal=false;
		profilXuser pxu=(profilXuser)o;
		if(profil.getPflId().equalsIgnoreCase(pxu.getProfil().getPflId())
				&& user.getUsrId().equalsIgnoreCase(pxu.getUser().getUsrId())) {
			isequal=true;
		}
		return isequal;
	}
	/**
	 * @return the usrpflId
	 */
	public int getUsrpflId() {
		return usrpflId;
	}
	/**
	 * @param usrpflId the usrpflId to set
	 */
	public void setUsrpflId(int usrpflId) {
		this.usrpflId = usrpflId;
	}
	private int usrpflId;
	private Profil profil;
	private User user;
}
