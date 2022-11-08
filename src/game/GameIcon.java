package game;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Purpose: This class is to store Game Icon
 * File name: GameIcon.java
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
	
	/**
	 * Serial UID for GameIcon
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * image path for icons
	 */
	private String imgPath = "";
	
	/**
	 * overloading constructor
	 * @param status icon status ("default": default game icon, "finished": finished game icon, "win": win game icon, "lose": lose game icon)
	 */
	public GameIcon(String status) {
		switch (status) {
		case GameApp.WIN_ICON:
			imgPath = "resources/GameWin.png";
			break;
		case GameApp.FINISHED_ICON:
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
		this.setTitle("Game Icon");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(600, 600);
	}
}
;