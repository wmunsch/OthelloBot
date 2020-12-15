package com.williammunsch.othellobot;

import java.util.Comparator;

public class SortByValue implements Comparator<BitMove>{

	//Used for sorting in ascending order of scores
	@Override
	public int compare(BitMove a, BitMove b) {
		return (int)b.getValue() - (int)a.getValue();
	}

}
