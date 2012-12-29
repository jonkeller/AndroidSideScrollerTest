package name.keller.jon.androidsidescrollertest;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class GameEngine extends Thread {
    private SurfaceHolder _surfaceHolder = null;
    private Panel _panel = null;
    private boolean _running = false;
    private static GameEngine _instance = null;
    
    private GameEngine() {
    }
    
    public static GameEngine getInstance() {
    	if (null == _instance) {
    		_instance = new GameEngine();
    	}
    	return _instance;
    }

	public void setPanel(Panel panel) {
        _panel = panel;
        _surfaceHolder = panel.getHolder();
	}

	public void setRunning(boolean running) {
        _running = running;
    }

    @Override
    public void start() {
        setRunning(true);
        try {
        	super.start();
    	} catch (IllegalThreadStateException e) {
    		
    	}
    }

    public SurfaceHolder getSurfaceHolder() {
        return _surfaceHolder;
    }
    
    @Override
    public void run() {
        Canvas canvas;
        while (_running) {
            canvas = null;
            try {
                canvas = _surfaceHolder.lockCanvas();
                if (null != canvas) {
                    synchronized (_surfaceHolder) {
                        updatePhysics();
                        _panel.onDraw(canvas);
                    }
                } else {
                	System.out.println("Wanted to draw, but canvas is null!");
                }
            } finally {
                if (canvas != null) {
                    _surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    
    public void updatePhysics() {
        for (AnimatedSprite animatedSprite : World.getInstance().getAnimatedSprites()) {
        	animatedSprite.tick();
        }
    }

	public Panel getPanel() {
		return _panel;
	}
}