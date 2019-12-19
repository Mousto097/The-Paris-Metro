// Chemin.java, 2017/11/27 01:20:12

// Metro de paris
// ==========================================================================
// 
// Creator: Mamadou Moustapha Bah
// 
// ==========================================================================

import java.util.ArrayList;

public class Station {

	/**
	 * Declaration des variable d'instance
	 */
	private int stationId;
	private String stationName;
	private ArrayList<Chemin> arrayChemins;

	/**
	 * Constructeur
	 */
	public Station(int id, String name) {

		stationId = id;
		stationName = name;
		arrayChemins = new ArrayList<Chemin>();
	}

	/**
	 * Getters and setters pour les variable d'instance
	 */
	public int getStationId() {
		return stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationId(int id) {
		stationId = id;
	}

	public void setStationName(String name) {
		stationName = name;
	}

	public ArrayList<Chemin> getArrayChemins() {
		return arrayChemins;
	}/* getters and setters */

	/**
	 * Cette methode me permet de lier une station a tous ses chemins
	 */
	public void creerChemin(Station destination, int temps) {
		Chemin chemin = new Chemin(destination, temps);
		arrayChemins.add(chemin);
	}/* creerChemin */

	/**
	 * Helper methode: Cette methode me permet de recuperer que les chemins positifs
	 */
	public ArrayList<Chemin> getNonTransferable() {
		ArrayList<Chemin> list = new ArrayList();

		for (Chemin chemin : arrayChemins) {
			if (chemin.getTemps() != -1) {
				list.add(chemin);
			}
		}
		return list;
	}/* getNonTransferable */

	/**
	 * Cette methode affiche le contenu d'un objet Station
	 */
	public String toString() {

		return ("StationId: " + stationId + " stationName: " + stationName);
	}/* toString */
}