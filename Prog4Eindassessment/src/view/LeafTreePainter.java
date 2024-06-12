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

	@Override
	public Pane paintMovableObject(MovableObject movableObject, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		Pane p = makeDefaultTreeBase(movableObject, paintingXproperty, paintingYproperty, controller);
		Circle c = new Circle();

		// Styling
		setBlackStroke(c);
		c.setFill(adjustHSBcolor(BASE_COLOR, ((Tree) movableObject).getObjectSize()));

		// Binding
		c.radiusProperty().bind(getSizeBindingForConstant(movableObject, LEAF_RADIUS));
		c.layoutYProperty().bind(getSizeBindingForConstant(movableObject, TRUNK_HEIGHT).negate());

		p.getChildren().add(c);

		return p;
	}
}
