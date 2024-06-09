package view;

import controller.Controller;
import enums.TreeType;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;

public class TreeMenuBar extends MenuBar {
	private static final double FONT_SIZE = 24.0;

	private Controller mainController;
	private PaintingScene paintingScene;

	public TreeMenuBar(Controller mainController, PaintingScene scene) {
		this.mainController = mainController;
		this.paintingScene = scene;
		createMenuBar();
	}

	private void createMenuBar() {
		Menu fileMenu = createFileMenu();
		Menu treeMenu = createTreeMenu();
		Menu autographMenu = createAutographMenu();
		Menu movieMenu = createMovieMenu();
		Menu extraObjectsMenu = createExtraObjectMenu();

		getMenus().addAll(fileMenu, treeMenu, autographMenu, movieMenu, extraObjectsMenu);
	}

	private Menu createExtraObjectMenu() {
//		MenuItem
		
		Menu extraObjectsMenu = new Menu("extra objects");
		return extraObjectsMenu;
	}

	private Menu createFileMenu() {
		MenuItem loadItem = new MenuItem("load painting...");
		loadItem.setOnAction(e -> handleLoadPainting());
		MenuItem saveAsItem = new MenuItem("save painting as...");
		saveAsItem.setOnAction(e -> handleSavePainting());
		MenuItem exitItem = new MenuItem("exit");
		exitItem.setOnAction(e -> Platform.exit());
		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(loadItem, saveAsItem, exitItem);
		return fileMenu;
	}

	private Menu createTreeMenu() {
		MenuItem addLeafItem = new MenuItem("add Leaf Tree");
		addLeafItem.setOnAction(e -> handleAddObject(TreeType.LEAF));
		MenuItem addPineItem = new MenuItem("add Pine Tree");
		addPineItem.setOnAction(e -> handleAddObject(TreeType.PINE));
		MenuItem addBatchItem = new MenuItem("add 100 Trees");
		addBatchItem.setOnAction(e -> handleAddBatch(3));
		MenuItem addLeet = new MenuItem("add 1337 Trees");
		addLeet.setOnAction(e -> handleAddBatch(1337));
		MenuItem addThousands = new MenuItem("add 9001 Trees!");
		addThousands.setOnAction(e -> handleAddBatch(9001));
		MenuItem clearItem = new MenuItem("clear all Trees");
		clearItem.setOnAction(e -> handleClear());
		Menu treeMenu = new Menu("Tree");
		treeMenu.getItems().addAll(addLeafItem, addPineItem, addBatchItem, addLeet, addThousands, clearItem);
		return treeMenu;
	}

	private Menu createAutographMenu() {
		RadioMenuItem greatVibesItem = new RadioMenuItem("GreatVibes");
		greatVibesItem.setUserData("GreatVibes");
		RadioMenuItem quikhandItem = new RadioMenuItem("Quikhand");
		quikhandItem.setUserData("Quikhand");
		RadioMenuItem tommysItem = new RadioMenuItem("tommys");
		tommysItem.setUserData("tommys");
		ToggleGroup tg = new ToggleGroup();

		tg.selectedToggleProperty().addListener((ov, o, n) -> setToggleGroupListener(n));
		tg.selectToggle(greatVibesItem);
		tg.getToggles().addAll(greatVibesItem, quikhandItem, tommysItem);
		Menu autographMenu = new Menu("Autograph font");
		autographMenu.getItems().addAll(greatVibesItem, quikhandItem, tommysItem);
		return autographMenu;
	}

	private Menu createMovieMenu() {
		MenuItem playItem = new MenuItem("play");
		playItem.setOnAction(e -> handleToggleMovie());
		Menu movieMenu = new Menu("Movie");
		movieMenu.getItems().add(playItem);
		return movieMenu;
	}

	private void setToggleGroupListener(Toggle n) {
		setFontTo(n.getUserData().toString());
	}

	private void setFontTo(String fontName) {
		Font f2 = Font.loadFont(getClass().getResourceAsStream("/fonts/" + fontName + ".ttf"), FONT_SIZE);
		paintingScene.handleFontChange(f2);
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

	private void handleAddBatch(int amountOfTrees) {
		mainController.addTreeBatch(amountOfTrees);
	}

	private void handleAddObject(TreeType type) {
		mainController.addTree(type);
	}

	private void handleClear() {
		mainController.clearAllTrees();
	}
}
