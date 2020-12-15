package com.williammunsch.othellobot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.williammunsch.othellobot.ABAlgorithms.AlphaBetaSO;
import com.williammunsch.othellobot.ABAlgorithms.AlphaBetaSOKM1;
import com.williammunsch.othellobot.ABAlgorithms.AlphaBetaSOKM2;
import com.williammunsch.othellobot.ABAlgorithms.AlphaBetaSOKMTT;
import com.williammunsch.othellobot.ABAlgorithms.AlphaBetaSOTT;

/**
 * This class contains the representation of the board as two 64-bit integers,
 * all of the board data, and the methods to generate moves and apply moves.
 */
public class BitBoard {
	public long whitePieces = 0b0000000000000000000000000000100000010000000000000000000000000000L;
	public long blackPieces = 0b0000000000000000000000000001000000001000000000000000000000000000L;
	boolean endOfGame=false;
	private static final int directions[] = {1,7,9,8};
	private static final long edgeMasks[] = {
			~0b1000000010000000100000001000000010000000100000001000000010000000L, //right edge
			~0b0000000100000001000000010000000100000001000000010000000100000001L, //left edge
			~0b1000000010000000100000001000000010000000100000001000000010000000L, //right edge
			~0
	};
	private static final long edgeMasks2[] = {
			~0b0000000100000001000000010000000100000001000000010000000100000001L, //left edge
			~0b1000000010000000100000001000000010000000100000001000000010000000L, //right edge
			~0b0000000100000001000000010000000100000001000000010000000100000001L, //left edge
			~0
	};
	
	int boardScore = 0;
	public int turn = 0;
	int piecesOnBoard = 4;
	int frontiers = 0;
	
	long playerPieces;
	long enemyPieces;
	long combined = whitePieces | blackPieces;

	boolean playerColor; //true for black, false for white
	
	int scoredDepth = 0;

	
	public int getPiecesOnBoard() {
		return piecesOnBoard;
	}
	
	public long getPlayerPieces() {
		return playerPieces;
	}
	
	public long getEnemyPieces() {
		return enemyPieces;
	}
	
