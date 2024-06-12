package model;

import java.util.Random;

import enums.MovableObjectType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class MovableObject {
	private static final double MOVEMENT_SPEED = 1;

	protected DoubleProperty relXproperty;
	protected DoubleProperty relYproperty;

	protected MovableObjectType movableObjectType;

	protected MovableObject() {
		relXproperty = new SimpleDoubleProperty();
		relYproperty = new SimpleDoubleProperty();
		Random r = new Random();
		relXproperty.set(r.nextDouble() * 100);
		relYproperty.set(r.nextDouble() * 50 + 50);
	}

	protected MovableObject(MovableObjectType type) {
		this();
		this.movableObjectType = type;
	}

	protected MovableObject(MovableObjectType type, double relX, double relY) {
		this(type);
		relXproperty.set(relX);
		relYproperty.set(relY);
	}

	public boolean move() {
		relXproperty.set((relXproperty.get() + MOVEMENT_SPEED * (Math.pow(relYproperty.get() / 100.0, 3))));
		return (relXproperty.get() > 110);
	}

	public double getRelX() {
		return relXproperty.get() / 100;
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
