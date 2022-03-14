package tsp.matrix.atsp;

import tsp.InstanceGenerator;
import tsp.matrix.MatrixImporter;

import java.util.Random;

public class ATSPMatrixImporter extends MatrixImporter implements InstanceGenerator<Integer[][]> {

	@Override
	public Integer[][] generateRandomInstances(int dimension, int maxValue) {
		Integer[][] coordinates = new Integer[dimension][dimension];
		Random random = new Random();

		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				coordinates[i][j] = random.nextInt(maxValue);
			}
		}

		return coordinates;
	}
}
