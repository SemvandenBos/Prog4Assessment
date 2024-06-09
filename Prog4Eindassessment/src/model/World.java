package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import enums.TreeType;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

public class World {
	private ListProperty<MovableObject> trees;
	private boolean running;
	private Duck duck;

	public World() {
		trees = new SimpleListProperty<>(FXCollections.observableArrayList());
		duck = new Duck();
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

		private static final int FRAMERATE = 24;

		@Override
		protected Void call() throws Exception {
			while (running) {
				Platform.runLater(() -> {
					for (MovableObject tree : trees) {
						tree.move();
					}
				});
				try {
					Thread.sleep(1000 / FRAMERATE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	private void addTreeAtIndex(MovableObject tree) {
		int index = Collections.binarySearch(trees, tree, Comparator.comparingDouble(MovableObject::getRelY));
		if (index < 0) {
			index = -index - 1;
		}
		trees.add(index, tree);
	}

	public void addTree(TreeType type) {
		Tree t = new Tree(type);
		addTreeAtIndex(t);
	}

	public void addTreeBatch(int amountOfTrees) {
		for (int i = 0; i < amountOfTrees; i++) {
			addTreeAtIndex(new Tree(TreeType.randomType()));
		}
	}

	public void addTreeBatch(List<Tree> trees) {
		clearAllTrees();
		for (Tree tree : trees) {
			addTreeAtIndex(tree);
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
}
