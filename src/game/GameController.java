package game;
/**
 * Purpose: This class is the main class of the program
 * File name: GameController.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 2 Oct 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: GameController.java
 * Method list: main()
 * Constants list: frame
 * Purpose: This class is the main class of the program
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameController {
	
	/**
	 * Main JFrame for "NumPuz"
	 */
	public static MainFrame frame;
	/**
	 * default constructor
	 */
	public GameController() {
		frame = new MainFrame();
	}
	
	/**
	 * Main method to call default constructor
	 * @param args input
	 */
	public static void main(String[] args) {
		new GameController();
	}
};