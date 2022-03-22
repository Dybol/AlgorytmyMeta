package tsp.matrix;

import tsp.matrix.model.MatrixGraph;
import tsp.matrix.tsp.TSPMatrixImporter;
import tsp.util.Pair;

import java.io.FileNotFoundException;
import java.util.List;

public class LowerDiagRowImporter extends TSPMatrixImporter {

	@Override
	public void importGraph(String pathToFile) throws FileNotFoundException {
		Pair<List<Integer>, Integer> allCordsAndDimension = getAllCordsAndDimension(pathToFile);

		List<Integer> allCoordinates = allCordsAndDimension.getFirst();
		int dimension = allCordsAndDimension.getSecond();

		int xCounter = 0;
		int yCounter = 0;
		for (Integer cord : allCoordinates) {
			getCordTab()[yCounter][xCounter] = cord;
			getCordTab()[xCounter][yCounter] = cord;
			xCounter++;
			if (xCounter == dimension) {
				yCounter++;
				xCounter = yCounter;
				if (yCounter == dimension) break;
			}
		}
		setGraph(new MatrixGraph(getCordTab()));

		System.out.println("Zaimportowano lower diag z sukcesem!");
		printMatrix(getCordTab(), dimension);
		System.out.println("---------------------------");
	}
}
