package com.mygame.flyingfoxtwo;

import com.mygame.framework.Graphics;
import com.mygame.framework.Graphics.PixmapFormat;
import com.mygame.framework.Game;
import com.mygame.framework.Screen;

public class LoadingScreen extends Screen {

	public LoadingScreen(Game game) {
		super(game);
	}

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
        
        Assets.click = game.getAudio().newSound("click.ogg");
        Assets.fall = game.getAudio().newSound("fall.ogg");
        
        Settings.load(game.getFileIO());
        game.setScreen(new StartMenuScreen(game));

	}

	@Override
	public void present(float deltaTime) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
