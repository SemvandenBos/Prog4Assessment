package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import model.MovableObject;

public class WheatPatchPainter extends MovableObjectPainter {
	private static final double WHEAT_LENGTH = 60.0;
	private static final double WHEAT_WIDTH = 6.0;
	private static final double WHEAT_AMPLITUTE = 10.0;
	private static final double WHEAT_FREQUENCY = 0.2;
	private static final double WHEAT_ANGLE = 15.0;

	protected WheatPatchPainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	@Override
	public Pane paintMovableObject(MovableObject movableObject) {
		Pane p = makeDefaultMovablePane(movableObject);
		Group group = makeGroup(movableObject);
		p.getChildren().add(group);
		for (int i = -2; i <= 2; i++) {
			group.getChildren().add(makeWheat(i * WHEAT_ANGLE, movableObject));
		}
		return p;
	}

	private Rectangle makeWheat(double startRotation, MovableObject movableObject) {
		Rectangle wheatStick = new Rectangle(0, -WHEAT_LENGTH, WHEAT_WIDTH, WHEAT_LENGTH);
		wheatStick.setFill(Color.WHEAT);

		Rotate rotation = new Rotate(startRotation, WHEAT_WIDTH * 0.5, 0);
		wheatStick.getTransforms().add(rotation);
		movableObject.getRelXproperty().addListener((ov, o, n) -> rotation
				.setAngle(Math.sin(n.doubleValue() * WHEAT_FREQUENCY) * WHEAT_AMPLITUTE + startRotation));

		return wheatStick;
	}

}
