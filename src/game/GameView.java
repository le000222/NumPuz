package game;

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
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.Border;

/**
 * Purpose: This class is to store all of the GUI components and anything that relates to the interface of the game
 * File name: GameView.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 2 Oct 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification: [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: GameView.java
 * Method list: getDim(), getFormat(), resetGrid(), shuffleNum(), shuffleText(), 
 * displayTextInput(), setGrid(), compConfig(), menuPanel(), helpPanel(), gamePanel(), tabPanel()
 * Constants list: HEIGHT, WIDTH, GRID_SIZE, tabs, menu, grids, help, game, textInput,
 * save, load, start, reset, show, hide, design play, dim, type, point, timer, detail,
 * shuffle[], matrix[][], border, dimension, isInitialLoad
 * Class list: StartGame, Design
 * Purpose: This class is to store all of the GUI components
 * and functionalities to implement the game
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameView  extends JFrame {
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
	 * GameController in GameView
	 */
	private GameController controller;
	/**
	 * GameModel in GameView
	 */
	private GameModel model;
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
	 * Different buttons for different functionalities
	 */
	private JLabel textLabel;
	/**
	 * Different menu items
	 */
	private JMenuItem newGame, solutions, exit, about, color;
	/**
	 * Different buttons for different functionalities
	 */
	private JButton save, load, stop, display, rand, setText;
	/**
	 * Buttons to design and play game
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
	 * Drop down for type
	 */
	private JComboBox<String> level;
	/**
	 * Text field for point and timer
	 */
	private JTextField point, timer, textField;
	/**
	 * Text Area to store details of current execution
	 */
	private JTextArea detail;
	/**
	 * border object to set border for JPanel
	 */
	private Border border = BorderFactory.createLineBorder(Color.BLACK); // for functions box
	/**
	 * border object to set border for JPanel
	 */
	private GridBagConstraints gbc;
	/**
	 * Shuffle array to store integer that have been shuffled
	 */
	protected Integer[] shuffle;
	/**
	 * 2D array of JButton for grids
	 */
	protected JButton[][] matrix = null;
	/**
	 * Dimension of the grid
	 */
	protected int seconds = 0;
	/**
	 * Check if timer is running
	 */
	protected boolean timerRunning = false;
	/**
	 * Timer
	 */
	protected Timer gameTimer = new Timer();
	/**
	 * Timer Task
	 */
	protected TimerTask timerTask;
	/**
	 * Old dimension
	 */
	private int oldDim;
	
	//Getter methods
	public JPanel getGame() { return gamePanel; }
	public JPanel getFunction() { return functionPanel; }
	public JPanel getGrids() { return grids; }
	public JPanel getTextInput() { return textInput; }
	public JButton getSave() { return save; }
	public JButton getLoad() { return load; }
	public JButton getStop() { return stop; }
	public JButton getDisplay() { return display; }
	public JButton getRand() { return rand; }
	public JMenuItem getNewGame() { return newGame; }
	public JMenuItem getSolution() { return solutions; }
	public JMenuItem getExit() { return exit; }
	public JMenuItem getAbout() { return about; }
	public JMenuItem getColor() { return color; }
	public JButton getSetText() { return setText; }
	public JRadioButton getPlay() { return play; }
	public JRadioButton getDesign() { return design; }
	public JComboBox<Integer> getDim() { return dim; }
	public JComboBox<String> getFormat() { return format; }
	public JComboBox<String> getLevel() { return level; }
	public JTextField getPoint() { return point; }
	public JTextField getTimer() { return timer; }
	public JTextField getTextField() { return textField; }
	public JTextArea getDetail() { return detail; }
	public JButton[][] getMatrix() { return matrix; }
	public JButton getMatrixButton(int row, int col) { return matrix[row][col]; }
	
	//Setter methods
	public void setPoint(JTextField point) { this.point = point; }
	public void setTextField(JTextField textField) { this.textField = textField; }
	public void setController(GameController controller) { this.controller = controller; }
	public void setModel(GameModel model) { this.model = model; }
	
	public GameView(GameModel model) {
		this.model = model;
	}
	
	public void initComponents() {
		this.add(gamePanel());
		this.setJMenuBar(menuBar());
		this.setTitle("NumPuz");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("resources/GameIcon.png")).getImage());
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(WIDTH, HEIGHT);
	}
	
	public JPanel gamePanel() {
		//add content to main JPanel
		gamePanel = new JPanel(new BorderLayout());
		gamePanel.add(functionPlay(), BorderLayout.EAST);
		gamePanel.add(gridPanel(), BorderLayout.WEST);
		return gamePanel;
	}
	
	public void startTimer() {
		seconds = 0;
		// Timer task
		timerTask = new TimerTask() {
			public void run() {
				seconds++;
				try {
					resetTimer(seconds);
				} catch (Exception e) {
					System.err.println(e); // Eventual errors when initializing text					
				}
			}
		};
		// Timer schedule
		if(!timerRunning) {
			try {
				gameTimer.scheduleAtFixedRate(timerTask, 0, 1000);
				timerRunning = true;
			} catch(Exception e) {
				System.err.println(e); // Eventual errors when game finished
			}
		}
	}
	
	public void timerPause() {
		timerTask.cancel();
		timerRunning = false;
	}
	
	public void resetTimer(int seconds) {
		timer.setText(Integer.toString(seconds));
	}
	
	/**
	 * Store all components that make up the menu bar panel (game, help)
	 * @return JPanel of all tabs
	 */
	public JMenuBar menuBar() {
		menuBar = new JMenuBar();
		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		
		newGame = new JMenuItem("New Game", new ImageIcon(getClass().getClassLoader().getResource("resources/IconNewGame.png")));
		newGame.setActionCommand("New Game");
		newGame.setMnemonic('N');
		newGame.addActionListener(controller);
		
		solutions = new JMenuItem("Solution", new ImageIcon(getClass().getClassLoader().getResource("resources/IconSol.png")));
		solutions.setActionCommand("Solution");
		solutions.setMnemonic('S');
		solutions.addActionListener(controller);
		
		exit = new JMenuItem("Exit", new ImageIcon(getClass().getClassLoader().getResource("resources/IconExit.png")));
		exit.setActionCommand("Exit");
		exit.setMnemonic('E');
		exit.addActionListener(controller);
		
		gameMenu.add(newGame);
		gameMenu.add(solutions);
		gameMenu.add(exit);
		gameMenu.addActionListener(controller);
		
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		about = new JMenuItem("About", new ImageIcon(getClass().getClassLoader().getResource("resources/IconAbout.png")));
		about.setActionCommand("About");
		about.setMnemonic('A');
		about.addActionListener(controller);
		
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
	
	private JPanel gridPanel() {
		gridPanel = new JPanel(new BorderLayout());
		resetGrid(GameApp.DEFAULT_DIM, 0); //add grids to grid panel
		return gridPanel;
	}
	
	/**
	 * Store all components that make up the function panel
	 * @return JPanel of all tabs
	 */
	private JPanel functionPlay() {
		// functions panel initialization
		functionPanel = new JPanel();
		functionPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 5);
		functionPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
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
		Integer dimen[] = {3,4,5,6,7,8};
		String input[] = {"Number", "Text"};
		String levels[] = {"Classic", "Easy", "Medium", "Hard"};
		dim = new JComboBox<Integer>(dimen);
		dim.addActionListener(controller);
		format = new JComboBox<String>(input);
		format.addActionListener(controller);
		level = new JComboBox<String>(levels);
		level.addActionListener(controller);
		dim.setSelectedIndex(0);
		format.setSelectedIndex(0);
		level.setSelectedIndex(0);
		compConfig(new JLabel("<html><font size=\"4\"><b>DIMENSION:</b></font></html>"), support2, 0, 0, 1, gbc.insets);
		compConfig(dim, support2, 1, 0, 1, gbc.insets);
		compConfig(new JLabel("<html><font size=\"4\"><b>TYPE:</b></font></html>"), support2, 0, 1, 1, gbc.insets);
		compConfig(format, support2, 1, 1, 1, gbc.insets);
		compConfig(new JLabel("<html><font size=\"4\"><b>LEVEL:</b></font></html>"), support2, 0, 2, 1, gbc.insets);
		compConfig(level, support2, 1, 2, 1, gbc.insets);
		
	    // support 3: show and hide solution
		support3 = new JPanel();
		support3.setLayout(new GridLayout());
		display = new JButton("DISPLAY");
		display.addActionListener(controller);
		rand = new JButton("RANDOM");
		rand.addActionListener(controller);
		support3.add(display);
		support3.add(rand);
	
		// detail of current execution
		detail = new JTextArea(12, 10);
		detail.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(detail);
		detail.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
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
		gbc.insets = new Insets(5, 5, 5, 5);
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
		setText = new JButton("Set");
		setText.addActionListener(controller);
		
		return functionPanel;
	}
	
	/**
	 * Set up size and position for a component
	 * @param comp Component needs to be attached to Panel
	 * @param panel Panel
	 * @param x x-coordinate of Component
	 * @param y y-coordinate of Component
	 * @param w width of the grid
	 * @param insets Insets for Component
	 * @return void
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
	 * Reset whole grid when Start button is clicked
	 */
	public void resetGrid(int newDim, int state) {
		//Set the whole grid again
		matrix = new JButton[newDim][newDim];
		grids = new JPanel(new GridLayout(newDim, newDim));
		grids.setPreferredSize(new Dimension(GRID_SIZE, GRID_SIZE));
		
		if (state == 0) {
			oldDim = newDim;
			setTextOrInitialGrid();
		}
		else {
			initializeNewGrid(newDim, state);
		}
		gridPanel.add(grids, BorderLayout.NORTH);
	}
	
	/**
	 * After remove the old grids, initialize new grid depends on model shuffle number
	 */
	private void initializeNewGrid(int newDim, int state) {
		Integer[] tempArrayNum;
		Character[] tempArrayText;
		if (isTypeNum()) {
			if (state == 1) {
				System.out.println("Print solution");
				tempArrayNum = model.getSolNum();
			}
			else {
				System.out.println("Calling initializeNewGridNum");
				tempArrayNum = model.getShuffleNum();
			}
			initializeNewGridNum(newDim, tempArrayNum);
		} else {
			if (state == 1) {
				System.out.println("Print solution");
				tempArrayText = model.getSolText();
			}
			else {
				System.out.println("Calling initializeNewGridText");
				tempArrayText = model.getShuffleText();
			}
			initializeNewGridText(newDim, tempArrayText);
		}
	}
		
	private void initializeNewGridText(int newDim, Character[] tempArray) {
		System.out.println("initializeNewGridText");
		if (GameController.bgColor == null) {
			GameController.bgColor = GameApp.DEFAULT_COLOR;
		}
		for (int y = 0; y < newDim; y++) {
			for (int x = 0; x < newDim; x++) {
				matrix[y][x] = new JButton("" + tempArray[y*newDim+x]);
				matrix[y][x].setFont(new Font("Dialog", Font.BOLD, 30));
				matrix[y][x].setBackground(GameController.bgColor);
				matrix[y][x].putClientProperty("y", y);
				matrix[y][x].putClientProperty("x", x);
				matrix[y][x].setOpaque(true);
				matrix[y][x].setBorderPainted(false);
				matrix[y][x].setForeground(Color.BLACK);
				matrix[y][x].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				matrix[y][x].addMouseListener(controller);
				grids.add(matrix[y][x]);
			}
		}
		for (int i = 0; i < newDim; i++) {
			for (int j = 0; j < newDim; j++) {
				if(matrix[i][j].getText().equalsIgnoreCase("0"))
				matrix[i][j].setBackground(Color.BLACK);
				matrix[i][j].setForeground(Color.BLACK);
			}
		}
	}
	
	private void initializeNewGridNum(int newDim, Integer[] tempArray) {
		System.out.println("initializeNewGridNum");
		if (GameController.bgColor == null) {
			GameController.bgColor = GameApp.DEFAULT_COLOR;
		}
		for (int y = 0; y < newDim; y++) {
			for (int x = 0; x < newDim; x++) {
				matrix[y][x] = new JButton("" + tempArray[y*newDim+x]);
				matrix[y][x].setFont(new Font("Dialog", Font.BOLD, 30));
				matrix[y][x].setBackground(GameController.bgColor);
				matrix[y][x].putClientProperty("y", y);
				matrix[y][x].putClientProperty("x", x);
				matrix[y][x].setOpaque(true);
				matrix[y][x].setBorderPainted(false);
				matrix[y][x].setForeground(GameApp.ZERO_COLOR);
				matrix[y][x].setBorder(BorderFactory.createLineBorder(GameApp.ZERO_COLOR));
				matrix[y][x].addMouseListener(controller);
				grids.add(matrix[y][x]);
			}
		}
		for (int i = 0; i < newDim; i++) {
			for (int j = 0; j < newDim; j++) {
				if(matrix[i][j].getText().equalsIgnoreCase("0")) {
					matrix[i][j].setBackground(GameApp.ZERO_COLOR);
					matrix[i][j].setForeground(GameApp.ZERO_COLOR);
				}
			}
		}
	}
	
	public void removeOldGrid(int newDim) {
		//Remove old matrix
		for (int i = 0; i < oldDim; i++) {
			for (int j = 0; j < oldDim; j++) {
				grids.remove(matrix[i][j]);
				gridPanel.remove(grids);
			}
		}
		//Remove old text input if text input
		System.out.println("Remove old Grid");
		textInput.remove(textLabel);
		textInput.remove(textField);
		textInput.remove(setText);
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
	 * @param void
	 * @return true for number type and false for text type
	 */
	public boolean isTypeNum() {
		boolean isNum = format.getSelectedItem().equals("Number") ? true : false;
		return isNum;
	}
	
	/**
	 * Set empty grid for the initial load
	 */
	private void setTextOrInitialGrid() {
		if (GameController.bgColor == null) {
			GameController.bgColor = GameApp.DEFAULT_COLOR;
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
	 * @return void
	 */
	public void displayTextInput() {
		textInput.add(textLabel);
		textInput.add(textField);
		textInput.add(setText);
		gridPanel.add(textInput, BorderLayout.SOUTH);
	}
}
