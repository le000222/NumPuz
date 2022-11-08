package game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Color;
import java.util.prefs.Preferences;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;
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
public class GameController extends AbstractAction implements ActionListener, MouseListener {
	
	/**
	 * Serial UID for GameController
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * background color for buttons
	 */
	public static Color bgColor = null;
	/**
	 * view object for GameView
	 */
	private GameView view;
	/**
	 * model object for GameModel
	 */
	private GameModel model;
	/**
	 * boolean to check if game is running
	 */
	private boolean gameIsRunning = false;
	/**
	 * points calculated by controller
	 */
	private int points = 0;
	
	/**
	 * default constructor
	 */
	public GameController() {}
	
	/**
	 * overloading constructor
	 * @param view view object in GameView
	 * @param model model object in GameModel
	 */
	public GameController(GameView view, GameModel model) {
		GameController controller = new GameController();
		controller.view = view;
		controller.model = model;
		view.setController(controller);
	}
	
	/**
	 * overloading method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//JMenuItems new game
		if (e.getSource() == view.getNewGame()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure to restart the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				designNewGame();
			}
		}
		//JMenuItems exit
		else if (e.getSource() == view.getExit()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure to quit the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		//JMenuItems color
		else if (e.getSource() == view.getColor()) {
			// JOption Pane to change color for buttons
			System.out.println("Color");
			bgColor = JColorChooser.showDialog(view.getGrids(),"Select a background color", Color.WHITE);
			
			// change color function panel
			for (int i = 0; i < view.getDimension(); i++) {
				for (int j = 0; j < view.getDimension(); j++) {
					if(view.getMatrixButton(i, j).getText().equalsIgnoreCase("0")) {
						continue;
					}
					view.getMatrixButton(i, j).setBackground(bgColor);
				}
			}
		}
		//JMenuItem about
		else if (e.getSource() == view.getAbout()) {
			new GameIcon(GameApp.DEFAULT_ICON);
			return;
		}
		
		//Play/Design Mode
		//type text is selected
		if (!gameIsRunning && view.getFormat().getSelectedItem().equals("Text")) { actionSetText(); }

		// play mode is clicked
		if (e.getSource() == view.getPlay()) {
			System.out.println("Play is selected");
			view.getSave().setEnabled(false);
			view.getLoad().setEnabled(false);
			view.getPlay().setEnabled(false);
			view.getDim().setEnabled(false);
//			view.getLevel().setEnabled(false);
			view.getDisplay().setEnabled(false);
			view.getFormat().setEnabled(false);
			view.getStop().setEnabled(true);
			gameIsRunning = true;
			if(!view.timerRunning) {
				view.startTimer();
			}
			// shuffle array
			if (view.isTypeNum()) { actionNumShuffle(); }
			else if (!view.isTypeNum()) { actionTextShuffle(); }
		}
		// design mode is clicked or selected
		else if (e.getSource() == view.getDesign() || view.getDesign().isSelected()) {
			System.out.println("Design is selected");
			view.getSave().setEnabled(true);
			view.getLoad().setEnabled(true);
			view.getPlay().setEnabled(true);
			view.getDim().setEnabled(true);
//			view.getLevel().setEnabled(true);
			view.getDisplay().setEnabled(true);
			view.getFormat().setEnabled(true);
			view.getDesign().setEnabled(false);
			view.getStop().setEnabled(false);
			// calculate solution number array
			model.calSolutionNum(view.getDimension());
			// display number or text in right order in the grid
			if (e.getSource() == view.getDisplay()) {
				if (view.isTypeNum()) { actionNumDisplay(); }
				else if (!view.isTypeNum()) { actionTextDisplay(); }
			}
		}
		// load configuration button is clicked
		if (e.getSource() == view.getLoad()) { loadGameConfig(); }
		// save configuration button is clicked
		else if (e.getSource() == view.getSave()) { saveGameConfig(); }
		// stop button is clicked to pause and restart the game
		else if (e.getSource() == view.getStop()) {
			if (JOptionPane.showConfirmDialog(null, "Stop and restart the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				gameIsRunning = false;
				view.timerPause();
				view.timerRunning = false;
				view.getStop().setEnabled(false);
				GameIcon icon = new GameIcon(GameApp.LOSE_ICON);
				if (JOptionPane.showConfirmDialog(null, "Design a new game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					icon.dispose();
					designNewGame();
				}
			}
		}
		// solution button is clicked to show solution array
		else if(e.getSource() == view.getSolution()) {
			if (view.getDesign().isSelected()) {
				JOptionPane.showMessageDialog(null, "Sorry, you cannot show solution in Design Mode");
				return;
			}
			if (JOptionPane.showConfirmDialog(null, "Are you sure to show solutions?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				System.out.println("Solutions");
				view.timerPause();
				printSolution();
				view.getDesign().setSelected(true);
				view.getPlay().setEnabled(false);
				view.getSave().setEnabled(false);
				view.getLoad().setEnabled(false);
				view.getDim().setEnabled(false);
//				view.getLevel().setEnabled(false);
				view.getDisplay().setEnabled(false);
				view.getFormat().setEnabled(false);
				new GameIcon(GameApp.LOSE_ICON);
			}
		}
	}
	
	/**
	 * Handle each button whenever it is clicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(((JButton) e.getSource()).getText());
		JButton self = (JButton) e.getSource();
		// if button clicked isn't near the black button, doesnt change anything
		if(!swapZero(self)) {
			return;
		}
		reloadGrid(view.getMatrix());
		view.getGrids().revalidate();
		for (int i = 0; i < view.getMatrix()[0].length; i++) {
			for (int j = 0; j < view.getMatrix()[0].length; j++) {
				if(view.getMatrix()[i][j].getText().equalsIgnoreCase("0"))
					view.getMatrix()[i][j].requestFocus();
			}
		}
		// compare the whole grids with solution array to determine if user solves the puzzle
		if (compareToSol(view.isTypeNum(), view.getDimension()) == GameApp.WIN) {
			System.out.println("COMPARE");
			view.timerPause();
			gameIsRunning = false;
			GameIcon icon = new GameIcon(GameApp.WIN_ICON);
			if (JOptionPane.showConfirmDialog(null, "Design a new game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				designNewGame();
			}
			icon.dispose();
			view.getStop().setEnabled(false);
			return;
		}
	}
	
	/**
	 * reload the whole grids
	 * @param matrix 2D matrix 
	 */
	private void reloadGrid(JButton[][] matrix) {
		view.getGrids().removeAll();
		for (int y = 0; y < matrix[0].length ; y++) {
			for (int x = 0; x < matrix[0].length; x++) {				
				view.getGrids().add(matrix[y][x]);
			}
		}
	}
	
