package view;

import controller.Controller;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import model.MovableObject;

public abstract class MovableObjectPainter {
	private static final double STROKE_WIDTH = 2d;
	private static final double DEPTH_SCALING = 40d;// The higher, the 'further' away objects
	private ReadOnlyDoubleProperty paintingXproperty;
	private ReadOnlyDoubleProperty paintingYproperty;
	private Controller controller;

	protected MovableObjectPainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		this.paintingXproperty = paintingXproperty;
		this.paintingYproperty = paintingYproperty;
		this.controller = controller;
	}

	public abstract Pane paintMovableObject(MovableObject movableObject);

	protected Pane makeDefaultMovablePane(MovableObject movableObject) {
		Pane movablePane = new Pane();
		makePaneDraggable(movablePane, movableObject);

		movablePane.layoutXProperty().bind(movableObject.getRelXproperty().multiply(paintingXproperty).divide(100.0));
		movablePane.layoutYProperty().bind(movableObject.getRelYproperty().multiply(paintingYproperty).divide(100.0));

		return movablePane;
	}

	// Allows for a pane to be dragged and change location (also accounts for depth)
	protected void makePaneDraggable(Pane pane, MovableObject movableObject) {
		pane.setOnMouseDragged(event -> {
			double xRel = event.getSceneX() / paintingXproperty.get() * 100.0;
			double yRel = event.getSceneY() / paintingYproperty.get() * 100.0;
			controller.setRelPoint(xRel, yRel, movableObject);
		});
		pane.setOnMouseReleased(e -> controller.adjustDepth(movableObject));
	}

	protected Group makeGroup(MovableObject movableObject) {
		Group group = new Group();
		Scale scale = new Scale();
		group.getTransforms().add(scale);

		DoubleBinding doubleBinding = movableObject.getRelYproperty().subtract(DEPTH_SCALING)
				.divide(100 - DEPTH_SCALING);
		scale.xProperty().bind(doubleBinding);
		scale.yProperty().bind(doubleBinding);
		return group;
	}

	protected void setBlackStroke(Shape s) {
		s.setStroke(Color.BLACK);
		s.setStrokeWidth(STROKE_WIDTH);
	}

}
