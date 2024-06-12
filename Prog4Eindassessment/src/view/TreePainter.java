package view;

import controller.Controller;
import enums.MovableObjectSize;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.MovableObject;
import model.Tree;

public abstract class TreePainter extends MovableObjectPainter {
	private static final double TRUNK_WIDTH = 20.0;
	protected static final double TRUNK_HEIGHT = 100.0;

	protected Pane makeDefaultTreeBase(MovableObject tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty, Controller controller) {
		Pane treePane = makeDefaultMovablePane(tree, paintingXproperty, paintingYproperty, controller);
		treePane.getChildren().add(makeTrunk(tree));
		return treePane;
	}

	private Rectangle makeTrunk(MovableObject tree) {
		Rectangle trunk = new Rectangle();
		trunk.setFill(Color.BROWN);
		setBlackStroke(trunk);

		DoubleBinding bindingHeight = getSizeBindingForConstant(tree, TRUNK_HEIGHT);
		DoubleBinding bindingWidth = getSizeBindingForConstant(tree, TRUNK_WIDTH);

		trunk.heightProperty().bind(bindingHeight);
		trunk.widthProperty().bind(bindingWidth);
		trunk.layoutYProperty().bind(bindingHeight.negate());
		trunk.layoutXProperty().bind(bindingWidth.multiply(-0.5));
		return trunk;
	}

	@Override
	// Override to implement treeSize scale for all but only trees
	protected DoubleBinding getSizeBindingForConstant(MovableObject movableObject, double constant) {
		Tree t = (Tree) movableObject;
		DoubleBinding doubleBinding = movableObject.getRelYproperty().subtract(35).divide(65);// TODO OOK MAGIC NUMBERS,
																								// zelfde als
																								// MOpainter
		return doubleBinding.multiply(constant * t.getObjectSize().getSizeScaleValue());
	}

	protected Color adjustHSBcolor(Color c, MovableObjectSize size) {
		double newSaturation = c.getSaturation() + size.getColorSaturation();
		double newBrightness = c.getBrightness() + size.getColorBrightness();
		return Color.hsb(c.getHue(), newSaturation, newBrightness);
	}
}
