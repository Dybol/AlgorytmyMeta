package tsp.tests.tabu;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.github.sh0nk.matplotlib4j.Plot;

import tsp.FileImporter;
import tsp.Graph;
import tsp.algorithms.basic.Algorithm2Opt;
import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.tabu.Tabu2Opt;
import tsp.algorithms.tabu.Tabu2OptWithAspiration;
import tsp.algorithms.tabu.Tabu2OptWithVNS;
import tsp.euc2d.Euc2dImporter;
import tsp.matrix.LowerDiagRowImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
import tsp.matrix.tsp.TSPMatrixImporter;
import tsp.tests.Test;

public class TypesComparisonTest {
	
	private final List<Integer> keys = new ArrayList<>();
	private final List<Double> valuesTabu2Opt = new ArrayList<>();
	private final List<Double> valuesTabu2OptWithVNS = new ArrayList<>();
	private final List<Double> valuesTabu2OptWithAspiration = new ArrayList<>();

	public void test() throws IOException {

		FileImporter TSPMatrixImporter = new TSPMatrixImporter();
		FileImporter eucImporter = new Euc2dImporter();
		FileImporter ATSPMatrixImporter = new ATSPMatrixImporter();
		FileImporter lowerDiagImporter = new LowerDiagRowImporter();
		FileWriter fileWriter = new FileWriter("wyniki_porowananie_algorytmow.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
		
	    
	    
		TSPMatrixImporter.importGraph("instances/bays29.tsp");
		TSPMatrixImporter.importOptimalTour("instances/bays29.opt.tour");
		Graph graph1 = TSPMatrixImporter.getGraph();

		ExtendedNearestNeighborAlgorithm extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph1);
		graph1.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());

