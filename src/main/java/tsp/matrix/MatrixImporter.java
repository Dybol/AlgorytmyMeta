package tsp.matrix;

import tsp.FileImporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MatrixImporter implements FileImporter {

	private Integer[][] cordTab;

	@Override
	public void importFile(String pathToFile) throws FileNotFoundException {
		File file = new File(pathToFile);

		List<String> allLines = new ArrayList<>();

		if(!file.exists())
			throw new FileNotFoundException();

		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine())
			allLines.add(scanner.nextLine());

		int dimension = Integer.parseInt(allLines.get(3).split(" ")[1]);

		cordTab = new Integer[dimension][dimension];

		List<Integer> allCoordinates = new ArrayList<>();

		//dodajemy wszystko do jednej listy zeby nie bylo problemu z nowymi liniami i splitowaniem
		for(String line: allLines.subList(7, allLines.size())) {
			String[] horizontalCords = line.split(" ");
			for(String cord: horizontalCords) {
				try {
					allCoordinates.add(Integer.parseInt(cord));
					//problemy ze spacjami
				} catch (Exception ignored){}
			}
		}

		int xCounter = 0;
		int yCounter = 0;

		for(Integer cord: allCoordinates) {
			cordTab[yCounter][xCounter] = cord;
			xCounter++;
			if(xCounter == dimension) {
				xCounter = 0;
				yCounter++;
			}
		}

		System.out.println("Zaimportowano z sukcesem!");
		printMatrix(cordTab, dimension);
		System.out.println("---------------------------");
	}

	public void printMatrix(Integer[][] table, int dimension) {
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
}
