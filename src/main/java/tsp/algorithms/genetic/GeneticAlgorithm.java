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
	
	private Tabu2Opt memeticAlgorithm;
	private final Graph graph;
	private int generationNo = 0;
	private Integer maxExecutionTime;
	private Integer maxGenerationNo;
	private Boolean stopOnTime;
	private long timeWhenStarted;
	private double probabilityOfMutation;
	
	private List<Integer> listToShuffle;
	
	private List<Integer[]> population;

	private int problemSize;
	private Integer populationSize;

	public GeneticAlgorithm(Graph graph, int populationSize, double probabilityOfMutation, int maxExecutionTime, int maxGenerationNo, boolean stopOnTime) {
		this.graph = graph;
		memeticAlgorithm = new Tabu2Opt(graph, maxExecutionTime/(100*populationSize), 1, true, 7, 100, 1);
		this.problemSize = graph.getNodesCount();
		this.populationSize = populationSize;
		this.probabilityOfMutation = probabilityOfMutation;
		this.maxExecutionTime = maxExecutionTime;
		this.maxGenerationNo = maxGenerationNo;
		this.stopOnTime = stopOnTime;
		timeWhenStarted = System.currentTimeMillis();
	}
	
	@Override
	public Integer[] findSolution() {
		listToShuffle = initList(problemSize);
		population = generate();
		Integer[] bestSolution = population.get(0);
		double smallestValue = graph.pathLength(population.get(0));
		do {
			generationNo++;
			Instant start = Instant.now();
			List<Pair<Integer[], Integer[]>> parents = generateParents();
			Instant end = Instant.now();
			//System.out.println(Duration.between(start, end).toMillis());
			start = Instant.now();
			List<Integer[]> children = crossover(parents);
			end = Instant.now();
			//System.out.println(Duration.between(start, end).toMillis());
			start = Instant.now();
			population.addAll(children);
			end = Instant.now();
			//System.out.println(Duration.between(start, end).toMillis());
			start = Instant.now();
			List<Integer[]> mutatedPopulation = mutate(population);
			end = Instant.now();
			//System.out.println(Duration.between(start, end).toMillis());
			start = Instant.now();
//			List<Integer[]> memedPopulation = memeticAlgorithm(mutatedPopulation);
			
			end = Instant.now();
			//System.out.println(Duration.between(start, end).toMillis());
			start = Instant.now();
			List<Pair<Integer[], Double>> survivorsWithValues = selectToSurviveWithValue(mutatedPopulation);
			end = Instant.now();
			//System.out.println(Duration.between(start, end).toMillis());
			Pair<Integer[], Double> bestSolutionWithValue = getTheBestOne(survivorsWithValues);
			if(bestSolutionWithValue.getSecond() < smallestValue) {
				bestSolution = bestSolutionWithValue.getFirst();
				smallestValue = bestSolutionWithValue.getSecond();
				System.out.println(generationNo + "|" + smallestValue);
			}
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

	public List<Pair<Integer[], Integer[]>> generateParents() {
		List<Pair<Integer[], Integer[]>> parents = new ArrayList<>();

		for (int i = 0; i < populationSize - 1; i += 2) {
			parents.add(new Pair<>(population.get(i), population.get(i + 1)));
		}

		return parents;
	}

	/**
	 * @param allParents - parents
	 * @return list of child
	 */
	public List<Integer[]> crossover(List<Pair<Integer[], Integer[]>> allParents) {
		List<Integer[]> listOfChild = new ArrayList<>();

		Random random = new Random();
		for (Pair<Integer[], Integer[]> pairOfParents : allParents) {
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

		return listOfChild;
	}
	
	public List<Integer[]> mutate(List<Integer[]> list) {
		double elementaryProbability = probabilityOfMutation / ((problemSize * (problemSize-1)) / 2);
		Random random = new Random();
		for (Integer[] solution : list) {
			for (int i = 0; i < problemSize; i++) {
				for (int j = i; j < problemSize; j++) {
					if(random.nextDouble() < elementaryProbability) {
						//SWAP
						Integer help = solution[i];
						solution[i] = solution[j];
						solution[j] = help;
					}
				}
			}
		}
		return list;
	}
	
	public List<Integer[]> memeticAlgorithm(List<Integer[]> list) {
		memeticAlgorithm = new Tabu2Opt(graph, maxExecutionTime/(100*populationSize), 1, true, 7, 100, 1);
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
		for (int i = 0 ; i < list.size(); i+=2) {
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


	
}
