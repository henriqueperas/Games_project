package com.good_fire.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;
import com.good_fire.world.Camera;
import com.good_fire.world.World;

public class Player extends Entity{
	
	public int xTarget, yTarget;
	public boolean atacando = false;
	
	public Player(int x, int y, int width, int height, int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

	}

	public void tick() {
		Enemy enemy = null;
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				int xEnemy = e.getX();
				int yEnemy = e.getY();
				if(Entity.calculateDistance(this.getX(), this.getY(), xEnemy, yEnemy) < 96) {
					enemy = (Enemy)e;
				}
			}
		}
		if(enemy != null) {
			atacando = true;
			xTarget = enemy.getX();
			yTarget = enemy.getY();
			enemy.vida -= 0.1;
		}else {
			atacando = false;
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		//mostrar o ataque
		if(atacando) {
			g.setColor(Color.white);
			g.drawLine((int)(x - 8), (int)(y + 8), xTarget - 8, yTarget + 8);
			
		}
	}
	
}