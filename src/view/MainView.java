package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;

public class MainView extends JFrame {

	/**
	 * Serialize UID for MainView
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Panel to store functionPanel and gridPanel
	 */
	private JPanel initialGUI;
	/**
	 * Different buttons for different functionalities
	 */
	private JButton run, cancel;
	/**
	 * Drop down for gameType
	 */
	private JComboBox<String> gameType;
	/**
	 * Setter for controller from MainController
	 */
	private MainController controller;
	
	/**
	 * getter for run button to run selected game type
	 * @return run button
	 */
	public JButton getRun() { return run; }
	/**
	 * getter for cancel button to exit the game
	 * @return cancel button
	 */
	public JButton getCancel() { return cancel; }
	/**
	 * getter for selected game type (standalone, server, client)
	 * @return combobox for game type 
	 */
	public JComboBox<String> getGameType() { return gameType; }
	
	public void setController(MainController controller) { this.controller = controller; }

	/**
	 * Default constructor
	 */
	public MainView() {}
	
	/**
	 * Init functions to set components after controller is initiated
	 */
	public void runMainView() {
		this.add(initialGUI());
		this.setTitle("NumPuz");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("resources/GameIcon.png")).getImage());
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(300, 150);
	}
	
	private JPanel initialGUI() {
		initialGUI = new JPanel();
		initialGUI.setLayout(null);
		JLabel label = new JLabel("<html><font size=\"4\"><b>Game Type:</b></font></html>");
		label.setBounds(40, 20, 150, 30);
		initialGUI.add(label);
		String[] gameArray = {"Select a type", "[A] Standalone", "[B] Server", "[C] Client"};
		gameType = new JComboBox<String>(gameArray);
		gameType.setSelectedIndex(0);
		gameType.setBounds(140, 20, 100, 30);
		initialGUI.add(gameType);
		run = new JButton("Run");
		run.setBounds(70, 60, 70, 30);
		cancel = new JButton("Cancel");
		cancel.setBounds(150, 60, 70, 30);
		initialGUI.add(run);
		initialGUI.add(cancel);
		
		run.addActionListener(controller);
		cancel.addActionListener(controller);
		return initialGUI;
	}
}
