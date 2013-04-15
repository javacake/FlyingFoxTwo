package com.mygame.flyingfoxtwo;

import com.mygame.framework.Screen;
import com.mygame.framework.impl.AndroidGame;
import com.mygame.flyingfoxtwo.LoadingScreen;

public class FlyingFox extends AndroidGame {

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}


}
