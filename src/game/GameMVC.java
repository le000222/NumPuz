package game;

import controller.GameController;
import model.GameModel;
import view.GameView;

/**
 * Purpose: This class is the model for standalone game
 * File name: GameMVC.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 4 Dec 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: GameMVC.java
 * Purpose: This class is the model for standalone game
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameMVC {

	/**
	 * gameModel object for GameModel
	 */
	public GameModel gameModel = null;
	/**
	 * gameView object for GameView
	 */
	public GameView gameView = null;
	
	/**
	 * overloading constructor
	 * @param isReceivedConfig check if game loaded is standalone or received config from server
	 */
	public GameMVC(boolean isReceivedConfig) {
		gameModel = new GameModel();
		gameView = new GameView(gameModel, isReceivedConfig);
		gameView.setModel(gameModel);
		new GameController(gameView, gameModel);
		gameView.runStandAloneView();
	}
}
