package tsp.matrix.tsp;

import tsp.InstanceGenerator;
import tsp.matrix.MatrixImporter;

import java.util.Random;

public class TSPMatrixImporter extends MatrixImporter implements InstanceGenerator<Integer[][]> {
	@Override
	public Integer[][] generateRandomInstances(int dimension, int maxValue) {
		Integer[][] coordinates = new Integer[dimension][dimension];
		Random random = new Random();

		for(int i = 0; i < dimension; i++) {
			coordinates[i][i] = 0;
		}

		for(int i = 0; i < dimension; i++) {
			for(int j = i+1; j < dimension; j++) {
				int value = random.nextInt(maxValue);
				coordinates[i][j] = value;
				coordinates[j][i] = value;
			}
		}

		return coordinates;
	}
}
