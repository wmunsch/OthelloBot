package com.williammunsch.othellobot.tests;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.williammunsch.othellobot.BitBoard;
import com.williammunsch.othellobot.BitMove;
import com.williammunsch.othellobot.Othello;
import com.williammunsch.othellobot.Player;
import com.williammunsch.othellobot.PrintBitBoard;
/**
 * This testing class is used to compare the speeds and move selection of different alpha beta heuristics.
 *
 */
public class AlphaBetaHeuristicsTest {
	public static final Map<Long, String> bitMap = new HashMap<>();
	public static final Map<String, Long> bitMapR = new HashMap<>();
	public static boolean timeUp = false;
	static StringBuilder sb = new StringBuilder();
	PrintBitBoard printBitBoard= new PrintBitBoard();
	 
	  static double [] timeAllocation = 
		{0.015, 0.015, 0.015, 0.015, 0.025, 0.025, 0.025, 0.025, 0.025, 0.025,
		 0.048, 0.048, 0.048, 0.048, 0.048, 0.048, 0.050, 0.051, 0.052, 0.053,
		 0.044, 0.045, 0.049, 0.049, 0.049, 0.051, 0.053, 0.055, 0.057, 0.059,
		 0.060, 0.060, 0.061, 0.062, 0.063, 0.064, 0.065, 0.065, 0.065, 0.065,
		 0.167, 0.168, 0.169, 0.169, 0.171, 0.172, 0.173, 0.175, 0.180, 0.180,
		 0.181, 0.187, 0.196, 0.199, 0.220, 0.220, 0.220, 0.220, 0.220, 0.220,
		 0.220, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250,
		 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250};
	
