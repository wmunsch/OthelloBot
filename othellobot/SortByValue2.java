package com.williammunsch.othellobot;

import java.util.Comparator;

public class SortByValue2 implements Comparator<BitMove>{

	//Used for sorting in ascending order of scores
	@Override
	public int compare(BitMove a, BitMove b) {
		return (int)a.getValue() - (int)b.getValue();
	}

}
