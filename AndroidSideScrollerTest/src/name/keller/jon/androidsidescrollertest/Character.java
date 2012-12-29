package name.keller.jon.androidsidescrollertest;

import android.graphics.Bitmap;

public class Character extends AnimatedSprite {
	private int _animationCyclesBeforeNextAnimation;
	private int _currentAnimationCycle;
	private int _animationXMovements[][];
	private int _animationYMovements[][];
	
	public Character(float centerX, float centerY, Bitmap[][] animations, int ticksPerFrame, boolean isSubjectToGravity,
			         int animationCyclesBeforeNextAnimation, int animationXMovements[][], int animationYMovements[][]) {
		super(centerX, centerY, 0, animations, ticksPerFrame, isSubjectToGravity);
		_animationCyclesBeforeNextAnimation = animationCyclesBeforeNextAnimation;
		_animationXMovements = animationXMovements;
		_animationYMovements = animationYMovements;
		_currentAnimationCycle = 0;
	}

	/**
	 * Called when one animation has completed a full cycle
	 */
	protected void onAnimationComplete() {
		++_currentAnimationCycle;
		if (_currentAnimationCycle >= _animationCyclesBeforeNextAnimation) {
			_currentAnimationCycle = 0;
			nextAnimation();
		}
	}
	
	public void tick() {
		super.tick();
		
		Coordinates movement = new Coordinates(_animationXMovements[_currentAnimation][_currentFrame],
				                               _animationYMovements[_currentAnimation][_currentFrame], 0f);
		move(movement);
	}

	public void die() {
		// TODO: Implement die()
	}
}
