package name.keller.jon.androidsidescrollertest;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

// TODO: Support other devices. Include other image sizes.

public class MainActivity extends Activity {
    //private static final String WORLD = "World";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart");
    }
    
    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume");
        
        // TODO Save/restore state
        // World world = World.getInstance();
        // (World)(savedInstanceState.getSerializable(WORLD));
        Panel p = new Panel(this);
        setContentView(p);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause");
    }
    
    @Override
    public void onStop() {
        super.onStop();
        System.out.println("onStop");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }
/*
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(WORLD, World.getInstance());
        System.out.println("onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }
    
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       
        System.out.println("onRestoreInstanceState");
        // Restore state members from saved instance
    }
*/
}