package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.MovableObject;

public class CustomObjectPainter extends MovableObjectPainter {
	private static final double BALL_RADIUS = 40;

	@Override
	public Pane paintMovableObject(MovableObject movableObject, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		Pane p = makeDefaultMovablePane(movableObject, paintingXproperty, paintingYproperty, controller);
		Circle c = new Circle(BALL_RADIUS);
		c.radiusProperty().bind(getSizeBindingForConstant(movableObject, BALL_RADIUS));
		p.getChildren().add(c);
		return p;
	}

}
