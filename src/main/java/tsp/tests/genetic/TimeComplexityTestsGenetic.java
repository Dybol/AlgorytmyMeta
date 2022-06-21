package tsp.tests.genetic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import tsp.FileImporter;
import tsp.algorithms.genetic.GeneticAlgorithm;
import tsp.algorithms.tabu.Tabu2Opt;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;

public class TimeComplexityTestsGenetic {

	public void test() throws IOException {

		TSPMatrixImporter TSPMatrixImporter = new TSPMatrixImporter();
//		Euc2dImporter eucImporter = new Euc2dImporter();
		//FileImporter ATSPMatrixImporter = new ATSPMatrixImporter();
		//FileImporter lowerDiagImporter = new LowerDiagRowImporter();
		FileWriter fileWriter = new FileWriter("wyniki_zlozonosc_matrix_genetic.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    
	    for (int i = 20; i <= 150; i += 10) {
	    	double avgTimeInsert = 0.0;
	    	long timeSumInsert = 0;
	    	double avgTimeSwap = 0.0;
	    	long timeSumSwap = 0;
	    	double avgTimeInvert = 0.0;
	    	long timeSumInvert = 0;
	    	for(int k = 0; k < 30; k++) {
	    		MatrixGraph graph = new MatrixGraph(TSPMatrixImporter.generateRandomInstances(i, 2*i - 1));
				graph.setCurrentPath(generatePath(i));
				GeneticAlgorithm algorithm = new GeneticAlgorithm(graph, 50, 0.20, 1.0, 1, 1, 1, 1000*300, 50, false);
				Instant before = Instant.now();
				algorithm.findSolution();
				Instant after = Instant.now();
				timeSumInsert += Duration.between(before, after).toMillis();
				
				algorithm = new GeneticAlgorithm(graph, 50, 0.20, 1.0, 2, 2, 1, 1000*300, 50, false);
				before = Instant.now();
				algorithm.findSolution();
				after = Instant.now();
				timeSumSwap += Duration.between(before, after).toMillis();
				
				algorithm = new GeneticAlgorithm(graph, 50, 0.20, 1.0, 3, 3, 1, 1000*300, 50, false);
				before = Instant.now();
				algorithm.findSolution();
				after = Instant.now();
				timeSumInvert += Duration.between(before, after).toMillis();
	    	}
	    	avgTimeInsert = timeSumInsert / 30.0;
	    	avgTimeSwap = timeSumSwap / 30.0;
	    	avgTimeInvert = timeSumInvert / 30.0;
	    	printWriter.println(i + ";" + avgTimeInvert + ";" + avgTimeSwap + ";" + avgTimeInsert);
	    	printWriter.flush();
	    	System.out.println(i);
		}
	    printWriter.close();
	}
	
	public Integer[] generatePath(int n) {
		Integer[] tab = new Integer[n];
		for (int i = 0; i < n; i++) {
			tab[i] = i + 1;
		}
		return tab;
	}
}
