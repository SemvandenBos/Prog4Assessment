package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.MovableObject;
import model.Tree;

public class LeafTreePainter extends TreePainter {

	private static final double LEAF_RADIUS = 30;
	private static final Color BASE_COLOR = Color.hsb(113, 0.7, 0.7);

	public LeafTreePainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	@Override
	public Pane paintMovableObject(MovableObject tree) {
		Pane p = makeDefaultTreeBase(tree);
		double leafHeight = realSize(tree, -TRUNK_HEIGHT);
		double leafRadius = realSize(tree, LEAF_RADIUS);
		Circle leaves = new Circle(0, leafHeight, leafRadius);

		// Styling
//		setBlackStroke(leaves);
		adjustHSBcolor(leaves, BASE_COLOR, ((Tree) tree).getObjectSize());

		Group group = makeGroup(tree);
		group.getChildren().add(leaves);
		p.getChildren().add(group);
		return p;
	}
}
