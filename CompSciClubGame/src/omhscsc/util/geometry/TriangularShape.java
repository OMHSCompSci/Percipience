package omhscsc.util.geometry;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class TriangularShape implements Shape, Cloneable {

	//Attempting to make a clone of the Rectangle class so they share almost exact functionality
	//We don't need half of the methods in there never mind...
	
	//May remove this class
	
	protected abstract TriangularShape clone();
	public abstract boolean contains(Point2D p);
	public abstract boolean contains(Triangle2D triangle);
	public abstract boolean contains(Rectangle2D rectangle);
	@Deprecated
	public abstract Rectangle getBounds();
	public abstract Triangle getTriangleBounds();
	public abstract double getCenterX();
	public abstract double getCenterY();
	public abstract Triangle getFrame();
	public abstract double getHeight();
	public abstract double getWidth();
	public abstract double getMaxX();
	public abstract double getMaxY();
	public abstract double getMinX();
	public abstract double getMinY();
	public abstract PathIterator getPathIterator(AffineTransform m, double flatness);
	public abstract double getX();
	public abstract double getY();
	public abstract boolean intersects(Rectangle2D r);
	public abstract boolean intersects(Triangle2D t);
	public abstract boolean isEmpty();
	public abstract void setFrame(double x, double y, double w, double h);
	public abstract void setFrame(Point2D loc, Dimension2D size);
	public abstract void setFrame(Rectangle2D r);
	
	
}
