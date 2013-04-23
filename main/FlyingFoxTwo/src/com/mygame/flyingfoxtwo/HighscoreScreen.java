package com.mygame.flyingfoxtwo;

import java.util.List;
import com.mygame.framework.Input.TouchEvent;
import com.mygame.framework.Graphics;
import com.mygame.framework.Game;
import com.mygame.framework.Screen;

/**
 * The Class HighscoreScreen creates a screen to display last five high scores.
 */
public class HighscoreScreen extends Screen {
    
    /** An string array to hold last five highscores from the settings*/
    String lines[] = new String[5];
    
	/**
	 * Creates a new HighscoreScreen object.
	 *
	 * @param game the game is reference to the main activity, this reference will be used to access resources in the assets folder. 
	 */
	public HighscoreScreen(Game game) {
		super(game);
		
        for (int i = 0; i < 5; i++) {
            lines[i] = "" + (i + 1) + ". " + Settings.highscores[i];
        }
	}

	/* 
	 * @see com.mygame.framework.Screen#update(float)
	 */
	@Override
	public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y > 416) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new StartMenuScreen(game));
                    return;
                }
            }
        }
	}

	/* 
	 * @see com.mygame.framework.Screen#present(float)
	 */
	@Override
	public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.mainMenu, 64, 20, 0, 84, 196, 42);

        int y = 100;
        for (int i = 0; i < 5; i++) {
            drawText(g, lines[i], 20, y);
            y += 50;
        }

        g.drawPixmap(Assets.buttons, 0, 416, 0, 128, 64, 64);
	}
	
    /**
     * This method will write numbers passed as a string at the specified position by x and y in pixel.
     *
     * @param g is reference to the Graphics context.
     * @param line is a number to draw on the screen
     * @param x is a left position of the text to draw.
     * @param y is a top position of the text to draw.
     */
    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
    
            if (character == ' ') {
                x += 20;
                continue;
            }
    
            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }
    
            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
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
