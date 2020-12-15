package com.williammunsch.othellobot;
/**
 * Class that prints a visual representation of the game board to the console
 */
public class PrintBitBoard {
	
    public void print(long blackPieces,long whitePieces) {
    	//prints a graphical representation of the board
    	long endOfBoard = 0b1000000000000000000000000000000000000000000000000000000000000000L;
    	
    	System.out.println("C #  A  B  C  D  E  F  G  H  #");
    	
    	System.out.println("C 1  " + 
    		bOrW(endOfBoard>>>7,blackPieces,whitePieces)+ "  " +
    		bOrW(endOfBoard>>>6,blackPieces,whitePieces)+ "  " +
    		bOrW(endOfBoard>>>5,blackPieces,whitePieces)+ "  " +
    		bOrW(endOfBoard>>>4,blackPieces,whitePieces)+ "  " +
    		bOrW(endOfBoard>>>3,blackPieces,whitePieces)+ "  " +
    		bOrW(endOfBoard>>>2,blackPieces,whitePieces)+ "  " +
    		bOrW(endOfBoard>>>1,blackPieces,whitePieces)+ "  " +
    		bOrW(endOfBoard,blackPieces,whitePieces) + "  #");
    	
    	System.out.println("C 2  " + 
    			bOrW(endOfBoard>>>15,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>14,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>13,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>12,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>11,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>10,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>9,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>8,blackPieces,whitePieces) + "  #");
    	
    	System.out.println("C 3  " + 
    			bOrW(endOfBoard>>>23,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>22,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>21,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>20,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>19,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>18,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>17,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>16,blackPieces,whitePieces) + "  #");
    	
    	System.out.println("C 4  " + 
    			bOrW(endOfBoard>>>31,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>30,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>29,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>28,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>27,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>26,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>25,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>24,blackPieces,whitePieces) + "  #");

    	System.out.println("C 5  " + 
    			bOrW(endOfBoard>>>39,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>38,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>37,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>36,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>35,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>34,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>33,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>32,blackPieces,whitePieces) + "  #");
    	
    	System.out.println("C 6  " + 
    			bOrW(endOfBoard>>>47,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>46,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>45,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>44,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>43,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>42,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>41,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>40,blackPieces,whitePieces) + "  #");
    	
    	System.out.println("C 7  " + 
    			bOrW(endOfBoard>>>55,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>54,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>53,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>52,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>51,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>50,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>49,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>48,blackPieces,whitePieces) + "  #");
    	
    	System.out.println("C 8  " + 
    			bOrW(endOfBoard>>>63,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>62,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>61,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>60,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>59,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>58,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>57,blackPieces,whitePieces)+ "  " +
        		bOrW(endOfBoard>>>56,blackPieces,whitePieces) + "  #");
    
    	System.out.println("C #  #  #  #  #  #  #  #  #  #");
    }

    
    private String bOrW(long boardPosition, long blackPieces,long whitePieces) {
    	if ((boardPosition&blackPieces)!=0) {
    		return "B";
    	}else if  ((boardPosition&whitePieces)!=0) {
    		return "W";
    	}
    	return "-";
    }
}
