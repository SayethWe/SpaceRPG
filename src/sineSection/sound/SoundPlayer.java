package sineSection.sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sineSection.spaceRPG.SpaceRPG;

public class SoundPlayer {
	private static final Map<String, Clip> sounds = new HashMap<>();

	public static void loadSound(String title) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		Clip audio = AudioSystem.getClip();		
		AudioInputStream input = AudioSystem.getAudioInputStream(SpaceRPG.class.getResourceAsStream("/sound/" + title + ".wav"));
		audio.open(input);
		
		sounds.put(title, audio);
	}

	public static void playSound(String title) throws IOException {
		new SoundThread(sounds.get(title)).run();
	}
	
	private static class SoundThread implements Runnable {
		private Clip a;
		
		SoundThread(Clip a) {
			this.a = a;
		}
		
		@Override
		public void run() {
			a.setFramePosition(0);
			a.start();
			while(a.isRunning());
			a.stop();
		}
		
	}

}
