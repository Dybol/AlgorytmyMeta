package tsp;


import java.io.FileNotFoundException;

public interface FileImporter {
	void importFile(String pathToFile) throws FileNotFoundException;
}
