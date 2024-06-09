package enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TreeType {

	PINE, LEAF;

	public static TreeType valueOf() {
		return null;
	}

	private static final List<TreeType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static TreeType randomType() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
