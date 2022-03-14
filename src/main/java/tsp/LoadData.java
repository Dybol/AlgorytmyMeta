package tsp;

import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2dGraph;

import java.io.FileNotFoundException;

public class LoadData {
	public static void main(String[] args) throws FileNotFoundException {
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		euc2dImporter.importGraph("pr76.tsp");
		euc2dImporter.importOptimalTour("pr76.opt.tour");
		
		
		Euc2dGraph graph = euc2dImporter.getGraph();
		System.out.println(graph.calculateDistance(2, 4));
		
		//przykładowa ścieżka 1->2->...->76->1
		int[] path = new int[graph.getNodesCount()];
		for(int i = 0; i < path.length; i++) {
			path[i] = i+1;
		}
		System.out.printf("%f %%\n", graph.PRD(path));
		
		//TODO: Jak przechowywac rozne dane?
		System.out.println(euc2dImporter.generateRandomInstances(100, 100));
	}
}
