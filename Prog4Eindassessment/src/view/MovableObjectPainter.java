package view;

import controller.Controller;
import enums.MovableObjectSize;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.MovableObject;

public abstract class MovableObjectPainter {
	private static final double STROKE_WIDTH = 1.5;

	// General abstract method for painting any object parsed
	public abstract Pane paintMovableObject(MovableObject movableObject, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller);

	// Returns a pane which is bound to the movable object and screen, that is
	// draggable
	protected Pane makeDefaultMovablePane(MovableObject movableObject, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		Pane movablePane = new Pane();
		makePaneDraggable(movablePane, movableObject, paintingXproperty, paintingYproperty, controller);

		movablePane.layoutXProperty().bind(movableObject.getRelXproperty().multiply(paintingXproperty).divide(100.0));
		movablePane.layoutYProperty().bind(movableObject.getRelYproperty().multiply(paintingYproperty).divide(100.0));

		return movablePane;
	}

	// Allows for a pane to be dragged and change location (also accounts for depth)
	protected void makePaneDraggable(Pane pane, MovableObject movableObject, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		pane.setOnMouseDragged(event -> {
			double xRel = event.getSceneX() / paintingXproperty.get() * 100.0;
			double yRel = event.getSceneY() / paintingYproperty.get() * 100.0;
			controller.setRelPoint(xRel, yRel, movableObject);
		});
		pane.setOnMouseReleased(e -> controller.adjustDepth(movableObject));
	}

	// Uses HSB to set colors
	protected void setColorForShapePerSize(Shape s, MovableObjectSize objectSize, int hue, double saturation,
			double brightness) {
		double newSaturation = saturation + objectSize.getColorSaturation();
		double newBrightness = brightness + objectSize.getColorBrightness();
		Color c = Color.hsb(hue, newSaturation, newBrightness);
		s.setFill(c);
	}

	// Sets black outline
	protected void setBlackStroke(Shape s) {
		s.setStroke(Color.BLACK);
		s.setStrokeWidth(STROKE_WIDTH);
	}

	// Rather than using constant as size, we make a binding with the models
	// relYproperty and the size scale value
	protected DoubleBinding getSizeBindingForConstant(MovableObject movableObject, double constant) {
		DoubleBinding doubleBinding = movableObject.getRelYproperty().subtract(40).divide(60);
		return doubleBinding.multiply(constant * 1);
//		return doubleBinding.multiply(constant * movableObject.getObjectSize().getSizeScaleValue());
	}

}
