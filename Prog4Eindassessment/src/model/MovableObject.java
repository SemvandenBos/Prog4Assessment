package model;

import enums.MovableObjectSize;
import enums.MovableObjectType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class MovableObject {
	protected DoubleProperty relXproperty;
	protected DoubleProperty relYproperty;
	protected MovableObjectSize treeSize;
	protected MovableObjectType movableObjectType;

	protected MovableObject() {
		relXproperty = new SimpleDoubleProperty();
		relYproperty = new SimpleDoubleProperty();
		treeSize = MovableObjectSize.randomSize();
	}

	protected MovableObject(MovableObjectSize size, double relX, double relY) {
		this();
		relXproperty.set(relX);
		relYproperty.set(relY);
		treeSize = size;
	}

	public abstract void move();

	public double getRelX() {
		return relXproperty.get() / 100;
	}

	public double getRelY() {
		return relYproperty.get() / 100;
	}

	public MovableObjectSize getObjectSize() {
		return treeSize;
	}

	public MovableObjectType getMovableObjectType() {
		return movableObjectType;
	}

	public DoubleProperty getRelXproperty() {
		return relXproperty;
	}

	public DoubleProperty getRelYproperty() {
		return relYproperty;
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
