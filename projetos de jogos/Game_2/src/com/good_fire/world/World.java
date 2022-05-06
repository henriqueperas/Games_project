package com.good_fire.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.good_fire.entities.Bala;
import com.good_fire.entities.Doce;
import com.good_fire.entities.Enemy;
import com.good_fire.entities.Entity;
import com.good_fire.entities.Player;
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
						//floor/ch�o
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					}else if(pixelsAtual == 0xFFFFFFFF) {
						//wall/parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
					}else if(pixelsAtual == 0xFF0026FF) {
						//player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}else if (pixelsAtual == 0xFFFF5900) {
						//docinho :)
						Doce doce = new Doce(xx*16, yy*16, 16, 16, 0, Entity.DOCE_SPRITE);
						Game.entities.add(doce);
						Game.doce_contagem++;
					}else if(pixelsAtual == 0xFF4800FF) {
						//balinha :)
						Bala bala = new Bala(xx*16, yy*16, 16, 16, 0, Entity.BALA_SPRITE);
						Game.entities.add(bala);
					}else if(pixelsAtual == 0xFFFF00DC) {
						//fantasma camarada
						Enemy enemy = new Enemy(xx*16, yy*16, 16, 16, 0, Entity.ENEMY1);
						Game.entities.add(enemy);
					}else if(pixelsAtual == 0xFF00FF21) {
						//fantasma camarada
						Enemy enemy = new Enemy (xx*16, yy*16, 16, 16, 0, Entity.ENEMY2);
						//if(Game.bala_atual == 1) {
							//enemy = new Enemy (xx*16, yy*16, 16, 16, 0, Entity.ENEMY1);
						//}
						Game.entities.add(enemy);
					}else if(pixelsAtual == 0xFF00FFFA) {
						//fantasma camarada
						Enemy enemy = new Enemy (xx*16, yy*16, 16, 16, 0, Entity.ENEMY3);
						Game.entities.add(enemy);
					}else if(pixelsAtual == 0xFFFF0000) {
						//fantasma camarada
						Enemy enemy = new Enemy (xx*16, yy*16, 16, 16, 0, Entity.ENEMY4);
						Game.entities.add(enemy);
					}else if(pixelsAtual == 0xFFFFD800) {
						//fantasma camarada
						Enemy enemy = new Enemy (xx*16, yy*16, 16, 16, 0, Entity.ENEMY5);
						Game.entities.add(enemy);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
					  }//coliz�o com o pulo :)
		if(zplayer > 0) {
			return true;
		}
		return false;*/
	}
	
	public static void restartGame() {
		Game.player = new Player(0,0,16,16, 1,Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.clear();
		Game.entities.add(Game.player);
		Game.doce_atual = 0;
		Game.doce_contagem = 0;
		Game.bala_atual = 0;
		Game.world = new World("/level1.png");
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
