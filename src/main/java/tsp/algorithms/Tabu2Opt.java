package tsp.algorithms;

import tsp.Graph;
import tsp.tabu.Move;
import tsp.util.Pair;

public class Tabu2Opt implements Algorithm {

	Graph graph;

	private static final Integer TENURE = 8;

	//przechowujemy atrybtuty ruchu ? gdy mamy n(i,j) to wrzucamy n(j,i)
	//reset -> losowe rozwiazanie - NIE
	//zapamietujemy rozwiazania po drodze, gdy mamy stagnacje to wracamy do poprzednio najlepszego rozwiazania i idziemy w inna strone
	//gdy zadna sciezka nie dala poprawy i wracamy do poczatku, to reset / losowy / koniec
	//kryterium - lepszy od najlepszego znanego o iles %  / gorrszy o iles %
	//kadencja - max elementow na liscie tabu
	Move[] tabuArray = new Move[TENURE];
	private int tabPointer = 0;

	public Tabu2Opt(Graph graph) {
		this.graph = graph;
	}

	@Override
	public Integer[] findSolution() {
		Integer[] curSolution;
		Integer[] newSolution = graph.getCurrentPath();
		Integer[] maxSolution = graph.getCurrentPath();
		Pair<Integer[], Move> bestMove = new Pair<>();
		Pair<Integer[], Move> newMove = new Pair<>();
		do {
			curSolution = newSolution;
			for (int i = 0; i < curSolution.length; i++) {
				for (int j = i + 1; j < curSolution.length; j++) {
					//todo 2 warunki
					if (!isOnTabuList(i, j) && graph.pathLength(invert(newSolution, i, j)) < graph.pathLength(maxSolution)) {
						addOnTabuList(i, j);
						maxSolution = invert(newSolution, i, j);
						bestMove.setFirst(maxSolution);
						bestMove.setSecond(new Move(i, j));
					}
				}
			}
			newSolution = maxSolution;
			newMove = bestMove;
		}
		while (graph.pathLength(newSolution) < graph.pathLength(curSolution));
		return newSolution;
	}

	public void addOnTabuList(int i, int j) {
		Move move = new Move(i, j);
		tabuArray[tabPointer] = move;
		increaseTabPointer();
	}

	public boolean isOnTabuList(int i, int j) {
		for (Move move : tabuArray) {
			if (move.getFrom() == i && move.getTo() == j)
				return true;
		}
		return false;
	}

	public Integer[] invert(Integer[] tab, int from, int to) {
		Integer[] tabPom = new Integer[tab.length];
		for (int i = 0; i < tab.length; i++) {
			if (i < from || i > to) {
				tabPom[i] = tab[i];
			} else {
				tabPom[i] = tab[to - (i - from)];
			}
		}
		return tabPom;
	}

	public void increaseTabPointer() {
		tabPointer += 1 % TENURE;
	}

}
