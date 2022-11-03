package game;

import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GameMenuBar implements ActionListener, MenuListener {
	
	private JFrame frame;
	
	public GameMenuBar(JFrame frame) {
		this.frame = frame;
		menuBar();
	}
	/**
	 * Store all components that make up the menu bar panel (game, help)
	 * @return JPanel of all tabs
	 */
	public JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		gameMenu.addMenuListener(this);
		gameMenu.add(new JMenuItem("New Game"));
		gameMenu.add(new JMenuItem("How to play"));
		gameMenu.add(new JMenuItem("Settings"));
		gameMenu.add(new JMenuItem("Exit "));
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		helpMenu.add(new JMenuItem("About"));
		helpMenu.add(new JMenuItem("Color"));
		helpMenu.add(new JMenuItem("Search"));
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}

	@Override
	public void menuSelected(MenuEvent e) {
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		System.out.println("File menu item is deselected");		
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		System.out.println("File menu is canceled");		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String arg = e.getActionCommand();
		System.out.println(arg);
		if (arg.equals("Exit")) {
			frame.dispose();
			///System.exit(0);
		}
		
	}
}
