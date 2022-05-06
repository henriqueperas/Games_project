package com.good_fire.graficos;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import com.good_fire.entities.Player;
import com.good_fire.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(10,10,220,30);
		g.setColor(Color.green);
		g.fillRect(10,10,(int)((Player.life / 100) * 220),30);
		g.setColor(Color.white);
		g.drawRect(10,10,220,30);
		
		g.drawImage(Game.spritesheet.getSprite(278, 4, 6, 6), (200 * 5) + 70, 12, 30, 30, null);
		g.setFont(new Font("Arial", Font.BOLD, 27));
		g.drawString("" +Player.currentFrangos + " / " + Player.maxFrangos, (200 * 5) + 115, 36);
	}
	
}
