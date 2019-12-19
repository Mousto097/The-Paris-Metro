// Chemin.java, 2017/11/27 01:20:12

// Metro de paris
// ==========================================================================
// 
// Author: Mamadou Moustapha Bah
//
// ==========================================================================

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.io.IOException;
import java.util.*;

public class Metro {

	/**
	 * Declaration des variable d'instance
	 */
	private static ArrayList<Station> arrayStations; // Je stocke toutes mes stations dans ce tableau
	private Station depart;
	private Station destination;
	private int temps;

	/**
	 * Create a Graph from file.
	 * 
	 * Modified by Mamadou Moustapha Bah on November 27th, 2017.
	 */
	public Metro(String fileName) throws Exception, IOException {
		arrayStations = new ArrayList<Station>();
		readMetro(fileName);
	}

	/**
	 * Read a list of edges from file.
	 * 
	 * Modified by Mamadou Moustapha Bah on November 27th, 2017.
	 */
	public void readMetro(String fileName) throws Exception, IOException {

		BufferedReader graphFile = new BufferedReader(new FileReader(fileName));

		// lis les donnees et ajoute dans mon graphe
		String line;
		int countLine = 1;

		while ((line = graphFile.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);

			String numberOfStations;
			String numberOfEdges;

			String stationId;
			String stationName;

			String source;
			String dest;
			Integer weight;

			if (countLine == 1) {
				numberOfStations = st.nextToken();
				numberOfEdges = st.nextToken();
				System.out.println("");
				System.out.println(
						"The number of stations is: " + numberOfStations + " The number of edges is: " + numberOfEdges);
			}
			// -----------Creation des stations--------------------
			if (countLine > 1 && countLine <= 377) {

				stationId = st.nextToken();
				stationName = st.nextToken();
				while (st.hasMoreTokens()) {
					stationName = stationName + " " + st.nextToken();
				}

				// --------------Je creer des stations et ajoute dans ma liste
				// arrayStation------

				Station station = new Station(Integer.parseInt(stationId), stationName);
				arrayStations.add(station);

			}
			// -----------Creation des chemins-------------------
			while (st.countTokens() == 3 && countLine >= 379) {

				source = st.nextToken();
				dest = st.nextToken();
				weight = new Integer(st.nextToken());

				depart = arrayStations.get(Integer.parseInt(source));
				destination = arrayStations.get(Integer.parseInt(dest));
				temps = weight;

				// ----------Je creer le chemin------------------
				depart.creerChemin(destination, temps);
			}
			countLine++;
		}

	} /* read */

	/**
	 * Cette methode me permet d'identifier toutes les stations appartenant à la
	 * même ligne qu’une station spécifiée
	 * 
	 * @throws Exception
	 */
	public ArrayList<Station> trouveLigne(int id) throws Exception {

		Station station = findStation(id); // Je retrouve la station
		Station extremite = trouveExtremite(station); // Je trouve une extremite
		ArrayList<Station> arrayMaLigne = new ArrayList<Station>(); // creation de la liste qui contiendra les stations
																	// de la ligne
		Boolean reachDestination = false;

		arrayMaLigne.add(extremite);

		while (!reachDestination) { // Tant que je n'arrive pas a l'autre bout , je continue
			for (Chemin chemin : extremite.getArrayChemins()) {
				if (chemin.getTemps() != -1 && !arrayMaLigne.contains(chemin.getDestination())) { // si je trouve un
																									// chemin positif

					extremite = chemin.getDestination(); // je vais a la prochaine station et je l'ajoute dans la liste
					arrayMaLigne.add(extremite);
					chemin.setFonctionne(false);

					if (extremite.getNonTransferable().size() == 1) {
						reachDestination = true;
					}
				}
			}
		}
		return arrayMaLigne;
	}/* trouveLigne */

