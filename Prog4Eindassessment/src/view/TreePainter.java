package view;

import controller.Controller;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.MovableObject;

public abstract class TreePainter extends MovableObjectPainter {
	private static final double TRUNK_WIDTH = 20.0;
	protected static final double TRUNK_HEIGHT = 100.0;
//	private static final double TREE_HUE = 0; TODO use in color

	protected Pane makeDefaultTreeBase(MovableObject tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		Pane treePane = makeDefaultMovablePane(tree, paintingXproperty, paintingYproperty, controller);
		// Binding location
		treePane.layoutXProperty().bind(tree.getRelXproperty().multiply(paintingXproperty).divide(100.0));
		treePane.layoutYProperty().bind(tree.getRelYproperty().subtract(50).multiply(paintingYproperty).divide(50.0));

		Rectangle trunk = new Rectangle();
		setBlackStroke(trunk);
		DoubleBinding bindingHeight = getSizeBindingForConstant(tree, TRUNK_HEIGHT);
		DoubleBinding bindingWidth = getSizeBindingForConstant(tree, TRUNK_WIDTH);
		trunk.heightProperty().bind(bindingHeight);
		trunk.widthProperty().bind(bindingWidth);
		trunk.layoutYProperty().bind(bindingHeight.negate());
		trunk.layoutXProperty().bind(bindingWidth.multiply(-0.5));

		treePane.getChildren().add(trunk);
		trunk.setFill(Color.BROWN);
		return treePane;
	}
}
