package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

class AnimationDuck extends ImageView {
	private static final int DUCK_FPS = 11;
	private static final Duration FRAME_TIME = Duration.seconds(1.0 / DUCK_FPS);

	private final IntegerProperty frameCounter = new SimpleIntegerProperty(0);
	private Timeline flyTimeline;
	private static final int[] FRAME_ORDER = new int[] { 3, 4, 5, 0, 1, 2, 2, 1, 0, 5, 4, 3 };

	private final Rectangle2D[] cellClips;

	public AnimationDuck(Image image, int numColumns, int numRows) {
		double cellWidth = image.getWidth() / numColumns;
		double cellHeight = image.getHeight() / numRows;

		// Create frames
		cellClips = new Rectangle2D[numColumns * numRows];
		for (int i = 0; i < cellClips.length; i++) {
			double startX = i % numColumns * cellWidth;
			double startY = i / numColumns * cellHeight;
			cellClips[i] = new Rectangle2D(startX, startY, cellWidth, cellHeight);
		}

		// Create animation timeline
		flyTimeline = new Timeline(new KeyFrame(FRAME_TIME, event -> {
			frameCounter.set((frameCounter.get() + 1) % FRAME_ORDER.length);
			setViewport(cellClips[FRAME_ORDER[frameCounter.get()]]);
		}));
		flyTimeline.setCycleCount(Timeline.INDEFINITE);

		setImage(image);
		setViewport(cellClips[FRAME_ORDER[0]]);
		setVisible(false);
	}

	public void fly() {
		flyTimeline.play();
		setVisible(true);
	}

	public void stopFly() {
		flyTimeline.stop();
//		setVisible(false);
	}

}