	/**
	 * Cette methode me permettant de trouver une des extremites
	 * 
	 * creator: Mamadou Moustapha Bah
	 */
	private Station trouveExtremite(Station s) {
		ArrayList<Chemin> nonTransfer = s.getNonTransferable();
		while (nonTransfer.size() != 1) { // une station est une extremite que si elle a un seul chemin positif qui mene
											// vers lui
			for (Chemin chemin : nonTransfer) {
				s = chemin.getDestination(); // J'ai trouve une extremite
				nonTransfer = s.getNonTransferable();
			}
		}
		return s;
	}/* trouveExtremite */

	/**
	 * Helper method: cette methode verifie si la station exite, si Oui, elle
	 * retourne cette station sinon elle lance une exception
	 * 
	 * @throws Exception creator: Mamadou Moustapha Bah
	 */
	private static Station findStation(int id) throws Exception {
		// Je verifie si la station existe
		if (id < 0 || id >= 376) {
			throw new Exception("Ce nombre n'est pas valide. 0 <= N1 <= 376");
		}
		return arrayStations.get(id);
	}/* find Station */

	/**
	 * Helper method: cette methode parcours le graphe et affiche toutes les
	 * stations, leurs chemins et le temps necessaire.
	 * 
	 * creator: Mamadou Moustapha Bah
	 */
	public void printGraphe() {

		for (int i = 0; i < arrayStations.size(); i++) {
			Station source = arrayStations.get(i);
			Station destination;
			int temps;
			for (int j = 0; j < source.getArrayChemins().size(); j++) {
				destination = source.getArrayChemins().get(j).getDestination();
				temps = source.getArrayChemins().get(j).getTemps();
				System.out.println("De la station [==> " + source.getStationName() + " <==] à la station [==> "
						+ destination.getStationName() + " <==] il faut [==> " + temps + " sec <==]");
			}
		}
	} /* printGraph */

	/**
	 * Helper method: cette methode met tout mes objets chemins dans un arrayList.
	 * creator: Mamadou Moustapha Bah
	 */
	public ArrayList<Chemin> toArrayChemin() {

		ArrayList<Chemin> cheminsArray = new ArrayList<Chemin>();
		for (int i = 0; i < arrayStations.size(); i++) {
			Station source = arrayStations.get(i);
			for (int j = 0; j < source.getArrayChemins().size(); j++) {
				cheminsArray.add(source.getArrayChemins().get(j));
			}
		}
		return cheminsArray;
	}/* toArrayChemin */

