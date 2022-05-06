package com.good_fire.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;
import com.good_fire.world.AStar;
import com.good_fire.world.Vector2i;
import com.good_fire.world.World;

public class Enemy extends Entity{
	
	public boolean right = true,left = false;
	
	public double vida = 28;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		path = AStar.findPath(Game.world,new Vector2i(World.xINITIAL,World.yINITIAL),new Vector2i(World.xFINAL,World.yFINAL));
	}
	
	public void tick() {
		
		followPath(path);
		if(x >= Game.WIDTH) {
			//Perdemos vida aqui!
			Game.vida -= 1;
			Game.entities.remove(this);
			
			return;
		}
		if(vida <= 0) {
			Game.entities.remove(this);
			Game.dinheiro++;
			return;
		}
	}	
	
	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.red);
		g.fillRect((int)(x-22), (int)(y-4), 28, 3);
		
		g.setColor(Color.green);
		g.fillRect((int)(x-22), (int)(y-4), (int)((vida/28) * 28), 3);
	}

}

//aqui foi uma forma de contornar o problema, mas queria saber o que tem de errado
		/*
		if(right) {
			if(World.isFree((int)(x+speed), (int)y)) {
				x+=speed;
			}else if(World.isFree((int)x, (int)(y+speed))) {
				right = false;
				up = true;
			}else if(World.isFree((int)x, (int)(y-speed))) {
				right = false;
				down = true;
			}
		}
		if(left) {
			if(World.isFree((int)(x-speed), (int)y)) {
				x-=speed;
			}else if(World.isFree((int)x, (int)(y+speed))) {
				left = false;
				up = true;
			}else if(World.isFree((int)x, (int)(y-speed))) {
				left = false;
				down = true;
			}
		}
		if(up) {
			if(World.isFree((int)x, (int)(y+speed))) {
				y+=speed;
			}else if(World.isFree((int)(x+speed), (int)y)) {
				up = false;
				right = true;
			}else if(World.isFree((int)(x-speed), (int)y)) {
				up = false;
				left = true;
			}
		}
		if(down) {
			if(World.isFree((int)x, (int)(y-speed))) {
				y-=speed;
			}else if(World.isFree((int)(x+speed), (int)y)) {
				down = false;
				right = true;
			}else if(World.isFree((int)(x-speed), (int)y)) {
				down = false;
				left = true;
			}
		}
		*/
