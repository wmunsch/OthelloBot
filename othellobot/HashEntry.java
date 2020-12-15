package com.williammunsch.othellobot;
/**
 * Data model that stores the board score and scored depth for the hashmap for the transposition table
 */
public class HashEntry {
	int boardScore = 0;
	int scoredDepth = 0;
	
	public HashEntry(int b, int d) {
		boardScore = b;
		scoredDepth = d;
	}
	
	public int getBoardScore() {
		return boardScore;
	}
	
	public int getScoredDepth() {
		return scoredDepth;
	}
	
	public void setBoardScore(int s) {
		this.boardScore = s;
	}
	
	public void setScoredDepth(int sd) {
		this.scoredDepth = sd;
	}
	

	
	
}
