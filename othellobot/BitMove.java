package com.williammunsch.othellobot;
/**
 * Class that represents a possible move
 */
public class BitMove {
	long boardPosition; //Bit map that stores the position of the move
	long piecesToBeFlipped; //Bit map that stores the discs that will be flipped
	int value; //The score of the board when this move happens
	BitMove child;

	public BitMove(long b) {
		this.boardPosition = b;
	}
	public BitMove(long b, long f) {
		this.boardPosition = b;
		this.piecesToBeFlipped = f;
	}
	
	public long getBoardPosition() {
		return boardPosition;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int va) {
		this.value = va;
	}
	
	public void setBoardPosition(long b) {
		this.boardPosition = b;
	}
	public void setChild(BitMove m) {
		this.child = m;
	}
	public BitMove getChild() {
		return child;
	}
	
	public long getPiecesToBeFlipped() {
		return piecesToBeFlipped;
	}
	
	public void setPiecesToBeFlipped(long p) {
		piecesToBeFlipped=p;
	}
	

}


