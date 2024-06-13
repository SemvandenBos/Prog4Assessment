package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import model.MovableObject;

public class PackmanPainter extends MovableObjectPainter {

	private static final double PACKMAN_RADIUS = 40.0;
	private static final double BITESIZE = 30.0;
	private static final double FOOD_RADIUS = 15.0;
	private static final double BITESPEED = 0.5;
	private static final double FOOD_FREQUENCY = 3.0;

	protected PackmanPainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	@Override
	public Pane paintMovableObject(MovableObject movableObject) {
		Pane p = makeDefaultMovablePane(movableObject);

		Circle food = new Circle(2 * PACKMAN_RADIUS, -PACKMAN_RADIUS, FOOD_RADIUS);

		Arc packman = new Arc(0, -PACKMAN_RADIUS, PACKMAN_RADIUS, PACKMAN_RADIUS, BITESIZE, 360 - 2 * BITESIZE);
		packman.setType(ArcType.ROUND);
		packman.setFill(Color.YELLOW);
		setBlackStroke(packman);

		movableObject.getRelXproperty().addListener((ov, o, n) -> {
			setAngles(n.doubleValue(), packman);
			food.setLayoutY(Math.sin(n.doubleValue()) * FOOD_FREQUENCY);
		});

		Group group = makeGroup(movableObject);
		group.getChildren().addAll(packman, food);
		p.getChildren().add(group);
		return p;
	}

	private void setAngles(double newDouble, Arc packman) {
		double newAngle = (Math.sin(newDouble * BITESPEED) + 1) * BITESIZE;
		packman.setStartAngle(newAngle);
		packman.setLength(360 - newAngle * 2);
	}

}
