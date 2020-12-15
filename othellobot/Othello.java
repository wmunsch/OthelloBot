package com.williammunsch.othellobot;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main class that runs the game thread and handles input and output between the bot and the other player.
 */
public class Othello {

    public static final int ME = 1;
    public static final int OPPONENT = -1;
    public static final int BORDER = -2;
    public static final int EMPTY = 0;
	public static int totalMovesFound = 0;
	public static int totalMovesPruned =0;
	public static int totalMovesPrunedSpecial =0;
	public static Player myPlayer;
	static StringBuilder sb = new StringBuilder();
	public static final Map<Long, String> bitMap = new HashMap<>(); //use two hash maps to allow for instant translation between input and array indexes
	public static final Map<String, Long> bitMapR = new HashMap<>();
	public static boolean gameOver = false;
	public static SortByValue sorter = new SortByValue();
	public static int totalMovesAddedToTranspositionTable = 0;
	public static int totalTimesTTSaved = 0;
	public static int totalTimesEvaluated = 0;
	public static int totalTimesEvaluatedSaved = 0;
	public static long totalScore = 0;
	public static long bestScore = 0;
	
	 //Timer variables
	static Timer timer;
	
	//This is the total time allotted in milliseconds for the bot to play (minus a small margin to allow for lag so it doesn't get disqualified)
	public static int timeRemaining = 599000;//Currently set to 10 minutes (20 minute total game time)
	
	public static boolean timeUp = false;
	public static boolean doneWithPath = false;

	public static Map<Long,HashEntry> transpositionTable = new HashMap<>();

	public static final BitEvaluator evaluator = new BitEvaluator();
	
