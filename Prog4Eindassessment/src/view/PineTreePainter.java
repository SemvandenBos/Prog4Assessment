package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import model.Tree;

public class PineTreePainter extends TreePainter {

	private static final double PINELEAF_SIZE = 50.0;

	@Override
	public Pane drawTree(Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {
		Pane p = makeDefaultTreeBase(tree, paintingXproperty, paintingYproperty);

		Arc arc = new Arc();
		arc.setRadiusX(calculateScreenSize(tree, PINELEAF_SIZE));
		arc.setRadiusY(calculateScreenSize(tree, PINELEAF_SIZE));
		arc.setStartAngle(235.0f);
		arc.setLength(70.0f);
		arc.setType(ArcType.ROUND);
		arc.setFill(Color.DARKGREEN);

		p.getChildren().add(arc);
		return p;
	}
}
