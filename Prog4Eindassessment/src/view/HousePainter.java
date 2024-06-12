package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import model.MovableObject;

public class HousePainter extends MovableObjectPainter {

	public HousePainter() {

	}

	@Override
	public Pane paintMovableObject(MovableObject movableObject, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		Pane p = makeDefaultMovablePane(movableObject, paintingXproperty, paintingYproperty, controller);

		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(new Double[] { 0.0, 0.0, 20.0, 10.0, 10.0, 20.0 });
		//TODO
		p.getChildren().add(polygon);

		return p;
	}
}
