package sineSection.spaceRPG.save;

import java.io.File;

import sineSection.spaceRPG.GameInfo;

public abstract class Saveable {
	
	public abstract String getFileName();
	
	public abstract String getFileExtention();

	public File getFile() {
		return new File(getFilePath());
	}

	public String getFilePath() {
		return GameInfo.getAppdataFolderPath() + File.separator + getFileName() + getFileExtention();
	}

	public abstract boolean read();

	public abstract boolean write();

	public void updateFromFile() {
		if (!exists())
			write();
		else
			read();
	}

	public boolean exists() {
		File file = new File(getFilePath());
		return file.exists();
	}

}
