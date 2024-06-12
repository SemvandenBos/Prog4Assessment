package view;

import controller.Controller;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import model.MovableObject;
import model.Tree;

public class PineTreePainter extends TreePainter {

	private static final double PINELEAF_SIZE = 50.0;
	private static final Color BASE_COLOR = Color.hsb(151, 0.7, 0.6);
	private static final double ARC_START_ANGLE = 235.0;
	private static final double ARC_LENGTH = 70.0;
	private static final double HEIGHT_FACTOR = -1.2;

	@Override
	public Pane paintMovableObject(MovableObject tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		Pane p = makeDefaultTreeBase(tree, paintingXproperty, paintingYproperty, controller);

		Arc arc = new Arc();
		setBlackStroke(arc);
		arc.setFill(adjustHSBcolor(BASE_COLOR, ((Tree) tree).getObjectSize()));

		arc.setStartAngle(ARC_START_ANGLE);
		arc.setLength(ARC_LENGTH);
		arc.setType(ArcType.ROUND);

		arc.layoutYProperty().bind(getSizeBindingForConstant(tree, TRUNK_HEIGHT).multiply(HEIGHT_FACTOR));
		DoubleBinding s = getSizeBindingForConstant(tree, PINELEAF_SIZE);
		arc.radiusXProperty().bind(s);
		arc.radiusYProperty().bind(s);

		p.getChildren().add(arc);
		return p;
	}
}
