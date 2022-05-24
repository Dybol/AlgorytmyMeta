package tsp.tests.tabu;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.tabu.Tabu2OptWithAspiration;
import tsp.algorithms.tabu.Tabu2OptWithVNS;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;

public class NeighborhoodEfficiencyTestsTSP {
	public void test() throws IOException {

		TSPMatrixImporter TSPMatrixImporter = new TSPMatrixImporter();
		FileWriter fileWriter = new FileWriter("wyniki_compare_tsp_normal.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    
	    for(int i = 20; i <= 200; i+=10) {
	    	double avgPRDInsert = 0.0;
	    	double avgPRDSwap = 0.0;
	    	double avgPRDInvert = 0.0;
	    	for(int k = 0; k < 30; k++) {
	    		MatrixGraph graph = new MatrixGraph(TSPMatrixImporter.generateRandomInstances(i, 2*i - 1));
	    		ExtendedNearestNeighborAlgorithm startAlg = new ExtendedNearestNeighborAlgorithm(graph);
				graph.setCurrentPath(startAlg.findSolution());
				Tabu2OptWithVNS algorithm = new Tabu2OptWithVNS(graph, 300000, 1000, false, 7, 0.1, 50, 1);
				graph.setOptimalPath(algorithm.findSolution());
				
				
				Tabu2OptWithAspiration algorithmInsert = new Tabu2OptWithAspiration(graph, 300000, 1000, false, 7, 0.1, 50, 3);
				avgPRDInsert += graph.PRD(algorithmInsert.findSolution());
				
				Tabu2OptWithAspiration algorithmSwap = new Tabu2OptWithAspiration(graph, 300000, 1000, false, 7, 0.1, 50, 2);
				avgPRDSwap += graph.PRD(algorithmSwap.findSolution());
				
				Tabu2OptWithAspiration algorithmInvert = new Tabu2OptWithAspiration(graph, 300000, 1000, false, 7, 0.1, 50, 1);
				avgPRDInvert += graph.PRD(algorithmInvert.findSolution());
				
				
	    	}
	    	
	    	avgPRDInsert /= 30.0;
	    	avgPRDSwap /= 30.0;
	    	avgPRDInvert /= 30.0;
	    	
	    	printWriter.println(i + ";" + avgPRDInvert + ";" + avgPRDSwap + ";" + avgPRDInsert);
	    	printWriter.flush();
	    }
	    printWriter.close();
	}
}
