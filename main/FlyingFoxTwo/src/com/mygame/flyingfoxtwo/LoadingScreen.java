package com.mygame.flyingfoxtwo;

import com.mygame.framework.Graphics;
import com.mygame.framework.Graphics.PixmapFormat;
import com.mygame.framework.Game;
import com.mygame.framework.Screen;

/**
 * The Class LoadingScreen is the first class instantiated by the main activity.
 * It first loads all the media resources then loads settings and start menu screen.
 */
public class LoadingScreen extends Screen {

	/**
	 * Creates a new loading screen.
	 *
	 * @param game the game is reference to the main activity, this reference will be used to access resources in the assets folder.  
	 */
	public LoadingScreen(Game game) {
		super(game);
	}

	/* 
	 * @see com.mygame.framework.Screen#update(float)
	 */
	@Override
	public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        
        Assets.background = g.newPixmap("startbg.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
        Assets.help = g.newPixmap("help.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
        Assets.foxL = g.newPixmap("fox.png", PixmapFormat.ARGB4444);
        Assets.foxR = g.newPixmap("foxR.png", PixmapFormat.ARGB4444);
        Assets.platform = g.newPixmap("platform.png", PixmapFormat.ARGB4444);
        Assets.cloudBack = g.newPixmap("Cloudbackground.png", PixmapFormat.ARGB4444);        
        Assets.ground = g.newPixmap("ground.png", PixmapFormat.ARGB4444);
        Assets.gameWon = g.newPixmap("gamewon.png", PixmapFormat.ARGB4444);
        Assets.coin = g.newPixmap("coin.png", PixmapFormat.ARGB4444);
        
        Assets.click = game.getAudio().newSound("click.ogg");
        Assets.fall = game.getAudio().newSound("Falling.ogg");
        Assets.Winmz = game.getAudio().newSound("win.ogg");
        
        Assets.backmz = game.getAudio().newMusic("backmz.ogg");
        Assets.backmz.setLooping(true);
        
        Settings.load(game.getFileIO());
        game.setScreen(new StartMenuScreen(game));

	}

	/* 
	 * @see com.mygame.framework.Screen#present(float)
	 */
	@Override
	public void present(float deltaTime) {
	}

	/* 
	 * @see com.mygame.framework.Screen#pause()
	 */
	@Override
	public void pause() {
	}

	/* 
	 * @see com.mygame.framework.Screen#resume()
	 */
	@Override
	public void resume() {
	}

	/* 
	 * @see com.mygame.framework.Screen#dispose()
	 */
	@Override
	public void dispose() {

	}

}
