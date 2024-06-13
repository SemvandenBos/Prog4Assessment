package controller;

import enums.MovableObjectType;
import enums.TreeType;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.MovableObject;
import model.World;
import view.PaintingScene;

public class Controller extends Application {
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
		fileIO = new FileIO(stage);
		world = new World();
		paintingScene = new PaintingScene(this, world);
		new SoundHandler(world);
	}

	private void stageSettings() {
		stage.setTitle("Sem van den Bos - Painting");
		stage.setScene(paintingScene);
		stage.centerOnScreen();
		stage.show();
	}

//	-----------MVC Methods passings-------------
	public void addTree(TreeType type) {
		world.addTreeOfType(type);
	}

	public void clearAllTrees() {
		world.clearAllTrees();
	}

	public void addTreeBatch(int amountOfTrees) {
		world.addRandomTrees(amountOfTrees);
	}

	public void toggleMovie() {
		world.toggleMovie();
	}

	public void loadPainting() {
		world.addTreeList(fileIO.loadPainting());
	}

	public void savePainting() {
		fileIO.savePainting(world.treesProperty().get());
	}

	public void keyPressed(KeyCode keyCode) {
		world.keyPressed(keyCode);
	}

	public void addMovableObject(MovableObjectType type) {
		world.addMovableObject(type);
	}

	public void setRelPoint(double xRel, double yRel, MovableObject movableObject) {
		world.setRelPoint(xRel, yRel, movableObject);
	}

	public void adjustDepth(MovableObject movableObject) {
		world.adjustDepth(movableObject);
	}

	public void nextBiome() {
		world.nextBiome();
	}
}
