package omhscsc;

import java.awt.Graphics;
import java.awt.Rectangle;

import omhscsc.GameObject;
import omhscsc.graphic.Renderable;
import omhscsc.util.Location;
import omhscsc.util.geometry.Hitbox;
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
	
	public Rectangle[] getAllBounds() {
		return new Rectangle[] {getBottomBound(),getLeftBound(),getTopBound(),getRightBound()};
	}

	public Rectangle getLeftBound() {
		return new Rectangle((int)hitbox.getLocation().getX(), (int)hitbox.getLocation().getY()+5, 10, (int)this.hitbox.getBounds().getHeight()-10);
	}
	
	public Rectangle getRightBound() {
		return new Rectangle((int)hitbox.getLocation().getX() + (int)(hitbox.getBounds().getWidth()-10), (int)hitbox.getLocation().getY()+5, 10, (int)this.hitbox.getBounds().getHeight()-10);
	}
	
	public Rectangle getTopBound() {
		return new Rectangle((int)hitbox.getLocation().getX()+5, (int)hitbox.getLocation().getY(), (int)(this.hitbox.getBounds().getWidth()-10), 10);
	}
	
	public Rectangle getBottomBound() {
		return new Rectangle((int)hitbox.getLocation().getX()+5, (int)hitbox.getLocation().getY()+(int)(hitbox.getBounds().getHeight()-10), (int)(this.hitbox.getBounds().getWidth()-10), 10);
	}
	
	
}