	public AlphaBetaHeuristicsTest() {
		//Translates between bits and board positions
		bitMap.put(0b0000000100000000000000000000000000000000000000000000000000000000L, "a1");bitMap.put(0b0000000000000001000000000000000000000000000000000000000000000000L, "a2");bitMap.put(0b0000000000000000000000010000000000000000000000000000000000000000L, "a3");bitMap.put(0b0000000000000000000000000000000100000000000000000000000000000000L, "a4");bitMap.put(0b0000000000000000000000000000000000000001000000000000000000000000L, "a5");bitMap.put(0b0000000000000000000000000000000000000000000000010000000000000000L, "a6");bitMap.put(0b0000000000000000000000000000000000000000000000000000000100000000L, "a7");bitMap.put(0b0000000000000000000000000000000000000000000000000000000000000001L, "a8");
    	bitMap.put(0b0000001000000000000000000000000000000000000000000000000000000000L, "b1");bitMap.put(0b0000000000000010000000000000000000000000000000000000000000000000L, "b2");bitMap.put(0b0000000000000000000000100000000000000000000000000000000000000000L, "b3");bitMap.put(0b0000000000000000000000000000001000000000000000000000000000000000L, "b4");bitMap.put(0b0000000000000000000000000000000000000010000000000000000000000000L, "b5");bitMap.put(0b0000000000000000000000000000000000000000000000100000000000000000L, "b6");bitMap.put(0b0000000000000000000000000000000000000000000000000000001000000000L, "b7");bitMap.put(0b0000000000000000000000000000000000000000000000000000000000000010L, "b8");
    	bitMap.put(0b0000010000000000000000000000000000000000000000000000000000000000L, "c1");bitMap.put(0b0000000000000100000000000000000000000000000000000000000000000000L, "c2");bitMap.put(0b0000000000000000000001000000000000000000000000000000000000000000L, "c3");bitMap.put(0b0000000000000000000000000000010000000000000000000000000000000000L, "c4");bitMap.put(0b0000000000000000000000000000000000000100000000000000000000000000L, "c5");bitMap.put(0b0000000000000000000000000000000000000000000001000000000000000000L, "c6");bitMap.put(0b0000000000000000000000000000000000000000000000000000010000000000L, "c7");bitMap.put(0b0000000000000000000000000000000000000000000000000000000000000100L, "c8");
    	bitMap.put(0b0000100000000000000000000000000000000000000000000000000000000000L, "d1");bitMap.put(0b0000000000001000000000000000000000000000000000000000000000000000L, "d2");bitMap.put(0b0000000000000000000010000000000000000000000000000000000000000000L, "d3");bitMap.put(0b0000000000000000000000000000100000000000000000000000000000000000L, "d4");bitMap.put(0b0000000000000000000000000000000000001000000000000000000000000000L, "d5");bitMap.put(0b0000000000000000000000000000000000000000000010000000000000000000L, "d6");bitMap.put(0b0000000000000000000000000000000000000000000000000000100000000000L, "d7");bitMap.put(0b0000000000000000000000000000000000000000000000000000000000001000L, "d8");
    	bitMap.put(0b0001000000000000000000000000000000000000000000000000000000000000L, "e1");bitMap.put(0b0000000000010000000000000000000000000000000000000000000000000000L, "e2");bitMap.put(0b0000000000000000000100000000000000000000000000000000000000000000L, "e3");bitMap.put(0b0000000000000000000000000001000000000000000000000000000000000000L, "e4");bitMap.put(0b0000000000000000000000000000000000010000000000000000000000000000L, "e5");bitMap.put(0b0000000000000000000000000000000000000000000100000000000000000000L, "e6");bitMap.put(0b0000000000000000000000000000000000000000000000000001000000000000L, "e7");bitMap.put(0b0000000000000000000000000000000000000000000000000000000000010000L, "e8");
    	bitMap.put(0b0010000000000000000000000000000000000000000000000000000000000000L, "f1");bitMap.put(0b0000000000100000000000000000000000000000000000000000000000000000L, "f2");bitMap.put(0b0000000000000000001000000000000000000000000000000000000000000000L, "f3");bitMap.put(0b0000000000000000000000000010000000000000000000000000000000000000L, "f4");bitMap.put(0b0000000000000000000000000000000000100000000000000000000000000000L, "f5");bitMap.put(0b0000000000000000000000000000000000000000001000000000000000000000L, "f6");bitMap.put(0b0000000000000000000000000000000000000000000000000010000000000000L, "f7");bitMap.put(0b0000000000000000000000000000000000000000000000000000000000100000L, "f8");
    	bitMap.put(0b0100000000000000000000000000000000000000000000000000000000000000L, "g1");bitMap.put(0b0000000001000000000000000000000000000000000000000000000000000000L, "g2");bitMap.put(0b0000000000000000010000000000000000000000000000000000000000000000L, "g3");bitMap.put(0b0000000000000000000000000100000000000000000000000000000000000000L, "g4");bitMap.put(0b0000000000000000000000000000000001000000000000000000000000000000L, "g5");bitMap.put(0b0000000000000000000000000000000000000000010000000000000000000000L, "g6");bitMap.put(0b0000000000000000000000000000000000000000000000000100000000000000L, "g7");bitMap.put(0b0000000000000000000000000000000000000000000000000000000001000000L, "g8");
    	bitMap.put(0b1000000000000000000000000000000000000000000000000000000000000000L, "h1");bitMap.put(0b0000000010000000000000000000000000000000000000000000000000000000L, "h2");bitMap.put(0b0000000000000000100000000000000000000000000000000000000000000000L, "h3");bitMap.put(0b0000000000000000000000001000000000000000000000000000000000000000L, "h4");bitMap.put(0b0000000000000000000000000000000010000000000000000000000000000000L, "h5");bitMap.put(0b0000000000000000000000000000000000000000100000000000000000000000L, "h6");bitMap.put(0b0000000000000000000000000000000000000000000000001000000000000000L, "h7");bitMap.put(0b0000000000000000000000000000000000000000000000000000000010000000L, "h8");
    	bitMapR.put("a1",0b0000000100000000000000000000000000000000000000000000000000000000L);bitMapR.put("a2",0b0000000000000001000000000000000000000000000000000000000000000000L);bitMapR.put("a3",0b0000000000000000000000010000000000000000000000000000000000000000L);bitMapR.put("a4",0b0000000000000000000000000000000100000000000000000000000000000000L);bitMapR.put("a5",0b0000000000000000000000000000000000000001000000000000000000000000L);bitMapR.put("a6",0b0000000000000000000000000000000000000000000000010000000000000000L);bitMapR.put("a7",0b0000000000000000000000000000000000000000000000000000000100000000L);bitMapR.put("a8",0b0000000000000000000000000000000000000000000000000000000000000001L);
    	bitMapR.put("b1",0b0000001000000000000000000000000000000000000000000000000000000000L);bitMapR.put("b2",0b0000000000000010000000000000000000000000000000000000000000000000L);bitMapR.put("b3",0b0000000000000000000000100000000000000000000000000000000000000000L);bitMapR.put("b4",0b0000000000000000000000000000001000000000000000000000000000000000L);bitMapR.put("b5",0b0000000000000000000000000000000000000010000000000000000000000000L);bitMapR.put("b6",0b0000000000000000000000000000000000000000000000100000000000000000L);bitMapR.put("b7",0b0000000000000000000000000000000000000000000000000000001000000000L);bitMapR.put("b8",0b0000000000000000000000000000000000000000000000000000000000000010L);
    	bitMapR.put("c1",0b0000010000000000000000000000000000000000000000000000000000000000L);bitMapR.put("c2",0b0000000000000100000000000000000000000000000000000000000000000000L);bitMapR.put("c3",0b0000000000000000000001000000000000000000000000000000000000000000L);bitMapR.put("c4",0b0000000000000000000000000000010000000000000000000000000000000000L);bitMapR.put("c5",0b0000000000000000000000000000000000000100000000000000000000000000L);bitMapR.put("c6",0b0000000000000000000000000000000000000000000001000000000000000000L);bitMapR.put("c7",0b0000000000000000000000000000000000000000000000000000010000000000L);bitMapR.put("c8",0b0000000000000000000000000000000000000000000000000000000000000100L);
    	bitMapR.put("d1",0b0000100000000000000000000000000000000000000000000000000000000000L);bitMapR.put("d2",0b0000000000001000000000000000000000000000000000000000000000000000L);bitMapR.put("d3",0b0000000000000000000010000000000000000000000000000000000000000000L);bitMapR.put("d4",0b0000000000000000000000000000100000000000000000000000000000000000L);bitMapR.put("d5",0b0000000000000000000000000000000000001000000000000000000000000000L);bitMapR.put("d6",0b0000000000000000000000000000000000000000000010000000000000000000L);bitMapR.put("d7",0b0000000000000000000000000000000000000000000000000000100000000000L);bitMapR.put("d8",0b0000000000000000000000000000000000000000000000000000000000001000L);
    	bitMapR.put("e1",0b0001000000000000000000000000000000000000000000000000000000000000L);bitMapR.put("e2",0b0000000000010000000000000000000000000000000000000000000000000000L);bitMapR.put("e3",0b0000000000000000000100000000000000000000000000000000000000000000L);bitMapR.put("e4",0b0000000000000000000000000001000000000000000000000000000000000000L);bitMapR.put("e5",0b0000000000000000000000000000000000010000000000000000000000000000L);bitMapR.put("e6",0b0000000000000000000000000000000000000000000100000000000000000000L);bitMapR.put("e7",0b0000000000000000000000000000000000000000000000000001000000000000L);bitMapR.put("e8",0b0000000000000000000000000000000000000000000000000000000000010000L);
    	bitMapR.put("f1",0b0010000000000000000000000000000000000000000000000000000000000000L);bitMapR.put("f2",0b0000000000100000000000000000000000000000000000000000000000000000L);bitMapR.put("f3",0b0000000000000000001000000000000000000000000000000000000000000000L);bitMapR.put("f4",0b0000000000000000000000000010000000000000000000000000000000000000L);bitMapR.put("f5",0b0000000000000000000000000000000000100000000000000000000000000000L);bitMapR.put("f6",0b0000000000000000000000000000000000000000001000000000000000000000L);bitMapR.put("f7",0b0000000000000000000000000000000000000000000000000010000000000000L);bitMapR.put("f8",0b0000000000000000000000000000000000000000000000000000000000100000L);
    	bitMapR.put("g1",0b0100000000000000000000000000000000000000000000000000000000000000L);bitMapR.put("g2",0b0000000001000000000000000000000000000000000000000000000000000000L);bitMapR.put("g3",0b0000000000000000010000000000000000000000000000000000000000000000L);bitMapR.put("g4",0b0000000000000000000000000100000000000000000000000000000000000000L);bitMapR.put("g5",0b0000000000000000000000000000000001000000000000000000000000000000L);bitMapR.put("g6",0b0000000000000000000000000000000000000000010000000000000000000000L);bitMapR.put("g7",0b0000000000000000000000000000000000000000000000000100000000000000L);bitMapR.put("g8",0b0000000000000000000000000000000000000000000000000000000001000000L);
    	bitMapR.put("h1",0b1000000000000000000000000000000000000000000000000000000000000000L);bitMapR.put("h2",0b0000000010000000000000000000000000000000000000000000000000000000L);bitMapR.put("h3",0b0000000000000000100000000000000000000000000000000000000000000000L);bitMapR.put("h4",0b0000000000000000000000001000000000000000000000000000000000000000L);bitMapR.put("h5",0b0000000000000000000000000000000010000000000000000000000000000000L);bitMapR.put("h6",0b0000000000000000000000000000000000000000100000000000000000000000L);bitMapR.put("h7",0b0000000000000000000000000000000000000000000000001000000000000000L);bitMapR.put("h8",0b0000000000000000000000000000000000000000000000000000000010000000L);
	
	}
	


