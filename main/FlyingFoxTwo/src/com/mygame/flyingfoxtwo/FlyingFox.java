package com.mygame.flyingfoxtwo;

import com.mygame.framework.Screen;
import com.mygame.framework.impl.AndroidGame;
import com.mygame.flyingfoxtwo.LoadingScreen;

public class FlyingFox extends AndroidGame {

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
	
	@Override
	protected void onDestroy() {
		Assets.backmz.dispose();
		Assets.click.dispose();
		Assets.Winmz.dispose();
		Assets.fall.dispose();
		
		super.onDestroy();				
	}
	
	@Override
	public void onPause() {
        if(Assets.backmz != null)
        	Assets.backmz.stop();
		super.onPause();        
	}


}
