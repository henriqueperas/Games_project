package com.good_fire.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.good_fire.entities.Ammo;
import com.good_fire.entities.Enemy;
import com.good_fire.entities.Entity;
import com.good_fire.entities.Gun;
import com.good_fire.entities.LifePack;
import com.good_fire.entities.Particles;
import com.good_fire.entities.Player;
import com.good_fire.graficos.Spritesheet;
import com.good_fire.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		//mapa randomico		
		/*
		Game.player.setX(0);
		Game.player.setY(0);
		WIDTH = 100;
		HEIGHT = 100;
		tiles = new Tile[WIDTH*HEIGHT];
		
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
			}
		}
		int dir = 0;
		int xx =0, yy = 0; 
		
		for(int i = 0; i < 200; i++) {
			tiles[xx+yy*WIDTH] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
			if(dir == 0) {
				//direita
				if(xx < WIDTH) {
					xx++;
				}
			}else if(dir == 1) {
				//esquerda
				if(xx > 0) {
					xx--;
				}
			}else if(dir == 2) {
				//baixo
				if(yy < HEIGHT) {
					yy++;
				}
			}else if(dir == 3) {
				//cima
				if(yy > 0) {
					yy++;
				}

			}
			
			if(Game.rand.nextInt(100) < 30) {
				dir = Game.rand.nextInt(4);
			}		
		}
*/
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelsAtual = pixels[xx + (yy *map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					if(pixelsAtual == 0xFF000000) {
						//floor/chão
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					}else if(pixelsAtual == 0xFFFFFFFF) {
						//wall/parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
					}else if(pixelsAtual == 0xFF0000FF) {
						//player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if(pixelsAtual == 0xFF4EE00F){
						//Enemy
						Enemy en = new Enemy(xx*16,yy*16,16,16,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					}else if(pixelsAtual == 0xFFDD6300){
						//Gun
						Gun gun = new Gun(xx*16,yy*16,16,16,Entity.GUN_EN);
						gun.setMask(4, 2, 8, 6);
						Game.entities.add(gun);
					}else if(pixelsAtual == 0xFFE00007){
						//Life Pack
						LifePack pack = new LifePack(xx*16,yy*16,16,16,Entity.LIFEPACK_EN);
						pack.setMask(4, 1, 7, 11);
						Game.entities.add(pack);
					}else if(pixelsAtual == 0xFFFFB82B){
						//Armor
						Ammo ammo = new Ammo(xx*16,yy*16,16,16,Entity.AMMO_EN);
						ammo.setMask(4, 2, 9, 10);
						Game.entities.add(ammo);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void generateParticles(int amount, int x, int y) {
		for(int i = 0; i < amount; i++) {
			Game.entities.add(new Particles(x,y,1,1,null));
		}
	}
	
	public static boolean isFreeDynamic(int xnext,int ynext,/*int zplayer,*/int width,int height) {
		
		int x1 =  xnext / TILE_SIZE;
		int y1 =  ynext / TILE_SIZE;
		
		int x2 = (xnext+width-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+height-1)  / TILE_SIZE;
		
		int x4 = (xnext+width-1) / TILE_SIZE;
		int y4 = (ynext+height-1)  / TILE_SIZE;
		
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
	
	public static void restartGame(String level) {
		Game.enemies.clear();
		Game.entities.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
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
	public static void renderMiniMap(){
		for(int i = 0; i < Game.minimapaPixels.length; i++) {
			Game.minimapaPixels[i] = 0;
		}
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if( tiles[xx + (yy*WIDTH)] instanceof WallTile) {
					Game.minimapaPixels[xx + (yy*WIDTH)] = 0xffffff;
				}
			}
		}
		for(int en = 0; en < Game.enemies.size(); en++)
		{
			Entity enemys = Game.enemies.get(en);

			int xEnemies = enemys.getX() / TILE_SIZE;
			int yEnemis = enemys.getY() / TILE_SIZE;


			Game.minimapaPixels[xEnemies + (yEnemis * WIDTH)] = 0x4EE00F;
		}
		for(int en = 0; en < Game.entities.size(); en++ )
		{
			Entity ammo = Game.entities.get(en);
				if(ammo instanceof Ammo) {
					int xAmmo = ammo.getX() / TILE_SIZE;
					int yAmmo = ammo.getY() / TILE_SIZE;
					Game.minimapaPixels[xAmmo + (yAmmo * WIDTH)] = 0xFFB82B;
			}
		}
		for(int en = 0; en < Game.entities.size(); en++ )
		{
			Entity lifePack = Game.entities.get(en);
				if(lifePack instanceof LifePack) {
					int xLifePack = lifePack.getX() / TILE_SIZE;
					int yLifePack = lifePack.getY() / TILE_SIZE;
					Game.minimapaPixels[xLifePack + (yLifePack * WIDTH)] = 0xE00007;
			}
		}
		for(int en = 0; en < Game.entities.size(); en++ )
		{
			Entity gun = Game.entities.get(en);
				if(gun instanceof Gun) {
					int xGun = gun.getX() / TILE_SIZE;
					int yGun = gun.getY() / TILE_SIZE;
					Game.minimapaPixels[xGun + (yGun * WIDTH)] = 0xDD6300;
			}
		}
		
		int xPlayer = Game.player.getX()/16;
		int yPlayer = Game.player.getY()/16;
		
		Game.minimapaPixels[xPlayer + (yPlayer*WIDTH)] = 0x0000ff;
	}
}
