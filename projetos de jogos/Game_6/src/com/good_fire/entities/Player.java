package com.good_fire.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;
import com.good_fire.world.Camera;
import com.good_fire.world.World;

public class Player extends Entity{
	
	public BufferedImage[] slime_right,slime_left;
	public BufferedImage slime_right_stop, slime_left_stop;
	
	public static double life = 100;

	public int side = 1;
	public boolean move, right, left; 
	public boolean isJumping, isJumpingEnemy, jump, jumpEnemy, jumpTime = false;
	public int jumpHeight = 48;
	public int jumpFrames = 0;
	public double jumpInter = 0;
	public double speed = 1;
	public double speedj = 2;
	
	private int frames = 0,maxFrames = 8, index = 0,maxIndex = 7;
	
	public Player(int x, int y, int width, int height, int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		slime_right = new BufferedImage[8];
		slime_left = new BufferedImage[8];	
		
		for(int i = 0; i < 8; i++) {
			slime_right[i] = Game.spritesheet.getSprite(112 + (i*16), 0, 16, 16);	
		}
		for(int i = 0; i < 8; i++) {
			slime_left[i] = Game.spritesheet.getSprite(224 - (i*16), 16, 16, 16);	
		}
	}

	public void tick() {
		depth = 2;
		move = false;	
		
		if(World.isFree((int) x, (int) (y+speedj)) && isJumping == false) {
			y+=speedj;
			
		}
		
		if(jump) {
			if(!World.isFree(this.getX(), this.getY()+1)) {
				isJumping = true;
			}else {
				jump = false;

			}
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-2)) {
				y-=2;
				jumpFrames+=2;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		
		if(right && World.isFree((int) (x+speed), this.getY())) {
			x+=speed;
			move = true;
			side = 1;
		}else if(left && World.isFree((int) (x-speed), this.getY())) {
			x-=speed;
			move = true;
			side = 2;
		}
		
		if(move) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
				
			}
		}
		
		Camera.x = Camera.clamp((int)x - Game.WIDTH / 2, 16, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y - Game.HEIGHT / 2 , 16, World.HEIGHT * 16 - Game.HEIGHT);
		
	}
	
	public void render(Graphics g) {
		
		if(move) {
			if(side == 1) {
				g.drawImage(slime_right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else {
				g.drawImage(slime_left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}else {
			if(side == 1) {
				sprite = Entity.PLAYER_SPRITE_RIGHT;
			}else {
				sprite = Entity.PLAYER_SPRITE_LEFT;
			}
			super.render(g);
		}
	}
	
}