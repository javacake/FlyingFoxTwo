package com.mygame.flyingfoxtwo;

import com.mygame.framework.Screen;
import com.mygame.framework.impl.AndroidGame;
import com.mygame.flyingfoxtwo.LoadingScreen;

/**
 * The Class FlyingFox is set as a main activity class of the game. 
 * It overrides getStartScreen, Ondestroy and OnPause methods of super class.
 * This class will create new LoadingScreen instance.
 * 
 */
public class FlyingFox extends AndroidGame {

	/* 
	 * This method will create new LoadingScreen instance.
	 * 
	 * @see com.mygame.framework.Game#getStartScreen()
	 */
	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
	
	/* 
	 * It will dispose any sound resources to stop playing after user exits from the game. 
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		Assets.backmz.dispose();
		Assets.click.dispose();
		Assets.Winmz.dispose();
		Assets.fall.dispose();
		
		super.onDestroy();				
	}
	
	/* 
	 * This method will stop background music If application state is Pause. 
	 * 
	 * @see com.mygame.framework.impl.AndroidGame#onPause()
	 */
	@Override
	public void onPause() {
        if(Assets.backmz != null)
        	Assets.backmz.stop();
		super.onPause();        
	}


}
