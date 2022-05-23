package tsp.algorithms.tabu;

import tsp.Graph;
import tsp.tabu.Move;
import tsp.util.Pair;

import java.util.Stack;

public class Tabu2Opt2Continuous extends TabuAlgorithm {

	// 1 - invert
	// 2 - swap
	// 3 - insert
	private Integer neighborhoodType = 1;

	//przechowujemy atrybtuty ruchu ? gdy mamy n(i,j) to wrzucamy n(j,i)
	//reset -> losowe rozwiazanie - NIE
	//zapamietujemy rozwiazania po drodze, gdy mamy stagnacje to wracamy do poprzednio najlepszego rozwiazania i idziemy w inna strone
	//gdy zadna sciezka nie dala poprawy i wracamy do poczatku, to reset / losowy / koniec
	//kryterium - lepszy od najlepszego znanego o iles %  / gorrszy o iles %
	//kadencja - max elementow na liscie tabu
	private final Stack<Pair<Integer[], Move>> longTermMemory = new Stack<>();

	private boolean isSCLinear = false;

	public Tabu2Opt2Continuous(Graph graph, Integer maxExecutionTime, Integer maxCounter, Boolean stopOnCounter, Integer tenure, Integer maxStagnationCounter, Integer neighborhoodType) {
		super(graph, maxExecutionTime, maxCounter, stopOnCounter, tenure, maxStagnationCounter);
		this.neighborhoodType = neighborhoodType;
	}

	public Tabu2Opt2Continuous(Graph graph, Integer maxCounter, Integer tenure, Integer stagnationConstant, Integer neighborhoodType) {
		super(graph, maxCounter, tenure, stagnationConstant);
		this.setStagnationMultiplier(0.0);
		this.isSCLinear = false;
		this.neighborhoodType = neighborhoodType;
		this.setMaxStagnationCounter(stagnationConstant);
	}

	public Tabu2Opt2Continuous(Graph graph, Integer maxCounter, Integer tenure, Double stagnationMultiplier, Integer stagnationConstant, Integer neighborhoodType) {
		super(graph, 0, maxCounter, true, tenure, stagnationMultiplier, stagnationConstant);
		this.isSCLinear = true;
		this.neighborhoodType = neighborhoodType;
	}

	public Tabu2Opt2Continuous(Graph graph) {
		super(graph);
	}

