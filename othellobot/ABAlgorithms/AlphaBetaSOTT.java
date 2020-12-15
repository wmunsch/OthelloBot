package com.williammunsch.othellobot.ABAlgorithms;

import java.util.Collections;
import java.util.List;

import com.williammunsch.othellobot.BitBoard;
import com.williammunsch.othellobot.BitMove;
import com.williammunsch.othellobot.HashEntry;
import com.williammunsch.othellobot.Othello;
import com.williammunsch.othellobot.SortByValue;
import com.williammunsch.othellobot.SortByValue2;

/**
 * An alpha beta algorithm that implements a shallow move ordering heuristic and a transposition table.
 */
public class AlphaBetaSOTT {

	public BitMove getMove(BitBoard board, int depth, int currentPlayer, double alpha, double beta, int maxDepth) {
    	//Exit the recursion upon reaching maximum depth, or a finished board.
   //***************************************************************************************************
    	if (depth >= maxDepth || Othello.timeUp ) {
    
    		List<BitMove> ml = board.generateMoves(currentPlayer);
    		
    		//Set the best move equal to the first in the list.
    		BitMove bestMove = ml.get(0);
    		
    		for (BitMove move : ml) {
    			Othello.totalMovesFound ++;
    			BitBoard newBoard = new BitBoard(board.whitePieces, board.blackPieces, board.getPiecesOnBoard(),board.getPlayerColor());
    			newBoard.applyMove(move,currentPlayer);
    			move.setValue(Othello.evaluator.evaluate(newBoard.getPlayerPieces(), newBoard.getEnemyPieces()));
    		
    			if (move.getValue() > alpha) {
    				bestMove = move;
    				alpha = move.getValue();
    				if (alpha > beta) {
    					return bestMove;
    				}
    			}
    		}
    		return bestMove;
   //***************************************************************************************************
    		
    	}else {
    		
    		//Generate Moves for the current player.
    		List<BitMove> ml = board.generateMoves(currentPlayer);
    		//Set the best move equal to the first in the list.
    		BitMove bestMove = ml.get(0);
    		
    		
    		
    		
    		if (depth%2==0) {
    			for (BitMove move : ml) {
	    			BitBoard newBoard = new BitBoard(board.whitePieces, board.blackPieces, board.getPiecesOnBoard(),board.getPlayerColor());
	    			newBoard.applyMove(move,currentPlayer);
	    			
	    			if (Othello.transpositionTable.containsKey(newBoard.hash())){
	        			move.setValue(Othello.transpositionTable.get(newBoard.hash()).getBoardScore());
	        		}else {
	        			move.setValue(Othello.evaluator.evaluate(newBoard.getPlayerPieces(), newBoard.getEnemyPieces()));
	        		}
	    			
	    		}
	    		Collections.sort(ml,new SortByValue());
    		}else {
    			for (BitMove move : ml) {
	    			BitBoard newBoard = new BitBoard(board.whitePieces, board.blackPieces, board.getPiecesOnBoard(),board.getPlayerColor());
	    			newBoard.applyMove(move,currentPlayer);
	    			
	    			if (Othello.transpositionTable.containsKey(newBoard.hash())){
	        			move.setValue(Othello.transpositionTable.get(newBoard.hash()).getBoardScore());
	        		}else {
	        			move.setValue(Othello.evaluator.evaluate(newBoard.getPlayerPieces(), newBoard.getEnemyPieces()));
	        		}
	    		}
	    		Collections.sort(ml,new SortByValue2()); //Flip the ordering because it was scored based on MY pieces, not enemies
    		}
    		
    	
    	
    		//For each move in the move list
    		for (BitMove move : ml) {
    			BitBoard newBoard = new BitBoard(board.whitePieces, board.blackPieces, board.getPiecesOnBoard(),board.getPlayerColor());
    			newBoard.applyMove(move,currentPlayer);
    			
    			if (Othello.transpositionTable.containsKey(newBoard.hash()) && Othello.transpositionTable.get(newBoard.hash()).getScoredDepth()==maxDepth){
    				//Do nothing because the move has already been evaluated to max depth
    				Othello.totalTimesTTSaved++;
        		}else {
        			BitMove tempMove = getMove(newBoard, depth+1, -currentPlayer, -beta, -alpha, maxDepth);
        			newBoard.setBoardScore(-tempMove.getValue());//Set the board states score and scoredDepth
        			newBoard.setScoredDepth(maxDepth);
        			move.setValue(-tempMove.getValue());
        			if (depth<=maxDepth-4) {
        				Othello.totalMovesAddedToTranspositionTable++;
            			Othello.transpositionTable.put(newBoard.hash(),new HashEntry(newBoard.getBoardScore(),maxDepth));
        			}
        		}
    			
    			if (move.getValue() > alpha) {
    				bestMove = move;
    				alpha = move.getValue();
    				if (alpha > beta) {
    					return bestMove;
    				}
    			}
    		}
    		return bestMove;
    	}
    }
	
}
