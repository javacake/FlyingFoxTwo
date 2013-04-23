package com.mygame.flyingfoxtwo;

/**
 * The fox is the main moving object in the game.
 * Fox continuously jumps during the game and moves left or right based on accelerometer events.
 * 
 */
public class Fox {

    /** indicates up moving direction  */
    final int UP = -1;
    
    /** Indicates down moving direction */
    final int DOWN = 1;
    
    /** Indicates left moving direction. */
    final int LEFT = -1;
    
    /** Indicates right moving direction. */
    final int RIGHT = 1;
    
    /** Sets moving direction to none. */
    final int NONE = 0;
    
    /** Jump height is a number of grid cells specified in World object.*/
    final int JUMP_HEIGHT = 3;
    
	
	/** Left Position in the World grid */
	public int GridX;
	
	/** Top position in the World grid. */
	public int GridY;
	
	/** Left position on the device Screen in pixel. */
	public int ScreenX;
	
	/** Top position on the device Screen in pixel. */
	public int ScreenY;
	
	/** Next left screen position to move. */
	public int NewScreenX;
	
	/** Next top screen position to move. */
	public int NewScreenY;
	
	/** Holds jump height count down*/
	public int jumpTick;
	
	/** Distance in pixel to move with each small update */
	public int deltaDst;
	
	/** The Vertical moving direction. */
	public int VerticalDirection;
	
	/** The Horizontal moving direction. */
	public int HorizontalDirection;
	
	/** Flag to draw correct fox image based on horizontal moving direction. */
	public boolean FaceLeft;
	
	/**
	 * Creates a new fox and sets its initial position x and y in the world grid.
	 * Fox will be in the jump mode the time it initialises. 
	 * 
	 * @param x the left position in the world grid.
	 * @param y the top position in the world grid.
	 */
	public Fox(int x, int y){
		HorizontalDirection = NONE;
		FaceLeft = true;
		
		GridX = x;
		GridY = y;
		
		ScreenX = GridX * GameScreen.PixelUnit;
		ScreenY = GridY * GameScreen.PixelUnit;
		NewScreenX = ScreenX;
		NewScreenY = ScreenY;
		
		deltaDst = GameScreen.PixelUnit / World.TICK_SLICE;
		
		jump();
		//Log.e("fox", "X: " + ScreenX + " Y: " + ScreenY);
	}

	
	/**
	 * It initialises new jump by setting UP to VerticalDirection variable and resetting jumpTick jump count down variable.
	 */
	public void jump(){
		VerticalDirection = UP;
		jumpTick = JUMP_HEIGHT;
	}
	
	/**
	 * This method will move fox to next grid position based on Horizontal and Vertical moving direction.
	 * It will also calculate new screen position in pixel to draw fox image on the screen. 
	 *
	 * @param accelX, Accelerometer value of X axis use to change Horizontal moving direction.
	 */
	public void advance(float accelX) {
		
		//First set horizontal direction based on accelerometer value then call updateHorizontalMove method.
		if(accelX >= 1)
			HorizontalDirection = LEFT;
		else if(accelX <= -1)
			HorizontalDirection = RIGHT;

		updateHorizontalMove();

		//Pixel to move on each small tick to the next grid. 
		deltaDst = GameScreen.PixelUnit / World.TICK_SLICE;
		
		//Advance grid position for continue jump
		if(VerticalDirection == UP){
			
			if(jumpTick == JUMP_HEIGHT){
				GridY -= 3;
				deltaDst = GameScreen.PixelUnit * 3 / World.TICK_SLICE;
			}
			else if(jumpTick > 0){
				GridY -= 1;
			}
			
			jumpTick-=1;
			
			if(jumpTick<=0) VerticalDirection = DOWN;
			
		}
		else{
			GridY += 1;
		}
		
		//New screen position for fox to move
		NewScreenX = (GridX * GameScreen.PixelUnit);
		NewScreenY = (GridY * GameScreen.PixelUnit);
		
	}

	
	/**
	 * This method will be called if user touches screen button to change the direction.
	 */
	public void jumpLeft() {
		HorizontalDirection = LEFT;
	}
	
	/**
	 * This method will be called if user touches screen button to change the direction.
	 */
	public void jumpRight() {
		HorizontalDirection = RIGHT;
	}
	
	/**
	 * Advance horizontal moving position based on direction.
	 * it needs to reset the direction for the next game loop tick so if user is not pressing any button or 
	 * phone is not tilted the fox will jump on the current position and will not move horizontally.
	 */
	private void updateHorizontalMove(){
		if(HorizontalDirection == LEFT){
			FaceLeft = true;
			
			//decreamentX
			GridX -= 1;
			if(GridX < 0) {
				GridX = World.WORLD_WIDTH - 1;
				
				//Set screen position no need to set newScreenX and transit
				ScreenX = (GridX * GameScreen.PixelUnit);
			}
			
		}else if(HorizontalDirection == RIGHT){
			FaceLeft = false;
			
			//increamentX
			GridX += 1;

			if(GridX >= World.WORLD_WIDTH){
				GridX = 0;
				
				//Set screen position do not set newScreenX and transit
				ScreenX = (GridX * GameScreen.PixelUnit);
			}			
		}
		
		
		HorizontalDirection = NONE;
	}

}



/*	private void updateJump(){
		
		//If at the maximum height of jump then come down
		
		if(jumpTick <= 0){
			VerticalDirection = DOWN;
			//freez = false;
			//Log.e("Down","---------------" + GridY);
		}else{
			jumpTick--;
			//Log.e("JumpPosition", jumpPosition + "--------");
		}
		
		//down
		//If hit at the top then come down
		if(GridY <= 0) {
			GridY = 0;
			VerticalDirection = DOWN;
			
			//So the fox doesnt stick at the top
			jumpTick = 0;
			//freez = false;
			
			//***************************
			//***************************
			//TO DO Game winning condition
			//***************************
			//***************************
		}	
	}
	
	public boolean isJumping(){
		
		boolean f;
		if(jumpTick > 0) f = true;
		else f = false;
		
		return jumpTick > 0;
	}*/