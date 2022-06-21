package tsp;

import tsp.algorithms.basic.ExtendedNearestNeighborAlgorithm;
import tsp.algorithms.genetic.GeneticAlgorithm;
import tsp.euc2d.Euc2dImporter;
import tsp.euc2d.model.Euc2dGraph;
import tsp.matrix.LowerDiagRowImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;
import tsp.tests.genetics.ProbabilityOfMutationTest;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadData {
	public static void main(String[] args) throws FileNotFoundException {
//		chooseOption();
		new ProbabilityOfMutationTest().test();
//		testGenetic();

	}


//	public static void testCrossover() {
//		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100);
//		List<Pair<Integer[], Integer[]>> parents = geneticAlgorithm.generateParents();
//		for (Pair<Integer[], Integer[]> p : parents) {
//			printSolution(p.getFirst());
//			printSolution(p.getSecond());
//			System.out.println("---------");
//		}
//		List<Integer[]> crossover = geneticAlgorithm.crossover(parents);
//		for (Integer[] c : crossover) {
//			int sum = 0;
//			printSolution(c);
//			for (int x : c) {
//				sum += x;
//			}
//			System.out.println(sum);
//		}
//	}

	public static void testGenetic() throws FileNotFoundException {
		FileImporter lowerDiagImporter = new Euc2dImporter();
		lowerDiagImporter.importGraph("instances/eil101.tsp");
//		lowerDiagImporter.importOptimalTour("instances/gr120.opt.tour");
		Graph graph1 = lowerDiagImporter.getGraph();
		graph1.setOptimalPathLength(629.0);
		GeneticAlgorithm alg = new GeneticAlgorithm(graph1, 50, 0.20, 1.0, 1, 2, 1, 1000 * 300, 100, true);
		alg.findSolution();
	}


	private static void chooseOption() throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Wczytaj rodzaj problemu:");
		System.out.println("1 - euc2d");
		System.out.println("2 - atsp matrix");
		System.out.println("3 - tsp matrix");

		String problemNumber = scanner.nextLine();
		FileImporter fileImporter;

		switch (problemNumber) {
			case "1" -> fileImporter = new Euc2dImporter();
			case "2" -> fileImporter = new ATSPMatrixImporter();
			case "3" -> {
				System.out.println("Chcesz wczytac full matrix, czy lower diag?");
				System.out.println("1 - lower diag");
				System.out.println("2 - full matrix");

				String choose = scanner.nextLine();
				if (choose.equals("1"))
					fileImporter = new LowerDiagRowImporter();
				else if (choose.equals("2"))
					fileImporter = new TSPMatrixImporter();
				else {
					System.out.println("bledny wybor!");
					return;
				}
			}
			default -> {
				System.out.println("blad");
				return;
			}
		}

		System.out.println("Aby wczytac instancje, wybierz 1.");
		System.out.println("Aby wygenerowac instancje, wybierz 2.");

		String num = scanner.nextLine();
		if (num.equals("1")) {
			System.out.println("Podaj sciezke do pliku");
			String path = scanner.nextLine();
			fileImporter.importGraph(path);

			System.out.println("Chcesz zaimportowac optymalna sciezke?");
			System.out.println("1 - tak");
			System.out.println("2 - nie");
			String choose = scanner.nextLine();
			if (choose.equals("1")) {

				System.out.println("Chcesz wprowadzic optymalna dlugosc recznie, czy korzystajac z pliku?");
				System.out.println("1 - recznie");
				System.out.println("2 - z pliku");
				String optPathLengthChoose = scanner.nextLine();

				if (optPathLengthChoose.equals("1")) {
					System.out.println("Podaj optymalna dlugosc");
					String optPathLength = scanner.nextLine();
					fileImporter.getGraph().setOptimalPathLength(Double.parseDouble(optPathLength));
				} else if (optPathLengthChoose.equals("2")) {
					System.out.println("Podaj sciezke do optymalnego rozwiazania");
					String pathToTour = scanner.nextLine();
					fileImporter.importOptimalTour(pathToTour);
				}

			}

		} else if (num.equals("2")) {
			System.out.println("Podaj rozmiar grafu");
			String numOfInstances = scanner.nextLine();
			System.out.println("Podaj maksymalna wartosc");
			String maxValue = scanner.nextLine();
			if (fileImporter instanceof Euc2dImporter) {
				Graph graph = new Euc2dGraph(((Euc2dImporter) fileImporter).generateRandomInstances(Integer.parseInt(numOfInstances), Integer.parseInt(maxValue)));
				fileImporter.setGraph(graph);
			} else if (fileImporter instanceof ATSPMatrixImporter) {
				Graph graph = new MatrixGraph(((ATSPMatrixImporter) fileImporter).generateRandomInstances(Integer.parseInt(numOfInstances), Integer.parseInt(maxValue)));
				fileImporter.setGraph(graph);
			} else {
				Graph graph = new MatrixGraph(((TSPMatrixImporter) fileImporter).generateRandomInstances(Integer.parseInt(numOfInstances), Integer.parseInt(maxValue)));
				fileImporter.setGraph(graph);
			}
		} else {
			System.out.println("Zly numer");
			return;
		}

		GeneticAlgorithm algorithm;

		System.out.println("Co ma byc kryterium stopu?");
		System.out.println("1 - ilosc iteracji");
		System.out.println("2 - czas dzialania algorytmu");

		String stop = scanner.nextLine();

		String iterations = null;
		String millis = null;
		if ("1".equals(stop)) {
			System.out.println("Podaj liczbe maksymalnych iteracji");
			iterations = scanner.nextLine();
		} else if ("2".equals(stop)) {
			System.out.println("Podaj ilosc ms, po jakiej program ma sie skonczyc");
			millis = scanner.nextLine();
		}

		System.out.println("Podaj wielkosc populacji");
		String populationSize = scanner.nextLine();

		System.out.println("Podaj prawdopodobienstwo mutacji");
		String probabilityOfMutation = scanner.nextLine();

		System.out.println("Podaj prawdopodobienstwo krzyzowania");
		String probabilityOfCrossover = scanner.nextLine();

		Integer maxIterations = iterations == null ? null : Integer.parseInt(iterations);
		Integer maxMillis = millis == null ? null : Integer.parseInt(millis);

		System.out.println("Podaj typ sasiedzctwa uzyty w algorytmie memetycznym:");
		System.out.println("1 - invert");
		System.out.println("2 - swap");
		System.out.println("3 - insert");

		String neighbourhoodTypeInMemetic = scanner.nextLine();

		System.out.println("Podaj typ operatora mutacji:");
		System.out.println("1 - invert");
		System.out.println("2 - swap");
		System.out.println("3 - insert");
		String typeOfMutationOperator = scanner.nextLine();

		algorithm = new GeneticAlgorithm(fileImporter.getGraph(), Integer.parseInt(populationSize), Double.parseDouble(probabilityOfMutation),
				Double.parseDouble(probabilityOfCrossover), Integer.parseInt(neighbourhoodTypeInMemetic),
				Integer.parseInt(typeOfMutationOperator), 1, maxMillis, maxIterations, stop.equals("2"));

		fileImporter.getGraph().setCurrentPath(new ExtendedNearestNeighborAlgorithm(fileImporter.getGraph()).findSolution());

		System.out.println("Algorytm sie uruchamia..............");

		Integer[] sol = algorithm.findSolution();

		printSolution(sol);

		double pathLength = fileImporter.getGraph().pathLength(sol);
		System.out.println("Dlugosc sciezki: " + pathLength);
		if (fileImporter.getGraph().getOptimalPath() != null) {
			System.out.println("\nOptymalna sciezka : ");
			printSolution(fileImporter.getGraph().getOptimalPath());
			System.out.println("Dlugosc optymalnej sciezki: " + fileImporter.getGraph().getOptimalPathLength());
			System.out.println("PRD: " + fileImporter.getGraph().PRD(sol));
		}
		scanner.close();
	}

	private static void printSolution(Integer[] sol) {
		System.out.print("Sciezka: ");
		for (Integer i : sol) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
