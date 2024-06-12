package controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.World;

public class SoundHandler {
	private static final String DUCK_QUACK_PATH = "/sounds/duck-quack.wav";

	public SoundHandler(World world) {
		world.getDuck().isActiveProperty().addListener((ob, o, n) -> {
			Media sound = new Media(getClass().getResource(DUCK_QUACK_PATH).toExternalForm());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
		});
	}
}
