package tsp.algorithms.genetic;

import tsp.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

	private List<Integer> listToShuffle;

	private List<Integer[]> population;

	private Integer listSize;

	public GeneticAlgorithm(int n) {
		listSize = n;
		listToShuffle = initList(n);
		population = generate();
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
		Random random = new Random();
		for (Pair<Integer[], Integer[]> pairOfParents : allParents) {
			Integer[] firstParent = pairOfParents.getFirst();
			Integer[] secondParent = pairOfParents.getSecond();

			// od 1 do listSize -1
			int i = random.nextInt(listSize - 1) + 1;
			int j = random.nextInt(listSize - 1) + 1;

			if (i == j) {
				i = j - random.nextInt(j - 1) + 1;
			}

			if (i > j) {
				int temp = i;
				j = i;
				i = temp;
			}

			Integer[] firstChild = new Integer[listSize];
			Integer[] secondChild = new Integer[listSize];

			for (int x = i + 1; x <= j; x++) {
				firstChild[x] = secondParent[x];
				secondChild[x] = firstParent[x];
			}

			int helpIndex = i + 1;

			for (int x = 0; x <= i; x++) {
				if (!doesArrayContainsValue(secondParent, firstChild[x], i, j)) {
					firstChild[x] = firstParent[x];
				} else {
					for (int y = helpIndex; y <= j; y++) {
						//TODO
						if (doesArrayContainsValue(firstParent, ))
					}
				}
			}
		}
	}

	private int findF

	private boolean doesArrayContainsValue(Integer[] array, int value, int i, int j) {
		for (int x = i + 1; x <= j; x++) {
			if (array[x] == value)
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
}
