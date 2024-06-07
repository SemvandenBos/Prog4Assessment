package enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TreeSize {
	S(0.7), M(1.0), L(1.3), XL(1.5), XXL(1.8);

	private double scaleValue;
	private static final List<TreeSize> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	private TreeSize(double scaleValue) {
		this.scaleValue = scaleValue;
	}

	public static TreeSize randomSize() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	public double getScaleValue() {
		return scaleValue;
	}
}
