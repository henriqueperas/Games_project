package com.good_fire.graficos;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import com.good_fire.entities.Player;
import com.good_fire.main.Game;

public class UI {
	
	public int seconds = 0;
	public int minutes = 0;
	public int frames = 0;
	
	public void tick() {
		frames++;
		if(frames == 60) {
			frames = 0;
			seconds++;
			if(seconds == 60) {
				seconds = 0;
				minutes++;
			}
		}
	}

	public void render(Graphics g) {
		int curLife = (int) ((Game.player.life/100) * 155);
		g.setColor(Color.red);
		g.fillRect(Game.WIDTH*Game.SCALE - 523, Game.HEIGHT*Game.SCALE - 66, 155, 16);
		g.setColor(Color.green);
		g.fillRect(Game.WIDTH*Game.SCALE - 523, Game.HEIGHT*Game.SCALE - 66, curLife, 16);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 14));
		g.drawString((int)(Game.player.life) + " / " + "100", Game.WIDTH*Game.SCALE - 480, Game.HEIGHT*Game.SCALE - 53);
	
		String formatTime = "";
		if(minutes < 10) {
			formatTime+="0"+minutes+":";
		}else {
			formatTime+=minutes+":";
		}
		
		if(seconds < 10) {
			formatTime+="0"+seconds;
		}else {
			formatTime+=seconds;
		}
		
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString(formatTime, 30, 30);
	}
	
}
