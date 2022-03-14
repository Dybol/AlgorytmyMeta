package tsp;

import tsp.euc2d.Euc2dImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
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
		ATSPMatrixImporter.importGraph("swiss42.tsp");

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

		System.out.println("-------------------------Generowanie losowych instancji--------------------------");

		System.out.println(euc2dImporter.generateRandomInstances(100, 100));
		ATSPMatrixImporter.printMatrix(ATSPMatrixImporter.generateRandomInstances(10, 100), 10);
		System.out.println("---------------------------------------------------------------");
		tspMatrixImporter.printMatrix(tspMatrixImporter.generateRandomInstances(10, 100), 10);
	}
}
