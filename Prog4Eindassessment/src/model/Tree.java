package model;

import java.util.Random;

import enums.MovableObjectSize;
import enums.MovableObjectType;
import enums.TreeType;

public class Tree extends MovableObject {
	private static final double MOVEMENT_SPEED = 1;

	private TreeType treeType;

	// Constructor for reading files
	public Tree(TreeType type, MovableObjectSize size, double relX, double relY) {
		super(size, relX, relY);
		treeType = type;
		movableObjectType = MovableObjectType.TREE;
	}

	public Tree(TreeType type) {
		super();
		this.treeType = type;
		Random r = new Random();
		relXproperty.set(r.nextDouble() * 100);
		relYproperty.set(r.nextDouble() * 50 + 50);
	}

	@Override
	public void move() {
		relXproperty.set((relXproperty.get() + MOVEMENT_SPEED * (Math.pow(relYproperty.get() / 100.0, 3))) % 100);
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
