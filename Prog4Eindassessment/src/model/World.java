package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import enums.Biome;
import enums.MovableObjectType;
import enums.TreeType;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

public class World {
	private static final int FRAMERATE = 24;
	private static final double TREESPAWNCHANCE = 0.3;
	private static final Biome[] BIOMES_LIST = { Biome.FOREST, Biome.WESTERN_FIELDS, Biome.TOWN };
	private IntegerProperty selectedBiome;

	private int videoProgression;

	private ListProperty<MovableObject> trees;
	private BooleanProperty running;
	private Duck duck;
	private Random random;

	public World() {
		trees = new SimpleListProperty<>(FXCollections.observableArrayList());
		running = new SimpleBooleanProperty();
		duck = new Duck(running);
		random = new Random();
		selectedBiome = new SimpleIntegerProperty();
	}

	public void toggleMovie() {
		running.set(!running.get());
		if (running.get()) {
			TreeTask treeTask = new TreeTask();
			Thread treeThread = new Thread(treeTask);
			treeThread.setDaemon(true);
			treeThread.start();
		}
	}

	private class TreeTask extends Task<Void> {
		private static final int BIOME_DURATION = 249;
		private static final int SPECIAL_OBJECT_FREQUENCY = 25;

		@Override
		protected Void call() throws Exception {
			random = new Random();
			while (running.get()) {
				Platform.runLater(() -> {
					moveTrees();
					handleSpawnObjects(videoProgression);
					if (videoProgression > BIOME_DURATION) {
						nextBiome();
					}
					videoProgression++;
				});
				try {
					Thread.sleep(1000 / FRAMERATE);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			return null;
		}

		// Uses iterator to safely delete while looping
		private void moveTrees() {
			ListIterator<MovableObject> iter = trees.listIterator();
			while (iter.hasNext()) {
				if (iter.next().move()) {
					iter.remove();
				}
			}
		}

		private void handleSpawnObjects(int videoProgression) {
			double randDouble = random.nextDouble();
			switch (BIOMES_LIST[selectedBiome.get()]) {
			case FOREST:
				if (randDouble < TREESPAWNCHANCE) {
					Tree t = new Tree(TreeType.randomType(), false);
					t.placeLeft();
					addObjectAtIndex(t);
				}
				break;
			case WESTERN_FIELDS:
				if (videoProgression % SPECIAL_OBJECT_FREQUENCY == 0) {
					addMovableObject(MovableObjectType.WINDMILL);
					addMovableObject(MovableObjectType.TUMBLEWEED);
				}
				break;
			case TOWN:
				if (randDouble < TREESPAWNCHANCE / 3f) {
					Tree t = new Tree(TreeType.randomType(), false);
					t.placeLeft();
					addObjectAtIndex(t);
				}
				if (videoProgression % SPECIAL_OBJECT_FREQUENCY == 0) {
					addMovableObject(MovableObjectType.HOUSE);
					addMovableObject(MovableObjectType.WHEATPATCH);
				}
				break;
			default:
				break;
			}
		}
	}

	// Find index in sorted tree list for depth view
	private void addObjectAtIndex(MovableObject tree) {
		int index = Collections.binarySearch(trees, tree, Comparator.comparingDouble(MovableObject::getRelY));
		if (index < 0) {
			index = -index - 1;
		}
		trees.add(index, tree);
	}

	public void addTreeOfType(TreeType type) {
		Tree t = new Tree(type, true);
		addObjectAtIndex(t);
	}

	public void addRandomTrees(int amountOfTrees) {
		for (int i = 0; i < amountOfTrees; i++) {
			addObjectAtIndex(new Tree(TreeType.randomType(), true));
		}
	}

	public void addTreeList(List<Tree> trees) {
		clearAllTrees();
		for (Tree tree : trees) {
			addObjectAtIndex(tree);
		}
	}

	public void clearAllTrees() {
		trees.clear();
	}

	public ListProperty<MovableObject> treesProperty() {
		return trees;
	}

	public void addMovableObject(MovableObjectType type) {
		addObjectAtIndex(new MovableObject(type));
	}

//	----------DUCK--------------------
	public void keyPressed(KeyCode keyCode) {
		duck.keyPressed(keyCode);
	}

	public Duck getDuck() {
		return duck;
	}

//	----------DRAG AND DROP----------------
	public void setRelPoint(double xRel, double yRel, MovableObject movableObject) {
		if (!running.get()) {
			movableObject.setRelPoint(xRel, yRel);
		}
	}

	public void adjustDepth(MovableObject movableObject) {
		if (!running.get()) {
			int index = trees.get().indexOf(movableObject);
			trees.get().remove(index);
			addObjectAtIndex(movableObject);
		}
	}

	public BooleanProperty getRunning() {
		return running;
	}

//	----------BIOMES-------------------
	public Biome getCurrentBiome() {
		return BIOMES_LIST[selectedBiome.get()];
	}

	public IntegerProperty selectedBiomeProperty() {
		return selectedBiome;
	}

	public void nextBiome() {
		selectedBiome.set((selectedBiome.get() + 1) % BIOMES_LIST.length);
		videoProgression = 0;
	}
}
