package model;

import enums.MovableObjectSize;
import enums.MovableObjectType;
import enums.TreeType;

public class Tree extends MovableObject {
	protected MovableObjectSize treeSize;
	private TreeType treeType;

	// Constructor for reading files
	public Tree(TreeType treeType, MovableObjectSize size, double relX, double relY) {
		super(MovableObjectType.TREE);
		this.treeType = treeType;
		this.treeSize = size;
		relXproperty.set(relX);
		relYproperty.set(relY);
	}

	// Constructor for adding randomised tree
	public Tree(TreeType type) {
		super(MovableObjectType.TREE);
		this.treeType = type;
		this.treeSize = MovableObjectSize.randomSize();
	}

	public MovableObjectSize getObjectSize() {
		return treeSize;
	}

//	-----------SETTERS AND GETTERS-------------------

	public TreeType getTreeType() {
		return treeType;
	}

	@Override
	public String toString() {
		return treeType + ":" + treeSize + ":" + relXproperty.intValue() + ":" + relYproperty.intValue();
	}
}
