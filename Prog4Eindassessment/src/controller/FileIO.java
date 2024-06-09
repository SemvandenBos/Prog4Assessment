package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enums.MovableObjectSize;
import enums.TreeType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Tree;

public class FileIO {
	private static final int EXPECTED_NR_OF_ARGS = 4;
	private FileChooser fc;
	private Stage stage;

	public FileIO(Stage stage) {
		this.stage = stage;
		fc = new FileChooser();
		File f = new File("./Resources/paintings");
		fc.setInitialDirectory(f);
		fc.setTitle("Open .painting file to load");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.painting"));
	}

	public List<Tree> loadPainting() {
		File selectedFile = fc.showOpenDialog(stage);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trees;
	}

	private void handleLine(String line, List<Tree> trees, int lineNr) {
		String[] sem = line.split(":");
		if (sem.length == 0) {
			System.out.println("Empty line at " + lineNr);
			return;
		}
		if (sem.length != EXPECTED_NR_OF_ARGS) {
			System.out.println("Error at " + lineNr + ": nr of args incorrect");
			return;
		}
		try {
			TreeType type = TreeType.valueOf(sem[0].toUpperCase());
			MovableObjectSize size = MovableObjectSize.valueOf(sem[1].toUpperCase());
			double relX = Double.parseDouble(sem[2]);
			double relY = Double.parseDouble(sem[3]);
			if (relX < 0 || relX > 100 || relY < 50 || relY > 100) {
				System.out.println("Coordinates out of bounds at line " + lineNr);
				return;
			}
			trees.add(new Tree(type, size, relX, relY));
		} catch (NumberFormatException nfe) {
			System.out.println("Wrong number on line " + lineNr);
		} catch (IllegalArgumentException iae) {
			System.out.println("Wrong tree type or size on line " + lineNr);
		}
	}

	public void savePainting(List<Tree> trees) {
		File selectedFile = fc.showSaveDialog(stage);
		if (selectedFile == null) {
			return;
		}

		if (!selectedFile.exists()) {
			try {
				selectedFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		StringBuilder builder = new StringBuilder();
		for (Tree tree : trees) {
			builder.append(tree.toString() + "\n");
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile))) {
			bw.write(builder.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
