package omhscsc.graphic;

import java.awt.Graphics;

import omhscsc.util.Hitbox;

public interface Renderable {

	public void render(Graphics g, int xoff, int yoff);
	public Hitbox getHitbox();
}
