package com.good_fire.entities;

import java.awt.image.BufferedImage;

public class Gun extends Entity{

	public Gun(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		depth = 0;
		
	}

}
