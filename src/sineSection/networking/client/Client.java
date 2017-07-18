package sineSection.networking.client;

import sineSection.spaceRPG.SpaceRPG;

/**
 * grabs world data from the server, and sends commands
 * 
 * @author geekman9097
 *
 */
public class Client {

	public Client() {
	}

	public void sendChat(String chat) {
		SpaceRPG.getMaster().writeToGui(chat);
	}

}
