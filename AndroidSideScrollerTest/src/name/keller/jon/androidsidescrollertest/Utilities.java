package name.keller.jon.androidsidescrollertest;

public class Utilities {
	public static boolean isEffectivelyZero(float f) {
		return Math.abs(f) < Coordinates.EFFECTIVELY_ZERO_THRESHOLD;
	}
}
