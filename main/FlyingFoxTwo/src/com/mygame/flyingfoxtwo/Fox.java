package com.mygame.flyingfoxtwo;

import android.util.Log;


public class Fox {

    static final int UP = -1;
    static final int DOWN = 1;
    static final int LEFT = -1;
    static final int RIGHT = 1;
    static final int MIDDLE = 0;
    final int JUMP_HEIGHT = 5;
	
	public boolean isfallDown = false;
	
	public int GridX;
	public int GridY;
	public int ScreenX;
	public int ScreenY;
	public int NewScreenX;
	public int NewScreenY;
	public float ax;
	public int jumpPosition;
	public boolean freez;
	
	public int VerticalDirection;
	public int HorizontalDirection;
	
	public Fox(int x, int y){
		HorizontalDirection = MIDDLE;
		
		GridX = x;
		GridY = y;
		
		ScreenX = GridX * 32;
		ScreenY = GridY * 32;
		
		freez = false;
		
		jumpStart();
		//Log.e("fox", "X: " + ScreenX + " Y: " + ScreenY);
	}

	
	public void jumpStart(){
		VerticalDirection = UP;
		jumpPosition = JUMP_HEIGHT;
	}
	
	public void advance(float accelX) {

		//Advance grid position for continue jump
		if(VerticalDirection == UP){
			GridY -= 1;
		}
		else{
			GridY += 1;
		}
		
		updateVerticalJump();	
		
		//New screen position for fox to move
		NewScreenY = (GridY * 32);
		
	}

	private void updateVerticalJump(){
		
		//If at the maximum height of jump then come down
		
		if(jumpPosition <= 0){
			VerticalDirection = DOWN;
			freez = false;
			//Log.e("Down","---------------" + GridY);
		}else{
			jumpPosition--;
			//Log.e("JumpPosition", jumpPosition + "--------");
		}
		
		//down
		//If hit at the top then come down
		if(GridY <= 0) {
			GridY = 0;
			VerticalDirection = DOWN;
			
			//So the fox doesnt stick at the top
			jumpPosition = 0;
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
		if(jumpPosition > 0) f = true;
		else f = false;
		
		return jumpPosition > 0;
	}
	
	public void jumpLeft() {
		// TODO Auto-generated method stub
		HorizontalDirection = LEFT;
		//decreamentX
		GridX -= 1;
		if(GridX < 0) {
			GridX = World.WORLD_WIDTH - 1;
			
			//Set screen position no need to set newScreenX and transit
			ScreenX = (GridX * 32);
		}
		
		NewScreenX = (GridX * 32);
	}
	public void jumpRight() {
		// TODO Auto-generated method stub
		HorizontalDirection = RIGHT;
		//increamentX
		GridX += 1;

		if(GridX >= World.WORLD_WIDTH){
			GridX = 0;
			
			//Set screen position do not set newScreenX and transit
			ScreenX = (GridX * 32);
		}
		
		NewScreenX = (GridX * 32);
	}

	
	private void updateHorizontalJump(){

		//Horizontal move while jumping
		if(HorizontalDirection == LEFT){
			
		}
		
		if(HorizontalDirection == RIGHT){
			
		}		
	}


}
