package view;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Duck;
import model.World;

public class DuckPane extends StackPane {
	private AnimationDuck animationDuck;

	public DuckPane(World world, Pane root) {
		setBindings(world.getDuck(), root);

		animationDuck = new AnimationDuck();

		getChildren().add(animationDuck);

		world.getDuck().isActiveProperty().addListener((ov, o, n) -> setActive(n));
		world.getDuck().isFlyingProperty().addListener((ov, o, n) -> setFlying(n));
	}

	private void setBindings(Duck duck, Pane paintingPane) {
		DoubleBinding layoutX = duck.getRelXproperty().multiply(paintingPane.widthProperty()).divide(100.0)
				.subtract(widthProperty().multiply(0.5));
		DoubleBinding layoutY = duck.getRelYproperty().multiply(paintingPane.heightProperty()).divide(100.0)
				.subtract(heightProperty().multiply(0.5));
		layoutXProperty().bind(layoutX);
		layoutYProperty().bind(layoutY);
	}

	private void setActive(boolean isActive) {
		animationDuck.setVisible(isActive);
	}

	private void setFlying(boolean isFlying) {
		if (isFlying) {
			animationDuck.fly();
		} else {
			animationDuck.stopFly();
		}
	}
}
