package model;

public class User {

	private int id;
	private String userName;
	private int points = 0;
	private int timer = 0;
	
	//Setters and Getters
	public int getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public int getPoints() {
		return points;
	}
	public int getTimer() {
		return timer;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}

	public User() {}
	
	public User(int id, String userName, int points, int timer) {
		this.id = id;
		this.userName = userName;
		this.points = points;
		this.timer = timer;
	}
}
