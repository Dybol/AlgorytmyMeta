package tsp.matrix;

import tsp.FileImporter;
import tsp.matrix.model.MatrixGraph;
import tsp.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MatrixImporter extends FileImporter {

	private Integer[][] cordTab;

	@Override
	public void importGraph(String pathToFile) throws FileNotFoundException {
		Pair<List<Integer>, Integer> allCordsAndDimension = getAllCordsAndDimension(pathToFile);

		List<Integer> allCoordinates = allCordsAndDimension.getFirst();
		int dimension = allCordsAndDimension.getSecond();

		int xCounter = 0;
		int yCounter = 0;
		for (Integer cord : allCoordinates) {
			cordTab[yCounter][xCounter] = cord;
			xCounter++;
			if (xCounter == dimension) {
				xCounter = 0;
				yCounter++;
				if (yCounter == dimension) break;
			}
		}
		setGraph(new MatrixGraph(cordTab));

		System.out.println("Zaimportowano matrix z sukcesem!");
		printMatrix(cordTab, dimension);
		System.out.println("---------------------------");
	}

	public Pair<List<Integer>, Integer> getAllCordsAndDimension(String pathToFile) throws FileNotFoundException {
		File file = new File(pathToFile);

		List<String> allLines = new ArrayList<>();

		if (!file.exists())
			throw new FileNotFoundException();

		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine())
			allLines.add(scanner.nextLine());

		int dimension = Integer.parseInt(allLines.get(3).split("\\s+")[1]);

		cordTab = new Integer[dimension][dimension];

		List<Integer> allCoordinates = new ArrayList<>();

		//dodajemy wszystko do jednej listy zeby nie bylo problemu z nowymi liniami i splitowaniem
		for (String line : allLines.subList(7, allLines.size())) {
			String[] horizontalCords = line.split(" ");
			for (String cord : horizontalCords) {
				try {
					allCoordinates.add(Integer.parseInt(cord));
					//problemy ze spacjami
				} catch (Exception ignored) {
				}
			}
		}
		scanner.close();

		return new Pair<>(allCoordinates, dimension);
	}

	public Integer[][] getCordTab() {
		return cordTab;
	}

	@Override
	public MatrixGraph getGraph() {
		return (MatrixGraph) super.getGraph();
	}

	public void printMatrix(Integer[][] table, int dimension) {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
}
