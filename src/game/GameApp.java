package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 * Purpose: This class is the main class of the program
 * File name: GameApp.java
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
	public final static int DEFAULT_DIM = 3;
	public GameView view;
	public GameModel model;
	public GameController controller;
	
	public GameApp() {
		if (model == null) {
			model = new GameModel();
		}
		if (view == null) {
			view = new GameView(model);
			view.setModel(model);
		}
		if (controller == null) {
			controller = new GameController(view, model);
		}
		view.initComponents();
	}
	
	private static void timer() {
		Timer timer = new Timer(1111, new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {}
		});
		timer.start();
		try {
			Thread.sleep(2222);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.stop();
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new GameApp();
		GameIcon icon = new GameIcon();
		timer();
		icon.dispose();
	}
}
