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
	private static final double TREESPAWNCHANCE = 0.2;
	private static final Biome[] BIOMES_LIST = { Biome.FOREST, Biome.TOWN, Biome.MAGICAL };
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
		@Override
		protected Void call() throws Exception {
			random = new Random();
			while (running.get()) {
				Platform.runLater(() -> {
					// Used iterator to savely remove trees and 1 by 1
					moveTrees();
					handleSpawnObjects(videoProgression);
					if (videoProgression > 100) {
						changeBiome();
					}
					videoProgression++;
//					System.out.println(videoProgression);
				});
				try {
					Thread.sleep(1000 / FRAMERATE);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			return null;
		}

		private void moveTrees() {
			ListIterator<MovableObject> iter = trees.listIterator();
			while (iter.hasNext()) {
				if (iter.next().move()) {
					iter.remove();
				}
			}
		}

		private void changeBiome() {
			selectedBiome.set((selectedBiome.get() + 1) % BIOMES_LIST.length);
			videoProgression = 0;
		}

		// TODO implement random spawn objects
		private void handleSpawnObjects(int videoProgression) {
			if (random.nextDouble() < TREESPAWNCHANCE) {
				Tree t = new Tree(TreeType.randomType());
				t.placeLeft();
				addObjectAtIndex(t);
			}

			if (videoProgression % 50 != 0) {
				return;
			}
			System.out.println("triggered");

			switch (BIOMES_LIST[selectedBiome.get()]) {
			case FOREST:
				addOrb();
				break;
			case TOWN:

				break;
			case MAGICAL:
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
		Tree t = new Tree(type);
		addObjectAtIndex(t);
	}

	public void addRandomTrees(int amountOfTrees) {
		for (int i = 0; i < amountOfTrees; i++) {
			addObjectAtIndex(new Tree(TreeType.randomType()));
		}
	}

	public void addTreeBatch(List<Tree> trees) {
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

	public void addOrb() {
		addObjectAtIndex(new MovableObject(MovableObjectType.WINDMILL));
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
}
