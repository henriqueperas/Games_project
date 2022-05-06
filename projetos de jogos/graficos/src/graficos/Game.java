package graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private  boolean isRunning = true;
	private final int WIDTH = 160;
	private final int HEIGHT = 120;
	private final int SCALE = 4;
	
	private BufferedImage image;
	
	private Sprite sheet;
	private BufferedImage[] object;
	//private BufferedImage[] object2;
	private int frames = 0;
	private int maxFrames = 1;
	private int curAnimation = 0,maxAnimation = 22;
	
	public Game() {
		
		sheet = new Sprite("/sprites.png");
		object = new BufferedImage[22];
		/*
		object[0] = sheet.getString(0, 0, 16, 16);
		object[1] = sheet.getString(16, 0, 16, 16);
		object2 = new BufferedImage[2];
		object2[0] = sheet.getString(32, 16, 16, 16);
		object2[1] = sheet.getString(48, 16, 16, 16);
		*/
		object[0] = sheet.getString(32, 0, 32, 32);
		object[1] = sheet.getString(64, 0, 32, 32);
		object[2] = sheet.getString(96, 0, 32, 32);
		object[3] = sheet.getString(128, 0, 32, 32);
		object[4] = sheet.getString(160, 0, 32, 32);
		object[5] = sheet.getString(192, 0, 32, 32);
		object[6] = sheet.getString(224, 0, 32, 32);
		object[7] = sheet.getString(256, 0, 32, 32);
		object[8] = sheet.getString(288, 0, 32, 32);
		object[9] = sheet.getString(0, 32, 32, 32);
		object[10] = sheet.getString(32, 32, 32, 32);
		object[11] = sheet.getString(64, 32, 32, 32);
		object[12] = sheet.getString(96, 32, 32, 32);
		object[13] = sheet.getString(128, 32, 32, 32);
		object[14] = sheet.getString(160, 32, 32, 32);
		object[15] = sheet.getString(192, 32, 32, 32);
		object[16] = sheet.getString(224, 32, 32, 32);
		object[17] = sheet.getString(256, 32, 32, 32);
		object[18] = sheet.getString(288, 32, 32, 32);
		object[19] = sheet.getString(0, 64, 32, 32);
		object[20] = sheet.getString(32, 64, 32, 32);
		object[21] = sheet.getString(64, 64, 32, 32);
		/*
		object[0] = sheet.getString(32, 32, 32, 32);
		object[1] = sheet.getString(64, 32, 32, 32);
		object[2] = sheet.getString(0, 32, 32, 32);
		object[3] = sheet.getString(96, 32, 32, 32);
		object[4] = sheet.getString(128, 32, 32, 32);
		object[5] = sheet.getString(96, 32, 32, 32);
		object[6] = sheet.getString(0, 32, 32, 32);
		object[7] = sheet.getString(64, 32, 32, 32);
		 */
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
	}
	
	public void initFrame(){
		
		frame = new JFrame("teste");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
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
	
	public static void main(String args[]) {
		
		Game game = new Game();
		game.start();
		
	}
	
	public void tick() {
		
		frames++;
		if(frames > maxFrames) {
			frames = 0;
			curAnimation++;
			if(curAnimation >= maxAnimation) {
				curAnimation = 0;
			}
		}
			
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(8);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		/*
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, 60, 79);
		g.setColor(Color.BLACK);
		g.fillOval(7, 10, 45, 59);
		g.setColor(Color.GREEN);
		g.fillRect(75, 22, 15, 57);
		g.setColor(Color.GREEN);
		g.fillRect(75, 2, 15, 15);
		
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setColor(Color.WHITE);
		g.drawString("Ola mundo", 20, 20);
		*/
		
		//rederização do jogo
		Graphics2D g2 = (Graphics2D) g;
		//g2.rotate(Math.toRadians(45),90+8,90+8);
		g2.drawImage(object[curAnimation], 20, 20, null);
		//g2.rotate(Math.toRadians(-45),90+8,90+8);
		//g2.setColor(new Color(0,0,0,200));
		//g2.fillRect(0, 0, WIDTH, HEIGHT);

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE,null);
		bs.show();
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
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

}
