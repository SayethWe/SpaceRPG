package sineSection.spaceRPG.sound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import sineSection.util.LogWriter;

public class SoundPlayer {

	private static HashMap<String, Clip> clips;
	private static int gap;
	private static boolean mute;
	public static int soundsLoaded;

	public static void init() {
		clips = new HashMap<String, Clip>();
		gap = 0;
		soundsLoaded = 0;
		mute = false;
	}

	public static void load(String path, String name) {
		if (clips.get(name) != null) return;
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(path));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			clips.put(name, clip);
			soundsLoaded++;
			LogWriter.print("Loading sound: " + path + ", name: " + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void play(String s) {
		play(s, gap);
	}

	public static void play(String s, int i) {
		if (mute)
			return;
		Clip c = clips.get(s);
		if (c == null)
			return;
		if (c.isRunning())
			c.stop();
		c.setFramePosition(i);
		while (!c.isRunning())
			c.start();
	}

	public static void play(String s, int start, int end) {
		if (mute)
			return;
		Clip c = clips.get(s);
		if (c == null)
			return;
		if (c.isRunning())
			c.stop();
		c.setFramePosition(start);
		c.setLoopPoints(start, end);
		while (!c.isRunning())
			c.start();
	}

	public static void stop(String s) {
		if (clips.get(s) == null)
			return;
		if (clips.get(s).isRunning())
			clips.get(s).stop();
	}

	public static void stopAll() {
		for (String s : clips.keySet()) {
			if (clips.get(s) == null)
				return;
			if (clips.get(s).isRunning())
				clips.get(s).stop();
		}
	}

	public static void resume(String s) {
		if (mute)
			return;
		if (clips.get(s).isRunning())
			return;
		clips.get(s).start();
	}

	public static void loop(String s) {
		loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int frame) {
		loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int start, int end) {
		loop(s, gap, start, end);
	}

	public static void loop(String s, int frame, int start, int end) {
		stop(s);
		if (mute)
			return;
		clips.get(s).setLoopPoints(start, end);
		clips.get(s).setFramePosition(frame);
		clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void setPosition(String s, int frame) {
		clips.get(s).setFramePosition(frame);
	}

	public static int getFrames(String s) {
		return clips.get(s).getFrameLength();
	}

	public static int getPosition(String s) {
		return clips.get(s).getFramePosition();
	}

	public static void close(String s) {
		stop(s);
		clips.get(s).close();
	}

	public static void loadSoundsFromSoundList(String soundLoaderFile) {
		LogWriter.print("Loading sounds from sound loader file: " + soundLoaderFile);
		try {
			InputStream in = SoundPlayer.class.getResourceAsStream(soundLoaderFile);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while((line = bfr.readLine()) != null) {
				String[] parts = line.split(":");
				if(parts.length != 2) continue;
				String file = parts[0];
				String name = parts[1];
				load(file, name);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
