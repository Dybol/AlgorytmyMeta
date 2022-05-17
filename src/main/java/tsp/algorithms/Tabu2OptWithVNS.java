package tsp.algorithms;

import tsp.Graph;
import tsp.tabu.Move;
import tsp.util.Pair;

import java.util.Stack;

public class Tabu2OptWithVNS implements Algorithm {

	private final Graph graph;
	private int counter;
	private Integer neighborhoodType = 1;

	private Integer MAX_COUNTER = 100000;
	private Integer TENURE = 7;

	//przechowujemy atrybtuty ruchu ? gdy mamy n(i,j) to wrzucamy n(j,i)
	//reset -> losowe rozwiazanie - NIE
	//zapamietujemy rozwiazania po drodze, gdy mamy stagnacje to wracamy do poprzednio najlepszego rozwiazania i idziemy w inna strone
	//gdy zadna sciezka nie dala poprawy i wracamy do poczatku, to reset / losowy / koniec
	//kryterium - lepszy od najlepszego znanego o iles %  / gorrszy o iles %
	//kadencja - max elementow na liscie tabu
	private Move[] tabuArray = new Move[TENURE];
	private final Stack<Pair<Integer[], Move>> longTermMemory = new Stack<>();

	private Integer stagnationCounter = 0;

	private int MAX_STAGNATION_COUNTER = 1000;
	private double STAGNATION_MULTIPLIER = 0.0;
	private int STAGNATION_CONSTANT = 1000;
	
	private boolean isSCLinear = false;
	
	private int tabPointer = 0;

	public Tabu2OptWithVNS(Graph graph, Integer maxCounter, Integer tenure, Integer stagnationConstant) {
		this.graph = graph;
		this.MAX_COUNTER = maxCounter;
		this.TENURE = tenure;
		this.STAGNATION_CONSTANT = stagnationConstant;
		this.STAGNATION_MULTIPLIER = 0.0;
		this.isSCLinear = false;
		this.MAX_STAGNATION_COUNTER = stagnationConstant;
		tabuArray = new Move[TENURE];
	}
	
	public Tabu2OptWithVNS(Graph graph, Integer maxCounter, Integer tenure, Double stagnationMultiplier, Integer stagnationConstant) {
		this.graph = graph;
		this.MAX_COUNTER = maxCounter;
		this.TENURE = tenure;
		this.STAGNATION_CONSTANT = stagnationConstant;
		this.STAGNATION_MULTIPLIER = stagnationMultiplier;
		this.isSCLinear = true;
		this.MAX_STAGNATION_COUNTER = (int)Math.floor(stagnationCounter*stagnationMultiplier + stagnationConstant);
		tabuArray = new Move[TENURE];
	}

