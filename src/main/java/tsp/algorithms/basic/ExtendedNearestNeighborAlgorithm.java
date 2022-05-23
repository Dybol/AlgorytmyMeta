package tsp.algorithms.basic;

import tsp.Graph;
import tsp.algorithms.Algorithm;

public class ExtendedNearestNeighborAlgorithm implements Algorithm {

	Graph graph;

	public ExtendedNearestNeighborAlgorithm(Graph graph) {
		this.graph = graph;
	}

	@Override
	public Integer[] findSolution() {
		NearestNeighborAlgorithm basicNNAlg = new NearestNeighborAlgorithm(graph, 1);
		Integer[] bestSolution;
		bestSolution = basicNNAlg.findSolution();
		double shortestPath = graph.pathLength(bestSolution);
		double len;
		Integer[] help;
		for (int i = 1; i <= graph.getNodesCount(); i++) {
			basicNNAlg.setStartingNode(i);
			help = basicNNAlg.findSolution();
			if ((len = graph.pathLength(help)) < shortestPath) {
				shortestPath = len;
				bestSolution = help;
			}

		}
		return bestSolution;
	}
}
