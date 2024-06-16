package model;

import enums.MovableObjectType;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

public class Duck extends MovableObject {
	private static final double INITIAL_X = 50.0;
	private static final double INITIAL_Y = 25.0;
	private BooleanProperty isActiveProperty;
	private BooleanBinding isFlyingProperty;
	private double speed;

	public Duck(BooleanProperty running) {
		super(MovableObjectType.DUCK);
		isActiveProperty = new SimpleBooleanProperty(false);
		this.isFlyingProperty = isActiveProperty.and(running);
		isFlyingProperty.addListener((ov, o, n) -> startThread());
		reset();
	}

	private void startThread() {
		if (isFlyingProperty.get()) {
			Thread duckFlyThread = new Thread(new DuckFlyTask());
			duckFlyThread.setDaemon(true);
			duckFlyThread.start();
		}
	}

	private void reset() {
		relXproperty.set(INITIAL_X);
		relYproperty.set(INITIAL_Y);
		speed = 0.0;
	}

	public void toggleActive() {
		this.isActiveProperty.set(!isActiveProperty.get());
		reset();
	}

	private class DuckFlyTask extends Task<Void> {

		private static final int DUCKFLY_FPS = 11;

		@Override
		protected Void call() {
			while (isFlyingProperty.get()) {
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
		return false;
	}

	public void keyPressed(KeyCode keyCode) {
		switch (keyCode) {
		case D:
			toggleActive();
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

	public BooleanProperty isActiveProperty() {
		return isActiveProperty;
	}

	public BooleanBinding isFlyingProperty() {
		return isFlyingProperty;
	}
}
