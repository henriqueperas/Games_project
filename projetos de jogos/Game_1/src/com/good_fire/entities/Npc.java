package com.good_fire.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.good_fire.main.Game;

public class Npc  extends Entity{
	
	public String[] frases = new String[2];
	
	public boolean showMessage = false;
	public boolean show = false;
	
	public int curIndexMsg = 0;
	
	public int frasesIndex = 0;
	
	public int time = 0;
	public int maxTime = 4;

	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		frases[0] = "tome cuidado criança";
		frases[1] = "pegue aquela arma";
	}

	public void tick() {
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		
		int xNpc = (int)x;
		int yNpc = (int)y;
		
		if(Math.abs(xPlayer-xNpc) < 20 && Math.abs(yPlayer-yNpc) < 20) {
			if(show == false) {
			showMessage = true;
			show = true;
			}
		}else {
			//showMessage = false;
		}
		if(showMessage) {
			
			this.time++;
			if(this.time >= this.maxTime) {
			this.time = 0;
			if(curIndexMsg < frases[frasesIndex].length() ) {
				curIndexMsg++;
			}else {
				if(frasesIndex < frases.length -1) {
					frasesIndex++;
					curIndexMsg = 0;
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		if(showMessage) {
			g.setColor(Color.black);
			g.fillRect(0, 110, Game.WIDTH, Game.HEIGHT - 110);
			g.setFont(new Font("Arial", Font.BOLD,9));
			g.setColor(Color.white);
			g.drawString(frases[frasesIndex].substring(0, curIndexMsg), (int)x + 35 , (int)y + 50);
			//g.drawString(frases[1].substring(0, curIndexMsg), (int)x + 35 , (int)y + 60);
		}
	}
	
}
