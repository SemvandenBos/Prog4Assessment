package view;

import controller.Controller;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.MovableObject;

public class WindmillPainter extends MovableObjectPainter {
	private static final double WINDMILL_HEIGHT = 100.0;
	private static final double WINDMILL_WIDTH = 20.0;
	private static final double SPINSPEED = 6.0;

	@Override
	public Pane paintMovableObject(MovableObject movableObject, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		Pane p = makeDefaultMovablePane(movableObject, paintingXproperty, paintingYproperty, controller);

		DoubleBinding windMillHeight = getSizeBindingForConstant(movableObject, WINDMILL_HEIGHT);
		DoubleBinding windMillWidth = getSizeBindingForConstant(movableObject, WINDMILL_WIDTH);
		Rectangle windmillBase = new Rectangle();
		windmillBase.setFill(Color.SALMON);

		// Bind windmillbase
		windmillBase.heightProperty().bind(windMillHeight);
		windmillBase.widthProperty().bind(windMillWidth);
		windmillBase.layoutYProperty().bind(windMillHeight.negate());

		// Make blades
		Rectangle first = createMillblades(windMillHeight, windMillWidth, movableObject.getRelXproperty(), true);
		Rectangle second = createMillblades(windMillHeight, windMillWidth, movableObject.getRelXproperty(), false);

		p.getChildren().addAll(windmillBase, first, second);
		return p;
	}

	private Rectangle createMillblades(DoubleBinding windMillHeight, DoubleBinding windMillWidth,
			DoubleProperty doubleProperty, boolean isOrthogonal) {
		Rectangle second = new Rectangle(80, 40);
		second.heightProperty().bind(windMillHeight);
		second.widthProperty().bind(windMillWidth);
		second.layoutYProperty().bind(windMillHeight.multiply(-1.5));
		second.rotateProperty().bind(doubleProperty.multiply(SPINSPEED).add(isOrthogonal ? 90 : 0));
		second.setFill(Color.SALMON);
		return second;
	}

}
