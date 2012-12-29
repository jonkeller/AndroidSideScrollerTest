package name.keller.jon.androidsidescrollertest;

public class Coordinates {
	public static final float EFFECTIVELY_ZERO_THRESHOLD = 0.1f; // Items closer than this will be considered to be touching
	
	private float _x;
	private float _y;
	private float _z;
	
	public Coordinates(float x, float y, float z) {
		_x = x;
		_y = y;
		_z = z;
	}
	
	public float getX() {
		return _x;
	}
	
	public float getY() {
		return _y;
	}

	public float getZ() {
		return _z;
	}

	public void setX(float x) {
		_x = x;
	}

	public void setY(float y) {
		_y = y;
	}

	public void setZ(float z) {
		_z = z;
	}

	public void incrementX(float x) {
		_x += x;
	}

	public void incrementY(float y) {
		_y += y;
	}

	public void incrementZ(float z) {
		_z += z;
	}

	public static Coordinates add(Coordinates a, Coordinates b) {
		return new Coordinates(a._x + b._x, a._y + b._y, a._z + b._z);
	}

	public boolean isXEffectivelyZero() {
		return Utilities.isEffectivelyZero(_x);
	}

	public boolean isYEffectivelyZero() {
		return Utilities.isEffectivelyZero(_y);
	}

	public float unsignedXDifference(Coordinates other) {
		return Math.abs(_x - other._x);
	}

	public float unsignedYDifference(Coordinates other) {
		return Math.abs(_y - other._y);
	}

	public float unsignedZDifference(Coordinates other) {
		return Math.abs(_z - other._z);
	}
}
