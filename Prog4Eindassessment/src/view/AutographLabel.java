package view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class AutographLabel extends Label {
	private static final Double AUTOGRAPH_SPACING = 10.0;

	public AutographLabel(Pane centerPane) {
		setText("Sem van den Bos");
		setAlignment(Pos.BOTTOM_RIGHT);
		DoubleBinding x = centerPane.widthProperty().subtract(widthProperty()).subtract(AUTOGRAPH_SPACING);
		DoubleBinding y = centerPane.heightProperty().subtract(heightProperty()).subtract(AUTOGRAPH_SPACING);
		layoutXProperty().bind(x);
		layoutYProperty().bind(y);
	}
}
