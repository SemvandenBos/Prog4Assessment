package view;

import java.util.Random;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import model.MovableObject;

public class HousePainter extends MovableObjectPainter {
	private static final double HOUSESIZE = 100d;
	private static final Color[] POSSIBLE_BASE_COLORS = { Color.ORANGE, Color.ORCHID, Color.POWDERBLUE };
	private static final Double[] ROOF_POINTS = { 0.5 * HOUSESIZE, -HOUSESIZE, 0.0, 0.0, HOUSESIZE, 0.0 };

	public HousePainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	@Override
	public Pane paintMovableObject(MovableObject movableObject) {
		Pane p = makeDefaultMovablePane(movableObject);

		Rectangle houseBase = new Rectangle(0, -HOUSESIZE, HOUSESIZE, HOUSESIZE);
		Random r = new Random();
		Color randColor = POSSIBLE_BASE_COLORS[r.nextInt(POSSIBLE_BASE_COLORS.length)];
		houseBase.setFill(randColor);

		Polygon houseRoof = new Polygon();
		houseRoof.getPoints().addAll(ROOF_POINTS);
		houseRoof.setLayoutY(-HOUSESIZE);
		houseRoof.setFill(Color.RED);

		Group group = makeGroup(movableObject);
		group.getChildren().addAll(houseBase, houseRoof);

		p.getChildren().add(group);
		return p;
	}
}
