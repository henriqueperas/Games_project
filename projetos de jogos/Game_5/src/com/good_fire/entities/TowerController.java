package com.good_fire.entities;

import java.awt.image.BufferedImage;

import com.good_fire.main.Game;
import com.good_fire.world.World;

public class TowerController extends Entity {
	
	public boolean isPressed = false;
	public int xTarget, yTarget;

	public TowerController(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void tick(){
		if(isPressed) {
			//cria uma torre
			//isPressed = false;
			isPressed = false;
			boolean liberado = true;
			int xx = (xTarget/16) * 16;
			int yy = (yTarget/16) * 16;
			Player player = new Player(xx, yy, 16, 16, 0, Game.spritesheet.getSprite(64, 0, 16, 16));
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Player) {
					if(Entity.isColidding(e, player)) {
						liberado = false;
						System.out.println("já tem uma torre aqui >:(");
					}
				}
			}
			
			if(World.isFree(xx, yy)) {
				liberado = false;
				System.out.println("aí não pode :|");
			}
			
			if(liberado) {
				if(Game.dinheiro >= 25) {
					Game.entities.add(player);
					Game.dinheiro -= 25;
				}else {
					System.out.println("é 25 meu amigo :)");
				}
			}
			
		}
		if(Game.vida <= 0) {
			System.exit(1);
		}
		
	}

}
