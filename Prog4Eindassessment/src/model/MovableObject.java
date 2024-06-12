package model;

import java.util.Random;

import enums.MovableObjectType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MovableObject {
	private static final double MOVEMENT_SPEED = 1;
	private static final int DELETION_THRESHOLD = 110;
	private static final int MIN_Y_VALUE = 50;

	protected DoubleProperty relXproperty;
	protected DoubleProperty relYproperty;

	protected MovableObjectType movableObjectType;

	protected MovableObject(MovableObjectType type) {
		relXproperty = new SimpleDoubleProperty();
		relYproperty = new SimpleDoubleProperty();
		Random r = new Random();
		relXproperty.set(r.nextDouble() * 100);
		relYproperty.set(r.nextDouble() * (100 - MIN_Y_VALUE) + MIN_Y_VALUE);
		this.movableObjectType = type;
	}

//	protected MovableObject(MovableObjectType type, double relX, double relY) {
//		this(type);
//		relXproperty.set(relX);
//		relYproperty.set(relY);
//	}

	public MovableObject(MovableObjectType type, boolean isLeft) {
		this(type);
		if (isLeft) {
			relXproperty.set(0);
		}
	}

//	-------------------------

	public boolean move() {
//		relXproperty.set((relXproperty.get() + MOVEMENT_SPEED * (Math.pow(relYproperty.get() / 100.0, 3))));
		relXproperty.set((relXproperty.get() + MOVEMENT_SPEED * relYproperty.get() / 100.0));
		return (relXproperty.get() > DELETION_THRESHOLD);
	}

	public double getRelX() {
		return relXproperty.get() / 100;
	}

	public void placeLeft() {
		relXproperty.set(0);
	}

	public double getRelY() {
		return relYproperty.get() / 100;
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

	// Used for drag and drop
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
