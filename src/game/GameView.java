package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	
	/**
	 * Fixed height for num grid
	 */
	private final int HEIGHT = 835;
	/**
	 * Fixed width for num grid
	 */
	private final int WIDTH = 1100;
	/**
	 * Fixed size for grid of numbers
	 */
	private final static int GRID_SIZE = 600;
	
	/**
	 * Panel to store functions
	 */
	private JPanel functions;
	/**
	 * GamePanel object for Game tab
	 */
	private JPanel game;
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
	
	public final static Color BTN = new Color(252, 187, 109);
	public final static Color TEXT = new Color(71, 92, 122);
	public final static Color BG = new Color(216, 115, 127);
	public final static Color BG2 = new Color(171, 108, 130);
	public final static Color BG3 = new Color(104, 93, 121);
	public final static Color BG4 = new Color(242, 224, 197);
	
	
	public GameView(GameModel model) {
		this.model = model;
		
		this.add(gamePanel());
		this.setJMenuBar(menuBar());
		this.setTitle("NumPuz");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(WIDTH, HEIGHT);
	}
	
	public JPanel gamePanel() {
		//add content to main JPanel
		game = new JPanel();
		game.add(functionPanel());
		resetGrid(3, true); //add grids to game panel
		game.setBackground(BG3);
		return game;
	}
	
	/**
	 * Store all components that make up the menu bar panel (game, help)
	 * @return JPanel of all tabs
	 */
	public JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		gameMenu.add(new JMenuItem("New Game"));
		gameMenu.add(new JMenuItem("How to play"));
		gameMenu.add(new JMenuItem("Settings"));
		gameMenu.add(new JMenuItem("Exit "));
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new JMenuItem("About"));
		helpMenu.add(new JMenuItem("Color"));
		helpMenu.add(new JMenuItem("Search"));
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}
	
	/**
	 * Store all components that make up the function panel
	 * @return JPanel of all tabs
	 */
	private JPanel functionPanel() {
		JPanel support1, support2, support3, support4, support5, support6;
		
		// functions panel initialization
		functions = new JPanel(new FlowLayout());
		functions.setLayout(new GridBagLayout());
		functions.setBackground(BG4);
		gbc = new GridBagConstraints();
		functions.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// support 1: mode
		support1 = new JPanel();
		support1.setLayout(new GridLayout(3,0));
		support1.setBackground(BG4);
		design = new JRadioButton("Design");
		play = new JRadioButton("Play");
		ButtonGroup group = new ButtonGroup();
		group.add(design);
		group.add(play);
		support1.add(new JLabel("<html><font size=\"4\"><b>MODE:</b></font></html>"));
		support1.add(design);
		support1.add(play);
		
		// support 2: dimension and type 
		support2 = new JPanel();
		support2.setLayout(new GridBagLayout());
		support2.setBackground(BG4);
		Integer dimen[] = {2,3,4,5,6,7,8,9,10};
		String input[] = {"Number", "Text"};
		dim = new JComboBox<Integer>(dimen);
		format = new JComboBox<String>(input);
		dim.setSelectedIndex(1);
		format.setSelectedIndex(0);
		gbc.insets = new Insets(0, 5, 10, 0);
		compConfig(new JLabel("<html><font size=\"4\"><b>DIMENSION:</b></font></html>"), support2, 0, 0, 1, gbc.insets);
		compConfig(new JLabel("<html><font size=\"4\"><b>TYPE:</b></font></html>"), support2, 0, 1, 1, gbc.insets);
		compConfig(dim, support2, 1, 0, 1, gbc.insets);
		compConfig(format, support2, 1, 1, 1, gbc.insets);
		
		// support 3: start and restart the game
		support3 = new JPanel();
	    support3.setLayout(new GridBagLayout());
	    support3.setBackground(BG4);
	    start = new JButton("START");
	    reset = new JButton("RESTART");
	    compConfig(start, support3, 0, 0, 1, gbc.insets);
	    compConfig(reset, support3, 0, 1, 1, gbc.insets);
		
	    // support 4: show and hide solution
		support4 = new JPanel();
		support4.setLayout(new GridBagLayout());
		support4.setBackground(BG4);
		show = new JButton("Show");
		hide = new JButton("Hide");
		compConfig(new JLabel("<html><font size=\"4\"><b>SOLUTION:</b></font></html>"), support4, 0, 0, 2, gbc.insets);
		compConfig(show, support4, 0, 1, 1, gbc.insets);
		compConfig(hide, support4, 1, 1, 1, gbc.insets);
	
		// detail of current execution
		detail = new JTextArea(5,35);
		detail.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// support 5: point and timer
		support5 = new JPanel();
		gbc.insets = new Insets(5, 10, 10, 0);
		support5.setLayout(new GridBagLayout());
		support5.setBackground(BG4);
		point = new JTextField(5);
		timer = new JTextField(5);
		point.setText("0");
		timer.setText("0");
		point.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		timer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		compConfig(new JLabel("POINTS"), support5, 0, 0, 1, gbc.insets);
		compConfig(new JLabel("TIMER"), support5, 0, 1, 1, gbc.insets);
		compConfig(point, support5, 1, 0, 1, gbc.insets);
		compConfig(timer, support5, 1, 1, 1, gbc.insets);
		
	    // support 6: save and load previous/ new game
	    support6 = new JPanel();
	    support6.setLayout(new GridBagLayout());
	    support6.setBackground(BG4);
	    gbc.insets = new Insets(0, 0, 5, 20);
	    save = new JButton("SAVE");
	    load = new JButton("LOAD");
	    compConfig(save, support6, 0, 0, 1, gbc.insets);
	    compConfig(load, support6, 0, 1, 1, gbc.insets);
		
	    // add supports to functions panel
		gbc.insets = new Insets(0, 20, 0, 0);
		compConfig(support1, functions, 0, 0, 2, gbc.insets);
		compConfig(support2, functions, 3, 0, 2, gbc.insets);
		compConfig(support3, functions, 5, 0, 2, gbc.insets);
		compConfig(support4, functions, 7, 0, 2, gbc.insets);
		compConfig(detail, functions, 9, 0, 2, gbc.insets);
		compConfig(support5, functions, 11, 0, 1, gbc.insets);
		compConfig(support6, functions, 12, 0, 2, gbc.insets);
		
		// initialize text input field
		textInput = new JPanel();
		textInput.setBackground(BG4);
		textLabel = new JLabel("Text Input: ");
		textField = new JTextField(40);
		textField.setText(" ");
		setText = new JButton("Set");
		
		return functions;
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
		int size = newDim * newDim;
		
		//Set the whole grid again
		matrix = new JButton[size][size];
		grids = new JPanel(new GridLayout(newDim, newDim));
		grids.setPreferredSize(new Dimension(GRID_SIZE, GRID_SIZE));
		
		if (isInitialLoadOrText) {
			oldDim = newDim;
			setTextOrInitialGrid();
		}
		else {
			initializeNewGrid(newDim);
		}
		game.add(grids);
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
					matrix[i][j].setBackground(BG);
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
				game.remove(grids);
			}
		}
		//Remove old text input if text input is not null
		if (!textField.getText().equals(" ")) {
			System.out.println("Go into text field!");
			textInput.remove(textLabel);
			textInput.remove(textField);
			textInput.remove(setText);
			game.remove(textInput);
		}
		game.revalidate();
		game.repaint();
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
				matrix[i][j].setBackground(BG);
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
		game.add(textInput);
	}
}
