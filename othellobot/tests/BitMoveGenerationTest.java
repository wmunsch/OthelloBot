package com.williammunsch.othellobot.tests;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.williammunsch.othellobot.BitBoard;
import com.williammunsch.othellobot.BitMove;

/**
 * This testing class is used to test for proper move generation for the generateMove method.
 * Create a board state and the list of moves, then test against what the generate move method returns.
 */
public class BitMoveGenerationTest {
	public static final Map<Long, String> bitMap = new HashMap<>(); //use two hash maps to allow for instant translation between input and array indexes
	public static final Map<String, Long> bitMapR = new HashMap<>();

	public BitMoveGenerationTest() {
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
	public void generateMovesForBoard1Test() {		
		long whitePieces = 0b0000000000000000000000000011100000010000000010000000000000000000L;
		long blackPieces = 0b0000000000000000000000000000000000001100000100000000000000000000L;
				//#  A  B  C  D  E  F  G  H  #
		 		//1  -  -  -  -  -  -  -  -  #
				//2  -  -  -  -  -  -  -  -  #
				//3  -  -  -  O  O  O  -  -  #
				//4  -  -  -  W  W  W  -  -  # 
				//5  -  -  B  B  W  O  -  -  # 
				//6  -  -  O  W  B  -  -  -  #
				//7  -  -  -  O  O  -  -  -  # 
				//8  -  -  -  -  -  -  -  -  #
				//#  #  #  #  #  #  #  #  #  #
		BitBoard bb = new BitBoard(whitePieces,blackPieces,8,true); //Create board state with current player as black
		ArrayList<BitMove> expectedList = new ArrayList<>();
		expectedList.add(new BitMove(bitMapR.get("d7")));
		expectedList.add(new BitMove(bitMapR.get("e7")));
		expectedList.add(new BitMove(bitMapR.get("c6")));
		expectedList.add(new BitMove(bitMapR.get("f5")));
		expectedList.add(new BitMove(bitMapR.get("d3")));
		expectedList.add(new BitMove(bitMapR.get("e3")));
		expectedList.add(new BitMove(bitMapR.get("f3")));

		assertTimeout(ofMillis(100), ()-> {
			//Test if the number of moves is correct
			assertEquals(expectedList.size(),bb.generateMoves(1).size(),"Incorrect number of moves.");

			//Test each move against the expected move with a maximum time of 100 milliseconds
			for (int i = 0;i<expectedList.size();i++) {
				assertEquals(expectedList.get(i).getBoardPosition(), bb.generateMoves(1).get(i).getBoardPosition(),"Invalid move generated.");
				
			}
		},"Generating moves took longer than expected.");
	}
	
	
	
	@Test
	public void generateMovesForBoard2Test() {	
		long whitePieces = 0b0000000000000000000100000001101000010000001110100000000000000000L;
		long blackPieces = 0b0000000000000000000000000000010000001111000001000000000000000000L;
		//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  -  -  -  -  -  #
		//2  -  -  -  -  -  -  -  -  #
		//3  -  O  -  -  W  -  -  -  #
		//4  -  W  B  W  W  -  -  -  # 
		//5  B  B  B  B  W  -  -  -  # 
		//6  -  W  B  W  W  W  -  -  #
		//7  -  O  -  -  -  -  -  -  # 
		//8  -  -  -  -  -  -  -  -  #
		//#  #  #  #  #  #  #  #  #  #
		BitBoard bb = new BitBoard(whitePieces,blackPieces,15,false);//Create board state with current player as white
		ArrayList<BitMove> expectedList = new ArrayList<>();
		expectedList.add(new BitMove(bitMapR.get("b7")));
		expectedList.add(new BitMove(bitMapR.get("b3")));
		assertTimeout(ofMillis(100), ()-> {
			//Test if the number of moves is correct
			assertEquals(expectedList.size(),bb.generateMoves(1).size(),"Incorrect number of moves.");
			
			//Test each move against the expected move with a maximum time of 100 milliseconds
			for (int i = 0;i<expectedList.size();i++) {
				assertEquals(expectedList.get(i).getBoardPosition(), bb.generateMoves(1).get(i).getBoardPosition(),"Invalid move generated.");
			}
		},"Generating moves took longer than expected.");
	}
	
	@Test
	public void generateMovesForBoard3Test() {	
		long whitePieces = 0b00000000000001000000101000011010001110111011001110101111100000000L;
		long blackPieces = 0b00011100000000000000010000000100010001000000110000010000001111110L;
		//#  A  B  C  D  E  F  G  H  #
		//1  -  -  -  B  B  B  -  -  #
		//2  -  -  O  W  O  -  -  -  #
		//3  -  -  W  B  W  -  -  -  #
		//4  -  -  W  B  W  W  -  -  #
		//5  W  W  W  B  W  W  W  B  #
		//6  W  W  W  B  B  W  W  -  #
		//7  W  W  W  W  W  B  W  -  #
		//8  -  B  B  B  B  B  B  -  #
		//#  #  #  #  #  #  #  #  #  #
		
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,false);//Create board state with current player as white
		ArrayList<BitMove> expectedList = new ArrayList<>();
		expectedList.add(new BitMove(bitMapR.get("c2")));
		expectedList.add(new BitMove(bitMapR.get("e2")));
		
		assertTimeout(ofMillis(100), ()-> {
			//Test if the number of moves is correct
			assertEquals(expectedList.size(),bb.generateMoves(1).size(),"Incorrect number of moves.");
			
			//Test each move against the expected move with a maximum time of 100 milliseconds
			for (int i = 0;i<expectedList.size();i++) {
				assertEquals(expectedList.get(i).getBoardPosition(), bb.generateMoves(1).get(i).getBoardPosition(),"Invalid move generated.");
			}
		},"Generating moves took longer than expected.");
	}
	