		Algorithm2Opt algorithm2Opt = new Algorithm2Opt(graph1);
		Tabu2Opt algorithmBasicTabuInvert = new Tabu2Opt(graph1, 1*60*1000, 1000, false, 7, 0.1, 100, 1);
		Tabu2Opt algorithmBasicTabuSwap = new Tabu2Opt(graph1, 1*60*1000, 1000, false, 7, 0.1, 100, 2);
		Tabu2Opt algorithmBasicTabuInsert = new Tabu2Opt(graph1, 1*60*1000, 1000, false, 7, 0.1, 100, 3);
		Tabu2OptWithAspiration algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph1, 1*60*1000, 1000, false, 7, 0.1, 100, 1);
		Tabu2OptWithAspiration algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph1, 1*60*1000, 1000, false, 7, 0.1, 100, 2);
		Tabu2OptWithAspiration algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph1, 1*60*1000, 1000, false, 7, 0.1, 100, 3);
		Tabu2OptWithVNS algorithmVNSTabu = new Tabu2OptWithVNS(graph1, 1*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("bays29.tsp");
		Integer[] sol2Opt = algorithm2Opt.findSolution();
		Integer[] solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		Integer[] solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		Integer[] solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		Integer[] solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		Integer[] solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		Integer[] solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		Integer[] solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph1.pathLength(sol2Opt) + ";" + graph1.pathLength(solTabuBasicInvert) + ";" + graph1.pathLength(solTabuBasicSwap) + ";" + graph1.pathLength(solTabuBasicInsert) 
		+ ";" + graph1.pathLength(solTabuAspirationInvert) + ";" + graph1.pathLength(solTabuAspirationSwap) + ";" + graph1.pathLength(solTabuAspirationInsert) + ";" + graph1.pathLength(solTabuVNS)
		+ ";" + graph1.pathLength(graph1.getOptimalPath()));
		printWriter.flush();
		lowerDiagImporter.importGraph("instances/gr48.tsp");
		lowerDiagImporter.importOptimalTour("instances/gr48.opt.tour");
		
		Graph graph2 = lowerDiagImporter.getGraph();

		extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph2);
		graph2.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());

		algorithm2Opt = new Algorithm2Opt(graph2);
		algorithmBasicTabuInvert = new Tabu2Opt(graph2, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmBasicTabuSwap = new Tabu2Opt(graph2, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmBasicTabuInsert = new Tabu2Opt(graph2, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph2, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph2, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph2, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmVNSTabu = new Tabu2OptWithVNS(graph2, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("gr48.tsp");
		sol2Opt = algorithm2Opt.findSolution();
		solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph2.pathLength(sol2Opt) + ";" + graph2.pathLength(solTabuBasicInvert) + ";" + graph2.pathLength(solTabuBasicSwap) + ";" + graph2.pathLength(solTabuBasicInsert) 
		+ ";" + graph2.pathLength(solTabuAspirationInvert) + ";" + graph2.pathLength(solTabuAspirationSwap) + ";" + graph2.pathLength(solTabuAspirationInsert) + ";" + graph2.pathLength(solTabuVNS)
		+ ";" + graph2.pathLength(graph2.getOptimalPath()));
		
		printWriter.flush();
		lowerDiagImporter.importGraph("instances/gr120.tsp");
		lowerDiagImporter.importOptimalTour("instances/gr120.opt.tour");
		Graph graph3 = lowerDiagImporter.getGraph();

		extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph3);
		graph3.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());

		algorithm2Opt = new Algorithm2Opt(graph3);
		algorithmBasicTabuInvert = new Tabu2Opt(graph3, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmBasicTabuSwap = new Tabu2Opt(graph3, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmBasicTabuInsert = new Tabu2Opt(graph3, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph3, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph3, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph3, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmVNSTabu = new Tabu2OptWithVNS(graph3, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("gr120.tsp");
		sol2Opt = algorithm2Opt.findSolution();
		solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph3.pathLength(sol2Opt) + ";" + graph3.pathLength(solTabuBasicInvert) + ";" + graph3.pathLength(solTabuBasicSwap) + ";" + graph3.pathLength(solTabuBasicInsert) 
		+ ";" + graph3.pathLength(solTabuAspirationInvert) + ";" + graph3.pathLength(solTabuAspirationSwap) + ";" + graph3.pathLength(solTabuAspirationInsert) + ";" + graph3.pathLength(solTabuVNS)
		+ ";" + graph3.pathLength(graph3.getOptimalPath()));
		
		
		printWriter.flush();
		eucImporter.importGraph("instances/berlin52.tsp");
		eucImporter.importOptimalTour("instances/berlin52.opt.tour");
		Graph graph4 = eucImporter.getGraph();
		extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph4);
		graph4.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
		
		
		algorithm2Opt = new Algorithm2Opt(graph4);
		algorithmBasicTabuInvert = new Tabu2Opt(graph4, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmBasicTabuSwap = new Tabu2Opt(graph4, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmBasicTabuInsert = new Tabu2Opt(graph4, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph4, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph4, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph4, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmVNSTabu = new Tabu2OptWithVNS(graph4, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("berlin52.tsp");
		sol2Opt = algorithm2Opt.findSolution();
		solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph4.pathLength(sol2Opt) + ";" + graph4.pathLength(solTabuBasicInvert) + ";" + graph4.pathLength(solTabuBasicSwap) + ";" + graph4.pathLength(solTabuBasicInsert) 
		+ ";" + graph4.pathLength(solTabuAspirationInvert) + ";" + graph4.pathLength(solTabuAspirationSwap) + ";" + graph4.pathLength(solTabuAspirationInsert) + ";" + graph4.pathLength(solTabuVNS)
		+ ";" + graph4.pathLength(graph4.getOptimalPath()));
		
		
		printWriter.flush();
		eucImporter.importGraph("instances/eil76.tsp");
		eucImporter.importOptimalTour("instances/eil76.opt.tour");
		Graph graph5 = eucImporter.getGraph();
		extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph5);
		graph5.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
		
		algorithm2Opt = new Algorithm2Opt(graph5);
		algorithmBasicTabuInvert = new Tabu2Opt(graph5, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmBasicTabuSwap = new Tabu2Opt(graph5, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmBasicTabuInsert = new Tabu2Opt(graph5, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph5, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph5, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph5, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmVNSTabu = new Tabu2OptWithVNS(graph5, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("eil76.tsp");
		sol2Opt = algorithm2Opt.findSolution();
		solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph5.pathLength(sol2Opt) + ";" + graph5.pathLength(solTabuBasicInvert) + ";" + graph5.pathLength(solTabuBasicSwap) + ";" + graph5.pathLength(solTabuBasicInsert) 
		+ ";" + graph5.pathLength(solTabuAspirationInvert) + ";" + graph5.pathLength(solTabuAspirationSwap) + ";" + graph5.pathLength(solTabuAspirationInsert) + ";" + graph5.pathLength(solTabuVNS)
		+ ";" + graph5.pathLength(graph5.getOptimalPath()));
		
		
		
		printWriter.flush();
		eucImporter.importGraph("instances/eil101.tsp");
		eucImporter.importOptimalTour("instances/eil101.opt.tour");
		Graph graph6 = eucImporter.getGraph();
		extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph6);
		graph6.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
		
		algorithm2Opt = new Algorithm2Opt(graph6);
		algorithmBasicTabuInvert = new Tabu2Opt(graph6, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmBasicTabuSwap = new Tabu2Opt(graph6, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmBasicTabuInsert = new Tabu2Opt(graph6, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph6, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph6, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph6, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmVNSTabu = new Tabu2OptWithVNS(graph6, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("eil101.tsp");
		sol2Opt = algorithm2Opt.findSolution();
		solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph6.pathLength(sol2Opt) + ";" + graph6.pathLength(solTabuBasicInvert) + ";" + graph6.pathLength(solTabuBasicSwap) + ";" + graph6.pathLength(solTabuBasicInsert) 
		+ ";" + graph6.pathLength(solTabuAspirationInvert) + ";" + graph6.pathLength(solTabuAspirationSwap) + ";" + graph6.pathLength(solTabuAspirationInsert) + ";" + graph6.pathLength(solTabuVNS)
		+ ";" + graph6.pathLength(graph6.getOptimalPath()));
		
		
		
		printWriter.flush();
		eucImporter.importGraph("instances/ch130.tsp");
		eucImporter.importOptimalTour("instances/ch130.opt.tour");
		Graph graph7 = eucImporter.getGraph();
		extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph7);
		graph7.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
		
		algorithm2Opt = new Algorithm2Opt(graph7);
		algorithmBasicTabuInvert = new Tabu2Opt(graph7, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmBasicTabuSwap = new Tabu2Opt(graph7, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmBasicTabuInsert = new Tabu2Opt(graph7, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph7, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph7, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph7, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmVNSTabu = new Tabu2OptWithVNS(graph7, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("ch130.tsp");
		sol2Opt = algorithm2Opt.findSolution();
		solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph7.pathLength(sol2Opt) + ";" + graph7.pathLength(solTabuBasicInvert) + ";" + graph7.pathLength(solTabuBasicSwap) + ";" + graph7.pathLength(solTabuBasicInsert) 
		+ ";" + graph7.pathLength(solTabuAspirationInvert) + ";" + graph7.pathLength(solTabuAspirationSwap) + ";" + graph7.pathLength(solTabuAspirationInsert) + ";" + graph7.pathLength(solTabuVNS)
		+ ";" + graph7.pathLength(graph7.getOptimalPath()));
		
		
		
		printWriter.flush();
		ATSPMatrixImporter.importGraph("instances/ftv33.atsp");
		Graph graph8 = ATSPMatrixImporter.getGraph();
		extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph8);
		graph8.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
		
		algorithm2Opt = new Algorithm2Opt(graph8);
		algorithmBasicTabuInvert = new Tabu2Opt(graph8, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmBasicTabuSwap = new Tabu2Opt(graph8, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmBasicTabuInsert = new Tabu2Opt(graph8, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph8, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph8, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph8, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmVNSTabu = new Tabu2OptWithVNS(graph8, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("ftv33.atsp");
		sol2Opt = algorithm2Opt.findSolution();
		solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph8.pathLength(sol2Opt) + ";" + graph8.pathLength(solTabuBasicInvert) + ";" + graph8.pathLength(solTabuBasicSwap) + ";" + graph8.pathLength(solTabuBasicInsert) 
		+ ";" + graph8.pathLength(solTabuAspirationInvert) + ";" + graph8.pathLength(solTabuAspirationSwap) + ";" + graph8.pathLength(solTabuAspirationInsert) + ";" + graph8.pathLength(solTabuVNS));
		
		printWriter.flush();
		ATSPMatrixImporter.importGraph("instances/ftv47.atsp");
		Graph graph9 = ATSPMatrixImporter.getGraph();
		extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph9);
		graph9.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
		
		algorithm2Opt = new Algorithm2Opt(graph9);
		algorithmBasicTabuInvert = new Tabu2Opt(graph9, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmBasicTabuSwap = new Tabu2Opt(graph9, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmBasicTabuInsert = new Tabu2Opt(graph9, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph9, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph9, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph9, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmVNSTabu = new Tabu2OptWithVNS(graph9, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("ftv47.atsp");
		sol2Opt = algorithm2Opt.findSolution();
		solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph9.pathLength(sol2Opt) + ";" + graph9.pathLength(solTabuBasicInvert) + ";" + graph9.pathLength(solTabuBasicSwap) + ";" + graph9.pathLength(solTabuBasicInsert) 
		+ ";" + graph9.pathLength(solTabuAspirationInvert) + ";" + graph9.pathLength(solTabuAspirationSwap) + ";" + graph9.pathLength(solTabuAspirationInsert) + ";" + graph9.pathLength(solTabuVNS));
		
		
		printWriter.flush();
		ATSPMatrixImporter.importGraph("instances/ftv70.atsp");
		Graph graph10 = ATSPMatrixImporter.getGraph();
		extendedNearestNeighborAlgorithm = new ExtendedNearestNeighborAlgorithm(graph10);
		graph10.setCurrentPath(extendedNearestNeighborAlgorithm.findSolution());
		
		algorithm2Opt = new Algorithm2Opt(graph10);
		algorithmBasicTabuInvert = new Tabu2Opt(graph10, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmBasicTabuSwap = new Tabu2Opt(graph10, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmBasicTabuInsert = new Tabu2Opt(graph10, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmAspirationTabuInvert = new Tabu2OptWithAspiration(graph10, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		algorithmAspirationTabuSwap = new Tabu2OptWithAspiration(graph10, 5*60*1000, 1000, false, 7, 0.1, 100, 2);
		algorithmAspirationTabuInsert = new Tabu2OptWithAspiration(graph10, 5*60*1000, 1000, false, 7, 0.1, 100, 3);
		algorithmVNSTabu = new Tabu2OptWithVNS(graph10, 5*60*1000, 1000, false, 7, 0.1, 100, 1);
		
		printWriter.println("ftv70.atsp");
		sol2Opt = algorithm2Opt.findSolution();
		solTabuBasicInvert = algorithmBasicTabuInvert.findSolution();
		solTabuBasicSwap = algorithmBasicTabuSwap.findSolution();
		solTabuBasicInsert = algorithmBasicTabuInsert.findSolution();
		solTabuAspirationInvert = algorithmAspirationTabuInvert.findSolution();
		solTabuAspirationSwap = algorithmAspirationTabuSwap.findSolution();
		solTabuAspirationInsert = algorithmAspirationTabuInsert.findSolution();
		solTabuVNS = algorithmVNSTabu.findSolution();
		printWriter.println(graph10.pathLength(sol2Opt) + ";" + graph10.pathLength(solTabuBasicInvert) + ";" + graph10.pathLength(solTabuBasicSwap) + ";" + graph10.pathLength(solTabuBasicInsert) 
		+ ";" + graph10.pathLength(solTabuAspirationInvert) + ";" + graph10.pathLength(solTabuAspirationSwap) + ";" + graph10.pathLength(solTabuAspirationInsert) + ";" + graph10.pathLength(solTabuVNS));
		printWriter.close();
	}
}
