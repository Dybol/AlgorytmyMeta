package tsp.algorithms.genetic;


import tsp.Graph;
import tsp.algorithms.Algorithm;

public class SingleIteration2Opt implements Algorithm {
	// 1 - invert (SLOW)
		// 2 - swap (QUICK)
		// 3 - insert (SLOW)
		private Integer neighborhoodType = 1;
		private Graph graph;

		public SingleIteration2Opt(Graph graph, Integer neighborhoodType) {
			this.graph = graph;
			this.neighborhoodType = neighborhoodType;
		}

		@Override
		public Integer[] findSolution() {
			Integer[] curSolution = graph.getCurrentPath();
			Integer[] newSolution = graph.getCurrentPath();
			Integer[] maxSolution = graph.getCurrentPath();
			double bestValue = graph.pathLength(maxSolution);
			double curValue = graph.pathLength(curSolution);
			if (neighborhoodType == 1) {
				for (int i = 0; i < curSolution.length; i++) {
					double newValue = curValue;
					newSolution = curSolution;
					for (int j = i + 1; j < curSolution.length; j++) {
						newValue = newValue - graph.calculateDistance(newSolution[j], newSolution[j-1])
								+ graph.calculateDistance(newSolution[j], newSolution[i]);
						if (i > 0) {
							newValue -= graph.calculateDistance(newSolution[i-1], newSolution[i]);
							newValue += graph.calculateDistance(newSolution[i-1], newSolution[j]);
						}
						if (j + 1 < curSolution.length) {
							newValue -= graph.calculateDistance(newSolution[j], newSolution[j+1]);
							newValue += graph.calculateDistance(newSolution[j-1], newSolution[j+1]);
						}
						Integer[] invertedNewSolution = invert(curSolution, i, j);
						if (newValue < bestValue) {
							
							maxSolution = invertedNewSolution;
							bestValue = newValue;
							
						}
						newSolution = invertedNewSolution;
					}
				}
			}
			else if (neighborhoodType == 2) {
				for (int i = 0; i < curSolution.length; i++) {
					for (int j = i + 1; j < curSolution.length; j++) {
						double newValue = curValue - graph.calculateDistance(curSolution[i], curSolution[i+1]) - graph.calculateDistance(curSolution[j-1], curSolution[j])
								+ graph.calculateDistance(curSolution[j], curSolution[i+1]) + graph.calculateDistance(curSolution[j-1], curSolution[i]);
						if (i > 0) {
							newValue -= graph.calculateDistance(curSolution[i-1], curSolution[i]);
							newValue += graph.calculateDistance(curSolution[i-1], curSolution[j]);
						}
						if (j + 1 < curSolution.length) {
							newValue -= graph.calculateDistance(curSolution[j], curSolution[j+1]);
							newValue += graph.calculateDistance(curSolution[i], curSolution[j+1]);
						}
						
						if (newValue < bestValue) {
							Integer[] invertedNewSolution = swap(curSolution, i, j);
							maxSolution = invertedNewSolution;
							bestValue = newValue;
							
						}
					}
				}
			} 
			else if (neighborhoodType == 3) {
				for (int i = 0; i < curSolution.length; i++) {
					for (int j = i + 1; j < curSolution.length; j++) {
						double newValue = curValue - graph.calculateDistance(curSolution[i], curSolution[i+1])
								+ graph.calculateDistance(curSolution[j], curSolution[i]);
						if (i > 0) {
							newValue -= graph.calculateDistance(curSolution[i-1], curSolution[i]);
							newValue += graph.calculateDistance(curSolution[i-1], curSolution[i+1]);
						}
						if (j + 1 < curSolution.length) {
							newValue -= graph.calculateDistance(curSolution[j], curSolution[j+1]);
							newValue += graph.calculateDistance(curSolution[i], curSolution[j+1]);
						}
						
						if (newValue < bestValue) {
							Integer[] invertedNewSolution = insert(curSolution, i, j);
							maxSolution = invertedNewSolution;
							bestValue = newValue;
						}
					}
				}
			}

			return maxSolution;

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

		public Integer[] swap(Integer[] tab, int from, int to) {
			Integer[] tabPom = tab.clone();
			Integer help = tabPom[from];
			tabPom[from] = tabPom[to];
			tabPom[to] = help;
			return tabPom;
		}
	}
