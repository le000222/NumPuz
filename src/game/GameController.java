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
import javax.swing.event.MenuEvent;
import java.awt.event.MouseEvent;
import javax.swing.Timer; 
import java.util.TimerTask;
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
	
	private GameView view;
	private GameModel model;
	boolean isDesignMode = true;
//	private int dim = GameApp.DEFAULT_DIM;
	private int limitTime = 0;
	private JButton button;
	
	public GameController() {}
	
	public GameController(GameView view, GameModel model) {
		GameController controller = new GameController();
//		this.view = view;
//		this.model = model;
//		System.out.println("This class view: " + this.view);
		controller.view = view;
		controller.model = model;
		view.setController(controller);
		System.out.println("controller object view: " + controller.view);
//		start();
	}

//	public void start() {
//		view.getDesign().addActionListener(this);
//		view.getPlay().addActionListener(this);
//		view.getDim().addActionListener(this);
//		view.getFormat().addActionListener(this);
//		view.getDesign().addActionListener(this);
//		view.getSetText().addActionListener(this);
//		view.getSave().addActionListener(this);
//		view.getLoad().addActionListener(this);
//		view.getAbout().addActionListener(this);
//		view.getSolution().addActionListener(this);
//		
//		view.getNewGame().addActionListener(new MenuItem());
//		view.getExit().addActionListener(new MenuItem());
//		view.getColor().addActionListener(new MenuItem());
//		view.getAbout().addActionListener(new MenuItem());
//	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//JMenuItems
		if (e.getSource() == view.getNewGame()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure to restart the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				actionNumSelected();
			}
		}
		else if(e.getSource() == view.getSolution()) {
			 System.out.println("Solution");
//			 printSolution();
		}
		else if (e.getSource() == view.getExit()) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure to quit the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		else if (e.getSource() == view.getColor()) {
			// JOption Pane to change color for buttons --> HAVE NOT FIGURED OUT YET
			Color color = JColorChooser.showDialog(view.getGrids(),"Select a background color", Color.WHITE);
			
			// change color function panel
			view.getFunction().setBackground(color);
			view.getSupport1().setBackground(color);
			view.getSupport2().setBackground(color);
			view.getSupport3().setBackground(color);
			view.getSupport4().setBackground(color);
			view.getSupport5().setBackground(color);
		}
		else if (e.getSource() == view.getAbout()) {
			System.out.println("About the game");
		}
		
		//Play/Design Mode
		if (e.getSource() == view.getLoad()) { loadGameConfig(); }
		else if (e.getSource() == view.getSave()) { saveGameConfig(); }
		else if (e.getSource() == view.getSetText()) { actionSetTextButton(); }
		if (view.getPlay().isSelected()) {
			view.getSave().setEnabled(false);
			view.getLoad().setEnabled(false);
			view.startTimer();
			levels();
			if (view.isTypeNum()) { actionNumSelected(); }
			else if (!view.isTypeNum()) { actionTextSelected(); }
		}
		else if (view.getDesign().isSelected()) {
			//Disable some buttons in the functionPanel
			if(view.timerRunning) {
				view.timerPause();
			} 
			view.getSave().setEnabled(true);
			view.getLoad().setEnabled(true);
		}
		if (e.getSource() == button) {
			System.out.println("button clicked");
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked");
		System.out.println(((JButton) e.getSource()).getText());
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
	
	private void printSolution() {
		if (view.isTypeNum()) {
			model.printSolution(true);
		}
		else {
			model.printSolution(false);
		}
	}
	
	private void levels() {
		if (view.getLevel().getSelectedItem() == "Easy") {
//			timer(600000, false);
		}
		else if (view.getLevel().getSelectedItem() == "Medium") {
//			timer(300000, false);
		}
		else if (view.getLevel().getSelectedItem() == "Hard") {
//			timer(120000, false);
		}
		else {
//			timer(600000, true);
		}
	}
	
//	private void timer(int limit, boolean isClassic) {
//		int second = limit;
//		Timer timer = new Timer(1000, new ActionListener() {
//			public void actionPerformed(ActionEvent event) {}
//		});
//		timer.start();
//		try {
//			if (!isClassic) {
//				view.getTimer().setText("" + second--);
//			}
//			else {
//				view.getTimer().setText("" + second);
//				second++;
//			}
//			System.out.println(second);
//			Thread.sleep(limit);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		timer.stop();
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
	
//	public void startTimer() { // Timer task
//		timerTask = new TimerTask() { 
//		public void run() { 
//			seconds++; // Update your interface 
//			} 
//		}; 
//		try { 
//			timer.scheduleAtFixedRate(timerTask, 0, 1000); 
//			} catch(Exception e) { // Eventual treatment 
//				System.out.println();
//			}
//		}
//	}

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
	
	class Button implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button) {
				System.out.println("button clicked");
			}
		}
	}
	
//	class MenuItem implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			//JMenuItem
//			if (e.getSource() == view.getNewGame()) {
//				if (JOptionPane.showConfirmDialog(null, "Are you sure to restart the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//					actionNumSelected();
//				}
//			}
//			else if(e.getSource() == view.getSolution()) {
//				 System.out.println("clicked");
////				 printSolution();
//			}
//			else if (e.getSource() == view.getExit()) {
//				if (JOptionPane.showConfirmDialog(null, "Are you sure to quit the game?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//					System.exit(0);
//				}
//			}
//			else if (e.getSource() == view.getColor()) {
//				// JOption Pane to change color for buttons --> HAVE NOT FIGURED OUT YET
//				Color color = JColorChooser.showDialog(view.getGrids(),"Select a background color", Color.WHITE);
//				
//				// change color function panel
//				view.getFunction().setBackground(color);
//				view.getSupport1().setBackground(color);
//				view.getSupport2().setBackground(color);
//				view.getSupport3().setBackground(color);
//				view.getSupport4().setBackground(color);
//				view.getSupport5().setBackground(color);
//			}
//			else if (e.getSource() == view.getAbout()) {
//				System.out.println("About the game");
//			}
//		}
//	}
}