	public boolean getPlayerColor() {
		return playerColor;
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


	public BitBoard(long w, long b, int p, boolean player) {
		this.whitePieces = w;
		this.blackPieces = b;
		this.combined = whitePieces | blackPieces;
		this.piecesOnBoard = p;
    	this.playerColor = player;
    	if (player) {
    		playerPieces = blackPieces;
    		enemyPieces = whitePieces;
    	}else {
    		playerPieces = whitePieces;
    		enemyPieces = blackPieces;
    	}
    	
	}

	
	public BitBoard(boolean player) {
		this.playerColor = player;
		if (player) {
    		playerPieces = blackPieces;
    		enemyPieces = whitePieces;
    	}else {
    		playerPieces = whitePieces;
    		enemyPieces = blackPieces;
    	}
	}

	public void flipPlayers() {
		long temp = enemyPieces;
		enemyPieces = playerPieces;
		playerPieces = temp;
	}

	//Chooses a random move from all possible moves
	public BitMove getRandomMove(int currentPlayer) {
    	List<BitMove> ml = generateMoves(currentPlayer);
    	Random random = new Random();
    	return ml.get(random.nextInt(ml.size()));
    }

	//Gets a move from an alpha beta algorithm with a shallow ordering heuristic but no progressive deepening
    public BitMove getBestMoveSO(int currentPlayer, int maxDepth) {
    	double alpha = -100000000;
    	double beta = 100000000;
    	AlphaBetaSO alphaBetaAlgorithm = new AlphaBetaSO();
    	return alphaBetaAlgorithm.getMove(this,0,currentPlayer,alpha,beta,maxDepth);
    }


    //Gets a move from an alpha beta algorithm with a shallow ordering heuristic and progressive deepening
    public BitMove getBestMoveSOPD(int currentPlayer, int maxDepth) {
    	double alpha = -100000000;
    	double beta = 100000000;
    	BitMove bestMove = null;
    	AlphaBetaSO alphaBetaAlgorithm = new AlphaBetaSO();
    	for (int i = 4; i<= maxDepth; i+=2) { 
    		BitMove tempMove = alphaBetaAlgorithm.getMove(this,0,currentPlayer,alpha,beta,i);
    		if (!Othello.timeUp) {bestMove = tempMove;}
    		else {break;}
    		System.out.println ("C Found depth " + i + " move " + bestMove.getValue());
    	}
    	return bestMove;
    }
    
    //Gets a move from an alpha beta algorithm with a shallow ordering heuristic and a transposition table with progressive deepening
    public BitMove getBestMoveSOTT(int currentPlayer, int maxDepth) {
    	double alpha = -100000000;
    	double beta = 100000000;
    	BitMove bestMove = null;
    	AlphaBetaSOTT alphaBetaAlgorithm = new AlphaBetaSOTT();
    	for (int i = 4; i<= maxDepth; i+=2) { 
    		BitMove tempMove = alphaBetaAlgorithm.getMove(this,0,currentPlayer,alpha,beta,i);
    		if (!Othello.timeUp) {bestMove = tempMove;}
    		else {break;}
    		System.out.println ("C Found depth " + i + " move " + bestMove.getValue());
    		Othello.transpositionTable.clear();
    	}
    	return bestMove;
    }

    //Gets a move from an alpha beta algorithm with shallow ordering and killer move heuristics and progressive deepening
    public BitMove getBestMoveSO3PDK(int currentPlayer, int maxDepth) {
    	double alpha = -100000000;
    	double beta = 100000000;
    	BitMove bestMove = null;
    	AlphaBetaSOKM1 alphaBetaAlgorithm1 = new AlphaBetaSOKM1();
    	AlphaBetaSOKM2 alphaBetaAlgorithm2 = new AlphaBetaSOKM2();
    	
    	//Get the depth 4 best move to start killer heuristic with for depth 4
    	bestMove = alphaBetaAlgorithm1.getMove(this,0,currentPlayer,alpha,beta,4,null);
    	//Loop to progressively deeper depths, generating a new best move path sequence each time and using it to speed up the search to next depth
    	for (int i = 4; i<= maxDepth; i+=2) {
    		Othello.doneWithPath = false;
    		BitMove tempMove = alphaBetaAlgorithm2.getMove(this,0,currentPlayer,alpha,beta,i,null,bestMove);
    		if (!Othello.timeUp) {bestMove = tempMove;}
    		else {break;}
    		System.out.println ("C Found depth " + i + " move " + bestMove.getBoardPosition() + " " + bestMove.getValue());
    	}
    	
    	return bestMove;
    }
    
  
    ////Gets a move from an alpha beta algorithm with shallow ordering and killer move heuristics and a transposition table with progressive deepening
    public BitMove getBestMoveSO3PDKTT(int currentPlayer, int maxDepth) {
    	double alpha = -100000000;
    	double beta = 100000000;
    	BitMove bestMove = null;
    	AlphaBetaSOKM1 alphaBetaAlgorithm1 = new AlphaBetaSOKM1();
    	AlphaBetaSOKMTT alphaBetaAlgorithm2 = new AlphaBetaSOKMTT();
    	//Get the depth 4 best move to start killer heuristic with for depth 4
    	bestMove = alphaBetaAlgorithm1.getMove(this,0,currentPlayer,alpha,beta,4,null);
    	//Loop to progressively deeper depths, generating a new best move path sequence each time and using it to speed up the search to next depth
    	for (int i = 4; i<= maxDepth; i+=2) {
    		Othello.doneWithPath = false;
    		BitMove tempMove = alphaBetaAlgorithm2.getMove(this,0,currentPlayer,alpha,beta,i,null,bestMove);
    		if (!Othello.timeUp) {bestMove = tempMove;}
    		else {break;}
    		System.out.println ("C Found depth " + i + " move " + bestMove.getBoardPosition() + " " + bestMove.getValue());
    		Othello.transpositionTable.clear();
    	}
    	
    	return bestMove;
    }
    
   

    public void updateCombined() {
    	this.combined = whitePieces | blackPieces;
    }
    

    /*
	 * Creates a bit mask of possible moves by shifting the player pieces mask 6 times in each direction (horizontal, vertical, and two diagonal)
	 * and 6 times in the opposite of the same direction (horizontal is checked 6 times to the right then 6 times to the left), and keeping the bits
	 * that intersect enemy pieces, using edge masks to clip the board, and generating the move mask by bitwise ANDing the spaces where there are 
	 * no discs and the end resulting player mask.
	 */
	public List<BitMove> generateMoves(int currentPlayer){
		if (currentPlayer==-1) {
			flipPlayers();
		}
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
	  
	    //Loop through the bit mask with the possible moves and generates a BitMove for each bit
	    List<BitMove> moveList = new ArrayList<>();
	    long indexMask = 0b0000000000000000000000000000000000000000000000000000000000000001L;
	       for (int i = 0 ; i < 64; i++) {
	    	   if (((indexMask)&moveMask)!=0) {
	    		   moveList.add(new BitMove(indexMask,getFlipMask(indexMask)));
	    	   }
	    	   indexMask<<=1;
	       }
	   	if (moveList.isEmpty()) {
		moveList.add(new BitMove(-1));
		}
	   	if (currentPlayer==-1) {
	   		flipPlayers();
		}
	 
	    return moveList;
	 }
	
	/**
	 * Helper method for creating a BitMove
	 * Creates a bit mask of all the discs that will be flipped when the move occurs
	 */
	public long getFlipMask(long indexMask) {
		long flipMask = 0L;
		long tempMask = 0L;
		long possibleFlips;
		
		for (int i = 0;i<4;i++) { //Shift the move piece in a direction (one loop for each direction)
			possibleFlips = 0;
			tempMask = indexMask;
			tempMask = (tempMask>>>directions[i])&edgeMasks[i];
			if (tempMask!=0 && (tempMask&enemyPieces) !=0) { //First iteration, must be enemy pieces to continue.
				possibleFlips |= tempMask;
				
				tempMask = (tempMask>>>directions[i])&edgeMasks[i];
				while (tempMask!=0) {//while the indexMask hasn't reached an edge or empty spot, continue moving
					if ((tempMask & playerPieces) != 0) { //reached friendly, end and save flips
						flipMask |= possibleFlips;
						possibleFlips = 0L;
						break;
					}else if ((tempMask&enemyPieces)!=0){ //reached another enemy, add to possible and continue
						possibleFlips |= tempMask;
					}else {
						break;//hit an empty
					}
					tempMask = (tempMask>>>directions[i])&edgeMasks[i];
				}
			}
			
			
			possibleFlips = 0;
			tempMask = indexMask;
			tempMask = (tempMask<<directions[i])&edgeMasks2[i];
			if (tempMask!=0 && (tempMask&enemyPieces) !=0) { //First iteration, must be enemy pieces to continue.
				possibleFlips |= tempMask;
				
				tempMask = (tempMask<<directions[i])&edgeMasks2[i];
				while (tempMask!=0) {//while the indexMask hasn't reached an edge or empty spot, continue moving
					if ((tempMask & playerPieces) != 0) { //reached friendly, end and save flips
						flipMask |= possibleFlips;
						possibleFlips = 0L;
						break;
					}else if ((tempMask&enemyPieces)!=0){ //reached another enemy, add to possible and continue
						possibleFlips |= tempMask;
					}else {
						break;//hit an empty
					}
					tempMask = (tempMask<<directions[i])&edgeMasks2[i];
				}
			}

	    }
		
		return flipMask;
	}
	
	//Applies a move to the board by setting the bits for white and black pieces
	public void applyMove(BitMove move, int currentPlayer) {
		if (move.getBoardPosition()!=-1) {
			//Switch player pieces if enemy turn
			if (currentPlayer==-1) {
				flipPlayers();
			}
			playerPieces |= move.getBoardPosition();
			
			//Flips
			playerPieces |= move.getPiecesToBeFlipped();
			enemyPieces ^= move.getPiecesToBeFlipped();
			
			if (currentPlayer==-1) {
				flipPlayers();
			}
			
			if (playerColor) {
	       		blackPieces = playerPieces;
	    		 whitePieces = enemyPieces;
	    	}else {
	    		whitePieces = playerPieces;
	    		blackPieces = enemyPieces;
	    	}
			piecesOnBoard +=1;
		}

	    turn +=1;
	  
	    updateCombined();	
				
	}
	
	//Undo a move
	public void unapplyMove(BitMove move, int currentPlayer) {
		if (move.getBoardPosition()!=-1) {
			//Switch player pieces if enemy turn
			if (currentPlayer==-1) {
				flipPlayers();
			}
			
	    	//Set the empty spot
			playerPieces ^= move.getBoardPosition();
			
			//Flips
			playerPieces ^= move.getPiecesToBeFlipped();
			enemyPieces |= move.getPiecesToBeFlipped();
			
			if (currentPlayer==-1) {
				flipPlayers();
			}
			
			if (playerColor) {
	       		blackPieces = playerPieces;
	    		 whitePieces = enemyPieces;
	    	}else {
	    		whitePieces = playerPieces;
	    		blackPieces = enemyPieces;
	    	}
			 piecesOnBoard -=1;
		}
	
		
		turn -=1;
	
		updateCombined();
	}
	
	

	/**
	 * Creates a hash from the current board state.
	 */
	public long hash() {
		return blackPieces*7 + whitePieces*13;
	}


}
