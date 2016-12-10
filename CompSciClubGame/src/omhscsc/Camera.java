package omhscsc;

import omhscsc.state.GameStateState;
import omhscsc.util.Anchor;
import omhscsc.util.Location;
import omhscsc.util.geometry.Hitbox;

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
	
	public void removeAnchor()
	{
		a = null;
	}

	//Sets anchor to center of screen
	@Override
	public void tick(GameStateState s) {
		if(a==null)
			return;
		box.setX(a.getLocation().getX() - (int)((double)Game.WIDTH/2.0));
		box.setY(a.getLocation().getY() - (int)((double)Game.HEIGHT/2.0));
	}
	
}
