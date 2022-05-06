package com.good_fire.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;

public class Tubo extends Entity{
	
	public BufferedImage tubo;
	
	public Tubo(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
		
		tubo = Game.spritesheet.getSprite(48, 0, 16, 16);
		
	}

	public void tick() {
		x--;
		if(x+width <= 0) {
			//System.out.println("cu");
			Game.score+=0.5;
			Game.entities.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		if(tubo != null) {
			g.drawImage(tubo,this.getX(),this.getY(),width,height,null);
		} else {
			g.setColor(Color.GRAY);
			g.fillRect(this.getX(), this.getY(), width, height);
		}
	}

}
