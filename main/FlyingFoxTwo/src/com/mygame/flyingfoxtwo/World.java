package com.mygame.flyingfoxtwo;

import java.util.Random;

/**
 * The world class is responsible for limiting the boundary of the game by specifying WIDTH and HEIGHT.
 * It is a virtual grid of cells where fox and other game elements are laid out.
 * The world class will hold platform position, coin position and will scroll down as fox moves up.
 *  
 */
public class World {
    
    /** Specifies how many cell wide the game grid is. */
    static final int WORLD_WIDTH = 10;
    
    /** Specifies how many cell tall the game grid is. */
    static final int WORLD_HEIGHT = 30;
    
    /** Number of vertical visible cells on the screen. */
    static final int VISIBLE_HEIGHT = 15;
    
    /** Value of how much score will be incremented each time.*/
    static final int SCORE_INCREMENT = 10;
    
    /** The next grid frame time. */
    static final float TICK_INITIAL = 0.2f;
    
    /** The Constant TICK_DECREMENT to increase speed of the game*/
    static final float TICK_DECREMENT = 0.05f;
    
    /** The number of small game frame of the total TICK_INITIAL time. */
    static final int TICK_SLICE = 4;

    /** The game over flag. */
    public boolean gameOver = false;   
    
    /** The game won flag. */
    public boolean gameWon = false;
    
    /** The maximum fox position vertically to be used to calculate score. */
    int scoregrid;
    
    /** The score of the user */
    public int score;
    
    /** A count down variable for game frame timespan. */
    float tickTime;
    
    /** The small division of the timespan for smooth movement of the fox. */
    float smallTick;
    
    /** Use to increase game speed by decreasing tick. */
    static float tick; 
    
    /** Flat to indicate if world is scrolling or not */
    boolean scrolling;
    
    /** Count down variable to indicate the small frame  . */
    int smoothTick;
    
    /** Flag indicates if game is in hard mode. */
    boolean isHard;
    
    /** The world screen position in pixel. */
    public int worldPosition;
    
    /** The world grid position displayed on the screen. */
    public int worldGridY;

    /** The fox object. */
    public Fox fox;
    
    /** The platform and coin array. */
    int platform[][];
    
    /**
     * Instantiates a new world with difficulty flag. if isHard is false game will be in easy mode.
     *
     * @param isHard the is hard
     */
    public World(boolean isHard) {
    	this.isHard = isHard;
        fox = new Fox(4, WORLD_HEIGHT - 1);
        

        
        score = 0;
        scoregrid = 6;
        worldGridY = WORLD_HEIGHT - VISIBLE_HEIGHT; //Screen height
        worldPosition = worldGridY * GameScreen.PixelUnit;        
        
		tickTime = 0;
		smallTick = 0;
		tick = TICK_INITIAL; //use to increase game speed by decreasing tick
		scrolling = false;
		smoothTick = TICK_SLICE;
		
        platform = new int[WORLD_WIDTH][WORLD_HEIGHT];
        Random random = new Random();
        int x;
        int increament = isHard?4:3;
        
/*    	for(int j = 0;j < WORLD_HEIGHT;j+=(increament+3)){
    		if(random.nextInt(5)>=3){	
    			i = random.nextInt(WORLD_WIDTH - 2);
    			platform[i][j] = 2;
    		}
    	}   */     
        
        //The final platform
		platform[3][3] = 1;
		platform[4][3] = 1;
		platform[5][3] = 1;
		platform[6][3] = 1;
        
		//Start after three positions below final platform
    	for(int y = 6;y < WORLD_HEIGHT;y+=increament){
    		//Draw platform near to each other minus three
    		
    		x = random.nextInt(WORLD_WIDTH - 3);
    		platform[x+1][y] = 1;
    		platform[x+2][y] = 1;

    		//place random coin above five cells of the platform 
    		if((y-5 > 0) && random.nextInt(5)>=3){	
    			x = random.nextInt(WORLD_WIDTH - 2);
    			if(platform[x][y-5] != 1)
    			platform[x][y-5] = 2;
    		}    	

    	}

    	updateScroll();
    }

	/**
	 * This method will be called at each tick to update values of the game. 
	 * The update method a two while loops starts with small and big time spans.
	 * 
	 * the loop with bigger time span will update position of fox and other elements.
	 * Also collision detection between fox and platform will be done in grid during the bigger tick.
	 * Because it is easy calculate few numbers.
	 *  
	 * the loop with smaller time span will occure more than once between each bigger time loop start.
	 * It will move fox image from one grid cell to the next one with little distance and gives smooth movement.
	 * 
	 *
	 * @param deltaTime the small timespan
	 * @param accelX accelerometer value of X-axis
	 */
	public void update(float deltaTime, float accelX) {

        if (gameOver)return;
        
        smallTick += deltaTime;
        tickTime += deltaTime;
        
        //Small timespan loop
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
        
        //Bigger timespan loop
        while (tickTime > tick) {
            tickTime -= tick;
            if(fox.GridY < WORLD_HEIGHT - 7 ){
            	if(scoregrid < WORLD_HEIGHT - fox.GridY){
            		scoregrid++;
            		score += 20;
            	}
            }
	        if(fox.GridY == 0){
	        	gameWon = true;
	    		//gameOver = true;
	        	if(Settings.soundEnabled){
	        		if(Assets.backmz.isPlaying())
	            		Assets.backmz.stop();
	        		Assets.Winmz.play(1);
	        	}
	    		return;
	    	}        
	        smoothTick = TICK_SLICE;
	        
            //Fox jumping up
            if(fox.VerticalDirection == fox.DOWN){
            	
            	     //updateScroll();
            	     
            //}else{
            //fox jumping down
            	
            	//if hit at bottom then jump
            	if(fox.GridY >= WORLD_HEIGHT - 1){
            		fox.jump();
            	}else if(fox.GridY >= worldGridY + 15){ 
            		
            		//fox fall down then gameover
            		gameOver=true;
    	        	if(Settings.soundEnabled){
    	        		if(Assets.backmz.isPlaying())
    	            		Assets.backmz.stop();
    	        		Assets.fall.play(1);
    	        	}
            		return;
            		
            	}else if(platform[fox.GridX][fox.GridY + 1] == 1){ 
            		//if fox collied with platform then jump
            		//DO not check horizontal collision only vertical
            		fox.jump();
            	}
            }
            
            fox.advance(accelX);
            updateScroll();
            
            //check if collied with coin, update score and remove coin
            if(platform[fox.GridX][fox.GridY] == 2){
            	platform[fox.GridX][fox.GridY] = 0;
            	score += 15;
        	}
        }
	}    
	

	/**
	 * This method will check fox position on the screen and scroll the world if necessary.
	 * 
	 */
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
