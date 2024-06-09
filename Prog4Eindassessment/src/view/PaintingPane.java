package view;

import controller.Controller;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.MovableObject;
import model.Tree;
import model.World;

public class PaintingPane extends Pane {
	private LeafTreePainter leafTreePainter;
	private PineTreePainter pineTreePainter;
	private CustomObjectPainter customObjectPainter;

	private Controller controller;

	public PaintingPane(World world, Controller controller) {
		this.controller = controller;
		leafTreePainter = new LeafTreePainter();
		pineTreePainter = new PineTreePainter();

		customObjectPainter = new CustomObjectPainter();
		this.setBackground(new Background(new BackgroundFill(Color.SANDYBROWN, null, null)));
		setListeners(world);
	}

	private void setListeners(World world) {
		world.treesProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) {
				this.getChildren().clear();
			}
		});

		world.treesProperty().addListener((ListChangeListener<MovableObject>) change -> {
			while (change.next()) {
				if (change.wasAdded()) {
					for (MovableObject tree : change.getAddedSubList()) {
						int indexInChildren = change.getFrom();
						addSingleMovableObject(tree, indexInChildren);
					}
				}
			}
		});
	}

	private void addSingleMovableObject(MovableObject tree, int index) {
		Pane p = null;
		switch (tree.getMovableObjectType()) {
		case TREE:
			addSingleTree((Tree) tree);
			p = leafTreePainter.paintMovableObject(tree, widthProperty(), heightProperty(), controller);
			break;
		case BUSH:
			p = pineTreePainter.paintMovableObject(tree, widthProperty(), heightProperty(), controller);
			break;
		case HOUSE:
			p = customObjectPainter.paintMovableObject(tree, widthProperty(), heightProperty(), controller);
			break;
		default:
			break;
		}
		getChildren().add(index, p);
	}

	private Pane addSingleTree(Tree tree) {
		Pane p = null;
		switch (tree.getTreeType()) {
		case LEAF:
			addSingleTree(tree);
			p = leafTreePainter.paintMovableObject(tree, widthProperty(), heightProperty(), controller);
			break;
		case PINE:
			p = pineTreePainter.paintMovableObject(tree, widthProperty(), heightProperty(), controller);
			break;
		}
		return p;
	}
}
