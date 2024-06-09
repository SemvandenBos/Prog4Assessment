package view;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.World;

public class PaintingScene extends Scene {
	private static final Double AUTHOGRAPH_SPACING = 10.0;
	private Controller mainController;
	private PaintingPane paintingPane;
	private MenuBar menuBar;
	private Label autographLabel;

	public PaintingScene(Controller controller, World world) {
		super(new BorderPane());
		this.mainController = controller;
		this.menuBar = new TreeMenuBar(controller, this);
		createPanes(world, controller);
		createEventHandlers();
	}

	private void createEventHandlers() {
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				mainController.keyPressed(event.getCode());
			}
		});
	}

	// TODO redundant? messy?
	private void createPanes(World world, Controller controller) {
		BorderPane root = (BorderPane) getRoot();
		root.setPrefSize(800, 600);

		Pane wrapper = new Pane();
		AnchorPane skyPane = new AnchorPane();
		skyPane.prefWidthProperty().bind(wrapper.widthProperty());
		skyPane.prefHeightProperty().bind(wrapper.heightProperty());
		skyPane.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, null, null)));

		autographLabel = new Label("Sem van den Bos");
		autographLabel.setAlignment(Pos.BOTTOM_RIGHT);
		AnchorPane.setBottomAnchor(autographLabel, AUTHOGRAPH_SPACING);
		AnchorPane.setRightAnchor(autographLabel, AUTHOGRAPH_SPACING);

		paintingPane = new PaintingPane(world, controller);
		paintingPane.prefHeightProperty().bind(skyPane.heightProperty().divide(2));
		paintingPane.prefWidthProperty().bind(root.widthProperty());
		AnchorPane.setBottomAnchor(paintingPane, 0.0);

		DuckPane duckPane = new DuckPane(world.getDuck(), wrapper);

		skyPane.getChildren().addAll(paintingPane, autographLabel);
		wrapper.getChildren().addAll(skyPane, duckPane);
		root.setTop(menuBar);
		root.setCenter(wrapper);
		setRoot(root);
	}

	public void handleFontChange(Font newFont) {
		autographLabel.setFont(newFont);
	}
}
