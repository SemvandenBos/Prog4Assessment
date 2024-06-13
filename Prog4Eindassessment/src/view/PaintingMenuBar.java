package view;

import controller.Controller;
import enums.MovableObjectType;
import enums.TreeType;
import javafx.application.Platform;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;

public class PaintingMenuBar extends MenuBar {
	private static final double FONT_SIZE = 24.0;

	private Controller controller;
	private PaintingScene paintingScene;

	public PaintingMenuBar(Controller mainController, PaintingScene scene) {
		this.controller = mainController;
		this.paintingScene = scene;
		createMenuBar();
	}

	private void createMenuBar() {
		Menu fileMenu = createFileMenu();
		Menu treeMenu = createTreeMenu();
		Menu autographMenu = createAutographMenu();
		Menu movieMenu = createMovieMenu();
		Menu extraObjectsMenu = createWesternObjectMenu();

		getMenus().addAll(fileMenu, treeMenu, autographMenu, movieMenu, extraObjectsMenu);
	}

	private Menu createFileMenu() {
		MenuItem loadItem = new MenuItem("load painting...");
		loadItem.setOnAction(e -> controller.loadPainting());

		MenuItem saveAsItem = new MenuItem("save painting as...");
		saveAsItem.setOnAction(e -> controller.savePainting());

		MenuItem exitItem = new MenuItem("exit");
		exitItem.setOnAction(e -> Platform.exit());

		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(loadItem, saveAsItem, exitItem);
		return fileMenu;
	}

	private Menu createTreeMenu() {
		MenuItem addLeafItem = new MenuItem("add Leaf Tree");
		addLeafItem.setOnAction(e -> controller.addTree(TreeType.LEAF));

		MenuItem addPineItem = new MenuItem("add Pine Tree");
		addPineItem.setOnAction(e -> controller.addTree(TreeType.PINE));

		MenuItem addBatchItem = new MenuItem("add 100 Trees");
		addBatchItem.setOnAction(e -> controller.addTreeBatch(100));

		MenuItem addLeet = new MenuItem("add 1337 Trees");
		addLeet.setOnAction(e -> controller.addTreeBatch(1337));

		MenuItem addThousands = new MenuItem("add 9001 Trees!");
		addThousands.setOnAction(e -> controller.addTreeBatch(9001));

		MenuItem clearItem = new MenuItem("clear all Trees");
		clearItem.setOnAction(e -> controller.clearAllTrees());

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

		quikhandItem.setSelected(true);

		ToggleGroup tg = new ToggleGroup();
		tg.selectedToggleProperty().addListener((ov, o, n) -> handleFontChange(n.getUserData().toString()));
		tg.getToggles().addAll(greatVibesItem, quikhandItem, tommysItem);

		Menu autographMenu = new Menu("Autograph font");
		autographMenu.getItems().addAll(greatVibesItem, quikhandItem, tommysItem);
		return autographMenu;
	}

	private Menu createMovieMenu() {
		CheckMenuItem playItem = new CheckMenuItem("play");
		playItem.setOnAction(e -> controller.toggleMovie());
		MenuItem nextBiome = new MenuItem("next biome");
		nextBiome.setOnAction(e -> controller.nextBiome());

		Menu movieMenu = new Menu("Movie");
		movieMenu.getItems().addAll(playItem, nextBiome);
		return movieMenu;
	}

	private Menu createWesternObjectMenu() {
		MenuItem windmillItem = new MenuItem("windmill");
		windmillItem.setOnAction(e -> controller.addMovableObject(MovableObjectType.WINDMILL));
		MenuItem tumbleWeedItem = new MenuItem("tumbleweed");
		tumbleWeedItem.setOnAction(e -> controller.addMovableObject(MovableObjectType.TUMBLEWEED));
		MenuItem packmanItem = new MenuItem("packmannn");
		packmanItem.setOnAction(e -> controller.addMovableObject(MovableObjectType.PACKMAN));

		Menu extraObjectsMenu = new Menu("extra objects");
		extraObjectsMenu.getItems().addAll(windmillItem, tumbleWeedItem, packmanItem);
		return extraObjectsMenu;
	}

	private void handleFontChange(String fontName) {
		Font f2 = Font.loadFont(getClass().getResourceAsStream("/fonts/" + fontName + ".ttf"), FONT_SIZE);
		paintingScene.handleFontChange(f2);
	}
}
