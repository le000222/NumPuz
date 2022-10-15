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
	
	public Integer[] getShuffleNum() { return shuffleNum; }
	public Character[] getShuffleText() { return shuffleText; }
	public void setShuffleNum(Integer[] shuffleNum) { this.shuffleNum = shuffleNum; }
	public void setShuffleText(Character[] shuffleText) { this.shuffleText = shuffleText; }
	
	private void shuffle(int size) {
		System.out.println(size);
		LinkedHashSet<Integer> treeSet = new LinkedHashSet<>();
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
	 * Shuffle the buttons and attach to grids panel
	 * @param size size of grid
	 * @return void
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
		
		//convert input string to char
		ArrayList<Integer> chars = new ArrayList<>();
		for (int i = 0; i < text.length(); i++) {
			if (i == 0) {
				chars.add(0);
			}
			int c = text.charAt(i); 
            chars.add(c);
        }
		//create a shuffled num array as index for shuffleChar ArrayList
		shuffle(size);
		// Assign back char from chars ArrayList to according index from shuffleNum
		Character[] temp = new Character[size];
		for (int i = 0; i < size; i++) {
			int num = chars.get(i);
			temp[shuffleNum[i]] = (char) num;
		}
		System.out.println("\nSize of shuffleChar arraylist: " + size);
		//Convert ArrayList to array
		shuffleText = temp;
		for (int i = 0; i < temp.length; i++) {
			System.out.print(temp[i] + " ");
		}
		return true;
	}
}
