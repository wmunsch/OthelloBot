package com.williammunsch.othellobot;


/**
 * Evaluation algorithm
 * Uses a combination of mobility, frontiers, stable disks, and least disk strategy
 */
public class BitEvaluator {
	
	//The bit mask of all possible board positions that could hold a frontier (edges can't be frontiers)
	public static final long frontierMask = 0b0000000000111100011111100111111001111110011111100011110000000000L;
	
	 // Array to store the number of bits a bit map needs to be shifted to move a certain directions (horizontal, vertical, and two diagonal directions)
	private static final int directions[] = {1,7,9,8};
	
	//Array to store the edge masks for finding possible moves
	private static final long edgeMasks[] = {
			~0b1000000010000000100000001000000010000000100000001000000010000000L, //right edge
			~0b0000000100000001000000010000000100000001000000010000000100000001L, //left edge
			~0b1000000010000000100000001000000010000000100000001000000010000000L, //right edge
			~0
	};
	
	//Array to store the edge masks for finding possible moves
	private static final long edgeMasks2[] = {
			~0b0000000100000001000000010000000100000001000000010000000100000001L, //left edge
			~0b1000000010000000100000001000000010000000100000001000000010000000L, //right edge
			~0b0000000100000001000000010000000100000001000000010000000100000001L, //left edge
			~0
	};

	//A bit for each of the four corners of the board
	private static final long cornerMask = 0b1000000100000000000000000000000000000000000000000000000010000001L;
	

