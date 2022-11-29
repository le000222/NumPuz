package game;

import controller.GameController;
import model.GameModel;
import view.GameView;

public class GameMVC {

	public GameModel gameModel = null;
	public GameView gameView = null;
	
	public GameMVC(boolean isReceivedConfig) {
		gameModel = new GameModel();
		gameView = new GameView(gameModel, isReceivedConfig);
		gameView.setModel(gameModel);
		new GameController(gameView, gameModel);
		gameView.runStandAloneView();
	}
}
