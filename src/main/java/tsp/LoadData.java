package tsp;

import tsp.algorithms.Algorithm2Opt;
import tsp.euc2d.Euc2dImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;
import tsp.euc2d.model.Euc2dGraph;

import java.io.FileNotFoundException;

public class LoadData {
	public static void main(String[] args) throws FileNotFoundException {
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		euc2dImporter.importGraph("fl417.tsp");

		ATSPMatrixImporter ATSPMatrixImporter = new ATSPMatrixImporter();
		ATSPMatrixImporter.importGraph("ft53.atsp");

		TSPMatrixImporter tspMatrixImporter = new TSPMatrixImporter();
		tspMatrixImporter.importGraph("bays29.tsp");
		tspMatrixImporter.importOptimalTour("bays29.opt.tour");

		euc2dImporter.importGraph("pr76.tsp");
		euc2dImporter.importOptimalTour("pr76.opt.tour");

		Euc2dGraph graph = euc2dImporter.getGraph();
		System.out.println(graph.calculateDistance(2, 4));
		
		MatrixGraph matrixGraph = tspMatrixImporter.getGraph();
		System.out.println(matrixGraph.calculateDistance(2, 4));

		//przykładowa ścieżka 1->2->...->76->1
		Integer[] path = new Integer[graph.getNodesCount()];
		for(int i = 0; i < path.length; i++) {
			path[i] = i+1;
		}
		
		Integer[] path2 = new Integer[matrixGraph.getNodesCount()];
		for(int i = 0; i < path2.length; i++) {
			path2[i] = i+1;
		}
		System.out.printf("%f %%\n", graph.PRD(path));
		System.out.printf("%f %%\n", matrixGraph.PRD(path2));

		System.out.println("-------------------------Generowanie losowych instancji--------------------------");

		System.out.println(euc2dImporter.generateRandomInstances(100, 100));
		ATSPMatrixImporter.printMatrix(ATSPMatrixImporter.generateRandomInstances(10, 100), 10);
		System.out.println("---------------------------------------------------------------");
		tspMatrixImporter.printMatrix(tspMatrixImporter.generateRandomInstances(10, 100), 10);
		
		Integer[] tab = new Integer[76];
		for(int i = 0; i <= 75; i++) {
			tab[i] = i+1;
		}
		graph.setCurrentPath(tab);
		Algorithm2Opt alg = new Algorithm2Opt(graph);
		System.out.println(graph.pathLength(graph.getCurrentPath()));
		System.out.println(graph.pathLength(alg.findSolution()));
		
	}
}
