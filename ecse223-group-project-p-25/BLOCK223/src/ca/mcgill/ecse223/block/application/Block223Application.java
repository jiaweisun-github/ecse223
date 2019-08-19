package ca.mcgill.ecse223.block.application;

import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;

import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.RegisterLoginPage;


public class Block223Application {

	private static Block223 block223;
	private static Game currentGame;
	private static UserRole currentUserRole;
	private static PlayedGame currentPlayedGame;
	public static void main(String[] args) {

		// start UI
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new RegisterLoginPage().setVisible(true);
			}
		});
	}

	public static Block223 getBlock223() {
		if (block223 == null) {
			// load model
			block223 = Block223Persistence.load();
		}
		return block223;
	}
	public static Block223 resetBlock223() {
		if (block223 != null) {
			block223.delete();
		}
		setCurrentGame(null);
		setCurrentPlayableGame(null);
		
		block223 = Block223Persistence.load();
		return block223;
	}

	public static UserRole getCurrentUserRole() {
		return currentUserRole;
	}

	public static void setCurrentUserRole(UserRole aUserRole) {
		currentUserRole = aUserRole;
	}

	public static Game getCurrentGame() {
		return currentGame;
	}

	public static void setCurrentGame(Game aGame) {
		currentGame = aGame;
	}
	
	public static PlayedGame getCurrentPlayableGame() {
		return currentPlayedGame;
	}
	
	public static void setCurrentPlayableGame(PlayedGame aGame) {
		currentPlayedGame = aGame;
	}

}
