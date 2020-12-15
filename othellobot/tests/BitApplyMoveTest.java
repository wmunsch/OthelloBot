package com.williammunsch.othellobot.tests;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.williammunsch.othellobot.BitBoard;
import com.williammunsch.othellobot.BitEvaluator;
import com.williammunsch.othellobot.BitMove;

/** 
 * Testing class to test the applyMove method to make sure it is applying moves correctly.
 * Given a board state and expected board state, call the applyMove and compare the board state to expected board state.
 */
public class BitApplyMoveTest {
	public static final Map<Long, String> bitMap = new HashMap<>(); //use two hash maps to allow for instant translation between input and array indexes
	public static final Map<String, Long> bitMapR = new HashMap<>();
	private static final BitEvaluator evaluator = new BitEvaluator();

	public BitApplyMoveTest() {
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
	public void applyMoveBoard1Test() {
		long whitePieces = 0b0000000000001000000010001000100001001000000001000000000000000000L;
		long blackPieces = 0b0000000000000000001000000011000000110000011100000000000000000000L;
		
		long expectedWhitePieces =  0b0000000000001000000010001000100001011000011001000100000000000000L;
		long expectedBlackPieces =  0b0000000000000000001000000011000000100000000100000000000000000000L;
									
		//#  A  B  C  D  E  F  G  H  #	//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  -  -  -  -  -  #	//1  -  -  -  -  -  -  -  -  #
		//2  -  -  -  W  -  -  -  -  #	//2  -  -  -  W  -  -  -  -  #
		//3  -  -  -  W  -  B  -  -  #	//3  -  -  -  W  -  B  -  -  #
		//4  -  -  -  W  B  B  -  W  #	//4  -  -  -  W  B  B  -  W  #
		//5  -  -  -  W  B  B  W  -  #	//5  -  -  -  W  W  B  W  -  #
		//6  -  -  W  -  B  B  B  -  #	//6  -  -  W  -  B  W  W  -  #
		//7  -  -  -  -  -  -  -  -  #	//7  -  -  -  -  -  -  W  -  #
		//8  -  -  -  -  -  -  -  -  #	//8  -  -  -  -  -  -  -  -  #
		//#  #  #  #  #  #  #  #  #  #	//#  #  #  #  #  #  #  #  #  #
		
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,false);
		BitBoard ebb = new BitBoard(expectedWhitePieces, expectedBlackPieces,0,false);
		BitMove m = new BitMove(bitMapR.get("g7"));
		m.setPiecesToBeFlipped(bb.getFlipMask(m.getBoardPosition()));
	
		assertTimeout(ofMillis(100), ()-> {
			bb.applyMove(m,1);
			assertEquals(bb.whitePieces, ebb.whitePieces, "Invalid piece detected");
			assertEquals(bb.blackPieces, ebb.blackPieces, "Invalid piece detected");
			assertEquals(8,evaluator.countFrontiers(ebb.whitePieces, ebb.whitePieces|ebb.blackPieces));
		},"Generating moves took longer than expected.");
		
	}
	
	@Test
	public void applyMoveBoard2Test() {
		long whitePieces = 0b0000000000000000000000000011100000010000000010000000000000000000L;
		long blackPieces = 0b0000000000000000000000000000000000001100000100000000000000000000L;
		long expectedWhitePieces = 0b0000000000000000000000000011100000010000000000000000000000000000L;
		long expectedBlackPieces = 0b0000000000000000000000000000000000001100000110000000100000000000L;

		//#  A  B  C  D  E  F  G  H  #  //#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  -  -  -  -  -  #  //1  -  -  -  -  -  -  -  -  #
		//2  -  -  -  -  -  -  -  -  #  //2  -  -  -  -  -  -  -  -  #
		//3  -  -  -  -  -  -  -  -  #  //3  -  -  -  -  -  -  -  -  #
		//4  -  -  -  W  W  W  -  -  #  //4  -  -  -  W  W  W  -  -  #
		//5  -  -  B  B  W  -  -  -  #  //5  -  -  B  B  W  -  -  -  #
		//6  -  -  -  W  B  -  -  -  #  //6  -  -  -  B  B  -  -  -  #
		//7  -  -  -  O  -  -  -  -  #  //7  -  -  -  B  -  -  -  -  # 
		//8  -  -  -  -  -  -  -  -  #  //8  -  -  -  -  -  -  -  -  #
		//#  #  #  #  #  #  #  #  #  #  //#  #  #  #  #  #  #  #  #  #
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true); //Created a board state with current player as black (true)
		BitBoard ebb = new BitBoard(expectedWhitePieces, expectedBlackPieces,0,true);

		
		BitMove m = new BitMove(bitMapR.get("d7"));
		m.setPiecesToBeFlipped(bb.getFlipMask(m.getBoardPosition()));
		
