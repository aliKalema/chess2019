package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;

public class BoardUtils {
	/*
	 * the following are array of booleans size 64 all false except
	 * for tiles that fall at their respective rows or column
	 */
	public static final String[] ALGEBREIC_NOTATION = 
		{
			     "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                 "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                 "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                 "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                 "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                 "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                 "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                 "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1" 
         };    
	public static final Map<String,Integer>POSITION_TO_COORDINATE = initializePositionToCoordinateMap();
	public static boolean[] FIRST_COLUMN = 
		{
			     true,false,false,false,false,false,false,false,
			     true,false,false,false,false,false,false,false,
			     true,false,false,false,false,false,false,false,
			     true,false,false,false,false,false,false,false,
			     true,false,false,false,false,false,false,false,
			     true,false,false,false,false,false,false,false,
			     true,false,false,false,false,false,false,false,
			     true,false,false,false,false,false,false,false
		};
	public static boolean[] SECOND_COLUMN = 
		{
				 false,true,false,false,false,false,false,false,
				 false,true,false,false,false,false,false,false,
				 false,true,false,false,false,false,false,false,
				 false,true,false,false,false,false,false,false,
				 false,true,false,false,false,false,false,false,
				 false,true,false,false,false,false,false,false,
				 false,true,false,false,false,false,false,false,
				 false,true,false,false,false,false,false,false
			};
	public static boolean[] THIRD_COLUMN = 
		{
				 false,false,true,false,false,false,false,false,
				 false,false,true,false,false,false,false,false,
				 false,false,true,false,false,false,false,false,
				 false,false,true,false,false,false,false,false,
				 false,false,true,false,false,false,false,false,
				 false,false,true,false,false,false,false,false,
				 false,false,true,false,false,false,false,false,
				 false,false,true,false,false,false,false,false
			};
	public static boolean[] FOUTH_COLUMN =
		{
				 false,false,false,true,false,false,false,false,
				 false,false,false,true,false,false,false,false,
				 false,false,false,true,false,false,false,false,
				 false,false,false,true,false,false,false,false,
				 false,false,false,true,false,false,false,false,
 				 false,false,false,true,false,false,false,false,
				 false,false,false,true,false,false,false,false,
				 false,false,false,true,false,false,false,false
			};
	public static boolean[] FIFTH_COLUMN = 
		{
				 false,false,false,false,true,false,false,false,
				 false,false,false,false,true,false,false,false,
				 false,false,false,false,true,false,false,false,
				 false,false,false,false,true,false,false,false,
				 false,false,false,false,true,false,false,false,
				 false,false,false,false,true,false,false,false,
				 false,false,false,false,true,false,false,false,
				 false,false,false,false,true,false,false,false
			};
	public static boolean[] SIXTH_COLUMN =
		{
				 false,false,false,false,false,true,false,false,
				 false,false,false,false,false,true,false,false,
				 false,false,false,false,false,true,false,false,
				 false,false,false,false,false,true,false,false,
				 false,false,false,false,false,true,false,false,
				 false,false,false,false,false,true,false,false,
				 false,false,false,false,false,true,false,false,
				 false,false,false,false,false,true,false,false
			};
	public static boolean[] SEVENTH_COLUMN = 
		{
				 false,false,false,false,false,false,true,false,
				 false,false,false,false,false,false,true,false,
				 false,false,false,false,false,false,true,false,
				 false,false,false,false,false,false,true,false,
				 false,false,false,false,false,false,true,false,
				 false,false,false,false,false,false,true,false,
				 false,false,false,false,false,false,true,false,
				 false,false,false,false,false,false,true,false
			};
	public static boolean[] EIGHTH_COLUMN = 
		{
				 false,false,false,false,false,false,false,true,
				 false,false,false,false,false,false,false,true,
				 false,false,false,false,false,false,false,true,
				 false,false,false,false,false,false,false,true,
				 false,false,false,false,false,false,false,true,
				 false,false,false,false,false,false,false,true,
				 false,false,false,false,false,false,false,true,
				 false,false,false,false,false,false,false,true
			};
	public static boolean[] EIGHTH_RANK = 
		{
			     true,true,true,true,true,true,true,true,
			     false,false,false,false,false,false,false,false,
			     false,false,false,false,false,false,false,false,
			     false,false,false,false,false,false,false,false,
			     false,false,false,false,false,false,false,false,
			     false,false,false,false,false,false,false,false,
			     false,false,false,false,false,false,false,false,
			     false,false,false,false,false,false,false,false
		};
	public static boolean[] SEVENTH_RANK = 
		{
				 false,false,false,false,false,false,false,false,
				 true,true,true,true,true,true,true,true,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false
			};
	public static boolean[] SIXTH_RANK = 
		{
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 true,true,true,true,true,true,true,true,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false
			};
	public static boolean[] FIFTH_RANK =
		{
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 true,true,true,true,true,true,true,true,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false
			};
	public static boolean[] FOUTH_RANK = 
		{
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 true,true,true,true,true,true,true,true,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false
			};
	public static boolean[] THIRD_RANK =
		{
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 true,true,true,true,true,true,true,true,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false
			};
	public static boolean[] SECOND_RANK = 
		{
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 true,true,true,true,true,true,true,true,
				 false,false,false,false,false,false,false,false
			};
	public static boolean[] FIRST_RANK = 
		{
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 false,false,false,false,false,false,false,false,
				 true,true,true,true,true,true,true,true
			};
	public BoardUtils(){
	    throw new RuntimeException("you can not initiatiate me");
	}
	private static Map<String, Integer> initializePositionToCoordinateMap() {
		final Map<String, Integer>positionToCoordinate = new HashMap<>();
		for(int i=0;i<64;i++) {
			positionToCoordinate.put(ALGEBREIC_NOTATION[i], i);
		}
		return positionToCoordinate;
	}
	public static  boolean validTileCoordinate(int coordinate) {
	     return coordinate >= 0 && coordinate < 64;
	}
	public static int getCoordinateAtPosition(int position) {
		return POSITION_TO_COORDINATE.get(position);
	}
	public static String getPositionAtCoordinate(final int coordinate) {
		return ALGEBREIC_NOTATION[coordinate];
	}
}