	@Override
	public Integer[] findSolution() {
		long now = System.currentTimeMillis();

		Integer[] curSolution;
		Integer[] newSolution = getGraph().getCurrentPath();
		Integer[] maxSolution = getGraph().getCurrentPath();
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
		if (neighborhoodType == 1) {
			do {
				if (timeToSave == 0) {
					bestMove.setSecond(newMove.getSecond());
					longTermMemory.push(bestMove);
				}
				if (timeToSave > -1)
					timeToSave--;
				noMovesFoundYet = true;
				curSolution = newSolution;
				for (int i = 0; i < curSolution.length; i++) {
					for (int j = i + 1; j < curSolution.length; j++) {
						if (!isOnTabuList(i, j)) {
							Integer[] invertedNewSolution = invert(newSolution, i, j);
							if (noMovesFoundYet) {
								newSolution = invertedNewSolution;
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(i, j));
								noMovesFoundYet = false;
								if (getGraph().pathLength(invertedNewSolution) < getGraph().pathLength(maxSolution)) {
									if (timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									resetStagnationCounter();
								}
							} else if (getGraph().pathLength(invertedNewSolution) < getGraph().pathLength(newSolution)) {
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(i, j));

								if (getGraph().pathLength(invertedNewSolution) < getGraph().pathLength(maxSolution)) {
									if (timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									resetStagnationCounter();
								}
							}
						}
					}
				}

				addOnTabuList(newMove.getSecond());
				newSolution = newMove.getFirst();

				increaseCounter();
				increaseStagnationCounter();

				if (bestStreak) {
					longTermMemory.push(new Pair<>(previousMaxSolution, newMove.getSecond()));
					bestStreak = false;
				}

				if (getStagnationCounter() >= getMaxStagnationCounter()) {
					resetStagnationCounter();

					if (isSCLinear) {
						setMaxStagnationCounter((int) Math.floor(getStagnationCounter() * getStagnationMultiplier() + getStagnationConstant()));
					}
					if (longTermMemory.isEmpty())
						return maxSolution;
					newMove = longTermMemory.pop();

					newSolution = newMove.getFirst();
					addOnTabuList(newMove.getSecond());
				}
			}
			while (!stopCriterion(now));
		} else if (neighborhoodType == 2) {
			do {
				if (timeToSave == 0) {
					bestMove.setSecond(newMove.getSecond());
					longTermMemory.push(bestMove);
				}
				if (timeToSave > -1)
					timeToSave--;
				noMovesFoundYet = true;
				curSolution = newSolution;
				for (int i = 0; i < curSolution.length; i++) {
					for (int j = i + 1; j < curSolution.length; j++) {
						if (!isOnTabuList(i, j)) {
							Integer[] invertedNewSolution = swap(newSolution, i, j);
							if (noMovesFoundYet) {
								newSolution = invertedNewSolution;
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(i, j));
								noMovesFoundYet = false;
								if (getGraph().pathLength(invertedNewSolution) < getGraph().pathLength(maxSolution)) {
									if (timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									resetStagnationCounter();
								}
							} else if (getGraph().pathLength(invertedNewSolution) < getGraph().pathLength(newSolution)) {
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(i, j));

								if (getGraph().pathLength(invertedNewSolution) < getGraph().pathLength(maxSolution)) {
									if (timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									resetStagnationCounter();
								}
							}
						}
					}
				}

				addOnTabuList(newMove.getSecond());
				newSolution = newMove.getFirst();

				increaseCounter();
				increaseStagnationCounter();

				if (bestStreak) {
					longTermMemory.push(new Pair<>(previousMaxSolution, newMove.getSecond()));
					bestStreak = false;
				}

				if (getStagnationCounter() >= getMaxStagnationCounter()) {
					resetStagnationCounter();

					if (isSCLinear) {
						setMaxStagnationCounter((int) Math.floor(getStagnationCounter() * getStagnationMultiplier() + getStagnationConstant()));
					}

					if (longTermMemory.isEmpty())
						return maxSolution;
					newMove = longTermMemory.pop();

					newSolution = newMove.getFirst();
					addOnTabuList(newMove.getSecond());
				}
			}
			while (!stopCriterion(now));
		} else if (neighborhoodType == 3) {
			do {
				if (timeToSave == 0) {
					bestMove.setSecond(newMove.getSecond());
					longTermMemory.push(bestMove);
				}
				if (timeToSave > -1)
					timeToSave--;
				noMovesFoundYet = true;
				curSolution = newSolution;
				for (int i = 0; i < curSolution.length; i++) {
					for (int j = 0; j < curSolution.length; j++) {
						if (!isOnTabuList(i, j)) {
							Integer[] invertedNewSolution = insert(newSolution, i, j);
							if (noMovesFoundYet) {
								newSolution = invertedNewSolution;
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(j, i));
								noMovesFoundYet = false;
								if (getGraph().pathLength(invertedNewSolution) < getGraph().pathLength(maxSolution)) {
									if (timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									resetStagnationCounter();
								}
							} else if (getGraph().pathLength(invertedNewSolution) < getGraph().pathLength(newSolution)) {
								newMove.setFirst(invertedNewSolution);
								newMove.setSecond(new Move(j, i));

								if (getGraph().pathLength(invertedNewSolution) < getGraph().pathLength(maxSolution)) {
									if (timeToSave == 0) {
										previousMaxSolution = bestMove.getFirst();
										bestStreak = true;
									}
									timeToSave = 1;
									maxSolution = invertedNewSolution;
									bestMove.setFirst(maxSolution);
									resetStagnationCounter();
								}
							}
						}
					}
				}

				addOnTabuList(newMove.getSecond());
				newSolution = newMove.getFirst();

				increaseCounter();
				increaseStagnationCounter();

				if (bestStreak) {
					longTermMemory.push(new Pair<>(previousMaxSolution, new Move(newMove.getSecond().getTo(), newMove.getSecond().getFrom())));
					bestStreak = false;
				}

				if (getStagnationCounter() >= getMaxStagnationCounter()) {
					resetStagnationCounter();

					if (isSCLinear) {
						setMaxStagnationCounter((int) Math.floor(getStagnationCounter() * getStagnationMultiplier() + getStagnationConstant()));
					}

					if (longTermMemory.isEmpty()) {
						getGraph().setCurrentPath(maxSolution);
						if(getStagnationMultiplier() > 0.0)
							//Tabu2Opt2Continuous newTabu = new Tabu2Opt2Continuous(getGraph(), getMaxExecutionTime(), getMaxCounter(), getStopOnCounter(), );
						return maxSolution;
					}
					newMove = longTermMemory.pop();

					newSolution = newMove.getFirst();
					addOnTabuList(newMove.getSecond());
				}

			}
			while (!stopCriterion(now));
		}

		return maxSolution;

	}
}
