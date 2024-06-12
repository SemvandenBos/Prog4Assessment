package view;

import controller.Controller;
import enums.TreeType;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
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

	private Menu createFileMenu() {
		MenuItem loadItem = new MenuItem("load painting...");
		loadItem.setOnAction(e -> mainController.loadPainting());

		MenuItem saveAsItem = new MenuItem("save painting as...");
		saveAsItem.setOnAction(e -> mainController.savePainting());

		MenuItem exitItem = new MenuItem("exit");
		exitItem.setOnAction(e -> Platform.exit());

		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(loadItem, saveAsItem, exitItem);
		return fileMenu;
	}

	private Menu createTreeMenu() {
		MenuItem addLeafItem = new MenuItem("add Leaf Tree");
		addLeafItem.setOnAction(e -> mainController.addTree(TreeType.LEAF));

		MenuItem addPineItem = new MenuItem("add Pine Tree");
		addPineItem.setOnAction(e -> mainController.addTree(TreeType.PINE));

		MenuItem addBatchItem = new MenuItem("add 100 Trees");
		addBatchItem.setOnAction(e -> mainController.addTreeBatch(100));

		MenuItem addLeet = new MenuItem("add 1337 Trees");
		addLeet.setOnAction(e -> mainController.addTreeBatch(1337));

		MenuItem addThousands = new MenuItem("add 9001 Trees!");
		addThousands.setOnAction(e -> mainController.addTreeBatch(9001));

		MenuItem clearItem = new MenuItem("clear all Trees");
		clearItem.setOnAction(e -> mainController.clearAllTrees());

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
		MenuItem playItem = new MenuItem("play");
		playItem.setOnAction(e -> mainController.toggleMovie());

		Menu movieMenu = new Menu("Movie");
		movieMenu.getItems().add(playItem);
		return movieMenu;
	}

	private Menu createExtraObjectMenu() {
		MenuItem orbItem = new MenuItem("orb");
		orbItem.setOnAction(e -> mainController.addOrb());

		Menu extraObjectsMenu = new Menu("extra objects");
		extraObjectsMenu.getItems().addAll(orbItem);
		return extraObjectsMenu;
	}

	private void handleFontChange(String fontName) {
		Font f2 = Font.loadFont(getClass().getResourceAsStream("/fonts/" + fontName + ".ttf"), FONT_SIZE);
		paintingScene.handleFontChange(f2);
	}
}
