package game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Purpose: This class is to store all of the GUI components and functionalities to implement the game
 * File name: MainFrame.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 2 Oct 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification: [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: MainFrame.java
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
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Fixed height for num grid
	 */
	private final int HEIGHT = 835;
	/**
	 * Fixed width for num grid
	 */
	private final int WIDTH = 1050;
	/**
	 * Fixed size for grid of numbers
	 */
	private final static int GRID_SIZE = 600;
	/**
	 * Tab pane to contain Game and Help tab
	 */
	private static JTabbedPane tabs;
	/**
	 * Panel to store functions
	 */
	private static JPanel menu;
	/**
	 * GamePanel object for Game tab
	 */
	private static JPanel game;
	/**
	 * Panel for Help
	 */
	private static JPanel help;
	/**
	 * Panel to store grid of numbers
	 */
	private static JPanel grids;
	/**
	 * Panel to store text input when choose text type
	 */
	private static JPanel textInput;
	/**
	 * Different buttons for different functionalities
	 */
	protected static JButton save, load, start, reset, show, hide;
	/**
	 * Buttons to design and play game
	 */
	protected static JRadioButton design, play;
	/**
	 * Drop down for dimension
	 */
	protected static JComboBox<Integer> dim;
	/**
	 * Drop down for type
	 */
	protected static JComboBox<String> type;
	/**
	 * Text field for point and timer
	 */
	protected static JTextField point, timer;
	/**
	 * Text Area to store details of current execution
	 */
	private static JTextArea detail;
	/**
	 * Shuffle array to store integer that have been shuffled
	 */
	private static Integer[] shuffle;
	/**
	 * 2D array of JButton for grids
	 */
	private static JButton[][] matrix = null;
	/**
	 * border object to set border for JPanel
	 */
	private static Border border = BorderFactory.createLineBorder(Color.BLACK); // for functions box
	/**
	 * Dimension of the grid
	 */
	protected static int dimension;
	/**
	 * Check to see if it's an initial load or not
	 */
	private static boolean isInitialLoad = true;
	
	/**
	 * default constructor
	 */
	public MainFrame() {
		this.add(tabPanel());
		// ActionListener
		start.addActionListener(new StartGame());
		this.setTitle("NumPuz");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(WIDTH, HEIGHT);
	}
	
	/**
	 * Store all components that make up the tab panel (game, hep)
	 * @return JPanel of all tabs
	 */
	private static JTabbedPane tabPanel() {
		// initialize tab pane for Game and Help
		tabs = new JTabbedPane();
		tabs.setBounds(50, 900, 900, 50);
		tabs.add("Game", gamePanel());
		tabs.add("Help", helpPanel());
		return tabs;
	}
	
	/**
	 * Store all components that make up the help panel
	 * @return JPanel of all panels in help tab
	 */
	private static JPanel helpPanel() {
		help = new JPanel();
		return help;
	}
	
	/**
	 * Store all components that make up the game panel
	 * @return JPanel of all panels in game tab (menu, grids)
	 */
	private static JPanel gamePanel() {
		game = new JPanel(new FlowLayout());
		game.add(menuPanel(), BorderLayout.NORTH);
		game.add(Box.createVerticalStrut(100));
		resetGrid();
		return game;
	}
	
	/**
	 * Store all components that make up the menu panel
	 * @return JPanel of all functionalities
	 */
	private static JPanel menuPanel() {
		JPanel support1, support2, support3, support4, support5, support6;
		
		// functions panel initialization
		menu = new JPanel(new BorderLayout());
		menu.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		menu.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// support 1: mode
		support1 = new JPanel();
		support1.setLayout(new GridLayout(3,0));
		design = new JRadioButton("Design");
		play = new JRadioButton("Play");
		ButtonGroup group = new ButtonGroup();
		group.add(design);
		group.add(play);
		support1.add(new JLabel("<html><p color=\"#4169E1\">MODE:</p></html>"));
		support1.add(design);
		support1.add(play);
		
		// support 2: dimension and type 
		support2 = new JPanel();
		support2.setLayout(new GridBagLayout());
		Integer dimen[] = {2,3,4,5,6,7,8,9,10};
		String input[] = {"Number", "Text"};
		dim = new JComboBox<Integer>(dimen);
		type = new JComboBox<String>(input);
		dim.setSelectedIndex(1);
		type.setSelectedIndex(0);
		gbc.insets = new Insets(0, 5, 10, 0);
		compConfig(new JLabel("<html><p color=\"#4169E1\">DIMENSION:</p></html>"), support2, 0, 0, 1, gbc.insets);
		compConfig(new JLabel("<html><p color=\"#4169E1\">TYPE:</p></html>"), support2, 0, 1, 1, gbc.insets);
		compConfig(dim, support2, 1, 0, 1, gbc.insets);
		compConfig(type, support2, 1, 1, 1, gbc.insets);
		
		// support 3: start and restart the game
		support3 = new JPanel();
	    support3.setLayout(new GridBagLayout());
	    start = new JButton("START");
	    reset = new JButton("RESTART");
	    compConfig(start, support3, 0, 0, 1, gbc.insets);
	    compConfig(reset, support3, 0, 1, 1, gbc.insets);
		
	    // support 4: show and hide solution
		support4 = new JPanel();
		support4.setLayout(new GridBagLayout());
		show = new JButton("Show");
		hide = new JButton("Hide");
		compConfig(new JLabel("<html><p color=\"#4169E1\">SOLUTION:</p></html>"), support4, 0, 0, 2, gbc.insets);
		compConfig(show, support4, 0, 1, 1, gbc.insets);
		compConfig(hide, support4, 1, 1, 1, gbc.insets);
	
		// detail of current execution
		detail = new JTextArea(5,20);
		detail.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		// support 5: point and timer
		support5 = new JPanel();
		gbc.insets = new Insets(5, 10, 10, 0);
		support5.setLayout(new GridBagLayout());
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
	    gbc.insets = new Insets(0, 0, 5, 10);
	    save = new JButton("SAVE");
	    load = new JButton("LOAD");
	    compConfig(save, support6, 0, 0, 1, gbc.insets);
	    compConfig(load, support6, 0, 1, 1, gbc.insets);
		
	    // add supports to functions panel
		gbc.insets = new Insets(0, 20, 0, 0);
		compConfig(support1, menu, 0, 0, 2, gbc.insets);
		compConfig(support2, menu, 3, 0, 2, gbc.insets);
		compConfig(support3, menu, 5, 0, 2, gbc.insets);
		compConfig(support4, menu, 7, 0, 2, gbc.insets);
		compConfig(detail, menu, 9, 0, 2, gbc.insets);
		compConfig(support5, menu, 11, 0, 1, gbc.insets);
		compConfig(support6, menu, 12, 0, 2, gbc.insets);
		
		return menu;
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
	private static void compConfig(Component comp, JPanel panel, int x, int y, int w, Insets insets) {
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.gridx = x;
	    gbc.gridy = y;
	    gbc.gridwidth = w;
	    gbc.insets = insets;
	    panel.add(comp, gbc);
	  }
	
	
	/**************************************************
	 * All functionalities for Grids Panel
	 **************************************************/
	/**
	 * Get dimension and size of grid
	 * @return size size of dimension (= dimension^2)
	 */
	protected static int getDim() {
		dimension = (int) dim.getSelectedItem();
		int size = dimension * dimension;
		return size;
	}
	
	/**
	 * Get true false for type of the grid
	 * @param void
	 * @return true for number type and false for text type
	 */
	private static boolean getFormat() {
		boolean isNum = type.getSelectedItem().equals("Number") ? true : false;
		return isNum;
	}
	
	/**
	 * Reset whole grid when loading initially or when Start button is clicked
	 */
	public static void resetGrid() {
		//Remove old matrix
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				grids.remove(matrix[i][j]);
				game.remove(grids);
			}
		}
		//Remove old text input if text input is not null
		if (textInput != null) {
			game.remove(textInput);
		}
		
		//Set the whole grid again
		int size = getDim();
		game.revalidate();
		game.repaint();
		matrix = new JButton[size][size];
		grids = new JPanel(new GridLayout(dimension, dimension));
		grids.setPreferredSize(new Dimension(GRID_SIZE, GRID_SIZE));
		if (isInitialLoad) {
			setGrid();
			isInitialLoad = false;
		}
		else {
			if (getFormat()) {
				shuffleNum(size);
			} else {
				setGrid();
				displayTextInput();
				shuffleText(size);
			}
		}
		game.add(grids, BorderLayout.SOUTH);
	}
	
	/**
	 * Set empty grid for the initial load
	 */
	private static void setGrid() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				matrix[i][j] = new JButton("");
				grids.add(matrix[i][j]);
			}
		}
	}
	
	/**
	 * Shuffle the buttons and attach to grids panel
	 * @param size size of grid
	 * @return void
	 */
	private static void shuffleNum(int size) {
		LinkedHashSet<Integer> treeSet = new LinkedHashSet<>();
		while (treeSet.size() != size) {
	      int random = (int) (Math.random() * size);
	      treeSet.add(random);
	    }
		shuffle = treeSet.toArray(new Integer[treeSet.size()]);
		
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (shuffle[i*dimension+j] == 0) {
					matrix[i][j] = new JButton();
					matrix[i][j].setBackground(Color.BLACK);
					grids.add(matrix[i][j]);
					continue;
				}
				matrix[i][j] = new JButton("" + shuffle[i*dimension+j]);
				grids.add(matrix[i][j]);
			}
		}
	}
	
	/**
	 * Shuffle the text in int and assign it to shuffle array
	 * @param size size of grid
	 * @return void
	 */
	private static void shuffleText(int size) {
		
	}
	
	/**
	 * Display text input when text type is selected
	 * @return void
	 */
	private static void displayTextInput() {
		textInput = new JPanel();
		JLabel textLabel = new JLabel("Text Input:");
		JTextField textField = new JTextField(30);
		JButton setText = new JButton("Set");
		textInput.add(textLabel);
		textInput.add(textField);
		textInput.add(setText);
		game.add(textInput, BorderLayout.CENTER);
	}
	
	/**
	 * StartGame class triggered when start button is clicked
	 * @author Ngoc Phuong Khanh Le, Dan McCue
	 */
	class StartGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (play.isSelected()) {
				resetGrid();
			}
		}
	}
	
	/**
	 * Design class triggered when design button is clicked
	 * @author Ngoc Phuong Khanh Le, Dan McCue
	 */
	class Design implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(grids, "Please choose \"Play\" Mode to start the game", "Error", 0);
		}
	}
}
