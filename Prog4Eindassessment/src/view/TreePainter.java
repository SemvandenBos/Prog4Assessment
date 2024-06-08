package view;

import enums.TreeSize;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Tree;

public abstract class TreePainter {
	// TODO access modifiers (check call hierarchy
	protected static final double TRUNK_WIDTH = 20.0;
	protected static final double TRUNK_HEIGHT = 100.0;
	private static final double TREE_HUE = 0;

	protected double calculateScreenSize(Tree tree, double constant) {
		return constant * tree.getTreeSize().getSizeScaleValue() * (Math.pow(tree.getRelY(), 3));
	}

	protected void setColorForShapePerSize(Shape s, TreeSize treeSize, int hue, double saturation, double brightness) {
		double newSaturation = saturation + treeSize.getColorSaturation();
		double newBrightness = brightness + treeSize.getColorBrightness();
		Color c = Color.hsb(hue, newSaturation, newBrightness);
		s.setFill(c);
	}

	protected void setBlackStroke(Shape s) {
		s.setStroke(Color.BLACK);
		s.setStrokeWidth(1.5);
	}

	// Rather than using constant as size, we make a binding with the models
	// relYproperty and the size scale value
	protected DoubleBinding getSizeBindingForConstant(Tree tree, double constant) {
		DoubleBinding doubleBinding = tree.getRelYproperty().multiply(tree.getRelYproperty().divide(10000));
		return doubleBinding.multiply(constant * tree.getTreeSize().getSizeScaleValue());
	}

	public abstract Pane drawTree(Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty);

	protected Pane makeDefaultTreeBase(Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {
		Pane treePane = new Pane();
		// Binding location
		treePane.layoutXProperty().bind(tree.getRelXproperty().multiply(paintingXproperty).divide(100.0));
		treePane.layoutYProperty().bind(tree.getRelYproperty().subtract(50).multiply(paintingYproperty).divide(50.0));
		makePaneDraggable(treePane, tree, paintingXproperty, paintingYproperty);
//		treePane.setBorder(
//				new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null, new Insets(3))));

		Rectangle trunk = new Rectangle();
		setBlackStroke(trunk);
		// constant size //TODO niet waar, moet scalen met drag and drop
		DoubleBinding bindingHeight = getSizeBindingForConstant(tree, TRUNK_HEIGHT);
		DoubleBinding bindingWidth = getSizeBindingForConstant(tree, TRUNK_WIDTH);
		trunk.heightProperty().bind(bindingHeight);
		trunk.widthProperty().bind(bindingWidth);
		trunk.layoutYProperty().bind(bindingHeight.negate());
		trunk.layoutXProperty().bind(bindingWidth.multiply(-0.5));

		treePane.getChildren().add(trunk);
		// TODO HSB colors
//		Color color = Color.hsb(360, 75 / 255.0, tree.getTreeSize().getColorBrightness());
//		trunk.setFill(color);
		trunk.setFill(Color.BROWN);
		return treePane;
	}

	/**
	 * Allows for a pane to be dragged and change location
	 * 
	 * @param pane
	 * @param tree
	 * @param paintingXproperty
	 * @param paintingYproperty
	 */

	private void makePaneDraggable(Pane pane, Tree tree, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {

		pane.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double xRel = event.getSceneX() / paintingXproperty.get() * 100.0;
				double yRel = (event.getSceneY() - 25.0) / paintingYproperty.get() * 50.0;
				tree.setRelPoint(xRel, yRel);
			}
		});
	}
}
