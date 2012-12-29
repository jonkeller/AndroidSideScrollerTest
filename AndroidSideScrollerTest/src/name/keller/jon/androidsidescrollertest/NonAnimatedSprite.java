package name.keller.jon.androidsidescrollertest;

import android.graphics.Bitmap;

public class NonAnimatedSprite extends Sprite {
    private Bitmap _bitmap;

    public NonAnimatedSprite(int centerX, int centerY, int z, Bitmap bitmap) {
		super(centerX, centerY, z, bitmap.getWidth(), bitmap.getHeight());
    	_bitmap = bitmap;
	}

	@Override
    public Bitmap getBitmap() {
        return _bitmap;
    }
}
