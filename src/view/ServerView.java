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

public class ServerView extends JFrame {

	/**
	 * Serialize UID for ServerView
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Panel to store functionPanel and gridPanel
	 */
	private JFrame serverFrame;
	/**
	 * Panel to store functionPanel and gridPanel
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
	 * Text field for point and timer and text field
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
	public ServerController getServerController() { return serverController; }
	/**
	 * Setter for serverContoller
	 * @param controller
	 */
	public void setServerController(ServerController controller) {
		serverController = controller;
	}
	
	//Getter
	public JFrame getServerFrame() { return serverFrame; }
	public JButton getStart() { return start; }
	public JButton getResult() { return result; }
	public JButton getEnd() { return end; }
	public JTextField getPort() { return port; }
	public JTextArea getDetail() { return detail; }
	// Add getter for finalize later
	
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
