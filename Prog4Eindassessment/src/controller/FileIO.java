package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enums.TreeSize;
import enums.TreeType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.MovableObject;
import model.Tree;

public class FileIO {
	private static final int EXPECTED_NR_OF_ARGS = 4;
	private static final String SPLIT_CHARACTER = ":";
	private static final String INITIAL_DIRECTORY = "./Resources/paintings";
	private static final String LINEBREAK = System.lineSeparator();
	private static final int LOWER_X_BOUND = 0;
	private static final int UPPER_X_BOUND = 100;
	private static final int LOWER_Y_BOUND = 50;
	private static final int UPPER_Y_BOUND = 100;

	private FileChooser fc;
	private Stage stage;

	public FileIO(Stage stage) {
		this.stage = stage;
		fc = new FileChooser();
		File initialDirectoryFile = new File(INITIAL_DIRECTORY);
		fc.setInitialDirectory(initialDirectoryFile);
		fc.setTitle("Open .painting file to load");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.painting"));
	}

	public List<Tree> loadPainting() {
		File selectedFile = fc.showSaveDialog(null);
		ArrayList<Tree> trees = new ArrayList<>();
		if (selectedFile == null) {
			return trees;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
			String line;
			int lineNr = 0;
			while ((line = br.readLine()) != null) {
				lineNr++;
				handleLine(line, trees, lineNr);
			}
		} catch (IOException e) {
			return trees;
		}
		return trees;
	}

	private void handleLine(String line, List<Tree> trees, int lineNr) {
		String[] splitString = line.split(SPLIT_CHARACTER);
		if (splitString.length != EXPECTED_NR_OF_ARGS) {
			return;
		}
		try {
			TreeType type = TreeType.valueOf(splitString[0].toUpperCase());
			TreeSize size = TreeSize.valueOf(splitString[1].toUpperCase());
			double relX = Double.parseDouble(splitString[2]);
			double relY = Double.parseDouble(splitString[3]);
			if (relX < LOWER_X_BOUND || relX > UPPER_X_BOUND || relY < LOWER_Y_BOUND || relY > UPPER_Y_BOUND) {
				return;
			}
			trees.add(new Tree(type, size, relX, relY));
		} catch (Exception nfe) {
		}
	}

	// Write to a selected file
	public void savePainting(List<MovableObject> movObjects) {
		File selectedFile = fc.showSaveDialog(stage);
		if (selectedFile == null) {
			return;
		}

		if (!selectedFile.exists()) {
			try {
				selectedFile.createNewFile();
			} catch (IOException e) {
				return;
			}
		}

		StringBuilder builder = new StringBuilder();
		for (MovableObject movObject : movObjects) {
			if (movObject instanceof Tree) {
				builder.append(movObject.toString() + LINEBREAK);
			}
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile))) {
			bw.write(builder.toString());
		} catch (IOException e) {
			return;
		}
	}
}
