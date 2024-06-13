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
	private static final IntegerProperty frameCounter = new SimpleIntegerProperty(0);
	private static final int[] FRAME_ORDER = new int[] { 3, 4, 5, 0, 1, 2, 2, 1, 0, 5, 4, 3 };

	private static final int COLUMNS = 3;
	private static final int ROWS = 2;
	private static final String DUCK_IMG_PATH = "./pics/duckshapes.png";
	private final Rectangle2D[] cellClips;
	private Timeline flyTimeline;

	public AnimationDuck() {
		Image image = new Image(DUCK_IMG_PATH);
		double cellWidth = image.getWidth() / COLUMNS;
		double cellHeight = image.getHeight() / ROWS;

		// Create frames
		cellClips = new Rectangle2D[COLUMNS * ROWS];
		for (int i = 0; i < cellClips.length; i++) {
			double startX = i % COLUMNS * cellWidth;
			double startY = i / COLUMNS * cellHeight;
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
	}

	public void stopFly() {
		flyTimeline.stop();
	}

}