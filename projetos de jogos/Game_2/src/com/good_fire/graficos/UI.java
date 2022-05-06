package com.good_fire.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.good_fire.main.Game;

public class UI {

	public void render(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.BOLD, 20));
		g.drawString("Doces:"+Game.doce_atual, 30, 30);
		
	}
	
}
