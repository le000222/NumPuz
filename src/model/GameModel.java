package model;
import java.util.ArrayList;
import java.util.LinkedHashSet;
/**
 * Purpose: This class is the model of the game
 * File name: GameModel.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 2 Oct 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

import javax.swing.JOptionPane;

import game.GameBasic;

/**
 * Class Name: GameModel.java
 * Method list: main()
 * Constants list: frame
 * Purpose: This class is the model of the game
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameModel {

//	private int points;
	/**
	 * shuffled array for numbers
	 */
	private Integer[] shuffleNum;
	/**
	 * shuffled array for text
	 */
	private Character[] shuffleText;
	/**
	 * solution array for numbers
	 */
	private Integer[] solutionNum;
	/**
	 * solution array for text
	 */
	private Character[] solutionText;
	/**
	 * treeSet to store shuffle unique numbers
	 */
	private LinkedHashSet<Integer> treeSet;
	/**
	 * Game String Config received from Server
	 */
	public static String gameString = GameBasic.DEFAULT_GAMECONFIG;
	
	
	//getters
	/**
	 * getter for gameDat
	 * @return timer timer counts for a game
	 */
	public String getGameString() { return gameString; }
	/**
	 * getter for shuffle number array
	 * @return an array o shuffled number
	 */
	public Integer[] getShuffleNum() { return shuffleNum; }
	/**
	 * getter for shuffle text array
	 * @return an array of shuffled chars
	 */
	public Character[] getShuffleText() { return shuffleText; }
	/**
	 * getter for solution number array
	 * @return solution number array
	 */
	public Integer[] getSolNum() { return solutionNum; }
	/**
	 * getter for solution text array
	 * @return solution text array
	 */
	public Character[] getSolText() { return solutionText; }
	/**
	 * setter for shuffle number array
	 * @return an array o shuffled number
	 */
	public void setShuffleNum(Integer[] shuffleNum) { this.shuffleNum = shuffleNum; }
	/**
	 * setter for shuffle text array
	 * @return an array of shuffled chars
	 */
	public void setShuffleText(Character[] shuffleText) { this.shuffleText = shuffleText; }
	
	/**
	 * Shuffle numbers and assigned it to shuffleNum[]
	 * @param size size of whole grids (dim^2)
	 */
	private void shuffle(int size) {
		treeSet = new LinkedHashSet<>();
		while (treeSet.size() != size) {
			int random = (int) (Math.random() * size);
			treeSet.add(random);
		}
		shuffleNum = treeSet.toArray(new Integer[treeSet.size()]);
	}

	/**
	 * Call shuffle functions to shuffle the number
	 * @param newDim new dimension selected by users
	 */
	public void shuffleNum(int newDim) {
		int size = newDim * newDim;
		shuffle(size);
	}
	
	/**
	 * Shuffle the text using shuffled array of shuffleNum[] to assign back to shuffleText[]
	 * @param text text input entered by users
	 * @param dim grid dimension selected by users
	 */
	public void shuffleText(String text, int dim) {
		int size = dim * dim;
		
		shuffle(size);
		shuffleText = new Character[size];
		// Assign back char from chars ArrayList to according index from shuffleNum
		for (int i = 0; i < size; i++) {
			shuffleText[i] = solutionText[shuffleNum[i]];
		}		
	}
	
	/**
	 * calculate solution for number array
	 * @param dim dimension selected by users
	 */
	public void calSolutionNum(int dim) {
		int size = dim * dim; 
		treeSet = new LinkedHashSet<>();
		for (int i = 1; i <= size; i++) {
			if (i == size) {
				treeSet.add(0);
			}
			treeSet.add(i);
		}
		solutionNum = treeSet.toArray(new Integer[treeSet.size()]);
	}
	
	/**
	 * calculate solution for text array
	 * @param text text entered by users from view
	 * @param dim dimension selected by users
	 */
	public void calSolutionText(String text, int dim) {
		ArrayList<Character> chars = new ArrayList<>();
		int i;
		for (i = 0; ( i < text.length() ) && ( (i+1) < (dim*dim) ) ; i++) {
			chars.add(text.charAt(i)); 
        }
		chars.add('0');
		solutionText = chars.toArray(new Character[chars.size()]);
	}
	
	public void swap(boolean isTypeNum, int dimen, int selfY, int selfX, int clickedY, int clickedX) {
		if (isTypeNum) {
			shuffleNum[selfY*dimen + selfX] = shuffleNum[clickedY*dimen + clickedX];
			shuffleNum[clickedY*dimen + clickedX] = 0;
		} else {
			shuffleText[selfY*dimen + selfX] = shuffleText[clickedY*dimen + clickedX];
			shuffleText[clickedY*dimen + clickedX] = 0;
		}
	}
	
	public void handleReceiveConfig(int gameDim, String gameType, String[] gameStringArray) {
		switch (gameType) {
		case "Number": // take game config from server as shuffle array and find solution later
			treeSet = new LinkedHashSet<>();
			for (int i = 0; i < gameStringArray.length; i++) {
				treeSet.add(Integer.parseInt(gameStringArray[i]));
			}
			shuffleNum = treeSet.toArray(new Integer[treeSet.size()]);
			calSolutionNum(gameDim);
			break;
		case "Text": // take game config from server as solution and shuffle later
			calSolutionText(gameString, gameDim);
			shuffleText(gameString, gameDim);
			break;
		default:
			JOptionPane.showMessageDialog(null, "Invalid game type", "Error", JOptionPane.ERROR_MESSAGE);	
		}
	}
}
