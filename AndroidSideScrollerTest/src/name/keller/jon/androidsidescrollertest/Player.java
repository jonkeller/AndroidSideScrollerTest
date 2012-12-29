package name.keller.jon.androidsidescrollertest;

import android.graphics.Bitmap;

public class Player extends AnimatedSprite {
	
	int _lives;
	int _coins;
	
	public Player(float centerX, float centerY, Bitmap[][] animations, int ticksPerFrame) {
		super(centerX, centerY, 0.01f, animations, ticksPerFrame, true);
	}

	@Override
	protected void onAnimationComplete() {
		// Don't care
	}

	public void die() {
		getCenter().setY(GameEngine.getInstance().getPanel().getHeight());
		// TODO: Implement die()
	}
}
