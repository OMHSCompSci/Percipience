package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;

import omhscsc.graphic.Renderable;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;

public class WorldObject implements Renderable {

	private Hitbox bounds;
	private Color c;
	
	public WorldObject(World world, int x, int y, int w, int h, Color c)
	{
		this.c = c;
		bounds = new Hitbox(w, h, new Location(x,y, world));
	}
	
	public Hitbox getHitbox()
	{
		return bounds;
	}

	@Override
	public void render(Graphics g, int xoff, int yoff) {
		Color last = g.getColor();
		g.setColor(c);
		g.fillRect(xoff, yoff, (int)bounds.getBounds().getWidth(), (int)bounds.getBounds().getHeight());
		g.setColor(last);
	}
	public void tick(GameStateState s) {} //Defined by child classes
	protected void changeHitbox(Hitbox h) {
		bounds=h;
	}
}
