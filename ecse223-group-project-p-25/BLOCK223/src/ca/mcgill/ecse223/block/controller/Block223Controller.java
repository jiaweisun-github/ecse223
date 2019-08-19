package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.block.controller.TOUserMode;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Ball;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.HallOfFameEntry;
import ca.mcgill.ecse223.block.model.Level;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.PlayedBlockAssignment;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGridCell;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;


public class Block223Controller {

	// ****************************
	// Modifier methods		
	// ****************************
	public static void createGame(String name) throws InvalidInputException {
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to create a game.");
		}
		Block223 block223 = Block223Application.getBlock223();
		Admin admin = (Admin) currentUser;
		if (block223.findGame(name)!=null) {
			throw new InvalidInputException("The name of a game must be unique.");
		}
		if (name ==null || name == "") {
			throw new InvalidInputException("The name of a game must be specified.");
		}
		try {
			Game game = new Game(name, 1, (Admin) admin, 1, 1, 1, 10, 10, block223);
			game.addLevel();
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
		Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		UserRole currentUser = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to define game settings.");
		}
		if(game == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}
		Admin admin = game.getAdmin();
		if(admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
		}
		if (nrLevels < 1 || nrLevels > 99) {
			throw new InvalidInputException("The number of levels must be between 1 and 99.");
		}
		if (minBallSpeedX == 0 && minBallSpeedY == 0) {
			throw new InvalidInputException("The minimum speed of the ball must be greater than zero.");
		}
		if (nrBlocksPerLevel<= 0) {
			throw new InvalidInputException("The number of blocks per level must be greater than zero.");
		}
		try {
			game.setNrBlocksPerLevel(nrBlocksPerLevel);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException("The maximum number of blocks per level cannot be less than the number of existing blocks in a level.");
		}
		Ball ball = game.getBall();
		try {
			ball.setMinBallSpeedX(minBallSpeedX);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			ball.setMinBallSpeedY(minBallSpeedY);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			ball.setBallSpeedIncreaseFactor(ballSpeedIncreaseFactor);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		Paddle paddle = game.getPaddle();
		try {
			paddle.setMaxPaddleLength(maxPaddleLength);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		try {
			paddle.setMinPaddleLength(minPaddleLength);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		List<Level> levels = game.getLevels();
		int size = levels.size();
		while (nrLevels > size) {
			game.addLevel();
			size = levels.size();
		}
		while (nrLevels < size) {
			Level level = game.getLevel(size-1);
			level.delete();
			size = levels.size();
		}
	}

	public static void deleteGame(String name) throws InvalidInputException {
		UserRole currentUser = Block223Application.getCurrentUserRole();
		// check if current user is an admin
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a game.");
		}
		Game currentGame = Block223Application.getCurrentGame();
		Admin admin = currentGame.getAdmin();
		// compare current user with the admin who created the game
		if (admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can delete the game.");
		}
		Block223 block223 = Block223Application.getBlock223();
		Game game = block223.findGame(name);
		// delete game if it exists
		if (game != null) {
			if (game.isPublished()) {
				throw new InvalidInputException("A published game cannot be deleted.");
			}
			Block223 block = game.getBlock223();
			game.delete();
			Block223Persistence.save(block);
		}
	}

	public static void selectGame(String name) throws InvalidInputException {
		String error = "";
		UserRole currentUser = Block223Application.getCurrentUserRole();
		// check if current user is an admin
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to select a game.");
		}
		Block223 block223 = Block223Application.getBlock223();
		Game currentGame = block223.findGame(name);
		// check if specified game exists
		if (currentGame == null) {
			throw new InvalidInputException("A game with name " + name + " does not exist.");
		}
		Admin admin = currentGame.getAdmin();
		// compare current user with the admin who created the game
		if (admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can select the game.");
		}
		if (currentGame.isPublished()) {
			throw new InvalidInputException("A published game cannot be changed.");
		}
		try {
			Block223Application.setCurrentGame(currentGame);
		}
		catch (RuntimeException e) {
			error = e.getMessage();
			throw new InvalidInputException(error);
		}
	}


	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		UserRole currentUser = Block223Application.getCurrentUserRole();
		Game currentGame = Block223Application.getCurrentGame();
		// check if current user is an admin
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to define game settings.");
		}
		// check if current game is set
		if (currentGame == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}
		Admin admin = currentGame.getAdmin();
		// compare current user with the admin who created the game
		if (admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
		}
		// change the name if it is different than the wanted name
		try {
			String currentName = currentGame.getName();
			if (currentName != name) {
				currentGame.setName(name);
			}
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		// change all the other parameters of the game
		Block223Controller.setGameDetails(nrLevels, nrBlocksPerLevel,
				minBallSpeedX, minBallSpeedY,
				ballSpeedIncreaseFactor,
				maxPaddleLength, minPaddleLength);
	}

	public static void addBlock(int aRed, int aGreen, int aBlue, int aPoints) throws InvalidInputException {

		//String error = "";

		UserRole currentUser = Block223Application.getCurrentUserRole();

		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to add a block.");
		}

		Game currentGame = Block223Application.getCurrentGame();

		if (currentGame == null) {
			throw new InvalidInputException("A game must be selected to add a block.");
		}

		Admin admin = currentGame.getAdmin();

		if (admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can add a block.");
		}

		List<Block> sourceList = currentGame.getBlocks();

				for(Block specificBlock : sourceList) {
					int colorRed = specificBlock.getRed();
					int colorGreen = specificBlock.getGreen();
					int colorBlue = specificBlock.getBlue();
		
					if (colorRed == aRed && colorGreen == aGreen && colorBlue == aBlue) {
						throw new InvalidInputException("A block with the same color already exists for the game.");
					}
				}
					try {
						Block block = new Block(aRed, aGreen, aBlue, aPoints, currentGame);		
					}
					catch (RuntimeException e) {
						throw new InvalidInputException(e.getMessage());
					}
	}

	public static void deleteBlock(int id) throws InvalidInputException {
		//William 01/03
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a block.");
		}
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to delete a block.");
		}
		Admin admin = game.getAdmin();
		if (admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can delete a block.");
		}
		Block block = game.findBlock(id);
		if (block != null) {
			block.delete();
		}
	}

	public static void updateBlock(int id, int aRed, int aGreen, int aBlue, int aPoints) throws InvalidInputException {

		String error = "";

		

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to update a block.");
		}

		Game currentGame = Block223Application.getCurrentGame();
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to update a block.");
		}

		Admin admin = currentGame.getAdmin();

		if (admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can update a block.");
		}
		List<Block> blocks = currentGame.getBlocks();
		for(Block block : blocks) {
			int colorRed = block.getRed();
			int colorGreen = block.getGreen();
			int colorBlue = block.getBlue();

			if ((colorRed == aRed) && (colorGreen == aGreen) && (colorBlue == aBlue) && (block.getId() != id)) {
				throw new InvalidInputException("A block with the same color already exists for the game.");
			}
		}

		if(currentGame.findBlock(id) == null){
			throw new InvalidInputException("The block does not exist.");
		}

		Block block = currentGame.findBlock(id);
		try {
			block.setRed(aRed);
		}
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
		try {
			block.setGreen(aGreen);
		}
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
		try {
			block.setBlue(aBlue);
		}
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}

		try {
			block.setPoints(aPoints);
		}
		catch (RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}


	}

	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		String error = "";

		//Check if the user is an admin
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to position a block.");
		}
		//check if the game exists 
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to position a block.");
		}
		//check if the admin created the game ***************question
		if(currentUser instanceof Admin) {
			boolean isAdminCurrentGameCreator = false;
			List<Game> games = ((Admin) currentUser).getGames();

			for(Game instanceOfGame : games) {
				if(instanceOfGame.getName().equals(game.getName())) {
					isAdminCurrentGameCreator = true;
				}
			}
			if(isAdminCurrentGameCreator == false) {
				throw new InvalidInputException("Only the admin who created the game can position a block.");
			}
		}

		Level currentLevel;
		try {
			currentLevel = game.getLevel(level - 1);
		}
		catch (IndexOutOfBoundsException e) {
				throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}

		Block block = game.findBlock(id);

		//Check if number of blocks in the level of the current game, if its already at the maximum, print the following error ****************QUESTION is the numberofblockassignments equivalent to the number of blocks per level?
		if (currentLevel.numberOfBlockAssignments() == game.getNrBlocksPerLevel()) {
			throw new InvalidInputException("The number of blocks has reached the maximum number (" + game.getNrBlocksPerLevel() + ") allowed for this game.");
		}

		//BlockAssignment newPosition = currentLevel.findBlockAssignment(gridHorizontalPosition, gridVerticalPosition)

		//If the position is not empty ((Horizontal/Vertical)gridLocation already occupied), print out error. 
		if(currentLevel.findBlockAssignment(gridHorizontalPosition, gridVerticalPosition) != null) {
			throw new InvalidInputException("A block already exists at location " + gridHorizontalPosition + "/" + gridVerticalPosition + ".");
		}
		//If block does not exist return null
		if(block == null) {
			throw new InvalidInputException("The block does not exist.");
		}
		//Why can't I reference to newBlockAssignment?
		//BlockAssignment newBlockAssignment;
		//System.out.println(newBlockAssignment.getMaxHorizontalGridPosition());
		try {
			BlockAssignment newBlockAssignment = new BlockAssignment(gridHorizontalPosition, gridVerticalPosition, currentLevel, block, game);
		}
		catch (RuntimeException e) {
			error = e.getMessage();
			if (error.equals("GridHorizontalPosition can't be negative or greater than " + game.maxNumberOfHorizontalBlocks())) {
				throw new InvalidInputException("The horizontal position must be between 1 and " + game.maxNumberOfHorizontalBlocks() + ".");
				}
			if (error.equals("GridVerticalPosition can't be negative or greater than " + game.maxNumberOfVerticalBlocks())) {
				throw new InvalidInputException("The vertical position must be between 1 and " + game.maxNumberOfVerticalBlocks() + ".");
				}

		}

	}

	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {
		//Check if the user is an admin
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to move a block.");
		}
		//check if the game exists
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to move a block.");
		}
		//check if the admin created the game ***************question
		if(currentUser instanceof Admin) {
			boolean isAdminCurrentGameCreator = false;
			List<Game> games = ((Admin) currentUser).getGames();

			for(Game instanceOfGame : games) {
				if(instanceOfGame.getName().equals(game.getName())) {
					isAdminCurrentGameCreator = true;
				}
			}
			if(isAdminCurrentGameCreator == false) {
				throw new InvalidInputException("Only the admin who created the game can move a block.");
			}
		}

		Level currentLevel;
		try {
			currentLevel = game.getLevel(level - 1);
		}
		catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}

		BlockAssignment assignment = currentLevel.findBlockAssignment(oldGridHorizontalPosition, oldGridVerticalPosition);

		if (assignment == null) {
			throw new InvalidInputException("A block does not exist at location " + oldGridHorizontalPosition + "/"+ oldGridVerticalPosition + ".");
		}

		if(currentLevel.findBlockAssignment(newGridHorizontalPosition, newGridVerticalPosition) != null) {
			throw new InvalidInputException("A block already exists at location " + newGridHorizontalPosition + "/"+ newGridVerticalPosition + ".");
		}

		try {
			assignment.setGridHorizontalPosition(newGridHorizontalPosition);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException("The horizontal position must be between 1 and " + game.maxNumberOfHorizontalBlocks() + ".");
		}

		try {
			assignment.setGridVerticalPosition(newGridVerticalPosition);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException("The vertical position must be between 1 and " + game.maxNumberOfVerticalBlocks() + ".");
		}
	}
	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		//		//William 01/03
		//FINISHED 03/23
		String error = "";
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to remove a block.");
		}
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to remove a block.");
		}
		Admin admin = game.getAdmin();
		if (admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can remove a block.");
		}
		Level currentLevel;
		try {
			currentLevel = game.getLevel(level - 1);
		}
		catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}
		BlockAssignment assignment = currentLevel.findBlockAssignment(gridHorizontalPosition, gridVerticalPosition);
		if(assignment != null){
			assignment.delete();
		}
	}

	public static void saveGame() throws InvalidInputException {

        if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
            throw new InvalidInputException("Admin privileges are required to save a game.");
        }
        if (Block223Application.getCurrentGame() == null) {
            throw new InvalidInputException("A game must be selected to save it.");
        }
        Game game = Block223Application.getCurrentGame();

        if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
            throw new InvalidInputException("Only the admin who created the game can save it.");
        }
        Block223 block223 = Block223Application.getBlock223();
        try {
            Block223Persistence.save(block223);
        } catch (RuntimeException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {
		if (Block223Application.getCurrentUserRole()!= null) {
			throw new InvalidInputException("Cannot register a new user while a user is logged in.");
		}
		if (playerPassword==null || playerPassword.equals("")) {
			throw new InvalidInputException("The player password needs to be specified.");
		}
		if (playerPassword.equals(adminPassword)) {
			throw new InvalidInputException("The passwords have to be different.");
		}
		if (username==null || username.equals("")) {
			throw new InvalidInputException("The username must be specified.");
		}
		Block223 block223 = Block223Application.getBlock223();
		Player player;
		User user;
		
		try {
			player = new Player(playerPassword, block223);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException("The player password needs to be specified.");
		}
		try {
			user = new User(username, block223, player);
		}
		catch (RuntimeException e) {
			player.delete();
			throw new InvalidInputException("The username has already been taken.");
		}

		if (adminPassword != null && adminPassword != "") {
			Admin admin = new Admin(adminPassword, block223);
			user.addRole(admin);
		}
		Block223Persistence.save(block223);
	}

	public static void login(String username, String password) throws InvalidInputException {
		if (Block223Application.getCurrentUserRole()!= null) {
			throw new InvalidInputException("Cannot login a user while a user is already logged in.");
		}
		Block223Application.resetBlock223();
		User user = User.getWithUsername(username);
		
		if (user==null) {
			throw new InvalidInputException("The username and password do not match.");
		}
		List<UserRole> roles = user.getRoles();
		boolean loginsuccessful= false;
		for (UserRole role : roles) {
			String rolePassword= role.getPassword();
			
			if (rolePassword.equals(password)) {
				Block223Application.setCurrentUserRole(role);
				loginsuccessful=true;
			}
		}
		
		if (!loginsuccessful) {//if no one is still not logged in
			throw new InvalidInputException("The username and password do not match.");
		}

	}

	public static void logout() {
		Block223Application.setCurrentUserRole(null);
	}

	// ****************************
	// Query methods
	// ****************************
	public static List<TOGame> getDesignableGames() throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole admin = Block223Application.getCurrentUserRole();
		// check if the current user is an admin
		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		List<TOGame> result = new ArrayList<TOGame>();
		List<Game> games = block223.getGames();
		// return the list of all games
		for (Game game : games) {
			Admin gameAdmin = game.getAdmin();
			if (gameAdmin.equals(admin) && !game.isPublished()) {
				TOGame to = new TOGame(game.getName(), game.getLevels().size(), 
						game.getNrBlocksPerLevel(), 
						game.getBall().getMinBallSpeedX(), 
						game.getBall().getMinBallSpeedY(), 
						game.getBall().getBallSpeedIncreaseFactor(), 
						game.getPaddle().getMaxPaddleLength(), 
						game.getPaddle().getMinPaddleLength());
				result.add(to);
			}
		}
		return result;
	}

	public static TOGame getCurrentDesignableGame() throws InvalidInputException {
		UserRole currentUser = Block223Application.getCurrentUserRole();
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		Admin admin = game.getAdmin();
		// check if the current user is an admin
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		// check if current game is set
		// compare current user is the admin of the current game
		if (admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		// return current game
		TOGame to = new TOGame(game.getName(), game.getLevels().size(),
				game.getNrBlocksPerLevel(),
				game.getBall().getMinBallSpeedX(),
				game.getBall().getMinBallSpeedY(),
				game.getBall().getBallSpeedIncreaseFactor(),
				game.getPaddle().getMaxPaddleLength(),
				game.getPaddle().getMinPaddleLength());
		return to;
	}

	public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException {
		//William 28/02
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		//check if the admin created the game *****************QUESTION is this (admin) notation fine?
		Admin admin = game.getAdmin();
		if (admin != (Admin) currentUser) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		List<TOBlock> result = new ArrayList<TOBlock>();

		List<Block> blocks = game.getBlocks();
		for(Block block: blocks){
			TOBlock to = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints());
			result.add(to);
		}
		return result;
	}

	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {

		UserRole currentUser = Block223Application.getCurrentUserRole();

		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}

		Game currentGame = Block223Application.getCurrentGame();

		if (currentGame == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}

		Admin admin = currentGame.getAdmin();

		if (admin != (Admin) Block223Application.getCurrentUserRole()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}

		Block block = currentGame.findBlock(id);
		if(block==null) {
			throw new InvalidInputException("The block does not exist.");
		}

		TOBlock to = new TOBlock(block.getId(),
				block.getRed(),
				block.getGreen(),
				block.getBlue(),
				block.getPoints());
		return to;
	}

	public static List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {

		//Check if the user is an admin 
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		//check if the game exists
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}


		//check if admin is creator of the game
		if(currentUser instanceof Admin) {
			boolean isAdminCurrentGameCreator = false;
			List<Game> games = ((Admin) currentUser).getGames();

			for(Game instanceOfGame : games) {
				if(instanceOfGame.getName().equals(game.getName())) {
					isAdminCurrentGameCreator = true;
				}
			}
			if(isAdminCurrentGameCreator == false) {
				throw new InvalidInputException("Only the admin who created the game can access its information.");
			}
		}

		List<TOGridCell> result = new ArrayList<TOGridCell>(); 

		Level currentLevel;
		try {
			currentLevel = game.getLevel(level - 1);
		}
		catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}


		if (currentLevel != null)
		{
			List<BlockAssignment> assignments = currentLevel.getBlockAssignments();
			for (BlockAssignment assignment: assignments) {

				TOGridCell to = new TOGridCell(assignment.getGridHorizontalPosition(), assignment.getGridVerticalPosition(), assignment.getBlock().getId(), assignment.getBlock().getRed(), assignment.getBlock().getGreen(), assignment.getBlock().getBlue(), assignment.getBlock().getPoints());

				result.add(to);
			}
		}
		return result;
	}

	public static TOUserMode getUserMode() {
		UserRole userRole = Block223Application.getCurrentUserRole();
		if (userRole==null) {
			TOUserMode to = new TOUserMode(Mode.None);
			return to;
		}
		if (userRole instanceof Player) {
			TOUserMode to = new TOUserMode(Mode.Play);
			return to;
		}
		if (userRole instanceof Admin) {
			TOUserMode to = new TOUserMode(Mode.Design);
			return to;
		}
		return null;
	}

	// ****************************
	// P1. Start/pause/resume game
	// ****************************

	public static List<TOPlayableGame> getPlayableGames() throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole role = Block223Application.getCurrentUserRole();
		if (!(role instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		List<TOPlayableGame> result = new ArrayList<TOPlayableGame>();
		List<Game> games = block223.getGames();
		for (Game game: games) {
			boolean published = game.isPublished();
			if (published) {
				TOPlayableGame to = new TOPlayableGame(game.getName(), -1, 0);
				result.add(to);
			}
		}
		Player player = (Player) role;
		List<PlayedGame> playedGames = player.getPlayedGames();
		for (PlayedGame game: playedGames) {
			TOPlayableGame to = new TOPlayableGame(game.getGame().getName(), game.getId(), game.getCurrentLevel());
			result.add(to);
		}
		return result;

	}

	public static void selectPlayableGame(String name, int id) throws InvalidInputException {
		PlayedGame pgame = null;
		Block223 block223 = Block223Application.getBlock223();
		Game game = block223.findGame(name);
		UserRole player = Block223Application.getCurrentUserRole();
		if (!(player instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		if (game != null) {
			String username = User.findUsername(player);
			pgame = new PlayedGame(username, game, block223);
			pgame.setPlayer((Player) player);
		}
		else {
			pgame = block223.findPlayableGame(id);
			if (pgame == null) {
				throw new InvalidInputException("The game does not exist.");
			}
			if (player != pgame.getPlayer()) {
				throw new InvalidInputException("Only the player that started a game can continue the game.");
			}
		}
		Block223Application.setCurrentPlayableGame(pgame);
	}
	
	public static void updatePaddlePosition(String userInputs) {
		PlayedGame currentPlayedGame = Block223Application.getCurrentPlayableGame();
		for (int i = 0; i < userInputs.length(); i++) {
			double x = currentPlayedGame.getCurrentPaddleX();
			if (userInputs.charAt(i) == 'l') {
				if (x <= 4) { //since it moves by 5 pixels, if x is at 4, it will be at -1 if it moves left
					continue;
				}
				else {
					currentPlayedGame.setCurrentPaddleX(x + PlayedGame.PADDLE_MOVE_LEFT);
				}
			}
			else if (userInputs.charAt(i) == 'r') {
				if (Game.PLAY_AREA_SIDE-Game.COLUMNS_PADDING - (x+currentPlayedGame.getCurrentPaddleLength()) <= 4) {
					//since it moves by 5 pixels, if x is at 4 pixels from the wall, it will be 1 pixel outside the wall if it moves right
					continue;
				}
				else {
					currentPlayedGame.setCurrentPaddleX(x + PlayedGame.PADDLE_MOVE_RIGHT);
				}
			}
		}
	}
	
	public static void startGame(Block223PlayModeInterface ui) throws InvalidInputException {
		PlayedGame game = Block223Application.getCurrentPlayableGame();
		UserRole player = Block223Application.getCurrentUserRole();
		if (player == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		if (game == null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		Player currentPlayer = game.getPlayer();
		if (player instanceof Admin && currentPlayer != null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		Game currentGame = game.getGame();
		if (player instanceof Admin && player != currentGame.getAdmin()) {
			throw new InvalidInputException("Only the admin of a game can test the game.");
		}
		if (player instanceof Player && currentPlayer == null) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		game.play();
		ui.takeInputs();
		game.resetCurrentPaddleX();
		while (game.getPlayStatus() == PlayStatus.Moving) {
			String userInputs = ui.takeInputs();
			updatePaddlePosition(userInputs);
			game.move();
			if (userInputs.contains(" ")) {
				String inputsBeforeSpace = userInputs.substring(0, (userInputs.indexOf(" ")));
//				System.out.println("Inputs: " + inputsBeforeSpace + " Pause");
				game.pause();
			}
//			System.out.println("Inputs: " + userInputs);
			try {
				Thread.sleep((long) game.getWaitTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ui.refresh();
		}
		if (game.getPlayStatus() == PlayStatus.GameOver) {
			System.out.println("Game Over");
			Block223Application.setCurrentPlayableGame(null);
			Block223 block223 = Block223Application.getBlock223();
			Block223Persistence.save(block223);
		}
		else if (game.getPlayer() != null) {
			game.setBounce(null);
			Block223 block223 = Block223Application.getBlock223();
			Block223Persistence.save(block223);
		}
	}

	public static TOCurrentlyPlayedGame getCurrentPlayableGame() throws InvalidInputException {
		UserRole player = Block223Application.getCurrentUserRole();
		if (player == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		PlayedGame game = Block223Application.getCurrentPlayableGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		Player currentPlayer = game.getPlayer();
		if (player instanceof Admin && currentPlayer != null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		Game currentGame = game.getGame();
		if (player instanceof Admin && player != currentGame.getAdmin()) {
			throw new InvalidInputException("Only the admin of a game can test the game.");
		}
		if (player instanceof Player && currentPlayer == null) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		boolean paused = (pgame.getPlayStatus() == PlayStatus.Ready || pgame.getPlayStatus() == PlayStatus.Paused);
		TOCurrentlyPlayedGame result = new TOCurrentlyPlayedGame(pgame.getGame().getName(), paused, pgame.getScore(),
				pgame.getLives(),pgame.getCurrentLevel(), pgame.getPlayername(), pgame.getCurrentBallX(), pgame.getCurrentBallY(),
				pgame.getCurrentPaddleLength(), pgame.getCurrentPaddleX());
		List<PlayedBlockAssignment> blocks = pgame.getBlocks();
		for (PlayedBlockAssignment pblock: blocks) {
			TOCurrentBlock to = new TOCurrentBlock(pblock.getBlock().getRed(),pblock.getBlock().getGreen(),
					pblock.getBlock().getBlue(),
					pblock.getBlock().getPoints(),
					pblock.getX(),
					pblock.getY(),
					result);
					}
		return result;
		}

	public static TOHallOfFame getHallOfFame(int start, int end) throws InvalidInputException {
		//Verify if user is a player

		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game’s hall of fame.");
		}
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		if (pgame == null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}

		Game game = pgame.getGame();

		TOHallOfFame result = new TOHallOfFame(game.getName());

		if (start < 1) {
			start = 1;
		}

		if (end > game.numberOfHallOfFameEntries()) {
			end = game.numberOfHallOfFameEntries();
			}

		start = game.numberOfHallOfFameEntries() - start;
		end = game.numberOfHallOfFameEntries() - end;

		for (int i = start; i >= end ; i-- ) {  
			TOHallOfFameEntry to = new TOHallOfFameEntry(i+1, game.getHallOfFameEntry(i).getPlayername(),
					game.getHallOfFameEntry(i).getScore(),
					result);
		}
	return result;
	}

	public static TOHallOfFame getHallOfFameWithMostRecentEntry(int numberOfEntries) throws InvalidInputException {
		//Check if user is a player
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game’s hall of fame.");
		}

		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		if (pgame == null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}

		Game game = pgame.getGame();

		TOHallOfFame result = new TOHallOfFame(game.getName());

		HallOfFameEntry mostRecent = game.getMostRecentEntry();
		
		int indexR = game.indexOfHallOfFameEntry(mostRecent);

		int start = indexR + (numberOfEntries/2);

		if (start > game.numberOfHallOfFameEntries() - 1) {
			start =  game.numberOfHallOfFameEntries() - 1;
		}

		int end = start - numberOfEntries + 1;

		if(end < 0) {
			end = 0;
		}
		for(int i = start; i >= end; i--) { //is end included ?
			TOHallOfFameEntry to = new TOHallOfFameEntry(i+1, game.getHallOfFameEntry(i).getPlayername(),
					game.getHallOfFameEntry(i).getScore(), result);
		}

		return result;
	}

	// ****************************
	// P7. Test game
	// ****************************

	public static void testGame(Block223PlayModeInterface ui) throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();
		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to test it.");
		}
		Admin testAdmin = game.getAdmin();
		if (testAdmin != (Admin) admin) {
			throw new InvalidInputException("Only the admin who created the game can test it.");
		}
		if (game.getBlockAssignments().size() < 1) {
			throw new InvalidInputException("There must be at least one block in the game before it can be tested");
		}
		Block223 block223 = Block223Application.getBlock223();
		String username = User.findUsername(admin);
		PlayedGame pgame = new PlayedGame(username, game, block223);
		pgame.setPlayer(null);
		Block223Application.setCurrentPlayableGame(pgame);
		Block223Controller.startGame(ui);
	}

	// ****************************
	// P8. Publish game
	// ****************************

	public static void publishGame () throws InvalidInputException {
		UserRole userRole = Block223Application.getCurrentUserRole();
		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to publish a game.");
		}
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			throw new InvalidInputException("A game must be selected to publish it.");
		}
		if (game.getAdmin() != (Admin) userRole) {
			throw new InvalidInputException("Only the admin who created the game can publish it.");
		}
		if (game.getBlocks().size() < 1) {
			throw new InvalidInputException("At least one block must be defined for a game to be published.");
		}
		game.setPublished(true);
	}

}