package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;

import omhscsc.RenderableGameObject;
import omhscsc.graphic.Renderable;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;

public abstract class WorldObject extends RenderableGameObject {

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
	public void render(Graphics g, int xoff, int yoff) {
		Color last = g.getColor();
		g.setColor(getColor());
		g.fillRect(xoff, yoff, (int)getHitbox().getBounds().getWidth(), (int)getHitbox().getBounds().getHeight());
		g.setColor(last);
		
	}
	public abstract void tick(GameStateState s); //Defined by child classes
}
