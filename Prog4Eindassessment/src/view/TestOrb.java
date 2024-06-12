package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class TestOrb extends ImageView {
	private static final int DUCK_FPS = 11;
	private static final Duration FRAME_TIME = Duration.seconds(1.0 / DUCK_FPS);

	private final IntegerProperty frameCounter = new SimpleIntegerProperty(0);
	private Timeline flyTimeline;

	private final Rectangle2D[] cellClips;

	public TestOrb(int numColumns, int numRows) {
		Image image = new Image("./pics/explosion.png");
		double cellWidth = image.getWidth() / numColumns;
		double cellHeight = image.getHeight() / numRows;
		System.out.println(cellWidth + " " + cellHeight);

		cellClips = new Rectangle2D[numColumns * numRows];
		for (int i = 0; i < cellClips.length; i++) {
			double startX = i % 3 * cellWidth;
			double startY = i / 3 * cellHeight;
			cellClips[i] = new Rectangle2D(startX, startY, cellWidth, cellHeight);
		}

		flyTimeline = new Timeline(new KeyFrame(FRAME_TIME, event -> {
			int[] frameOrder = new int[] { 3, 4, 5, 0, 1, 2, 2, 1, 0, 5, 4, 3 };
			frameCounter.set((frameCounter.get() + 1) % frameOrder.length);
			setViewport(cellClips[frameOrder[frameCounter.get()]]);
			setViewport(cellClips[frameCounter.get()]);
		}));
//		flyTimeline.setCycleCount(num);

		setImage(image);
		setViewport(cellClips[3]);
		setVisible(false);
	}

	public void fly() {
		flyTimeline.play();
		setVisible(true);
	}

	public void stopFly() {
		flyTimeline.stop();
		setVisible(false);
	}

}
