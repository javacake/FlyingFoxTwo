package com.mygame.flyingfoxtwo;

import java.util.Random;

import android.provider.UserDictionary.Words;
import android.util.Log;


public class World {
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 23;
    static final int SCORE_INCREMENT = 10;
    
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;
    
    public Fox fox;
    public boolean gameOver = false;;
    public int score = 0;
    public int worldPosition;
    public int worldGridY = 10;

    boolean platform[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    float smallTick = 0;
    static float tick = TICK_INITIAL;

    public World() {
        fox = new Fox(4, WORLD_HEIGHT - 1);
        
        worldPosition = worldGridY * 32;
    	
        int i;
    	for(int j = 0;j < WORLD_HEIGHT;j+=3){
    		i = random.nextInt(WORLD_WIDTH - 2);
    		platform[i][j] = true;
    		platform[i+1][j] = true;
    	}
    }

	public void update(float deltaTime, float accelX) {
		// TODO Auto-generated method stub
        if (gameOver)
            return;

        tickTime += deltaTime;
        smallTick += deltaTime;
                
        while (smallTick > (tick/4)) {
        	smallTick -= (tick/4);

            //Log.e("small", "sT: " + tickTime);
//            int dt = (int) (64 * tickTime);
//            if(dt > 32) dt = 32;

            if(fox.ScreenX != fox.GridX * 32){
	            if(fox.ScreenX > fox.NewScreenX)
	            	fox.ScreenX -= 8;
	            else
	            	fox.ScreenX += 8;
            }
        	
            if(fox.ScreenY != fox.GridY * 32){
	            if(fox.ScreenY > fox.NewScreenY)
	            	fox.ScreenY -= 8;
	            else
	            	fox.ScreenY +=8;
            }
            
            if(fox.freez){
            	worldPosition -= 8;
            	if(worldPosition < worldGridY * 32)
            		worldPosition = worldGridY * 32;
            	
            	Log.d("Position","fox: " + fox.ScreenY + " world: " + worldPosition);
            }
       }        
        
        while (tickTime > tick) {
            tickTime -= tick;

            fox.advance(accelX);
            
            updateScroll();
            
            try{
	            if(fox.VerticalDirection == fox.DOWN){
	            	if(fox.GridY >= WORLD_HEIGHT - 1){
	            		//gameOver = true;
	            		fox.jumpStart();
	            		//Log.d("UP", "At bottom");
	                	//return;
	            	}else if(platform[fox.GridX][fox.GridY + 1]){ //if fox collied with platform then
	            		fox.jumpStart();
	            		//Log.d("UP", "Collied");
	            	}
	            }
            }catch(ArrayIndexOutOfBoundsException ex){
            	Log.d("Array Error", "X: " + fox.GridX + "Y: " + fox.GridY);
            	gameOver = true;
            	return;
            }
        }
	}    
	
	private void updateScroll(){
		
		worldPosition = worldGridY * 32;

		//Log.d("fxposition","fgrid--- " + (fox.GridY - worldGridY));
		//Log.d("Jmposition","---fjump: " + fox.jumpPosition);
		
		if(worldGridY != 0){	
			//if(fox.GridY - worldGridY <= 2 && fox.isJumping())
			if(fox.GridY - worldGridY <= 2)
			{
				fox.freez = true;
				worldGridY -= 1;
				//Log.w("frezz", "Start---------------------");
			}
		}else{
			fox.freez = false;
		}
	}
	
	private void checkGameOver(){
		//if fox outside screen
		
		//if fox hit at the top
		
		
	}
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
