package tsp.tests.tabu;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;
import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.tabu.Tabu2Opt;
import tsp.algorithms.tabu.Tabu2OptWithAspiration;
import tsp.algorithms.tabu.Tabu2OptWithVNS;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;

public class AspirationTest {
	public void test() throws IOException {

		TSPMatrixImporter TSPMatrixImporter = new TSPMatrixImporter();
		FileWriter fileWriter = new FileWriter("wyniki_aspiration_tsp.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    
	    for(int i = 20; i <= 200; i+=10) {
	    	double [] results = new double [20];
	    	double [] resultsAspiration = new double [20];
	    	double avgPRDAspiration = 0.0;
	    	for(int k = 0; k < 20; k++) {
	    		MatrixGraph graph = new MatrixGraph(TSPMatrixImporter.generateRandomInstances(i, 2*i - 1));
	    		ExtendedNearestNeighborAlgorithm startAlg = new ExtendedNearestNeighborAlgorithm(graph);
				graph.setCurrentPath(startAlg.findSolution());
				
				Tabu2Opt algorithm = new Tabu2Opt(graph, 300000, 1000, false, 7, 0.1, 50, 1);
				Integer [] sol = algorithm.findSolution();
				results[k] = graph.pathLength(sol);
				graph.setOptimalPath(sol);
				
				Tabu2OptWithAspiration algorithmAspiration = new Tabu2OptWithAspiration(graph, 300000, 1000, false, 7, 0.1, 50, 1);
				Integer [] solAspiration = algorithmAspiration.findSolution();
				avgPRDAspiration += graph.PRD(solAspiration);
				resultsAspiration[k] = graph.pathLength(solAspiration);
				
	    	}
	    	WilcoxonSignedRankTest test = new WilcoxonSignedRankTest();
			double pLevel = test.wilcoxonSignedRankTest(results, resultsAspiration, true);
	    	avgPRDAspiration /= 20.0;
	    	
	    	printWriter.println(i + ";" + avgPRDAspiration + ";" + pLevel);
	    	System.out.println(i);
	    	printWriter.flush();
	    }
	    printWriter.close();
	}
}
