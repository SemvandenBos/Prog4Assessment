package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Tree;

public class LeafTreePainter extends TreePainter {

	private static final double LEAF_RADIUS = 20;

	@Override
	public Pane drawTree(Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {
		Pane p = makeDefaultTreeBase(tree, paintingXproperty, paintingYproperty);

		Circle c = new Circle(calculateScreenSize(tree, LEAF_RADIUS));
		tree.getRelYproperty().addListener((e) -> {
			c.setRadius(calculateScreenSize(tree, LEAF_RADIUS));
		});
		c.setFill(Color.GREEN);

		p.getChildren().add(c);

		return p;

	}

}
