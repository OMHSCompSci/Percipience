package omhscsc.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import omhscsc.Game;
import omhscsc.GameObject;
import omhscsc.RenderableGameObject;
import omhscsc.graphic.Renderable;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;
import omhscsc.util.Velocity;
import omhscsc.world.World;
import omhscsc.world.WorldObject;

public abstract class LivingEntity extends Entity {

	protected double health,maxHealth,jumpHeight,speed,damage;
	protected boolean canJump;
	protected Velocity velocity;
	protected boolean bot=false;
	/*
	 * Haven't finished, feel free to work on it. --Test comment--
	 */
	
	public LivingEntity(Hitbox h) {
		super(h);
		canJump = true;
	}
	
	public LivingEntity(int x, int y, int w, int h)
	{
		super(x,y,w,h);
		canJump = true;
	}
	
	public boolean canJump()
	{
		return canJump;
	}
	
	public void setCanJump(boolean jump) {
		this.canJump=jump;
	}

	
	@Override
	public void tick(GameStateState s)
	{
		super.tick(s);
		//Always super tick (Exceptions may happen)
		defaultMovement(s);
		//fixCollisions(s);
		//Removing fix collisions, add it wherever needed in subclasses
	}
	
	public void render(Graphics g, int x, int y)
	{
		super.render(g,x,y);
		//place holder
	}

	protected void fixCollisions(GameStateState gs)
	{
		//Fixes collisions with boxes
		//If we want to change to collision to entities and boxes, change w to wo, remove the try/catch and wo declaration
		for(RenderableGameObject w: gs.getCurrentWorld().getGameObjects()){
			try {
				WorldObject wo = (WorldObject)w;
				if(wo.getTopBound().intersects(getBottomBound())){
					if(!bot) {
					//	System.out.println("Colliding bottom");
						bot=!bot;
					}
					
					velocity.setY(0);
					hitbox.setY(wo.getHitbox().getLocation().getY()-hitbox.getBounds().getHeight());
					setCanJump(true);
				}
				if(wo.getHitbox().getBounds().intersects(getLeftBound())){
					//System.out.println("Colliding left");
					bot=false;
					velocity.setX(0);
					hitbox.setX(wo.getHitbox().getLocation().getX()+wo.getHitbox().getBounds().getWidth());
				}
				if(wo.getHitbox().getBounds().intersects(getRightBound())){
				//	System.out.println("Colliding right");
					bot=false;
					velocity.setX(0);
					hitbox.setX(wo.getHitbox().getLocation().getX()-hitbox.getBounds().getWidth());
				}
				if(wo.getHitbox().getBounds().intersects(getTopBound())){
				//	System.out.println("Colliding top");
					bot=false;
					velocity.setY(0);
					hitbox.setY(wo.getHitbox().getLocation().getY()+wo.getHitbox().getBounds().getHeight());
				}
			} catch (ClassCastException e) {}
			catch (Exception e) {e.printStackTrace();}
		
		}
	}
	
	protected void defaultMovement(GameStateState s)
	{
		Location last = this.getLocation().clone();
		//double change = velocity.getX();
		velocity.addY((double)Game.GRAVITY/(double)Game.TPS);
		hitbox.addY(velocity.getY()/(double)Game.TPS);
		hitbox.addX(velocity.getX()/(double)Game.TPS);
		velocity.addX((velocity.getX()*-.9)/(double)Game.TPS);

		Location now = this.getLocation().clone();
		//System.out.println(change + "...but then - now is " + (now.getX() - last.getX()));
		
		checkChange(last, now, velocity.getX(), velocity.getY(), s);
		//Checking change BEFORE setting velocity to 0 because it could make checkChange infinite.
		if(velocity.getX() < 50)
			velocity.setX(0);
	}
	
