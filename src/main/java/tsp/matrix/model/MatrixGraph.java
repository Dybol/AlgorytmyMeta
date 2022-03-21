package tsp.matrix.model;

import tsp.Graph;

//Ta klasa zadziała zarówno dla symetrycznych i asymetrycznych grafów
public class MatrixGraph extends Graph{
	
	//konwencja: edges[from-1][to-1] jako krawędź od punktu <from> do punktu <to> 
	private final Integer[][] edges;
	
	public MatrixGraph(Integer[][] cordTab) {
		super(cordTab.length);
		this.edges = cordTab;
	}
	
	@Override
	public double calculateDistance(int from, int to) {
		return edges[from-1][to-1];
	}
}
