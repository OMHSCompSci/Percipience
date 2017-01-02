package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import omhscsc.RenderableGameObject;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;

public abstract class WorldObject extends RenderableGameObject implements Serializable {

	/*
	 * The world / world objects implement serializable because we want to be able to save and load worlds. Images are not included in the world file, and are packaged in the source foulder instead. 
	 */
	private static final long serialVersionUID = 1360726877497841224L;
	private Color c;
	
	public WorldObject(int x, int y, int w, int h, Color c)
	{
		super(new Hitbox(w, h, new Location(x,y)));
		this.c = c;
	}
	
	public Color getColor()
	{
		return c;
	}

	@Override
	public void render(Graphics g, int xoff, int yoff, float scale) {
		Color last = g.getColor();
		g.setColor(getColor());
		g.fillRect(xoff, yoff, (int)(getHitbox().getBounds().getWidth() * scale), (int)(getHitbox().getBounds().getHeight() * scale));
		g.setColor(last);
		
	}
	public abstract void tick(GameStateState s); //Defined by child classes
}
