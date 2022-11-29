package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.ServerReply;
import model.User;
import view.ServerView;

public class ServerController implements ActionListener {
	private ServerView serverView;
	private ServerSocket serverSocket;
	private ServerController serverController;
	private Socket clientSocket;
	private int nclient = 0;
	private int nclients = 0;
	private String port;
	private ArrayList<User> usersList = new ArrayList<>();
	private ArrayList<ServerReply> clientsList = new ArrayList<>();
	private boolean runningServer = false;
	
	public ArrayList<ServerReply> getServerReply() { return clientsList; }
	public ArrayList<User> getUser() { return usersList; }
	
	public ServerController() {}
	
	public ServerController(ServerView serverView) {
		serverController = new ServerController();
		serverController.serverView = serverView;
		serverView.setServerController(serverController);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
	}
	
	public void displayExecution(String message) {
		serverView.getDetail().append(message + "\n");
	}
	
	public void displayErrorMessage(String errorMessage, String title) {
		JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}
	
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
						nclient += 1;
						nclients += 1;
						displayExecution("Current number of clients: " + nclients);
						displayExecution("Connecting " + clientSocket.getInetAddress() + " at port " + clientSocket.getPort());
					
						User user = new User();
						user.setId(nclient);
						usersList.add(user);
						
						displayExecution("New task [" + (nclient-1) + "] created...");
						ServerReply serverReply = new ServerReply(clientSocket, nclient, nclients);
						clientsList.add(serverReply);
						serverReply.setServerController(serverController);
						serverReply.start(); // invoke run() in ServerReply
					}
				} catch (Exception e) {
					System.out.println(e);
					displayExecution(e.getMessage());
					displayErrorMessage(e.getMessage(), "Unable to start server");
				}
			}
		};
		
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
	
	private void endServer() {
		runningServer = false;
		clientsList.clear();
		serverView.getServerFrame().dispose();
		System.exit(0);
	}
	
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
}