	/**
	 * Genere un graphe et les affiche les stations de depart , d'arriver et le
	 * temps requis si on ne donne que le fichie metro.txt comme argument Montre la
	 * liste des stations de la ligne d'une stations specifiee si on donne comme
	 * argument "metro.txt" et le "le numero de la station" Montre la liste du plus
	 * court chemin si on fournit "metro.txt", "numero de la station de depart" et
	 * "numero de la station d'arrive" comme argument Montre la liste du plus court
	 * chemin si une station est en panne "metro.txt" "depart" "arrive" "station en
	 * panne"
	 */
	public static void main(String[] argv) {

		if (argv.length < 1) {
			System.err.println("Usage: java Metro fileName N1 ou/et N2 ou/et N3");
			System.out.println("");
			System.out.println("");
			System.out.println(
					"<<-----------------------------------------Usage------------------------------------------------------>>");
			System.out.println(
					"1) Pour construire le graphe en lisant le fichier, veuillez entrer dans la ligne de commande: ");
			System.out.println("java Metro metro.txt");
			System.out.println("");
			System.out.println(
					"2) Pour Identifier toutes les stations appartenant à la même ligne qu’une station spécifiée, veuillez entrer:");
			System.out.println(" java Metro metro.txt numeroDeLaStation");
			System.out.println("");
			System.out.println("3) Pour Trouver le trajet le plus rapide entre deux stations, veuillez entrer:");
			System.out.println(" java Metro metro.txt numeroDeLaStationDepart numeroDeLaStationD'arrive ");
			System.out.println("");
			System.out.println(
					"4) Pour trouver le trajet le plus rapide entre deux stations lorsque l’une des lignes est hors-fonction, veuillez entrer:");
			System.out.println(
					" java Metro metro.txt numeroDeLaStationDepart numeroDeLaStationD'arrive numeroDeLaStationALextremiteD'uneLigneEnPanne");
			System.out.println("");
			System.out.println("");
			System.exit(-1);
		}
		if (argv.length == 1) {
			try {
				Metro metro = new Metro(argv[0]);
				metro.printGraphe();
			} catch (Exception except) {
				System.err.println(except);
				except.printStackTrace();
			}
		}
		if (argv.length == 2) {
			try {
				Metro metro = new Metro(argv[0]);
				// System.out.println( metro.trouveLigne( Integer.parseInt(argv[1])) );
				ArrayList<Station> liste = new ArrayList();
				System.out.println("");
				System.out.println("");
				System.out.println(
						"----Voici la liste des stations se trouvant sur la ligne de la station specifiée----");
				System.out.println(
						"<<-----------------------------Début de la liste des stations--------------------------->>");
				liste = metro.trouveLigne(Integer.parseInt(argv[1]));
				for (int i = 0; i < liste.size(); i++) {
					System.out.println(liste.get(i).toString());
				}
				System.out.println("<<------------------------Fin de la liste des stations------------------------->>");
				System.out.println("");
			} catch (Exception except) {
				System.err.println(except);
				except.printStackTrace();
			}
		}
		if (argv.length == 3) {
			try {
				Metro metro = new Metro(argv[0]);
				ArrayList<Chemin> cheminsArray = new ArrayList<Chemin>();

				cheminsArray = metro.toArrayChemin();
				int stationId1 = Integer.parseInt(argv[1]);
				int stationId2 = Integer.parseInt(argv[2]);
				Stack<Station> plusCourtChemin = Dijkstra.findShortestPath(arrayStations, cheminsArray,
						findStation(stationId1), findStation(stationId2));

				System.out.println("-----le trajet le plus rapide de " + findStation(stationId1).getStationName()
						+ " a " + findStation(stationId2).getStationName() + "------");
				System.out.println("");
				System.out.println(
						"<<-----------------------------Début de la liste des stations--------------------------->>");
				while (!plusCourtChemin.isEmpty()) {
					Station tmp = plusCourtChemin.pop();
					if (tmp != null) {
						System.out.println(tmp.getStationName());
					}
				}
				System.out.println("");
				System.out.println(
						"<<-----------------------------Fin de la liste des stations--------------------------->>");

			} catch (Exception except) {
				System.err.println(except);
				except.printStackTrace();
			}
		}
		if (argv.length == 4) {
			try {
				Metro metro = new Metro(argv[0]);
				ArrayList<Chemin> cheminsArray = new ArrayList<Chemin>();
				cheminsArray = metro.toArrayChemin();
				int stationId1 = Integer.parseInt(argv[1]);
				int stationId2 = Integer.parseInt(argv[2]);

				Station station1 = findStation(Integer.parseInt(argv[1]));
				Station station2 = findStation(Integer.parseInt(argv[2]));
				Station station3 = findStation(Integer.parseInt(argv[3]));

				ArrayList<Station> liste = new ArrayList<Station>();
				liste = metro.trouveLigne(Integer.parseInt(argv[3]));

				Stack<Station> plusCourtChemin = Dijkstra.findShortestPath(arrayStations, cheminsArray,
						findStation(stationId1), findStation(stationId2));
				System.out.println(
						"------le trajet le plus rapide entre deux stations lorsque l’une des lignes est hors-fonction--------");
				System.out.println("");
				System.out.println("La station de départ est: " + station1.getStationName());
				System.out.println("La station de d'arrivée est: " + station2.getStationName());
				System.out.println("la station à l’extrémité de la ligne en panne est: " + station3.getStationName());
				System.out.println("");
				System.out.println("<<-----------------------------liste des stations--------------------------->>");
				System.out.println("");

				while (!plusCourtChemin.isEmpty()) {
					Station tmp = plusCourtChemin.pop();
					if (tmp != null) {
						System.out.println(tmp.getStationName());
					}
				}

				System.out.println("");
				System.out.println(
						"<<-----------------------------Fin de la liste des stations--------------------------->>");
			} catch (Exception except) {
				System.err.println(except);
				except.printStackTrace();
			}
		}
	}/* main */
}