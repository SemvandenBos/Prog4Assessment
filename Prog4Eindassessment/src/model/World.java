package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import enums.MovableObjectType;
import enums.TreeType;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

public class World {
	private static final int FRAMERATE = 24;
	private static final double TREESPAWNCHANCE = 0.2;

	private ListProperty<MovableObject> trees;
	private boolean running;
	private Duck duck;
	private Random random;

	public World() {
		trees = new SimpleListProperty<>(FXCollections.observableArrayList());
		duck = new Duck();
		random = new Random();
	}

	public void toggleMovie() {
		running = !running;
		if (running) {
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
			while (running) {
				Platform.runLater(() -> {
					// Used iterator to savely remove trees and 1 by 1
					ListIterator<MovableObject> iter = trees.listIterator();
					while (iter.hasNext()) {
						if (iter.next().move()) {
							iter.remove();
						}
					}
					if (random.nextDouble() < TREESPAWNCHANCE) {
						addTrees(1);
					}
				});
				try {
					Thread.sleep(1000 / FRAMERATE);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			return null;
		}
	}

	public void keyPressed(KeyCode keyCode) {
		switch (keyCode) {
		case D:
			duck.toggleFlying();
			break;
		case LEFT:
			duck.setFlySpeed(-1.0);
			break;
		case RIGHT:
			duck.setFlySpeed(1.0);
			break;
		case DOWN:
			duck.setFlySpeed(0);
			break;
		default:
			break;
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

	public void addTree(TreeType type) {
		Tree t = new Tree(type);
		addObjectAtIndex(t);
	}

	public void addTrees(int amountOfTrees) {
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

	public List<MovableObject> getTrees() {
		return trees.get();
	}

	public ListProperty<MovableObject> treesProperty() {
		return trees;
	}

	public Duck getDuck() {
		return duck;
	}

	public void addOrb() {
		addObjectAtIndex(new MovableObject(MovableObjectType.HOUSE));
	}

	public void adjustDepth(MovableObject movableObject) {
		int index = trees.get().indexOf(movableObject);
		System.out.println(index);
		trees.get().remove(index);
		addObjectAtIndex(movableObject);
	}
}
