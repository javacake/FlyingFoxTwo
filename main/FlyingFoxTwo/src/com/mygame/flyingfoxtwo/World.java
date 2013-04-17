package com.mygame.flyingfoxtwo;

import java.util.Random;

import com.mygame.framework.Screen;

import android.provider.UserDictionary.Words;
import android.util.Log;


public class World {
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 30;
    static final int SCORE_INCREMENT = 10;
    static final float TICK_INITIAL = 0.2f;
    static final float TICK_DECREMENT = 0.05f;
    static final int TICK_SLICE = 4;

    public boolean gameOver = false;   
    public int score;
    float tickTime;
    float smallTick;
    static float tick; //use to increase game speed by decreasing tick
    boolean scrolling;
    int smoothTick;
    
    public int worldPosition;
    public int worldGridY;

    public Fox fox;
    boolean platform[][];
    
    public World() {
        fox = new Fox(4, WORLD_HEIGHT - 1);
        
        worldPosition = worldGridY * GameScreen.PixelUnit;
        
        score = 0;
        worldGridY = WORLD_HEIGHT - 15; //Screen height
		tickTime = 0;
		smallTick = 0;
		tick = TICK_INITIAL; //use to increase game speed by decreasing tick
		scrolling = false;
		smoothTick = TICK_SLICE;
		
        platform = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
        Random random = new Random();
        int i;
    	for(int j = 0;j < WORLD_HEIGHT;j+=3){
    		i = random.nextInt(WORLD_WIDTH - 2);
    		platform[i][j] = true;
    		platform[i+1][j] = true;
    	}
    }

	public void update(float deltaTime, float accelX) {
		// TODO Auto-generated method stub
        if (gameOver)return;

        tickTime += deltaTime;
        smallTick += deltaTime;

        while (smallTick > (tick/TICK_SLICE)) {
			smallTick -= (tick/TICK_SLICE);

			if(smoothTick >= 0){
				
				if(scrolling){
	        	
		        	worldPosition -= fox.deltaDst;
		        	
		        	if(worldPosition < worldGridY * GameScreen.PixelUnit)
		        		worldPosition = worldGridY * GameScreen.PixelUnit;
	
				}				

	            if(fox.ScreenX > fox.NewScreenX)
	            	fox.ScreenX -= 8;
	            else if(fox.ScreenX < fox.NewScreenX)
	            	fox.ScreenX += 8;

	            
	        	if(fox.ScreenY > fox.NewScreenY)
	            	fox.ScreenY -= fox.deltaDst;
	            else if(fox.ScreenY < fox.NewScreenY)
	            	fox.ScreenY += fox.deltaDst;

		        smoothTick--;
		        
			}else{
				
				fox.ScreenX = fox.GridX * GameScreen.PixelUnit;
				fox.ScreenY = fox.GridY * GameScreen.PixelUnit;
						
			}
			
        }        
        
        while (tickTime > tick) {
            tickTime -= tick;
            
	        if(fox.GridY == 0){
	    		gameOver = true;
	    		return;
	    	}
	        
	        smoothTick = TICK_SLICE;
	        
            //Fox jumping up
            if(fox.VerticalDirection == fox.UP){
            	
            	     updateScroll();
            	     
            }else{
            //fox jumping down
            	
            	//if hit at bottom then jump
            	if(fox.GridY >= WORLD_HEIGHT - 1){
            		fox.jump();
            	}else if(fox.GridY >= worldGridY + 15){ 
            		
            		//fox fall down then gameover
            		gameOver=true;
            		return;
            		
            	}else if(platform[fox.GridX][fox.GridY + 1]){ 
            		//if fox collied with platform then jump
            		//DO not check horizontal collision only vertical
            		fox.jump();
            	}

            }
            fox.advance(accelX);
        }

	}    
	
	
	private void smoothMove(){

//            if(scrolling){
//            	
//            	worldPosition -= fox.deltaDst;
//            	
//            	if(worldPosition < worldGridY * GameScreen.PixelUnit)
//            		worldPosition = worldGridY * GameScreen.PixelUnit;
//            	
//            	//Log.d("Position","fox: " + fox.ScreenY + " world: " + worldPosition);
//            }			
			
			
            //if(fox.ScreenX != fox.GridX * GameScreen.PixelUnit){
	            if(fox.ScreenX > fox.NewScreenX)
	            	fox.ScreenX -= 8;
	            else
	            	fox.ScreenX += 8;
            //}
        	
            //if(fox.ScreenY != fox.GridY * GameScreen.PixelUnit){
	            if(fox.ScreenY > fox.NewScreenY)
	            	fox.ScreenY -= fox.deltaDst;
	            else
	            	fox.ScreenY += fox.deltaDst;
            //}
	}
	
	private void updateScroll(){
		
		worldPosition = worldGridY * GameScreen.PixelUnit;
		
		//if world is at the top then no scrolling
		if(worldGridY != 0){
			
			if(fox.GridY - worldGridY <= 2)
			{
				scrolling = true;
				if(fox.jumpTick == fox.JUMP_HEIGHT)
					worldGridY -= 3;
				else
					worldGridY -= 1;
			}else
				scrolling = false;
			
		}else{
			//if world is at the top then unfreez fox to move to the top edge		
			scrolling = false;
		}
	}
	
}


/*            //Log.e("dt","dt:" + dt);            
if(fox.HorizontalDirection == fox.RIGHT)
	fox.ScreenX = (fox.GridX * GameScreen.PixelUnit) + dt;
else if(fox.HorizontalDirection == fox.LEFT)
	fox.ScreenX = (fox.GridX * GameScreen.PixelUnit) - dt;            
if(dt >= GameScreen.PixelUnit || fox.ScreenX >= (fox.GridX * GameScreen.PixelUnit)) fox.HorizontalDirection = fox.MIDDLE;
//Log.e("x","x: " + fox.ScreenX);

if(fox.VerticalDirection == fox.UP)
	fox.ScreenY = (fox.GridY * GameScreen.PixelUnit) - dt;
else
	fox.ScreenY = (fox.GridY * GameScreen.PixelUnit) + dt;

//Log.e("ScreenY", "Y: " + fox.ScreenY);
*/        

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
