package omhscsc.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import omhscsc.GameObject;
import omhscsc.RenderableGameObject;
import omhscsc.graphic.Renderable;
import omhscsc.state.GameStateState;
import omhscsc.util.Location;
import omhscsc.util.geometry.Hitbox;
import omhscsc.world.World;

public abstract class Entity extends RenderableGameObject {
	
	public Entity(Hitbox h)
	{
		super(h);
	}
	
	public Entity(World wr, int x, int y, int w, int h)
	{
		super(new Hitbox(w,h,new Location(x,y,wr)));
	}
	
	@Override
	public void render(Graphics g, int x, int y)
	{
		//Place holder
	}
	
	@Override
	public void tick(GameStateState s)
	{
		//Default tick
		//May be used later, so leave empty
	}
	
	
}
