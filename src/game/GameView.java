package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

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
import javax.swing.border.Border;
import javax.swing.event.MenuListener;

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
	 * Panel to store functionPanel and gridPanel
	 */
	private JPanel game;
	/**
	 * Panel to store function play mode
	 */
	private JPanel functionPanel;
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
	private JMenuItem newGame, instructions, exit, about, color;
	/**
	 * Different buttons for different functionalities
	 */
	private JButton save, load, start, reset, show, hide, setText;
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
	private GridBagConstraints gbc;
	private GameModel model;
	
	
	
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
	private int oldDim;
	
	//Getter methods
	public JPanel getGrids() { return grids; }
	public JPanel getTextInput() { return textInput; }
	public JButton getSave() { return save; }
	public JButton getLoad() { return load; }
	public JButton getStart() { return start; }
	public JButton getReset() { return reset; }
	public JButton getShow() { return show; }
	public JButton getHide() { return hide; }
	public JMenuItem getNewGame() { return newGame; }
	public JMenuItem getInstructions() { return instructions; }
	public JMenuItem getExit() { return exit; }
	public JMenuItem getAbout() { return about; }
	public JMenuItem getColor() { return color; }
	public JButton getSetText() { return setText; }
	public JRadioButton getPlay() { return play; }
	public JRadioButton getDesign() { return design; }
	public JComboBox<Integer> getDim() { return dim; }
	public JComboBox<String> getFormat() { return format; }
	public JTextField getPoint() { return point; }
	public JTextField getTimer() { return timer; }
	public JTextField getTextField() { return textField; }
	public JTextArea getDetail() { return detail; }
	
	//Setter methods
	public void setPoint(JTextField point) { this.point = point; }
	public void setTimer(JTextField timer) { this.timer = timer; }
	public void setTextField(JTextField textField) { this.textField = textField; }
//	public void setNewGame(JMenuItem newGame) { this.newGame = newGame; }
//	public void setInstructions(JMenuItem instructions) { this.instructions = instructions; }
//	public void setExit(JMenuItem exit) { this.exit = exit; }
//	public void setAbout(JMenuItem about) { this.about = about; }
//	public void setColor(JMenuItem color) { this.color = color; }
	