	/**
	 * Swap clicked button and zero button if possible
	 * @param self clicked button
	 * @return true: successfully swapped, false: isn't swappable
	 */
	private boolean swapZero(JButton self) {
		JButton[][] matrix = view.getMatrix();
		int dimen = view.getDimension();
		JButton zeroButton = null;
		int myY = (int)self.getClientProperty("y");
		int myX = (int)self.getClientProperty("x");
		boolean isNum = view.isTypeNum();
		
		System.out.println("Self is at Y=["+(myY)+"] X=["+(myX)+"]");
		if(myY > 0) {
			System.out.println("checking above me");
			//check the one above me
			if(matrix[myY -1][myX].getText().equalsIgnoreCase("0") ) {
				System.out.println("found above");
				zeroButton = matrix[myY -1][myX];
				matrix[myY -1][myX] = self;
				System.out.println("Put self at Y=["+(myY-1)+"] X=["+(myX)+"]");
				self.putClientProperty("y", myY-1);
				self.putClientProperty("x", myX);
				matrix[myY][myX] = zeroButton;
				System.out.println("Put Temp at Y=["+(myY)+"] X=["+(myX)+"]");
				zeroButton.putClientProperty("y", myY);
				zeroButton.putClientProperty("x", myX);
				//POINTS
				countPoints(isNum, dimen, myY-1, myX);
				return true;
			}
		}
		//check the one to the right
		if( myX < (dimen-1) ) {
			System.out.println("checking my right");
			if(matrix[myY][myX+1].getText().equalsIgnoreCase("0")) {
				System.out.println("found right");
				zeroButton = matrix[myY][myX+1];				
				matrix[myY][myX+1] = self;
				System.out.println("Put self at Y=["+(myY)+"] X=["+(myX+1)+"]");
				self.putClientProperty("y", myY);
				self.putClientProperty("x", myX+1);
				matrix[myY][myX] = zeroButton;
				System.out.println("Put Temp at Y=["+(myY)+"] X=["+(myX)+"]");
				zeroButton.putClientProperty("y", myY);
				zeroButton.putClientProperty("x", myX);
				//POINTS
				countPoints(isNum, dimen, myY, myX+1);
				return true;
			}
		}
		//check the one below
		if( myY < (dimen -1) ) {
			System.out.println("checking below me");
			if(matrix[myY+1][myX].getText().equalsIgnoreCase("0")) {
				System.out.println("found below");
				zeroButton = matrix[myY+1][myX];
				matrix[myY+1][myX] = self;
				System.out.println("Put self at Y=["+(myY+1)+"] X=["+(myX)+"]");
				self.putClientProperty("y", myY+1);
				self.putClientProperty("x", myX);
				matrix[myY][myX] = zeroButton;
				System.out.println("Put Temp at Y=["+(myY)+"] X=["+(myX)+"]");
				zeroButton.putClientProperty("y", myY);
				zeroButton.putClientProperty("x", myX);
				//POINTS
				countPoints(isNum, dimen, myY+1, myX);
				return true;
			}
		}
		//check the one to the left
		if ( myX > 0 ) {
			System.out.println("checking my left");
			if(matrix[myY][myX-1].getText().equalsIgnoreCase("0")) {
				System.out.println("found left");
				zeroButton = matrix[myY][myX-1];
				matrix[myY][myX-1] = self;
				System.out.println("Put self at Y=["+(myY)+"] X=["+(myX-1)+"]");
				self.putClientProperty("y", myY);
				self.putClientProperty("x", myX-1);
				matrix[myY][myX] = zeroButton;
				System.out.println("Put Temp at Y=["+(myY)+"] X=["+(myX)+"]");
				zeroButton.putClientProperty("y", myY);
				zeroButton.putClientProperty("x", myX);
				//POINTS
				countPoints(isNum, dimen, myY, myX-1);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * count points for each button is clicked
	 * @param isNum type is number or text
	 * @param dimen grid dimension
	 * @param clickedY position of row for clicked button
	 * @param clickedX position of col for clicked button
	 */
	private void countPoints(boolean isNum, int dimen, int clickedY, int clickedX) {
		if(isNum) {
			if (Integer.parseInt(view.getMatrixButton(clickedY, clickedX).getText()) == model.getSolNum()[clickedY * dimen + clickedX]) {
				points++;
			}
		} else {
			if (view.getMatrixButton(clickedY, clickedX).getText().equals(Character.toString(model.getSolText()[clickedY * dimen + clickedX]))) {
				points++;
			}
		}
		view.getPoint().setText(String.valueOf(points));
		System.out.println("POINTS = " + points);
	}
	
	/**
	 * Compare buttons in grid with solution array
	 * @param isNum type is number or text
	 * @param dimen grid dimension
	 * @return number of correct buttons comparing to solution array 
	 */
	private int compareToSol(boolean isNum, int dimen) {
		int correctBtn = 0;
		for (int i = 0; i < dimen; i++) {
			for (int j = 0; j < dimen; j++) {
				if (isNum) {
					if (Integer.parseInt(view.getMatrixButton(i, j).getText()) == model.getSolNum()[i * dimen + j]) {
					correctBtn++;
					}
				}
				else {
					if (view.getMatrixButton(i, j).getText().equals(Character.toString(model.getSolText()[i * dimen + j]))) {
						correctBtn++;
					}
				}
			}
		}
		if (correctBtn == dimen*dimen) {
			return GameApp.WIN;
		}
		System.out.println("CORRECT BUTTON=" + correctBtn);
		return correctBtn;
	}
	
	/**
	 * save game configuration
	 */
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

	/**
	 * load game configuration
	 */
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
	
	/**
	 * design new game for the grid
	 */
	private void designNewGame() {
		view.getDesign().setSelected(true);
		view.getDesign().setEnabled(false);
		view.getPlay().setEnabled(true);
		view.getSave().setEnabled(true);
		view.getLoad().setEnabled(true);
		view.getPlay().setEnabled(true);
		view.getDim().setEnabled(true);
//		view.getLevel().setEnabled(true);
		view.getDisplay().setEnabled(true);
		view.getFormat().setEnabled(true);
		view.getDesign().doClick();
		view.getPoint().setText("0");
		view.getDetail().setText("");
		view.getTimer().setText("0");
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 0);
	}
	
	/**
	 * print solution for number or text array
	 */
	private void printSolution() {
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 1);
		view.getStop().setEnabled(false);
	}
	
	/**
	 * display number in ascending order in grid
	 */
	private void actionNumDisplay() {
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 1);
	}
	
