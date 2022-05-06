package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	private BufferedImage sprites;
	
	public Sprite(String path) {
		
		try {
			sprites = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	public BufferedImage getString(int x,int y,int width,int height) {
		
		return sprites.getSubimage(x, y, width, height);
		
	}
	
}
