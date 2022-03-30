package tsp.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.github.sh0nk.matplotlib4j.Plot;

import tsp.algorithms.KRandomAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2d;
import tsp.euc2d.model.Euc2dGraph;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;

public class KRandomSpeedTest {
	
	private final List<Integer> keysEuclid = new ArrayList<>();
	private final List<Double> valuesEuclid = new ArrayList<>();
	private final List<Integer> keysTSP = new ArrayList<>();
	private final List<Double> valuesTSP = new ArrayList<>();
	
	public void testK() throws IOException {
		FileWriter fileWriter = new FileWriter("wyniki_krandom_stale_n_euclid.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		Euc2dGraph graph = new Euc2dGraph((ArrayList<Euc2d>) euc2dImporter.generateRandomInstances(100, 100));
		for(int k = 100; k <= 10000; k+=100) {
			KRandomAlgorithm alg = new KRandomAlgorithm(graph, k);
			Instant start = Instant.now();
			for(int r = 0; r < 100; r++) {
				alg.findSolution();
			}
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			double timeElapsedPerOne = (double)timeElapsed / 1000.0;
			keysEuclid.add(k);
			valuesEuclid.add(timeElapsedPerOne);
			printWriter.printf("%d; %f\n", k, timeElapsedPerOne);
			System.out.println(k);
		}
		Plot plot = Plot.create();

		plot.plot().add(keysEuclid, valuesEuclid).label("normal");
		plot.title("Time of finding path by k-random algorithm, for given Euc2d graph");
		plot.xlabel("number of trials - k").build();
		plot.ylabel("time [ms]");
		plot.legend();
		try {
			plot.show();
		} catch (Throwable ignored) {
		}
		printWriter.close();
		fileWriter.close();
		FileWriter fileWriterTSP = new FileWriter("wyniki_krandom_stale_n_tsp.txt");
	    PrintWriter printWriterTSP = new PrintWriter(fileWriterTSP);
		TSPMatrixImporter TSPImporter = new TSPMatrixImporter();
		MatrixGraph graphTSP = new MatrixGraph(TSPImporter.generateRandomInstances(100, 100));
		for(int k = 100; k <= 10000; k+=100) {
			KRandomAlgorithm alg = new KRandomAlgorithm(graphTSP, k);
			Instant start = Instant.now();
			for(int r = 0; r < 100; r++) {
				alg.findSolution();
			}
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			double timeElapsedPerOne = (double)timeElapsed / 1000.0;
			keysTSP.add(k);
			valuesTSP.add(timeElapsedPerOne);
			printWriterTSP.printf("%d; %f\n", k, timeElapsedPerOne);
			System.out.println(k);
		}
		Plot plotTSP = Plot.create();

		plotTSP.plot().add(keysTSP, valuesTSP).label("normal");
		plotTSP.title("Time of finding path by k-random algorithm, for given TSPMatrix graph");
		plotTSP.xlabel("number of trials - k").build();
		plotTSP.ylabel("time [ms]");
		plotTSP.legend();
		try {
			plotTSP.show();
		} catch (Throwable ignored) {
		}
		printWriterTSP.close();
		fileWriterTSP.close();
	}
	
	public void testN() throws IOException {
		FileWriter fileWriter = new FileWriter("wyniki_krandom_stale_k_euclid.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		Euc2dGraph graph;
		for(int n = 100; n <= 5000; n+=100) {
			graph = new Euc2dGraph((ArrayList<Euc2d>) euc2dImporter.generateRandomInstances(n, n));
			KRandomAlgorithm alg = new KRandomAlgorithm(graph, 100);
			Instant start = Instant.now();
			for(int r = 0; r < 100; r++) {
				alg.findSolution();
			}
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			double timeElapsedPerOne = (double)timeElapsed / 1000.0;
			keysEuclid.add(n);
			valuesEuclid.add(timeElapsedPerOne);
			System.out.println(n);
			printWriter.printf("%d; %f\n", n, timeElapsedPerOne);
		}
		Plot plot = Plot.create();

		plot.plot().add(keysEuclid, valuesEuclid).label("normal");
		plot.title("Time of finding path by k-random algorithm, for given random Euc2D graph and k=100 trials");
		plot.xlabel("n - size of TSP problem (nodes count)").build();
		plot.ylabel("time [ms]");
		plot.legend();
		printWriter.close();
		fileWriter.close();
		
		try {
			plot.show();
		} catch (Throwable ignored) {
		}	
		FileWriter fileWriterTSP = new FileWriter("wyniki_krandom_stale_k_tsp.txt");
	    PrintWriter printWriterTSP = new PrintWriter(fileWriterTSP);
		TSPMatrixImporter TSPImporter = new TSPMatrixImporter();
		MatrixGraph graphTSP;
		for(int n = 100; n <= 5000; n+=100) {
			graphTSP = new MatrixGraph(TSPImporter.generateRandomInstances(n, n));
			KRandomAlgorithm alg = new KRandomAlgorithm(graphTSP, 100);
			Instant start = Instant.now();
			for(int r = 0; r < 100; r++) {
				alg.findSolution();
			}
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			double timeElapsedPerOne = (double)timeElapsed / 1000.0;
			keysTSP.add(n);
			valuesTSP.add(timeElapsedPerOne);
			System.out.println(n);
			printWriterTSP.printf("%d; %f\n", n, timeElapsedPerOne);
		}
		Plot plotTSP = Plot.create();

		plotTSP.plot().add(keysTSP, valuesTSP).label("normal");
		plotTSP.title("Time of finding path by k-random algorithm, for given random TSPMatrix graph of size n and k=100 trials");
		plotTSP.xlabel("n - size of TSP problem (nodes count)").build();
		plotTSP.ylabel("time [ms]");
		plotTSP.legend();
		printWriterTSP.close();
		fileWriterTSP.close();
		
		try {
			plotTSP.show();
		} catch (Throwable ignored) {
		}
	}

}
