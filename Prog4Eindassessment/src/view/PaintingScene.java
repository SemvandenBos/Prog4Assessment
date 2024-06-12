package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.World;

public class PaintingScene extends Scene {

	private static final double PAINTING_WIDTH = 800;
	private static final double PAINTING_HEIGHT = 600;
	private Controller mainController;
	private PaintingMenuBar menuBar;
	private Label autographLabel;

	private Pane centerPane;

	public PaintingScene(Controller controller, World world) {
		super(new BorderPane());
		this.mainController = controller;
		createChildren(controller, world);
		layoutRoot();
		setOnKeyPressed(e -> mainController.keyPressed(e.getCode()));
	}

	private void createChildren(Controller controller, World world) {
		centerPane = new Pane();
		this.autographLabel = new AutographLabel(centerPane);
		menuBar = new PaintingMenuBar(controller, this);

		PaintingPane paintingPane = new PaintingPane(world, controller, centerPane);
		DuckPane duckPane = new DuckPane(world, centerPane);
		centerPane.getChildren().addAll(paintingPane, autographLabel, duckPane);
	}

	// create childrenPanes with right references
	private void layoutRoot() {
		BorderPane root = (BorderPane) getRoot();
		root.setPrefSize(PAINTING_WIDTH, PAINTING_HEIGHT);
		root.setTop(menuBar);
		root.setCenter(centerPane);
		setRoot(root);
	}

	public void handleFontChange(Font newFont) {
		autographLabel.setFont(newFont);
	}
}
