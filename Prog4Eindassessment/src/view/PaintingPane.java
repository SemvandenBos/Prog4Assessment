package view;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Tree;
import model.World;

public class PaintingPane extends Pane {
	private LeafTreePainter leafTreePainter;
	private PineTreePainter pineTreePainter;

	public PaintingPane(World world) {
		world.treesProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.isEmpty()) {
				this.getChildren().clear();
			}
		});
		world.treesProperty().addListener((ListChangeListener<Tree>) change -> {
			while (change.next()) {
				if (change.wasAdded()) {
					for (Tree tree : change.getAddedSubList()) {
						addSingleTreePane(tree);
					}
				}
				// TODO add implementation for removal?
//				if (change.wasRemoved()) {
//                    for (Tree tree : change.getRemoved()) {
//                        removePersonListeners(tree);
//                    }
//				}
			}
		});

		leafTreePainter = new LeafTreePainter();
		pineTreePainter = new PineTreePainter();

		this.setBackground(new Background(new BackgroundFill(Color.SANDYBROWN, null, null)));
	}

	private void addSingleTreePane(Tree tree) {
		switch (tree.getTreeType()) {
		case LEAF:
			getChildren().add(leafTreePainter.drawTree(tree, widthProperty(), heightProperty()));
			break;
		case PINE:
			getChildren().add(pineTreePainter.drawTree(tree, widthProperty(), heightProperty()));
			break;
		default:
			break;
		}
	}
}
