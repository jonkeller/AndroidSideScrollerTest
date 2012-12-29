package name.keller.jon.androidsidescrollertest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

public class World implements Serializable {
	private static final long serialVersionUID = -4439398544536231352L;

	public static final float GRAVITY = 0.7f;
	// TODO Gravity towards somewhere other than down (aka wind)?
	
	public static final int EYE_DISTANCE = 35;

	private static World _instance = null;
    private ArrayList<AnimatedSprite> _animatedSprites = new ArrayList<AnimatedSprite>();
    private ArrayList<NonAnimatedSprite> _nonAnimatedSprites = new ArrayList<NonAnimatedSprite>();
    private ArrayList<Sprite> _sprites = new ArrayList<Sprite>(); // Union of the previous two lists
    private Player _player = null;
    
	private World() {
	}
	
	public static World getInstance() {
		if (null == _instance) {
			_instance = new World();
		}
		return _instance;
	}

	public void addAnimatedSprite(AnimatedSprite animatedSprite) {
		_animatedSprites.add(animatedSprite);
		_sprites.add(animatedSprite);
	}
	
	public void addNonAnimatedSprite(NonAnimatedSprite nonAnimatedSprite) {
		_nonAnimatedSprites.add(nonAnimatedSprite);
		_sprites.add(nonAnimatedSprite);
	}
	
	public List<AnimatedSprite> getAnimatedSprites() {
		return _animatedSprites;
	}

	public List<NonAnimatedSprite> getNonAnimatedSprites() {
		return _nonAnimatedSprites;
	}
	
	public List<Sprite> getSprites() {
		return _sprites;
	}
	
	public void setPlayer(Player player) throws Exception {
		if (null != _player) {
			throw new Exception("Player already added!");
		}
		_player = player;
		addAnimatedSprite(_player);
	}
	
	public Player getPlayer() {
		return _player;
	}
	
	protected Point project(Coordinates coordinates, int screenWidth) {
		float playerX = getPlayer().getCenter().getX();
		float xBeforePerspectiveAdjustment = coordinates.getX() - playerX;
		float xAfterPerspectiveAdjustment = EYE_DISTANCE * xBeforePerspectiveAdjustment / (EYE_DISTANCE - coordinates.getZ());

		int halfScreenWidth = screenWidth / 2;
		return new Point(halfScreenWidth + (int)xAfterPerspectiveAdjustment, (int)coordinates.getY());
	}

  	protected Point unProject(float x, float y, float z, int screenWidth) {
        x = (x * (EYE_DISTANCE - z)) / EYE_DISTANCE;
		int halfScreenWidth = screenWidth / 2;
		return new Point(halfScreenWidth + (int)x, (int)y);
	}
	
/*
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// write 'this' to 'out'...
	}	

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// populate the fields of 'this' from the data in 'in'...
	}
*/
}
