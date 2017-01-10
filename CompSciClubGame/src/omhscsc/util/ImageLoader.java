package omhscsc.util;

import java.awt.image.BufferedImage;
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
	
}
