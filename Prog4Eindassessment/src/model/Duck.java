package model;

import enums.MovableObjectType;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

public class Duck extends MovableObject {
	private static final double INITIAL_X = 50.0;
	private BooleanProperty isFlying;
	private double speed;

	public Duck() {
		super(MovableObjectType.DUCK);
		isFlying = new SimpleBooleanProperty(false);
		reset();
	}

	public BooleanProperty isFlyingProperty() {
		return isFlying;
	}

	private void reset() {
		relXproperty.set(INITIAL_X);
		relYproperty.set(25.0);
		speed = 0.0;
	}

	public void toggleFlying() {
		this.isFlying.set(!isFlying.get());
		if (isFlying.get()) {
			reset();
			Thread duckFlyThread = new Thread(new DuckFlyTask());
			duckFlyThread.setDaemon(true);
			duckFlyThread.start();
		}
	}

	private class DuckFlyTask extends Task<Void> {

		private static final int DUCKFLY_FPS = 11;

		@Override
		protected Void call() throws Exception {
			while (isFlying.get()) {
				Platform.runLater(Duck.this::move);
				try {
					Thread.sleep(1000 / DUCKFLY_FPS);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			return null;
		}

	}

	public void setFlySpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public boolean move() {
		relXproperty.set(relXproperty.get() + speed);
		return false;// TODO
	}

	public void keyPressed(KeyCode keyCode) {
		switch (keyCode) {
		case D:
			toggleFlying();
			break;
		case LEFT:
			setFlySpeed(-1.0);
			break;
		case RIGHT:
			setFlySpeed(1.0);
			break;
		case DOWN:
			setFlySpeed(0);
			break;
		default:
			break;
		}
	}
}
