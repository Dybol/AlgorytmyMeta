package tsp.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.github.sh0nk.matplotlib4j.Plot;

import tsp.algorithms.Algorithm2Opt;
import tsp.algorithms.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.KRandomAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;

public class Alg2OPTSpeedTest {

	private final List<Integer> keysEuclid = new ArrayList<>();
	private final List<Double> valuesEuclidRand = new ArrayList<>();
	private final List<Double> valuesEuclidNeigh = new ArrayList<>();
	private final List<Integer> keysTSP = new ArrayList<>();
	private final List<Double> valuesTSPRand = new ArrayList<>();
	private final List<Double> valuesTSPNeigh = new ArrayList<>();
	
	public void testGoodVsBadStart() throws IOException {
		/*
		FileWriter fileWriter = new FileWriter("wyniki_2OPT_sciezki_euclid.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		Euc2dGraph graph; 
		for(int n = 5; n <= 100; n+=5) {
			long timeElapsedRand = 0;
			long timeElapsedNeigh = 0;
			
			for(int r = 0; r < 100; r++) {
				graph = new Euc2dGraph((ArrayList<Euc2d>) euc2dImporter.generateRandomInstances(n, n));
				ExtendedNearestNeighborAlgorithm neighbor = new ExtendedNearestNeighborAlgorithm(graph);
				KRandomAlgorithm random = new KRandomAlgorithm(graph, 100);
				graph.setCurrentPath(random.initRandomArray(graph.getNodesCount()));
				Algorithm2Opt alg = new Algorithm2Opt(graph);
				Instant start = Instant.now();
				alg.findSolution();
				Instant finish = Instant.now();
				timeElapsedRand += Duration.between(start, finish).toMillis();
				graph.setCurrentPath(neighbor.findSolution());
				start = Instant.now();
				alg.findSolution();
				finish = Instant.now();
				timeElapsedNeigh += Duration.between(start, finish).toMillis();
			}

			double timeElapsedPerOneRand = (double)timeElapsedRand / 100.0;
			double timeElapsedPerOneNeigh = (double)timeElapsedNeigh / 100.0;
			keysEuclid.add(n);
			valuesEuclidRand.add(timeElapsedPerOneRand);
			valuesEuclidNeigh.add(timeElapsedPerOneNeigh);
			printWriter.printf("%d; %f; %f\n", n, timeElapsedPerOneRand, timeElapsedPerOneNeigh);
			System.out.println(n);
		}
		printWriter.close();
		fileWriter.close();
		Plot plot = Plot.create();

		plot.plot().add(keysEuclid, valuesEuclidRand).label("Random starting path");
		plot.plot().add(keysEuclid, valuesEuclidNeigh).label("Starting path made by ENN");
		plot.title("Time of finding path by 2-OPT algorithm, for a random Euc2d graph");
		plot.xlabel("n - number of nodes in a graph").build();
		plot.ylabel("time [ms] (avg from 100 random graphs)");
		plot.legend();
		
		try {
			plot.show();
		} catch (Throwable ignored) {
		}

		*/
		
		FileWriter fileWriterTSP = new FileWriter("wyniki_2OPT_sciezki_TSP.txt");
	    PrintWriter printWriterTSP = new PrintWriter(fileWriterTSP);
		TSPMatrixImporter TSPImporter = new TSPMatrixImporter();
		MatrixGraph graphTSP;
		for(int n = 5; n <= 100; n+=5) {
			long timeElapsedRand = 0;
			long timeElapsedNeigh = 0;

			for(int r = 0; r < 100; r++) {
				graphTSP = new MatrixGraph(TSPImporter.generateRandomInstances(n, n));
				ExtendedNearestNeighborAlgorithm neighbor = new ExtendedNearestNeighborAlgorithm(graphTSP);
				KRandomAlgorithm random = new KRandomAlgorithm(graphTSP, 100);
				graphTSP.setCurrentPath(random.initRandomArray(graphTSP.getNodesCount()));
				Algorithm2Opt alg = new Algorithm2Opt(graphTSP);
				Instant start = Instant.now();
				alg.findSolution();
				Instant finish = Instant.now();
				timeElapsedRand += Duration.between(start, finish).toMillis();
				graphTSP.setCurrentPath(neighbor.findSolution());
				start = Instant.now();
				alg.findSolution();
				finish = Instant.now();
				timeElapsedNeigh += Duration.between(start, finish).toMillis();
			}

			double timeElapsedPerOneRand = (double)timeElapsedRand / 10.0;
			double timeElapsedPerOneNeigh = (double)timeElapsedNeigh / 10.0;
			
			keysTSP.add(n);
			valuesTSPRand.add(timeElapsedPerOneRand);
			valuesTSPNeigh.add(timeElapsedPerOneNeigh);
			printWriterTSP.printf("%d; %f; %f\n", n, timeElapsedPerOneRand, timeElapsedPerOneNeigh);
			System.out.println(n);
		}
		Plot plotTSP = Plot.create();

		plotTSP.plot().add(keysTSP, valuesTSPRand).label("Random starting path");
		plotTSP.plot().add(keysTSP, valuesTSPNeigh).label("Starting path made by ENN");
		plotTSP.title("Time of finding path by 2-OPT algorithm, for a random TSPMatrix graph");
		plotTSP.xlabel("n - number of nodes in a graph").build();
		plotTSP.ylabel("time [ms] (avg from 100 random graphs)");
		plotTSP.legend();
		printWriterTSP.close();
		fileWriterTSP.close();
		try {
			plotTSP.show();
		} catch (Throwable ignored) {
		}

	}

}
