package sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import sineSection.spaceRPG.SpaceRPG;

public class SoundPlayer {
	private static final Map<String, AudioStream> sounds = new HashMap<>();

	public static void loadSound(String title) throws IOException {
		InputStream input = SpaceRPG.class.getResourceAsStream("/sound/" + title + ".wav");
		AudioStream audio = new AudioStream(input);
		sounds.put(title, audio);
	}

	public static void playSound(String title) {
		AudioPlayer.player.start(sounds.get(title));
	}

}
