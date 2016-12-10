package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;

import omhscsc.state.GameStateState;
import omhscsc.util.Velocity;
import omhscsc.util.geometry.Hitbox;

public class MovingBox extends WorldObject { //Moving block
	protected Velocity v; //velocities in x and y direction
	
	public MovingBox(World world, int x, int y, int w, int h, Color c, double vx, double vy) {
		super(world,x,y,w,h,c);
		this.v = new Velocity(vx,vy);
	}
	@Override
	public void tick(GameStateState s) { //Changes position of hitbox to hitbox+velocity
		Hitbox h = super.getHitbox();
		h.addX(v.getX());
		h.addY(v.getY());
		super.changeHitbox(h);
		Hitbox t = new Hitbox(h.getBounds().width,h.getBounds().height,h.getLocation());
		t.addY(-1); //covers bottom pixel of player
		if (s.getPlayer().getHitbox().getBounds().intersects(t.getBounds())) {//player is touching box
			s.getPlayer().getHitbox().addX(v.getX());
			s.getPlayer().getHitbox().addY(v.getY());
		}
	}
	
}