	@Test
	public void generateMovesForBoard4Test() {	
		long whitePieces = 0b1000011010000110100011101100110010010000101100001001000010000000L;
		long blackPieces = 0b0011100101111001011100010011001101101111010011110110110101111100L;
		//#  A  B  C  D  E  F  G  H  #
		//1  B  W  W  B  B  B  -  W  #
		//2  B  W  W  B  B  B  B  W  #
		//3  B  W  W  W  B  B  B  W  #
		//4  B  B  W  W  B  B  W  W  #
		//5  B  B  B  B  W  B  B  W  #
		//6  B  B  B  B  W  W  B  W  #
		//7  B  -  B  B  W  B  B  W  #
		//8  -  -  B  B  B  B  B  W  #
		//#  #  #  #  #  #  #  #  #  #
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);//Create board state with current player as black
		ArrayList<BitMove> expectedList = new ArrayList<>();
		expectedList.add(new BitMove(-1));
		assertTimeout(ofMillis(100), ()-> {
			//Test if the number of moves is correct
			assertEquals(expectedList.size(),bb.generateMoves(1).size(),"Incorrect number of moves.");
			
			//Test each move against the expected move with a maximum time of 100 milliseconds
			for (int i = 0;i<expectedList.size();i++) {
				assertEquals(expectedList.get(i).getBoardPosition(), bb.generateMoves(1).get(i).getBoardPosition(),"Invalid move generated.");
			}
		},"Generating moves took longer than expected.");
	}
	
	
	@Test
	public void generateMovesForBoard5Test() {	
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
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);//Create board state with current player as black
		ArrayList<BitMove> expectedList = new ArrayList<>();
		expectedList.add(new BitMove(bitMapR.get("d7")));
		expectedList.add(new BitMove(bitMapR.get("e7")));
		expectedList.add(new BitMove(bitMapR.get("f7")));
		expectedList.add(new BitMove(bitMapR.get("g7")));
		expectedList.add(new BitMove(bitMapR.get("b6")));
		expectedList.add(new BitMove(bitMapR.get("c6")));
		expectedList.add(new BitMove(bitMapR.get("g6")));
		expectedList.add(new BitMove(bitMapR.get("b5")));
		expectedList.add(new BitMove(bitMapR.get("h5")));
		expectedList.add(new BitMove(bitMapR.get("a4")));
		expectedList.add(new BitMove(bitMapR.get("g4")));
		expectedList.add(new BitMove(bitMapR.get("d3")));
		expectedList.add(new BitMove(bitMapR.get("h3")));
		expectedList.add(new BitMove(bitMapR.get("e2")));
		expectedList.add(new BitMove(bitMapR.get("f2")));
		assertTimeout(ofMillis(100), ()-> {
			//Test if the number of moves is correct
			assertEquals(expectedList.size(),bb.generateMoves(1).size(),"Incorrect number of moves.");
			
			//Test each move against the expected move with a maximum time of 100 milliseconds
			for (int i = 0;i<expectedList.size();i++) {
				assertEquals(expectedList.get(i).getBoardPosition(), bb.generateMoves(1).get(i).getBoardPosition(),"Invalid move generated.");

			}
		},"Generating moves took longer than expected.");
	}
	
	@Test
	public void generateMovesForBoard6Test() {	
		long whitePieces = 0b0010000001011100001111100111110001001001001100100111100101111000L;
		long blackPieces = 0b1000001110000010110000011000001010010100100011011000011000000110L;
		
		//#  A  B  C  D  E  F  G  H  #	
		//1  B  B  -  -  -  W  -  B  # 
		//2  -  B  W  W  W  -  W  B  #
		//3  B  W  W  W  W  W  B  B  #
		//4  -  B  W  W  W  W  W  B  #
		//5  W  -  B  W  B  -  W  B  #
		//6  B  W  B  B  W  W  -  B  #
		//7  W  B  B  W  W  W  W  B  #
		//8  -  B  B  W  W  W  W  -  #
		//#  #  #  #  #  #  #  #  #  #
		BitBoard bb = new BitBoard(whitePieces,blackPieces,0,true);//Create board state with current player as black
		ArrayList<BitMove> expectedList = new ArrayList<>();
		expectedList.add(new BitMove(bitMapR.get("a8")));
		expectedList.add(new BitMove(bitMapR.get("h8")));
		expectedList.add(new BitMove(bitMapR.get("g6")));
		expectedList.add(new BitMove(bitMapR.get("b5")));
		expectedList.add(new BitMove(bitMapR.get("f5")));
		expectedList.add(new BitMove(bitMapR.get("a4")));
		expectedList.add(new BitMove(bitMapR.get("f2")));
		expectedList.add(new BitMove(bitMapR.get("c1")));
		expectedList.add(new BitMove(bitMapR.get("d1")));
		expectedList.add(new BitMove(bitMapR.get("e1")));
		expectedList.add(new BitMove(bitMapR.get("g1")));
		assertTimeout(ofMillis(100), ()-> {
			//Test if the number of moves is correct
			assertEquals(expectedList.size(),bb.generateMoves(1).size(),"Incorrect number of moves.");
			
			//Test each move against the expected move with a maximum time of 100 milliseconds
			for (int i = 0;i<expectedList.size();i++) {
				assertEquals(expectedList.get(i).getBoardPosition(), bb.generateMoves(1).get(i).getBoardPosition(),"Invalid move generated.");

			}
		},"Generating moves took longer than expected.");
	}
	

	
}