		assertTimeout(ofMillis(100), ()-> {
			bb.applyMove(m,1);
			//pb.print(bb.blackPieces, bb.whitePieces);
			
			assertEquals(bb.whitePieces, ebb.whitePieces, "Invalid piece detected");
			assertEquals(bb.blackPieces, ebb.blackPieces, "Invalid piece detected");
			assertEquals(4,evaluator.countFrontiers(ebb.blackPieces, ebb.whitePieces|ebb.blackPieces));
			
		},"Generating moves took longer than expected.");
		
	}
	
	@Test
	public void applyMoveBoard3Test() {		
		long whitePieces = 0b0000000000000000000000000011100000000100000000100001000000000000L;
		long blackPieces = 0b0000000000000000000000000000000000111000000110000000100000000000L;

		long expectedWhitePieces = 0b0000000000000000000000000010000000000100000000100001000000000000L;
		long expectedBlackPieces = 0b0000000000000000000010000001100000111000000110000000100000000000L;
	
		//#  A  B  C  D  E  F  G  H  #	//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  -  -  -  -  -  #	//1  -  -  -  -  -  -  -  -  #
		//2  -  -  -  -  -  -  -  -  #	//2  -  -  -  -  -  -  -  -  #
		//3  -  -  -  O  -  -  -  -  #	//3  -  -  -  B  -  -  -  -  #
		//4  -  -  -  W  W  W  -  -  # 	//4  -  -  -  B  B  W  -  -  #
		//5  -  -  W  B  B  B  -  -  # 	//5  -  -  W  B  B  B  -  -  # 
		//6  -  W  -  B  B  -  -  -  #	//6  -  W  -  B  B  -  -  -  #
		//7  -  -  -  B  W  -  -  -  #	//7  -  -  -  B  W  -  -  -  # 
		//8  -  -  -  -  -  -  -  -  #	//8  -  -  -  -  -  -  -  -  #
		//#  #  #  #  #  #  #  #  #  #	//#  #  #  #  #  #  #  #  #  #
		
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		BitBoard ebb = new BitBoard(expectedWhitePieces, expectedBlackPieces,0,true);
		BitMove m = new BitMove(bitMapR.get("d3"));
		m.setPiecesToBeFlipped(bb.getFlipMask(m.getBoardPosition()));
		
		assertTimeout(ofMillis(100), ()-> {
			bb.applyMove(m,1);
			
			assertEquals(bb.whitePieces, ebb.whitePieces, "Invalid piece detected");
			assertEquals(bb.blackPieces, ebb.blackPieces, "Invalid piece detected");
			assertEquals(7,evaluator.countFrontiers(ebb.blackPieces, ebb.whitePieces|ebb.blackPieces));
		},"Generating moves took longer than expected.");
		
	}
	
	@Test
	public void applyMoveBoard4Test() {
		long whitePieces = 0b0000000000001000000010001000100001001000000001000000000000000000L;
		long blackPieces = 0b0000000000000000001000000011000000110000011100000000000000000000L;
		
		long expectedWhitePieces =  0b0000000000001000000010001000100001011000011001000100000000000000L;
		long expectedBlackPieces =  0b0000000000000000001000000011000000100000000100000000000000000000L;
									
		//#  A  B  C  D  E  F  G  H  #	//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  -  -  -  -  -  #	//1  -  -  -  -  -  -  -  -  #
		//2  -  -  -  W  -  -  -  -  #	//2  -  -  -  W  -  -  -  -  #
		//3  -  -  -  W  -  B  -  -  #	//3  -  -  -  W  -  B  -  -  #
		//4  -  -  -  W  B  B  -  W  #	//4  -  -  -  W  B  B  -  W  #
		//5  -  -  -  W  B  B  W  -  #	//5  -  -  -  W  W  B  W  -  #
		//6  -  -  W  -  B  B  B  -  #	//6  -  -  W  -  B  W  W  -  #
		//7  -  -  -  -  -  -  -  -  #	//7  -  -  -  -  -  -  W  -  #
		//8  -  -  -  -  -  -  -  -  #	//8  -  -  -  -  -  -  -  -  #
		//#  #  #  #  #  #  #  #  #  #	//#  #  #  #  #  #  #  #  #  #
		
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,false);//Created a board state with current player as white (false)
		BitBoard ebb = new BitBoard(expectedWhitePieces, expectedBlackPieces,0,false);
		BitMove m = new BitMove(bitMapR.get("g7"));
		m.setPiecesToBeFlipped(bb.getFlipMask(m.getBoardPosition()));
		
		assertTimeout(ofMillis(100), ()-> {
			bb.applyMove(m,1);
			assertEquals(bb.whitePieces, ebb.whitePieces, "Invalid piece detected");
			assertEquals(bb.blackPieces, ebb.blackPieces, "Invalid piece detected");
			assertEquals(8,evaluator.countFrontiers(ebb.whitePieces, ebb.whitePieces|ebb.blackPieces));
		},"Generating moves took longer than expected.");
		
	}
	
	@Test
	public void applyMoveBoard5Test() {		
		long whitePieces = 0b0000000000111110001000001110110100001000000111000000000000000000L;
		long blackPieces = 0b0000100000000000000010000001001011110000111000000101000010010000L;
		
		long expectedWhitePieces = 0b0000000000111110001000000010110100001000000111000000000000000000L;
		long expectedBlackPieces = 0b0000100000000000100010001101001011110000111000000101000010010000L;
		
	
		//#  A  B  C  D  E  F  G  H  #	//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  B  -  -  -  -  #	//1  -  -  -  B  -  -  -  -  #
		//2  -  W  W  W  W  W  -  -  #	//2  -  W  W  W  W  W  -  -  #
		//3  -  -  -  B  -  W  -  -  #	//3  -  -  -  B  -  W  -  B  #
		//4  W  B  W  W  B  W  W  W  #	//4  W  B  W  W  B  W  B  B  #
		//5  -  -  -  W  B  B  B  B  #	//5  -  -  -  W  B  B  B  B  #
		//6  -  -  W  W  W  B  B  B  #	//6  -  -  W  W  W  B  B  B  #
		//7  -  -  -  -  B  -  B  -  #	//7  -  -  -  -  B  -  B  -  #
		//8  -  -  -  -  B  -  -  B  #	//8  -  -  -  -  B  -  -  B  #
		//#  #  #  #  #  #  #  #  #  #	//#  #  #  #  #  #  #  #  #  #
		
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		BitBoard ebb = new BitBoard(expectedWhitePieces, expectedBlackPieces,0,true);
		BitMove m = new BitMove(bitMapR.get("h3"));
		m.setPiecesToBeFlipped(bb.getFlipMask(m.getBoardPosition()));
		
		assertTimeout(ofMillis(100), ()-> {
			bb.applyMove(m,1);

			assertEquals(bb.whitePieces, ebb.whitePieces, "Invalid piece detected");
			assertEquals(bb.blackPieces, ebb.blackPieces, "Invalid piece detected");
			assertEquals(6,evaluator.countFrontiers(ebb.blackPieces, ebb.whitePieces|ebb.blackPieces));
		},"Generating moves took longer than expected.");
		
	}
	
	@Test
	public void applyMoveBoard6Test() {		
		long whitePieces = 0b0000000000111110001000001110110100001000000111000000000000000000L;
		long blackPieces = 0b0000100000000000000010000001001011110000111000000101000010010000L;
		
		long expectedWhitePieces = 0b0000000000111110001000001110110100001000000101000000000000000000L;
		long expectedBlackPieces = 0b0000100000000000000010000001001011110000111010000101010010010000L;

		//#  A  B  C  D  E  F  G  H  #	//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  B  -  -  -  -  #	//1  -  -  -  B  -  -  -  -  #
		//2  -  W  W  W  W  W  -  -  #	//2  -  W  W  W  W  W  -  -  #
		//3  -  -  -  B  -  W  -  -  #	//3  -  -  -  B  -  W  -  -  #
		//4  W  B  W  W  B  W  W  W  #	//4  W  B  W  W  B  W  W  W  #
		//5  -  -  -  W  B  B  B  B  #	//5  -  -  -  W  B  B  B  B  #
		//6  -  -  W  W  W  B  B  B  #	//6  -  -  W  B  W  B  B  B  #
		//7  -  -  -  -  B  -  B  -  #	//7  -  -  B  -  B  -  B  -  #
		//8  -  -  -  -  B  -  -  B  #	//8  -  -  -  -  B  -  -  B  #
		//#  #  #  #  #  #  #  #  #  #	//#  #  #  #  #  #  #  #  #  #
		
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		BitBoard ebb = new BitBoard(expectedWhitePieces, expectedBlackPieces,0,true);
		BitMove m = new BitMove(bitMapR.get("c7"));
		m.setPiecesToBeFlipped(bb.getFlipMask(m.getBoardPosition()));
		
		assertTimeout(ofMillis(100), ()-> {
			bb.applyMove(m,1);
			assertEquals(bb.whitePieces, ebb.whitePieces, "Invalid piece detected");
			assertEquals(bb.blackPieces, ebb.blackPieces, "Invalid piece detected");
			assertEquals(7,evaluator.countFrontiers(ebb.blackPieces, ebb.whitePieces|ebb.blackPieces));
		},"Generating moves took longer than expected.");
		
	}
	
	@Test
	public void applyMoveBoard7Test() {	
		long whitePieces = 0b0000000000000000000000000011100000110000111010000100000010000000L;
		long blackPieces = 0b0000000000000000000000000000000000001000000101000001111001011111L;
		
		long expectedWhitePieces = 0b0000000000000000000000000011100000110000111000000100000010000000L;
		long expectedBlackPieces = 0b0000000000000000000000000000000000001100000111000001111001011111L;
		
		//#  A  B  C  D  E  F  G  H  #	//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  -  -  -  -  -  #	//1  -  -  -  -  -  -  -  -  #
		//2  -  -  -  -  -  -  -  -  #	//2  -  -  -  -  -  -  -  -  #
		//3  -  -  -  -  -  -  -  -  #	//3  -  -  -  -  -  -  -  -  #
		//4  -  -  -  W  W  W  -  -  #	//4  -  -  -  W  W  W  -  -  #
		//5  -  -  -  B  W  W  -  -  #	//5  -  -  B  B  W  W  -  -  #
		//6  -  -  B  W  B  W  W  W  #	//6  -  -  B  B  B  W  W  W  #
		//7  -  B  B  B  B  -  W  -  #	//7  -  B  B  B  B  -  W  -  #
		//8  B  B  B  B  B  -  B  W  #	//8  B  B  B  B  B  -  B  W  #
		//#  #  #  #  #  #  #  #  #  #	//#  #  #  #  #  #  #  #  #  #
		
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		BitBoard ebb = new BitBoard(expectedWhitePieces, expectedBlackPieces,0,true);
		BitMove m = new BitMove(bitMapR.get("c5"));
		m.setPiecesToBeFlipped(bb.getFlipMask(m.getBoardPosition()));
		
		assertTimeout(ofMillis(100), ()-> {
			bb.applyMove(m,1);
			assertEquals(bb.whitePieces, ebb.whitePieces, "Invalid piece detected");
			assertEquals(bb.blackPieces, ebb.blackPieces, "Invalid piece detected");
			assertEquals(3,evaluator.countFrontiers(ebb.blackPieces, ebb.whitePieces|ebb.blackPieces));
		},"Generating moves took longer than expected.");
		
	}
	
	@Test
	public void applyMoveBoard8Test() {		
		long whitePieces = 0b0010000001011100111111100000010000001001001100110111111101111000L;
		long blackPieces = 0b1000001100000010000000010111101010010100100011001000000000000010L;
		
		long expectedWhitePieces = 0b0010000001011100111111100000010000001001001100110111101101111000L;
		long expectedBlackPieces = 0b1000001100000010000000010111101010010100100011001000010000000110L;
		
		//#  A  B  C  D  E  F  G  H  #) //#  A  B  C  D  E  F  G  H  #).
		//1  B  B  -  -  -  W  -  B  #).//1  B  B  -  -  -  W  -  B  #)
		//2  -  B  W  W  W  -  W  -  #).//2  -  B  W  W  W  -  W  -  #).
		//3  B  W  W  W  W  W  W  W  #).//3  B  W  W  W  W  W  W  W  #).
		//4  -  B  W  B  B  B  B  -  #).//4  -  B  W  B  B  B  B  -  #).
		//5  W  -  B  W  B  -  -  B  #).//5  W  -  B  W  B  -  -  B  #).
		//6  W  W  B  B  W  W  -  B  #).//6  W  W  B  B  W  W  -  B  #).
		//7  W  W  W  W  W  W  W  B  #).//7  W  W  B  W  W  W  W  B  #).
		//8  -  B  -  W  W  W  W  -  #).//8  -  B  B  W  W  W  W  -  #).
		//#  #  #  #  #  #  #  #  #  #).//#  #  #  #  #  #  #  #  #  #).

		
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);
		BitBoard ebb = new BitBoard(expectedWhitePieces, expectedBlackPieces,0,true);
		BitMove m = new BitMove(bitMapR.get("c8"));
		m.setPiecesToBeFlipped(bb.getFlipMask(m.getBoardPosition()));
		
		assertTimeout(ofMillis(100), ()-> {
			bb.applyMove(m,1);
			assertEquals(bb.whitePieces, ebb.whitePieces, "Invalid piece detected");
			assertEquals(bb.blackPieces, ebb.blackPieces, "Invalid piece detected");
		},"Generating moves took longer than expected.");
		
	}
	
	
	@Test
	public void applyMoveBoard9Test() {		
		long whitePieces = 0b0000010000011110001111100011111000101100000100000000000000000000L;
		long blackPieces = 0b0000000000000001000000010000000100010011001011110111111111111111L;
		
		long expectedWhitePieces = 0b0000010000011110001111100011111000101100011100000000000000000000L;
		long expectedBlackPieces = 0b0000000000000001000000010000000100010011000011110111111111111111L;
		

		//C #  A  B  C  D  E  F  G  H  #	//C #  A  B  C  D  E  F  G  H  #
		//C 1  -  -  W  -  -  -  -  -  #	//C 1  -  -  W  -  -  -  -  -  #
		//C 2  B  W  W  W  W  -  -  -  #	//C 2  B  W  W  W  W  -  -  -  #
		//C 3  B  W  W  W  W  W  -  -  #	//C 3  B  W  W  W  W  W  -  -  #
		//C 4  B  W  W  W  W  W  -  -  #	//C 4  B  W  W  W  W  W  -  -  #
		//C 5  B  B  W  W  B  W  -  -  #	//C 5  B  B  W  W  B  W  -  -  #
		//C 6  B  B  B  B  W  B  -  -  #	//C 6  B  B  B  B  W  W  W  -  #
		//C 7  B  B  B  B  B  B  B  -  #	//C 7  B  B  B  B  B  B  B  -  #
		//C 8  B  B  B  B  B  B  B  B  #	//C 8  B  B  B  B  B  B  B  B  #
		//C #  #  #  #  #  #  #  #  #  #	//C #  #  #  #  #  #  #  #  #  #

		
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,false);
		BitBoard ebb = new BitBoard(expectedWhitePieces, expectedBlackPieces,0,false);
		BitMove m = new BitMove(bitMapR.get("g6"));
		m.setPiecesToBeFlipped(bb.getFlipMask(m.getBoardPosition()));
		
		assertTimeout(ofMillis(100), ()-> {
			bb.applyMove(m,1);
			assertEquals(bb.whitePieces, ebb.whitePieces, "Invalid piece detected");
			assertEquals(bb.blackPieces, ebb.blackPieces, "Invalid piece detected");
		},"Generating moves took longer than expected.");
		
	}

	

}
