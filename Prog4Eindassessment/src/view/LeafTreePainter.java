package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.MovableObject;
import model.Tree;

public class LeafTreePainter extends TreePainter {

	private static final double LEAF_RADIUS = 30;
	private static final int LEAF_HUE = 113;
	private static final double LEAF_SATURATION = 0.70;
	private static final double LEAF_BRIGHTNESS = 0.70;

	@Override
	public Pane paintMovableObject(MovableObject movableObject, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		Pane p = makeDefaultTreeBase(movableObject, paintingXproperty, paintingYproperty, controller);
		Circle c = new Circle();
		setBlackStroke(c);
		setColorForShapePerSize(c, ((Tree) movableObject).getObjectSize(), LEAF_HUE, LEAF_SATURATION, LEAF_BRIGHTNESS);
		c.radiusProperty().bind(getSizeBindingForConstant(movableObject, LEAF_RADIUS));
		c.layoutYProperty().bind(getSizeBindingForConstant(movableObject, TRUNK_HEIGHT).negate());
		p.getChildren().add(c);
		return p;

	}

}
