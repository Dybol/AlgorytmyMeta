package tsp;

import tsp.algorithms.Algorithm2Opt;
import tsp.algorithms.KRandomAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2dGraph;
import tsp.matrix.LowerDiagRowImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;

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
		
		LowerDiagRowImporter lowerDiagRowImporter = new LowerDiagRowImporter();
		lowerDiagRowImporter.importGraph("gr48.tsp");

		Euc2dGraph graph = euc2dImporter.getGraph();
		System.out.println(graph.calculateDistance(2, 4));

		MatrixGraph matrixGraph = tspMatrixImporter.getGraph();
		System.out.println(matrixGraph.calculateDistance(2, 4));

		//przykładowa ścieżka 1->2->...->76->1
		Integer[] path = new Integer[graph.getNodesCount()];
		for (int i = 0; i < path.length; i++) {
			path[i] = i + 1;
		}

		Integer[] path2 = new Integer[matrixGraph.getNodesCount()];
		for (int i = 0; i < path2.length; i++) {
			path2[i] = i + 1;
		}
		System.out.printf("%f %%\n", graph.PRD(path));
		System.out.printf("%f %%\n", matrixGraph.PRD(path2));

		System.out.println("-------------------------Generowanie losowych instancji--------------------------");

		System.out.println(euc2dImporter.generateRandomInstances(100, 100));
		ATSPMatrixImporter.printMatrix(ATSPMatrixImporter.generateRandomInstances(10, 100), 10);
		System.out.println("---------------------------------------------------------------");
		tspMatrixImporter.printMatrix(tspMatrixImporter.generateRandomInstances(10, 100), 10);

		System.out.println("---------------------------------------------------------------");

		System.out.println("----------------------2OPT dla EUC2D----------------------------");

		Integer[] tab = new Integer[76];
		for (int i = 0; i <= 75; i++) {
			tab[i] = i + 1;
		}
		graph.setCurrentPath(tab);
		Algorithm2Opt alg = new Algorithm2Opt(graph);
		System.out.println(graph.pathLength(graph.getCurrentPath()));
		System.out.println(graph.pathLength(alg.findSolution()));

		System.out.println("2opt: " + graph.pathLength(graph.getCurrentPath()));
		System.out.println("Optymalna: " + graph.pathLength(alg.findSolution()));

		System.out.println("----------------------2OPT dla Matrix----------------------------");

		Integer[] tab2 = new Integer[matrixGraph.getNodesCount()];
		for (int i = 0; i < matrixGraph.getNodesCount(); i++) {
			tab2[i] = i + 1;
		}

		matrixGraph.setCurrentPath(tab2);
		Algorithm2Opt alg2 = new Algorithm2Opt(matrixGraph);
		System.out.println("2opt: " + matrixGraph.pathLength(matrixGraph.getCurrentPath()));
		System.out.println("Optymalna: " + matrixGraph.pathLength(alg2.findSolution()));

		System.out.println("----------------------K-random dla EUC2D----------------------------");
		KRandomAlgorithm kRandomAlgorithm = new KRandomAlgorithm(graph, 100000);
		System.out.println("k-random: " + graph.pathLength(kRandomAlgorithm.findSolution()));
		System.out.println("Optymalna: " + graph.pathLength(graph.getOptimalPath()));


		System.out.println("----------------------K-random dla Matrixs----------------------------");
		KRandomAlgorithm kRandomAlgorithm2 = new KRandomAlgorithm(matrixGraph, 100000);
		System.out.println("k-random: " + matrixGraph.pathLength(kRandomAlgorithm2.findSolution()));
		System.out.println("Optymalna: " + matrixGraph.pathLength(matrixGraph.getOptimalPath()));

	}
}
