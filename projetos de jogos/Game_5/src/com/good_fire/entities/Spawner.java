package com.good_fire.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;

public class Spawner extends Entity{
	
	private int timer = 30;
	private int curTimer = 0;

	public Spawner(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		//Criar inimigos!
		curTimer++;
		if(curTimer == timer) {
			//Hora de criar o inimigo!
			curTimer = 0;
			Enemy enemy = new Enemy(x,y,16,16,1,Entity.COCKROACH);
			Game.entities.add(enemy);
		}
		
	}

	
	public void render(Graphics g) {
		//g.setColor(Color.red);
		//g.fillRect((int)x, (int)y, width, height);
	}
}