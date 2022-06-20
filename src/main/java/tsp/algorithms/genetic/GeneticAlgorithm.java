package tsp.algorithms.genetic;

import tsp.Graph;
import tsp.algorithms.Algorithm;
import tsp.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm implements Algorithm {
	
	private final Graph graph;
	private int generationNo = 0;
	private Integer maxExecutionTime = 10 * 1_000;
	private Integer maxGenerationNo = 100000;
	private Boolean stopOnTime = true;
	private long timeWhenStarted;
	
	private List<Integer> listToShuffle;
	
	private List<Integer[]> population;

	private Integer listSize;

	public GeneticAlgorithm(Graph graph, int populationSize, int maxExecutionTime, int maxGenerationNo, boolean stopOnTime) {
		this.graph = graph;
		listSize = populationSize;
		this.maxExecutionTime = maxExecutionTime;
		this.maxGenerationNo = maxGenerationNo;
		this.stopOnTime = stopOnTime;
		timeWhenStarted = System.currentTimeMillis();
	}
	
	@Override
	public Integer[] findSolution() {
		listToShuffle = initList(listSize);
		population = generate();
		do {
			List<Pair<Integer[], Integer[]>> parents = generateParents();
			List<Integer[]> children = crossover(parents);
		}
		while(!stopCriterion(System.currentTimeMillis()));
		return population.get(0);
	}


	public List<Integer[]> generate() {
		List<Integer[]> list = new ArrayList<>();

		for (int i = 0; i < listSize; i++) {
			list.add(shuffleArray());
		}

		return list;
	}

	public List<Pair<Integer[], Integer[]>> generateParents() {
		List<Pair<Integer[], Integer[]>> parents = new ArrayList<>();

		for (int i = 0; i < listSize - 1; i += 2) {
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

			// od 1 do listSize -1
			int i = random.nextInt(listSize - 2) + 1;
			int j = random.nextInt(listSize - 2) + 1;

			if (i == j) {
				i = j - (random.nextInt(j - 1) + 1);
			}

			if (i > j) {
				int temp = i;
				i = j;
				j = temp;
			}

			System.out.println("i = " + i);
			System.out.println("j = " + j);

			Integer[] firstChild = new Integer[listSize];
			Integer[] secondChild = new Integer[listSize];

			//kopiowanie srodkow - dziala
			for (int x = i; x <= j; x++) {
				firstChild[x] = secondParent[x];
				secondChild[x] = firstParent[x];
			}

			int helpIndex = i;

			//dodawanie poczatku i konca
			for (int x = 0; x < listSize; x++) {
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

			for (int x = 0; x < listSize; x++) {
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

		Integer[] tab = new Integer[listSize];
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
