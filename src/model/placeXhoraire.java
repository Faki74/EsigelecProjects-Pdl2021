package model;

/**
 * @author Idrissa
 *
 */
public class placeXhoraire {
	/**
	 * @param place
	 * @param horaire
	 */
	public placeXhoraire(Place place, Horaire horaire) {
		this.place = place;
		this.horaire = horaire;
	}
	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}
	/**
	 * @param place the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
	}
	/**
	 * @return the horaire
	 */
	public Horaire getHoraire() {
		return horaire;
	}
	/**
	 * @param horaire the horaire to set
	 */
	public void setHoraire(Horaire horaire) {
		this.horaire = horaire;
	}
	@Override
	public boolean equals(Object o) {
		boolean isequal=false;
		placeXhoraire pxh=(placeXhoraire)o;
		if(place.getPlcId().equalsIgnoreCase(pxh.getPlace().getPlcId())
				&& horaire.getHrId().equalsIgnoreCase(pxh.getHoraire().getHrId())) {
			isequal=true;
		}
		return isequal;
	}
	private Place place;
	private Horaire horaire;
}