	@Test
	public void alphaBetaTest1() {
		Othello.totalMovesFound = 0;
		
		long whitePieces = 0b0000000000111110001000001110110100001000000111000000000000000000L;
		long blackPieces = 0b0000100000000000000010000001001011110000111000000101000010010000L;
		//#  A  B  C  D  E  F  G  H  #	
		//1  -  -  -  B  -  -  -  -  #	
		//2  -  W  W  W  W  W  -  -  #
		//3  -  -  -  B  -  W  -  -  #	
		//4  W  B  W  W  B  W  W  W  #	
		//5  -  -  -  W  B  B  B  B  #	
		//6  -  -  W  W  W  B  B  B  #	
		//7  -  -  -  -  B  -  B  -  #	
		//8  -  -  -  -  B  -  -  B  #	
		//#  #  #  #  #  #  #  #  #  #	
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		int currentPlayer = 1;
		BitMove bestMove = null;
		
		//Measure the time to finish depth 10 search with shallow ordering
		long start = System.nanoTime();    
		System.out.println("Shallow Ordering:");
		bestMove = bb.getBestMoveSOPD(currentPlayer, 10);
		long elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 10 : "+elapsedTime+"ms");
		System.out.println("Depth 10 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
		
		//Measure the time to finish depth 10 search with shallow ordering and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with killer move:");
		bestMove = bb.getBestMoveSO3PDK(currentPlayer, 10);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 10 : "+elapsedTime+"ms");
		System.out.println("Depth 10 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
		
		//Measure the time to finish depth 10 search with shallow ordering and transposition table
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table:");
		bestMove = bb.getBestMoveSOTT(currentPlayer, 10);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 10 : "+elapsedTime+"ms");
		System.out.println("Depth 10 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
		
		//Measure the time to finish depth 10 search with shallow ordering and transposition table and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table and killer move:");
		bestMove = bb.getBestMoveSO3PDKTT(currentPlayer, 10);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 10 : "+elapsedTime+"ms");
		System.out.println("Depth 10 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
	}
	
	
	
	@Test
	public void alphaBetaTest2() {
		Othello.totalMovesFound = 0;
		
		long whitePieces = 0b0000000000000000000000000011100000010000000010000000000000000000L;
		long blackPieces = 0b0000000000000000000000000000000000001100000100000000000000000000L;
		//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  -  -  -  -  -  # 
		//2  -  -  -  -  -  -  -  -  #
		//3  -  -  -  -  -  -  -  -  # 
		//4  -  -  -  W  W  W  -  -  # 
		//5  -  -  B  B  W  -  -  -  # 
		//6  -  -  -  W  B  -  -  -  # 
		//7  -  -  -  -  -  -  -  -  # 
		//8  -  -  -  -  -  -  -  -  #
		//#  #  #  #  #  #  #  #  #  # 
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		int currentPlayer = 1;
		BitMove bestMove = null;

		//Measure the time to finish depth 12 search with shallow ordering
		long start = System.nanoTime();    
		System.out.println("Shallow Ordering:");
		bestMove = bb.getBestMoveSOPD(currentPlayer, 12);
		long elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 12 : "+elapsedTime+"ms");
		System.out.println("Depth 12 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 12 search with shallow ordering and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with killer move:");
		bestMove = bb.getBestMoveSO3PDK(currentPlayer, 12);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 12 : "+elapsedTime+"ms");
		System.out.println("Depth 12 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 12 search with shallow ordering and transposition table
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table:");
		bestMove = bb.getBestMoveSOTT(currentPlayer, 12);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 12 : "+elapsedTime+"ms");
		System.out.println("Depth 12 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 12 search with shallow ordering and transposition table and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table and killer move:");
		bestMove = bb.getBestMoveSO3PDKTT(currentPlayer, 12);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 12 : "+elapsedTime+"ms");
		System.out.println("Depth 12 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
	}
	

	
	@Test
	public void alphaBetaTest3() {
		Othello.totalMovesFound = 0;
		Othello.myPlayer=new Player("B");
		long whitePieces = 0b0010000001011100111111100000010000001001001100110111111101111000L;
		long blackPieces = 0b1000001100000010000000010111101010010100100011001000000000000010L;
		//#  A  B  C  D  E  F  G  H  #)
		//1  B  B  -  -  -  W  -  B  #)
		//2  -  B  W  W  W  -  W  -  #)
		//3  B  W  W  W  W  W  W  W  #)
		//4  -  B  W  B  B  B  B  -  #)
		//5  W  -  B  W  B  -  -  B  #)
		//6  W  W  B  B  W  W  -  B  #)
		//7  W  W  W  W  W  W  W  B  #)
		//8  -  B  -  W  W  W  W  -  #)
		//#  #  #  #  #  #  #  #  #  #)
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		int currentPlayer = 1;
		BitMove bestMove = null;

		//Measure the time to finish depth 18 search with shallow ordering
		long start = System.nanoTime();    
		System.out.println("Shallow Ordering:");
		bestMove = bb.getBestMoveSOPD(currentPlayer, 18);
		long elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 18 : "+elapsedTime+"ms");
		System.out.println("Depth 18 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 18 search with shallow ordering and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with killer move:");
		bestMove = bb.getBestMoveSO3PDK(currentPlayer, 18);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 18 : "+elapsedTime+"ms");
		System.out.println("Depth 18 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 18 search with shallow ordering and transposition table
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table:");
		bestMove = bb.getBestMoveSOTT(currentPlayer, 18);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 18 : "+elapsedTime+"ms");
		System.out.println("Depth 18 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 18 search with shallow ordering and transposition table and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table and killer move:");
		bestMove = bb.getBestMoveSO3PDKTT(currentPlayer, 18);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 18 : "+elapsedTime+"ms");
		System.out.println("Depth 18 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
	}
	
	
	@Test
	public void alphaBetaTest4() {
		Othello.totalMovesFound = 0;
		long blackPieces = 0b0001111100010001000010010000100100000001000010010000000100001000L;
		long whitePieces = 0b0100000000101110001101100111011000111110001101100011111000010100L;
				//(C #  A  B  C  D  E  F  G  H  #).
				//(C 1  B  B  B  B  B  -  W  -  #).
				//(C 2  B  W  W  W  B  W  -  -  #).
				//(C 3  B  W  W  B  W  W  -  -  #).
				//(C 4  B  W  W  B  W  W  W  -  #).
				//(C 5  B  W  W  W  W  W  -  -  #).
				//(C 6  B  W  W  B  W  W  -  -  #).
				//(C 7  B  W  W  W  W  W  -  -  #).
				//(C 8  -  -  W  B  W  -  -  -  #).
				//(C #  #  #  #  #  #  #  #  #  #).
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		int currentPlayer = 1;
		BitMove bestMove = null;

		//Measure the time to finish depth 18 search with shallow ordering
		long start = System.nanoTime();    
		System.out.println("Shallow Ordering:");
		bestMove = bb.getBestMoveSOPD(currentPlayer, 18);
		long elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 18 : "+elapsedTime+"ms");
		System.out.println("Depth 18 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 18 search with shallow ordering and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with killer move:");
		bestMove = bb.getBestMoveSO3PDK(currentPlayer, 18);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 18 : "+elapsedTime+"ms");
		System.out.println("Depth 18 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 18 search with shallow ordering and transposition table
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table:");
		bestMove = bb.getBestMoveSOTT(currentPlayer, 18);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 18 : "+elapsedTime+"ms");
		System.out.println("Depth 18 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 18 search with shallow ordering and transposition table and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table and killer move:");
		bestMove = bb.getBestMoveSO3PDKTT(currentPlayer, 18);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 18 : "+elapsedTime+"ms");
		System.out.println("Depth 18 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
	}
	
	
	
	
	@Test
	public void alphaBetaTest5() {
		Othello.totalMovesFound = 0;
		long whitePieces = 0b0000000000000000010100000010001001110100001110000000000000000000L;
		long blackPieces = 0b0000000000000000001000000001110000001000000000000000000000000000L;
		//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  -  -  -  -  -  #
		//2  -  -  -  -  -  -  -  -  #
		//3  -  -  -  -  W  B  W  -  #
		//4  -  W  B  B  B  W  -  -  #
		//5  -  -  W  B  W  W  W  -  #
		//6  -  -  -  W  W  W  -  -  #
		//7  -  -  -  -  -  -  -  -  #
		//8  -  -  -  -  -  -  -  -  #
		//#  #  #  #  #  #  #  #  #  #
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		int currentPlayer = 1;
		BitMove bestMove = null;

		//Measure the time to finish depth 12 search with shallow ordering
		long start = System.nanoTime();    
		System.out.println("Shallow Ordering:");
		bestMove = bb.getBestMoveSOPD(currentPlayer, 12);
		long elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 12 : "+elapsedTime+"ms");
		System.out.println("Depth 12 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 12 search with shallow ordering and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with killer move:");
		bestMove = bb.getBestMoveSO3PDK(currentPlayer, 12);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 12 : "+elapsedTime+"ms");
		System.out.println("Depth 12 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 12 search with shallow ordering and transposition table
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table:");
		bestMove = bb.getBestMoveSOTT(currentPlayer, 12);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 12 : "+elapsedTime+"ms");
		System.out.println("Depth 12 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
		Othello.totalMovesFound = 0;
				
		//Measure the time to finish depth 12 search with shallow ordering and transposition table and killer move
		start = System.nanoTime();    
		System.out.println("Shallow Ordering with transposition table and killer move:");
		bestMove = bb.getBestMoveSO3PDKTT(currentPlayer, 12);
		elapsedTime = (System.nanoTime() - start)/1000000;
		System.out.println("Time to finish depth 12 : "+elapsedTime+"ms");
		System.out.println("Depth 12 move found " + bestMove.getBoardPosition() + " " +bitMap.get(bestMove.getBoardPosition())+ " " + bestMove.getValue());
		System.out.println("Total moves found : " + Othello.totalMovesFound + "\n");
	}
	
}
