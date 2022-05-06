package com.good_fire.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;
import com.good_fire.main.Sound;
import com.good_fire.world.Camera;
import com.good_fire.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	public int right_dir = 0,left_dir = 1,up_dir = 2,down_dir = 4;
	public int dir = right_dir;
	public double speed = 1;

	private int frames = 0,maxFrames = 15, index = 0,maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage playerDamage;
	
	private boolean hasGun = false;
	
	public int ammo = 0;
	
	public boolean isDamage = false;
	private int damageFrames = 0;
	
	public boolean shoot = false,mouseShoot = false;
	public int mx,my;
	
	public double life = 100,maxlife = 100;
	
	public boolean jump = false;
	public boolean isJumping = false;
	
	public int zplayer = 0;
	
	public int jumpFrames = 50, jumpCur = 0;
	
	public boolean jumpUp = false;
	public boolean jumpDown = false;
	
	public int jumpSpd = 2;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(0,32,16,16);
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32+ (i*16), 0, 16, 16);	
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32+ (i*16), 16, 16, 16);	
		}
		for(int i = 0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(32+ (i*16), 48, 16, 16);	
		}
		for(int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32+ (i*16), 32, 16, 16);	
		}
		
	}/*
	public void revealMap() {
		int xx = (int) (x/16);
		int yy = (int) (y/16);
		
		World.tiles[xx+yy*World.WIDTH].show = true;
		World.tiles[xx-1+yy*World.WIDTH].show = true;
		World.tiles[xx-2+yy*World.WIDTH].show = true;
		World.tiles[xx+1+yy*World.WIDTH].show = true;
		World.tiles[xx+2+yy*World.WIDTH].show = true;
		
		World.tiles[xx+((yy+1)*World.WIDTH)].show = true;
		World.tiles[xx+((yy+2)*World.WIDTH)].show = true;
		World.tiles[xx+((yy-1)*World.WIDTH)].show = true;
		World.tiles[xx+((yy-2)*World.WIDTH)].show = true;
		
		World.tiles[xx+1+((yy+1)*World.WIDTH)].show = true;
		World.tiles[xx+2+((yy+1)*World.WIDTH)].show = true;
		World.tiles[xx-1+((yy-1)*World.WIDTH)].show = true;
		World.tiles[xx-2+((yy-1)*World.WIDTH)].show = true;
		
		World.tiles[xx+1+((yy-1)*World.WIDTH)].show = true;
		World.tiles[xx+2+((yy-1)*World.WIDTH)].show = true;
		World.tiles[xx-1+((yy+1)*World.WIDTH)].show = true;
		World.tiles[xx-2+((yy+1)*World.WIDTH)].show = true;
		
		World.tiles[xx+1+((yy+2)*World.WIDTH)].show = true;
		World.tiles[xx+1+((yy-2)*World.WIDTH)].show = true;
		World.tiles[xx-1+((yy+2)*World.WIDTH)].show = true;
		World.tiles[xx-1+((yy-2)*World.WIDTH)].show = true;
		

	}
	*/

	public void tick() {
		
		//revealMap();
		
		if(jump) {
			if(isJumping == false) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}
		}
		
		if(isJumping == true) {
			//if(jumpCur < jumpFrames) {
				if(jumpUp) {
					jumpCur += jumpSpd;
				}else if(jumpDown) {
					jumpCur -= jumpSpd;
					if(jumpCur <= 0) {
						isJumping = false;
						jumpDown = false;
						jumpUp = false;
					}
				}
				zplayer = jumpCur;
				if(jumpCur >= jumpFrames) {
					jumpUp = false;
					jumpDown= true;
					//System.out.println("blz");
				}
			//}
		}
		
		depth = 1;
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY()/*,zplayer*/)) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}else if (left && World.isFree((int)(x-speed),this.getY()/*,zplayer*/)) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed)/*,zplayer*/)) {
			moved = true;
			dir = up_dir;
			y-=speed;
		}else if (down && World.isFree(this.getX(),(int)(y+speed)/*,zplayer*/)) {
			moved = true;
			dir = down_dir;
			y+=speed;
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
		
		chekCollisionLifePack();
		chekCollisionAmmo();
		chekCollisionGun();
		
		if(isDamage) {
			this.damageFrames++;
			if(this.damageFrames == 5) {
				this.damageFrames = 0;
				isDamage = false;
			}
		}
		
		if(shoot) {
			shoot = false;
			if(hasGun && ammo > 0) {
			ammo--;
			//criar bala e atirar
			shoot = false;
			int dx = 0;
			int px = 0;
			int py = 7;
			if(dir == right_dir) { 
				px = 12;
				dx = 1;
			}else {
				py = 7;
				dx = -1;
			}
			
			AmmoShoot ammo = new AmmoShoot(this.getX() + px,this.getY() + py,3,3,null,dx,0);
			Game.ammo.add(ammo);
			}
		}
		
		if(mouseShoot) {
			mouseShoot = false;

			if(hasGun && ammo > 0) {
			ammo--;
			//criar bala e atirar
			int px = 0,py = 7;
			double angle = 0;
			if(dir == right_dir) { 
				px = 12;
				angle = Math.atan2(my - (this.getY() +py - Camera.y),mx - (this.getX() +px - Camera.x));
			}else {
				px = 7; 
				angle = Math.atan2(my - (this.getY() +py - Camera.y),mx - (this.getX() +px - Camera.x));
			}
			
			double dx = Math.cos(angle);
			double dy = Math.sin(angle);
			
			AmmoShoot ammo = new AmmoShoot(this.getX() + px,this.getY() + py,3,3,null,dx,dy);
			Game.ammo.add(ammo);
			}
		}
		
		if(life <= 0) {
			//Game Over
			life = 0;
			Game.gameState = "GAME_OVER";
		}
		updateCamera();
	}
	
