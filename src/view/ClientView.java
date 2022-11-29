package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import controller.ClientController;
import game.GameBasic;

public class ClientView extends JFrame {

	/**
	 * Serialize UID for ServerView
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Panel to store functionPanel and gridPanel
	 */
	private JPanel clientView;
	/**
	 * Different buttons for different functionalities
	 */
	private JButton connect, end, sendGame, receiveGame, sendData, play;
	/**
	 * Text field for point and timer and text field
	 */
	private JTextField userName, server, port;
	/**
	 * Text Area to store details of current execution
	 */
	private JTextArea detail;
	/**
	 * ServerController for ServerView
	 */
	private ClientController clientController;
	/**
	 * Setter for serverContoller
	 * @param controller
	 */
	public void setClientController(ClientController controller) {
		clientController = controller;
	}
	
	//Getter
	public JButton getConnect() { return connect; }
	public JButton getEnd() { return end; }
	public JButton getSendConfig() { return sendGame; }
	public JButton getReceiveConfig() { return receiveGame; }
	public JButton getSendData() { return sendData; }
	public JButton getPlay() { return play; }
	public JTextField getPort() { return port; }
	public JTextField getUserName() { return userName; }
	public JTextField getServer() { return server; }
	public JTextArea getDetail() { return detail; }
	public void setDetail(String message) { detail.append(message + " \n"); }
	
	/**
	 * Default constructor
	 */
	public ClientView() {}
	
	/**
	 * Init functions to set components after controller is initiated
	 */
	public void runClientView() {
		this.add(clientView());
		this.setTitle("Client");
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(900, 700);
	}
	
	private JPanel clientView() {
		clientView = new JPanel();
		clientView.setLayout(null);
		
		JLabel icon = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/ClientIcon.jpg")));
		icon.setBounds(30, 0, 825, 330);
		
		JLabel userLabel = new JLabel("<html><font size=\"4\"><b>User:</b></font></html>");
		userLabel.setBounds(110, 320, 35, 30);
		userName = new JTextField(100);
		userName.setBounds(150, 323, 100, 25);
		userName.setText(GameBasic.DEFAULT_USER);
		
		JLabel serverLabel = new JLabel("<html><font size=\"4\"><b>Server:</b></font></html>");
		serverLabel.setBounds(260, 320, 50, 30);
		server = new JTextField(100);
		server.setBounds(315, 323, 100, 25);
		server.setText(GameBasic.DEFAULT_SERVER);
		
		JLabel portLabel = new JLabel("<html><font size=\"4\"><b>Port:</b></font></html>");
		portLabel.setBounds(420, 320, 35, 30);
		port = new JTextField(100);
		port.setBounds(465, 323, 100, 25);
		port.setText(GameBasic.DEFAULT_PORT);
		
		connect = new JButton("Connect");
		connect.setBounds(590, 320, 80, 30);
		end = new JButton("End");
		end.setBounds(675, 320, 70, 30);
		
		sendGame = new JButton("Send Game Config");
		sendGame.setBounds(180, 355, 150, 30);
		receiveGame = new JButton("Receive Game Config");
		receiveGame.setBounds(335, 355, 150, 30);
		sendData = new JButton("Send Data");
		sendData.setBounds(490, 355, 100, 30);
		play = new JButton("Play");
		play.setBounds(595, 355, 70, 30);
		
		sendGame.setEnabled(false);
		receiveGame.setEnabled(false);
		sendData.setEnabled(false);
		play.setEnabled(false);
		
		detail = new JTextArea(750, 280);
		detail.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(detail);
		scrollPane.setBounds(60, 395, 750, 250);
		
		clientView.add(icon);
		clientView.add(userLabel);
		clientView.add(serverLabel);
		clientView.add(portLabel);
		clientView.add(userName);
		clientView.add(server);
		clientView.add(port);
		clientView.add(connect);
		clientView.add(end);
		clientView.add(sendGame);
		clientView.add(receiveGame);
		clientView.add(sendData);
		clientView.add(play);
		clientView.add(scrollPane);
		
		connect.addActionListener(clientController);
		end.addActionListener(clientController);
		sendGame.addActionListener(clientController);
		receiveGame.addActionListener(clientController);
		sendData.addActionListener(clientController);
		play.addActionListener(clientController);
		
		return clientView;
	}
}
