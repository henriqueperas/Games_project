package com.good_fire.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;
import com.good_fire.world.Camera;
import com.good_fire.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	
	public BufferedImage[] sprite_right, sprite_left, sprite_up, sprite_down;
	public BufferedImage[] sprite_right_puto, sprite_left_puto, sprite_up_puto, sprite_down_puto;
	
	private int frames = 0,maxFrames = 4, index = 0,maxIndex = 5;
	private boolean moved = false;
	public int lastDir = 1;
	
	public Player(int x, int y, int width, int height, int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		//sprite_left = Game.spritesheet.getSprite(48, 16, 16, 16);
		//sprite_up = Game.spritesheet.getSprite(48, 32, 16, 16);
		//sprite_down = Game.spritesheet.getSprite(48, 48, 16, 16);
		
		sprite_left = new BufferedImage[6];
		sprite_up = new BufferedImage[6];
		sprite_down = new BufferedImage[6];
		sprite_right_puto = new BufferedImage[6];
		sprite_left_puto = new BufferedImage[6];
		sprite_up_puto = new BufferedImage[6];
		sprite_down_puto = new BufferedImage[6];
		
		for(int i = 0; i < 6; i++) {
			sprite_right[i] = Game.spritesheet.getSprite(48+ (i*16), 0, 16, 16);	
		}
		for(int i = 0; i < 6; i++) {
			sprite_left[i] = Game.spritesheet.getSprite(48+ (i*16), 16, 16, 16);	
		}
		for(int i = 0; i < 6; i++) {
			sprite_up[i] = Game.spritesheet.getSprite(48+ (i*16), 32, 16, 16);	
		}
		for(int i = 0; i < 6; i++) {
			sprite_down[i] = Game.spritesheet.getSprite(48+ (i*16), 48, 16, 16);	
		}
		for(int i = 0; i < 6; i++) {
			sprite_right_puto[i] = Game.spritesheet.getSprite(48+ (i*16), 64, 16, 16);	
		}
		for(int i = 0; i < 6; i++) {
			sprite_left_puto[i] = Game.spritesheet.getSprite(48+ (i*16), 80, 16, 16);	
		}
		for(int i = 0; i < 6; i++) {
			sprite_up_puto[i] = Game.spritesheet.getSprite(48+ (i*16), 96, 16, 16);	
		}
		for(int i = 0; i < 6; i++) {
			sprite_down_puto[i] = Game.spritesheet.getSprite(48+ (i*16), 112, 16, 16);	
		}

	}

	public void tick() {
		
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY()/*,zplayer*/)) {
			moved = true;
			x+=speed;
			lastDir = 1;
		}else if (left && World.isFree((int)(x-speed),this.getY()/*,zplayer*/)) {
			moved = true;
			x-=speed;
			lastDir = -1;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed)/*,zplayer*/)) {
			moved = true;
			y-=speed;
			lastDir = 2;
		}else if (down && World.isFree(this.getX(),(int)(y+speed)/*,zplayer*/)) {
			moved = true;
			y+=speed;
			lastDir = -2;
		}
		
		if(moved) {
			
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
				
			}
			
		}
		
		verificPegaDoce();
		verificPegaBala();
		
		if(Game.doce_contagem == Game.doce_atual){
			//System.out.println("vc ganhou :)");
			World.restartGame();
		}
	}
	
	public void verificPegaDoce() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Doce) {
				if(Entity.isColidding(this, current)) {
					Game.doce_atual++;
					Game.entities.remove(i);
					return;
				}
			}
		}
		
	}
	
	public void verificPegaBala() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Bala) {
				if(Entity.isColidding(this, current)) {
					Game.bala_atual++;
					Game.entities.remove(i);
					return;
				}
			}
		}
		
	}
	
	public void render(Graphics g) {
		
		if(lastDir == 1) {
			g.drawImage(sprite_right[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if (lastDir == -1){
			g.drawImage(sprite_left[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if (lastDir == 2) {
			g.drawImage(sprite_up[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if (lastDir == -2) {
			g.drawImage(sprite_down[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		if(Game.bala_atual >= Enemy.j) {
			//System.out.println("cu");
			if(lastDir == 1) {
				g.drawImage(sprite_right_puto[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if (lastDir == -1){
				g.drawImage(sprite_left_puto[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if (lastDir == 2) {
				g.drawImage(sprite_up_puto[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if (lastDir == -2) {
				g.drawImage(sprite_down_puto[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}
	}
}
