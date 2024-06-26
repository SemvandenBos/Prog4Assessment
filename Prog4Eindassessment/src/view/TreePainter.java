package view;

import controller.Controller;
import enums.TreeSize;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.MovableObject;
import model.Tree;

public abstract class TreePainter extends MovableObjectPainter {
	private static final double TRUNK_WIDTH = 20.0;
	protected static final double TRUNK_HEIGHT = 100.0;

	protected TreePainter(ReadOnlyDoubleProperty paintingXproperty, ReadOnlyDoubleProperty paintingYproperty,
			Controller controller) {
		super(paintingXproperty, paintingYproperty, controller);
	}

	private Rectangle makeTrunk(MovableObject tree) {
		double trunkWidth = getRealTreeSize(tree, TRUNK_WIDTH);
		double trunkHeight = getRealTreeSize(tree, TRUNK_HEIGHT);
		Rectangle trunk = new Rectangle(-0.5 * trunkWidth, -trunkHeight, trunkWidth, trunkHeight);
		trunk.setFill(Color.BROWN);
		setBlackStroke(trunk);
		return trunk;
	}

	@Override
	protected Group makeGroup(MovableObject tree) {
		Group group = super.makeGroup(tree);
		group.getChildren().add(makeTrunk(tree));
		return group;
	}

	// Returns the proper scaling for constant based on TreeSize
	protected double getRealTreeSize(MovableObject tree, double constant) {
		return constant * ((Tree) tree).getObjectSize().getSizeScaleValue();
	}

	// Shifts the tree's base color and styles shape
	protected void adjustHSBcolor(Shape shape, Color c, TreeSize size) {
		setBlackStroke(shape);
		double newSaturation = c.getSaturation() + size.getColorSaturation();
		double newBrightness = c.getBrightness() + size.getColorBrightness();
		shape.setFill(Color.hsb(c.getHue(), newSaturation, newBrightness));
	}
}
