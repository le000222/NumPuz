package game;

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
	
//	public int getPoints() { return points; }
//	public void setPoints(int points) { this.points = points; }
	
	//getters
	public Integer[] getShuffleNum() { return shuffleNum; }
	public Character[] getShuffleText() { return shuffleText; }
	public Integer[] getSolNum() { return solutionNum; }
	public Character[] getSolText() { return solutionText; }
	
	/**
	 * Shuffle numbers and assigned it to shuffleNum[]
	 * @param size size of whole grids (dim^2)
	 */
	private void shuffle(int size) {
		System.out.println(size);
		treeSet = new LinkedHashSet<>();
		while (treeSet.size() != size) {
			int random = (int) (Math.random() * size);
			treeSet.add(random);
		}
		shuffleNum = treeSet.toArray(new Integer[treeSet.size()]);
		for (int i = 0; i < shuffleNum.length; i++) {
			System.out.print(shuffleNum[i] + " ");
		}
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
	 * @param size size of grid
	 */
	public boolean shuffleText(String text, int dim) {
		int size = dim * dim;
		
		shuffle(size);
		shuffleText = new Character[size];
		// Assign back char from chars ArrayList to according index from shuffleNum
		for (int i = 0; i < size; i++) {
			shuffleText[i] = solutionText[shuffleNum[i]];
		}		
		//delete
		System.out.println("\nSize of shuffleText character array: " + size);
		for (int i = 0; i < shuffleText.length; i++) {
			System.out.print("["+shuffleText[i]+"]");
		}
		return true;
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
		
		//delete
		for (i = 0; i < solutionText.length; i++) {
			System.out.print("["+solutionText[i]+"]" + " ");
		}
	}
}
