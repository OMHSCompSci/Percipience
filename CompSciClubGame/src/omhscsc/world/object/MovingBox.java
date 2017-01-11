package omhscsc.world.object;

import java.awt.Color;
import java.awt.Graphics;

import omhscsc.Game;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.Velocity;
import omhscsc.world.WorldObject;

public class MovingBox extends WorldObject { //Moving block
	protected Velocity v; //velocities in x and y direction
	
	public MovingBox(int x, int y, int w, int h, Color c, double vx, double vy) {
		super(x,y,w,h,c);
		this.v = new Velocity(vx,vy);
	}
	@Override
	public void tick(GameStateState s) { //Changes position of hitbox to hitbox+velocity
		Hitbox h = super.getHitbox();
		double xAdded = (v.getX()/Game.TPS)* Game.getTimeRate();
		double yAdded = (v.getY()/Game.TPS)* Game.getTimeRate();
		Hitbox t = new Hitbox(h.getBounds().width,h.getBounds().height,h.getLocation());
		t.addY(-1); //covers bottom pixel of player
		if (s.getPlayer().getHitbox().getBounds().intersects(t.getBounds())) {//player is touching box
			s.getPlayer().getHitbox().addX(xAdded);
			s.getPlayer().getHitbox().addY(yAdded);
		}
		//Do this BEFORE the box moves
		h.addX(xAdded);
		h.addY(yAdded);
		super.changeHitbox(h);
	}
	
}
