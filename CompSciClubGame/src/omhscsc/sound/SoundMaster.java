package omhscsc.sound;

import java.io.BufferedInputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import omhscsc.Game;
import omhscsc.util.Debug;

/**
 * Sound is a static class, and will most likely be reworked in some time in
 * order to more efficiently store and play sounds. Must be initialized before use
 * with {@link SoundMaster#init()}.
 */
public class SoundMaster {

	
	private static boolean init = false;
	private static HashMap<Sound, Clip> default_sounds;
	/*
	 * Why create an use the enum Sound? This makes it easier to play sounds
	 * that will be used a lot more often than any extra sounds that might be
	 * added in for one reason or another. Being able to type
	 * SoundMaster.play(Sound.JUMP); feels easier than typing
	 * SoundMaster.play("/sound/jump.wav"); and removes the possible mistype
	 * annoyances that may happen.
	 * 
	 * Ways to play a sound: SoundMaster.play(Sound s); Sound.EXAMPLE.play();
	 * 
	 * 
	 * If anyone wants to improve this class, they can do that by finding a way
	 * to get the Data *inside* a clip and storing that, instead of the clip
	 * itself. I think that if the clip data is stored in memory, maybe we can
	 * make a new class that can read and play the data. This means that the
	 * data will be publicly accessible on it's own and will be able to be
	 * simultaneously read from multiple sources (good, I think).
	 */
	private static HashMap<String, Clip> extra_sounds;

	/*
	 * Loaded as false when the class is loaded
	 */
	/**
	 * Initializes SoundMaster with all the default sounds, as well as creates room for custom sounds. To free up
	 * all space used by SoundMaster, call {@link SoundMaster#unload()};
	 */
	public static void init() {
		if(!Game.sound)
			return;
		// Load default sounds
		// Set up extra sounds
		default_sounds = new HashMap<Sound, Clip>();
		extra_sounds = new HashMap<String, Clip>();

		for (Sound s : Sound.values()) {
			try {
				/*
				 * 
				 */
				Clip c = AudioSystem.getClip();
				//Gets a Clip, from the "audio system" which is a playable object that stores audio information (DataLine?).
				AudioInputStream ais = AudioSystem.getAudioInputStream(
						(new BufferedInputStream(SoundMaster.class.getResourceAsStream(s.getPath()))));
				//Finds the resource stream
				c.open(ais);
				//Opens stream
				default_sounds.put(s, c);
				//Places clip into memory. (I'm pretty sure this uses RAM, meaning if we have too many sounds open at once it'll start
				//to cause some problems. Let's be efficient with sounds and not take up too much space.
				Debug.info("Successfull loaded " + s.toString());
			} catch (Exception e) {
				e.printStackTrace();
				Debug.info("Unsuccessfully attempted to load " + s.toString() + "\n " + Debug.printStackTrace(e));
			}
		}
		init = true;
	}
	
	public static boolean isSoundPlaying(Sound s)
	{
		if(!Game.sound)
			return false;
		Clip c = default_sounds.get(s);
		if(c == null)
			return true;
		return c.isActive();
	}
	
	/**
	 * Clears all sound resources, SoundMaster must be reinitialized to be used using {@link SoundMaster#init()};
	 */
	public static void unload()
	{
		if(!Game.sound)
			return;
		for (Sound s : Sound.values())
		{
			Clip c = default_sounds.get(s);
			c.close();
		}
		default_sounds.clear();
		default_sounds = null;
		for (Clip s : extra_sounds.values())
		{
			s.close();
		}
		extra_sounds.clear();
		extra_sounds = null;
		//Just making sure
		init=false;
	}
	
	/**
	 * Play a specified default {@link Sound} <em>one</em> time; and restarts if the clip is in progress. If sound master is not initialized, it will do so now.
	 * If there is no Clip to be found, an error will be printed using {@link Debug#warn(String)}.
	 * @param s The default {@link Sound}.
	 */
	public static void playSound(Sound s)
	{
		if(!Game.sound)
			return;
		if(!init)
			init();
		Clip c = default_sounds.get(s);
		if (c == null)
		{
			Debug.warn("Could not find sound (while attempting to play) " + s.toString());
			return;
		}
		c.setFramePosition(0);
		c.start();
	}
	
	/**
	 * Stop playing a specific {@link Sound} and reset it's frame. If it is not currently playing, nothing will happen.
	 * If there is no Clip to be found, an error will be printed using {@link Debug#warn(String)}.
	 * @param s The {@link Sound}.
	 */
	public static void stopSound(Sound s)
	{
		if(!Game.sound)
			return;
		Clip c = default_sounds.get(s);
		if (c == null)
		{
			Debug.warn("Could not find sound (while attempting to stop) " + s.toString());
			return;
		}
		c.stop();
		c.setFramePosition(0);
	}
}
