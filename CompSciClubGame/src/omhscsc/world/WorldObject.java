package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import omhscsc.RenderableGameObject;
import omhscsc.state.GameStateState;
import omhscsc.util.Debug;
import omhscsc.util.Hitbox;
import omhscsc.util.ImageLoader;
import omhscsc.util.Location;

public abstract class WorldObject extends RenderableGameObject implements Serializable {

	/*
	 * The world / world objects implement serializable because we want to be able to save and load worlds. Images are not included in the world file, and are packaged in the source foulder instead. 
	 */
	private static final long serialVersionUID = 1360726877497841224L;
	private Color c;
	private transient BufferedImage objectImage;
	private String imagePath;
	
	/**
	 * Create a world object with the specified hitbox and color
	 * @param x x pos
	 * @param y y pos
	 * @param w width
	 * @param h height
	 * @param c color
	 */
	public WorldObject(int x, int y, int w, int h, Color c)
	{
		super(new Hitbox(w, h, new Location(x,y)));
		this.c = c;
		objectImage = null;
		imagePath = null;
	}

	/**
	 * Create a world object with the specified hitbox and it's custom image.
	 * @param x x pos
	 * @param y y pos
	 * @param w width
	 * @param h height
	 * @param imagePath Path used for {@link omhscsc.util.ImageLoader#getImage(String)}
	 */
	public WorldObject(int x, int y, int w, int h, String imagePath)
	{
		super(new Hitbox(w, h, new Location(x,y)));
		this.c = Color.WHITE;
		this.imagePath = imagePath;
		this.objectImage = ImageLoader.getImage(imagePath);
	}
	
	/**
	 * Return the color used to draw this object
	 * @return
	 */
	public Color getColor()
	{
		return c;
	}
	
	/**
	 * Return the image used for this object
	 * @return
	 */
	public BufferedImage getObjectImage() {
		return this.objectImage;
	}
	
	public String getObjectImageName() {
		return this.imagePath;
	}

	@Override
	public void render(Graphics g, int xoff, int yoff, float scale) {
		Color last = g.getColor();
		g.setColor(getColor());
		if(this.objectImage == null && this.imagePath == null)
			g.fillRect(xoff, yoff, (int)(getHitbox().getBounds().getWidth() * scale), (int)(getHitbox().getBounds().getHeight() * scale));
		else {
			if(this.objectImage == null) {
				this.objectImage = ImageLoader.getImage(imagePath);
				if(this.objectImage == null) {
					//Very bad
					Debug.warn("Can't find image for " + this.getClass() + " " + this.toString() + "!!!! Setting variable imagePath to null to avoid further errors. (WorldObject#render()");
					this.imagePath = null;
					return;
				}
					
			}
			g.drawImage(this.objectImage, xoff, yoff, (int)(getHitbox().getBounds().getWidth() * scale), (int)(getHitbox().getBounds().getHeight() * scale), null);
		}
		g.setColor(last);
		
	}
	public abstract void tick(GameStateState s); //Defined by child classes
}
