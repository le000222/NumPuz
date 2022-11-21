package game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.util.ArrayList;
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
	 * boolean to check if user wins the game
	 */
	private boolean gameWin = false;
	/**
	 * boolean to check if array has been shuffled
	 */
	private boolean arrayShuffle = false;
	/**
	 * points calculated by controller
	 */
	private int points = 0;
	/**
	 * getter for game running
	 * @return true if game is running, false if not
	 */
	public boolean getGameRunning() { return gameIsRunning; }
	
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
		// load configuration button is clicked
		if (e.getSource() == view.getLoad()) {
			arrayShuffle = true;
			loadGameConfig(); 
			return;
		}
		// save configuration button is clicked
		else if (e.getSource() == view.getSave()) { 
			saveGameConfig(); 
			return; 
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
		
		//type text is selected
		if (!gameIsRunning && view.getFormat().getSelectedItem().equals("Text")) { actionSetText(); }

		// play mode is clicked
		if (e.getSource() == view.getPlay()) {
			gameWin = false;
			view.getSave().setEnabled(false);
			view.getLoad().setEnabled(false);
			view.getPlay().setEnabled(false);
			view.getDim().setEnabled(false);
			view.getDisplay().setEnabled(false);
			view.getFormat().setEnabled(false);
			view.getStop().setEnabled(true);
			view.getPoint().setEnabled(false);
			view.getTimer().setEnabled(false);
			view.getRand().setEnabled(false);
			gameIsRunning = true;
			if(!view.timerRunning) {
				view.startTimer();
			}
			if (!arrayShuffle) {
				// shuffle array
				if (view.isTypeNum()) { actionNumShuffle(); }
				else if (!view.isTypeNum()) { actionTextShuffle(); }
			}
		}
		// design mode is clicked or selected
		else if (e.getSource() == view.getDesign() || view.getDesign().isSelected()) {
			view.getSave().setEnabled(true);
			view.getLoad().setEnabled(true);
			view.getPlay().setEnabled(true);
			view.getDim().setEnabled(true);
			view.getDisplay().setEnabled(true);
			view.getFormat().setEnabled(true);
			view.getDesign().setEnabled(false);
			view.getStop().setEnabled(false);
			view.getPoint().setEnabled(false);
			view.getTimer().setEnabled(false);
			view.getRand().setEnabled(true);
			// calculate solution number array
			model.calSolutionNum(view.getDimension());
			// display number or text in right order in the grid
			if (e.getSource() == view.getDisplay()) {
				if (view.isTypeNum()) { actionNumDisplay(); }
				else if (!view.isTypeNum()) { actionTextDisplay(); }
			}
			// random number
			else if (e.getSource() == view.getRand()) {
				// shuffle array
				if (view.isTypeNum()) { actionNumShuffle(); }
				else if (!view.isTypeNum()) { actionTextShuffle(); }
				arrayShuffle = true;
			}
		}
		
		// stop button is clicked to pause and restart the game
		if (e.getSource() == view.getStop()) {
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
				hint();
			}
		}
		//JMenuItems new game
		else if (e.getSource() == view.getNewGame()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure to restart the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				gameIsRunning = false;
				designNewGame();
			}
			return;
		}
		// solution button is clicked to show solution array
		else if(e.getSource() == view.getSolution()) {
			if (view.getDesign().isSelected()) {
				JOptionPane.showMessageDialog(null, "Sorry, you cannot show solution in Design Mode", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else if (gameWin) {
				JOptionPane.showMessageDialog(null, "You already win, no need solution ^^");
				return;
			}
			if (JOptionPane.showConfirmDialog(null, "Are you sure to show solutions?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				gameIsRunning = false;
				view.timerPause();
				printSolution();
				view.getDesign().setSelected(true);
				view.getDesign().doClick();
				view.getPlay().setEnabled(false);
				view.getSave().setEnabled(false);
				view.getLoad().setEnabled(false);
				view.getDim().setEnabled(false);
				view.getDisplay().setEnabled(false);
				view.getFormat().setEnabled(false);
				new GameIcon(GameApp.LOSE_ICON);
				hint();
			}
		}
	}
	
	private void hint() {
		JOptionPane.showMessageDialog(null, "You can start a new game by selecting \"New Game\" in menu bar");
	}
	
	/**
	 * design new game for the grid
	 */
	private void designNewGame() {
		view.getFormat().setSelectedIndex(0);
		view.getDesign().setSelected(true);
		view.getDesign().setEnabled(false);
		view.getPlay().setEnabled(true);
		view.getSave().setEnabled(true);
		view.getLoad().setEnabled(true);
		view.getPlay().setEnabled(true);
		view.getDim().setEnabled(true);
		view.getPoint().setEnabled(false);
		view.getTimer().setEnabled(false);
		view.getDisplay().setEnabled(true);
		view.getFormat().setEnabled(true);
		view.getRand().setEnabled(true);
		view.getDesign().doClick();
		view.getPoint().setText("0");
		view.getDetail().setText("");
		view.getTimer().setText("0");
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 0);
	}
	
	/**
	 * Handle each button whenever it is clicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (gameIsRunning) {
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
				view.timerPause();
				gameIsRunning = false;
				gameWin = true;
				GameIcon icon = new GameIcon(GameApp.WIN_ICON);
				if (JOptionPane.showConfirmDialog(null, "Design a new game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					designNewGame();
				}
				hint();
				icon.dispose();
				view.getStop().setEnabled(false);
				return;
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Please choose \"Play\" mode to move the buttons");
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
		
		if(myY > 0) {
			//check the one above me
			if(matrix[myY -1][myX].getText().equalsIgnoreCase("0") ) {
				zeroButton = matrix[myY -1][myX];
				matrix[myY -1][myX] = self;
				self.putClientProperty("y", myY-1);
				self.putClientProperty("x", myX);
				
				matrix[myY][myX] = zeroButton;
				zeroButton.putClientProperty("y", myY);
				zeroButton.putClientProperty("x", myX);
				//POINTS
				countPoints(isNum, dimen, myY-1, myX);
				return true;
			}
		}
		//check the one to the right
		if( myX < (dimen-1) ) {
			if(matrix[myY][myX+1].getText().equalsIgnoreCase("0")) {
				zeroButton = matrix[myY][myX+1];				
				matrix[myY][myX+1] = self;
				self.putClientProperty("y", myY);
				self.putClientProperty("x", myX+1);
				
				matrix[myY][myX] = zeroButton;
				zeroButton.putClientProperty("y", myY);
				zeroButton.putClientProperty("x", myX);
				//POINTS
				countPoints(isNum, dimen, myY, myX+1);
				return true;
			}
		}
		//check the one below
		if( myY < (dimen -1) ) {
			if(matrix[myY+1][myX].getText().equalsIgnoreCase("0")) {
				zeroButton = matrix[myY+1][myX];
				matrix[myY+1][myX] = self;
				self.putClientProperty("y", myY+1);
				self.putClientProperty("x", myX);
				
				matrix[myY][myX] = zeroButton;
				zeroButton.putClientProperty("y", myY);
				zeroButton.putClientProperty("x", myX);
				//POINTS
				countPoints(isNum, dimen, myY+1, myX);
				return true;
			}
		}
		//check the one to the left
		if ( myX > 0 ) {
			if(matrix[myY][myX-1].getText().equalsIgnoreCase("0")) {
				zeroButton = matrix[myY][myX-1];
				matrix[myY][myX-1] = self;
				self.putClientProperty("y", myY);
				self.putClientProperty("x", myX-1);
				
				matrix[myY][myX] = zeroButton;
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
		    
	        try (FileWriter fw = new FileWriter(selectedFile)) {
	        	for (int i = 0; i < 3; i++) {
					if (i == 0) {
						System.out.println(view.getDimension());
						fw.write(view.getDimension() + "\n");
					}
					else if (i == 1) {
						if (view.isTypeNum()) {
							fw.append("Number"+ "\n");
						} else {
							fw.append("Text"+ "\n");
						}
					}
					else {
						int dimen = view.getDimension();
						String text = "";
						for (int j = 0; j < dimen*dimen; j++) {
							if (view.isTypeNum()) {
								text += model.getShuffleNum()[j] + " ";
							} else {
								text += model.getShuffleText()[j] + "";
							}
						}
						fw.append(text);
					}
	        	}
	        	fw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}

	/**
	 * load game configuration
	 */
	private void loadGameConfig() {
		Preferences pref = Preferences.userRoot();
		String path = pref.get("DEFAULT_PATH", "");
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setCurrentDirectory(new File(path));
		
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    try {
		    	BufferedReader br = new BufferedReader(new FileReader(selectedFile));
				int lineNum = 0;
				int dimen = 0;
				ArrayList<Integer> tempNum = null;
				ArrayList<Character> tempText = null;
				for (String line; (line = br.readLine()) != null; lineNum++) {
					switch (lineNum) {
					case 0:
						dimen = Integer.parseInt(line);
						view.getDim().setSelectedIndex(Integer.parseInt(line)-3);
						break;
					case 1:
						if (line.equals("Number")) {
							view.getFormat().setSelectedIndex(0);
							tempNum = new ArrayList<>();
						}
						else {
							view.getFormat().setSelectedIndex(1);
							tempText = new ArrayList<>();
						}
						break;
					case 2:
						String[] matrix = line.split(" ");
						for (int i = 0; i < dimen*dimen; i++) {
							if (view.isTypeNum()) {
								tempNum.add(Integer.parseInt(matrix[i]));
							} else {
								System.out.println(line.charAt(i));
								tempText.add(line.charAt(i));
							}
						}
						break;
					}
				}
				for (Character i : tempText) {
					System.out.print(i);
				}
				if (view.isTypeNum()) {
					model.setShuffleNum(tempNum.toArray(new Integer[tempNum.size()]));
				} else {
					model.setShuffleText(tempText.toArray(new Character[tempText.size()]));
				}
//				for (int i = 0; i < model.getShuffleNum().length; i++) {
//					System.out.print(model.getShuffleNum()[i] + " ");
//				}
				System.out.println(dimen);
				view.removeOldGrid(dimen);
				view.resetGrid(dimen, 2);
				br.close();
			} catch (FileNotFoundException e) {
				System.out.println("File cannot be found.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
			JOptionPane.showMessageDialog(null, "Text length is not large enough to store in grids dimension", "Error", 0);
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

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}

