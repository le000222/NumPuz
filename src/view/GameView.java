package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.GameController;
import game.GameBasic;
import model.GameModel;

/**
 * Purpose: This class is to store all of the GUI components and anything that relates to the interface of the game
 * File name: GameView.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 4 Dec 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification: [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: GameView.java
 * Method list: getters, setters, getDimension, isTypeNum, gamePanel, menuBar, gridPanel, functionalPanel, compConfig, resetGrid
 * receivedGameConfig, setTextOrInitialGrid, initializedNewGrid, initializedNewGridNum, initializeNewGridText, removeOldGrid, displayTextInput
 * Purpose: This class is to store all of the GUI components and functionalities to implement the game
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameView  extends JFrame {
	/**
	 * Serial UID for GameView
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Fixed height for num grid
	 */
	private final int HEIGHT = 800;
	/**
	 * Fixed width for num grid
	 */
	private final int WIDTH = 980;
	/**
	 * Fixed size for grid of numbers
	 */
	private final static int GRID_SIZE = 700;
	/**
	 * controller object in GameView
	 */
	private GameController controller;
	/**
	 * model object in GameView
	 */
	private GameModel gameModel;
	/**
	 * Panel to store functionPanel and gridPanel
	 */
	private JPanel gamePanel;
	/**
	 * Panel to store function play mode
	 */
	private JPanel functionPanel;
	/**
	 * Panel to store support panel
	 */
	private JPanel support1, support2, support3, support4, support5;
	/**
	 * Menu Bar to the menu bar
	 */
	private JMenuBar menuBar;
	/**
	 * Menu for the help and game menu
	 */
	private JMenu helpMenu, gameMenu;
	/**
	 * GamePanel object for Game tab
	 */
	private JPanel gridPanel;
	/**
	 * Panel to store grid of numbers
	 */
	private JPanel grids;
	/**
	 * Panel to store text input when choose text type
	 */
	private JPanel textInput;
	/**
	 * Labels for text
	 */
	private JLabel textLabel;
	/**
	 * Different menu items
	 */
	private JMenuItem newGame, solutions, exit, about, color;
	/**
	 * Different buttons for different functionalities
	 */
	private JButton save, load, stop, display, rand;
	/**
	 * Radio buttons to design and play game
	 */
	private JRadioButton design, play;
	/**
	 * Drop down for dimension
	 */
	private JComboBox<Integer> dim;
	/**
	 * Drop down for type
	 */
	private JComboBox<String> format;
	/**
	 * Text field for point and timer and text field
	 */
	private JTextField point, timer, textField;
	/**
	 * Text Area to store details of current execution
	 */
	private JTextArea detail;
	/**
	 * grid bag constraints for GridBagLayout layout
	 */
	private GridBagConstraints gbc;
	/**
	 * 2D array of JButton for grids
	 */
	private JButton[][] matrix = null;
	/**
	 * Old dimension
	 */
	private int oldDim;
	/**
	 * check if the game config is received from server or not
	 */
	private boolean isReceivedConfig;
	
	//Getter methods
	/**
	 * getter for game panel
	 * @return game panel stores grids and function menu
	 */
	public JPanel getGame() { return gamePanel; }
	/**
	 * getter for grids panel
	 * @return grids panel stores all buttons
	 */
	public JPanel getGrids() { return grids; }
	/**
	 * getter for text input panel
	 * @return text input panel to store input field
	 */
	public JPanel getTextInput() { return textInput; }
	/**
	 * getter for save configuration button
	 * @return save button
	 */
	public JButton getSave() { return save; }
	/**
	 * getter for load configuration button
	 * @return load button
	 */
	public JButton getLoad() { return load; }
	/**
	 * getter for stop button which stops the game
	 * @return stop button
	 */
	public JButton getStop() { return stop; }
	/**
	 * getter for display button to display the design before playing
	 * @return display button
	 */
	public JButton getDisplay() { return display; }
	/**
	 * getter for display button to display the design before playing
	 * @return display button
	 */
	public JButton getRand() { return rand; }
	/**
	 * getter for selected dimension (3,4,5,6)
	 * @return combobox for dimension
	 */
	public JComboBox<Integer> getDim() { return dim; }
	/**
	 * getter for selected type (number, text)
	 * @return combobox for type 
	 */
	public JComboBox<String> getFormat() { return format; }
	//JMenuItems
	/**
	 * getter for new game menu item
	 * @return new game JMenuItem
	 */
	public JMenuItem getNewGame() { return newGame; }
	/**
	 * getter for solution menu item
	 * @return solution JMenuItem
	 */
	public JMenuItem getSolution() { return solutions; }
	/**
	 * getter for exit menu item
	 * @return exit JMenuItem
	 */
	public JMenuItem getExit() { return exit; }
	/**
	 * getter for about the game menu item
	 * @return about JMenuItem
	 */
	public JMenuItem getAbout() { return about; }
	/**
	 * getter for color menu item
	 * @return color JMenuItem
	 */
	public JMenuItem getColor() { return color; }
	//Mode
	/**
	 * getter for play mode radio button
	 * @return selected play mode radio button
	 */
	public JRadioButton getPlay() { return play; }
	/**
	 * getter for design mode radio button
	 * @return selected design mode radio button
	 */
	public JRadioButton getDesign() { return design; }
	//Text field
	/**
	 * getter for point text field to access text entered by users
	 * @return point text field
	 */
	public JTextField getPoint() { return point; }
	/**
	 * getter for timer text field
	 * @return timer text field
	 */
	public JTextField getTimer() { return timer; }
	/**
	 * getter for text field input for type text
	 * @return input text field
	 */
	public JTextField getTextField() { return textField; }
	/**
	 * getter for detail of current execution in text area
	 * @return text area of detail of current execution
	 */
	public JTextArea getDetail() { return detail; }
	//Matrix
	/**
	 * getter for 2D matrix for buttons
	 * @return 2D matrix of buttons
	 */
	public JButton[][] getMatrix() { return matrix; }
	/**
	 * getter for specific buttons in 2D matrix
	 * @param row row coordinate of matrix
	 * @param col col coordinate of matrix
	 * @return specific button with position of x and y
	 */
	public JButton getMatrixButton(int row, int col) { return matrix[row][col]; }
	
	//Setter for controller and model methods
	/**
	 * set controller in GameView
	 * @param controller controller from GameApp
	 */
	public void setController(GameController controller) { this.controller = controller; }
	/**
	 * set model in GameView
	 * @param model model from GameApp
	 */
	public void setModel(GameModel model) { this.gameModel = model; }
	
	/**
	 * Overloading constructor
	 * @param model model object
	 */
	public GameView(GameModel model, boolean isReceivedConfig) {
		this.gameModel = model;
		this.isReceivedConfig = isReceivedConfig;
	}
	
	public void runStandAloneView() {
		this.add(gamePanel());
		if (isReceivedConfig) {
			receivedGameConfig();
		}
		this.setJMenuBar(menuBar());
		design.doClick();
		this.setTitle("NumPuz");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("resources/GameIcon.png")).getImage());
		this.setResizable(false);
		this.setVisible(!isReceivedConfig);
		this.setSize(WIDTH, HEIGHT);
	}
	
	/**
	 * create game panel
	 * @return game panel
	 */
	public JPanel gamePanel() {
		//add content to main JPanel
		gamePanel = new JPanel(new BorderLayout());
		gamePanel.add(functionPanel(), BorderLayout.EAST);
		gamePanel.add(gridPanel(), BorderLayout.WEST);
		return gamePanel;
	}
	
	/**
	 * Store all components that make up the menu bar panel (game, help)
	 * @return JPanel of menu bar JPanel
	 */
	public JMenuBar menuBar() {
		// Game Menu
		menuBar = new JMenuBar();
		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		// New Game Menu Item
		newGame = new JMenuItem("New Game", new ImageIcon(getClass().getClassLoader().getResource("resources/IconNewGame.png")));
		newGame.setActionCommand("New Game");
		newGame.setMnemonic('N');
		newGame.addActionListener(controller);
		// Solution Menu Item
		solutions = new JMenuItem("Solution", new ImageIcon(getClass().getClassLoader().getResource("resources/IconSol.png")));
		solutions.setActionCommand("Solution");
		solutions.setMnemonic('S');
		solutions.addActionListener(controller);
		// Exit Menu Item
		exit = new JMenuItem("Exit", new ImageIcon(getClass().getClassLoader().getResource("resources/IconExit.png")));
		exit.setActionCommand("Exit");
		exit.setMnemonic('E');
		exit.addActionListener(controller);
		
		gameMenu.add(newGame);
		gameMenu.add(solutions);
		gameMenu.add(exit);
		gameMenu.addActionListener(controller);
		
		// Help Menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		// About Menu Item
		about = new JMenuItem("About", new ImageIcon(getClass().getClassLoader().getResource("resources/IconAbout.png")));
		about.setActionCommand("About");
		about.setMnemonic('A');
		about.addActionListener(controller);
		// Color Menu Item
		color = new JMenuItem("Color", new ImageIcon(getClass().getClassLoader().getResource("resources/IconColor.png")));
		color.setActionCommand("Color");
		color.setMnemonic('C');
		color.addActionListener(controller);
		
		helpMenu.add(about);
		helpMenu.add(color);	
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		
		return menuBar;
	}
	
	/**
	 * Grid Panel that stores the 2D matrix buttons
	 * @return gridPanel grid panel
	 */
	private JPanel gridPanel() {
		gridPanel = new JPanel(new BorderLayout());
		resetGrid(GameBasic.DEFAULT_DIM, 0);
		return gridPanel;
	}
	
	/**
	 * Store all components that make up the function panel
	 * @return JPanel all components that are contained in function panel
	 */
	private JPanel functionPanel() {
		// functions panel initialization
		functionPanel = new JPanel();
		functionPanel.setLayout(new GridBagLayout());
		functionPanel.setPreferredSize(new Dimension(255, HEIGHT));
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 5);
		functionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//Image
		JLabel pic = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/Icon.jpg")));
		
		// support 1: mode
		support1 = new JPanel();
		support1.setLayout(new GridBagLayout());
		design = new JRadioButton("Design");
		design.addActionListener(controller);
		play = new JRadioButton("Play");
		play.addActionListener(controller);
		ButtonGroup group = new ButtonGroup();
		group.add(design);
		group.add(play);
		compConfig(new JLabel("<html><font size=\"4\"><b>MODE:</b></font></html>"), support1, 0, 0, 1, gbc.insets);
		compConfig(design, support1, 1, 0, 1, gbc.insets);
		compConfig(play, support1, 2, 0, 1, gbc.insets);
		
		// support 2: dimension and type 
		support2 = new JPanel();
		support2.setLayout(new GridBagLayout());
		Integer dimen[] = {3,4,5,6};
		String input[] = {"Number", "Text"};
		dim = new JComboBox<Integer>(dimen);
		dim.addActionListener(controller);
		format = new JComboBox<String>(input);
		format.addActionListener(controller);
		dim.setSelectedIndex(0);
		format.setSelectedIndex(0);
		compConfig(new JLabel("<html><font size=\"4\"><b>DIMENSION:</b></font></html>"), support2, 0, 0, 1, gbc.insets);
		compConfig(dim, support2, 1, 0, 1, gbc.insets);
		compConfig(new JLabel("<html><font size=\"4\"><b>TYPE:</b></font></html>"), support2, 0, 1, 1, gbc.insets);
		compConfig(format, support2, 1, 1, 1, gbc.insets);
		
	    // support 3: display grids
		support3 = new JPanel();
		support3.setLayout(new GridLayout());
		display = new JButton("DISPLAY");
		rand = new JButton("RANDOM");
		display.addActionListener(controller);
		rand.addActionListener(controller);
		support3.add(display);
		support3.add(rand);
	
		// detail of current execution
		detail = new JTextArea(12, 10);
		detail.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(detail);
		
		// support 4: point and timer
		support4 = new JPanel();
		support4.setLayout(new GridBagLayout());
		point = new JTextField(5);
		timer = new JTextField(5);
		point.setText("0");
		timer.setText("0");
		point.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		timer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		compConfig(new JLabel("POINTS"), support4, 0, 0, 1, gbc.insets);
		compConfig(point, support4, 1, 0, 1, gbc.insets);
		compConfig(new JLabel("TIMER"), support4, 2, 0, 1, gbc.insets);
		compConfig(timer, support4, 3, 0, 1, gbc.insets);
		
		// support 5: save and load previous/ new game
	    support5 = new JPanel();
	    support5.setLayout(new GridLayout());
	    save = new JButton("SAVE");
	    save.addActionListener(controller);
	    load = new JButton("LOAD");
	    load.addActionListener(controller);
	    support5.add(save);
	    support5.add(load);
	    
	    // stop button
	    stop = new JButton("STOP");
	    stop.addActionListener(controller);
		
	    // add supports to functions panel
		gbc.insets = new Insets(5, 10, 5, 5);
		compConfig(pic, functionPanel, 0, 0, 3, gbc.insets);
		compConfig(support1, functionPanel, 0, 4, 3, gbc.insets);
		compConfig(support2, functionPanel, 0, 5, 2, gbc.insets);
		compConfig(support3, functionPanel, 0, 6, 2, gbc.insets);
		compConfig(scrollPane, functionPanel, 0, 7, 2, gbc.insets);
		compConfig(support4, functionPanel, 0, 9, 1, gbc.insets);
		compConfig(support5, functionPanel, 0, 10, 1, gbc.insets);
		gbc.insets = new Insets(10, 20, 0, 10);
		compConfig(stop, functionPanel, 0, 11, 1, gbc.insets);
		
		// initialize text input field
		textInput = new JPanel();
		textLabel = new JLabel("Text Input: ");
		textField = new JTextField(40);
		textField.setText("");
		
		return functionPanel;
	}
	
	/**
	 * Set up size and position for a component
	 * @param comp Component needs to be attached to Panel
	 * @param panel parent panel
	 * @param x x-coordinate of Component
	 * @param y y-coordinate of Component
	 * @param w width of the grid
	 * @param insets Insets for Component
	 */
	private void compConfig(Component comp, JPanel panel, int x, int y, int w, Insets insets) {
	    gbc = new GridBagConstraints();
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.gridx = x;
	    gbc.gridy = y;
	    gbc.gridwidth = w;
	    gbc.insets = insets;
	    panel.add(comp, gbc);
	}
	
	/**
	 * function runs when user receive config from server and load the received game config
	 */
	public void receivedGameConfig() {
		String[] gameConfig = GameModel.gameString.split(GameBasic.PROTOCOL_SPACE);
		int gameDim = Integer.parseInt(gameConfig[0]);
		String gameFormat = gameConfig[1];
		int indexType = 0;
		if (gameFormat.equals("Number")) { indexType = 0; } 
		else { indexType = 1; }
		String gameString = gameConfig[2];
		dim.setSelectedIndex(gameDim-3);
		format.setSelectedIndex(indexType);
		String[] gameStringArray = gameString.split(",");
		detail.setText(gameString);
		gameModel.handleReceiveConfig(gameDim, gameFormat, gameStringArray);
		removeOldGrid(getDimension());
		resetGrid(gameDim, 2);
		controller.setArrayShuffle(true);
	}
	
	/**
	 * Reset whole grid depending on state, calling initializeNewGrid() method
	 * @param newDim new dimension selected by users
	 * @param state 0: reset empty buttons, 1: reset solution, 2: reset shuffled buttons
	 */
	public void resetGrid(int newDim, int state) {
		// initialize new matrix and grid panel
		matrix = new JButton[newDim][newDim];
		grids = new JPanel(new GridLayout(newDim, newDim));
		grids.setPreferredSize(new Dimension(GRID_SIZE, GRID_SIZE));
		
		if (state == GameBasic.STATE_EMPTY) {
			oldDim = newDim;
			setTextOrInitialGrid();
		}
		else {
			initializeNewGrid(newDim, state);
		}
		gridPanel.add(grids, BorderLayout.NORTH);
	}
	
	/**
	 * Initialize new grid (numbers, text) depending on state
	 * @param newDim new dimension selected by users
	 * @param state 1: reset solution, 2: reset shuffled buttons
	 */
	private void initializeNewGrid(int newDim, int state) {
		Integer[] tempArrayNum = null;
		Character[] tempArrayText = null;
		//Numbers
		if (isTypeNum()) {
			if (state == GameBasic.STATE_SOLUTION) {
				tempArrayNum = gameModel.getSolNum();
			} else if (state == GameBasic.STATE_SHUFFLE) {
				tempArrayNum = gameModel.getShuffleNum();
			}
			initializeNewGridNum(newDim, tempArrayNum);
		} 
		//Text
		else {
			if (state == GameBasic.STATE_SOLUTION) {
				tempArrayText = gameModel.getSolText();
			} else if (state == GameBasic.STATE_SHUFFLE) {
				tempArrayText = gameModel.getShuffleText();
			}
			initializeNewGridText(newDim, tempArrayText);
		}
	}
		
	/**
	 * Initialize new grid for text
	 * @param newDim new dimension selected by users
	 * @param tempArray temporary shuffled text array received by calling function
	 */
	private void initializeNewGridText(int newDim, Character[] tempArray) {
		// if color is not chosen, white will be used
		if (GameController.bgColor == null) {
			GameController.bgColor = GameBasic.DEFAULT_COLOR;
		}
		for (int y = 0; y < newDim; y++) {
			for (int x = 0; x < newDim; x++) {
				matrix[y][x] = new JButton("" + tempArray[y*newDim+x]);
				matrix[y][x].setFont(new Font("Dialog", Font.BOLD, 30));
				matrix[y][x].setBackground(GameController.bgColor);
				matrix[y][x].putClientProperty("y", y);
				matrix[y][x].putClientProperty("x", x);
				matrix[y][x].setOpaque(true);
				matrix[y][x].setBorderPainted(true);
				matrix[y][x].setForeground(GameBasic.ZERO_COLOR);
				matrix[y][x].setBorder(BorderFactory.createLineBorder(GameBasic.ZERO_COLOR));
				matrix[y][x].addMouseListener(controller);
				grids.add(matrix[y][x]);
			}
		}
		for (int i = 0; i < newDim; i++) {
			for (int j = 0; j < newDim; j++) {
				if(matrix[i][j].getText().equalsIgnoreCase("0")) {
					matrix[i][j].setBackground(GameBasic.ZERO_COLOR);
					matrix[i][j].setForeground(GameBasic.ZERO_COLOR);
					matrix[i][j].setBorderPainted(false);
				}
			}
		}
	}
	
	/**
	 * Initialize new grid for numbers
	 * @param newDim new dimension selected by users
	 * @param tempArray temporary shuffled number array received by calling function
	 */
	private void initializeNewGridNum(int newDim, Integer[] tempArray) {
		// if color is not chosen, white will be used
		if (GameController.bgColor == null) {
			GameController.bgColor = GameBasic.DEFAULT_COLOR;
		}
		for (int y = 0; y < newDim; y++) {
			for (int x = 0; x < newDim; x++) {
				matrix[y][x] = new JButton("" + tempArray[y*newDim+x]);
				matrix[y][x].setFont(new Font("Dialog", Font.BOLD, 30));
				matrix[y][x].setBackground(GameController.bgColor);
				matrix[y][x].putClientProperty("y", y);
				matrix[y][x].putClientProperty("x", x);
				matrix[y][x].setOpaque(true);
				matrix[y][x].setBorderPainted(true);
				matrix[y][x].setForeground(GameBasic.ZERO_COLOR);
				matrix[y][x].setBorder(BorderFactory.createLineBorder(GameController.bgColor));
				matrix[y][x].addMouseListener(controller);
				grids.add(matrix[y][x]);
			}
		}
		for (int i = 0; i < newDim; i++) {
			for (int j = 0; j < newDim; j++) {
				if(matrix[i][j].getText().equalsIgnoreCase("0")) {
					matrix[i][j].setBackground(GameBasic.ZERO_COLOR);
					matrix[i][j].setForeground(GameBasic.ZERO_COLOR);
					matrix[i][j].setBorderPainted(false);
				}
			}
		}
	}
	
	/**
	 * Set empty grid for the initial load
	 */
	private void setTextOrInitialGrid() {
		if (GameController.bgColor == null) {
			GameController.bgColor = GameBasic.DEFAULT_COLOR;
		}
		for (int i = 0; i < oldDim; i++) {
			for (int j = 0; j < oldDim; j++) {
				matrix[i][j] = new JButton("");
				matrix[i][j].setBackground(GameController.bgColor);
				grids.add(matrix[i][j]);
			}
		}
	}
	
	/**
	 * Display text input when text type is selected
	 */
	public void displayTextInput() {
		textInput.add(textLabel);
		textInput.add(textField);
		gridPanel.add(textInput, BorderLayout.SOUTH);
	}
	
	/**
	 * remove old grid and text input
	 * @param newDim new dimension selected by users
	 */
	public void removeOldGrid(int newDim) {
		//Remove old matrix
		for (int i = 0; i < oldDim; i++) {
			for (int j = 0; j < oldDim; j++) {
				grids.remove(matrix[i][j]);
				gridPanel.remove(grids);
			}
		}
		//Remove old text input
		textInput.remove(textLabel);
		textInput.remove(textField);
		gridPanel.remove(textInput);
		gridPanel.revalidate();
		gridPanel.repaint();
		oldDim = newDim;
	}
	
	/**
	 * Get dimension and size of grid
	 * @return size size of dimension (= dimension^2)
	 */
	public int getDimension() {
		int dimension = (int) dim.getSelectedItem();
		return dimension;
	}
	
	/**
	 * Get true false for type of the grid
	 * @return true for number type and false for text type
	 */
	public boolean isTypeNum() {
		boolean isNum = format.getSelectedItem().equals("Number") ? true : false;
		return isNum;
	}
}
