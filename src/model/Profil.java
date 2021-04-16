package model;
import java.util.*;
/**
 * @author Idrissa
 *
 */
public class Profil {
	/**
	 * @param pflId
	 * @param pflDesc
	 * @param pflPlace
	 * @param pflHoraire
	 */
	public Profil(String pflId, String pflDesc, Place pflPlace, Horaire pflHoraire) {
		this.pflId = pflId;
		this.pflDesc = pflDesc;
		this.pflPlace = pflPlace;
		this.pflHoraire = pflHoraire;
	}
	/**
	 * @return the pflId
	 */
	public String getPflId() {
		return pflId;
	}
	/**
	 * @param pflId the pflId to set
	 */
	public void setPflId(String pflId) {
		this.pflId = pflId;
	}
	/**
	 * @return the pflDesc
	 */
	public String getPflDesc() {
		return pflDesc;
	}
	/**
	 * @param pflDesc the pflDesc to set
	 */
	public void setPflDesc(String pflDesc) {
		this.pflDesc = pflDesc;
	}
	/**
	 * @return the pflPlace
	 */
	public Place getPflPlace() {
		return pflPlace;
	}
	/**
	 * @param pflPlace the pflPlace to set
	 */
	public void setPflPlace(Place pflPlace) {
		this.pflPlace = pflPlace;
	}
	/**
	 * @return the pflHoraire
	 */
	public Horaire getPflHoraire() {
		return pflHoraire;
	}
	/**
	 * @param pflHoraire the pflHoraire to set
	 */
	public void setPflHoraire(Horaire pflHoraire) {
		this.pflHoraire = pflHoraire;
	}
	private String pflId;
	private String pflDesc;
	private Place pflPlace;
	private Horaire pflHoraire;
	
}
