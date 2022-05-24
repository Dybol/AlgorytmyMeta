package tsp.tests.tabu;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.tabu.Tabu2OptWithAspiration;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;

public class TenureSizeTestTSP {

	public void test() throws IOException {

		TSPMatrixImporter TSPMatrixImporter = new TSPMatrixImporter();
		FileWriter fileWriter = new FileWriter("wyniki_tenure_tsp_normal.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    
	    for (int i = 20; i <= 120; i += 10) {
	    	double [] PRDAvgConstants = new double[4];
	    	double [] PRDAvgLogs = new double[5];
	    	double [] PRDAvgSqrts = new double[5];
	    	double [] PRDAvgLinears = new double[5];
	    	double [] PRDAvgSquares = new double[5];
	    	
	    	for(int k = 0; k < 15; k++) {
	    		MatrixGraph graph = new MatrixGraph(TSPMatrixImporter.generateRandomInstances(i, 2*i - 1));
	    		ExtendedNearestNeighborAlgorithm startAlg = new ExtendedNearestNeighborAlgorithm(graph);
				graph.setCurrentPath(startAlg.findSolution());
				Tabu2OptWithAspiration algorithm = new Tabu2OptWithAspiration(graph, 10000, 1000, false, 7, 0.1, 50, 1);
				graph.setOptimalPath(algorithm.findSolution());
				
				algorithm = new Tabu2OptWithAspiration(graph, 10000, 1000, false, 3, 0.1, 50, 1);
				PRDAvgConstants[0] += graph.PRD(algorithm.findSolution());
				
				for(int j = 1; j < 4; j++) {
					algorithm = new Tabu2OptWithAspiration(graph, 10000, 1000, false, (7 + 4*j), 0.1, 50, 1);
					PRDAvgConstants[j] += graph.PRD(algorithm.findSolution());
				}
				
				for(int j = 0; j < 5; j++) {
					algorithm = new Tabu2OptWithAspiration(graph, 10000, 1000, false, Integer.valueOf((int) Math.ceil(0.4*(j+1)*Math.log(i))), 0.1, 50, 1);
					PRDAvgLogs[j] += graph.PRD(algorithm.findSolution());
					algorithm = new Tabu2OptWithAspiration(graph, 10000, 1000, false, Integer.valueOf((int) Math.ceil(0.4*(j+1)*Math.sqrt(i))), 0.1, 50, 1);
					PRDAvgSqrts[j] += graph.PRD(algorithm.findSolution());
					algorithm = new Tabu2OptWithAspiration(graph, 10000, 1000, false, Integer.valueOf((int) (0.4*(j+1)*(i))), 0.1, 50, 1);
					PRDAvgLinears[j] += graph.PRD(algorithm.findSolution());
					algorithm = new Tabu2OptWithAspiration(graph, 10000, 1000, false, Integer.valueOf((int) (0.4*(j+1)*i*i)), 0.1, 50, 1);
					PRDAvgSquares[j] += graph.PRD(algorithm.findSolution());
				}
				
				
				
	    	}
	    	for(int j = 0; j < 4; j++) {
	    		PRDAvgConstants[j] /= 15.0;
	    	}
	    	for(int j = 0; j < 5; j++) {
	    		PRDAvgLogs[j] /= 15.0;
	    		PRDAvgSqrts[j] /= 15.0;
	    		PRDAvgLinears[j] /= 15.0;
	    		PRDAvgSquares[j] /= 15.0;
	    	}
	    	
	    	printWriter.println("Constant;" + i + ";" + PRDAvgConstants[0] + ";" + PRDAvgConstants[1] + ";" + PRDAvgConstants[2] + ";" + PRDAvgConstants[3]);
	    	printWriter.println("Log;" + i + ";" + PRDAvgLogs[0] + ";" + PRDAvgLogs[1] + ";" + PRDAvgLogs[2] + ";" + PRDAvgLogs[3] + ";" + PRDAvgLogs[4]);
	    	printWriter.println("Sqrt;" + i + ";" + PRDAvgSqrts[0] + ";" + PRDAvgSqrts[1] + ";" + PRDAvgSqrts[2] + ";" + PRDAvgSqrts[3] + ";" + PRDAvgSqrts[4]);
	    	printWriter.println("Linear;" + i + ";" + PRDAvgLinears[0] + ";" + PRDAvgLinears[1] + ";" + PRDAvgLinears[2] + ";" + PRDAvgLinears[3] + ";" + PRDAvgLinears[4]);
	    	printWriter.println("Square;" + i + ";" + PRDAvgSquares[0] + ";" + PRDAvgSquares[1] + ";" + PRDAvgSquares[2] + ";" + PRDAvgSquares[3] + ";" + PRDAvgSquares[4]);
	    	System.out.println(i);
	    	printWriter.flush();
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