//	public final static Color FUNCBG = new Color(214, 234, 233);
//	public final static Color MAINBG = new Color(146, 186, 146);
//	public final static Color GRID = new Color(128, 155, 206);
//	public final static Color GRID = new Color(149, 184, 209);
//	public final static Color BTN = new Color(216, 115, 127);
//	public final static Color TEXT = new Color(71, 92, 122);
//	public final static Color BG3 = new Color(104, 93, 121);
	
	public GameView(GameModel model) {
		this.model = model;
		this.add(gamePanel());
		this.setJMenuBar(menuBar());
		this.setTitle("NumPuz");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("resources/GameIcon.png")).getImage());
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(WIDTH, HEIGHT);
	}
	
	public JPanel gamePanel() {
		//add content to main JPanel
		game = new JPanel(new BorderLayout());
		game.add(functionPlay(), BorderLayout.EAST);
		game.add(gridPanel(), BorderLayout.WEST);
//		game.setBackground(MAINBG);
		return game;
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
		instructions = new JMenuItem("How to play", new ImageIcon(getClass().getClassLoader().getResource("resources/IconNewGame.png")));
		instructions.setActionCommand("New Game");
		exit = new JMenuItem("Exit", new ImageIcon(getClass().getClassLoader().getResource("resources/IconExit.png")));
		exit.setActionCommand("New Game");
		gameMenu.add(newGame);
		gameMenu.add(instructions);
		gameMenu.add(exit);
		
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		about = new JMenuItem("About", new ImageIcon(getClass().getClassLoader().getResource("resources/IconAbout.png")));
		about.setActionCommand("New Game");
		color = new JMenuItem("Color", new ImageIcon(getClass().getClassLoader().getResource("resources/IconColor.png")));
		color.setActionCommand("New Game");
		helpMenu.add(about);
		helpMenu.add(color);
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}
	
	private JPanel gridPanel() {
		gridPanel = new JPanel(new BorderLayout());
		resetGrid(3, true); //add grids to grid panel
		return gridPanel;
	}
	
	/**
	 * Store all components that make up the function panel
	 * @return JPanel of all tabs
	 */
	private JPanel functionPlay() {
		JPanel support1, support2, support3, support4, support5, support6;
		
		// functions panel initialization
		functionPanel = new JPanel();
		functionPanel.setLayout(new GridBagLayout());
//		functions.setBackground(FUNCBG);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 0, 10, 10);
		functionPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		//Image
		JLabel pic = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/Icon.jpg")));
		
		// support 1: mode
		support1 = new JPanel();
		support1.setLayout(new GridBagLayout());
//		support1.setBackground(FUNCBG);
		design = new JRadioButton("Design");
		play = new JRadioButton("Play", true);
		ButtonGroup group = new ButtonGroup();
		group.add(design);
		group.add(play);
		compConfig(new JLabel("<html><font size=\"4\"><b>MODE:</b></font></html>"), support1, 0, 0, 1, gbc.insets);
		compConfig(design, support1, 1, 0, 1, gbc.insets);
		compConfig(play, support1, 2, 0, 1, gbc.insets);
		
		// support 2: dimension and type 
		support2 = new JPanel();
		support2.setLayout(new GridBagLayout());
//		support2.setBackground(FUNCBG);
		Integer dimen[] = {2,3,4,5,6,7,8,9,10};
		String input[] = {"Number", "Text"};
		dim = new JComboBox<Integer>(dimen);
		format = new JComboBox<String>(input);
		dim.setSelectedIndex(1);
		format.setSelectedIndex(0);
		compConfig(new JLabel("<html><font size=\"4\"><b>DIMENSION:</b></font></html>"), support2, 0, 0, 1, gbc.insets);
		compConfig(dim, support2, 1, 0, 1, gbc.insets);
		compConfig(new JLabel("<html><font size=\"4\"><b>TYPE:</b></font></html>"), support2, 0, 1, 1, gbc.insets);
		compConfig(format, support2, 1, 1, 1, gbc.insets);
		
		// support 3: start and restart the game
//		support3 = new JPanel();
//	    support3.setLayout(new GridBagLayout());
//	    support3.setBackground(BG4);
//	    start = new JButton("START");
//	    reset = new JButton("RESTART");
//	    compConfig(start, support3, 0, 0, 1, gbc.insets);
//	    compConfig(reset, support3, 0, 1, 1, gbc.insets);
		
	    // support 4: show and hide solution
		support4 = new JPanel();
		support4.setLayout(new GridBagLayout());
//		support4.setBackground(FUNCBG);
		show = new JButton("Show");
		hide = new JButton("Hide");
		compConfig(new JLabel("<html><font size=\"4\"><b>SOLUTION:</b></font></html>"), support4, 0, 0, 1, gbc.insets);
		compConfig(show, support4, 2, 0, 1, gbc.insets);
		compConfig(hide, support4, 3, 0, 1, gbc.insets);
	
		// detail of current execution
		detail = new JTextArea(15, 10);
		detail.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(detail);
		detail.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// support 5: point and timer
		support5 = new JPanel();
		gbc.insets = new Insets(10, 10, 0, 0);
		support5.setLayout(new GridBagLayout());
//		support5.setBackground(FUNCBG);
		point = new JTextField(5);
		timer = new JTextField(5);
		point.setText("0");
		timer.setText("0");
		point.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		timer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		compConfig(new JLabel("POINTS"), support5, 0, 0, 1, gbc.insets);
		compConfig(point, support5, 1, 0, 1, gbc.insets);
		compConfig(new JLabel("TIMER"), support5, 2, 0, 1, gbc.insets);
		compConfig(timer, support5, 3, 0, 1, gbc.insets);
		
		// support 6: save and load previous/ new game
	    support6 = new JPanel();
	    support6.setLayout(new GridBagLayout());
//	    support6.setBackground(FUNCBG);
	    gbc.insets = new Insets(10, 30, 0, 10);
	    save = new JButton("SAVE");
	    load = new JButton("LOAD");
	    compConfig(save, support6, 0, 0, 1, gbc.insets);
	    compConfig(load, support6, 1, 0, 1, gbc.insets);
		
	    // add supports to functions panel
		gbc.insets = new Insets(0, 5, 5, 5);
		compConfig(pic, functionPanel, 0, 0, 3, gbc.insets);
		compConfig(support1, functionPanel, 0, 4, 3, gbc.insets);
		compConfig(support2, functionPanel, 0, 5, 2, gbc.insets);
		compConfig(support4, functionPanel, 0, 6, 2, gbc.insets);
		compConfig(scrollPane, functionPanel, 0, 7, 2, gbc.insets);
		compConfig(support5, functionPanel, 0, 9, 1, gbc.insets);
		compConfig(support6, functionPanel, 0, 10, 1, gbc.insets);
		
		// initialize text input field
		textInput = new JPanel();
//		textInput.setBackground(FUNCBG);
		textLabel = new JLabel("Text Input: ");
		textField = new JTextField(40);
		textField.setText(" ");
		setText = new JButton("Set");
		
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
	public void resetGrid(int newDim, boolean isInitialLoadOrText) {
		//Set the whole grid again
		matrix = new JButton[newDim][newDim];
		grids = new JPanel(new GridLayout(newDim, newDim));
		grids.setPreferredSize(new Dimension(GRID_SIZE, GRID_SIZE));
		
		if (isInitialLoadOrText) {
			oldDim = newDim;
			setTextOrInitialGrid();
		}
		else {
			initializeNewGrid(newDim);
		}
		gridPanel.add(grids, BorderLayout.NORTH);
	}
	
	/**
	 * After remove the old grids, initialize new grid depends on model shuffle number
	 */
	private void initializeNewGrid(int newDim) {
		for (int i = 0; i < newDim; i++) {
			for (int j = 0; j < newDim; j++) {
				if (isTypeNum()) {
					if (model.getShuffleNum()[i*newDim+j] == 0) {
						matrix[i][j] = new JButton();
						matrix[i][j].setBackground(Color.BLACK);
						grids.add(matrix[i][j]);
						continue;
					}
					matrix[i][j] = new JButton("" + model.getShuffleNum()[i*newDim+j]);
//					matrix[i][j].setBackground(GRID);
					grids.add(matrix[i][j]);
				}
				else {
					if (model.getShuffleText()[i*newDim+j] == 0) {
						matrix[i][j] = new JButton();
						matrix[i][j].setBackground(Color.BLACK);
						grids.add(matrix[i][j]);
						continue;
					}
					matrix[i][j] = new JButton("" + model.getShuffleText()[i*newDim+j]);
//					matrix[i][j].setBackground(GRID);
					grids.add(matrix[i][j]);
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
		//Remove old text input if text input is not null
//		if (!textField.getText().equals(" ")) {
			System.out.println("Go into text field!");
			textInput.remove(textLabel);
			textInput.remove(textField);
			textInput.remove(setText);
			gridPanel.remove(textInput);
//		}
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
		for (int i = 0; i < oldDim; i++) {
			for (int j = 0; j < oldDim; j++) {
				matrix[i][j] = new JButton("");
//				matrix[i][j].setBackground(GRID);
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
	
//	public void changeMode(boolean isPlayMode) {
//		if (isPlayMode) {
//			game.remove(functionDesign);
//		}
//		else {
//			game.remove(functionPanel);
//		}
//		game.remove(gridPanel);
//		this.remove(game);
//		game.revalidate();
//		game.repaint();
//	}
}
