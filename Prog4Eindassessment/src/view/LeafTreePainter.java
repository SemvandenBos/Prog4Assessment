package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Tree;

public class LeafTreePainter extends TreePainter {

	private static final double LEAF_RADIUS = 20;
	private static final int LEAF_HUE = 113;
	private static final double LEAF_SATURATION = 0.70;
	private static final double LEAF_BRIGHTNESS = 0.70;

	@Override
	public Pane drawTree(Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {
		Pane p = makeDefaultTreeBase(tree, paintingXproperty, paintingYproperty);
		Circle c = new Circle();
		setBlackStroke(c);
		setColorForShapePerSize(c, tree.getTreeSize(), LEAF_HUE, LEAF_SATURATION, LEAF_BRIGHTNESS);
		c.radiusProperty().bind(getSizeBindingForConstant(tree, LEAF_RADIUS));
		c.layoutYProperty().bind(getSizeBindingForConstant(tree, TRUNK_HEIGHT).negate());
		p.getChildren().add(c);
		return p;

	}

}
