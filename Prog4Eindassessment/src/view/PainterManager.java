package view;

import controller.Controller;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import model.MovableObject;
import model.Tree;

public class PainterManager {

	private LeafTreePainter leafTreePainter;
	private PineTreePainter pineTreePainter;
	private WindmillPainter windmillPainter;
	private HousePainter housePainter;
	// TODO add all painters

	private Controller controller;
	private ReadOnlyDoubleProperty paintingXproperty;
	private ReadOnlyDoubleProperty paintingYproperty;

	public PainterManager(Controller controller, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {
		this.controller = controller;
		this.paintingXproperty = paintingXproperty;
		this.paintingYproperty = paintingYproperty;

		leafTreePainter = new LeafTreePainter();
		pineTreePainter = new PineTreePainter();
		windmillPainter = new WindmillPainter();
		housePainter = new HousePainter();
	}

	public Pane getPane(MovableObject tree) {
		Pane p = null;
		switch (tree.getMovableObjectType()) {
		case TREE:
			p = addSingleTree((Tree) tree);
			break;
		case BUSH:
			p = pineTreePainter.paintMovableObject(tree, paintingXproperty, paintingYproperty, controller);
			break;
		case HOUSE:
			p = housePainter.paintMovableObject(tree, paintingXproperty, paintingYproperty, controller);
			break;
		case WINDMILL:
			p = windmillPainter.paintMovableObject(tree, paintingXproperty, paintingYproperty, controller);
			break;
		default:
			break;
		}
		return p;
	}

	private Pane addSingleTree(Tree tree) {
		Pane p = null;
		switch (tree.getTreeType()) {
		case LEAF:
			p = leafTreePainter.paintMovableObject(tree, paintingXproperty, paintingYproperty, controller);
			break;
		case PINE:
			p = pineTreePainter.paintMovableObject(tree, paintingXproperty, paintingYproperty, controller);
			break;
		}
		return p;
	}
}
