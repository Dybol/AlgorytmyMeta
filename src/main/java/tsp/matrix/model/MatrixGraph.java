package tsp.matrix.model;

import java.util.ArrayList;

import tsp.Graph;

//Ta klasa zadziała zarówno dla symetrycznych i asymetrycznych grafów
public class MatrixGraph implements Graph{

	private int size;
	
	//konwencja: edges[from-1][to-1] jako krawędź od punktu <from> do punktu <to> 
	private int [][] edges;
	private int[] optimalPath = null;
	private int[] currentPath = null;
	
	public MatrixGraph(int [][] edges) {
		this.edges = edges;
		this.size = edges.length;
	}
	
	public void setOptimalPath(int[] node_numbers) {
		if(isPathCorrect(node_numbers))
			this.optimalPath = node_numbers;
		else
			System.out.println("Incorrect path!");
	}
	
	public void setCurrentPath(int[] node_numbers) {
		if(isPathCorrect(node_numbers))
			this.currentPath = node_numbers;
		else
			System.out.println("Incorrect path!");
	}
	
	@Override
	public double calculateDistance(int from, int to) {
		return edges[from-1][to-1];
	}
	
	@Override
	public double pathLength(int[] node_numbers) {
		double sum = 0.0;
		if(isPathCorrect(node_numbers)) {
			for (int i = 0; i < (size - 1); i++) {
				sum += calculateDistance(node_numbers[i], node_numbers[i+1]);
			}
			sum += calculateDistance(node_numbers[node_numbers.length - 1], node_numbers[0]);
		}
		else {
			sum = -1.0;
		}
		return sum;
	}

	@Override
	public boolean isPathCorrect(int[] node_numbers) {
		if(node_numbers.length != size) {
			return false;
		}
		else {
			ArrayList<Integer> visited_points = new ArrayList<>();
			for(int i = 0; i<node_numbers.length; i++) {
				if(visited_points.contains(node_numbers[i])) {
					return false;
				}
				else if (node_numbers[i] > size || node_numbers[i] < 1){
					return false;
				}
				else {
					visited_points.add(node_numbers[i]);
				}
			}
			return true;
		}
		
	}

	@Override
	public double PRD(int[] path) {
		double prd = -1.0;
		if(isPathCorrect(path)) {
			if(optimalPath != null) {
				double optimal_length = pathLength(optimalPath);
				//nie jestem pewny tego wzoru!
				prd = ((pathLength(path) - optimal_length)/optimal_length) * 100.0;
			}
			else {
				System.out.println("Unknown optimal tour!");
			}
		}
		else {
			System.out.println("Incorrect path!");
		}
		return prd;
	}
	
	public void printMatrix() {
		for(int i = 0; i < edges.length; i++) {
			for(int j = 0; j < edges.length; j++) {
				System.out.printf("%f ", calculateDistance(i,j));
			}
			System.out.print("\n");
		}
	}
	
	public void printCurrentPath() {
		for(int i = 0; i < currentPath.length; i++) {
			System.out.printf("%d\n", currentPath[0]);
		}
	}
	
	public int getNodesCount() {
		return size;
	}



}
