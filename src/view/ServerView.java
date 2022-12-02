package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import controller.ServerController;
import game.GameBasic;

/**
 * Purpose: This class is to store GUI for server side
 * File name: ServerView.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 4 Dec 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: ServerView.java
 * Method list: getters, setters, runServerView, serverView
 * Purpose: This class is store GUI for server side
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class ServerView extends JFrame {

	/**
	 * Serialize UID for ServerView
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Frame for serverView
	 */
	private JFrame serverFrame;
	/**
	 * Panel to store serverView
	 */
	private JPanel serverView;
	/**
	 * Different buttons for different functionalities
	 */
	private JButton start, result, end;
	/**
	 * Check box for finalize
	 */
	private JCheckBox finalize;
	/**
	 * Text field for server's port
	 */
	private JTextField port;
	/**
	 * Text Area to store details of current execution
	 */
	private JTextArea detail;
	/**
	 * ServerController for ServerView
	 */
	private ServerController serverController;
	/**
	 * Getter for serverController
	 * @return serverController received from serverController
	 */
	public ServerController getServerController() { return serverController; }
	/**
	 * Setter for serverContoller
	 * @param controller serverController object
	 */
	public void setServerController(ServerController controller) {
		serverController = controller;
	}
	
	//Getter
	/**
	 * Getters for server frame
	 * @return serverFrame server's frame
	 */
	public JFrame getServerFrame() { return serverFrame; }
	/**
	 * Getters for start button
	 * @return start start button
	 */
	public JButton getStart() { return start; }
	/**
	 * Getters for result button
	 * @return result result button
	 */
	public JButton getResult() { return result; }
	/**
	 * Getters for end button
	 * @return end end button
	 */
	public JButton getEnd() { return end; }
	/**
	 * Getters for finalize check box
	 * @return finalize finalize check box
	 */
	public JCheckBox getFinalize() { return finalize; }
	/**
	 * Getters for port text field
	 * @return port port text field
	 */
	public JTextField getPort() { return port; }
	/**
	 * Getters for detail text area
	 * @return detail detail text area for current execution
	 */
	public JTextArea getDetail() { return detail; }
	
	/**
	 * Default constructor
	 */
	public ServerView() {}
	
	/**
	 * Init functions to set components after controller is initiated
	 */
	public void runServerView() {
		serverFrame = new JFrame();
		serverFrame.add(serverView());
		serverFrame.setTitle("Server");
		serverFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		serverFrame.setResizable(false);
		serverFrame.setVisible(true);
		serverFrame.setSize(900, 720);
	}
	
	/**
	 * server's view
	 * @return panel for server view
	 */
	private JPanel serverView() {
		serverView = new JPanel();
		serverView.setLayout(null);
		
		JLabel icon = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/ServerIcon.jpg")));
		icon.setBounds(30, 0, 825, 338);
		
		JLabel label = new JLabel("<html><font size=\"4\"><b>Port:</b></font></html>");
		label.setBounds(180, 350, 35, 30);
		port = new JTextField(100);
		port.setBounds(220, 350, 100, 25);
		port.setText(GameBasic.DEFAULT_PORT);
		
		result = new JButton("Result");
		result.setBounds(370, 350, 70, 30);
		result.setEnabled(false);
		finalize = new JCheckBox("Finalize");
		finalize.setBounds(440, 350, 70, 30);
		start = new JButton("Start");
		start.setBounds(530, 350, 70, 30);
		end = new JButton("End");
		end.setBounds(600, 350, 70, 30);
		
		detail = new JTextArea(750, 280);
		detail.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(detail);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(60, 390, 750, 280);
		
		serverView.add(icon);
		serverView.add(label);
		serverView.add(port);
		serverView.add(result);
		serverView.add(finalize);
		serverView.add(start);
		serverView.add(end);
		serverView.add(scrollPane);
		
		start.addActionListener(serverController);
		end.addActionListener(serverController);
		result.addActionListener(serverController);
		finalize.addActionListener(serverController);
		
		return serverView;
	}
}
