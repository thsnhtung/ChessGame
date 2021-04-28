package com.chess.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;


public abstract class Tile 
{
	protected final int tileCoordinate;
	private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPosibleEmptyTile();
	
	private static Map<Integer, EmptyTile> createAllPosibleEmptyTile()
	{
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		for (int  i = 0 ; i < BoardUtils.NUM_TILES ; i ++)
		{
			emptyTileMap.put(i, new EmptyTile(i)) ; 
		}
		Collections.unmodifiableMap(emptyTileMap);
		return emptyTileMap;
	}
	
	public static Tile CreateTile(final int tileCoordinate, final Piece piece)
	{
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILE_CACHE.get(tileCoordinate);
	}
	
	private Tile(final int tileCoordinate)
	{
		this.tileCoordinate = tileCoordinate;
	}
	
	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile
	{
		EmptyTile(final int TileCoordinate)
		{
			super(TileCoordinate); 
		}
		
		@Override
		public Piece getPiece()
		{
			return null ; 
		}
		
		@Override
		public String toString()
		{
			return "_" ;
		}
		
		@Override
		public boolean isTileOccupied()
		{
			return false ; 
		}
	}
	
	public static final class OccupiedTile extends Tile
	{
		private final Piece PieceOnTile;  
		OccupiedTile (final int TileCoordinate,final Piece PieceOnTile)
		{
			super(TileCoordinate); 
			this.PieceOnTile = PieceOnTile; 
		}
		
		@Override
		public Piece getPiece()
		{
			return this.PieceOnTile ; 
		}
		
		@Override
		public String toString()
		{
			return this.getPiece().getPieceAlliance().isBlack() ? this.getPiece().toString().toLowerCase() : this.getPiece().toString() ; 
		}
		
		@Override
		public boolean isTileOccupied()
		{
			return true ; 
		}
		
	}

	public int getTileCoordinate()
	{
		return this.tileCoordinate ; 
	}
}











