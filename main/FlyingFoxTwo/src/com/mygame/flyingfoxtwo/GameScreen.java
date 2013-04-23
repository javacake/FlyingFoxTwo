package com.mygame.flyingfoxtwo;

import java.util.List;
import com.mygame.framework.Graphics;
import com.mygame.framework.Input.TouchEvent;
import com.mygame.framework.Game;
import com.mygame.framework.Screen;

public class GameScreen extends Screen {
    static final int PixelUnit = 32;

	enum GameState {
        Ready,
        Running,
        Paused,
        GameOver,
        GameWon
    }
    
    GameState state = GameState.Ready;
    World world;
    int oldScore = 0;
    String score = "0";
    boolean isHard = false;
    
	public GameScreen(Game game, boolean isHard) {
		super(game);
		world = new World(isHard);
		this.isHard = isHard;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        float AccelX = game.getInput().getAccelX();
        
        if(state == GameState.Ready)
            updateReady(touchEvents);
        if(state == GameState.Running)
            updateRunning(touchEvents, deltaTime, AccelX);
        if(state == GameState.Paused)
            updatePaused(touchEvents);
        if(state == GameState.GameOver)
            updateGameOver(touchEvents);
        if(state == GameState.GameWon)
            updateGameWon(touchEvents);  
	}

    
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime, float accelX) {        
        int len = touchEvents.size();
        
        if(Settings.soundEnabled){
        	if(!Assets.backmz.isPlaying())
        		Assets.backmz.play();
        }
        
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.Paused;
                    return;
                }
            }
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 64 && event.y > 416) {
                    world.fox.jumpLeft();
                }
                if(event.x > 256 && event.y > 416) {
                    world.fox.jumpRight();
                }
            }
        }
        
        world.update(deltaTime, accelX);

        if(world.gameWon) {
            state = GameState.GameWon;
        }
       
        if(world.gameOver) {
            state = GameState.GameOver;
        }
        
        if(oldScore != world.score) {
            oldScore = world.score;
            score = "" + oldScore;
//            if(Settings.soundEnabled)
//                Assets.eat.play(1);
        }
    }

	
	private void updateReady(List<TouchEvent> touchEvents) {
	    if(touchEvents.size() > 0)
	        state = GameState.Running;
	    
	    if(Settings.soundEnabled){
	    	if(!Assets.backmz.isPlaying())
	    		Assets.backmz.play();
	    }
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		
	    if(Settings.soundEnabled){
	    	if(Assets.backmz.isPlaying())
	    		Assets.backmz.stop();
	    }
		
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {
	        TouchEvent event = touchEvents.get(i);
	        if(event.type == TouchEvent.TOUCH_UP) {
	            if(event.x > 80 && event.x <= 240) {
	                if(event.y > 100 && event.y <= 148) {
	                    if(Settings.soundEnabled){
	                        Assets.click.play(1);
	                    }
	                    state = GameState.Running;
	                    return;
	                }                    
	                if(event.y > 148 && event.y < 196) {
	                    if(Settings.soundEnabled){
	                        Assets.click.play(1);
	                    }
	                    game.setScreen(new StartMenuScreen(game));                        
	                    return;
	                }
	            }
	        }
	    }
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
	    	
	/*        if(Settings.soundEnabled){
	        	if(!Assets.backmz.isPlaying())
	        		Assets.backmz.stop();
	        	//Assets.fall.play(1);
	        }*/
	    	
	        int len = touchEvents.size();
	        for(int i = 0; i < len; i++) {
	            TouchEvent event = touchEvents.get(i);
	            if(event.type == TouchEvent.TOUCH_UP) {
	                if(event.x >= 128 && event.x <= 192 &&
	                   event.y >= 200 && event.y <= 264) {
	                    if(Settings.soundEnabled){
	                        Assets.click.play(1);
	                    }
	                    game.setScreen(new StartMenuScreen(game));
	                    return;
	                }
	            }
	        }
	    }

	private void updateGameWon(List<TouchEvent> touchEvents) {
	    	
	//        if(Settings.soundEnabled){
	//        	if(!Assets.backmz.isPlaying())
	//        		Assets.backmz.stop();
	//        	//Assets.Winmz.play(1);
	//        }    	
	    	
	        int len = touchEvents.size();
	        for(int i = 0; i < len; i++) {
	            TouchEvent event = touchEvents.get(i);
	            if(event.type == TouchEvent.TOUCH_UP) {
	                if(event.x >= 128 && event.x <= 192 &&
	                   event.y >= 200 && event.y <= 264) {
	                    if(Settings.soundEnabled)
	                        Assets.click.play(1);
	                    
	                    game.setScreen(new StartMenuScreen(game));
	                    return;
	                }
	            }
	        }
	    }

	@Override
	public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        
        drawWorld(world, deltaTime);
        
        if(state == GameState.Ready) 
            drawReadyUI();
        if(state == GameState.Running){
        	drawRunningUI();
        }
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.GameOver){
            drawGameOverUI();
        }
        if(state == GameState.GameWon){
        	drawGameWonUI();
        }
        drawText(g, score, g.getWidth() / 2 - score.length()*20 / 2, g.getHeight() - 42);   
	}
	
	
	private void drawReadyUI() {
	    Graphics g = game.getGraphics();
	    
	    g.drawPixmap(Assets.ready, 47, 100);
	}

	private void drawRunningUI() {
	    Graphics g = game.getGraphics();
	    
	    g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
	    g.drawPixmap(Assets.buttons, 256, 416, 64, 64, 64, 64);
	    g.drawPixmap(Assets.buttons, 0, 416, 0, 64, 64, 64);
	}

	private void drawPausedUI() {
	    Graphics g = game.getGraphics();
	    
	    g.drawPixmap(Assets.pause, 80, 100);
	}

	private void drawGameOverUI() {
	    Graphics g = game.getGraphics();
	    
	    g.drawPixmap(Assets.gameOver, 62, 100);
	    g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
	}

	private void drawGameWonUI() {
	    Graphics g = game.getGraphics();
	    
	    g.drawPixmap(Assets.gameWon, 62, 100);
	    g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
	}

	private void drawWorld(World world, float deltaTime) {
			// TODO drawWorld
	        Graphics g = game.getGraphics();
	    	g.drawPixmap(Assets.cloudBack, 0, 0);
	
	        //Draw with the variables in smoothMove methods for smooth movement
	
	    	//ground background
	        for(int i = 0;i < World.WORLD_WIDTH;i++){
	        	g.drawPixmap(Assets.ground, i * PixelUnit, (World.WORLD_HEIGHT - 1) * PixelUnit - world.worldPosition );
	        }
	    	
	        //platforms
	        int visibleY = world.worldGridY + World.VISIBLE_HEIGHT;
	        
	        Fox fox = world.fox;
	        
	//        if(world.platform[fox.GridX][fox.GridY] == 2){
	//        	world.platform[fox.GridX][fox.GridY] = 0;
	//        	score += 15;
	//    	}     
	
	    	for(int x = 3;x < 7;x++){
	    		if(world.platform[x][3] == 1){
	    			g.drawPixmap(Assets.platform, x * PixelUnit,3 * PixelUnit - world.worldPosition );
	    		}    	
	    	}
	        
	        for(int x = 0;x < World.WORLD_WIDTH;x++){
	        	int increament = isHard?4:3;
	        	
	        	for(int y = 6;y < World.WORLD_HEIGHT;y+=increament){
	        		
	        		if(world.platform[x][y] == 1){
	        			g.drawPixmap(Assets.platform, x * PixelUnit, y * PixelUnit - world.worldPosition );
	        		}
	        		
	        		if((y-5 > 0)){	
	        			if(world.platform[x][y-5] == 2)
	        				g.drawPixmap(Assets.coin, x * PixelUnit, (y-5) * PixelUnit - world.worldPosition );
	        		}
	        	}
	        }
	
	        if(fox.FaceLeft)
	        	g.drawPixmap(Assets.foxL, fox.ScreenX, fox.ScreenY - world.worldPosition);
	        else
	        	g.drawPixmap(Assets.foxR, fox.ScreenX, fox.ScreenY - world.worldPosition);
	        	
		}

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

	@Override
	public void pause() {
        if(state == GameState.Running)
            state = GameState.Paused;
		
        if(world.gameOver || world.gameWon) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {

	}

}
