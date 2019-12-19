// Reference: https://gist.github.com/gennad/791938
// Reference: https://en.wikipedia.org/wiki/Dijkstra's_algorithm

import java.util.*;

public class Dijkstra {

	public Dijkstra() {

	}
	public static Stack<Station> findShortestPath(ArrayList<Station> stations, ArrayList<Chemin> chemins, Station source, Station target) {
		
		System.out.println(source.getStationName() + " to " + target.getStationName()); 			//Imprime les deux stations pour laquelle nous cherchons le plus court chemin
		
		Integer[] dist = new Integer[stations.size()];			//Contient tous les temps pour se rendre à chacune des objets Station à partir de la Station source. 
		Station[] prev = new Station[stations.size()];			//Contient toutes les objets Station dans l'ordre inverse pour se rendre de la Station source jusqu'à la Station target.
		Station[] parcours = new Station[stations.size()];		//Contient toutes les objets Station en ordre pour ce rendre de la Station source jusqu'à la Station target. Utiliser pour inverser prev et avoir les objets Station dans le bon ordre.
		for(Station i : stations){			
			dist[i.getStationId()] = Integer.MAX_VALUE;				//Nous initialison toute les valeurs dans le tableau dist a l'infinie
			prev[i.getStationId()] = null;			//Nous initialison toute les valeurs dans le tableau prev a null
		}
		
		Comparator<Station> PQComparator = new Comparator<Station>(){			//Nous creons un comparateur
			public int compare(Station a, Station b){
				if(dist[a.getStationId()] > dist[b.getStationId()]){			//Retourn 1 si Station a a un temps plus grand que le temps de la Station b
					return 1;
				}
				else if(dist[a.getStationId()] < dist[b.getStationId()]){			//Retourn -1 si Station a a un temps plus petit que le temps de la Station b
					return -1;
				}
				return 0;			//Retourn 0 si la Station a et la Station on le meme temps
			}
		};
		
		PriorityQueue<Station> Q = new PriorityQueue<Station>(stations.size(), PQComparator);			//Contient tous les objets Station découverte
		dist[source.getStationId()] = 0;			//La premiere station a un temps de 0 par default
		Q.offer(source);			//Nous inserons le noeud de depart dans Q pour commencer notre algorithme
		
		while(!Q.isEmpty()){			//Lorsque Q est vide nous avons trouver notre chemin
			Station next = Q.poll();		//Nous l'element de tete de Q qui est couramment l'element avec le plus petit temps
			if(next.getStationId() == target.getStationId()){			//Si nous somme rendu a notre destination, nous retournons notre tableau du chemin le plus court
				return readPath(prev,target);
			}
			for(Chemin i : next.getArrayChemins()){				//Pour chaque chemin connecter a la station courrante ayant le temps le plus court
				int temps = i.getTemps();
				if(temps == -1){			//Si le temps du chemin est de -1, (Le passager doit marcher), le temps stocker dans temps est 90
					temps = 90;				//Sinon, temps a le temps du chemin
				}
				int minTime = dist[next.getStationId()]+temps;			//Nous trouvons le temps minimal pour ce rendre a la station courante
				if(dist[i.getDestination().getStationId()]==Integer.MAX_VALUE){				//Si le noeud a la fin du chemin n'est pas encore decouvert
					dist[i.getDestination().getStationId()] = minTime;			//Nous mettons a jour le temps de ce noeud dans le tableau dist
					prev[i.getDestination().getStationId()] = next;				//Nous ajoutons le noeud courant a la liste de noeuds pour le chemin
					Q.offer(i.getDestination());			//Nous ajouton ce nouveau noeud a Q
				}
				else if (minTime < dist[i.getDestination().getStationId()]){			//Si nous avons trouver un chemin plus petit
					dist[i.getDestination().getStationId()] = minTime;			////Nous mettons a jour le temps de ce noeud dans le tableau dist avec la nouvelle plus petite valeur trouver
					prev[i.getDestination().getStationId()] = next;				//Nous ajoutons le courant precedent a la liste de noeuds pour le chemin
				}
			}
		}
		return null;			//Nous n'avons pas trouver le target dans le graph
	}
	static Stack<Station> readPath(Station[] path, Station target){			//Cette fonction nous permet de lire le path
		Stack<Station> stack = new Stack<Station>();				//Nous creeons un nouveau Stack
		Station t = target;
		stack.push(target);			//Nous ajoutons le target au stack en premier
		while(t!=null){				//Nous lisons le tableau contenant les Station et empilons les elements du chemins dans le stack
			t=path[t.getStationId()];
			stack.push(t);
		}
		return stack;			//Nous retournons le stack
	}
}
