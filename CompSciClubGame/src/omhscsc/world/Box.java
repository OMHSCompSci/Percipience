package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;

public class Box extends WorldObject {

	public Box(World world, int x, int y, int w, int h, Color c) {
		super(world, x, y, w, h, c);
		
	}
	
	@Override
	public void render(Graphics g, int xoff, int yoff)
	{
		Color last = g.getColor();
		g.setColor(getColor());
		g.fillRect(xoff, yoff, (int)getHitbox().getBounds().getWidth(), (int)getHitbox().getBounds().getHeight());
		g.setColor(last);
	}

}
