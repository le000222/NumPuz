package game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
/**
 * Purpose: This class is the controller of the game
 * File name: GameController.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 2 Oct 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */
import javax.swing.event.MenuListener;

/**
 * Class Name: GameController.java
 * Method list: main()
 * Constants list: frame
 * Purpose: This class is the controller of the game
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameController implements ActionListener {
	
	private GameView view;
	private GameModel model;
	boolean isDesignMode = true;
	
	public GameController(GameView view, GameModel model) {
		this.view = view;
		this.model = model;
	}
	
	public void start() {
		view.getDesign().addActionListener(this);
		view.getPlay().addActionListener(this);
		view.getDim().addActionListener(this);
		view.getFormat().addActionListener(this);
		view.getDesign().addActionListener(this);
		view.getSetText().addActionListener(this);
		view.getSave().addActionListener(this);
		view.getLoad().addActionListener(this);
		view.getAbout().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getLoad()) { loadGameConfig(); }
		else if (e.getSource() == view.getSave()) { saveGameConfig(); }
//		else if (e.getActionCommand().equals("Exit")) { frame.dispose(); }
		else if (e.getSource() == view.getSetText()) { actionSetTextButton(); }
		else if (view.getPlay().isSelected()) {
			view.getSave().disable();
			view.getLoad().disable();
			
			if (view.isTypeNum()) { actionNumSelected(); }
			if (!view.isTypeNum()) { actionTextSelected(); }
		}
		else if (view.getDesign().isSelected()) {
			//Disable some buttons in the functionPanel
		}
	}
	
	private void saveGameConfig() {
		Preferences pref = Preferences.userRoot();
		String path = pref.get("DEFAULT_PATH", "");
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(path));
		
		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}

	private void loadGameConfig() {
		Preferences pref = Preferences.userRoot();
		String path = pref.get("DEFAULT_PATH", "");
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(path));
		
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}

//	private void actionPlayMode() {
//		view.resetGrid(view.getDimension(), true);
//		System.out.println("Play");
//		view.gamePanel(0);
//	}
//
//	private void actionDesignMode() {
//		view.resetGrid(view.getDimension(), true);
//		System.out.println("Design");
//		view.gamePanel(1);
//	}
	
	private void actionNumSelected() {
		model.shuffleNum(view.getDimension());
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), false);
	}
	
	private void actionTextSelected() {
		view.removeOldGrid(view.getDimension());
		view.displayTextInput();
		view.resetGrid(view.getDimension(), true);
	}
	
	private void actionSetTextButton() {
		String text = view.getTextField().getText();
		int dim = view.getDimension();
		if (text.length() < dim*dim) {
			JOptionPane.showMessageDialog(view.getGrids(), "Text length is not large enough to store in grids dimension", "Error", 0);
			return;
		}
		model.shuffleText(text, view.getDimension());
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), false);
		view.getDetail().setText(text);
		view.getTextField().setText(" ");
	}
}
