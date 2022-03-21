package tsp.euc2d.model;

import java.util.ArrayList;

import tsp.Graph;

public class Euc2dGraph extends Graph{

	private final ArrayList<Euc2d> nodes;

	public Euc2dGraph(ArrayList<Euc2d> nodes) {
		super(nodes.size());
		this.nodes = nodes;
	}
	
	@Override
	public double calculateDistance(int from, int to) {
		return Math.sqrt(Math.pow((nodes.get(from-1).getCordX()-nodes.get(to-1).getCordX()), 2)
				+ Math.pow((nodes.get(from-1).getCordY()-nodes.get(to-1).getCordY()), 2));
	}
}
