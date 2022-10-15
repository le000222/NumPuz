package game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
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
	
	public GameController(GameView view, GameModel model) {
		this.view = view;
		this.model = model;
	}
	
	public void start() {
		view.getStart().addActionListener(this);
		view.getDesign().addActionListener(this);
		view.getSetText().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (view.getDesign().isSelected()) { actionDesignMode(); }
		if (e.getSource() == view.getStart()) {
			if (view.isTypeNum() && view.getPlay().isSelected()) { actionNumSelected(); }
			else if (!view.isTypeNum() && view.getPlay().isSelected()) { 
				actionTextSelected(); 
			}
		}
		if (e.getSource() == view.getSetText()) {
			actionSetTextButton();
		}
	}
	
	private void actionDesignMode() {
		System.out.println("Design");
		JOptionPane.showMessageDialog(view.getGrids(), "Please choose \"Play\" Mode to start the game", "Error", 0);
	}
	
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
