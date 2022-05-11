package tsp.algorithms;

import tsp.Graph;
import tsp.tabu.Move;
import tsp.util.Pair;

import java.util.Stack;

public class Tabu2Opt implements Algorithm {

	Graph graph;
	private int counter;

	private static final Integer MAXCOUNTER = 100_000;
	private static final Integer TENURE = 8;

	//przechowujemy atrybtuty ruchu ? gdy mamy n(i,j) to wrzucamy n(j,i)
	//reset -> losowe rozwiazanie - NIE
	//zapamietujemy rozwiazania po drodze, gdy mamy stagnacje to wracamy do poprzednio najlepszego rozwiazania i idziemy w inna strone
	//gdy zadna sciezka nie dala poprawy i wracamy do poczatku, to reset / losowy / koniec
	//kryterium - lepszy od najlepszego znanego o iles %  / gorrszy o iles %
	//kadencja - max elementow na liscie tabu
	Move[] tabuArray = new Move[TENURE];
	Stack<Pair<Integer[], Move>> longTermMemory = new Stack<>();

	private Integer stagnationCounter = 0;

	private final static Integer MAX_STAGNATION_COUNTER = 1000;

	private int tabPointer = 0;

	public Tabu2Opt(Graph graph) {
		this.graph = graph;
		counter = 0;
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
					if (!isOnTabuList(i, j)) {
						Integer[] invertedNewSolution = invert(newSolution, i, j);
						if (graph.pathLength(invertedNewSolution) < graph.pathLength(newSolution)) {
							newMove.setFirst(invertedNewSolution);
							newMove.setSecond(new Move(i, j));

							if (graph.pathLength(invertedNewSolution) < graph.pathLength(maxSolution)) {
								maxSolution = invertedNewSolution;
								bestMove.setFirst(maxSolution);
								bestMove.setSecond(new Move(i, j));
								stagnationCounter = 0;
								longTermMemory.push(bestMove);
							}
						}
					}
				}
			}

			addOnTabuList(newMove.getSecond());
			newSolution = newMove.getFirst();

			counter++;
			stagnationCounter++;

			if (stagnationCounter >= MAX_STAGNATION_COUNTER) {
				stagnationCounter = 0;
				if (longTermMemory.isEmpty())
					return maxSolution;
				newMove = longTermMemory.pop();

				newSolution = newMove.getFirst();
				addOnTabuList(newMove.getSecond());
			}

		}
		while (!stopCriterion());
		return maxSolution;
	}

	public void addOnTabuList(Move move) {
		tabuArray[tabPointer] = move;
		increaseTabPointer();
	}

	public boolean isOnTabuList(int i, int j) {
		for (Move move : tabuArray) {
			if (move != null)
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
		tabPointer = (tabPointer + 1) % TENURE;
	}

	public boolean stopCriterion() {
		return counter >= MAXCOUNTER;
	}

}
