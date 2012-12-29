package name.keller.jon.androidsidescrollertest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public abstract class Sprite {
	protected Coordinates _center;
    protected Coordinates _dimensions;
	public char type; // TODO: For testing only.  Remove
    
    public Sprite(float centerX, float centerY, float z, int width, int height) {
    	_center = new Coordinates(centerX, centerY, z);
    	_dimensions = new Coordinates(width, height, 0f);
    }

    public Coordinates getCenter() {
        return _center;
    }

    public Coordinates getDimensions() {
        return _dimensions;
    }

    public abstract Bitmap getBitmap();

	public void draw(Canvas canvas) {
		Point p = World.getInstance().project(_center, canvas.getWidth());
        canvas.drawBitmap(getBitmap(), p.x, p.y, null);
	}
}