package enums;

public enum Biome {
	FOREST("SKYBLUE", "GREEN"), WESTERN_FIELDS("SKYBLUE", "SANDYBROWN"), TOWN("LIGHTSKYBLUE", "LIMEGREEN"),
	MOUNTAINS("LIGHTSKYBLUE", "GRAY");

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
