package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.ClientTask;
import model.User;
import view.ServerView;

/**
 * Purpose: This class is to store functionalities for server controller
 * File name: ServerController.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 4 Dec 2022
 * Prof: Paulo Sousa
 * Assignment: A32
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification: [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: ServerController.java
 * Method list: getters, setters, startServer, endServer, showResults, displayExecution, displayErrorMessage
 * Purpose: This class is to store all functionalities for ServerController
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class ServerController implements ActionListener {
	/**
	 * serverView object in ServerController
	 */
	private ServerView serverView;
	/**
	 * serverSocket object in ServerController
	 */
	private ServerSocket serverSocket;
	/**
	 * serverController object in ServerController
	 */
	private ServerController serverController;
	/**
	 * clientSocket object in ServerController
	 */
	private Socket clientSocket;
	/**
	 * id for each client
	 */
	private int nclient = 0;
	/**
	 * total number of clients
	 */
	private int nclients = 0;
	/**
	 * port of server
	 */
	private String port;
	/**
	 * userLists array lists to store all users
	 */
	private ArrayList<User> usersList = new ArrayList<>();
	/**
	 * clientsLists array lists to store all clients that implements tasks on server
	 */
	private ArrayList<ClientTask> clientTasks = new ArrayList<>();
	/**
	 * check if server is running or not
	 */
	private boolean runningServer = false;
	/**
	 * check if server is finalized or not
	 */
	private boolean isFinalized = false;
	
	//Getters
	/**
	 * Getter for getting each clientTask array list
	 * @return clientTask
	 */
	public ArrayList<ClientTask> getClientTask() { return clientTasks; }
	/**
	 * Getter for getting usersList array list
	 * @return
	 */
	public ArrayList<User> getUser() { return usersList; }
	/**
	 * Getter for finalize check box
	 * @return isFinalized true: check box is checked, false: check box isnt checked
	 */
	public boolean getIsFinalized() { return isFinalized; }
	/**
	 * Getters for total number of clients
	 * @return nclients num of clients
	 */
	public int getNclients() { return nclients; }
	/**
	 * Setter for total number of clients
	 * @param nclients num of clients
	 */
	public void setNclients(int nclients) { this.nclients = nclients; }
	
	/**
	 * Default constructor
	 */
	public ServerController() {}
	
	/**
	 * Overloading constructor
	 * @param serverView serverView object from ServerView
	 */
	public ServerController(ServerView serverView) {
		serverController = new ServerController();
		serverController.serverView = serverView;
		serverView.setServerController(serverController);
	}

	/**
	 * overloading method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// keep track of serverController to get access to right serverController while transfering between classes
		serverController = serverView.getServerController();
		port = serverView.getPort().getText();
		if (e.getSource() == serverView.getStart()) {
			//start the server
			displayExecution("Start Button Pressed ...");
			serverView.getStart().setEnabled(false);
			serverView.getResult().setEnabled(true);
			runningServer = true;
			startServer();
		} 
		else if (e.getSource() == serverView.getEnd()) {
			//end server and disconnect with all clients
			displayExecution("End Button Pressed ...");
			endServer();
		}
		else if (e.getSource() == serverView.getResult()) {
			//print all clients that has connected to server
			displayExecution("Results Button Pressed ...");
			showResults();
		}
		else if (serverView.getFinalize().isSelected()) {
			isFinalized = true;
		}
	}
	
	/**
	 * start server
	 */
	private void startServer() {
		Runnable server = new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(Integer.parseInt(port));
					displayExecution("Starting Server Thread on port " + port);
					displayExecution("Waiting for the clients to connect...");
					
					while (runningServer) {
						clientSocket = serverSocket.accept();
						nclient++;
						nclients++;
						displayExecution("Current number of clients: " + nclients);
						displayExecution("Connecting " + clientSocket.getInetAddress() + " at port " + clientSocket.getPort());
					
						User user = new User();
						user.setId(nclient);
						usersList.add(user);
						
						displayExecution("New task [" + (nclient-1) + "] created...");
						ClientTask serverReply = new ClientTask(clientSocket, nclient);
						clientTasks.add(serverReply);
						serverReply.setServerController(serverController);
						serverReply.start(); // invoke run() in ServerReply
					}
				} catch (Exception e) {
					displayExecution(e.getMessage());
					displayErrorMessage(e.getMessage(), "Unable to start server");
				}
			}
		};
		
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
	
	/**
	 * end server
	 */
	private void endServer() {
		runningServer = false;
		clientTasks.clear();
		serverView.getServerFrame().dispose();
		System.exit(0);
	}
	
	/**
	 * print list of user
	 */
	private void showResults() {
		StringBuilder sb = new StringBuilder("");
		if (usersList.size() > 0) {
			sb.append("Game Results:\n");
			for (User user : usersList) {
				sb.append("User[" + user.getId() + "]: ");
				sb.append(user.getUserName());
				sb.append("; Points: " + user.getPoints());
				sb.append("; Timer: " + user.getTimer() + "\n");
			}
		} else {
			sb.append("Empty List.");
		}
		JOptionPane.showMessageDialog(serverView, sb, "Users List", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * display executions continuously after the others
	 * @param message message to display
	 */
	public void displayExecution(String message) {
		serverView.getDetail().append(message + "\n");
	}
	
	/**
	 * display error message
	 * @param errorMessage error message from exceptions
	 * @param title title for the error popup window
	 */
	public void displayErrorMessage(String errorMessage, String title) {
		JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}
}
