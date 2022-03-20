package tsp;

import java.util.ArrayList;

public abstract class Graph {

	private final int size;

	private Integer[] optimalPath = null;
	private Integer[] currentPath = null;

	public Graph(int size) {
		this.size = size;
	}

	public abstract double calculateDistance(int from, int to);

	public double pathLength(Integer[] nodes) {
		double sum = 0.0;
		if (isPathCorrect(nodes)) {
			for (int i = 0; i < (size - 1); i++) {
				sum += calculateDistance(nodes[i], nodes[i + 1]);
			}
			sum += calculateDistance(nodes[nodes.length - 1], nodes[0]);
		} else {
			sum = -1.0;
		}
		return sum;
	}

	public boolean isPathCorrect(Integer[] nodes) {
		if (nodes.length != size) {
			return false;
		} else {
			ArrayList<Integer> visited_points = new ArrayList<>();
			for (Integer node : nodes) {
				if (visited_points.contains(node)) {
					return false;
				} else if (node > size || node < 1) {
					return false;
				} else {
					visited_points.add(node);
				}
			}
			return true;
		}
	}

	public double PRD(Integer[] path) {
		double prd = -1.0;
		if (isPathCorrect(path)) {
			if (optimalPath != null) {
				double optimal_length = pathLength(optimalPath);
				//nie jestem pewny tego wzoru!
				prd = ((pathLength(path) - optimal_length) / optimal_length) * 100.0;
			} else {
				System.out.println("Unknown optimal tour!");
			}
		} else {
			System.out.println("Incorrect path!");
		}
		return prd;
	}

	public void printCurrentPath() {
		for (Integer integer : currentPath) {
			System.out.printf("%d\n", integer);
		}
	}

	public void printMatrix() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.printf("%f ", calculateDistance(i, j));
			}
			System.out.print("\n");
		}
	}

	public int getNodesCount() {
		return size;
	}

	public Integer[] getCurrentPath() {
		return currentPath;
	}

	public Integer[] getOptimalPath() {
		return optimalPath;
	}

	public void setCurrentPath(Integer[] nodes) {
		if (isPathCorrect(nodes))
			this.currentPath = nodes;
		else
			System.out.println("Incorrect path!");
	}

	public void setOptimalPath(Integer[] nodes) {
		if (isPathCorrect(nodes))
			this.optimalPath = nodes;
		else
			System.out.println("Incorrect path!");
	}
}
