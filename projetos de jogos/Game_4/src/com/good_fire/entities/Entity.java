package com.good_fire.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.awt.Rectangle;

import com.good_fire.main.Game;
import com.good_fire.world.Camera;
import com.good_fire.world.Node;
import com.good_fire.world.Vector2i;
import com.good_fire.world.World;

public class Entity {
	
	public static BufferedImage PLAYER_SPRITE_RIGHT = Game.spritesheet.getSprite(96, 0, 16, 16);
	public static BufferedImage PLAYER_SPRITE_LEFT = Game.spritesheet.getSprite(96, 16, 16, 16);
	public static BufferedImage ENEMY1_RIGHT = Game.spritesheet.getSprite(96, 48, 16, 16);
	public static BufferedImage ENEMY1_LEFT = Game.spritesheet.getSprite(96+16, 48, 16, 16);
	
	public double x;
	public double y;
	protected int width;
	protected int height;
	protected double speed;
	
	public int depth;
	
	protected List<Node> path;
	
	public boolean debug = false;
	
	public BufferedImage sprite;
	
	public static Random rand = new Random();
	
	public Entity(double x,double y,int width,int height,double speed,BufferedImage sprite) {
		
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
	}
	
public static Comparator<Entity> nodeSorter = new Comparator<Entity>(){
		
		@Override
		public int compare(Entity n0, Entity n1) {
			if(n1.depth < n0.depth)
				return + 1;
			if(n1.depth > n0.depth)
				return - 1;
			return 0;
		}
		
	};
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2) + 25,0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2)+ 20,0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void setX(int newX) {
		
		this.x = newX;
		
	}
	
	public void setY(int newY) {
		
		this.y = newY;
		
	}
	public int getX() {
		
		return (int)this.x;
		
	}
	
	public int getY() {
		
		return (int)this.y;
		
	}
	
	public int getWidth() {
		
		return this.width;
		
	}
	
	public int getHeight() {
		
		return this.height;
		
	}
	
	public void tick() {
		
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;   
				if(x < target.x * 16 /*&& !isColidding(this.getX() + 1,this.getY())*/) {
					x++;
				}else if(x > target.x * 16 /*&& !isColidding(this.getX() - 1, this.getY())*/) {
					x--;
				}
				
				if(y < target.y * 16 /*&& !isColidding(this.getX(), this.getY() + 1)*/) {
					y++;
				}else if(y > target.y * 16 /*&& !isColidding(this.getX(), this.getY() - 1)*/) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
			}
		}
	}

	public static boolean isColidding(Entity e1,Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX(),e1.getY(),e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(),e2.getY(),e2.getWidth(),e2.getHeight());
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x,this.getY() + maskx - Camera.y,mwidth,mheight);
	}
	
}