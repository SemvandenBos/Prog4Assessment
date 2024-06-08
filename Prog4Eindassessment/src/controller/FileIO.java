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
import model.Tree;

public class FileIO {
	private FileChooser fc;
	private BufferedReader br;
	private BufferedWriter bw;

	public FileIO() {
		fc = new FileChooser();
		File f = new File("./Resources/paintings");
		fc.setInitialDirectory(f);
//		fc.setTitle("Open Resource File");
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.painting"));
	}

	public List<Tree> loadPainting() {
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile == null) {
			return null;
		}
		ArrayList<Tree> trees = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(selectedFile));
			String line;
			while ((line = br.readLine()) != null) {
				String[] sem = line.split(":");
				TreeType type = TreeType.valueOf(sem[0].toUpperCase());
				TreeSize size = TreeSize.valueOf(sem[1].toUpperCase());
				double relX = Double.parseDouble(sem[2]);
				double relY = Double.parseDouble(sem[3]);
				trees.add(new Tree(type, size, relX, relY));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trees;
	}

	public void savePainting(List<Tree> trees) {
		File selectedFile = fc.showSaveDialog(null);
		if (selectedFile != null && !selectedFile.exists()) {
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
		try {
			bw = new BufferedWriter(new FileWriter(selectedFile));
			bw.write(builder.toString());
			System.out.println(builder.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
