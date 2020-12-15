package com.williammunsch.othellobot;
/**
 * Class that stores the player's color and the enemies color
 */
public class Player {
	private String color;
	private String enemyColor;
	
	public Player(String c) {
		this.color = c;
		if (c.contentEquals("B")) {enemyColor="W";}
		else {enemyColor="B";}
	}
	
	public String getColor() {
		return color;
	}
	
	public String getEnemyColor() {
		return enemyColor;
	}
}
