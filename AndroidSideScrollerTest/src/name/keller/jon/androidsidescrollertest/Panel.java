package name.keller.jon.androidsidescrollertest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Panel extends SurfaceView implements SurfaceHolder.Callback {
    private World _world = null;
	int startX;
	int startY;
    
    public Panel(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
		GameEngine.getInstance().setPanel(this);
		_world = World.getInstance();
	}

	@Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (Sprite sprite : _world.getSprites()) {
        	sprite.draw(canvas);
        }
    }

	// TODO: Use items

	@Override
    public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		// TODO: Controls in special area of screen?
        synchronized (GameEngine.getInstance().getSurfaceHolder()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                startX = x;
                startY = y;
            } else if ((event.getAction() == MotionEvent.ACTION_MOVE) ||
                       (event.getAction() == MotionEvent.ACTION_UP)) {
            	_world.getPlayer().move(new Coordinates(x-startX, 0f, 0f));
            	if (startY - y > 25) {
            		_world.getPlayer().beginJump();
            	}
                startX = x;
                startY = y;
            }
        }
        return true;
    }
    
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("surfaceChanged. New width=" + width + ", height=" + height);
    }

    public void surfaceCreated(SurfaceHolder holder) {
		WorldReader reader = new WorldReader(_world, getWidth());
        reader.populate(getResources());
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.start();
        System.out.println("surfaceCreated");
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    	GameEngine.getInstance().setRunning(false);
        boolean retry = true;
        System.out.println("surfaceDestroyed");
        while (retry) {
            try {
            	GameEngine.getInstance().join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}