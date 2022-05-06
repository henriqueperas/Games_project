package com.good_fire.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.good_fire.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(5,5,75,10);
		g.setColor(Color.green);
		g.fillRect(5,5,(int)((Game.player.life/Game.player.maxlife)*75),10);
		g.setFont(new Font("arial",Font.BOLD,11));
		g.setColor(Color.white);
		g.drawString((int)Game.player.life + " / " + (int)Game.player.maxlife,22,14);
		
	}
	
}
