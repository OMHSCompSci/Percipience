package omhscsc;

import java.awt.Graphics;

import omhscsc.GameObject;
import omhscsc.graphic.Renderable;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;
/**
 * Superclass for Boxes and Entities, is basically objects that need to render
 * @author Morgan
 */
public abstract class RenderableGameObject extends GameObject implements Renderable {
	protected Hitbox hitbox;
	public RenderableGameObject(Hitbox h) {
		this.hitbox = h;
	}	
	
	public abstract void render(Graphics g, int xoff, int yoff);

	public Hitbox getHitbox() {
		return hitbox;
	}
	public Location getLocation()
	{
		return hitbox.getLocation();
	}
	public void changeHitbox(Hitbox h) {
		hitbox=h;
	}

}
