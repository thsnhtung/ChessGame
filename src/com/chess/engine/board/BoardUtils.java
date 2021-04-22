package com.chess.engine.board;

public class BoardUtils 
{
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	public static final boolean[] SECOND_COLUMN = initColumn(1);
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);
	public static final boolean[] EIGHTH_COLUMN = initColumn(7);
	public static final boolean[] SECOND_ROW = initRow(8) ;
	public static final boolean[] SEVENTH_ROW = initRow(48) ;
	
	
	
	
	public static final int NUM_TILES = 64 ; 
	public static final int NUM_TILES_PER_ROW = 8 ;

	
	private static boolean[] initColumn(int ColumnNumber)
	{
		final boolean[] column = new boolean[64] ;
		do
		{
			column[ColumnNumber] = true ;
			ColumnNumber += NUM_TILES_PER_ROW;
		}
		while(ColumnNumber < NUM_TILES);
		
		return column ; 
	}
		
	private static boolean[] initRow(int rowNumber) 
	{
		final boolean[] row = new boolean[64] ;
		do
		{
			row[rowNumber] = true ;
			rowNumber ++ ;
		}
		while(rowNumber % NUM_TILES_PER_ROW != 0);
		
		return row ;
	}

	private BoardUtils()
	{
		throw new RuntimeException("You cannot instantiate me! k duoc goi cai lz nay");
	}
	
	public static boolean isValidTileCoordinate(int coordinate) 
	{
		return coordinate >=0 && coordinate < NUM_TILES;
	}
	
	
}