public void updateCamera() {
	Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2) + 25,0,World.WIDTH*16 - Game.WIDTH);
	Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2)+ 20,0,World.HEIGHT*16 - Game.HEIGHT);
}
	
public void chekCollisionGun() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Gun) {
				if(Entity.isColidding(this, atual)) {
					hasGun = true;
					//System.out.println("Armado");
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void chekCollisionAmmo() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Ammo) {
				if(Entity.isColidding(this, atual)) {
					Sound.powerupEffect.play();  
					ammo+=10;
					//System.out.println("Munição:" + ammo);
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void chekCollisionLifePack() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof LifePack) {
				if(Entity.isColidding(this, atual)) {
					Sound.powerupEffect.play();
					life+=25;
					if(life > 100)
						life = 100;
					Game.entities.remove(atual);
				}
			}
		}
	}
		
	public void render(Graphics g) {
		if(!isDamage) {
		if(dir == right_dir) {
		g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - zplayer, null);
		if(hasGun) {
			g.drawImage(Entity.GUN_RIGHT, this.getX() + 5  - Camera.x, this.getY() + 3  - Camera.y - zplayer, null);
		}
		}else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - zplayer, null);
			if(hasGun) {
				g.drawImage(Entity.GUN_LEFT, this.getX() - 5 - Camera.x, this.getY() + 3 - Camera.y- zplayer, null);
			}
		}else if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - zplayer, null);
		
		}else if(dir == down_dir) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - zplayer, null);
			if(hasGun) {
				g.drawImage(Entity.GUN_DOWN, this.getX() +2 - Camera.x, this.getY() + 4 - Camera.y - zplayer, null);
			}
		}
		}else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y - zplayer, null);
			if(hasGun) {
				if(dir == left_dir) {
					g.drawImage(Entity.GUN_DAMAGE_LEFT, this.getX() - 5 - Camera.x, this.getY() + 3 - Camera.y - zplayer, null);
				}else {
					g.drawImage(Entity.GUN_DAMAGE_RIGHT, this.getX() + 5  - Camera.x, this.getY() + 3  - Camera.y - zplayer, null);
				}
			}
		}
		if(isJumping) {
			g.setColor(Color.BLACK);
			g.fillOval(this.getX() - Camera.x +2, this.getY() - Camera. y +12,12,5);
		}
	}
	
}
