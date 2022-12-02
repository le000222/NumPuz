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

/**
 * Purpose: This class is to store controller for client view
 * File name: ClientController.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 4 Dec 2022
 * Prof: Paulo Sousa
 * Assignment: A32
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification: [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: ClientController.java
 * Method list: clientConnect, clientEnd, clientSendGame, clientReceiveGame, clientSendData, clientPlay
 * Purpose: This class is to store controller for client view
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class ClientController implements ActionListener {

	/**
	 * clientView object in ClientView
	 */
	private ClientView clientView = null;
	/**
	 * gameMVC object in GameMVC
	 */
	private GameMVC gameMVC = null;
	/**
	 * socket object in Socket
	 */
	private Socket socket = null;
	/**
	 * inputServer object in BufferedReader: receive message from server to client
	 */
	private BufferedReader inputServer;
	/**
	 * outputClient object in PrintStream: send message from client to server
	 */
	private PrintStream outputClient;
	/**
	 * port number
	 */
	private int port;
	/**
	 * server's name
	 */
	private String server;
	/**
	 * client ID as string
	 */
	private String clientID;
	/**
	 * check if client is connected to server or not
	 */
	private boolean clientConnected = false;
	/**
	 * check if client receive game config from server or not
	 */
	private boolean isReceivedConfig = false;
	
	/**
	 * default constructor
	 */
	public ClientController() {}
	/**
	 * overloading constructor
	 * @param clientView object from ClientView
	 */
	public ClientController(ClientView clientView) {
		ClientController clientController = new ClientController();
		clientController.clientView = clientView;
		clientView.setClientController(clientController);
	}

	/**
	 * override method
	 */
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
	
	/**
	 * connect client with server
	 */
	private void clientConnect() {
		port = Integer.parseInt(clientView.getPort().getText());
		server = clientView.getServer().getText();
		displayExecution("Connecting...................");
		displayExecution("Connecting with server on " + server + " at port " + port);
		
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
	
	/**
	 * disconnect client from server
	 */
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
		if (gameMVC != null) {
			gameMVC.gameView.dispose();
		}
		clientView.dispose();
		System.exit(0);
	}
	
	/**
	 * allow client to play game
	 */
	private void clientPlayGame() {
		if (isReceivedConfig) {
			gameMVC.gameView.setVisible(true);
		}
		else {
			gameMVC = new GameMVC(!GameBasic.RECEIVED_CONFIG);
		}
	}
	
	/**
	 * allow client to send data to server
	 */
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
	
	/**
	 * allow client to send game config to server
	 */
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
				if (gameMVC.gameView.getFormat().getSelectedIndex() == 0) {
					for (int i = 0; i < gameMVC.gameModel.getShuffleNum().length; i++) {
						gameString += gameMVC.gameModel.getShuffleNum()[i] + ",";
					}
				}
				else {
					for (int i = 0; i < gameMVC.gameModel.getShuffleText().length; i++) {
						gameString += gameMVC.gameModel.getShuffleText()[i] + ",";
					}
				}
			consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_SENDGAME + GameBasic.PROTOCOL_SEPARATOR 
					+ gameMVC.gameView.getDimension() + GameBasic.PROTOCOL_SPACE + (gameMVC.gameView.isTypeNum() ? "Number" : "Text") 
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
	
	/**
	 * allow client to receive game config from server or any client that sends game config
	 */
	private void clientReceiveGame() {
		displayExecution("Receiving Game Config from client[" + clientID + "]..............");
		isReceivedConfig = true;
		
		try {
			clientID = inputServer.readLine();
			String consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_RECEIVEGAME;
			outputClient.println(consoleData);
			outputClient.flush();
			displayExecution("Client[" + clientID + "]:" + consoleData);
			String serverData = inputServer.readLine();
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
	
	/**
	 * display executions continuously after the others
	 * @param message message to display
	 */
	private void displayExecution(String message) {
		clientView.getDetail().append(message + "\n");
	}
	
	/**
	 * display error message
	 * @param errorMessage error message from exceptions
	 * @param title title for the error popup window
	 */
	private void displayErrorMessage(String errorMessage, String title) {
		JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}
}
