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
	private TumbleWeedPainter tumbleWeedPainter;
	private WheatPatchPainter wheatPatchPainter;
	private PackmanPainter packmanPainter;
	private MountainPainter mountainPainter;
	private RockPainter rockPainter;

	public PainterManager(Controller controller, ReadOnlyDoubleProperty paintingXproperty,
			ReadOnlyDoubleProperty paintingYproperty) {
		leafTreePainter = new LeafTreePainter(paintingXproperty, paintingYproperty, controller);
		pineTreePainter = new PineTreePainter(paintingXproperty, paintingYproperty, controller);
		windmillPainter = new WindmillPainter(paintingXproperty, paintingYproperty, controller);
		housePainter = new HousePainter(paintingXproperty, paintingYproperty, controller);
		tumbleWeedPainter = new TumbleWeedPainter(paintingXproperty, paintingYproperty, controller);
		wheatPatchPainter = new WheatPatchPainter(paintingXproperty, paintingYproperty, controller);
		packmanPainter = new PackmanPainter(paintingXproperty, paintingYproperty, controller);
		mountainPainter = new MountainPainter(paintingXproperty, paintingYproperty, controller);
		rockPainter = new RockPainter(paintingXproperty, paintingYproperty, controller);
	}

	public Pane getPane(MovableObject tree) {
		Pane p;
		switch (tree.getMovableObjectType()) {
		case TREE:
			p = addSingleTree((Tree) tree);
			break;
		case WHEATPATCH:
			p = wheatPatchPainter.paintMovableObject(tree);
			break;
		case HOUSE:
			p = housePainter.paintMovableObject(tree);
			break;
		case WINDMILL:
			p = windmillPainter.paintMovableObject(tree);
			break;
		case TUMBLEWEED:
			p = tumbleWeedPainter.paintMovableObject(tree);
			break;
		case PACKMAN:
			p = packmanPainter.paintMovableObject(tree);
			break;
		case MOUNTAIN:
			p = mountainPainter.paintMovableObject(tree);
			break;
		case ROCK:
			p = rockPainter.paintMovableObject(tree);
			break;
		default:
			p = null;
			break;
		}
		return p;
	}

	private Pane addSingleTree(Tree tree) {
		Pane p = null;
		switch (tree.getTreeType()) {
		case LEAF:
			p = leafTreePainter.paintMovableObject(tree);
			break;
		case PINE:
			p = pineTreePainter.paintMovableObject(tree);
			break;
		}
		return p;
	}
}
