package model;

/**
 * Purpose: This class is to store information for a user
 * File name: User.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 4 Dec 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: User.java
 * Purpose: This class is store information for a user
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class User {

	/**
	 * user's id
	 */
	private int id;
	/**
	 * user's name
	 */
	private String userName;
	/**
	 * user's points
	 */
	private int points = 0;
	/**
	 * user's timer
	 */
	private int timer = 0;
	
	//Setters and Getters
	/**
	 * Getter for user's id
	 * @return user's id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Getter for user's name
	 * @return user's name
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Getter for user's points
	 * @return user's points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * Getter for user's timer
	 * @return user's timer
	 */
	public int getTimer() {
		return timer;
	}
	/**
	 * setter for user's id
	 * @param id user's id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * setter for user's name
	 * @param id user's name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * setter for user's points
	 * @param id user's points
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	/**
	 * setter for user's timer
	 * @param id user's timer
	 */
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	/**
	 * default constructor
	 */
	public User() {}
	
	/**
	 * overloading constructor
	 * @param id user's id
	 * @param userName user's name
	 * @param points user's points
	 * @param timer user's timer
	 */
	public User(int id, String userName, int points, int timer) {
		this.id = id;
		this.userName = userName;
		this.points = points;
		this.timer = timer;
	}
}
