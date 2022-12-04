package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import controller.ServerController;
import game.GameBasic;

/**
* Purpose: This class is to manage client tasks that server creates
* File name: ClientTask.java
* Course: CST8221 JAP, Lab Section: 301
* Date: 4 Dec 2022
* Prof: Paulo Sousa
* Assignment: A32
* Compiler: Eclipse IDE - 2021-09 (4.21.0)
* Identification: [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
*/

/**
* Class Name: ClientTask.java
* Method list: run, findClientTaskByClientID
* Purpose: This class is to manage client tasks that server creates
* @author Ngoc Phuong Khanh Le, Dan McCue
* @version 3
* @see game
* @since 4.21.0
*/
public class ClientTask extends Thread {
	/**
	 * Socket variable.
	 */
	private Socket socket;
	/**
	 * String for data.
	 */
	private String clientID, clientData, protocol;
	/**
	 * outputServer: send message from server to client
	 */
	private PrintStream outputServer = null;
	/**
	 * inputClient: receive message from client to server
	 */
	private BufferedReader inputClient = null;
	/**
	 * serverController from ServerController
	 */
	private ServerController serverController;
	/**
	 * check if a client task is running or not
	 */
	private boolean runningClient = true;

	//Setter
	/**
	 * setter for serverController from ServerController in ClientTask
	 * @param serverController
	 */
	public void setServerController(ServerController serverController) { this.serverController = serverController; }
	
	/**
	 * Default constructor.
	 * @param s Socket each client socket
	 * @param nclient Number of client
	 */
	public ClientTask(Socket socket, int nclient) {
		this.socket = socket;
		clientID = Integer.toString(nclient);
		
		try {
			outputServer = new PrintStream(socket.getOutputStream());
			inputClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			serverController.displayExecution(e.getMessage());
			serverController.displayErrorMessage(e.getMessage(), "Unable to start server");
		}
	}

	/**
	 * Run method.
	 */
	public void run() {
		int numRecevieMsg = 0;
		int numSendMsg = 0;
		
		while (runningClient) {
			try {
				int index = -1;
				String consoleData = "";
				outputServer.println(clientID);
				clientData = inputClient.readLine();
				serverController.displayExecution("Received Message[" + ++numRecevieMsg + "]: " + clientData);
				String[] dataSplit = clientData.split(GameBasic.PROTOCOL_SEPARATOR);
				clientID = dataSplit[0];
				protocol = dataSplit[1];
				switch (protocol) {
				case GameBasic.PROTOCOL_CONNECT:
					String name = dataSplit[2];
					index = findClientTaskByClientID(clientID);
					if (index != -1) {
						User user = serverController.getUser().get(index);
						user.setPoints(0);
						user.setTimer(0);
						user.setUserName(name);
					}
					serverController.displayExecution("Connecting to client[" + clientID + "]..............");
					consoleData = "client[" + clientID + "]: connected";
					outputServer.println(consoleData);
					outputServer.flush();
					serverController.displayExecution("Sent Message[" + ++numSendMsg + "]: " + consoleData);
					break;
				case GameBasic.PROTOCOL_SENDGAME: // send game config
					serverController.displayExecution("Send Game Config to Server................");
					String gameConfig = dataSplit[2];
					consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_SENDGAME + GameBasic.PROTOCOL_SEPARATOR + gameConfig;
					outputServer.println(consoleData);
					outputServer.flush();
					serverController.displayExecution("Sent Message[" + ++numSendMsg + "]: " + consoleData);
					GameModel.gameString = gameConfig;
					break;
				case GameBasic.PROTOCOL_RECEIVEGAME: // receive game config
					serverController.displayExecution("Receive Game Config from Server...............");
					consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_RECEIVEGAME + GameBasic.PROTOCOL_SEPARATOR + GameModel.gameString;
					outputServer.println(consoleData);
					outputServer.flush();
					serverController.displayExecution("Sent Message[" + ++numSendMsg + "]: " + consoleData);
					break;
				case GameBasic.PROTOCOL_SENDDATA: // send data config
					serverController.displayExecution("Data sent from client[" + clientID + "].............");
					String[] userData = dataSplit[2].split(GameBasic.PROTOCOL_HYPHEN);
					String userName = userData[0];
					int points = Integer.parseInt(userData[1]);
					int timer = Integer.parseInt(userData[2]);
					//Updating user
					index = findClientTaskByClientID(clientID);
					if (index != -1) {
						User user = serverController.getUser().get(index);
						user.setPoints(user.getPoints() + points);
						user.setTimer(timer);
						user.setUserName(userName);
						consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_SENDDATA + GameBasic.PROTOCOL_SEPARATOR 
								+ userName + GameBasic.PROTOCOL_HYPHEN + points + GameBasic.PROTOCOL_HYPHEN + timer;
						outputServer.println(consoleData);
						outputServer.flush();
						serverController.displayExecution("Sent Message[" + ++numSendMsg + "]: " + consoleData);
					}
					break;
				case GameBasic.PROTOCOL_END: // end connection
					serverController.displayExecution("Disconnecting " + socket.getInetAddress() + "...................");
					serverController.displayExecution("Ending client: " + clientID);
					index = findClientTaskByClientID(clientID);
					if (index != -1) {
						serverController.getClientTask().remove(index);
						serverController.setNclients(serverController.getNclients()-1);
						runningClient = false;
						serverController.displayExecution("Current client number: " + serverController.getNclients());
						if (serverController.getNclients() == 0 && serverController.getIsFinalized()) {
							serverController.displayExecution("Ending server..............");
							socket.close();
							System.exit(0);
						}
						break;
					}
				}
			} catch (IOException e) {
				serverController.displayExecution(e.getMessage());
				serverController.displayErrorMessage(e.getMessage(), "Unable to start server");
			}
		}
	}
	
	/**
	 * find client task by client ID in clientTask array list
	 * @param clientID client's ID
	 * @return index index of client task in clientTask array list if found, -1 if not found
	 */
	private int findClientTaskByClientID(String clientID) {
		int index = 0;
		for (ClientTask task : serverController.getClientTask()) {
			if (task.clientID == clientID) {
				return index;
			}
			index++;
		}
		return -1;
	}
}
