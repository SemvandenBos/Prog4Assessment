package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import model.MovableObject;

public class MountainPainter extends MovableObjectPainter {
	private static final double MOUNTAIN_SIZE = 150.0;
	private static final double ARC_START_ANGLE = 235.0;
	private static final double ARC_LENGTH = 70.0;
	private static final double SNOWRATIO = 0.5;

	protected MountainPainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	@Override
	public Pane paintMovableObject(MovableObject movableObject) {
		Pane p = makeDefaultMovablePane(movableObject);

		Arc mountain = makeArc(1, Color.DARKGRAY);
		Arc mountainPeak = makeArc(SNOWRATIO, Color.WHITE);

		Group group = makeGroup(movableObject);
		group.getChildren().addAll(mountain, mountainPeak);
		p.getChildren().add(group);
		return p;
	}

	private Arc makeArc(double size, Color color) {
		Arc arc = new Arc(0, -MOUNTAIN_SIZE, MOUNTAIN_SIZE * size, MOUNTAIN_SIZE * size, ARC_START_ANGLE, ARC_LENGTH);
		arc.setType(ArcType.ROUND);
		arc.setFill(color);
		setBlackStroke(arc);
		return arc;
	}

}
