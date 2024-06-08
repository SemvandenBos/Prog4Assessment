package model;

import java.util.Random;

import enums.TreeSize;
import enums.TreeType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Tree {
	private DoubleProperty relXproperty;
	private DoubleProperty relYproperty;
	private TreeType treeType;
	private TreeSize treeSize;

	// Constructor for reading files
	public Tree(TreeType type, TreeSize size, double relX, double relY) {
		treeType = type;
		treeSize = size;
		relXproperty = new SimpleDoubleProperty(relX);
		relYproperty = new SimpleDoubleProperty(relY);
	}

	// TODO property or regular double? (remove all legacy code)
	public Tree(TreeType type) {
		this.treeType = type;
		this.treeSize = TreeSize.randomSize();

		Random r = new Random();
		relXproperty = new SimpleDoubleProperty(r.nextDouble() * 100);
		relYproperty = new SimpleDoubleProperty(r.nextDouble() * 50 + 50);
	}

	public void move() {
		relXproperty.set((relXproperty.get() + 1 * (Math.pow(relYproperty.get() / 100.0, 3))) % 100);
	}

	public double getRelX() {
		return relXproperty.get() / 100;
	}

	public double getRelY() {
		return relYproperty.get() / 100;
	}

	public TreeType getTreeType() {
		return treeType;
	}

	public TreeSize getTreeSize() {
		return treeSize;
	}

	public DoubleProperty getRelXproperty() {
		return relXproperty;
	}

	public DoubleProperty getRelYproperty() {
		return relYproperty;
	}

	@Override
	public String toString() {
		return treeType + ":" + treeSize + ":" + relXproperty.intValue() + ":" + relYproperty.intValue();
	}

	public void setRelPoint(double xRel, double yRel) {
		relXproperty.set(xRel);
		if (yRel < 50.0) {
			yRel = 50.0;
		} else if (yRel > 100.0) {
			yRel = 100.0;
		}
		relYproperty.set(yRel);
	}
}
