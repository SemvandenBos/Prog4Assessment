package controller;

import enums.TreeType;
import javafx.application.Application;
import javafx.stage.Stage;
import model.World;
import view.PaintingScene;

public class MainController extends Application {
	private Stage stage;
	private World world;
	private PaintingScene paintingScene;
	private FileIO fileIO;

	public void launchApp() {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		createClasses();
		stageSettings();
	}

	private void createClasses() {
		fileIO = new FileIO();
		world = new World();
		paintingScene = new PaintingScene(this, world);
	}

	private void stageSettings() {
		stage.setTitle("Sem van den Bos - Painting");
		stage.setScene(paintingScene);
		stage.centerOnScreen();
		stage.show();
	}

	public void addTree(TreeType type) {
		world.addTree(type);
	}

	public void clearAllTrees() {
		world.clearAllTrees();
	}

	public void addTreeBatch() {
		world.addTreeBatch();
	}

	public void toggleMovie() {
		world.toggleMovie();
	}

	public void loadPainting() {
		world.addTreeBatch(fileIO.loadPainting());
	}

	public void savePainting() {
		fileIO.savePainting(world.getTrees());
	}

}
