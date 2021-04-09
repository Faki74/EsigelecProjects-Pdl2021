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
	 */
	public Profil(String pflId, String pflDesc, Place place, Horaire horaire) {
		this.pflId = pflId;
		this.pflDesc = pflDesc;
		this.setCorrespondances(place, horaire);
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
	 * @return the correspondances
	 */
	public ArrayList<placeXhoraire> getCorrespondances() {
		return correspondances;
	}
	/**
	 * @param correspondances the correspondances to set
	 */
	public void setCorrespondances(ArrayList<placeXhoraire> correspondances) {
		this.correspondances = correspondances;
	}
	/**
	 * 
	 * @param place
	 * @param horaire
	 * @return
	 */
	public boolean setCorrespondances(Place place,Horaire horaire) {
		Iterator<placeXhoraire> iter=correspondances.iterator();
		placeXhoraire newpxh=new placeXhoraire(place, horaire);
		while(iter.hasNext()) {
			placeXhoraire pxh=iter.next();
			if(pxh.equals(newpxh)) {
				System.out.println("Cette correspondance existe déjà");
				return false;
			}
		}
		correspondances.add(newpxh);
		return true;
	}
	private String pflId;
	private String pflDesc;
	private ArrayList<placeXhoraire> correspondances=new ArrayList<placeXhoraire>();
	
}
