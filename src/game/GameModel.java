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

	private int points;
	private Integer[] shuffleNum;
	private Character[] shuffleText;
	private Integer[] solutionNum;
	private Character[] solutionText;
	private LinkedHashSet<Integer> treeSet;
	
	public int getPoints() { return points; }
	public Integer[] getShuffleNum() { return shuffleNum; }
	public Character[] getShuffleText() { return shuffleText; }
	public Integer[] getSolNum() { return solutionNum; }
	public Character[] getSolText() { return solutionText; }
	public void setPoints(int points) { this.points = points; }
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
		shuffle(size);
	}
	
	/**
	 * Shuffle the text in int and assign it to shuffle array
	 * @param size size of grid
	 * @return void
	 */
	public boolean shuffleText(String text, int dim) {
		int size = dim * dim;
		
		shuffle(size);
		shuffleText = new Character[size];
		for (int i = 0; i < solutionText.length; i++) {
			System.out.print("["+solutionText[i]+"]");
		}
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
	
	private void swapPosInArray(boolean isNum, int dimen, int zeroY, int zeroX, int clickedY, int clickedX) {
		System.out.println(dimen + " " + zeroY + " " + zeroX + " " + clickedY + " " + clickedX);
		System.out.println((zeroY * dimen + zeroX) + " " + (clickedY * dimen + clickedX));
		if (isNum) {
			for (int i = 0; i < shuffleNum.length; i++) {
				System.out.print(shuffleNum[i] + " ");
			}
			shuffleNum[zeroY * dimen + zeroX] = shuffleNum[clickedY * dimen + clickedX];
			shuffleNum[clickedY * dimen + clickedX] = 0;
		}
		else {
			for (int i = 0; i < shuffleText.length; i++) {
				System.out.print(shuffleText[i] + " ");
			}
			shuffleText[zeroY * dimen + zeroX] = shuffleText[clickedY * dimen + clickedX];
			shuffleText[clickedY * dimen + clickedX] = 0;
		}
//		for (int i = 0; i < shuffleText.length; i++) {
//			System.out.print(shuffleText[i] + " ");
//		}
	}
	
	private int countPoints(boolean isNum, int dimen, int zeroY, int zeroX, int clickedY, int clickedX) {
		if (isNum) {
			if ((shuffleNum[zeroY * dimen + zeroX] == solutionNum[zeroY * dimen + zeroX])
					|| (shuffleNum[clickedY * dimen + clickedX] == solutionNum[clickedY * dimen + clickedX])) {
				points += 1;
				System.out.println("POINTS= " + points);
			}
			if (shuffleNum == solutionNum) {
				return GameApp.WIN;
			}
		}
		else {
			if ((shuffleText[zeroY * dimen + zeroX] == solutionText[zeroY * dimen + zeroX])
					|| (shuffleText[clickedY * dimen + clickedX] == solutionText[clickedY * dimen + clickedX])) {
				points += 1;
				System.out.println("POINTS= " + points);
			}
			if (shuffleText == solutionText) {
				return GameApp.WIN;
			}
		}
		return points;
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
		for (int i = 0; i < size; i++) {
			System.out.print(solutionNum[i] + " ");
		}
		System.out.println();
	}
	
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