	  //This array sets the maximum amount of time each turn can take as a percent of the time remaining
	  static double [] timeAllocation = 
		{0.015, 0.015, 0.015, 0.015, 0.025, 0.025, 0.025, 0.025, 0.025, 0.025,
		 0.048, 0.048, 0.048, 0.048, 0.048, 0.048, 0.050, 0.051, 0.052, 0.053,
		 0.044, 0.045, 0.049, 0.049, 0.049, 0.051, 0.053, 0.055, 0.057, 0.059,
		 0.060, 0.060, 0.061, 0.062, 0.063, 0.064, 0.065, 0.065, 0.065, 0.065,
		 0.167, 0.168, 0.169, 0.169, 0.171, 0.172, 0.173, 0.175, 0.180, 0.180,
		 0.181, 0.187, 0.196, 0.199, 0.220, 0.220, 0.220, 0.220, 0.220, 0.220,
		 0.220, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250,
		 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250, 0.250};
	 
		 
    public static void main(String[] args) {
    	//Translates between bits and board positions for console output and input
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
	
    	//Setting up the game and console output
		timer = new Timer();
    	System.out.println("C Starting Othello");
    
    	int currentPlayer;
    	BitMove move = null;
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("C Initialize AI as : ");
    	String input = scanner.nextLine();
    	
    	boolean playercolor=true;
    	
    	if (input.contentEquals("I B")) {
    		myPlayer = new Player("B");
    		playercolor =true;
    		
    	}else if (input.contentEquals("I W")) {
    		myPlayer = new Player("W");
    		playercolor = false;
    	}else {
    		System.out.println("C Incorrect input");
    		myPlayer = null;
    	}
    	
    
    	System.out.println("C ----- BitBoard implementation SO3PDKTT 10min ----- ");
    	BitBoard bitBoard = new BitBoard(playercolor);
    	PrintBitBoard printBitBoard= new PrintBitBoard();

    	
    	if (myPlayer.getColor().contentEquals("B")) {
    		currentPlayer = ME;
    	}else {
    		currentPlayer = OPPONENT;
    	}
    	System.out.println("C                       /  \\ ");
    	System.out.println("C                       |  | ");
    	System.out.println("C        ________       |  | ");
    	System.out.println("C      /          \\     |  |  ");
    	System.out.println("C     /   \\    /   \\    |  |  ");
    	System.out.println("C    /  ( )    ( )  \\   |  |   ");
    	System.out.println("C    |      __      |  _|__|_  ");
    	System.out.println("C    ================   /||    ");
    	System.out.println("C    ================  / ||    ");
    	System.out.println("C   Preparing for battle as " + myPlayer.getColor()+"  ");

   
    	if (myPlayer.getColor().contentEquals("B")) {System.out.println("R B");}
    	else if (myPlayer.getColor().contentEquals("W")) {System.out.println("R W");}
    	
    	//Starting the game loop
    	while(!gameOver) {
    		if (currentPlayer == ME) {
    			//Bots turn! Find the best move.
    			timeUp = false;
    			long timeAllowedForThisMove = (long)(timeAllocation[bitBoard.turn]*(double)timeRemaining);
    			System.out.println("C Milliseconds allowed for this move: " + timeAllowedForThisMove);
    			Timer timer = new Timer();
    			timer.schedule(new TimerTask() { //The bot will continue to search until the timer runs out
					@Override
					public void run() {
						System.out.println("C TIMES UP!");
						timeUp = true;
						timer.cancel();
					}
    				
    			},timeAllowedForThisMove);
    			
    	
    			//Make a back-up move in case recursion takes too long.
    			move = bitBoard.getBestMoveSO(currentPlayer,0);
    			
    			
    			//Set which algorithm is used by the bot. Different algorithms are used for different parts of the game.
    			if (bitBoard.piecesOnBoard<39) { 
    				move = bitBoard.getBestMoveSO3PDK(currentPlayer, 30);//Shallow move ordering with killer move
    			}else if (bitBoard.piecesOnBoard>38 && bitBoard.piecesOnBoard<45) {
    				System.out.println("C --Transpositioning Roots--");
					move = bitBoard.getBestMoveSO3PDKTT(currentPlayer, 30);//Shallow move ordering, killer move, and transposition table
    			}else {
    				System.out.println("C --Finishing Enemy--");
    				move = bitBoard.getBestMoveSOPD(currentPlayer, 30);//Shallow move ordering only
    			}
    			
    		
			
    			timeRemaining -= timeAllowedForThisMove;
    			System.out.println("C time remaining: " + timeRemaining + "ms");
    			timer.cancel();
    	
    		}else {
    			//Player or opposing bot's turn!
    			System.out.println("C Enter your move: ");
    	    	String input2 = scanner.nextLine();
    	    	sb.setLength(0); // reset the stringBuilder to empty for new input
    	    	if(input2.length() ==5) {
    	    		//charAt = 0 is W or B, 2 is column letter, 4 is row number
    	    		sb.append(input2.charAt(2));
    	    		sb.append(input2.charAt(4));
    	    		String play = sb.toString();
    	    		move = new BitMove(bitMapR.get(play));
    	    		
    	    		bitBoard.flipPlayers();
        	    	move.piecesToBeFlipped = bitBoard.getFlipMask(move.getBoardPosition());
        	    	bitBoard.flipPlayers();
    	    	
    	    	}else if (input2.length()==1) {
    	    		move = new BitMove(-1);
    	    	}
    		}
    	
    		System.out.println("C Applying move : " + move.getBoardPosition());
    		
    		bitBoard.applyMove(move,currentPlayer);
    		printBitBoard.print(bitBoard.blackPieces, bitBoard.whitePieces);
    		
    		timeUp = false;
    	
    		if (currentPlayer == ME) {
    			//Bot's turn, apply the move found from the previous if statement
    			System.out.println("C Best move: "+bitMap.get(move.getBoardPosition())+ " " + move.getValue());

        		sb.setLength(0);
    			if (myPlayer.getColor().contentEquals("B")) {
    				sb.append("B ");
    			}else {
    				sb.append("W ");
    			}
    			if (move.getBoardPosition() != -1) {
    				String moveUnformatted = bitMap.get(move.boardPosition);
    				sb.append(moveUnformatted.substring(0,1));
    				sb.append(" ");
    				sb.append(moveUnformatted.substring(1,2));
    				if (move.getValue()>=1000000) {//Win detected
    					System.out.println("C        ___________          ");
    					System.out.println("C      /            \\          ");
    					System.out.println("C     /( ' )____( ' )\\        ");
    					System.out.println("C    /     |\\/ \\/|    \\       ");
    					System.out.println("C \\  |     |/\\_/\\|    |  /     ");
    					System.out.println("C  \\ |================| /       ");
    					System.out.println("C     ================         ");
    					System.out.println("C All your base are belong to us");
    				}else if (move.getValue()<=-1000000) {//Loss detected
    				   	System.out.println("C        _________           ");
    			    	System.out.println("C      /          \\         ");
    			    	System.out.println("C     /            \\        ");
    			    	System.out.println("C    /  ( )  _  ( ) \\       ");
    			    	System.out.println("C \\  |      |_|     |  /   ");
    			    	System.out.println("C  \\ |==============| /     ");
    			    	System.out.println("C     ==============       ");
    			    	System.out.println("C      OH NOEESSS            ");
    				}
    			
    				System.out.println(sb.toString());
    				
    			}else {
    				System.out.println(myPlayer.getColor());
    			}
    			
    		}

    		currentPlayer = -1*currentPlayer; //switch players
    		
    		if (evaluator.countMoves(bitBoard.blackPieces, bitBoard.whitePieces)==0 && evaluator.countMoves(bitBoard.whitePieces, bitBoard.blackPieces)==0) {
    			System.out.println("C Game is over!");
    			gameOver=true;
    		}
    		if(bitBoard.piecesOnBoard>=64) {
    			System.out.println("C Game is over!");
    			gameOver=true;
    		}
    	}
    	System.out.println("C Finished game.");
    
    	scanner.close();
    	timer.cancel();
    	

    }
    
}