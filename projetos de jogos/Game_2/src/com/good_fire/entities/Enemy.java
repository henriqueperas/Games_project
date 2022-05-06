package com.good_fire.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.good_fire.main.Game;
import com.good_fire.world.AStar;
import com.good_fire.world.Camera;
import com.good_fire.world.Vector2i;

public class Enemy extends Entity{
	
	public BufferedImage sprite_ghost;
	
	public boolean ghostMode = false;
	public int ghostFrames = 59;
	public int i = 1;
	public static int j = 1;

	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		sprite_ghost = Game.spritesheet.getSprite(176, 80, 16, 16);

	}

	public void tick(){
			
		if(ghostMode == false) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i((int)(x/16), (int)(y/16));
				Vector2i end = new Vector2i((int)(Game.player.x/16), (int)(Game.player.y/16));
				path = AStar.findPath(Game.world, start, end);
				}
				
			if(new Random().nextInt(100) < 95)
						followPath(path);	
				
			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) < 5) {
					Vector2i start = new Vector2i((int)(x/16), (int)(y/16));
					Vector2i end = new Vector2i((int)(Game.player.x/16), (int)(Game.player.y/16));
					path = AStar.findPath(Game.world, start, end);
				}
			}
		}
		
		ghostFrames++;
		if(Game.bala_atual >= i) {
			if(ghostFrames >= 60*4) {
				ghostFrames = 0;
				if(ghostMode == false) {
					ghostMode = true;
					System.out.println("oi");
				}else {
					ghostMode = false;
					i = i + 1;
					j = j + 1;
				}
			}
		}
		
	}
	
	public void render(Graphics g) {
		//g.drawImage(sprite_right,this.getX() - Camera.x,this.getY() - Camera.y,null);
		super.render(g);
		if(Game.bala_atual >= i) {
			g.drawImage(sprite_ghost,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		//super.render(g);
	} 
}
	

