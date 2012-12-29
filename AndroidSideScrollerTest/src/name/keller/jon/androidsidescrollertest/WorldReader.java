package name.keller.jon.androidsidescrollertest;

import name.keller.jon.sidescrollertest.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class WorldReader {
	private int _canvasWidth;
	private World _world;
	
	public WorldReader(World world, int canvasWidth) {
		_world = world;
		_canvasWidth = canvasWidth;
	}
	
	public void populate(Resources resources) {
		// TODO: Repeat background layers
		addLayer(resources, R.drawable.sky, -30);
		addLayer(resources, R.drawable.mountains, -20);
		addLayer(resources, R.drawable.hills, -10);

		addMap(resources, R.array.map, 0);
//		addMap(resources, R.array.test, 0);
	}
	
	private void addLayer(Resources resources, int layerResource, int z) {
	    BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inScaled = false;
		Point p = _world.unProject(-_canvasWidth / 2, 0, z, _canvasWidth);
		NonAnimatedSprite sprite = new NonAnimatedSprite(p.x, p.y, z, BitmapFactory.decodeResource(resources, layerResource, options));
		sprite.type = 'L';
		_world.addNonAnimatedSprite(sprite);
	}
	
	private void addMap(Resources resources, int mapResource, int z) {
		String[] map = resources.getStringArray(mapResource);
		
	    int width = 48;
	    int height = 48;
	    
	    BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inScaled = false;
	    
	    for (int i=0; i<map.length; i++) {
	    	String row = map[i];
	    	for (int j=0; j<row.length(); ++j) {
	    		int resource = 0;
	    		boolean found = true;
	    		char spriteType = row.charAt(j);
	    		switch (spriteType) {
	    		case 'R': resource = R.drawable.red; break;
	    		case 'O': resource = R.drawable.orange; break;
	    		case 'Y': resource = R.drawable.yellow; break;
	    		case 'G': resource = R.drawable.green; break;
	    		case 'B': resource = R.drawable.blue; break;
	    		case 'W': resource = R.drawable.white; break;
	    		case 'N': resource = R.drawable.brown; break;
	    		case 'A': resource = R.drawable.gray; break;
	    		case 'P': resource = R.drawable.purple; break;
	    		case 'I': resource = R.drawable.icon; break;
	    		case 'K': resource = R.drawable.black; break;
	    		default: found = false; break;
	    		}
	    		
	    		if (found) {
		    		int x = width*j;
		    		int y = height*i;
	    			Point p = _world.unProject(x, y, z, _canvasWidth);

	    			if (spriteType == 'I') {
		    			try {
		    				Bitmap[][] animations = new Bitmap[1][1];
		    				animations[0][0] = BitmapFactory.decodeResource(resources, resource, options);
    		    			Player player = new Player(p.x, p.y, animations, 20);
	    	    			_world.setPlayer(player);
	    	    			player.type = spriteType;
		    			} catch (Exception e) {
		    				e.printStackTrace();
		    			}
		    		} else {
			    		NonAnimatedSprite sprite = new NonAnimatedSprite(p.x, p.y, z, BitmapFactory.decodeResource(resources, resource, options));
			    		sprite.type = spriteType;
			    		_world.addNonAnimatedSprite(sprite);
		    		}
	    		}
	    	}
	    }
	}
}
