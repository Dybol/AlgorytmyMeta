package tsp.algorithms;

import tsp.Graph;

public class Algorithm2Opt implements Algorithm {

	Graph graph;
	
	public Algorithm2Opt(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	public Integer[] findSolution() {
		Integer[] curSolution = graph.getCurrentPath();
		Integer[] newSolution = graph.getCurrentPath();
		Integer[] maxSolution = graph.getCurrentPath();
		do {
			curSolution = newSolution;
			for(int i=0; i<curSolution.length; i++) {
				for(int j=i+1; j<curSolution.length; j++) {
					if(graph.pathLength(invert(newSolution, i, j)) < graph.pathLength(maxSolution)) {
						maxSolution = invert(newSolution, i, j);
					}
				}
			}
			newSolution = maxSolution;
		}
		while(graph.pathLength(newSolution) < graph.pathLength(curSolution));
		return newSolution;
	}
	
	public Integer[] invert(Integer[] tab, int from, int to) {
		Integer[] tabPom = new Integer[tab.length];
		for(int i = 0; i < tab.length; i++) {
			if(i < from || i > to) {
				tabPom[i] = tab[i];
			}
			else {
				tabPom[i] = tab[to-(i-from)];
			}
		}
		return tabPom;
	}
	
}
