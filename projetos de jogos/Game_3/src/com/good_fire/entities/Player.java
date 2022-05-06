package com.good_fire.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;
import com.good_fire.world.Camera;
import com.good_fire.world.World;

public class Player extends Entity{
	
	public BufferedImage sprite_fall, sprite_up;
	
	public boolean isPressed = false;
	
	public Player(int x, int y, int width, int height, int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		sprite_fall = Game.spritesheet.getSprite(16, 0, 16, 16);
		sprite_up = Game.spritesheet.getSprite(32, 0, 16, 16);
		
	}

	public void tick() {
		depth = 2;
		if(!isPressed) {
			y+=1.5;
		}else {
			if(y > 0) {
				y-=1.5;
			}
		}
		
		if(y > Game.HEIGHT) {
			World.restartGame();
		}
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e != this) {
				if(Entity.isColidding(this, e)) {
					World.restartGame();
					return;
				}
			}
		}
		
	}
	
	public void render(Graphics g) {
		if(!isPressed) {
			g.drawImage(sprite_fall,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else {
			g.drawImage(sprite_up,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
	
}
