package view;

import controller.MainController;
import enums.TreeType;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.World;

public class PaintingScene extends Scene {
	private static final Double AUTHOGRAPH_SPACING = 10.0;
	private static final double FONT_SIZE = 20.0;
	private MainController mainController;
	private PaintingPane paintingPane;
	private MenuBar menuBar;
	private Label autographLabel;

	public PaintingScene(MainController mainController, World world) {
		super(new BorderPane());
		this.mainController = mainController;
		createMenuBar();
		createPanes(world);
	}

	//TODO redundant? messy?
	private void createPanes(World world) {
		BorderPane root = (BorderPane) getRoot();
		root.setPrefSize(800, 600);

		AnchorPane skyPane = new AnchorPane();
		skyPane.prefWidthProperty().bind(root.widthProperty());
		skyPane.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, null, null)));

		autographLabel = new Label("Sem van den Bos");
		handleFontChange("Arial");
		AnchorPane.setBottomAnchor(autographLabel, AUTHOGRAPH_SPACING);
		AnchorPane.setRightAnchor(autographLabel, AUTHOGRAPH_SPACING);

		paintingPane = new PaintingPane(world);
		paintingPane.prefHeightProperty().bind(skyPane.heightProperty().divide(2));
		paintingPane.prefWidthProperty().bind(root.widthProperty());
		AnchorPane.setBottomAnchor(paintingPane, 0.0);

		skyPane.getChildren().addAll(paintingPane, autographLabel);
		root.setTop(menuBar);
		root.setCenter(skyPane);
		setRoot(root);
	}

	// TODO te lang ??
	private void createMenuBar() {

		MenuItem loadItem = new MenuItem("load painting...");
		loadItem.setOnAction(e -> handleLoadPainting());
		MenuItem saveAsItem = new MenuItem("save painting as...");
		saveAsItem.setOnAction(e -> handleSavePainting());
		MenuItem exitItem = new MenuItem("exit");
		exitItem.setOnAction(e -> Platform.exit());
		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(loadItem, saveAsItem, exitItem);

		MenuItem addLeafItem = new MenuItem("add Leaf Tree");
		addLeafItem.setOnAction(e -> handleAddTree(TreeType.LEAF));
		MenuItem addPineItem = new MenuItem("add Pine Tree");
		addPineItem.setOnAction(e -> handleAddTree(TreeType.PINE));
		MenuItem addBatchItem = new MenuItem("add 100 Trees");
		addBatchItem.setOnAction(e -> handleAddBatch());
		MenuItem clearItem = new MenuItem("clear all Trees");
		clearItem.setOnAction(e -> handleClear());
		Menu treeMenu = new Menu("Tree");
		treeMenu.getItems().addAll(addLeafItem, addPineItem, addBatchItem, clearItem);

		RadioMenuItem arialItem = new RadioMenuItem("Arial");
		arialItem.setUserData("Arial");
		RadioMenuItem courierItem = new RadioMenuItem("Courier");
		courierItem.setUserData("Courier");
		RadioMenuItem helveticaItem = new RadioMenuItem("Helvetica");
		helveticaItem.setUserData("Helvetica");
		RadioMenuItem timesItem = new RadioMenuItem("Times New Roman");
		timesItem.setUserData("Times New Roman");
		ToggleGroup tg = new ToggleGroup();
		tg.selectedToggleProperty().addListener((ov, o, n) -> setToggleGroupListener(n));
		tg.getToggles().addAll(arialItem, courierItem, helveticaItem, timesItem);
		Menu autographMenu = new Menu("Autograph font");
		autographMenu.getItems().addAll(arialItem, courierItem, helveticaItem, timesItem);

		MenuItem playItem = new MenuItem("play");
		playItem.setOnAction(e -> handleToggleMovie());
		Menu movieMenu = new Menu("Movie");
		movieMenu.getItems().add(playItem);

		menuBar = new MenuBar(fileMenu, treeMenu, autographMenu, movieMenu);
	}

	private void setToggleGroupListener(Toggle n) {
		handleFontChange(n.getUserData().toString());
	}

	private void handleFontChange(String newFont) {
		autographLabel.setFont(new Font(newFont, FONT_SIZE));
	}

	private void handleLoadPainting() {
		mainController.loadPainting();
	}

	private void handleSavePainting() {
		mainController.savePainting();
	}

	private void handleToggleMovie() {
		mainController.toggleMovie();
	}

	private void handleAddBatch() {
		mainController.addTreeBatch();
	}

	private void handleAddTree(TreeType type) {
		mainController.addTree(type);
	}

	private void handleClear() {
		mainController.clearAllTrees();
	}

}
