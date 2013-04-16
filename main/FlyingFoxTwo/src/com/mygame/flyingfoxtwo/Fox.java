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
	public int jumpStartY;
	
	public int VerticalDirection;
	public int HorizontalDirection;
	
	public Fox(int x, int y){
		VerticalDirection = UP;
		HorizontalDirection = MIDDLE;
		
		GridX = x;
		GridY = y;
		
		jumpStartY = GridY;
		
		ScreenX = GridX * 32;
		ScreenY = GridY * 32;
	}
	
	public void jumpLeft() {
		// TODO Auto-generated method stub
		HorizontalDirection = LEFT;

	}
	public void jumpRight() {
		// TODO Auto-generated method stub
		HorizontalDirection = RIGHT;
	}
	
	public void advance(float accelX) {
		// TODO Auto-generated method stub
		
		
		if(HorizontalDirection == LEFT){
			GridX -= 1;
			if(GridX < 0) {
				GridX = World.WORLD_WIDTH - 1;
				ScreenX = (GridX * 32);
			}
			NewScreenX = (GridX * 32);			
		}
		if(HorizontalDirection == RIGHT){
			GridX += 1;
			if(GridX > World.WORLD_WIDTH){
				GridX = 0;
				ScreenX = (GridX * 32);
			}
			NewScreenX = (GridX * 32);			
		}
		
		if(VerticalDirection == UP)
			GridY -= 1;
		else
			GridY += 1;

		//If at the top
		if(GridY < 0) {
			GridY = 0;
//			ScreenX = GridX * 32;
//			ScreenY = GridY * 32;
			VerticalDirection = DOWN;
		}
		
		//If at the maximum height of jump then come down
		if(jumpStartY - GridY >= JUMP_HEIGHT){
			VerticalDirection = DOWN;
		}
		
		//Game over
		/*if(GridY >= 13) {
			GridY = 12; 
			VerticalDirection = UP;
			HorizontalDirection = MIDDLE;
		}//Game Over
*/		
		
		NewScreenY = (GridY * 32);
		
	}
	
	

}
