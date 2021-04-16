package model;
import java.util.ArrayList;
/**
 * @author Idrissa
 *
 */
public class Place {
	
	/**
	 * @param plcId
	 * @param plcLocation
	 * @param plcNbAcces
	 * @param horaire un seul horaire pas la liste
	 * @param plcStatus
	 */
	public Place(String plcId, String plcLocation, int plcNbAcces,Horaire horaire, int plcStatus) {
		this.plcId = plcId;
		this.plcLocation = plcLocation;
		this.plcNbAcces = plcNbAcces;
		this.plcHoraire = horaire;
		this.plcStatus = plcStatus;
	}
	
	/**
	 * @return the plcId
	 */
	public String getPlcId() {
		return plcId;
	}
	/**
	 * @param plcId the plcId to set
	 */
	public void setPlcId(String plcId) {
		this.plcId = plcId;
	}
	/**
	 * @return the plcLocation
	 */
	public String getPlcLocation() {
		return plcLocation;
	}
	/**
	 * @param plcLocation the plcLocation to set
	 */
	public void setPlcLocation(String plcLocation) {
		this.plcLocation = plcLocation;
	}
	/**
	 * @return the plcNbAcces
	 */
	public int getPlcNbAcces() {
		return plcNbAcces;
	}
	/**
	 * @param plcNbAcces the plcNbAcces to set
	 */
	public void setPlcNbAcces(int plcNbAcces) {
		this.plcNbAcces = plcNbAcces;
	}
	/**
	 * @return the plcHoraire
	 */
	public Horaire getPlcHoraire() {
		return plcHoraire;
	}

	/**
	 * @param plcHoraire the plcHoraire to set
	 */
	public void setPlcHoraire(Horaire plcHoraire) {
		this.plcHoraire = plcHoraire;
	}

	/**
	 * @return the plcStatus
	 */
	public int getPlcStatus() {
		return plcStatus;
	}
	/**
	 * @param plcStatus the plcStatus to set
	 */
	public void setPlcStatus(int plcStatus) {
		this.plcStatus = plcStatus;
	}
	/**
	 * @return the plcAcces
	 */
	public ArrayList<Acces> getPlcAcces() {
		return plcAcces;
	}
	
	/**
	 * @param plcAcces the plcAcces to set
	 */
	public void setPlcAcces(ArrayList<Acces> plcAcces) {
		this.plcAcces = plcAcces;
	}
	/**
	 * @param acces to add
	 */
	public String setPlcAcces(Acces acces) {
		 //Verifier que l'accès n'est pas déjà ajouté
		if(plcAcces.size()<=plcNbAcces) {
			this.plcAcces.add(acces);
			return("Nouvel accès associé");
		}
		else
			return("Nombre d'accès maximal atteint");
	}
	/**
	 * @param plcAcces the plcAcces to set
	 */
	public void resetPlcAcces() {
		this.plcAcces = null;
		System.out.println("Liste des accès réinitialisée");
	}
	/**
	 * @param acces to add
	 */
	public String resetPlcAcces(Acces acces) {
		if(plcAcces.contains(acces)) {
			plcAcces.remove(acces);
			return("Accès associé supprimé");
		}
		else
			return("Cet accès n'est pas associé à ce lieu");
	}
	private String plcId;
	private String plcLocation;
	private int plcNbAcces;
	private Horaire plcHoraire;
	private int plcStatus;
	private ArrayList<Acces> plcAcces =new ArrayList<Acces>();
}
