package com.mygame.flyingfoxtwo;

import java.util.Random;

import android.util.Log;


public class World {
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;
    static final int SCORE_INCREMENT = 10;
    
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;

    public Fox fox;
    public boolean gameOver = false;;
    public int score = 0;

    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    float smallTick = 0;
    static float tick = TICK_INITIAL;

    public World() {
        fox = new Fox(4, 12);
    }

	public void update(float deltaTime) {
		// TODO Auto-generated method stub
        if (gameOver)
            return;

        tickTime += deltaTime;
        smallTick += deltaTime;
        
        //Log.d("world update", "t: " + tick + " tT: " + tickTime + " dT: " + deltaTime);

        
        while (smallTick > (tick/4)) {
        	smallTick -= (tick/4);

            //Log.e("small", "sT: " + tickTime);
            int dt = (int) (64 * tickTime);
            if(dt > 32) dt = 32;

            if(fox.ScreenX != fox.GridX * 32){
	            if(fox.ScreenX > fox.NewScreenX)
	            	fox.ScreenX -= 8;
	            else
	            	fox.ScreenX += 8;
            }
        	//Log.e("x","x: " + fox.ScreenX);
        	
            if(fox.ScreenY != fox.GridY * 32){
	            if(fox.ScreenY > fox.NewScreenY)
	            	fox.ScreenY -= 8;
	            else
	            	fox.ScreenY +=8;
            }
            
/*            //Log.e("dt","dt:" + dt);            
            if(fox.HorizontalDirection == fox.RIGHT)
            	fox.ScreenX = (fox.GridX * 32) + dt;
            else if(fox.HorizontalDirection == fox.LEFT)
            	fox.ScreenX = (fox.GridX * 32) - dt;            
        	if(dt >= 32 || fox.ScreenX >= (fox.GridX * 32)) fox.HorizontalDirection = fox.MIDDLE;
        	//Log.e("x","x: " + fox.ScreenX);
        	
            if(fox.VerticalDirection == fox.UP)
            	fox.ScreenY = (fox.GridY * 32) - dt;
            else
            	fox.ScreenY = (fox.GridY * 32) + dt;
            
            //Log.e("ScreenY", "Y: " + fox.ScreenY);
*/        
       }        
        
        while (tickTime > tick) {
            tickTime -= tick;

            //Log.w("Tick", "tT: " + tickTime);            
            
            fox.advance();
            
            if (fox.isfallDown) {
                gameOver = true;
                return;
            }
        }
        
	}    
}

/*            SnakePart head = snake.parts.get(0);
if (head.x == stain.x && head.y == stain.y) {
    score += SCORE_INCREMENT;
    snake.eat();
    if (snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
        gameOver = true;
        return;
    } else {
        placeStain();
    }

    if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
    	//increase time
        tick -= TICK_DECREMENT;
    }
}*/
