package com.mygame.flyingfoxtwo;

import java.util.List;

import com.mygame.framework.Graphics;
import com.mygame.framework.Input.TouchEvent;
import com.mygame.framework.Game;
import com.mygame.framework.Screen;

/**
 * The StartMenuScreen Class creates a screen to display the main start menu and entry point of the game.
 * It provides links to play game, view highscores and change sound setting.
 */
public class StartMenuScreen extends Screen {

	/**
	 * 
	 * Creates a StartMenuScreen and passes the game reference to the Screen class.
	 *
	 * @param game the game is reference to the main activity, this reference will be used to access resources in the assets folder. 
	 */
	public StartMenuScreen(Game game) {
		super(game);
	}

	/* 
	 * This method polls touch events and advances the game based on events.
	 * 
	 * @see com.mygame.framework.Screen#update(float)
	 */
	@Override
	public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(inBounds(event, 256, g.getHeight() - 64, 64, 64)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                
                if(inBounds(event, 64, 220, 192, 42) ) {
                    game.setScreen(new GameScreen(game,false));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                
                if(inBounds(event, 64, 220 + 42, 192, 42) ) {
                    game.setScreen(new GameScreen(game,true));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                
                if(inBounds(event, 64, 220 + 84, 192, 42) ) {
                    game.setScreen(new HighscoreScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                
                if(inBounds(event, 64, 220 + 126, 192, 42) ) {
                    game.setScreen(new HelpScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                
            }
        }
	}

    /**
     * This method will check if the touch or tap event touched any of the screen link items.
     * 
     *
     * @param event the TouchEvent object
     * @param x the left position of the screen link item.
     * @param y the top position of the screen link item.
     * @param width the width of the screen link item.
     * @param height the height of the screen link item.
     * @return true, if TouchEvent occurred for a link item. 
     */
    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
	    if(event.x > x && event.x < x + width - 1 && 
	       event.y > y && event.y < y + height - 1) 
	        return true;
	    else
	        return false;
	}

	/* 
	 * Draw a screen with menu link items and logo. 
	 * 
	 * @see com.mygame.framework.Screen#present(float)
	 */
	@Override
	public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.logo, 32, 20);
        g.drawPixmap(Assets.mainMenu, 64, 220);
        
        if(Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, 256, 416, 0, 0, 64, 64);
        else
            g.drawPixmap(Assets.buttons, 256, 416, 64, 0, 64, 64);
	}

	/*
	 * Game will write current sound and highscore variables to phone storage if game is in pause state.
	 * 
	 * @see com.mygame.framework.Screen#pause()
	 */
	@Override
	public void pause() {
		Settings.save(game.getFileIO());
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
