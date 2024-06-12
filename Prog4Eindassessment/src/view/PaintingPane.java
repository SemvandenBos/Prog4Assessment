package view;

import controller.Controller;
import enums.Biome;
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
	private CustomObjectPainter houseObjectPainter;

	private Controller controller;
	private Pane skyPane;

	public PaintingPane(World world, Controller controller, Pane wrapper) {
		this.controller = controller;
		skyPane = new Pane();

		leafTreePainter = new LeafTreePainter();
		pineTreePainter = new PineTreePainter();
		houseObjectPainter = new CustomObjectPainter();

		getChildren().add(skyPane);

		setBiomeLayout(world.getCurrentBiome());
		setBindings(wrapper);
		setListeners(world);
	}

	private void setBindings(Pane wrapper) {
		prefHeightProperty().bind(wrapper.heightProperty());
		prefWidthProperty().bind(wrapper.widthProperty());
		skyPane.prefHeightProperty().bind(wrapper.heightProperty().divide(2));
		skyPane.prefWidthProperty().bind(wrapper.widthProperty());
	}

	private void setListeners(World world) {
		world.treesProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) {
				getChildren().clear();
				getChildren().add(skyPane);
			}
		});
		world.treesProperty().addListener((ListChangeListener<MovableObject>) change -> {
			while (change.next()) {
				int index = change.getFrom();
				if (change.wasAdded()) {
					for (MovableObject tree : change.getAddedSubList()) {
						addSingleMovableObject(tree, index);
					}
					return;
				}
				if (getChildren().size() > 1) {
					getChildren().remove(index + 1);
				}
			}
		});

		world.selectedBiomeProperty().addListener((ov, o, n) -> {
			Biome biome = world.getCurrentBiome();
			setBiomeLayout(biome);
		});
	}

	private void setBiomeLayout(Biome biome) {
		Color skyColor = Color.valueOf(biome.getSkyColor());
		Color groundColor = Color.valueOf(biome.getGroundColor());
		skyPane.setBackground(new Background(new BackgroundFill(skyColor, null, null)));
		this.setBackground(new Background(new BackgroundFill(groundColor, null, null)));
	}

	// TODO hoe en wat? niet chill
	private void addSingleMovableObject(MovableObject tree, int index) {
		Pane p = null;
		switch (tree.getMovableObjectType()) {
		case TREE:
			p = addSingleTree((Tree) tree);
			break;
		case BUSH:
			p = pineTreePainter.paintMovableObject(tree, widthProperty(), heightProperty(), controller);
			break;
		case HOUSE:
			p = houseObjectPainter.paintMovableObject(tree, widthProperty(), heightProperty(), controller);
			break;
		default:
			break;
		}
		getChildren().add(index + 1, p);
	}

	private Pane addSingleTree(Tree tree) {
		Pane p = null;
		switch (tree.getTreeType()) {
		case LEAF:
			p = leafTreePainter.paintMovableObject(tree, widthProperty(), heightProperty(), controller);
			break;
		case PINE:
			p = pineTreePainter.paintMovableObject(tree, widthProperty(), heightProperty(), controller);
			break;
		}
		return p;
	}
}
