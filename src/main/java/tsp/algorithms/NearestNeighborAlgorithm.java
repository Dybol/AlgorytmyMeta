package tsp.algorithms;

import java.util.ArrayList;

import tsp.Graph;

public class NearestNeighborAlgorithm implements Algorithm {

	Graph graph;
	private int startingNode;
	
	public NearestNeighborAlgorithm(Graph graph, int startingNode) {
		this.graph = graph;
		this.startingNode = startingNode;
	}
	
	public void setStartingNode(int startingNode) {
		this.startingNode = startingNode;
	}
	
	@Override
	public Integer[] findSolution() {
		ArrayList<Integer> unvisitedNodes = new ArrayList<Integer>();
		for (int i = 1; i <= graph.getNodesCount(); i++) {
			unvisitedNodes.add(i);
		}
		Integer[] solution = new Integer[graph.getNodesCount()];
		solution[0] = startingNode;
		unvisitedNodes.remove(unvisitedNodes.indexOf(startingNode));
		int iter = 1;
		while(!unvisitedNodes.isEmpty()) {
			double shortest = graph.calculateDistance(solution[iter-1], unvisitedNodes.get(0));
			int nodeToRemove = unvisitedNodes.get(0);
			for(int node: unvisitedNodes) {
				if(shortest > graph.calculateDistance(solution[iter-1], node)) {
					shortest = graph.calculateDistance(solution[iter-1], node);
					nodeToRemove = node;
				}
			}
			solution[iter] = nodeToRemove;
			unvisitedNodes.remove(unvisitedNodes.indexOf(nodeToRemove));
			iter++;
		}
		return solution;
	}

}
