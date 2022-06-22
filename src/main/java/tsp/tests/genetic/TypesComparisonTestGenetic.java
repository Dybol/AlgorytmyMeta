package tsp.tests.genetic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import tsp.FileImporter;
import tsp.Graph;
import tsp.algorithms.basic.Algorithm2Opt;
import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.genetic.GeneticAlgorithm;
import tsp.algorithms.tabu.Tabu2Opt;
import tsp.algorithms.tabu.Tabu2OptWithAspiration;
import tsp.algorithms.tabu.Tabu2OptWithVNS;
import tsp.euc2d.Euc2dImporter;
import tsp.matrix.LowerDiagRowImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
import tsp.matrix.tsp.TSPMatrixImporter;

public class TypesComparisonTestGenetic {

	public void test() throws IOException {

		FileImporter TSPMatrixImporter = new TSPMatrixImporter();
		FileImporter eucImporter = new Euc2dImporter();
		FileImporter ATSPMatrixImporter = new ATSPMatrixImporter();
		FileImporter lowerDiagImporter = new LowerDiagRowImporter();
		FileWriter fileWriter = new FileWriter("wyniki_porowananie_genetyk.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
		
	    
	    
		TSPMatrixImporter.importGraph("instances/bays29.tsp");
		TSPMatrixImporter.importOptimalTour("instances/bays29.opt.tour");
		Graph graph1 = TSPMatrixImporter.getGraph();

		
		
		printWriter.println("bays29.tsp");
		GeneticAlgorithm algInvert = new GeneticAlgorithm(graph1, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		GeneticAlgorithm algSwap = new GeneticAlgorithm(graph1, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		GeneticAlgorithm algInsert = new GeneticAlgorithm(graph1, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		double sumInvert = 0.0;
		double sumSwap = 0.0;
		double sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph1.pathLength(solInvert);
			sumSwap += graph1.pathLength(solSwap);
			sumInsert += graph1.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		
		
		
		lowerDiagImporter.importGraph("instances/gr48.tsp");
		lowerDiagImporter.importOptimalTour("instances/gr48.opt.tour");
		
		Graph graph2 = lowerDiagImporter.getGraph();
		
		printWriter.println("gr48.tsp");
		algInvert = new GeneticAlgorithm(graph2, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		algSwap = new GeneticAlgorithm(graph2, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		algInsert = new GeneticAlgorithm(graph2, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		sumInvert = 0.0;
		sumSwap = 0.0;
		sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph2.pathLength(solInvert);
			sumSwap += graph2.pathLength(solSwap);
			sumInsert += graph2.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		
		
		lowerDiagImporter.importGraph("instances/gr120.tsp");
		lowerDiagImporter.importOptimalTour("instances/gr120.opt.tour");
		Graph graph3 = lowerDiagImporter.getGraph();

		printWriter.println("gr120.tsp");
		algInvert = new GeneticAlgorithm(graph3, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		algSwap = new GeneticAlgorithm(graph3, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		algInsert = new GeneticAlgorithm(graph3, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		sumInvert = 0.0;
		sumSwap = 0.0;
		sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph3.pathLength(solInvert);
			sumSwap += graph3.pathLength(solSwap);
			sumInsert += graph3.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		
		
		
		eucImporter.importGraph("instances/berlin52.tsp");
		eucImporter.importOptimalTour("instances/berlin52.opt.tour");
		Graph graph4 = eucImporter.getGraph();
		
		printWriter.println("berlin52.tsp");
		algInvert = new GeneticAlgorithm(graph4, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		algSwap = new GeneticAlgorithm(graph4, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		algInsert = new GeneticAlgorithm(graph4, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		sumInvert = 0.0;
		sumSwap = 0.0;
		sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph4.pathLength(solInvert);
			sumSwap += graph4.pathLength(solSwap);
			sumInsert += graph4.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		
		
		
		
		eucImporter.importGraph("instances/eil76.tsp");
		eucImporter.importOptimalTour("instances/eil76.opt.tour");
		Graph graph5 = eucImporter.getGraph();
				
		printWriter.println("eil76.tsp");

		algInvert = new GeneticAlgorithm(graph5, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		algSwap = new GeneticAlgorithm(graph5, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		algInsert = new GeneticAlgorithm(graph5, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		sumInvert = 0.0;
		sumSwap = 0.0;
		sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph5.pathLength(solInvert);
			sumSwap += graph5.pathLength(solSwap);
			sumInsert += graph5.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		
		
		eucImporter.importGraph("instances/eil101.tsp");
		eucImporter.importOptimalTour("instances/eil101.opt.tour");
		Graph graph6 = eucImporter.getGraph();
		
		printWriter.println("eil101.tsp");
		
		algInvert = new GeneticAlgorithm(graph6, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		algSwap = new GeneticAlgorithm(graph6, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		algInsert = new GeneticAlgorithm(graph6, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		sumInvert = 0.0;
		sumSwap = 0.0;
		sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph6.pathLength(solInvert);
			sumSwap += graph6.pathLength(solSwap);
			sumInsert += graph6.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		
		
		
		
		eucImporter.importGraph("instances/ch130.tsp");
		eucImporter.importOptimalTour("instances/ch130.opt.tour");
		Graph graph7 = eucImporter.getGraph();
		
		
		printWriter.println("ch130.tsp");
		
		algInvert = new GeneticAlgorithm(graph7, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		algSwap = new GeneticAlgorithm(graph7, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		algInsert = new GeneticAlgorithm(graph7, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		sumInvert = 0.0;
		sumSwap = 0.0;
		sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph7.pathLength(solInvert);
			sumSwap += graph7.pathLength(solSwap);
			sumInsert += graph7.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		
		ATSPMatrixImporter.importGraph("instances/ftv33.atsp");
		Graph graph8 = ATSPMatrixImporter.getGraph();
		
		printWriter.println("ftv33.atsp");
		
		algInvert = new GeneticAlgorithm(graph8, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		algSwap = new GeneticAlgorithm(graph8, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		algInsert = new GeneticAlgorithm(graph8, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		sumInvert = 0.0;
		sumSwap = 0.0;
		sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph8.pathLength(solInvert);
			sumSwap += graph8.pathLength(solSwap);
			sumInsert += graph8.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		
		
		ATSPMatrixImporter.importGraph("instances/ftv47.atsp");
		Graph graph9 = ATSPMatrixImporter.getGraph();
		
		printWriter.println("ftv47.atsp");
		
		algInvert = new GeneticAlgorithm(graph9, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		algSwap = new GeneticAlgorithm(graph9, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		algInsert = new GeneticAlgorithm(graph9, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		sumInvert = 0.0;
		sumSwap = 0.0;
		sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph9.pathLength(solInvert);
			sumSwap += graph9.pathLength(solSwap);
			sumInsert += graph9.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		
		ATSPMatrixImporter.importGraph("instances/ftv70.atsp");
		Graph graph10 = ATSPMatrixImporter.getGraph();
		
		
		printWriter.println("ftv70.atsp");
		algInvert = new GeneticAlgorithm(graph10, 50, 0.20, 1.0, 1, 2, 1, 1000*120, 100, true);
		algSwap = new GeneticAlgorithm(graph10, 50, 0.20, 1.0, 2, 2, 1, 1000*120, 100, true);
		algInsert = new GeneticAlgorithm(graph10, 50, 0.20, 1.0, 3, 2, 1, 1000*120, 100, true);
		sumInvert = 0.0;
		sumSwap = 0.0;
		sumInsert = 0.0;
		for(int k = 0; k < 4; k++) {
			Integer[] solInvert = algInvert.findSolution();
			Integer[] solSwap = algSwap.findSolution();
			Integer[] solInsert = algInsert.findSolution();
			sumInvert += graph10.pathLength(solInvert);
			sumSwap += graph10.pathLength(solSwap);
			sumInsert += graph10.pathLength(solInsert);
		}

		printWriter.println((sumInvert / 4.0) + ";" + (sumSwap / 4.0) + ";" + (sumInsert / 4.0));
		printWriter.flush();
		printWriter.close();
	}
}
