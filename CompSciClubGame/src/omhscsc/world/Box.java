package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;

import omhscsc.state.GameStateState;

public class Box extends WorldObject {

	public Box(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
		
	}
	
	@Override
	public void render(Graphics g, int xoff, int yoff, float scale)
	{
		Color last = g.getColor();
		g.setColor(getColor());
		g.fillRect(xoff, yoff, (int)(getHitbox().getBounds().getWidth() * scale), (int)(getHitbox().getBounds().getHeight() * scale));
		g.setColor(last);
	}

	@Override
	public void tick(GameStateState s) {
		//Do nothing (Basic box)
	}

}
