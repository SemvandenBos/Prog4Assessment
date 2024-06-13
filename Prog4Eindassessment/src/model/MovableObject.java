package model;

import java.util.Random;

import enums.MovableObjectType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MovableObject {
	private static final double MOVEMENT_SPEED = 1;
	private static final int DELETION_THRESHOLD = 110;
	protected static final int MIN_Y_VALUE = 50;

	protected DoubleProperty relXproperty;
	protected DoubleProperty relYproperty;
	protected MovableObjectType movableObjectType;
	protected Random random;

	protected MovableObject(MovableObjectType type) {
		relXproperty = new SimpleDoubleProperty();
		relYproperty = new SimpleDoubleProperty();
		random = new Random();
		relXproperty.set(0);
		relYproperty.set(random.nextDouble() * (100 - MIN_Y_VALUE) + MIN_Y_VALUE);
		this.movableObjectType = type;
	}

//	-------------------------

	public boolean move() {
		relXproperty.set((relXproperty.get() + MOVEMENT_SPEED * relYproperty.get() / 100.0));
		return (relXproperty.get() > DELETION_THRESHOLD);
	}

	public void placeLeft() {
		relXproperty.set(0);
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

//	----------GETTERS-----------------
	public double getRelY() {
		return relYproperty.get();
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
}
