package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import controller.ServerController;
import game.GameBasic;

public class ServerReply extends Thread {
	/**
	 * Socket variable.
	 */
	private Socket socket;
	/**
	 * Integers for client and positions.
	 */
	private int nclients;
	/**
	 * String for data.
	 */
	private String clientID, clientData, protocol;
	private PrintStream outputServer = null;
	private BufferedReader inputClient = null;
	private ServerController serverController;
	private boolean runningClient = true;

	public void setServerController(ServerController serverController) { this.serverController = serverController; }
	
	/**
	 * Default constructor.
	 * @param s Socket
	 * @param nclient Number of client.
	 */
	public ServerReply(Socket socket, int nclient, int nclients) {
		this.socket = socket;
		clientID = Integer.toString(nclients);
		this.nclients = nclients;
		
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
					String[] userData = dataSplit[2].split(GameBasic.PROTOCOL_SPACE);
					String userName = userData[0];
					int points = Integer.parseInt(userData[1]);
					int timer = Integer.parseInt(userData[2]);
					//Updating user
					index = findServerReplyByClientID(clientID);
					if (index != -1) {
						User user = serverController.getUser().get(index);
						user.setPoints(points);
						user.setTimer(timer);
						user.setUserName(userName);
						consoleData = clientID + GameBasic.PROTOCOL_SEPARATOR + GameBasic.PROTOCOL_SENDDATA + GameBasic.PROTOCOL_SEPARATOR 
								+ userName + GameBasic.PROTOCOL_SPACE + points + GameBasic.PROTOCOL_SPACE + timer + GameBasic.PROTOCOL_SPACE;
						outputServer.println(consoleData);
						outputServer.flush();
						serverController.displayExecution("Sent Message[" + ++numSendMsg + "]: " + consoleData);
					}
					break;
				case GameBasic.PROTOCOL_END: // end connection
					serverController.displayExecution("Disconnecting " + socket.getInetAddress() + "...................");
					serverController.displayExecution("Ending client: " + clientID);
					index = findServerReplyByClientID(clientID);
					if (index != -1) {
						serverController.getServerReply().remove(index);
						nclients--;
						runningClient = false;
						serverController.displayExecution("Current client number: " + nclients);
//						if (nclients == 0) {
//							serverController.displayExecution("Ending server...");
//							socket.close();
//							System.exit(0);
//						}
						break;
					}
				}
			} catch (IOException e) {
				serverController.displayExecution(e.getMessage());
				serverController.displayErrorMessage(e.getMessage(), "Unable to start server");
			}
		}
	}
	
	private int findServerReplyByClientID(String clientID) {
		int index = 0;
		for (ServerReply reply : serverController.getServerReply()) {
			if (reply.clientID == clientID) {
				return index;
			}
			index++;
		}
		return -1;
	}
}
