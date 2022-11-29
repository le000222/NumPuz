package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import game.GameBasic;
import game.GameMVC;
import view.ClientView;
import view.MainView;
import view.ServerView;

public class MainController implements ActionListener {
	
	private MainView mainView;
	
	public MainController() {}
	
	public MainController(MainView view) {
		MainController controller = new MainController();
		controller.mainView = view;
		view.setController(controller);
	}
	
	/**
	 * overloading method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainView.getRun()) {
			int gameType = mainView.getGameType().getSelectedIndex();
			switch (gameType) {
			case 1:
				new GameMVC(!GameBasic.RECEIVED_CONFIG);
				break;
			case 2:
				mainView.dispose();
				ServerView serverView = new ServerView();
				new ServerController(serverView);
				serverView.runServerView();
				break;
			case 3:
				mainView.dispose();
				ClientView clientView = new ClientView();
				new ClientController(clientView);
				clientView.runClientView();
				break;
			default:
				JOptionPane.showMessageDialog(null, "Invalid input. Please select a type!", "", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (e.getSource() == mainView.getCancel()){
			System.exit(0);
		}
	}
}
