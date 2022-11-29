package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.MainController;
import view.MainView;
/**
 * File name: GameApp.java
 * Purpose: This class is the main class of the program
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 2 Oct 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: GameApp.java
 * Method list: main()
 * Constants list: frame
 * Purpose: This class is the main class of the program
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameApp extends JFrame {
	/**
	 * Serial UID for GameApp
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * main method
	 * @param args arguments
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		GameIcon icon = new GameIcon(GameBasic.DEFAULT_ICON);
		timer(1111);
		icon.dispose();
		MainView view = new MainView();
		new MainController(view);
		view.runMainView();
	}
	
	/**
	 * Timer for specific functions
	 * @param time time for timer to wait before execute next line of code
	 */
	public final static void timer(int time) {
		Timer timer = new Timer(1111, new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {}
		});
		timer.start();
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.stop();
	}
	
}
