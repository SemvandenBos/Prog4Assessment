package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import model.MovableObject;

public class RockPainter extends MovableObjectPainter {
	private static final double ROCK_HEIGHT = 40.0;
	private static final double ROCK_WIDTH = 60.0;

	protected RockPainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	@Override
	public Pane paintMovableObject(MovableObject movableObject) {
		Pane p = makeDefaultMovablePane(movableObject);

		Ellipse rock = new Ellipse(ROCK_WIDTH, ROCK_HEIGHT);
		setBlackStroke(rock);

		Stop[] stops = new Stop[] { new Stop(0, Color.GRAY), new Stop(1, Color.DARKGRAY) };
		LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		rock.setFill(gradient);
		rock.setCenterY(-ROCK_HEIGHT);

		Group group = makeGroup(movableObject);
		group.getChildren().add(rock);
		p.getChildren().add(group);
		return p;
	}

}
