package tsp.euc2d.model;

import java.util.ArrayList;

import tsp.Graph;

public class Euc2dGraph implements Graph{

	private ArrayList<Euc2d> nodes;
	private Integer[] optimalPath = null;
	private Integer[] currentPath = null;
	
	public Euc2dGraph(ArrayList<Euc2d> nodes) {
		this.nodes = nodes;
	}
	
	public Integer[] getCurrentPath() {
		return currentPath;
	}
	
	public void setOptimalPath(Integer[] node_numbers) {
		if(isPathCorrect(node_numbers))
			this.optimalPath = node_numbers;
		else
			System.out.println("Incorrect path!");
	}
	
	@Override
	public void setCurrentPath(Integer[] node_numbers) {
		if(isPathCorrect(node_numbers))
			this.currentPath = node_numbers;
		else
			System.out.println("Incorrect path!");
	}
	
	@Override
	public double calculateDistance(int from, int to) {
		return Math.sqrt(Math.pow((nodes.get(from-1).getCordX()-nodes.get(to-1).getCordX()), 2)
				+ Math.pow((nodes.get(from-1).getCordY()-nodes.get(to-1).getCordY()), 2));
	}
	
	@Override
	public double pathLength(Integer[] node_numbers) {
		double sum = 0.0;
		if(isPathCorrect(node_numbers)) {
			for (int i = 0; i < (nodes.size() - 1); i++) {
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
	public boolean isPathCorrect(Integer[] node_numbers) {
		if(node_numbers.length != nodes.size()) {
			return false;
		}
		else {
			ArrayList<Integer> visited_points = new ArrayList<>();
			for(int i = 0; i<node_numbers.length; i++) {
				if(visited_points.contains(node_numbers[i])) {
					return false;
				}
				else if (node_numbers[i] > nodes.size() || node_numbers[i] < 1){
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
	public double PRD(Integer[] path) {
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
		for(int i = 0; i < nodes.size(); i++) {
			for(int j = 0; j < nodes.size(); j++) {
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
		return nodes.size();
	}



}
