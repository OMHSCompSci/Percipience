package omhscsc.world;

import java.awt.Color;

import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;

public class Projectile extends WorldObject { //Moving block
	private double vx,vy; //velocities in x and y direction
	
	public Projectile(World world, int x, int y, int w, int h, Color c, double vx, double vy) {
		super(world,x,y,w,h,c);
		this.vx = vx;
		this.vy = vy;
	}
	@Override
	public void tick(GameStateState s) { //Changes position of hitbox to hitbox+velocity
		Hitbox h = super.getHitbox();
		h.addX(vx);
		h.addY(vy);
		super.changeHitbox(h);
	}
}
