package game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.util.prefs.Preferences;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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
	
	public static Color bgColor = null;
	private GameView view;
	private GameModel model;
	boolean isDesignMode = true;
	boolean gameIsRunning = false;
	
	public GameController() {}
	
	public GameController(GameView view, GameModel model) {
		GameController controller = new GameController();
		controller.view = view;
		controller.model = model;
		view.setController(controller);
		System.out.println("controller object view: " + controller.view);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//JMenuItems
		if (e.getSource() == view.getNewGame()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure to restart the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				actionNumShuffle();
			}
		}
		else if(e.getSource() == view.getSolution()) {
			 view.timerPause();
			 printSolution();
		}
		else if (e.getSource() == view.getExit()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure to quit the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		else if (e.getSource() == view.getColor()) {
			// JOption Pane to change color for buttons --> HAVE NOT FIGURED OUT YET
			System.out.println("Color");
			bgColor = JColorChooser.showDialog(view.getGrids(),"Select a background color", Color.WHITE);
			
			// change color function panel
			for (int i = 0; i < view.getDimension(); i++) {
				for (int j = 0; j < view.getDimension(); j++) {
					if(view.getMatrixButton(i, j).getText().equalsIgnoreCase("0")) {
						continue;
					}
					view.getMatrixButton(i, j).setBackground(bgColor);
//					view.getMatrixButton(i, j).setFont(new Font("Dialog", Font.BOLD, 30));
//					view.getMatrixButton(i, j).setOpaque(true);
//					view.getMatrixButton(i, j).setBorderPainted(false);
//					view.getMatrixButton(i, j).setForeground(Color.BLACK);
//					view.getMatrixButton(i, j).setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}
		}
		else if (e.getSource() == view.getAbout()) {
			new GameIcon(GameApp.DEFAULT_ICON);
		}
		
		//Play/Design Mode
		if (e.getSource() == view.getPlay() || view.getPlay().isSelected()) {
			System.out.println("Play is selected");
			view.getSave().setEnabled(false);
			view.getLoad().setEnabled(false);
			view.getPlay().setEnabled(false);
			view.getSetText().setEnabled(false);
			view.getDim().setEnabled(false);
			view.getLevel().setEnabled(false);
			view.getDisplay().setEnabled(false);
			view.getRand().setEnabled(false);
			view.getFormat().setEnabled(false);
		}
		else if (e.getSource() == view.getDesign() || view.getDesign().isSelected()) {
			// Disable some buttons in the functionPanel
			System.out.println("Design is selected");
			view.getSave().setEnabled(true);
			view.getLoad().setEnabled(true);
			view.getPlay().setEnabled(true);
			view.getSetText().setEnabled(true);
			view.getDim().setEnabled(true);
			view.getLevel().setEnabled(true);
			view.getDisplay().setEnabled(true);
			view.getRand().setEnabled(true);
			view.getFormat().setEnabled(true);
			view.getDesign().setEnabled(false);
			if (e.getSource() == view.getDisplay()) {
				levels();
				if (view.isTypeNum()) { actionNumDisplay(); }
				else if (!view.isTypeNum()) { actionTextDisplay(); }
			}
		}
		if (e.getSource() == view.getLoad()) { loadGameConfig(); }
		else if (e.getSource() == view.getSave()) { saveGameConfig(); }
		else if (e.getSource() == view.getSetText()) { actionSetText(); }
		else if (e.getSource() == view.getRand()) { 
			if (view.isTypeNum()) { actionNumShuffle(); }
			else if (!view.isTypeNum()) { actionTextShuffle(); }
		}
		else if (e.getSource() == view.getStop()) {
			if (JOptionPane.showConfirmDialog(null, "Stop the game and restart?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//change active to design
				//restart the game
				view.timerPause();
				view.getDesign().setEnabled(true);
				view.resetGrid(GameApp.DEFAULT_DIM, 0);
				view.resetGrid(view.getDimension(), 1);
				view.getStop().setEnabled(false);
				new GameIcon(GameApp.FINISHED_ICON);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked");
		System.out.println(((JButton) e.getSource()).getText());
		JButton self = (JButton) e.getSource();
		if(swapZero(self)) {
			//count points here.
		}
		reloadGrid(view.getMatrix());
		view.getGrids().revalidate();
		for (int i = 0; i < view.getMatrix()[0].length; i++) {
			for (int j = 0; j < view.getMatrix()[0].length; j++) {
				if(view.getMatrix()[i][j].getText().equalsIgnoreCase("0"))
					view.getMatrix()[i][j].requestFocus();
			}
		}		
	}
	
	private void reloadGrid(JButton[][] matrix) {
		view.getGrids().removeAll();
		for (int y = 0; y < matrix[0].length ; y++) {
			for (int x = 0; x < matrix[0].length; x++) {				
				view.getGrids().add(matrix[y][x]);
			}
		}
	}
	
	private boolean swapZero(JButton self) {
		JButton[][] matrix = view.getMatrix();
		int gridSize = view.getDimension();
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
				model.swapPosInArray(isNum, gridSize, myY-1, myX, myY, myX);
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
				countPoints(isNum, gridSize, myY-1, myX, myY, myX);
				return true;
			}
		}
		//check the one to the right
		if( myX < (gridSize-1) ) {
			System.out.println("checking my right");
			if(matrix[myY][myX+1].getText().equalsIgnoreCase("0")) {
				System.out.println("found right");
				model.swapPosInArray(isNum, gridSize, myY, myX+1, myY, myX);
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
				countPoints(isNum, gridSize, myY, myX+1, myY, myX);
				return true;
			}
		}
		//check the one below
		if( myY < (gridSize -1) ) {
			System.out.println("checking below me");
			if(matrix[myY+1][myX].getText().equalsIgnoreCase("0")) {
				System.out.println("found below");
				model.swapPosInArray(isNum, gridSize, myY+1, myX, myY, myX);
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
				countPoints(isNum, gridSize, myY+1, myX, myY, myX);
				return true;
			}
		}
		//check the one to the left
		if ( myX > 0 ) {
			System.out.println("checking my left");
			if(matrix[myY][myX-1].getText().equalsIgnoreCase("0")) {
				System.out.println("found left");
				model.swapPosInArray(isNum, gridSize, myY, myX-1, myY, myX);
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
				countPoints(isNum, gridSize, myY, myX-1, myY, myX);
				return true;
			}
		}
		return false;
	}
	
	private int countPoints(boolean isNum, int dimen, int shuffleY, int shuffleX, int solY, int solX) {
		int points = model.countPoints(isNum, dimen, shuffleY, shuffleX, solY, solX);
		model.setPoints(points);
		view.getPoint().setText(Integer.toString(points));
		if (points == GameApp.WIN) {
			new GameIcon(GameApp.WIN_ICON);
		}
		return points;
	}
	
//	private int countPoints(JButton[][] matrix, Character[] target) {
//		int points = 0;
//		//black square needs to be at the end but the black square is the first character in target
//		//create a character string representation of the numbers to pass in here
//		for (int y = 0; y < matrix[0].length ; y++) {
//			for (int x = 0; x < matrix[0].length; x++) {				
//				if( ( 1+x+y < target.length ) && ( matrix[x][y].getText().charAt(0) == target[1+x+y] ) ) {
//					points += 10;
//				}
//			}
//		}
//		return points;
//	}
	
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
	
	private void printSolution() {
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 1);
		view.getStop().setEnabled(false);
	}
	
	private void levels() {
		if (view.getLevel().getSelectedItem() == "Easy") {
//			view.startTimer();
//			timer(600000, false);
		}
		else if (view.getLevel().getSelectedItem() == "Medium") {
//			view.startTimer();
//			timer(300000, false);
		}
		else if (view.getLevel().getSelectedItem() == "Hard") {
//			view.startTimer();
//			timer(120000, false);
		}
		else {
//			view.startTimer();
//			timer(600000, true);
		}
	}
	
	private void actionNumDisplay() {
		model.calSolutionNum(view.getDimension());
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 1);
	}
	
	private void actionNumShuffle() {
		gameIsRunning = true;
		if(!view.timerRunning) {
			view.startTimer();
		}
		model.shuffleNum(view.getDimension());
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 2);
	}
	private void actionNumRestartSelected() {
		if(gameIsRunning || view.timerRunning) {
			view.timerPause();
			gameIsRunning = false;
		}
		view.getPlay().setEnabled(true);
	}
	
	private void actionTextDisplay() {
		view.removeOldGrid(view.getDimension());
		view.displayTextInput();
		view.resetGrid(view.getDimension(), 0);
	}
	
	private void actionSetText() {
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
	
	private void actionTextShuffle() {
		gameIsRunning = true;
		if(!view.timerRunning) {
			view.startTimer();
		}
		String text = view.getTextField().getText();
		model.shuffleText(text, view.getDimension());
		view.removeOldGrid(view.getDimension());
		view.resetGrid(view.getDimension(), 2);
		view.getDetail().setText(text);
		view.getTextField().setText("");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

