package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
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

	public PineTreePainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	@Override
	public Pane paintMovableObject(MovableObject tree) {
		Pane p = makeDefaultMovablePane(tree);
		double leavesHeight = getRealTreeSize(tree, TRUNK_HEIGHT * HEIGHT_FACTOR);
		double leavesSize = getRealTreeSize(tree, PINELEAF_SIZE);

		Arc leavesArc = new Arc(0, leavesHeight, leavesSize, leavesSize, ARC_START_ANGLE, ARC_LENGTH);
		leavesArc.setType(ArcType.ROUND);
		adjustHSBcolor(leavesArc, BASE_COLOR, ((Tree) tree).getObjectSize());

		Group group = makeGroup(tree);
		group.getChildren().add(leavesArc);
		p.getChildren().add(group);
		return p;
	}
}
