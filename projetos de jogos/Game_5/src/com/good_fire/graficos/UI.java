package com.good_fire.graficos;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;

import com.good_fire.entities.Player;
import com.good_fire.main.Game;

public class UI {

	public static BufferedImage HEART = Game.spritesheet.getSprite(0, 64, 16, 16);
	
	public void render(Graphics g) {
		for(int i = 0; i < Game.vida; i++) {
			g.drawImage(HEART, 30 + (i * 52), 10, 64, 64, null);
		}
		
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString("Bufunfa: " + Game.dinheiro, (Game.WIDTH * Game.SCALE) - 210, 50);
	}
	
}
