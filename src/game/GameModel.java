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

	private Integer[] shuffleNum;
	private Character[] shuffleText;
	private Integer[] solutionNum;
	private Character[] solutionText;
	private LinkedHashSet<Integer> treeSet;
	
	public Integer[] getShuffleNum() { return shuffleNum; }
	public Character[] getShuffleText() { return shuffleText; }
	public Integer[] getSolNum() { return solutionNum; }
	public Character[] getSolText() { return solutionText; }
	public void setSolNum(Integer[] solNum) { this.solutionNum = solNum; }
	public void setSolText(Character[] solText) { this.solutionText = solText; }
	public void setShuffleNum(Integer[] shuffleNum) { this.shuffleNum = shuffleNum; }
	public void setShuffleText(Character[] shuffleText) { this.shuffleText = shuffleText; }
	
	/**
	 * Call shuffle functions to shuffle the number
	 * @param newDim new dimension updated in GUI
	 */
	public void shuffleNum(int newDim) {
		int size = newDim * newDim;
		calSolutionNum(size);
		shuffle(size);
	}
	
	/**
	 * Shuffle the text in int and assign it to shuffle array
	 * @param size size of grid
	 * @return void
	 */
	public boolean shuffleText(String text, int dim) {
		int size = dim * dim;
		
		calSolutionText(text,dim);
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
	
	public void printSolution(boolean isNum) {
		if (isNum) {
			for (int i = 0; i < solutionNum.length; i++) {
				System.out.print(solutionNum[i] + " ");
			}
		} else {
			for (int i = 0; i < solutionText.length; i++) {
				System.out.print(solutionText[i] + " ");
			}
		}
	}
	
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
	
	private void calSolutionNum(int size) {
		treeSet = new LinkedHashSet<>();
		for (int i = 0; i < size; i++) {
			treeSet.add(i);
		}
		solutionNum = treeSet.toArray(new Integer[treeSet.size()]);
	}
	
	private void calSolutionText(String text, int dim) {
		ArrayList<Character> chars = new ArrayList<>();
		chars.add('0');
		int i;
		for (i = 0; ( i < text.length() ) && ( (i+1) < (dim*dim) ) ; i++) {
			chars.add(text.charAt(i)); 
        }
		while(i+1 < dim*dim) {
			chars.add(' ');
		}
		solutionText = chars.toArray(new Character[chars.size()]);
		
		
		//delete
		for (i = 0; i < solutionText.length; i++) {
			System.out.println("["+solutionText[i]+"]");
		}
	}
}
