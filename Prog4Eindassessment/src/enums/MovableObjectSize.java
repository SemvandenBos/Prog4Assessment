package enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum MovableObjectSize {
	S(0.75, 0.0, 0.0), M(0.9, 0.02, -0.08), L(1.0, 0.04, -0.16), XL(1.2, 0.06, -0.24), XXL(1.4, 0.08, -0.32);

	private double sizeScaleValue;
	private double colorSaturation;
	private double colorBrightness;

	private static final List<MovableObjectSize> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	private MovableObjectSize(double sizeScaleValue, double colorSaturation, double colorBrightness) {
		this.sizeScaleValue = sizeScaleValue;
		this.colorSaturation = colorSaturation;
		this.colorBrightness = colorBrightness;
	}

	public static MovableObjectSize randomSize() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	public double getColorSaturation() {
		return colorSaturation;
	}

	public double getColorBrightness() {
		return colorBrightness;
	}

	public double getSizeScaleValue() {
		return sizeScaleValue;
	}
}
