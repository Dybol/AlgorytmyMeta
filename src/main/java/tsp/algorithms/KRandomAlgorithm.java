package tsp.algorithms;

import tsp.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KRandomAlgorithm implements Algorithm {

	private final Graph graph;
	private Integer k;

	public KRandomAlgorithm(Graph graph, int k) {
		this.graph = graph;
		this.k = k;
	}

	private Integer[] initRandomArray(int size) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			list.add(i + 1);
		}
		Collections.shuffle(list);

		Integer[] tab = new Integer[size];
		tab = list.toArray(tab);

		return tab;
	}

	@Override
	public Integer[] findSolution() {
		int size = graph.getNodesCount();
		double bestPath = graph.pathLength(initRandomArray(size));
		Integer[] bestSolution = new Integer[size];

		for (int i = 0; i < k; i++) {
			Integer[] array = initRandomArray(size);
			double currLength = graph.pathLength(array);
			if (currLength < bestPath) {
				bestPath = currLength;
				bestSolution = array;
			}
		}

		return bestSolution;
	}

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}
}
