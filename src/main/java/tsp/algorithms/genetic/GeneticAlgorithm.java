package tsp.algorithms.genetic;

import tsp.Graph;
import tsp.algorithms.Algorithm;
import tsp.algorithms.tabu.Tabu2Opt;
import tsp.algorithms.tabu.Tabu2OptWithVNS;
import tsp.util.Pair;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm implements Algorithm {
	
	private SingleIteration2Opt memeticAlgorithm;
	private final Graph graph;
	private int generationNo = 0;
	private Integer maxExecutionTime;
	private Integer maxGenerationNo;
	private Boolean stopOnTime;
	private long timeWhenStarted;
	private double probabilityOfMutation;
	private double probabilityOfCrossing;
	
//	1 - invert
//	2 - swap
//	3 - insert
	private int typeOfNeighborhoodForMemetics;
//	1 - invert
//	2 - swap
//	3 - insert
	private int typeOfMutationOperator;
//  1 - PMX
	private int typeOfCrossoverOperator;

	private List<Integer> listToShuffle;
	
	private List<Integer[]> population;

	private int problemSize;
	private Integer populationSize;

	public GeneticAlgorithm(Graph graph, int populationSize, double probabilityOfMutation, double probabilityOfCrossing, 
			int typeOfNeighborhoodForMemetics, int typeOfMutationOperator, int typeOfCrossoverOperator, int maxExecutionTime, int maxGenerationNo, boolean stopOnTime) {
		this.graph = graph;
		this.problemSize = graph.getNodesCount();
		this.populationSize = populationSize;
		this.probabilityOfMutation = probabilityOfMutation;
		this.probabilityOfCrossing = probabilityOfCrossing;
		this.typeOfNeighborhoodForMemetics = typeOfNeighborhoodForMemetics;
		this.typeOfMutationOperator = typeOfMutationOperator;
		this.typeOfCrossoverOperator = typeOfCrossoverOperator;
		this.maxExecutionTime = maxExecutionTime;
		this.maxGenerationNo = maxGenerationNo;
		this.stopOnTime = stopOnTime;
		timeWhenStarted = System.currentTimeMillis();
		memeticAlgorithm = new SingleIteration2Opt(graph, this.typeOfNeighborhoodForMemetics);
	}
	
	@Override
	public Integer[] findSolution() {
		generationNo = 0;
		listToShuffle = initList(problemSize);
		population = generateStartingPopulationWithTournament((int)Math.sqrt(populationSize));
		//population = generate();
		Integer[] bestSolution = population.get(0);
		double smallestValue = graph.pathLength(population.get(0));
		do {
			generationNo++;
			List<Pair<Integer[], Integer[]>> parents = generateParentsWithRoulette();
			List<Integer[]> children = new ArrayList<>();
			if(typeOfCrossoverOperator == 1) {
				children = crossoverPMX(parents);
			}
			population.addAll(children);
			List<Integer[]> mutatedPopulation = mutate(population);
			List<Integer[]> memedPopulation = memeticAlgorithm(mutatedPopulation);
			List<Pair<Integer[], Double>> survivorsWithValues = selectToSurviveWithValue(memedPopulation);
			Pair<Integer[], Double> bestSolutionWithValue = getTheBestOne(survivorsWithValues);
			if(bestSolutionWithValue.getSecond() < smallestValue) {
				bestSolution = bestSolutionWithValue.getFirst().clone();
				smallestValue = graph.pathLength(bestSolution);
				
			}
			double averageValueOfPopulation = getAverageValue(survivorsWithValues);
			System.out.println(generationNo + "\t" + smallestValue + " (min)\t" + graph.PRD(bestSolution) + "% (PRD)\t" + averageValueOfPopulation + " (avg)\t");
			
			
//			System.out.println(generationNo);
			

			population = getSurvivors(survivorsWithValues);
			// TODO
			// Mutacja - wykonywanie ruchów swap/insert/invert z zadanym ppb.
			// Algorytm memetyczny - zapuszczamy np. 2Opt na każdym zmutowanym osobniku
			// Selekcja (kto przeżywa), możliwe proste implementacje:
			// *pojedynki w parach, przeżywa lepszy z pary
			// *elitaryzm - sortujemy rozwiązania rosnąco w liście względem ich wartości funkcji celu, wybieramy z 5-10% najlepszych
			// i odrzuczamy analogiczną liczbę najgorszych; reszta staje do pojedynków w parach
			// *brutalne odcięcie najgorszej połowy
		}
		while(!stopCriterion(System.currentTimeMillis()));
		return bestSolution;
	}


	public List<Integer[]> generate() {
		List<Integer[]> list = new ArrayList<>();

		for (int i = 0; i < populationSize; i++) {
			list.add(shuffleArray());
		}

		return list;
	}
	
	/**
	 * 
	 * @param tournamentSize liczba rywali przypadająca na jedno miejsce
	 * @return populacja początkowa
	 */
	public List<Integer[]> generateStartingPopulationWithTournament(int tournamentSize) { 
		List<Pair<Integer[], Double>> largeList = new ArrayList<>();
		List<Integer[]> list = new ArrayList<>();
		int listSize = populationSize * tournamentSize;
		for (int i = 0; i < listSize; i++) {
			Integer[] sol = shuffleArray();
			largeList.add(new Pair(sol, graph.pathLength(sol)));
		}
		for (int i = 0; i < listSize / tournamentSize; i++) {
			List<Pair<Integer[], Double>> tournamentList = largeList.subList(i*tournamentSize, (i+1)*tournamentSize);
			while(tournamentList.size() > 1) {
				List<Pair<Integer[], Double>> winnersList = new ArrayList<>();
				for(int j = 0; j + 1 < tournamentList.size(); j+=2) {
					if(tournamentList.get(j).getSecond() <= tournamentList.get(j+1).getSecond()) {
						winnersList.add(tournamentList.get(j));
					}
					else {
						winnersList.add(tournamentList.get(j+1));
					}
				}
				if(tournamentList.size() % 2 == 1) {
					winnersList.add(tournamentList.get(tournamentList.size()-1));
				}
				tournamentList = winnersList;
			}
			list.add(tournamentList.get(0).getFirst());
		}

		return list;
	}

	public List<Pair<Integer[], Integer[]>> generateParents() {
		List<Pair<Integer[], Integer[]>> parents = new ArrayList<>();

		for (int i = 0; i < populationSize - 1; i += 2) {
			parents.add(new Pair<>(population.get(i), population.get(i + 1)));
		}

		return parents;
	}
	
	public List<Pair<Integer[], Integer[]>> generateParentsWithRoulette() {
		List<Pair<Integer[], Integer[]>> parents = new ArrayList<>();
		List<Pair<Integer[], Double>> populationWithValues = evaluate(population);
		double length = 0.0;
		for(Pair<Integer[], Double> pair : populationWithValues) {
			length += (1/pair.getSecond());
		}
		while (parents.size() < populationSize/2) {
			Random random = new Random();
			double randomDouble = random.nextDouble() * length;
			int iterOne = 0;
			int iterTwo = 0;
			Integer[] parentOne = populationWithValues.get(populationSize-1).getFirst();
			Integer[] parentTwo = populationWithValues.get(populationSize-2).getFirst();
			double helpToFindSection = 0.0;
			for(Pair<Integer[], Double> pair : populationWithValues) {
				if (helpToFindSection + (1/pair.getSecond()) < randomDouble) {
					helpToFindSection += (1/pair.getSecond());
				}
				else {
					iterOne = populationWithValues.indexOf(pair);
					parentOne = pair.getFirst();
					break;
				}
			}
			randomDouble = random.nextDouble() * length;
			helpToFindSection = 0.0;
			for(Pair<Integer[], Double> pair : populationWithValues) {
				if (helpToFindSection + (1/pair.getSecond()) < randomDouble) {
					helpToFindSection += (1/pair.getSecond());
				}
				else {
					iterTwo = populationWithValues.indexOf(pair);
					if (iterOne == iterTwo) {
						if (iterTwo + 1 < populationWithValues.size()) {
							iterTwo++;
						}
					}
					parentTwo = populationWithValues.get(iterTwo).getFirst();
					break;
				}
			}
			parents.add(new Pair(parentOne, parentTwo));
		}
		return parents;
	}

	/**
	 * @param allParents - parents
	 * @return list of child
	 */
	public List<Integer[]> crossoverPMX(List<Pair<Integer[], Integer[]>> allParents) {
		List<Integer[]> listOfChild = new ArrayList<>();

		Random random = new Random();
		for (Pair<Integer[], Integer[]> pairOfParents : allParents) {
			double randomDouble = random.nextDouble();
			if(randomDouble <= probabilityOfCrossing) {
				Integer[] firstParent = pairOfParents.getFirst();
				Integer[] secondParent = pairOfParents.getSecond();
	
				// od 1 do problemSize -1
				int i = random.nextInt(problemSize - 2) + 1;
				// od 2 do problemSize -1
				int j = random.nextInt(problemSize - 3) + 2;
	
				if (i == j) {
					i = j - (random.nextInt(j - 1) + 1);
				}
	
				if (i > j) {
					int temp = i;
					i = j;
					j = temp;
				}
	
				//System.out.println("i = " + i);
				//System.out.println("j = " + j);
	
				Integer[] firstChild = new Integer[problemSize];
				Integer[] secondChild = new Integer[problemSize];
	
				//kopiowanie srodkow - dziala
				for (int x = i; x <= j; x++) {
					firstChild[x] = secondParent[x];
					secondChild[x] = firstParent[x];
				}
	
				int helpIndex = i;
	
				//dodawanie poczatku i konca
				for (int x = 0; x < problemSize; x++) {
					if (x == i) {
						x = j + 1;
					}
					if (!doesArrayContainsValue(secondParent, firstParent[x], i, j)) {
						firstChild[x] = firstParent[x];
					} else {
						for (int y = helpIndex; y <= j; y++) {
							if (!doesArrayContainsValue(secondParent, firstParent[y], i, j) && !doesArrayContainsValue(firstChild, firstParent[y], 0, x - 1)) {
								firstChild[x] = firstParent[y];
								helpIndex = y + 1;
								break;
							}
						}
					}
				}
	
				helpIndex = i;
	
				for (int x = 0; x < problemSize; x++) {
					if (x == i) {
						x = j + 1;
					}
					if (!doesArrayContainsValue(firstParent, secondParent[x], i, j)) {
						secondChild[x] = secondParent[x];
					} else {
						for (int y = helpIndex; y <= j; y++) {
							if (!doesArrayContainsValue(firstParent, secondParent[y], i, j) && !doesArrayContainsValue(secondChild, secondParent[y], 0, x - 1)) {
								secondChild[x] = secondParent[y];
								helpIndex = y + 1;
								break;
							}
						}
					}
				}
				listOfChild.add(firstChild);
				listOfChild.add(secondChild);
			}
			else {
				listOfChild.add(pairOfParents.getFirst());
				listOfChild.add(pairOfParents.getSecond());
			}
		}

		return listOfChild;
	}
	
	public List<Integer[]> mutate(List<Integer[]> list) {
		double elementaryProbability = probabilityOfMutation / ((problemSize * (problemSize-1)) / 2);
		Random random = new Random();
		for (Integer[] solution : list) {
			for (int i = 0; i < problemSize; i++) {
				for (int j = i; j < problemSize; j++) {
					if (random.nextDouble() < elementaryProbability) {
						
						//INVERT
						if (typeOfMutationOperator == 1) {
							solution = invert(solution, i, j);
						}
						//SWAP
						else if (typeOfMutationOperator == 2) {
							Integer help = solution[i];
							solution[i] = solution[j];
							solution[j] = help;
						}
						//INSERT
						else {
							solution = insert(solution, i, j);
						}
					}
				}
			}
		}
		return list;
	}
	
	public List<Integer[]> memeticAlgorithm(List<Integer[]> list) {
		List<Integer[]> improvedList = new ArrayList<>();
		for (Integer[] solution : list) {
			graph.setCurrentPath(solution);
			Integer[] newSolution = memeticAlgorithm.findSolution();
			improvedList.add(newSolution);
		}
		return improvedList;
	}
	
	public List<Pair<Integer[], Double>> selectToSurviveWithValue(List<Integer[]> list) {
		List<Pair<Integer[], Double>> winnersWithValues = new ArrayList<>();
		for (int i = 0 ; i + 1 < list.size(); i+=2) {
			double valueOne = graph.pathLength(list.get(i));
			double valueTwo = graph.pathLength(list.get(i+1));
			if(valueOne <= valueTwo) {
				winnersWithValues.add(new Pair(list.get(i), valueOne));
			}
			else {
				winnersWithValues.add(new Pair(list.get(i+1), valueTwo));
			}
		}
		return winnersWithValues;
	}
	
	public Pair<Integer[], Double> getTheBestOne(List<Pair<Integer[], Double>> list) {
		Pair<Integer[], Double> theBestOne = new Pair<Integer[], Double>(list.get(0).getFirst(), list.get(0).getSecond());
		for (Pair<Integer[], Double> solutionWithValue : list) {
			if(solutionWithValue.getSecond() < theBestOne.getSecond()) {
				theBestOne = solutionWithValue;
			}
		}
		return theBestOne;
	}
	
	public double getAverageValue(List<Pair<Integer[], Double>> list) {
		double avg = 0.0;
		for (Pair<Integer[], Double> solutionWithValue : list) {
			avg += solutionWithValue.getSecond();
		}
		return avg / list.size();
	}
	
	public List<Integer[]> getSurvivors(List<Pair<Integer[], Double>> list) {
		List<Integer[]> survivors = new ArrayList<>();
		for(Pair<Integer[], Double> pair : list) {
			survivors.add(pair.getFirst());
		}
		return survivors;
	}

	private boolean doesArrayContainsValue(Integer[] array, int value, int i, int j) {
		for (int x = i; x <= j; x++) {
			if (array[x] != null && array[x] == value)
				return true;
		}
		return false;
	}

	private List<Integer> initList(int size) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			list.add(i + 1);
		}

		return list;
	}

	private Integer[] shuffleArray() {

		Collections.shuffle(listToShuffle);

		Integer[] tab = new Integer[problemSize];
		tab = listToShuffle.toArray(tab);

		return tab;
	}
	
	public List<Pair<Integer[], Double>> evaluate (List<Integer[]> list) {
		List<Pair<Integer[], Double>> listWithValues = new ArrayList<>();
		for (Integer[] sol : list) {
			listWithValues.add(new Pair<Integer[], Double>(sol, graph.pathLength(sol)));
		}
		return listWithValues;
	}
	
	boolean stopCriterion(long currentTime) {
		if(stopOnTime) {
			if(currentTime - timeWhenStarted >= maxExecutionTime)
				return true;
			else
				return false;
		}
		else {
			if(generationNo >= maxGenerationNo)
				return true;
			else
				return false;
			
		}
	}
	
	public Integer[] insert(Integer[] tab, int from, int to) {
		Integer[] tabPom = tab.clone();
		Integer toInsert = tabPom[from];
		if (from < to) {
			for (int i = from; i < to; i++) {
				tabPom[i] = tabPom[i + 1];
			}
			tabPom[to] = toInsert;
		} else if (from > to) {
			for (int i = from; i > to; i--) {
				tabPom[i] = tabPom[i - 1];
			}
			tabPom[to] = toInsert;
		}
		return tabPom;
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


	
}
