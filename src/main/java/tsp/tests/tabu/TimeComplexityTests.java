package tsp.tests.tabu;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import tsp.FileImporter;
import tsp.algorithms.basic.Algorithm2Opt;
import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.tabu.Tabu2Opt;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;
import tsp.matrix.LowerDiagRowImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
import tsp.matrix.tsp.TSPMatrixImporter;

public class TimeComplexityTests {

	public void test() throws IOException {

		FileImporter TSPMatrixImporter = new TSPMatrixImporter();
		Euc2dImporter eucImporter = new Euc2dImporter();
		FileImporter ATSPMatrixImporter = new ATSPMatrixImporter();
		FileImporter lowerDiagImporter = new LowerDiagRowImporter();
		FileWriter fileWriter = new FileWriter("wyniki_zlozonosc_euc2d_new.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    
	    for (int i = 20; i <= 150; i += 10) {
	    	double avgTimeInsert = 0.0;
	    	long timeSumInsert = 0;
	    	double avgTimeSwap = 0.0;
	    	long timeSumSwap = 0;
	    	double avgTimeInvert = 0.0;
	    	long timeSumInvert = 0;
	    	for(int k = 0; k < 30; k++) {
	    		Euc2dGraph graph = new Euc2dGraph((ArrayList<Euc2d>) eucImporter.generateRandomInstances(i, 2*i - 1));
				graph.setCurrentPath(generatePath(i));
				Tabu2Opt algorithm = new Tabu2Opt(graph, 0, 100, true, 7, 0.1, 100, 3);
				Instant before = Instant.now();
				algorithm.findSolution();
				Instant after = Instant.now();
				timeSumInsert += Duration.between(before, after).toMillis();
				
				algorithm = new Tabu2Opt(graph, 0, 100, true, 7, 0.1, 100, 2);
				before = Instant.now();
				algorithm.findSolution();
				after = Instant.now();
				timeSumSwap += Duration.between(before, after).toMillis();
				
				algorithm = new Tabu2Opt(graph, 0, 100, true, 7, 0.1, 100, 1);
				before = Instant.now();
				algorithm.findSolution();
				after = Instant.now();
				timeSumInvert += Duration.between(before, after).toMillis();
	    	}
	    	avgTimeInsert = timeSumInsert / 20.0;
	    	avgTimeSwap = timeSumSwap / 20.0;
	    	avgTimeInvert = timeSumInvert / 20.0;
	    	printWriter.println(i + ";" + avgTimeInvert + ";" + avgTimeSwap + ";" + avgTimeInsert);
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
