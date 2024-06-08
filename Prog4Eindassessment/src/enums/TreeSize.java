package enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TreeSize {
	S(0.7, 0.0, 0.0), M(1.0, 0.02, -0.08), L(1.3, 0.04, -0.16), XL(1.5, 0.06, -0.24), XXL(1.8, 0.08, -0.32);

	private double sizeScaleValue;
	private double colorSaturation;
	private double colorBrightness;

	private static final List<TreeSize> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	private TreeSize(double sizeScaleValue, double colorSaturation, double colorBrightness) {
		this.sizeScaleValue = sizeScaleValue;
		this.colorSaturation = colorSaturation;
		this.colorBrightness = colorBrightness;
	}

	public double getColorSaturation() {
		return colorSaturation;
	}

	public double getColorBrightness() {
		return colorBrightness;
	}

	public static TreeSize randomSize() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	public double getSizeScaleValue() {
		return sizeScaleValue;
	}
}
