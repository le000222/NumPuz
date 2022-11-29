package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import game.GameBasic;
import game.GameMVC;
import model.GameModel;
import view.ClientView;

public class ClientController implements ActionListener {

	private ClientView clientView = null;
	private GameMVC gameMVC = null;
	private Socket socket = null;
	private BufferedReader inputServer;
	private PrintStream outputClient;
	private int port;
	private String server;
	private String clientID;
	private boolean clientConnected = false;
	private boolean isReceivedConfig = false;
	
	public ClientController() {}
	
	public ClientController(ClientView clientView) {
		ClientController clientController = new ClientController();
		clientController.clientView = clientView;
		clientView.setClientController(clientController);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clientView.getConnect()) {
			if (clientView.getUserName().getText().equals("") || clientView.getServer().getText().equals("") || clientView.getPort().getText().equals("")) {
				displayErrorMessage("Missing information", "Error");
				return;
			}
			//connect client with server
			displayExecution("Connect Button");
			clientConnect();
		} 
		else if (e.getSource() == clientView.getEnd()) {
			//end connections with server
			displayExecution("End Button");
			clientEnd();
		}
		else if (e.getSource() == clientView.getSendConfig()) {
			//send current config to server
			displayExecution("Send Game Config Button");
			clientSendGame();
		}
		else if (e.getSource() == clientView.getReceiveConfig()) {
			//receive available config from server
			displayExecution("Receive Game Config Button");
			clientReceiveGame();
		}
		else if (e.getSource() == clientView.getSendData()) {
			//send username, point and timer to server
			displayExecution("Send Data Button");
			clientSendData();
		}
		else if (e.getSource() == clientView.getPlay()) {
			//play the game
			displayExecution("Play Button");
			clientPlayGame();
		}
	}
	
	private void displayExecution(String message) {
		clientView.setDetail(message);
	}
	
	private void displayErrorMessage(String errorMessage, String title) {
		JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}
	
	private void clientConnect() {
		displayExecution("Connecting...................");
		displayExecution("Connecting with server on " + server + " at port " + port);
		port = Integer.parseInt(clientView.getPort().getText());
		server = clientView.getServer().getText();
		
		try {
			socket = new Socket(server, port);
			inputServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputClient = new PrintStream(socket.getOutputStream());
			clientConnected = true;
			clientID = inputServer.readLine(); // read clientID from server
			String consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_CONNECT;
			outputClient.println(consoleData);
			outputClient.flush();
			displayExecution("Client [" + clientID + "]: " + consoleData);
			String serverData = inputServer.readLine();
			displayExecution("Server: " + serverData);
			// Enable and disable buttons
			clientView.getConnect().setEnabled(false);
			clientView.getSendConfig().setEnabled(true);
			clientView.getReceiveConfig().setEnabled(true);
			clientView.getSendData().setEnabled(true);
			clientView.getPlay().setEnabled(true);
		} catch (Exception e) {
			displayExecution(e.getMessage());
			displayErrorMessage(e.getMessage(), "Invalid Connection");
		}
	}
	
	private void clientEnd() {
		if (clientConnected) {
			try {
				String consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_END;
				outputClient.println(consoleData);
				outputClient.flush();
				displayExecution("Client[" + clientID + "]" + consoleData);
				outputClient.close();
				inputServer.close();
				socket.close();
			} catch (IOException e) {
				displayExecution(e.getMessage());
				displayErrorMessage(e.getMessage(), "Error");
			}
		}
		displayExecution("Disconnecting.........");
		displayExecution("Disconnecting with server on " + server + " at port " + port);
		clientView.dispose();
	}
	
	private void clientPlayGame() {
		if (isReceivedConfig) {
			gameMVC.gameView.setVisible(true);
		}
		else {
			gameMVC = new GameMVC(!GameBasic.RECEIVED_CONFIG);
		}
	}
	
	private void clientSendData() {
		displayExecution("Sending data from client [" + clientID + "].........");
		try {
			clientID = inputServer.readLine();
			String userName = clientView.getUserName().getText();
			int points = 0, timer = 0;
			if (gameMVC != null && gameMVC.gameView != null) {
				points = Integer.parseInt(gameMVC.gameView.getPoint().getText());
				timer = Integer.parseInt(gameMVC.gameView.getTimer().getText());
			}
			String consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_SENDDATA + GameBasic.PROTOCOL_SEPARATOR 
					+ userName + GameBasic.PROTOCOL_SPACE + points + GameBasic.PROTOCOL_SPACE + timer;
			outputClient.println(consoleData);
			outputClient.flush();
			displayExecution("Client[" + clientID + "]: " + consoleData);
			String serverData = inputServer.readLine();
			displayExecution("Server: " + serverData);
		} catch (IOException e) {
			displayExecution(e.getMessage());
			displayErrorMessage(e.getMessage(), "Error");
		}
	}
	
	private void clientSendGame() {
		displayExecution("Sending Game Config from client[" + clientID + "]..............");
		String consoleData = "";
		String gameString = "";
		try {
			clientID = inputServer.readLine();
			if (gameMVC == null) {
				consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_SENDGAME 
						+ GameBasic.PROTOCOL_SEPARATOR + GameBasic.DEFAULT_GAMECONFIG;
			}
			else {
				for (int i = 0; i < gameMVC.gameModel.getShuffleNum().length; i++) {
					gameString += gameMVC.gameModel.getShuffleNum()[i] + ",";
				}
				consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_SENDGAME + GameBasic.PROTOCOL_SEPARATOR 
						+ gameMVC.gameView.getDimension() + GameBasic.PROTOCOL_SPACE + (gameMVC.gameView.isTypeNum() ? "Number" : "Type") 
						+ GameBasic.PROTOCOL_SPACE + gameString;
			}
			outputClient.println(consoleData);
			outputClient.flush();
			displayExecution("Client[" + clientID + "]:" + consoleData);
			String serverData = inputServer.readLine();
			displayExecution("Server: " + serverData);
		} catch (Exception e) {
			displayExecution(e.getMessage());
			displayErrorMessage(e.getMessage(), "Error");
		}
	}
	
	private void clientReceiveGame() {
		displayExecution("Receiving Game Config from client[" + clientID + "]..............");
		String consoleData = "";
		String serverData = "";
		isReceivedConfig = true;
		
		try {
			clientID = inputServer.readLine();
			consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_RECEIVEGAME;
			outputClient.println(consoleData);
			outputClient.flush();
			displayExecution("Client[" + clientID + "]:" + consoleData);
			serverData = inputServer.readLine();
			displayExecution("Server: " + serverData);
			String[] gameData = serverData.split(GameBasic.PROTOCOL_SEPARATOR);
			String gameString = gameData[2];
			GameModel.gameString = gameString;
			System.out.println("GameString = " + gameString);
			gameMVC = new GameMVC(GameBasic.RECEIVED_CONFIG);
			
		} catch (Exception e) {
			e.printStackTrace();
			displayExecution(e.getMessage());
			displayErrorMessage(e.getMessage(), "Error");
		}
	}
}
