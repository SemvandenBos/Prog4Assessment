package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Tree;

public abstract class TreePainter {
	private static final double TRUNK_WIDTH = 20.0;
	private static final double TRUNK_HEIGHT = 100.0;

	protected double calculateScreenSize(Tree tree, double constant) {
		return constant * tree.getTreeSize().getScaleValue() * (Math.pow(tree.getRelY(), 2.5));
	}

	public abstract Pane drawTree(Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty);

	protected Pane makeDefaultTreeBase(Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {
		Pane treePane = new StackPane();
		Rectangle trunk = new Rectangle();

		setMouseDragEvents(treePane, tree, paintingXproperty, paintingYproperty);

		// Binding size TODO (turned out to be unnecessary)
//		double treeSize = tree.getTreeSize().getScaleValue();
//		DoubleBinding widthBinding = paintingXproperty.multiply(TRUNK_WIDTH * treeSize / 500.0);
//		trunk.widthProperty().bind(widthBinding);
//		DoubleBinding heightBinding = paintingYproperty.multiply(TRUNK_HEIGHT * treeSize / 500.0);
//		trunk.heightProperty().bind(heightBinding);

		// constant size //TODO niet waar, moet scalen met drag and drop
		trunk.setWidth(calculateScreenSize(tree, TRUNK_WIDTH));
		trunk.setHeight(calculateScreenSize(tree, TRUNK_HEIGHT));
		tree.getRelYproperty().addListener((e) -> {
			trunk.setWidth(calculateScreenSize(tree, TRUNK_WIDTH));
			trunk.setHeight(calculateScreenSize(tree, TRUNK_HEIGHT));
		});

		// Binding location TODO netter
		treePane.layoutXProperty().bind(tree.getRelXproperty().multiply(paintingXproperty).divide(100.0));
		treePane.layoutYProperty()
				.bind(tree.getRelYproperty().subtract(50).multiply(paintingYproperty).divide(50.0).subtract(50.0));

		treePane.getChildren().add(trunk);
		trunk.setFill(Color.BROWN);
		return treePane;
	}

	private void setMouseDragEvents(Pane treePane, Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {
		treePane.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double xRel = event.getSceneX() / paintingXproperty.get();
				double yRel = event.getSceneY() / paintingYproperty.get();
				tree.setRelPoint(xRel, yRel);
//				System.out.println("rels" + xRel + " " + yRel);
			}
		});
	}
}