	//Scores the current board state
	public int evaluate(long playerPieces, long enemyPieces) {
		int boardScore = 0;
		int pm = (int)countMoves(playerPieces,enemyPieces);
		int em = (int)countMoves(enemyPieces,playerPieces);
		
		if (pm==0 && em==0) {
			//game is over, score board based off of number of pieces
			
			int pp = Long.bitCount(playerPieces);
			int ep = Long.bitCount(enemyPieces);
			
			if (pp>ep) {boardScore = 1000000 + (pp-ep);}
			else {boardScore = -1000000 - (ep-pp);}
		
	    	return boardScore;
	    	
		}
		else {
			int pf = countFrontiers(playerPieces, playerPieces|enemyPieces);
			int ef = countFrontiers(enemyPieces, playerPieces|enemyPieces);
		
			int ps = countStables(playerPieces);
			int es = countStables(enemyPieces);
		
			boardScore += (pm-em)*73 - (pf-ef)*57 + (ps-es)*141;
	        return boardScore;
		}
	}

	
	 //Counts the number of stable discs in corners and edges
	public int countStables(long disks) {
		//First check to see if any corners are taken, otherwise stable disks won't exist
		if ((cornerMask & disks) == 0) {
			return 0;
		}else {
			long holdMask = 0;
				//top right
				if ((0b1000000000000000000000000000000000000000000000000000000000000000L & disks)!=0) {
					holdMask|=0b1000000000000000000000000000000000000000000000000000000000000000L;
					long singleBitMask = 0b0100000000000000000000000000000000000000000000000000000000000000L;
					while ((singleBitMask&disks) !=0 && singleBitMask!=0b0000000100000000000000000000000000000000000000000000000000000000L) {//search left (top edge)
						holdMask |= singleBitMask;
						singleBitMask>>>=1;
					}
					singleBitMask = 0b0000000010000000000000000000000000000000000000000000000000000000L;
					while ((singleBitMask&disks) !=0 && singleBitMask!=0b0000000000000000000000000000000000000000000000000000000010000000L) { //search down (right edge)
						holdMask |= singleBitMask;
						singleBitMask>>>=8;
					}
					singleBitMask = 0b1100000010000000000000000000000000000000000000000000000000000000L;
					while ((singleBitMask&disks) ==singleBitMask && singleBitMask!=0b0000000110000001000000000000000000000000000000000000000000000000L) {//search left (2nd from top edge)
						holdMask |= singleBitMask;
						singleBitMask>>>=1;
					}
					singleBitMask = 0b1100000010000000000000000000000000000000000000000000000000000000L;
					while ((singleBitMask&disks) ==singleBitMask && singleBitMask!=0b0000000000000000000000000000000000000000000000000000000011000000L) { //search down (2nd from right edge)
						holdMask |= singleBitMask;
						singleBitMask>>>=8;
					}
					//Check for the 3rd piece diagonally from the corner
					if ((0b1111100011110000111000000000000000000000000000000000000000000000L&disks)==0b1111100011110000111000000000000000000000000000000000000000000000L) {
						holdMask|=0b1111100011110000111000000000000000000000000000000000000000000000L;
					}else if ((0b1110000011100000111000001100000010000000000000000000000000000000L&disks)==0b1110000011100000111000001100000010000000000000000000000000000000L) {
						holdMask|=0b1110000011100000111000001100000010000000000000000000000000000000L;
					}
					
				 //bottom left
				}
				if ((0b0000000000000000000000000000000000000000000000000000000000000001L & disks)!=0) {
					holdMask|=0b0000000000000000000000000000000000000000000000000000000000000001L;
					long singleBitMask = 0b0000000000000000000000000000000000000000000000000000000000000010L;
					while ((singleBitMask&disks) !=0 && singleBitMask!=0b0000000000000000000000000000000000000000000000000000000010000000L) {//search right (bottom edge)
						holdMask |= singleBitMask;
						singleBitMask<<=1;
					}
					singleBitMask = 0b0000000000000000000000000000000000000000000000000000000100000000L;
					while ((singleBitMask&disks) !=0 && singleBitMask!=0b0000000100000000000000000000000000000000000000000000000000000000L) { //search up (left edge)
						holdMask |= singleBitMask;
						singleBitMask<<=8;
					}
					singleBitMask = 0b0000000000000000000000000000000000000000000000000000000100000011L;
					while ((singleBitMask&disks) ==singleBitMask && singleBitMask!=0b0000000000000000000000000000000000000000000000001000000110000000L) { //search right 2nd from edge
						holdMask |= singleBitMask;
						singleBitMask<<=1;
					}
					singleBitMask = 0b0000000000000000000000000000000000000000000000000000000100000011L;
					while ((singleBitMask&disks) ==singleBitMask && singleBitMask!=0b0000001100000000000000000000000000000000000000000000000000000000L) { //search up 2nd from edge
						holdMask |= singleBitMask;
						singleBitMask<<=8;
					}
					//Check for the 3rd piece diagonally from the corner
					if ((0b0000000000000000000000000000000000000000000001110000111100011111L&disks)==0b0000000000000000000000000000000000000000000001110000111100011111L) {
						holdMask|=0b0000000000000000000000000000000000000000000001110000111100011111L;
					}else if ((0b0000000000000000000000000000000100000011000001110000011100000111L&disks)==0b0000000000000000000000000000000100000011000001110000011100000111L) {
						holdMask|=0b0000000000000000000000000000000100000011000001110000011100000111L;
					}
				//top left	
				}
				
				if ((0b0000000100000000000000000000000000000000000000000000000000000000L & disks)!=0) {
					holdMask|=0b0000000100000000000000000000000000000000000000000000000000000000L;
					long singleBitMask = 0b0000001000000000000000000000000000000000000000000000000000000000L;
					while ((singleBitMask&disks) !=0 && singleBitMask!=0b1000000000000000000000000000000000000000000000000000000000000000L) {//search right (top edge)
						holdMask |= singleBitMask;
						singleBitMask<<=1;
					}
					singleBitMask = 0b0000000000000001000000000000000000000000000000000000000000000000L;
					while ((singleBitMask&disks) !=0 && singleBitMask!=0b0000000000000000000000000000000000000000000000000000000000000001L) { //search down (right edge)
						holdMask |= singleBitMask;
						singleBitMask>>>=8;
					}
					singleBitMask = 0b0000001100000001000000000000000000000000000000000000000000000000L;
					while ((singleBitMask&disks) ==singleBitMask && singleBitMask!=0b0000000000000000000000000000000000000000000000000000000000000011L) { //search down (2nd from right edge)
						holdMask |= singleBitMask;
						singleBitMask>>>=8;
					}
					singleBitMask = 0b0000001100000001000000000000000000000000000000000000000000000000L;
					while ((singleBitMask&disks) ==singleBitMask && singleBitMask!=0b1000000010000000000000000000000000000000000000000000000000000000L) { //search right 2nd from edge
						holdMask |= singleBitMask;
						singleBitMask<<=1;
					}
					//Check for the 3rd piece diagonally from the corner
					if ((0b0001111100001111000001110000000000000000000000000000000000000000L&disks)==0b0001111100001111000001110000000000000000000000000000000000000000L) {
						holdMask|=0b0001111100001111000001110000000000000000000000000000000000000000L;
					}else if ((0b0000011100000111000001110000001100000001000000000000000000000000L&disks)==0b0000011100000111000001110000001100000001000000000000000000000000L) {
						holdMask|=0b0000011100000111000001110000001100000001000000000000000000000000L;
					}
					
				//bottom right	
				}
				if ((0b0000000000000000000000000000000000000000000000000000000010000000L & disks)!=0) {
					holdMask|=0b0000000000000000000000000000000000000000000000000000000010000000L;
					long singleBitMask = 0b0000000000000000000000000000000000000000000000001000000000000000L;
					while ((singleBitMask&disks) !=0 && singleBitMask!=0b1000000000000000000000000000000000000000000000000000000000000000L) { //search up (right edge)
						holdMask |= singleBitMask;
						singleBitMask<<=8;
					}
					singleBitMask = 0b0000000000000000000000000000000000000000000000000000000001000000L;
					while ((singleBitMask&disks) !=0 && singleBitMask!=0b0000000000000000000000000000000000000000000000000000000000000001L) {//search left (bottom edge)
						holdMask |= singleBitMask;
						singleBitMask>>>=1;
					}
					singleBitMask = 0b0000000000000000000000000000000000000000000000001000000011000000L;
					while ((singleBitMask&disks) ==singleBitMask && singleBitMask!=0b1100000000000000000000000000000000000000000000000000000000000000L) { //search up 2nd from edge
						holdMask |= singleBitMask;
						singleBitMask<<=8;
					}
					singleBitMask = 0b0000000000000000000000000000000000000000000000001000000011000000L;
					while ((singleBitMask&disks) ==singleBitMask && singleBitMask!=0b0000000000000000000000000000000000000000000000000000000100000001L) {//search left (2nd from top edge)
						holdMask |= singleBitMask;
						singleBitMask>>>=1;
					}
					//Check for the 3rd piece diagonally from the corner
					if ((0b0000000000000000000000000000000000000000111000001111000011111000L&disks)==0b0000000000000000000000000000000000000000111000001111000011111000L) {
						holdMask|=0b0000000000000000000000000000000000000000111000001111000011111000L;
					}else if ((0b0000000000000000000000001000000011000000111000001110000011100000L&disks)==0b0000000000000000000000001000000011000000111000001110000011100000L) {
						holdMask|=0b0000000000000000000000001000000011000000111000001110000011100000L;
					}
				}
			
			return Long.bitCount(holdMask);
		}
	}
	

	
	//Counts the number of disks with at least 1 empty spot up down left or right.
	public int countFrontiers(long playerPieces,long combinedPieces) {
		long frontierHold = 0;
		long pieces = frontierMask&playerPieces; //make a new long with the pieces of player pieces that are valid frontier suspects
		long emptyMask = ~combinedPieces;
		long holdMask = (pieces<<8 & emptyMask);
		frontierHold |= holdMask>>>8;
		holdMask = (pieces>>>8 & emptyMask);
		frontierHold |= holdMask<<8;
		holdMask = (pieces>>>1 & emptyMask);
		frontierHold |= holdMask<<1;
		holdMask = (pieces<<1 & emptyMask);
		frontierHold |= holdMask>>>1;
		
		return Long.bitCount(frontierHold);	
	}
	
	
	/*
	 * Creates a bit mask of possible moves by shifting the player pieces mask 6 times in each direction (horizontal, vertical, and two diagonal)
	 * and 6 times in the opposite of the same direction (horizontal is checked 6 times to the right then 6 times to the left), and keeping the bits
	 * that intersect enemy pieces, using edge masks to clip the board, and generating the move mask by bitwise ANDing the spaces where there are 
	 * no discs and the end resulting player mask.
	 */
	public long countMoves(long playerPieces, long enemyPieces){
		long combinedMask = playerPieces|enemyPieces;
	    long playerMask ,moveMask = 0;
	    long emptyMask = ~combinedMask;
	    for (int i = 0;i<4;i++) { //Shift the player pieces in a direction (one loop for each direction)
	    	playerMask = playerPieces;
	    	playerMask = (playerMask>>>directions[i])&edgeMasks[i];
	    	playerMask &=enemyPieces; //Only keep the bits where the player pieces shifted a direction now ANDS an enemy piece
	    	for (int j=0; j<6; j++) {//Shift again in the same direction as before, 6 times (checking whole board for a given direction)
	    		if (playerMask==0) {break;}
	    		playerMask = (playerMask>>>directions[i])&edgeMasks[i];
	    		moveMask|= emptyMask&playerMask;
	    		playerMask &= enemyPieces;//Set new player mask to pieces that equal enemy pieces
	    	}
	    	
	    	playerMask = playerPieces;
	    	playerMask = (playerMask<<directions[i])&edgeMasks2[i];
	    	playerMask &=enemyPieces;
	    	for (int j=0; j<6; j++) {
	    		if (playerMask==0) {break;}
	    		playerMask = (playerMask<<directions[i])&edgeMasks2[i];
	    		moveMask|= emptyMask&playerMask;
	    		playerMask &= enemyPieces;
	    	}

	    }
	    return Long.bitCount(moveMask);
	 }

	
}

