package view;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Duck;
import model.World;

public class DuckPane extends StackPane {
	private static final String DUCK_IMG_PATH = "./pics/duckshapes.png";
	private AnimationDuck animationDuck;

	public DuckPane(World world, Pane root) {
		setBindings(world.getDuck(), root);

		Image image = new Image(DUCK_IMG_PATH);
		animationDuck = new AnimationDuck(image, 3, 2);

		getChildren().add(animationDuck);
		world.getDuck().isFlyingProperty().addListener((ov, o, n) -> setFlying(n));
		world.getDuck().isFlyingProperty().and(world.getRunning());
	}

	private void setBindings(Duck duck, Pane paintingPane) {
		DoubleBinding layoutX = duck.getRelXproperty().multiply(paintingPane.widthProperty()).divide(100.0)
				.subtract(widthProperty().multiply(0.5));
		DoubleBinding layoutY = duck.getRelYproperty().multiply(paintingPane.heightProperty()).divide(100.0)
				.subtract(heightProperty().multiply(0.5));
		layoutXProperty().bind(layoutX);
		layoutYProperty().bind(layoutY);
	}

	private void setFlying(boolean isFlying) {
		if (isFlying) {
			animationDuck.fly();
		} else {
			animationDuck.stopFly();
		}
//		if (isFlying) {
//			animationDuck.fly();
//		} else {
//			animationDuck.stopFly();
//		}
	}
}
