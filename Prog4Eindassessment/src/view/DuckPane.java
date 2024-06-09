package view;

import javax.security.auth.Subject;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.Duck;

public class DuckPane extends StackPane {
	private static final String DUCK_IMG_PATH = "./pics/duckshapes.png";
	private AnimationDuck animationDuck;

	public DuckPane(Duck duck, Pane root) {
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null, new Insets(2))));

		setBindings(duck, root);

		Image image = new Image(DUCK_IMG_PATH);
		animationDuck = new AnimationDuck(image, 3, 2);
		getChildren().add(animationDuck);
		duck.isFlyingProperty().addListener((ov, o, n) -> setFlying(n));
	}

	private void setBindings(Duck duck, Pane root) {
		DoubleBinding x = duck.getRelXproperty().multiply(root.widthProperty()).divide(100.0)
				.subtract(widthProperty().multiply(0.5));
		DoubleBinding y = duck.getRelYproperty().multiply(root.heightProperty()).divide(100.0)
				.subtract(heightProperty().multiply(0.5));
		layoutXProperty().bind(x);
		layoutYProperty().bind(y);
	}

	private void setFlying(boolean isFlying) {
		if (isFlying) {
			animationDuck.fly();
		} else {
			animationDuck.stopFly();
		}
	}
}
