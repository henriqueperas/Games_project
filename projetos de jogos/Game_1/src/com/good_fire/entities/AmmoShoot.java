package com.good_fire.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;
import com.good_fire.world.Camera;
import com.good_fire.world.World;

public class AmmoShoot extends Entity{
	
	private double dx;
	private double dy;

	private double spd = 4  ;
	
	private int life = 30, curLife = 0;

	public AmmoShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;

	}
	
	public void tick() {
		if(World.isFreeDynamic((int)(x+(dx*spd)), (int)(y+(dy*spd)), 3, 3)) {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		}else {
			Game.ammo.remove(this);
			World.generateParticles(100,(int) x,(int) y);
			return;
		}
		if(curLife == life) {
			Game.ammo.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.RED);
		g.fillOval(this.getX() - Camera.x,this.getY() - Camera.y,width,height);
		
	}
	
}
