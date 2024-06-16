package view;

import controller.Controller;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.MovableObject;

public class WindmillPainter extends MovableObjectPainter {
	private static final double WINDMILL_HEIGHT = 100.0;
	private static final double WINDMILL_WIDTH = 20.0;
	private static final double SPINSPEED = 6.0;

	public WindmillPainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	@Override
	public Pane paintMovableObject(MovableObject movableObject) {
		Pane p = makeDefaultMovablePane(movableObject);

		Rectangle windmillBase = new Rectangle(0, -WINDMILL_HEIGHT, WINDMILL_WIDTH, WINDMILL_HEIGHT);
		windmillBase.setFill(Color.GRAY);

		Rectangle firstBlade = createMillblades(movableObject.getRelXproperty(), true);
		Rectangle secondBlade = createMillblades(movableObject.getRelXproperty(), false);

		Group group = makeGroup(movableObject);
		group.getChildren().addAll(windmillBase, firstBlade, secondBlade);
		p.getChildren().addAll(group);
		return p;
	}

	// Make rotatable windmillblade (bound to relX for spinning)
	private Rectangle createMillblades(DoubleProperty doubleProperty, boolean isOrthogonal) {
		Rectangle blade = new Rectangle(0, -1.5 * WINDMILL_HEIGHT, WINDMILL_WIDTH, WINDMILL_HEIGHT);
		blade.rotateProperty().bind(doubleProperty.multiply(SPINSPEED).add(isOrthogonal ? 90 : 0));
		blade.setFill(Color.GRAY);
		return blade;
	}

}
