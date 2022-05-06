package com.good_fire.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.good_fire.entities.Ammo;
import com.good_fire.entities.AmmoShoot;
import com.good_fire.entities.Enemy;
import com.good_fire.entities.Entity;
import com.good_fire.entities.Npc;
import com.good_fire.entities.Player;
import com.good_fire.graficos.Spritesheet;
import com.good_fire.graficos.UI;
import com.good_fire.world.Camera;
import com.good_fire.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	private int CUR_LEVEL = 1 , MAX_LEVEL = 2;
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<AmmoShoot> ammo;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	public static Random rand;
	
	public UI ui;
	
	//public int xx, yy;
	/*
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
	public Font newfont;
	*/
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	//sistema de cutscene
	public static int entrada = 1;
	public static int comecar = 2;
	public static int comecar2 = 3;
	public static int jogando = 4;
	public static int estado_cena = entrada;
	
	public int timeCena = 0, maxTimeCena = 120;
	public int timeCena2 = 0, maxTimeCena2 = 120;
	
	public Menu menu;
	
	public BufferedImage lightmap;
	public static BufferedImage minimapa;
	
	public static int[] pixels;
	public static int[] lightMapPixels;
	public static int[] minimapaPixels;
	
	public Npc npc;
	
	public int mx, my;
	
	public boolean saveGame = false;
	
	public Game() {
		Sound.musicBackground.loop();
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		//setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		//o codigo a cima é para o fullscreen
		//para aplicar é neseserio trocar WIDTH*SCALE por Toolkit.getDefaultToolkit().getScreenSize().width e fazer io mesmo com o height
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		//inicializando objetos
		ui = new UI();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		try {
			lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0, lightmap.getWidth());
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		ammo = new ArrayList<AmmoShoot>();
		
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0,0,16,16,spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		world = new World("/level1.png");
		
		minimapa = new BufferedImage(World.WIDTH, World.HEIGHT,BufferedImage.TYPE_INT_RGB);
		minimapaPixels = ((DataBufferInt)minimapa.getRaster().getDataBuffer()).getData();
		/*
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(32f);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		*/
		
		npc = new Npc(16,80,16,16,spritesheet.getSprite(128, 16, 16, 16));
		entities.add(npc);
		
		menu = new Menu();
	}
	
	public void initFrame(){
		
		frame = new JFrame("Game 1 #");
		frame.add(this);
		//frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		//Icone da Janela
		Image imagem = null;
		try {
			imagem = ImageIO.read(getClass().getResource("/cursor.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		//Icone Cursor
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("/icone.png"));
		Cursor c = toolkit.createCustomCursor(imagem, new Point(0,0), "img");
		frame.setCursor(c);
		frame.setIconImage(image);
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public synchronized void start() {
		
		thread = new Thread(this);
		isRunning = true;
		thread.start();
		
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String args[]) {
		
		Game game = new Game();
		game.start();
		
	}
	
	public void tick() {
		if(gameState == "NORMAL") {
			//xx++;
			if(this.saveGame) {
				this.saveGame = false;
				
				String[] opt1 = {"level","vida","municao"};
				int[] opt2 = {this.CUR_LEVEL, (int) player.life, (int) player.ammo};
				Menu.saveGame(opt1,opt2,10);
				System.out.println("Jogo Salvo");
			}
		this.restartGame = false;
		//if(Game.estado_cena == Game.jogando) {
		for(int i = 0; i < entities.size(); i++) {
			
			Entity e = entities.get(i);
			e.tick();
		}
		
		for(int i = 0; i < ammo.size(); i++) {
			ammo.get(i).tick();
		}/*
		}else {
			if(Game.estado_cena == Game.entrada) {
				if(player.getX() < 100) {
					player.x += 0.6;
				}else {
					Game.estado_cena = Game.comecar;
				}
			}else if(Game.estado_cena == Game.comecar) {
				timeCena++;
				if(timeCena == maxTimeCena) {
					Game.estado_cena = Game.comecar2;
				}
			}else if(Game.estado_cena == Game.comecar2) {
				timeCena2++;
				if(timeCena2 == maxTimeCena2) {
					Game.estado_cena = Game.jogando;
				}
			}
		}*/
		/*
		if(enemies.size() == 0) {
			//Proximo level
			CUR_LEVEL++;
			if(CUR_LEVEL > MAX_LEVEL) {
				CUR_LEVEL = 1;
			}
			String newWorld = "level"+CUR_LEVEL+".png";
			World.restartGame(newWorld);
		}*/
		}else if(gameState == "GAME_OVER") {
			this.framesGameOver++;
			if(this.framesGameOver == 30) {
				this.framesGameOver = 0;
				if(this.showMessageGameOver)
					this.showMessageGameOver = false;
				else
					this.showMessageGameOver = true;
			}
			
			if(restartGame) {
				this.restartGame = false;
				this.gameState = "NORMAL";
				CUR_LEVEL = 1;
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartGame(newWorld);
			}
		}else if(gameState == "MENU") {
			player.updateCamera();
			menu.tick();
		}
	}

	public void drawRectangleExample(int xoff, int yoff) {
		for(int xx = 0; xx < 32; xx++) {
			for(int yy = 0; yy < 32; yy++) {
				int xOff = xx + xoff;
				int yOff = yy + yoff;
				if(xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT)
					continue;
				pixels[xOff + (yOff*WIDTH)] = 0xff0000;
			}
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		Collections.sort(entities, Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			
			Entity e = entities.get(i);
			e.render(g);
			
		}
		for(int i = 0; i < ammo.size(); i++) {
			ammo.get(i).render(g);
		}
		//applyLight();
		ui.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		//drawRectangleExample(xx,yy);
		//para dapitar o jogo para fullscreen coloque todas as linhas entre o setFont até e tambem o else if antes desse drawImage :) 
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE,null);
		g.setFont(new Font("arial",Font.BOLD,20));
		g.setColor(Color.white);
		g.drawString("Munição: " + player.ammo,475,40);
		
		g.drawImage(minimapa, 590, 20, World.WIDTH*5, World.HEIGHT*5, null);
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial",Font.BOLD,40));
			g.setColor(Color.white);
			g.drawString("GAMER OVER",(WIDTH*SCALE - 270) / 2, (HEIGHT*SCALE -30) / 2);
			g.setFont(new Font("arial",Font.BOLD,22));
			if(showMessageGameOver)
			g.drawString("Precione enter para recomeçar",(WIDTH*SCALE - 310) / 2, (HEIGHT*SCALE + 45) / 2);
		}else if(gameState == "MENU"){
			menu.render(g);
		}/*
		if(Game.estado_cena == Game.comecar) {
			g.drawString("Prepere-se", 500, 150);
		}
		if(Game.estado_cena == Game.comecar2) {
			g.drawString("Q...",350,200);
		}*/
		/*
		Graphics2D g2 = (Graphics2D) g;
		double angleMouse = Math.atan2(my -225, mx - 225);
		g2.rotate(angleMouse, 225, 225);
		System.out.println(Math.toDegrees(angleMouse));
		g.setColor(Color.RED);
		g.fillRect(200, 200, 50, 50);
		*/
		/*
		g.setFont(newfont);
		g.setColor(Color.white);
		g.drawString("Teste", 20, 20);
		*/
		bs.show();
		World.renderMiniMap();
	}
	
	public void applyLight() {
		for(int xx= 0; xx < Game.WIDTH; xx++) {
			for(int yy = 0; yy < Game.HEIGHT; yy++) {
				if(lightMapPixels[xx+(yy* Game.WIDTH)] == 0xffffffff) {
					int pixel = Pixel.getLighBlend(pixels[xx + (yy * Game.WIDTH)], 0x808080, 0);
					pixels[xx + (yy * Game.WIDTH)] = pixel;
				}
			}
		}
	}
	
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
				
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
				
			}
			
		}
		stop();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.jump = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			npc.showMessage = false;
		}

		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			if(gameState == "MENU") {
				menu.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			if(gameState == "MENU") {
				menu.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.shoot = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if(gameState == "MENU") {
				menu.enter = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P) {
			gameState = "MENU";
			menu.pause = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_O) {
			if(gameState == "NORMAL")
			this.saveGame = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		
	}

	@Override
	public void mousePressed(MouseEvent e) {
			player.mouseShoot = true;
			player.mx = (e.getX() / 3);
			player.my = (e.getY() / 3);

			
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();
		
	}

}

