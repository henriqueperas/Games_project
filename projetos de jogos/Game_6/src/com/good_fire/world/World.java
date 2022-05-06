package com.good_fire.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.good_fire.entities.Enemy;
import com.good_fire.entities.Entity;
import com.good_fire.entities.Player;
import com.good_fire.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World() {
		String[] tilesTypes = {"grama","neve","areia","terra"};
		WIDTH = 40;
		HEIGHT = 80;
		//Divisor do mapa
		int divisao = WIDTH/tilesTypes.length;
		tiles = new Tile[WIDTH*HEIGHT];
		for(int xx = 0; xx < WIDTH; xx++) {
			int initialHeaight = Entity.rand.nextInt(12 - 8) +8;
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(yy == HEIGHT - 1 || xx == WIDTH -1 || xx == 0 || yy == 0) {
					tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_SOLID);
					tiles[xx+yy*WIDTH].solid = true;
				}else {
					if(yy >= initialHeaight) {
						int indexBioma = xx/divisao;
						if(tilesTypes[indexBioma] == "grama") {
							tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_GRAMA);
						}else if (tilesTypes[indexBioma] == "terra"){
							tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_TERRA);
						}else if (tilesTypes[indexBioma] == "areia"){
							tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_AREIA);
						}else if (tilesTypes[indexBioma] == "neve"){
							tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_NEVE);
						}
					}else {
						tiles[xx+yy*WIDTH] = new FloorTile(xx*16,yy*16,Tile.TILE_AR);
					}
					
				}
			}
		}
	}
	
	public static boolean isFree(int xnext,int ynext/*,int zplayer*/) {
		
		int x1 =  xnext / TILE_SIZE;
		int y1 =  ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1)  / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1)  / TILE_SIZE;
		
		/*if(*/return!((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
					  (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
					  (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
					  (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));/*){
						  return true;
					  }//colizão com o pulo :)
		if(zplayer > 0) {
			return true;
		}
		return false;*/
	}
	
	public static void restartGame() {
		//Game.player = new Player(40,75,16,16, 2,Game.spritesheet.getSprite(0, 0, 16, 16));
		//Game.entities.clear();
		//Game.entities.add(Game.player);;
		return;
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
