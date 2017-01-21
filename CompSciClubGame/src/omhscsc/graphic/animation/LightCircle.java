package omhscsc.graphic.animation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import omhscsc.util.Hitbox;
import omhscsc.util.ImageLoader;
import omhscsc.util.Location;
import omhscsc.util.Velocity;

public class LightCircle extends AnimationComponent {

	private BufferedImage image;
	private Hitbox viewBounds,self;
	private Velocity v;
	
	public LightCircle(Location l, Hitbox viewBounds) {
		self = new Hitbox(50,50,l);
		image = ImageLoader.getImage("light_circle.png");
		this.viewBounds = viewBounds;
		v = new Velocity((int)(Math.random() * 5) + 1, (int)(Math.random() * 5) + 1);
	}

	@Override
	public void render(Graphics g, int xoff, int yoff, float scale) {
		g.drawImage(image, xoff, yoff, null);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public Hitbox getHitbox() {
		return self;
	}

	
	
}
