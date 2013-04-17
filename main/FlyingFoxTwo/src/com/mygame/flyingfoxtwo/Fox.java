package com.mygame.flyingfoxtwo;


public class Fox {

    final int UP = -1;
    final int DOWN = 1;
    final int LEFT = -1;
    final int RIGHT = 1;
    final int NONE = 0;
    
    final int JUMP_HEIGHT = 3;
	
	public int GridX;
	public int GridY;
	
	public int ScreenX;
	public int ScreenY;
	public int NewScreenX;
	public int NewScreenY;
	
	public int jumpTick;
	public int deltaDst;
	
	public int VerticalDirection;
	public int HorizontalDirection;
	
	public Fox(int x, int y){
		HorizontalDirection = NONE;
		
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

	
	public void jump(){
		VerticalDirection = UP;
		jumpTick = JUMP_HEIGHT;
	}
	
	public void advance(float accelX) {

		updateHorizontalMove();

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

	
	public void jumpLeft() {
		HorizontalDirection = LEFT;
	}
	public void jumpRight() {
		HorizontalDirection = RIGHT;
	}
	
	private void updateHorizontalMove(){
		if(HorizontalDirection == LEFT){
			//decreamentX
			GridX -= 1;
			if(GridX < 0) {
				GridX = World.WORLD_WIDTH - 1;
				
				//Set screen position no need to set newScreenX and transit
				ScreenX = (GridX * GameScreen.PixelUnit);
			}
			
		}else if(HorizontalDirection == RIGHT){
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
			//TODO Game winning condition
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