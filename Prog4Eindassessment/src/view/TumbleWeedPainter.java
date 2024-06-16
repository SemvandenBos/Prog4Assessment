package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.MovableObject;

public class TumbleWeedPainter extends MovableObjectPainter {
	private static final double TUMBLE_RADIUS = 30.0;
	private static final double BOUNCE_HEIGHT = 20.0;
	private static final double BOUNCE_FREQUENCY = 0.2;

	protected TumbleWeedPainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	@Override
	public Pane paintMovableObject(MovableObject movableObject) {
		Pane p = makeDefaultMovablePane(movableObject);

		Circle tumbleWeed = new Circle(TUMBLE_RADIUS);
		movableObject.getRelXproperty().addListener((ov, o, n) -> tumbleWeed
				.setLayoutY(Math.abs(Math.sin(n.doubleValue() * BOUNCE_FREQUENCY)) * -BOUNCE_HEIGHT + -TUMBLE_RADIUS));
		tumbleWeed.setFill(Color.DARKGOLDENROD);

		Group group = makeGroup(movableObject);
		group.getChildren().addAll(tumbleWeed);
		p.getChildren().add(group);
		return p;
	}
}
