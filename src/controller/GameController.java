package controller;

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
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import game.GameBasic;
import game.GameIcon;
import model.GameModel;
import view.GameView;

import java.awt.event.MouseEvent;
/**
 * Purpose: This class is the controller of the game
 * File name: GameController.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 4 Dec 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: GameController.java
 * Method list: to many to write :)
 * Purpose: This class is the controller of the game
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameController implements ActionListener, MouseListener {
	
	/**
	 * background color for buttons
	 */
	public static Color bgColor = null;
	/**
	 * view object for GameView
	 */
	private GameView gameView;
	/**
	 * model object for GameModel
	 */
	private GameModel gameModel;
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
	private boolean isArrayShuffle = false;
	/**
	 * points calculated by controller
	 */
	private int points = 0;
	/**
	 * Seconds for timer task
	 */
	private int seconds = 0;
	/**
	 * Timer
	 */
	private Timer gameTimer = new Timer();
	/**
	 * Timer Task
	 */
	private TimerTask timerTask;
	/**
	 * Check if timer is running
	 */
	private boolean timerRunning = false;
	/**
	 * getter for game running
	 * @return true if game is running, false if not
	 */
	public void setArrayShuffle(boolean isArrayShuffle) { this.isArrayShuffle = isArrayShuffle; }
	
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
		controller.gameView = view;
		controller.gameModel = model;
		view.setController(controller);
	}
	
	/**
	 * overloading method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// load configuration button is clicked
		if (e.getSource() == gameView.getLoad()) {
			isArrayShuffle = true;
			loadGameConfig(); 
			return;
		}
		// save configuration button is clicked
		else if (e.getSource() == gameView.getSave()) { 
			saveGameConfig(); 
			return; 
		}
		//JMenuItems exit
		else if (e.getSource() == gameView.getExit()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure to quit the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				gameView.dispose();
			}
		}
		//JMenuItems color
		else if (e.getSource() == gameView.getColor()) {
			// JOption Pane to change color for buttons
			bgColor = JColorChooser.showDialog(gameView.getGrids(),"Select a background color", Color.WHITE);
			
			// change color function panel
			for (int i = 0; i < gameView.getDimension(); i++) {
				for (int j = 0; j < gameView.getDimension(); j++) {
					if(gameView.getMatrixButton(i, j).getText().equalsIgnoreCase("0")) {
						continue;
					}
					gameView.getMatrixButton(i, j).setBackground(bgColor);
				}
			}
		}
		//JMenuItem about
		else if (e.getSource() == gameView.getAbout()) {
			new GameIcon(GameBasic.DEFAULT_ICON);
			return;
		}
		
		//type text is selected
		if (gameView.getFormat().getSelectedItem().equals("Text") && !gameIsRunning && !isArrayShuffle) { actionSetText(); }

		// play mode is clicked
		if (e.getSource() == gameView.getPlay()) {
			gameWin = false;
			gameIsRunning = true;
			gameView.getSave().setEnabled(false);
			gameView.getLoad().setEnabled(false);
			gameView.getPlay().setEnabled(false);
			gameView.getDim().setEnabled(false);
			gameView.getDisplay().setEnabled(false);
			gameView.getFormat().setEnabled(false);
			gameView.getStop().setEnabled(true);
			gameView.getPoint().setEnabled(false);
			gameView.getTimer().setEnabled(false);
			gameView.getRand().setEnabled(false);
			if(!timerRunning) {
				startTimer();
			}
			if (!isArrayShuffle) {
				// shuffle array
				if (gameView.isTypeNum()) { actionNumShuffle(); }
				else if (!gameView.isTypeNum()) { actionTextShuffle(); }
			}
		}
		// design mode is clicked or selected
		else if (e.getSource() == gameView.getDesign() || gameView.getDesign().isSelected()) {
			gameView.getSave().setEnabled(true);
			gameView.getLoad().setEnabled(true);
			gameView.getPlay().setEnabled(true);
			gameView.getDim().setEnabled(true);
			gameView.getDisplay().setEnabled(true);
			gameView.getFormat().setEnabled(true);
			gameView.getDesign().setEnabled(false);
			gameView.getStop().setEnabled(false);
			gameView.getPoint().setEnabled(false);
			gameView.getTimer().setEnabled(false);
			gameView.getRand().setEnabled(true);
			// calculate solution number array
			gameModel.calSolutionNum(gameView.getDimension());
			// display number or text in right order in the grid
			if (e.getSource() == gameView.getDisplay()) {
				if (gameView.isTypeNum()) { actionNumDisplay(); }
				else if (!gameView.isTypeNum()) { actionTextDisplay(); }
			}
			// random number
			else if (e.getSource() == gameView.getRand()) {
				// shuffle array
				if (gameView.isTypeNum()) { actionNumShuffle(); }
				else if (!gameView.isTypeNum()) { actionTextShuffle(); }
				isArrayShuffle = true;
			}
		}
		
		// stop button is clicked to pause and restart the game
		if (e.getSource() == gameView.getStop()) {
			if (JOptionPane.showConfirmDialog(null, "Stop and restart the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				timerPause();
				gameIsRunning = false;
				timerRunning = false;
				isArrayShuffle = false;
				gameView.getStop().setEnabled(false);
				if (gameView.isTypeNum()) { gameModel.setShuffleNum(null); }
				else if (!gameView.isTypeNum()) { gameModel.setShuffleText(null); }
				GameIcon icon = new GameIcon(GameBasic.LOSE_ICON);
				if (JOptionPane.showConfirmDialog(null, "Design a new game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					icon.dispose();
					designNewGame();
				}
				hint();
			}
		}
		//JMenuItems new game
		else if (e.getSource() == gameView.getNewGame()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure to restart the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				gameIsRunning = false;
				isArrayShuffle = false;
				designNewGame();
			}
			return;
		}
		// solution button is clicked to show solution array
		else if(e.getSource() == gameView.getSolution()) {
			if (gameView.getDesign().isSelected()) {
				JOptionPane.showMessageDialog(null, "Sorry, you cannot show solution in Design Mode", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else if (gameWin) {
				JOptionPane.showMessageDialog(null, "You already win, no need solution ^^");
				return;
			}
			if (JOptionPane.showConfirmDialog(null, "Are you sure to show solutions?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				gameIsRunning = false;
				isArrayShuffle = false;
				timerPause();
				printSolution();
				gameView.getDesign().setSelected(true);
				gameView.getDesign().doClick();
				gameView.getPlay().setEnabled(false);
				gameView.getSave().setEnabled(false);
				gameView.getLoad().setEnabled(false);
				gameView.getDim().setEnabled(false);
				gameView.getDisplay().setEnabled(false);
				gameView.getFormat().setEnabled(false);
				if (gameView.isTypeNum()) { gameModel.setShuffleNum(gameModel.getSolNum()); }
				else if (!gameView.isTypeNum()) { gameModel.setShuffleText(gameModel.getSolText()); }
				new GameIcon(GameBasic.LOSE_ICON);
				hint();
			}
		}
	}
		
	/**
	 * show hint for users after they end a game
	 */
	private void hint() {
		JOptionPane.showMessageDialog(null, "You can start a new game by selecting \"New Game\" in menu bar");
	}
	
	/**
	 * design new game for the grid
	 */
	private void designNewGame() {
		gameIsRunning = false;
		isArrayShuffle = false;
		gameView.getFormat().setSelectedIndex(0);
		gameView.getDesign().setSelected(true);
		gameView.getDesign().setEnabled(false);
		gameView.getPlay().setEnabled(true);
		gameView.getSave().setEnabled(true);
		gameView.getLoad().setEnabled(true);
		gameView.getPlay().setEnabled(true);
		gameView.getDim().setEnabled(true);
		gameView.getPoint().setEnabled(false);
		gameView.getTimer().setEnabled(false);
		gameView.getDisplay().setEnabled(true);
		gameView.getFormat().setEnabled(true);
		gameView.getRand().setEnabled(true);
		gameView.getDesign().doClick();
		gameView.getPoint().setText("0");
		gameView.getDetail().setText("");
		gameView.getTimer().setText("0");
		gameView.removeOldGrid(gameView.getDimension());
		gameView.resetGrid(gameView.getDimension(), 0);
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
			reloadGrid(gameView.getMatrix());
			gameView.getGrids().revalidate();
			gameView.getGrids().repaint();
			for (int i = 0; i < gameView.getDimension(); i++) {
				for (int j = 0; j < gameView.getDimension(); j++) {
					if(gameView.getMatrix()[i][j].getText().equalsIgnoreCase("0"))
						gameView.getMatrix()[i][j].requestFocusInWindow();
				}
			}
			// compare the whole grids with solution array to determine if user solves the puzzle
			if (compareToSol(gameView.isTypeNum(), gameView.getDimension()) == GameBasic.WIN) {
				timerPause();
				gameIsRunning = false;
				gameWin = true;
				GameIcon icon = new GameIcon(GameBasic.WIN_ICON);
				if (JOptionPane.showConfirmDialog(null, "Design a new game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					designNewGame();
				}
				hint();
				icon.dispose();
				gameView.getStop().setEnabled(false);
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
		gameView.getGrids().removeAll();
		for (int y = 0; y < matrix[0].length ; y++) {
			for (int x = 0; x < matrix[0].length; x++) {				
				gameView.getGrids().add(matrix[y][x]);
			}
		}
	}
	
	/**
	 * Swap clicked button and zero button if possible
	 * @param self clicked button
	 * @return true: successfully swapped, false: isn't swappable
	 */
	private boolean swapZero(JButton self) {
		JButton[][] matrix = gameView.getMatrix();
		int dimen = gameView.getDimension();
		JButton zeroButton = null;
		int myY = (int)self.getClientProperty("y");
		int myX = (int)self.getClientProperty("x");
		boolean isNum = gameView.isTypeNum();
		
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
				gameModel.swap(gameView.isTypeNum(), dimen, myY-1, myX, myY, myX);
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
				gameModel.swap(gameView.isTypeNum(), dimen, myY, myX+1, myY, myX);
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
				gameModel.swap(gameView.isTypeNum(), dimen, myY+1, myX, myY, myX);
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
				gameModel.swap(gameView.isTypeNum(), dimen, myY, myX-1, myY, myX);
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
			if (Integer.parseInt(gameView.getMatrixButton(clickedY, clickedX).getText()) == gameModel.getSolNum()[clickedY * dimen + clickedX]) {
				points++;
			}
		} else {
			if (gameView.getMatrixButton(clickedY, clickedX).getText().equals(Character.toString(gameModel.getSolText()[clickedY * dimen + clickedX]))) {
				points++;
			}
		}
		gameView.getPoint().setText(String.valueOf(points));
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
					if (Integer.parseInt(gameView.getMatrixButton(i, j).getText()) == gameModel.getSolNum()[i * dimen + j])
						correctBtn++;
				}
				else {
					if (gameView.getMatrixButton(i, j).getText().equals(Character.toString(gameModel.getSolText()[i * dimen + j])))
						correctBtn++;
				}
			}
		}
		if (correctBtn == dimen*dimen) {
			return GameBasic.WIN;
		}
		return correctBtn;
	}
	
	/**
	 * print solution for number or text array
	 */
	private void printSolution() {
		gameView.removeOldGrid(gameView.getDimension());
		gameView.resetGrid(gameView.getDimension(), 1);
		gameView.getStop().setEnabled(false);
	}
	
	/**
	 * display number in ascending order in grid
	 */
	private void actionNumDisplay() {
		gameView.removeOldGrid(gameView.getDimension());
		gameView.resetGrid(gameView.getDimension(), 1);
	}
	
	/**
	 * shuffle number array
	 */
	private void actionNumShuffle() {
		gameModel.shuffleNum(gameView.getDimension());
		gameView.removeOldGrid(gameView.getDimension());
		gameView.resetGrid(gameView.getDimension(), 2);
	}
	
	/**
	 * display text input when user selects text type
	 */
	private void actionSetText() {
		gameView.removeOldGrid(gameView.getDimension());
		gameView.displayTextInput();
		gameView.resetGrid(gameView.getDimension(), 0);
	}
	
	/**
	 * display text in correct order in grid
	 */
	private void actionTextDisplay() {
		String text = gameView.getTextField().getText();
		int dim = gameView.getDimension();
		if (text.length() < dim*dim) {
			JOptionPane.showMessageDialog(null, "Text length is not large enough to store in grids dimension", "Error", 0);
			return;
		}
		gameModel.calSolutionText(text, gameView.getDimension());
		gameView.removeOldGrid(gameView.getDimension());
		gameView.resetGrid(gameView.getDimension(), 1);
	}
	
	/**
	 * shuffle text array
	 */
	private void actionTextShuffle() {
		String text = gameView.getTextField().getText();
		gameModel.shuffleText(text, gameView.getDimension());
		gameView.removeOldGrid(gameView.getDimension());
		gameView.resetGrid(gameView.getDimension(), 2);
		gameView.getDetail().setText(text);
		gameView.getTextField().setText("");
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
						fw.write(gameView.getDimension() + "\n");
					}
					else if (i == 1) {
						if (gameView.isTypeNum()) {
							fw.append("Number"+ "\n");
						} else {
							fw.append("Text"+ "\n");
						}
					}
					else {
						int dimen = gameView.getDimension();
						String text = "";
						for (int j = 0; j < dimen*dimen; j++) {
							if (gameView.isTypeNum()) {
								text += gameModel.getShuffleNum()[j] + " ";
							} else {
								text += gameModel.getShuffleText()[j] + "";
							}
						}
						fw.append(text);
					}
	        	}
	        } catch (IOException e) {
	        	JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
						gameView.getDim().setSelectedIndex(Integer.parseInt(line)-3);
						break;
					case 1:
						if (line.equals("Number")) {
							gameView.getFormat().setSelectedIndex(0);
							tempNum = new ArrayList<>();
						}
						else {
							gameView.getFormat().setSelectedIndex(1);
							tempText = new ArrayList<>();
						}
						break;
					case 2:
						String[] matrix = line.split(" ");
						for (int i = 0; i < dimen*dimen; i++) {
							if (gameView.isTypeNum()) {
								tempNum.add(Integer.parseInt(matrix[i]));
							} else {
								tempText.add(line.charAt(i));
							}
						}
						break;
					}
				}
				if (gameView.isTypeNum()) {
					gameModel.setShuffleNum(tempNum.toArray(new Integer[tempNum.size()]));
				} else {
					gameModel.setShuffleText(tempText.toArray(new Character[tempText.size()]));
				}
				gameView.removeOldGrid(dimen);
				gameView.resetGrid(dimen, 2);
				br.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * start timer and display timer on GUI
	 */
	public void startTimer() {
		seconds = 0;
		// Timer task
		timerTask = new TimerTask() {
			public void run() {
				seconds++;
				try {
					resetTimer(seconds);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);				
				}
			}
		};
		// Timer schedule
		if(!timerRunning) {
			try {
				gameTimer.scheduleAtFixedRate(timerTask, 0, 1000);
				timerRunning = true;
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	/**
	 * pause timer
	 */
	public void timerPause() {
		timerTask.cancel();
		timerRunning = false;
	}
	/**
	 * reset timer
	 * @param seconds seconds that timer should be reset to
	 */
	private void resetTimer(int seconds) {
		gameView.getTimer().setText(Integer.toString(seconds));
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

