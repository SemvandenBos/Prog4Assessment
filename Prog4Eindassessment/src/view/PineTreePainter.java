package view;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import model.Tree;

public class PineTreePainter extends TreePainter {

	private static final double PINELEAF_SIZE = 50.0;

	private static final int PINE_HUE = 151;
	private static final double PINE_SATURATION = 0.70;
	private static final double PINE_BRIGHTNESS = 0.60;

	@Override
	public Pane drawTree(Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {
		Pane p = makeDefaultTreeBase(tree, paintingXproperty, paintingYproperty);

		Arc arc = new Arc();
		setBlackStroke(arc);
		setColorForShapePerSize(arc, tree.getTreeSize(), PINE_HUE, PINE_SATURATION, PINE_BRIGHTNESS);

		arc.layoutYProperty().bind(getSizeBindingForConstant(tree, TRUNK_HEIGHT).multiply(-1.2));
		DoubleBinding s = getSizeBindingForConstant(tree, PINELEAF_SIZE);
		arc.radiusXProperty().bind(s);
		arc.radiusYProperty().bind(s);
		arc.setStartAngle(235.0);
		arc.setLength(70.0);
		arc.setType(ArcType.ROUND);

		p.getChildren().add(arc);
		return p;
	}
}
