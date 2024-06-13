package model;

import enums.TreeSize;
import enums.MovableObjectType;
import enums.TreeType;

public class Tree extends MovableObject {
	protected TreeSize treeSize;
	private TreeType treeType;

	// Constructor for reading files
	public Tree(TreeType treeType, TreeSize size, double relX, double relY) {
		super(MovableObjectType.TREE);
		this.treeType = treeType;
		this.treeSize = size;
		relXproperty.set(relX);
		relYproperty.set(relY);
	}

	// Constructor for adding randomised tree TODO
	public Tree(TreeType type, boolean isRandom) {
		super(MovableObjectType.TREE);
		if (isRandom) {
			relYproperty.set(random.nextDouble() * (100 - MIN_Y_VALUE) + MIN_Y_VALUE);
			relXproperty.set(random.nextDouble() * 100);
		}
		this.treeType = type;
		this.treeSize = TreeSize.randomSize();
	}

//	-----------GETTERS-------------------

	public TreeType getTreeType() {
		return treeType;
	}

	public TreeSize getObjectSize() {
		return treeSize;
	}

	@Override
	public String toString() {
		return treeType + ":" + treeSize + ":" + relXproperty.intValue() + ":" + relYproperty.intValue();
	}
}
