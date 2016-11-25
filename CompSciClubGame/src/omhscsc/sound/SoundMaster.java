package omhscsc.sound;

import java.io.BufferedInputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import omhscsc.util.Debug;

public class SoundMaster {

	/*
	 * Sound is a static class, and will most likely be reworked in some time in
	 * order to more efficiently store and play sounds
	 */
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
	public static void init() {
		// Load default sounds
		// Set up extra sounds
		default_sounds = new HashMap<Sound, Clip>();
		extra_sounds = new HashMap<String, Clip>();

		for (Sound s : Sound.values()) {
			try {
				Clip c = AudioSystem.getClip();
				AudioInputStream ais = AudioSystem.getAudioInputStream(
						(new BufferedInputStream(SoundMaster.class.getResourceAsStream(s.getPath()))));
				c.open(ais);
				default_sounds.put(s, c);
				Debug.info("Successfull loaded " + s.toString());
			} catch (Exception e) {
				e.printStackTrace();
				Debug.info("Unsuccessfully attempted to load " + s.toString() + "\n " + Debug.printStackTrace(e));
			}
		}

	}
}
