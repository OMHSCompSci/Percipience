package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;

import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;

public class MovingBox extends WorldObject { //Moving block
	private double vx,vy; //velocities in x and y direction
	
	public MovingBox(World world, int x, int y, int w, int h, Color c, double vx, double vy) {
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
	
	@Override
	public void render(Graphics g, int xo, int yo)
	{
		Color last = g.getColor();
		g.setColor(getColor());
		g.fillRect(xo, yo, (int)getHitbox().getBounds().getWidth(), (int)getHitbox().getBounds().getHeight());
		g.setColor(last);
	}
}
