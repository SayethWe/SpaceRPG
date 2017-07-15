package sineSection.spaceRPG.save;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import sineSection.spaceRPG.UI.panel.GameScreen;
import sineSection.superSerial.containers.SSDatabase;
import sineSection.superSerial.containers.SSField;
import sineSection.superSerial.containers.SSObject;
import sineSection.util.LogWriter;

public class GameSettings extends Saveable {

	public int fontSizeIndex;
	public boolean debugMode;
	
	public GameSettings() {
		fontSizeIndex = GameScreen.DEFAULT_FONT_SIZE;
		debugMode = false;
	}
	
	public String getFileName() {
		return "settings";
	}

	public String getFileExtention() {
		return SSDatabase.FILE_EXTENTION;
	}

	public boolean write() {
		SSDatabase settings = new SSDatabase(getFileName());

		SSObject root = new SSObject("root");
		root.addField(SSField.Integer("fontSizeIndex", fontSizeIndex));
		root.addField(SSField.Boolean("debugMode", debugMode));
		settings.addObject(root);

		getFile().getParentFile().mkdirs();
		try {
			byte[] data = new byte[settings.getSize()];
			settings.writeBytes(data, 0);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(getFilePath()));
			stream.write(data);
			stream.close();
			LogWriter.print("Wrote " + getFileName() + getFileExtention() + " successfully to: " + getFilePath());
			return true;
		} catch (Exception e) {
			LogWriter.printErr("Can't write "  + getFileName() + getFileExtention() + " file to: " + getFilePath());
			return false;
		}
	}
	
	public boolean read() {
		File file = getFile();
		try {
			byte[] data = Files.readAllBytes(file.toPath());
			SSDatabase settings = SSDatabase.Deserialize(data);

			SSObject root = settings.getObject("root");
			this.fontSizeIndex = root.getField("fontSizeIndex").getInteger();
			this.debugMode = root.getField("debugMode").getBoolean();

			LogWriter.print("Loaded " + getFileName() + getFileExtention() + " successfully from: " + getFilePath());
			return true;
		} catch (Exception e) {
			LogWriter.printErr("Can't read " + getFileName() + getFileExtention() + " file from: " + getFilePath() + ", rewriting file.");
			write();
			read();
			return false;
		}
	}

	public GameSettings clone() {
		GameSettings copy = new GameSettings();
		copy.fontSizeIndex = fontSizeIndex;
		return copy;
	}

}
