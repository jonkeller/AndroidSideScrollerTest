package name.keller.jon.androidsidescrollertest;

import android.graphics.Bitmap;

public abstract class AnimatedSprite extends Sprite {
	public static final int TAKEOFF_SPEED = -20; // TODO: This should be able to be different for different sprites
	protected Bitmap[][] _animations;
	protected int _currentAnimation;
    protected int _currentFrame;
    protected int _currentTick;
    protected int _ticksPerFrame;
	boolean _isSubjectToGravity;
	protected float _verticalSpeed;
    
	public AnimatedSprite(float centerX, float centerY, float z, Bitmap[][] animations, int ticksPerFrame, boolean isSubjectToGravity) {
		super(centerX, centerY, z, animations[0][0].getWidth(), animations[0][0].getHeight());
		_animations = animations;
		_currentAnimation = 0;
		_currentFrame = 0;
		_currentTick = 0;
		_ticksPerFrame = ticksPerFrame;
		_isSubjectToGravity = isSubjectToGravity;
		_verticalSpeed = 0f;
	}

	public void tick() {
		handleAirborne();
		
		// Update animation
		++_currentTick;
		if (_currentTick >= _ticksPerFrame) {
			_currentTick = 0;
			++_currentFrame;
			if (_currentFrame >= _animations[_currentAnimation].length) {
				_currentFrame = 0;
				onAnimationComplete();
			}
		}
	}

	protected abstract void onAnimationComplete();
	
	protected void nextAnimation() {
		setAnimation((++_currentAnimation) % _animations.length);
	}

	protected void setAnimation(int which) {
		_currentAnimation = which;
	}
	
	@Override
    public Bitmap getBitmap() {
    	return _animations[_currentAnimation][_currentFrame];
    }
	
	public void beginJump() {
		if (isOnTopOfSomething()) {
    		_verticalSpeed = TAKEOFF_SPEED;
		}
	}

	private void handleAirborne() {
		if (_isSubjectToGravity) {
			if (_verticalSpeed < 0 || !isOnTopOfSomething()) {
        		_verticalSpeed += World.GRAVITY;
        		move(new Coordinates(0f, _verticalSpeed, 0f));
			} else {
				_verticalSpeed = 0;
			}
		}
	}
	
	public void move(Coordinates desiredRelativeDistance) {
    	// TODO: Collision detection still a little buggy when moving both vertically and horizontally
    	// TODO: onCollide() for bad guys, items, etc.
		
		if (!desiredRelativeDistance.isXEffectivelyZero()) {
			// Clip movement in x direction
			getCenter().incrementX(getNonImpededXDistance(desiredRelativeDistance.getX()));
		}
		
		if (!desiredRelativeDistance.isYEffectivelyZero()) {
			// Clip movement in y direction wrt other Sprites
			getCenter().incrementY(getNonImpededYDistance(desiredRelativeDistance.getY()));

			clipToCanvasTopAndBottom();
		}
		
		// Current implementation only allows for 2D sprites moving within their XY planes.  Z only
		// exists to allow for multiple such independant sandboxes.
	}

	private float getNonImpededXDistance(float x) {
		Sprite impediment = getImpedimentToMotion(new Coordinates(x, 0f, 0f));
		if (null == impediment) {
			return x;
		} else {
			return getSignedXDistanceTo(impediment);
		}
	}

	private float getNonImpededYDistance(float y) {
		Sprite impediment = getImpedimentToMotion(new Coordinates(0f, y, 0f));
		if (null == impediment) {
			return y;
		} else {
			if (_verticalSpeed < 0f) {
				_verticalSpeed = -_verticalSpeed;
			}
			return getSignedYDistanceTo(impediment);
		}
	}
	
	public boolean isOnTopOfSomething() {
		return (null != getImpedimentToMotion(new Coordinates(0f, Coordinates.EFFECTIVELY_ZERO_THRESHOLD, 0f)));
	}

	private Sprite getImpedimentToMotion(Coordinates tentativeMotion) {
		Coordinates tentativePosition = Coordinates.add(getCenter(), tentativeMotion);
		for (Sprite other : World.getInstance().getSprites()) {
			if (this != other && impedesMotion(tentativePosition, other)) {
				return other;
			}
		}
		return null;
	}

	private float getSignedXDistanceTo(Sprite impediment) {
		float radii = (getDimensions().getX() + impediment.getDimensions().getX())/2;
		float distanceBetweenCenters = impediment.getCenter().getX() - getCenter().getX();
		if (impediment.getCenter().getX() > getCenter().getX()) {
			return distanceBetweenCenters - radii;
		} else {
			return distanceBetweenCenters + radii;
		}
	}

	private float getSignedYDistanceTo(Sprite impediment) {
		float radii = (getDimensions().getY() + impediment.getDimensions().getY())/2;
		float distanceBetweenCenters = impediment.getCenter().getY() - getCenter().getY();
		if (impediment.getCenter().getY() > getCenter().getY()) {
			return distanceBetweenCenters - radii;
		} else {
			return distanceBetweenCenters + radii;
		}
    }

	/**
	 * Returns true if moving this to tentativePosition would result in this being colocated with other.
	 * Does NOT consider whether moving this to tentativePosition would necessitate this passing through other and out the other side.
	 */
	private boolean impedesMotion(Coordinates tentativePosition, Sprite other) {
		if (!isSameLayer(other)) {
			return false;
		}
		float xRadii = (getDimensions().getX() + other.getDimensions().getX())/2;
		float yRadii = (getDimensions().getY() + other.getDimensions().getY())/2;
		return (tentativePosition.unsignedXDifference(other.getCenter()) < xRadii) &&
			   (tentativePosition.unsignedYDifference(other.getCenter()) < yRadii);
	}
	
	private boolean isSameLayer(Sprite other) {
		return Utilities.isEffectivelyZero(getCenter().unsignedZDifference(other.getCenter()));
	}

	private void clipToCanvasTopAndBottom() {
		// Max height = top of screen
		if (getCenter().getY() <= 0f) {
			getCenter().setY(0f);
			_verticalSpeed = -_verticalSpeed;
		}
		// Min height = bottom of screen
		if (getCenter().getY() >= GameEngine.getInstance().getPanel().getHeight()) {
			die();
		}
	}

	public abstract void die();
}
