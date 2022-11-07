package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

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
 * Class Name: GameIcon.java
 * Purpose: This class is to create a new frame for the game icon
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameIcon  extends JFrame {
	
	private String imgPath = "";
	
	public GameIcon(String status) {
		switch (status) {
		case GameApp.WIN_ICON:
			imgPath = "resources/GameWin.png";
			break;
		case "finished":
			System.out.println("Finished");
			imgPath = "resources/GameFinished.png";
			break;
		case GameApp.LOSE_ICON:
			imgPath = "resources/GameLose.png";
			break;
		default:
			imgPath = "resources/GameIcon.png";
		}
		JPanel panel = new JPanel();
		JLabel icon = new JLabel();
		icon.setIcon(new ImageIcon(getClass().getClassLoader().getResource(imgPath)));
		panel.add(icon);
		this.add(panel);
		this.setTitle("WELCOME TO NUMPUZ");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(600, 600);
	}
}
;