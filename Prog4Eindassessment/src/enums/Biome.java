package enums;

public enum Biome {
	FOREST("SKYBLUE", "GREEN"), TOWN("SKYBLUE", "SANDYBROWN"), MAGICAL("BLUE", "RED");

	private String skyColor;
	private String groundColor;

	Biome(String skyColor, String groundColor) {
		this.skyColor = skyColor;
		this.groundColor = groundColor;
	}

	public String getSkyColor() {
		return skyColor;
	}

	public String getGroundColor() {
		return groundColor;
	}

}
