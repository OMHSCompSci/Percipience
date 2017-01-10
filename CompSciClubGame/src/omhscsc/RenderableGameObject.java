package omhscsc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import omhscsc.graphic.Renderable;
import omhscsc.graphic.animation.Animation;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;
/**
 * Superclass for Boxes and Entities, is basically objects that need to render
 * @author Morgan
 */
public abstract class RenderableGameObject extends GameObject implements Renderable {
	
	protected Hitbox hitbox;
	/*
	 * objectImage is the image used for this object. (Not needed)
	 * objectAnimation is the animation used for this object. (Not needed)
	 * objectAnimationMap is used to hold other animations used by the object with a key to locate specific animations (Not needed)
	 * objectColor is used to draw the object if nothing else exists. (Needed) 
	 */
	protected transient BufferedImage objectImage;
	protected transient Animation objectAnimation;
	protected transient HashMap<String,Animation> objectAnimationMap;
	protected Color objectColor;
	
	public RenderableGameObject(Hitbox h) {
		this.hitbox = h;
	}	
	

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
		return new Rectangle((int)hitbox.getLocation().getX()+10, (int)hitbox.getLocation().getY()+(int)(hitbox.getBounds().getHeight()-10), (int)(this.hitbox.getBounds().getWidth()-20), 10);
	}
	
	
}
