package omhscsc;

import omhscsc.state.GameStateState;
import omhscsc.util.Anchor;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;

public class Camera extends GameObject {

	//Hitbox / Area of camera (View box)
	private Hitbox box;
	//What the camera is hooked too
	private Anchor a;
	
	public Camera(Location l, int w, int h)
	{
		box = new Hitbox(w,h,l);
	}
	
	public Hitbox getHitbox()
	{
		return box;
	}
	
	public boolean intersects(Hitbox b)
	{
		return b.getBounds().intersects(box.getBounds());
	}

	public void setAnchor(Anchor a)
	{
		this.a = a;
	}
	
	public double getWidth() {
		return this.getHitbox().getBounds().getWidth();
	}
	
	public double getHeight() {
		return this.getHitbox().getBounds().getHeight();
	}
	
	//wtf am i doing why wont this work aahhhhhhh
	public float getScale() {
		return (float)(Game.WIDTH/getWidth());
	}
	
	public void removeAnchor()
	{
		a = null;
	}

	// default = size * scale
	
	//Sets anchor to center of screen
	@Override
	public void tick(GameStateState s) {
		if(a==null)
			return;
		box.setX(a.getCenterLocation().getX() - (int)((double)this.getHitbox().getBounds().getWidth()/2.0));
		box.setY(a.getCenterLocation().getY() - (int)((double)this.getHitbox().getBounds().getHeight()/2.0));
	}

	public void setScale(float d) {
		int width = (int)(Game.WIDTH / d);
		int height = (int)(Game.HEIGHT / d);
		box.setWidth(width);
		box.setHeight(height);
	}
	
}
