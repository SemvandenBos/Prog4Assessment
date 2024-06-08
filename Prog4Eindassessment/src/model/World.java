package model;

import java.util.List;

import enums.TreeType;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class World {
	private ListProperty<Tree> trees;
	private boolean running;

	public World() {
		running = false;
		trees = new SimpleListProperty<>(FXCollections.observableArrayList());
	}

	public void toggleMovie() {
		running = !running;
		if (running) {
			Thread t = new Thread(new TreeRunnable());
			t.setDaemon(true);
			t.start();
		}
	}

	private class TreeRunnable implements Runnable {

		private static final int FRAMERATE = 24;

		@Override
		public void run() {
			while (running) {
				for (Tree tree : trees) {
					tree.move();
				}
				try {
					Thread.sleep(1000 / FRAMERATE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void addTree(TreeType type) {
		trees.add(new Tree(type));
	}

	public void clearAllTrees() {
		trees.clear();
	}

	public ListProperty<Tree> treesProperty() {
		return trees;
	}

	public void addTreeBatch() {
		for (int i = 0; i < 100; i++) {
			this.trees.add(new Tree(TreeType.randomType()));
		}
	}

	public void addTreeBatch(List<Tree> trees) {
		this.trees.clear();
		this.trees.setAll(trees);
	}

	public List<Tree> getTrees() {
		return trees.get();
	}

}
