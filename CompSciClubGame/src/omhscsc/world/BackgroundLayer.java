package omhscsc.world;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class BackgroundLayer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9011163281778148010L;

	private transient BufferedImage image;
	private float scrollRate;
	private boolean repeat;
	private int layerNumber;
	
	public BackgroundLayer(int layerNumber, BufferedImage bi, float scrollRate, boolean repeat) {
		this.image = bi;
		this.scrollRate = scrollRate;
		this.repeat = repeat;
		this.layerNumber = layerNumber;
	}

	/**
	 * Layer number is used to identify which image this background layer should be
	 * @return the world background image id
	 */
	public int getLayerNumber() {
		return this.layerNumber;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public float getScrollRate() {
		return this.scrollRate;
	}
	
	public boolean doesRepeat() {
		return this.repeat;
	}
	
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	
	public void setScrollRate(float scrollRate) {
		this.scrollRate = scrollRate;
	}
	
	public void setImage(BufferedImage bi) {
		this.image = bi;
	}
	
	public void setLayerNumber(int num) {
		this.layerNumber = num;
	}
}
