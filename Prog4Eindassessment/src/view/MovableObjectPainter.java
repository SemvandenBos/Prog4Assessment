package view;

import controller.Controller;
import enums.MovableObjectSize;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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
		movablePane.layoutYProperty()
				.bind(movableObject.getRelYproperty().subtract(50).multiply(paintingYproperty).divide(50.0));

		return movablePane;
	}

	// Allows for a pane to be dragged and change location
	protected void makePaneDraggable(Pane pane, MovableObject movableObject, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		pane.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double xRel = event.getSceneX() / paintingXproperty.get() * 100.0;
				double yRel = (event.getSceneY() - 25.0) / paintingYproperty.get() * 50.0;
				controller.setRelPoint(xRel, yRel, movableObject);
//				movableObject.setRelPoint(xRel, yRel);
			}
		});
	}

	protected void setColorForShapePerSize(Shape s, MovableObjectSize objectSize, int hue, double saturation,
			double brightness) {
		double newSaturation = saturation + objectSize.getColorSaturation();
		double newBrightness = brightness + objectSize.getColorBrightness();
		Color c = Color.hsb(hue, newSaturation, newBrightness);
		s.setFill(c);
	}

	protected void setBlackStroke(Shape s) {
		s.setStroke(Color.BLACK);
		s.setStrokeWidth(STROKE_WIDTH);
	}

	// Rather than using constant as size, we make a binding with the models
	// relYproperty and the size scale value
	protected DoubleBinding getSizeBindingForConstant(MovableObject movableObject, double constant) {
		DoubleBinding doubleBinding = movableObject.getRelYproperty()
				.multiply(movableObject.getRelYproperty().divide(10000));
		return doubleBinding.multiply(constant * movableObject.getObjectSize().getSizeScaleValue());
	}

}
