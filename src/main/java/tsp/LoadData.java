package tsp;

import tsp.euc2d.Euc2dImporter;
import tsp.matrix.atsp.ATSPMatrixImporter;
import tsp.matrix.tsp.TSPMatrixImporter;

public class LoadData {
	public static void main(String[] args) throws Exception {
		Euc2dImporter euc2dImporter = new Euc2dImporter();
		euc2dImporter.importFile("fl417.tsp");

		ATSPMatrixImporter ATSPMatrixImporter = new ATSPMatrixImporter();
		ATSPMatrixImporter.importFile("ft53.atsp");

		TSPMatrixImporter tspMatrixImporter = new TSPMatrixImporter();
		ATSPMatrixImporter.importFile("swiss42.tsp");

		System.out.println(euc2dImporter.generateRandomInstances(100, 100));
		ATSPMatrixImporter.printMatrix(ATSPMatrixImporter.generateRandomInstances(10, 100), 10);
		System.out.println("---------------------------------------------------------------");
		tspMatrixImporter.printMatrix(tspMatrixImporter.generateRandomInstances(10, 100), 10);
	}
}
