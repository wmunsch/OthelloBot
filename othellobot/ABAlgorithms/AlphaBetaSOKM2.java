package com.williammunsch.othellobot.ABAlgorithms;

import java.util.Collections;
import java.util.List;

import com.williammunsch.othellobot.BitBoard;
import com.williammunsch.othellobot.BitMove;
import com.williammunsch.othellobot.Othello;
import com.williammunsch.othellobot.SortByValue;
import com.williammunsch.othellobot.SortByValue2;

/**
 * An alpha beta algorithm that implements shallow move ordering and killer move heuristics.
 * 
 */
public class AlphaBetaSOKM2 {

	public BitMove getMove(BitBoard board, int depth, int currentPlayer, double alpha, double beta, int maxDepth, BitMove parent, BitMove killerMove) {
    	//Exit the recursion upon reaching maximum depth, or a finished board.
   //***************************************************************************************************
    	if (depth >= maxDepth || Othello.timeUp ) {
    		Othello.doneWithPath=true;
    		List<BitMove> ml = board.generateMoves(currentPlayer);
    		//Set the best move equal to the first in the list.
    		BitMove bestMove = ml.get(0);
    		for (BitMove move : ml) {
    			Othello.totalMovesFound++;
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
    		parent.setChild(bestMove);
    		return bestMove;
   //***************************************************************************************************
    		
    	}else {
    		//Generate Moves for the current player.
    		List<BitMove> ml = board.generateMoves(currentPlayer);
    		//if (depth==2) {Othello.doneWithPath = true;}
    		//Set the best move equal to the first in the list.
    		BitMove bestMove = ml.get(0);
    		if (depth%2==0) {
    			for (BitMove move : ml) {
	    			BitBoard newBoard = new BitBoard(board.whitePieces, board.blackPieces, board.getPiecesOnBoard(),board.getPlayerColor());
	    			newBoard.applyMove(move,currentPlayer);
	    			move.setValue(Othello.evaluator.evaluate(newBoard.getPlayerPieces(), newBoard.getEnemyPieces()));
	    			//This doesn't result in null pointer exception because the killerMove is not null at depth-2, depth-1 is the below odd depth and final depth is above ^
	    			if (!Othello.doneWithPath && move.getBoardPosition() == killerMove.getBoardPosition() ) { 
	    				//System.out.println("even*****" + depth + "    "+ move.getBoardPosition());
	    				move.setValue(move.getValue()+5000);
	    			}
	    		}
	    		Collections.sort(ml,new SortByValue());
    		}else {
    			for (BitMove move : ml) {
	    			BitBoard newBoard = new BitBoard(board.whitePieces, board.blackPieces, board.getPiecesOnBoard(),board.getPlayerColor());
	    			newBoard.applyMove(move,currentPlayer);
	    			move.setValue(Othello.evaluator.evaluate(newBoard.getPlayerPieces(), newBoard.getEnemyPieces()));
	    			if (!Othello.doneWithPath && killerMove!=null && move.getBoardPosition() == killerMove.getBoardPosition() ) { 
	    				//System.out.println("odd*****" + depth + "    " + move.getBoardPosition());
	    				move.setValue(move.getValue()-5000);
	    			}
	    		}
	    		Collections.sort(ml,new SortByValue2()); //Flip the ordering because it was scored based on MY pieces, not enemies
    		}
    	
    		//For each move in the move list
    		for (BitMove move : ml) {
    			BitBoard newBoard = new BitBoard(board.whitePieces, board.blackPieces, board.getPiecesOnBoard(),board.getPlayerColor());
    			newBoard.applyMove(move,currentPlayer);
    			BitMove tempMove;
    			if (killerMove != null && killerMove.getChild()!= null) {
    				tempMove = getMove(newBoard, depth+1, -currentPlayer, -beta, -alpha, maxDepth,move, killerMove.getChild());
    			}else {
    				tempMove = getMove(newBoard, depth+1, -currentPlayer, -beta, -alpha, maxDepth, move,null);
    			}
    			move.setValue(-tempMove.getValue());
    			
    			if (move.getValue() > alpha) {
    				bestMove = move;
    				alpha = move.getValue();
    				if (alpha > beta) {
    					return bestMove;
    				}
    			}
    		}
    		if (parent!=null) {parent.setChild(bestMove);}
    		return bestMove;
    	}
    }
	
}
