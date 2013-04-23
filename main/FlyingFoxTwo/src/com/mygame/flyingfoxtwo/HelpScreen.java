package com.mygame.flyingfoxtwo;

import java.util.List;
import com.mygame.framework.Graphics;
import com.mygame.framework.Input.TouchEvent;
import com.mygame.framework.Game;
import com.mygame.framework.Screen;


/**
 * The HelpScreen class inherits Screen class. It displays an image of instruction and a close button.
 * 
 */
public class HelpScreen extends Screen {

	/**
	 * Instantiates a new help screen.
	 *
	 * @param game is reference to the main activity, this reference will be used to access resources in the assets folder. 
	 */
	public HelpScreen(Game game) {
		super(game);
	}

	/* 
	 * @see com.mygame.framework.Screen#update(float)
	 */
	@Override
	public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();       
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
            	 if (event.x < 64 && event.y > 416) {
                    game.setScreen(new StartMenuScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
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
        g.drawPixmap(Assets.help, 0, 0);
        g.drawPixmap(Assets.buttons, 0, 416, 0, 128, 64, 64);

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
