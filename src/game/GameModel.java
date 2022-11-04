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
	private int emptyPosRow;
	private int emptyPosCol;
	private int clickedPosRow;
	private int clickedPosCol;
//	private int dimen;
	
	public Integer[] getShuffleNum() { return shuffleNum; }
	public Character[] getShuffleText() { return shuffleText; }
	public Integer[] getSolNum() { return solutionNum; }
	public Character[] getSolText() { return solutionText; }
	public int getClickedPosRow() {
		return clickedPosRow;
	}
	public int getClickedPosCol() {
		return clickedPosCol;
	}
	public int getEmptyPosRow() {
		return emptyPosRow;
	}
	public int getEmptyPosCol() {
		return emptyPosCol;
	}
	public void setClickedPosRow(int clickedPosRow) {
		this.clickedPosRow = clickedPosRow;
	}
	public void setClickedPosCol(int clickedPosCol) {
		this.clickedPosCol = clickedPosCol;
	}
	public void setEmptyPosRow(int emptyPosRow) {
		this.emptyPosRow = emptyPosRow;
	}
	public void setEmptyPosCol(int emptyPosCol) {
		this.emptyPosCol = emptyPosCol;
	}
	public void setSolNum(Integer[] solNum) { this.solutionNum = solNum; }
	public void setSolText(Character[] solText) { this.solutionText = solText; }
	public void setShuffleNum(Integer[] shuffleNum) { this.shuffleNum = shuffleNum; }
	public void setShuffleText(Character[] shuffleText) { this.shuffleText = shuffleText; }
//	public void setDimen(int dimen) { this.dimen = dimen; }
	
	public boolean isBtnMovable(String buttonLabel, int dimen) {
		for (int i = 0; i < shuffleNum.length; i++) {
			if (shuffleNum[i] == Integer.parseInt(buttonLabel)) {
				clickedPosRow = i / dimen; //row coordinates of button
				clickedPosCol = i % dimen; //col coordinates of button
				if (((clickedPosRow == emptyPosRow) && ((clickedPosCol == (emptyPosCol+1)) || (clickedPosCol == (emptyPosCol-1))))
					|| ((clickedPosCol == emptyPosCol) && ((clickedPosRow == (emptyPosRow+1)) || (clickedPosRow == (emptyPosRow-1))))) {
					return true;
				}
			}
		}
		return false;
	}
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
		
		calSolutionText(text);
		//convert input string to char
		ArrayList<Integer> chars = new ArrayList<>();
		for (int i = 0; i < text.length(); i++) {
			if (i == 0) {
				chars.add(0);
			}
			int c = text.charAt(i); 
            chars.add(c);
        }
		shuffle(size);
		// Assign back char from chars ArrayList to according index from shuffleNum
		Character[] temp = new Character[size];
		for (int i = 0; i < size; i++) {
			int num = chars.get(i);
			temp[shuffleNum[i]] = (char) num;
		}
		shuffleText = temp;
		
		//delete
		System.out.println("\nSize of shuffleChar arraylist: " + size);
		for (int i = 0; i < temp.length; i++) {
			System.out.print(temp[i] + " ");
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
	
	private void calSolutionText(String text) {
		ArrayList<Character> chars = new ArrayList<>();
		for (int i = 0; i < text.length(); i++) {
			chars.add(text.charAt(i)); 
        }
		solutionText = chars.toArray(new Character[chars.size()]);
		
		//delete
		for (int i = 0; i < solutionText.length; i++) {
			System.out.print(solutionText[i] + " ");
		}
	}
}