	/**
	 * shuffle number array
	 */
	private void actionNumShuffle() {
		model.shuffleNum(view.getDimension());
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 2);
	}
	
	/**
	 * display text input when user selects text type
	 */
	private void actionSetText() {
		view.removeOldGrid(view.getDimension());
		view.displayTextInput();
		view.resetGrid(view.getDimension(), 0);
	}
	
	/**
	 * display text in correct order in grid
	 */
	private void actionTextDisplay() {
		String text = view.getTextField().getText();
		int dim = view.getDimension();
		if (text.length() < dim*dim) {
			JOptionPane.showMessageDialog(view.getGrids(), "Text length is not large enough to store in grids dimension", "Error", 0);
			return;
		}
		model.calSolutionText(text, view.getDimension());
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 1);
	}
	
	/**
	 * shuffle text array
	 */
	private void actionTextShuffle() {
		String text = view.getTextField().getText();
		model.shuffleText(text, view.getDimension());
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 2);
		view.getDetail().setText(text);
		view.getTextField().setText("");
	}
	
//	private void levels() {
//	if (view.getLevel().getSelectedItem() == "Easy") {
//		view.startTimer();
//		timer(600000, false);
//	}
//	else if (view.getLevel().getSelectedItem() == "Medium") {
//		view.startTimer();
//		timer(300000, false);
//	}
//	else if (view.getLevel().getSelectedItem() == "Hard") {
//		view.startTimer();
//		timer(120000, false);
//	}
//	else {
//		view.startTimer();
//		timer(600000, true);
//	}
//}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}

