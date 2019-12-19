// Chemin.java, 2017/11/27 01:20:12

// Metro de paris
// ==========================================================================
// 
// Creator: Mamadou Moustapha Bah
// ==========================================================================

public class Chemin {

	/**
	 * Declaration des variable d'instance
	 */
	private Station destination;
	private int temps;
	private boolean fonctionne;

	/**
	 * Constructeur
	 */
	public Chemin(Station s, int t) {
		destination = s;
		temps = t;
		fonctionne = true;
	}

	/**
	 * Getters and setters pour les variable d'instance
	 */
	public Station getDestination() {
		return destination;
	}

	public int getTemps() {
		return temps;
	}

	public void setDestination(Station s) {
		this.destination = s;
	}

	public void setTemps(int t) {
		this.temps = t;
	}/* getters and setters */

	/**
	 * Cette methode affiche le contenu d'un objet Chemin
	 */
	public boolean getFonctionne() {
		return fonctionne;
	}

	public void setFonctionne(boolean f) {
		fonctionne = f;
	}

	public String toString() {

		return ("destination is: " + destination + " temps qu'il faut: " + temps);
	}/* toString */
}