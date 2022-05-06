package com.good_fire.world;

import com.good_fire.entities.Entity;
import com.good_fire.entities.Tubo;
import com.good_fire.main.Game;
 
public class TuboGenerator {

	public int time = 0;
	public int targetTime = 90;
	
	public void tick() {
		time++;
		if(time == targetTime) {
			//int altura1 = Entity.rand.nextInt(90 - 40) + 30;
			int altura1 = Entity.rand.nextInt(80 + 20); 
			Tubo tubo1 = new Tubo(Game.WIDTH,0,16,altura1,1,Game.spritesheet.getSprite(48, 0, 16, 16));  
			
			int altura2 = altura1 - 110;
			Tubo tubo2 = new Tubo(Game.WIDTH,Game.HEIGHT,16,altura2,1,Game.spritesheet.getSprite(48, 0, 16, 16));
			
			Game.entities.add(tubo1);
			Game.entities.add(tubo2);
			
			time = 0;
		}
		
	}
	
}
