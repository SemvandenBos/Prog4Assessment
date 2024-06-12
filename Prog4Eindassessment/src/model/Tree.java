package model;

import enums.MovableObjectSize;
import enums.MovableObjectType;
import enums.TreeType;

public class Tree extends MovableObject {
	protected MovableObjectSize treeSize;
	private TreeType treeType;

	// Constructor for reading files
	public Tree(MovableObjectType objType, TreeType treeType, MovableObjectSize size, double relX, double relY) {
		super(objType, relX, relY);
		this.treeType = treeType;
		this.treeSize = size;
		movableObjectType = MovableObjectType.TREE;
	}

	public Tree(TreeType type) {
		super();
		this.treeType = type;
		this.treeSize = MovableObjectSize.randomSize();
		movableObjectType = MovableObjectType.TREE;
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
