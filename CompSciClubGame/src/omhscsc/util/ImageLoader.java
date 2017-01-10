package omhscsc.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class ImageLoader {

	private static HashMap<String,BufferedImage> images = new HashMap<String,BufferedImage>();
	
	public static BufferedImage getImage(String name)
	{
		if(!images.containsKey(name))
			images.put(name, ImageLoader.loadImage(name));
		return images.get(name);
	}
	
	public static BufferedImage loadImage(String name)
	{
		try {
			return ImageIO.read(ImageLoader.class.getResourceAsStream("/image/"+name+".png"));
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * BackgroundImages aren't kept in memory because they're a lot bigger than other images.
	 */
	public static BufferedImage[] loadWorldBackground(String name) {
		// Impossible to know frames
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		//This should break when it runs out of frames to find
		int bgNum = 0;
		do {
			try {
				String n = "/image/worlds/" + name + "/" + bgNum + ".png";				
				BufferedImage bi = ImageIO.read(ImageLoader.class.getResourceAsStream(n));
				if(bi == null) {
					throw new IllegalArgumentException("Couldn't locatate image");
				} else {
					images.add(bi);
					bgNum++;
				}
			} catch (IllegalArgumentException | IOException e) {
			//	e.printStackTrace();
				break;
			}
		} while (true);
		BufferedImage[] imageArray = new BufferedImage[images.size()];
		return images.toArray(imageArray);
	}
	
}