	public Tabu2OptWithVNS(Graph graph) {
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
		boolean noMovesFoundYet;
		//gdy true to znaleźliśmy nowe najlepsze rozwiązanie i trzeba wrzucić
		//na listę długoterminową ruch, który wykonamy z tego najlepszego punktu
		//aby nie wpaść w cykl
		int timeToSave = -1;
		//jeśli w następnej iteracji znowu znajdujemy najlepsze rozwiązanie to mamy problem
		//do rozwiązania przy zapisie ruchów
		boolean bestStreak = false;
		Integer[] previousMaxSolution = new Integer[newSolution.length];
		do {
			if(timeToSave == 0) {
				bestMove.setSecond(newMove.getSecond());
				longTermMemory.push(bestMove);
			}
			if(timeToSave > -1)
				timeToSave--;
			if (neighborhoodType == 1) {
				noMovesFoundYet = true;
				curSolution = newSolution;
				for (int i = 0; i < curSolution.length; i++) {
					for (int j = i + 1; j < curSolution.length; j++) {
						if (!isOnTabuList(i, j)) {
							Integer[] invertedNewSolution = invert(newSolution, i, j);
							if(noMovesFoundYet) {
								newSolution = invertedNewSolution;
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(i, j));
								noMovesFoundYet = false;
								if (graph.pathLength(invertedNewSolution) < graph.pathLength(maxSolution)) {
									if(timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									stagnationCounter = 0;
								}
							}
							else if (graph.pathLength(invertedNewSolution) < graph.pathLength(newSolution)) {
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(i, j));
	
								if (graph.pathLength(invertedNewSolution) < graph.pathLength(maxSolution)) {
									if(timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									stagnationCounter = 0;
								}
							}
						}
					}
				}
			}
			else if (neighborhoodType == 2) {
				noMovesFoundYet = true;
				curSolution = newSolution;
				for (int i = 0; i < curSolution.length; i++) {
					for (int j = i + 1; j < curSolution.length; j++) {
						if (!isOnTabuList(i, j)) {
							Integer[] invertedNewSolution = swap(newSolution, i, j);
							if(noMovesFoundYet) {
								newSolution = invertedNewSolution;
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(i, j));
								noMovesFoundYet = false;
								if (graph.pathLength(invertedNewSolution) < graph.pathLength(maxSolution)) {
									if(timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									stagnationCounter = 0;
								}
							}
							else if (graph.pathLength(invertedNewSolution) < graph.pathLength(newSolution)) {
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(i, j));
	
								if (graph.pathLength(invertedNewSolution) < graph.pathLength(maxSolution)) {
									if(timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									stagnationCounter = 0;
								}
							}
						}
					}
				}
			}
			else if (neighborhoodType == 3) {
				noMovesFoundYet = true;
				curSolution = newSolution;
				for (int i = 0; i < curSolution.length; i++) {
					for (int j = 0; j < curSolution.length; j++) {
						if (!isOnTabuList(i, j)) {
							Integer[] invertedNewSolution = insert(newSolution, i, j);
							if(noMovesFoundYet) {
								newSolution = invertedNewSolution;
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(j, i));
								noMovesFoundYet = false;
								if (graph.pathLength(invertedNewSolution) < graph.pathLength(maxSolution)) {
									if(timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									stagnationCounter = 0;
								}
							}
							else if (graph.pathLength(invertedNewSolution) < graph.pathLength(newSolution)) {
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(j, i));
	
								if (graph.pathLength(invertedNewSolution) < graph.pathLength(maxSolution)) {
									if(timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									stagnationCounter = 0;
								}
							}
						}
					}
				}
			}
			addOnTabuList(newMove.getSecond());
			newSolution = newMove.getFirst();

			counter++;
			stagnationCounter++;
			
			if(bestStreak == true) {
				if(neighborhoodType == 3)
					longTermMemory.push(new Pair<Integer[], Move>(previousMaxSolution, new Move(newMove.getSecond().getTo(), newMove.getSecond().getFrom())));
				else
					longTermMemory.push(new Pair<Integer[], Move>(previousMaxSolution, newMove.getSecond()));
				bestStreak = false;
			}

			if (stagnationCounter >= MAX_STAGNATION_COUNTER) {
				stagnationCounter = 0;
				if(isSCLinear) {
					MAX_STAGNATION_COUNTER = (int)Math.floor(stagnationCounter*STAGNATION_MULTIPLIER + STAGNATION_CONSTANT);
				}
				if(neighborhoodType < 3) {
					neighborhoodType++;
					System.out.println(neighborhoodType + " " + graph.pathLength(maxSolution));
				}
				else {
					neighborhoodType = 1;
					if (longTermMemory.isEmpty()) {
						return maxSolution;
					}
					else
						newMove = longTermMemory.pop();
	
					newSolution = newMove.getFirst();
					addOnTabuList(newMove.getSecond());
				}
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
			if (move != null) {
				if (move.getFrom() == i && move.getTo() == j)
					return true;
				//fragment kodu sprawdzający szczególny przypadek:
				//invert(i,i+1) = invert(i+1,i) = swap(i,i+1) = swap(i+1, i) = insert(i, i+1) = insert(i+1, i)
				if (i == j+1 || i == j-1)
					if (move.getFrom() == j && move.getTo() == i)
						return true;
			}
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
	
	public Integer[] swap(Integer[] tab, int from, int to) {
		Integer[] tabPom = tab.clone();
		Integer help = tabPom[from];
		tabPom[from] = tabPom[to];
		tabPom[to] = help;
		return tabPom;
	}
	
	public Integer[] insert(Integer[] tab, int from, int to) {
		Integer[] tabPom = tab.clone();
		Integer toInsert = tabPom[from];
		if(from < to) {
			for(int i = from; i < to; i++) {
				tabPom[i] = tabPom[i+1];
			}
			tabPom[to] = toInsert;
		}
		else if(from > to) {
			for(int i = from; i > to; i--) {
				tabPom[i] = tabPom[i-1];
			}
			tabPom[to] = toInsert;
		}
		return tabPom;
	}

	public void increaseTabPointer() {
		tabPointer = (tabPointer + 1) % TENURE;
	}

	public boolean stopCriterion() {
		return counter >= MAX_COUNTER;
	}
}