	/**
	 * This method is to check the change from the last location to the most recent location. If there is a Box somewhere in between the last and most recent, it will do a more accurate change of location.
	 * @param lastLoc
	 * @param newLoc
	 * @param xchange The x velocity
	 * @param ychange The y velocity
	 */
	protected void checkChange(Location lastLoc, Location newLoc, double xchange, double ychange, GameStateState gs) {
		double w = (newLoc.getX() - lastLoc.getX());
		//System.out.println("Check change width: " + w);
		if(Math.abs(w) < 1 || w == 0)
			w=1 * (w < 0 ? -1:1);
		int x = (int)((w < 0) ? newLoc.getX():lastLoc.getX());
		
		w = (w < 0) ? w*-1:w;
		/*Find the width of the change, if it's negative (moving left) set the starting x value of the
		*hit box to the new loc instead of the old one.
		*If w less than 0 make positive
		*/
		/*
		 * Same thing here, if h < 0 (moving up), change starting y
		 */
		double h = (int)(newLoc.getY() - lastLoc.getY());
		if(Math.abs(h) < 1 || h == 0)
			h=1 * (h < 0 ? -1:1);
		int y = (int)((h < 0) ? lastLoc.getY():newLoc.getY());
		Hitbox changeBox = new Hitbox((int)w,(int)h,x,y);
		//System.out.println(x + " " + y + " " + w + " " + h);
		for (GameObject rgo : gs.getCurrentWorld().getGameObjects()) {
			try {
				WorldObject wo = (WorldObject)rgo;
				if(wo.getHitbox().getBounds().intersects(changeBox.getBounds())) {
					//Need to be more specific with movement because there should have been a collision in the distance between then and now, but we missed it.
					//int xdiff = (int)(wo.getHitbox().getLocation().getX() - changeBox.getLocation().getX());
				//	int ydiff = (int)(wo.getHitbox().getLocation().getY() - changeBox.getLocation().getY());
					
					preciseMovement(lastLoc,xchange, ychange, wo);
					break;
				}
			} catch (ClassCastException e) {}
			catch (Exception e) {e.printStackTrace();}
		}
		
	}
	
	protected boolean isColliding(WorldObject wo) {
		return (wo.getHitbox().getBounds().intersects(this.getHitbox().getBounds()));
	}
	
	protected void preciseMovement(Location startingLocation, double xv, double yv, WorldObject wo)
	{
		//Using xv and yv because of player (literally). The players input is a seperate thing, and because of that this is required
		//double currentX = 0;
		//double currentY = 0;
		//System.out.print("USING PRECISE ");
		int t = 0;
		do {
			double xChange = xv/500.0;
			double yChange = yv/500.0;
			velocity.addY(yChange);
			hitbox.addX(xChange);
			t++;
			//currentX+=xChange;
			//currentY+=yChange;
		} while (!isColliding(wo));
	//	System.out.print(t + " times\n");
	}
	
	@Override
	public Location getLocation() {
		return hitbox.getLocation();
	}
	
	
	public void drawHitBoxes(Graphics g, int x, int y)
	{
		g.setColor(Color.ORANGE);
		Graphics2D g2 = (Graphics2D)g;
		g2.draw(new Rectangle((int)x, (int)y+5, 10, (int)this.hitbox.getBounds().getHeight()-10));
		g2.setColor(Color.RED);
		g2.draw(new Rectangle((int)x + (int)(hitbox.getBounds().getWidth()-10), (int)y+5, 10, (int)this.hitbox.getBounds().getHeight()-10));
		g2.setColor(Color.PINK);
		g2.draw(new Rectangle((int)x+5, (int)y, (int)(this.hitbox.getBounds().getWidth()-10), 10));
		g2.setColor(Color.BLUE);
		g2.draw(new Rectangle((int)x+5, (int)y+(int)(hitbox.getBounds().getHeight()-10), (int)(this.hitbox.getBounds().getWidth()-10), 10));
		g2.setColor(Color.GREEN);
		g2.draw(new Rectangle((int)x,(int)y,(int)getHitbox().getBounds().getWidth(), (int)getHitbox().getBounds().getHeight()));
		
	}
	

	public void jump()
	{
		if(!canJump)
			return;
		velocity.setY(-jumpHeight*400);
		hitbox.addY(-5);
		canJump = false;
	}
}
